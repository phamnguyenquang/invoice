package htML.invoice;

import DataGenerator.TrainingDataGenerator;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		DataExtractor dd = new DataExtractor(7);
		dd.ExtractUnlabelled();
//		new pdf2html(7);
//		new TrainingDataGenerator(500, "/home/quang/javaDataGen.txt");
	}
}
