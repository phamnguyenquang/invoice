package Default;

import NER.NERPreprocess;
import htML.invoice.DataExtractor;
import htML.invoice.pdf2html;

public class App {

	public static void main(String[] args) {
		// Extracting pdf
		new pdf2html("resources/pdf/", "resources/html/");
		new DataExtractor("resources/html/", "resources/coreNLP/data/original/").ExtractUnlabelled();
		new NERPreprocess("unlabelled_invoices.txt", "receipient.txt", "information.txt").doWork();
	}

}
