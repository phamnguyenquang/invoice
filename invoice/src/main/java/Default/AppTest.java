package Default;

import java.io.IOException;

import DataGenerator.TrainingDataGenerator;
import ML.CNN;
import NER.NERPreprocess;
import TestClass.BoW;
import TestClass.CSVTest;
import TestClass.NERTest;
import htML.invoice.DataExtractor;
import pyscript.TestImport;

/**
 * Hello world!
 *
 */
public class AppTest {
	public static void main(String[] args) {
//		DataExtractor dd = new DataExtractor(5);
//		dd.ExtractUnlabelled();
//		new pdf2html(7);
//		new TrainingDataGenerator(500, "resources/text/", false, false);
//		new NERPreprocess("unlabelled_invoices.txt", "receipient.txt", "information.txt").doWork();
//		new CNN();
//		BoW test = new BoW("/resources/text/JavaData.txt");
//		test.WriteToCSV("/resources/dataSet/train/sender1.txt", "/resources/dataSet/temp/sender.txt", "0");
//		test.WriteToCSV("/resources/dataSet/train/receiver1.txt", "/resources/dataSet/temp/sender.txt", "1");
//		try {
//			test.FinishTrainingWrite();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		test.WriteToTestCSV("/resources/dataSet/predict/unlabelled_invoices.txt", "/resources/dataSet/temp/fit.txt");
//		new CSVTest(test.getArraySize());
		new TestImport();
//		new NERTest();
	}
}
