package Default;

import java.io.FileNotFoundException;
import java.io.IOException;

import DataGenerator.DictionaryReader;
import DataGenerator.TagFileDataGenerator;
import DataGenerator.TrainingPdfGenerator;
import DataGenerator.MLTrainingDataGen;
import Experiment.Annotator;
import Experiment.CompanyAssessment;
import Experiment.CountryAssessment;
import Experiment.DateAssessment;
import Experiment.DateGenerator;
import Experiment.LocationAssessment;
import Experiment.LogBackup;
import Experiment.NameAssessment;
import NER.CoreNlpPreprocess;
import NER.CustomModelTagging;
import NER.ModelTraining;
import NER.OpenNlpPreprocess;
import PDFGen.LatexTemplateParser;
import htML.invoice.DataExtractor;

/**
 * Hello world!
 *
 */
public class AppTest {
	public static void main(String[] args) {
//
//		new ModelTraining("resources/coreNLP/CustomAnnotator/customModel.ser.gz",
//				"resources/coreNLP/CustomAnnotator/sampleProp.txt",
//				"resources/coreNLP/CustomAnnotator/GMB_dataset.txt");
//		double k = 0;
//		for (int i = 0; i < 5; ++i) {
//			// Data Generation Part
//			/*------------------------------                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
//			 * 2 = German, 0=full, 1= short, 3=number
//			 -------------------------------*/
//		new DateGenerator(2000, 2500, 1, true);
//			new TagFileDataGenerator("resources/coreNLP/data/original/ExperimentalData.txt").doWork();
//			// Test Part
//			System.out.println("done here");
//			new Annotator("ExperimentalData.txt", "neural").doWork();
////			 Evaluation and backup
//			k+= new DateAssessment().getAccuracy();
//			k += new NameAssessment().getAccuracy();
//			k += new CountryAssessment().getAccuracy();
//			k += new CompanyAssessment().getAccuracy();
//			new LogBackup("resources/coreNLP/data/processed/log.txt",
//					"resources/coreNLP/Log/log" + Integer.toString(i) + ".txt");
//		}
//		System.out.println("average " + k / 5);
		LatexTemplateParser tex2pdf = new LatexTemplateParser("HomogeneousInvoice.tex", "GenericInvoice");
		TrainingPdfGenerator pdfgen = new TrainingPdfGenerator("HomogeneousInvoice.tex", "GenericInvoice", 1, 0);
		for(int i=0;i<10;++i) {
			String name = "GenericInvoice"+Integer.toString(i);
			pdfgen.setPdfName(name);
			pdfgen.generateData();
		}
	}
}
