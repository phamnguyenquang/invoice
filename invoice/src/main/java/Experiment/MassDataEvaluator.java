package Experiment;

import java.io.BufferedWriter;
import java.io.File;

public class MassDataEvaluator {
	private File[] fileList;
	private File FileDir;
	private File[] logList;
	private String LogDi = "/resources/coreNLP/data/Log/";
	private boolean[] result;

	private BufferedWriter[] writer;

	public MassDataEvaluator(String dir) {
		FileDir = new File(dir);
	}

	private void initiailze() {
		int totalFile = FileDir.listFiles().length;
		logList = new File[totalFile];
		fileList = new File[totalFile];
		fileList = FileDir.listFiles();
		writer = new BufferedWriter[totalFile];

	}

	private void doWork() {

	}
}
