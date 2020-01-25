package htML.invoice;

import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFText2HTML;

public class pdf2html {

	private File output;
	private BufferedWriter writer;

	public pdf2html() {
		try {
			output = new File("/home/quang/testinfo1.txt");
			writer = new BufferedWriter(new FileWriter(output));
			dowork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dowork() {
		try {
			InputStream is = new FileInputStream("/home/quang/PythonScript/invoices/6.pdf");// ..... Read PDF file
			PDDocument pdd = PDDocument.load(is); // This is the in-memory representation of the PDF document.
			PDFText2HTML converter = new PDFText2HTML(); // the converter
			String html = converter.getText(pdd); // That's it!
			System.out.println(html);
			writer.append(html);
			pdd.close();
			is.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTerm() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
