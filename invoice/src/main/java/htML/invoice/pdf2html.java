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
	private int total;

	public pdf2html(int n) {
		try {
			total = n;
			dowork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dowork() {
		try {
			for (int i = 1; i <= total; ++i) {
				System.out.println(i);
				output = new File("/home/quang/html/" + Integer.toString(i) + ".html");
				writer = new BufferedWriter(new FileWriter(output));
				InputStream is = new FileInputStream(
						"/home/quang/PythonScript/invoices/" + Integer.toString(i) + ".pdf");// ..... Read PDF file
				PDDocument pdd = PDDocument.load(is); // This is the in-memory representation of the PDF document.
				PDFText2HTML converter = new PDFText2HTML(); // the converter
				String html = converter.getText(pdd); // That's it!
//				System.out.println(html);
				writer.append(html);
				pdd.close();
				is.close();
				writer.close();
			}

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
