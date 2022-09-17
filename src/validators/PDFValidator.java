package validators;

import java.io.File;


public class PDFValidator {
	private String file;
	private static String dir = "C:\\Users\\crist\\Desktop\\pdfDir\\input"; // atributo apenas para teste - n�o deve permanecer na classe
	
	public PDFValidator(String file) {
		this.file = file;	
		System.out.println(file);
	}
	
	public boolean validDocument() {
		boolean documentValidity = validExtension();
		if(documentValidity) {
			documentValidity = validSize();
		}
		return documentValidity;
	}
	
	private boolean validExtension() {

		String fileExtension = getFileExtension(this.file);

		boolean documentValidity = false;

		if (fileExtension.equals("pdf")) {
			documentValidity = true;
		}
		return documentValidity;
	}
	
	
	private boolean validSize() {
		
		File f = new File(this.dir +"\\" +this.file);
		
		double sizeFile = f.length();

		boolean documentValidity = false;

		if (sizeFile <= 102400) {
			documentValidity = true;
		}

		return documentValidity;
	}
	

	static String getFileExtension(String filename) {
		if (filename.contains("."))
			return filename.substring(filename.lastIndexOf(".") + 1);
		else
			return "";
	}
}
