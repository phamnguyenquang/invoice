package Default;

import NER.CoreNlpPreprocess;
import NER.OpenNlpPreprocess;
import htML.invoice.DataExtractor;
import htML.invoice.pdf2html;
import pyscript.PythonModel;

public class App {

	public static void main(String[] args) {
		// Extracting pdf
//		new pdf2html("resources/pdf/", "resources/html/");
//		new DataExtractor("resources/html/", "resources/coreNLP/data/original/").ExtractUnlabelled();
		new CoreNlpPreprocess("unlabelled_invoices.txt", "receipient.txt", "information.txt","neural").doWork();
//		PythonModel test = new PythonModel("tokenizer.json", "model_config.json","model_weights.h5", 100);
//		test.GetPredictResult("resources/coreNLP/data/temp/unlabelled_invoices.txt");
//		test.WriteResultToFile();
		
	}

}
