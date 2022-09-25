package validators;

import java.io.File;

public class PDFValidator {

	public PDFValidator() {

	}
	
	public boolean validateDocument(String file, String inputFolderPath) {

		boolean isValidDocument = checkFileExtension(file);

		if(isValidDocument) {
			isValidDocument = validSize(inputFolderPath, file);
		}
		return isValidDocument;
	}
	
	private boolean checkFileExtension(String file) {
		String fileExtension = getFileExtension(file);

		return fileExtension.equals("pdf");
	}
	
	private boolean validSize(String inputFolderPath, String file) {
		File f = new File(inputFolderPath + File.separator + file);

		return f.length() <= 204800;
	}

	static String getFileExtension(String filename) {
		if (filename.contains("."))
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}
}