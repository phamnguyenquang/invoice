package Default;

import java.io.File;
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
		String[] pathnames;
		File f = new File("resources/coreNLP/data/original");
		pathnames = f.list();

		for (int i = 0; i < pathnames.length; ++i) {
			System.out.println(pathnames[i]);
			new Annotator(pathnames[i], "neural").doWork();
			new LogBackup("resources/coreNLP/data/processed/log.txt",
					"resources/coreNLP/data/Log/log" + Integer.toString(i) + ".txt");
//			new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
//					"resources/coreNLP/data/Debug/logDebug" + Integer.toString(i) + ".txt");
		}

	}

}
