package watchers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import validators.PDFValidator;
public class WatchDir {
	
private static String dir = "C:\\Users\\crist\\Desktop\\pdfDir\\input";
	
	public static void watchDir() {

		try {

			System.out.println("Watching directory for changes");

			// Create a watch service
			WatchService watchService = FileSystems.getDefault().newWatchService();

			// Get the path of the directory which you want to monitor.
			Path directory = Path.of(dir);

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
						
						
						PDFValidator vpdf = new PDFValidator(fileName.toString());
						boolean documentValidity = vpdf.validDocument();
						System.out.println(documentValidity);
						
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

}
