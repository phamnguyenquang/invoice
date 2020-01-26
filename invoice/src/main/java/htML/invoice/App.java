package htML.invoice;

import DataGenerator.TrainingDataGenerator;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
//		DataExtractor dd = new DataExtractor("/home/quang/PythonScript/invoices/test2s.html");
//		dd.extract();
//		new pdf2html();
		new TrainingDataGenerator(500, "/home/quang/javaDataGen.txt");
	}
}
