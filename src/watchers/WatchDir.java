package watchers;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import exporters.DocumentExporter;
import extractors.PDFExtractor;
import handlers.DataHandler;
import org.apache.pdfbox.pdmodel.PDDocument;
import persistence.ConfigPersistence;
import validators.PDFValidator;

public class WatchDir {

	private static ConfigPersistence configPersistence;
	private static PDFExtractor pdfExtractor;
	private static DocumentExporter documentExporter;
	private static DataHandler dataHandler;
	private static PDFValidator pdfValidator;

	public WatchDir(ConfigPersistence configPersistence) {
		this.configPersistence = configPersistence;

		pdfExtractor = new PDFExtractor();

		documentExporter = new DocumentExporter(configPersistence);
		dataHandler = new DataHandler(documentExporter, configPersistence);
		pdfValidator = new PDFValidator();
	}

	public static void watchDir() {

		try {

			System.out.println("Watching directory for changes " + configPersistence.getInputFolderPath());

			// Create a watch service
			WatchService watchService = FileSystems.getDefault().newWatchService();

			// Get the path of the directory which you want to monitor.
			Path directory = Path.of(configPersistence.getInputFolderPath());

			// Register the directory with the watch service
			WatchKey watchKey = directory.register(watchService,
					StandardWatchEventKinds.ENTRY_CREATE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_DELETE);

			// Poll for events
			while (true) {
				for (WatchEvent<?> event : watchKey.pollEvents()) {

					WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;

					// Get file name from even context
					Path fileName = pathEvent.context();

					// Check type of event.
					WatchEvent.Kind<?> kind = event.kind();

					// Perform necessary action with the event
					if (kind == StandardWatchEventKinds.ENTRY_CREATE) {

						System.out.println("A new file is created : " + fileName);

						boolean isDocumentValid = pdfValidator.validateDocument(fileName.toString(), configPersistence.getInputFolderPath());
						System.out.println("Document " + fileName.toAbsolutePath().toString() + " isValid? " + isDocumentValid);

						if (isDocumentValid) {
							ProcessDocument(configPersistence.getInputFolderPath() + File.separator + fileName.toString());
						}
					}

					/*if (kind == StandardWatchEventKinds.ENTRY_DELETE) {

						System.out.println("A file has been deleted: " + fileName);
					}
					if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {

						System.out.println("A file has been modified: " + fileName);
					}*/

				}

				// Reset the watch key everytime for continuing to use it for further
				// event polling
				boolean valid = watchKey.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException x) {
			System.err.println(x);
		}
	}

	private static void ProcessDocument(String filePath) {
		try {
			PDDocument pdDocument = pdfExtractor.InitializeDocument(filePath);
			dataHandler.HandleData(pdDocument);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}

