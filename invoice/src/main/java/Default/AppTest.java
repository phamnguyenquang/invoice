package Default;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import DataGenerator.DictionaryReader;
import DataGenerator.TagFileDataGenerator;
import DataGenerator.TrainingPdfGenerator;
import Experiment.Annotator;
import Experiment.DataAssessment;
import Experiment.DateGenerator;
import Experiment.LogBackup;
import Experiment.LogConcat;
import Experiment.LogMerge;
import Experiment.MassDataEvaluator;
import NER.CoreNlpPreprocess;
import NER.CustomModelTagging;
import NER.ModelTraining;
import NER.OpenNlpPreprocess;
import PDFGen.LatexTemplateParser;
import PDFGen.MLTrainingDataGen;
import ParallelProgramming.ParallelPdfGen;
import ch.qos.logback.core.boolex.Matcher;
import htML.invoice.DataExtractor;
import htML.invoice.MassDataExtractor;
import htML.invoice.pdf2html;

/**
 * Hello world!
 *
 */
public class AppTest {
	public static void main(String[] args) {
		/*
		 * Left to right: tex template, output dir, senderType, receivierType 0 for
		 * person, 1 for real company, 2 for imaginary company, left to right
		 */
//		TrainingPdfGenerator pdfgen = new TrainingPdfGenerator("2Col.tex", "2ColRealCompany", 1, 0);
//		for (int i = 0; i < 1619; ++i) {
//			pdfgen.setIndex(i);
//			System.out.println(i);
//			String name = "2ColRealCompany" + Integer.toString(i);
//			pdfgen.setPdfName(name);
//			pdfgen.generateData();
//		}
//		pdfgen.ClearAux();
//===================================================================================================
//multicore pdf generator
//		new Thread(new ParallelPdfGen("2Col.tex", "2Col3WordOrg", 2, 0, 2500, 1)).start();
//		new Thread(new ParallelPdfGen("2Col.tex", "2Col3WordOrg", 2, 0, 2500, 2)).start();
//		new Thread(new ParallelPdfGen("2Col.tex", "2Col3WordOrg", 2, 0, 2500, 3)).start();
//		new Thread(new ParallelPdfGen("2Col.tex", "2Col3WordOrg", 2, 0, 2500, 4)).start();

// ===================================================================================================
//
//		// Misc, convert PDF to Image
//		 Extracting pdf
//		File ff = new File("resources/coreNLP/data/original/");
//		for (File fff : ff.listFiles()) {
//			try {
//				if (fff.isDirectory()) {
//					FileUtils.deleteDirectory(fff);
//				} else {
//					fff.delete();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		new pdf2html("resources/pdf/2Col3WordOrg/", "resources/html/2Col3WordOrg/");
//		new MassDataExtractor("resources/html/2Col3WordOrg/", "resources/coreNLP/data/original/").extractPart();
//===================================================================================================
//New Annotate Version
//USe this 
//		String[] pathnames;
//		File f = new File("resources/coreNLP/data/original");
//		pathnames = f.list();
//		String llogName = "resources/coreNLP/data/Log/2Col3WordOrg/";
//		File ffLog = new File(llogName);
//		if (!ffLog.isDirectory()) {
//			ffLog.mkdir();
//		}
//		for (File f1 : f.listFiles()) {
//			if (f1.isDirectory()) {
//				String logName = llogName + f1.getName();
//				File fLog = new File(logName);
//				if (!fLog.isDirectory()) {
//					fLog.mkdir();
//				}
//				int i = 0;
//				for (File f2 : f1.listFiles()) {
//					System.out.println(f2.getName());
//					String absPath = f1.getName() + "/" + f2.getName() + "/";
//					new Annotator(absPath, "neural").doWork();
//					new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
//							logName + "/logDebug_" + f2.getName());
//					i++;
//				}
//
//			}
//		}
//===================================================================================================
//Annotating Mass, use for mass file
//		new Annotator("file0.txt", "neural").doWork();
//		new LogBackup("resources/coreNLP/data/processed/logDebug.txt", "resources/coreNLP/data/Debug/HRealCompany/logDebug0.txt");
//		new Annotator("file2.txt", "neural").doWork();
//		new LogBackup("resources/coreNLP/data/processed/logDebug.txt", "resources/coreNLP/data/Debug/HRealCompany/logDebug2.txt");
//		new Annotator("file3.txt", "neural").doWork();
//		new LogBackup("resources/coreNLP/data/processed/logDebug.txt", "resources/coreNLP/data/Debug/HRealCompany/logDebug3.txt");
//		DataAssessment CA = new DataAssessment("resources/coreNLP/data/Debug/2ColAF3WordPteLtd/logDebug.txt");
//		CA.AssessFileByRegex("^(NUMBER)[\\s](ORGANIZATION)[\\s](ORGANIZATION)[\\s]((ORGANIZATION)[\\s])+.*");	
//		CA.getDetails();
//		new LocationAssessment("resources/coreNLP/data/Debug/2ColAF3WordPteLtd/logDebugLast.txt");
//		new DateAssessment("resources/coreNLP/data/Debug/2Col3WordOrg/logDebug.txt");
		DataAssessment CA = new DataAssessment("resources/coreNLP/data/Debug/2Col3WordInc/logDebug.txt");
		CA.AssessFileByRegex("^.*((CITY)[\\s])+((STATE_OR_PROVINCE)|(LOCATION)[\\s])+((COUNTRY)[\\s])+.*");
//		CA.getDetails();
//===================================================================================================
//		new LogMerge("resources/coreNLP/data/Log/2Col3WordCorp/",10000, 1, true);
//		new MassDataEvaluator("resources/coreNLP/data/Log/2Col3WordCorp/").doWork();
//===================================================================================================
//Annotating Mass, use for debugging purpose
//		new LogMerge("resources/coreNLP/data/Log/2Col3WordPteLtd/",10000, 1, false);
//		new Annotator("logDebug.txt", "neural").doWork();
//		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
//				"resources/coreNLP/data/Debug/2Col3WordPteLtd/logDebug.txt");
//		new Annotator("logDebugLast.txt", "neural").doWork();
//		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
//				"resources/coreNLP/data/Debug/2Col3WordPteLtd/logDebugLast.txt");
	}

}
