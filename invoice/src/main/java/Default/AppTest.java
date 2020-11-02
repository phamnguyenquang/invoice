package Default;

import Backend.Functions;
import Experiment.DataAssessment;
import Experiment.LogBackup;
import Experiment.LogMerge;

/**
 * Hello world!
 *
 */
public class AppTest {
	public static void main(String[] args) {
		Functions generalWork = new Functions();
		/*
		 * multicore pdf generator Left to right: tex template, output dir, senderType,
		 * receivierType,SenderLength(!), ReceiverLength(!!) 0 for person, 1 for real
		 * company, 2 for imaginary company, left to right (!) & (!!): 2 = 2 words, 3 =
		 * 3 words, other number = mix
		 */
//		generalWork.GeneratePDF("InvoiceReversedWParagraph2.5","InvoiceReversedWParagraph2.5RealComp", 1, 0, 1, 1);
//		generalWork.GenerateTestPDF("InvoiceNew2", 2, 0, 1, 1);
// ===================================================================================================
//	
		/*
		 * pdf to html, look in the class for the concrete implementation true for
		 * extracting part, false for putting all into 1 text file
		 */
//		generalWork.ConvertToHtml("HInvoiceWParagraph2.5RealComp"); //ALREADY: CORP, LLC, LTD,INC, PTE LTD,ORG
//		generalWork.ConvertToHtml("HInvoiceWParagraphRealComp"); //ALREADY: CORP, LLC, LTD,INC, PTE LTD,ORG,RealComp
//		generalWork.GetRawText("HInvoiceWParagraph2.5RealComp", true);
//===================================================================================================
		/*
		 * Annotating and logging the result look in the file for concrete
		 * implementation
		 * 
		 */
//		generalWork.Annotate("HInvoiceWParagraph2.5RealComp");
//		generalWork.AnnotateMass("HInvoiceWParagraphRealComp");
//===================================================================================================

		/*
		 * Log merge, 2nd line merge as the boolean option. False, means no tagging
		 * included. This option comes before mass annotating (unfragmented) for result
		 * compare True, means tagging included. This comes before general data
		 * assessment purpose
		 */
//		new LogMerge("resources/coreNLP/data/Log/HInvoiceWParagraph2.5LLC/", 9988, 1, true);
//		new LogMerge("resources/coreNLP/data/Log/InvoiceRI2.5VO/", 9999, 1, false);

		/*
		 * Backup log Use after log merge in this class Also called in other class
		 */
//		new LogBackup("resources/coreNLP/data/Log/HInvoiceWParagraph2.5LLC/logDebugS.txt",
//				"resources/coreNLP/data/Debug/HInvoiceWParagraph2.5LLC/logDebugS.txt");
//		new LogBackup("resources/coreNLP/data/Log/HInvoiceWParagraph2.5LLC/logDebugR.txt",
//				"resources/coreNLP/data/Debug/HInvoiceWParagraph2.5LLC/logDebugR.txt");
//		new LogBackup("resources/coreNLP/data/Log/HInvoiceWParagraph2.5LLC/logDebugLast.txt",
//				"resources/coreNLP/data/Debug/HInvoiceWParagraph2.5LLC/logDebugLast.txt");
//===================================================================================================
		/*
		 * Data Assessment, Use various regex for assessing accuracy
		 */
		DataAssessment CA = new DataAssessment("resources/coreNLP/data/Debug/HInvoiceWParagraph2.5RealComp/logDebugS.txt");
		CA.setLogUnfragment(false);
		CA.AssessFileByRegex("^(ORGANIZATION)[\\s](ORGANIZATION)[\\s]+.*");	
		CA.getDetails();
	}

}
