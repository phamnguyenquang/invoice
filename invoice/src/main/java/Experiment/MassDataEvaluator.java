package Experiment;

import java.io.BufferedWriter;
import java.io.File;

public class MassDataEvaluator {
	private File[] fileList;
	private File FileDir;
	private File[] logList;
	private String LogDir = "/resources/coreNLP/data/Log/";
	private boolean[] result;
	private boolean isDate;
	private boolean isPerson;
	private boolean isCompany;
	private boolean isLocation;

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
	
	private void dataAssessment()
	{
		
	}

	private void doWork() {

	}
}
