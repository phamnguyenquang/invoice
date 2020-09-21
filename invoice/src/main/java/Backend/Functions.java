package Backend;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import Experiment.Annotator;
import Experiment.LogBackup;
import ParallelProgramming.ParallelPdfGen;
import htML.invoice.MassDataExtractor;
import htML.invoice.pdf2html;

public class Functions {
	public Functions() {

	}

	public void GeneratePDF(String name, int Stype, int Rtype, int SLength, int Rlength) {
		/*
		 * Left to right: tex template, output dir, senderType,
		 * receivierType,SenderLength(!), ReceiverLength(!!) 0 for person, 1 for real
		 * company, 2 for imaginary company, left to right (!) & (!!): 2 = 2 words, 3 =
		 * 3 words, other number = mix
		 */
		new Thread(new ParallelPdfGen(name + ".tex", name, Stype, Rtype, SLength, Rlength, 3333, 1)).start();
		new Thread(new ParallelPdfGen(name + ".tex", name, Stype, Rtype, SLength, Rlength, 3333, 2)).start();
		new Thread(new ParallelPdfGen(name + ".tex", name, Stype, Rtype, SLength, Rlength, 3333, 3)).start();
	}

	public void GenerateTestPDF(String name, int Stype, int Rtype, int SLength, int Rlength) {
		/*
		 * Left to right: tex template, output dir, senderType,
		 * receivierType,SenderLength(!), ReceiverLength(!!) 0 for person, 1 for real
		 * company, 2 for imaginary company, left to right (!) & (!!): 2 = 2 words, 3 =
		 * 3 words, other number = mix
		 */
		new Thread(new ParallelPdfGen(name + ".tex", name, Stype, Rtype, SLength, Rlength, 20, 1)).start();
	}

	public void ConvertToHtml(String name, boolean part) {
		File ff = new File("resources/coreNLP/data/original/");
		for (File fff : ff.listFiles()) {
			try {
				if (fff.isDirectory()) {
					FileUtils.deleteDirectory(fff);
				} else {
					fff.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		new pdf2html("resources/pdf/" + name + "/", "resources/html/" + name + "/");
		if (part) {
			new MassDataExtractor("resources/html/" + name + "/", "resources/coreNLP/data/original/").extractPart();
		} else {
			new MassDataExtractor("resources/html/" + name + "/", "resources/coreNLP/data/original/").extract();
		}
	}

	public void Annotate(String name) {
		String[] pathnames;
		File f = new File("resources/coreNLP/data/original");
		pathnames = f.list();
		String llogName = "resources/coreNLP/data/Log/" + name + "/";
		File ffLog = new File(llogName);
		if (!ffLog.isDirectory()) {
			ffLog.mkdir();
		}
		for (File f1 : f.listFiles()) {
			if (f1.isDirectory()) {
				String logName = llogName + f1.getName();
				File fLog = new File(logName);
				if (!fLog.isDirectory()) {
					fLog.mkdir();
				}
				int i = 0;
				for (File f2 : f1.listFiles()) {
					System.out.println(f2.getName());
					String absPath = f1.getName() + "/" + f2.getName() + "/";
					new Annotator(absPath, "neural").doWork();
					new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
							logName + "/logDebug_" + f2.getName());
					i++;
				}

			}
		}
	}

	public void AnnotateMass(String name) {
		/*
		 * Use after merge file, (with removing fragmented annotated data) warning, may
		 * be obsolete.
		 */
		new Annotator("file0.txt", "neural").doWork();
		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
				"resources/coreNLP/data/Debug/" + name + "/logDebug0.txt");
		new Annotator("file2.txt", "neural").doWork();
		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
				"resources/coreNLP/data/Debug/" + name + "/logDebug2.txt");
		new Annotator("file3.txt", "neural").doWork();
		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
				"resources/coreNLP/data/Debug/" + name + "/logDebug3.txt");
	}

	public void AnnotateMerged(String name) {
		/*
		 * Use after merging log (a separate function not coded here) May be obsolete
		 */
		new Annotator("logDebug.txt", "neural").doWork();
		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
				"resources/coreNLP/data/Debug/" + name + "/logDebug.txt");
		new Annotator("logDebugLast.txt", "neural").doWork();
		new LogBackup("resources/coreNLP/data/processed/logDebug.txt",
				"resources/coreNLP/data/Debug/" + name + "/logDebugLast.txt");
	}
}
