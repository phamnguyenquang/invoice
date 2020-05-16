package Default;

import DataGenerator.TrainingPdfGenerator;
import Misc.PdfToImage;
import NER.CoreNlpPreprocess;
import NER.OpenNlpPreprocess;
import htML.invoice.DataExtractor;
import htML.invoice.MassDataExtractor;
import htML.invoice.pdf2html;
import pyscript.PythonModel;

public class App {

	public static void main(String[] args) {
		TrainingPdfGenerator pdfgen = new TrainingPdfGenerator("HomogeneousInvoice.tex", "GenericInvoice", 1, 0);
		for (int i = 0; i < 1000; ++i) {
			String name = "NonGenericInvoice" + Integer.toString(i);
			pdfgen.setPdfName(name);
			pdfgen.generateData();
		}

		// Misc, convert PDF to Image
		// Extracting pdf
		new pdf2html("resources/pdf/", "resources/html/");
		new MassDataExtractor("resources/html/", "resources/coreNLP/data/original/").extract();
//		new CoreNlpPreprocess("unlabelled_invoices.txt","neural")
//				.doWork();
//		new TagSwitcher("/resources/html/2.html").doWork();
//		PythonModel test = new PythonModel("tokenizer.json", "model_config.json","model_weights.h5", 100);
//		test.GetPredictResult("resources/coreNLP/data/temp/unlabelled_invoices.txt");
//		test.WriteResultToFile();

	}

}
