package Experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class LogMerge {
//	private LogConcat make;
	private File folder;
	private BufferedWriter writer;
	private BufferedWriter writer1;
	private int lower;
	private int upper;
	private boolean second;

	public LogMerge(String file, boolean secondLine) {
		folder = new File(file);
		second=secondLine;
//		half = firstHalf;
		try {
			writer = new BufferedWriter(new FileWriter(new File(file + "logDebug.txt")));
			writer1 = new BufferedWriter(new FileWriter(new File(file + "logDebugLast.txt")));
			doWork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public LogMerge(String file,int batchNo, int index, boolean secondLine) {
		folder = new File(file);
		upper = batchNo * index - 1;
		lower = batchNo * (index - 1);
		second=secondLine;
		try {
			writer = new BufferedWriter(new FileWriter(new File(file + "logDebug.txt")));
			writer1 = new BufferedWriter(new FileWriter(new File(file + "logDebugLast.txt")));
			doWork();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(File f) {
		try {
			if (f.isDirectory()) {
				System.out.println(f.getName());
				LogConcat make = new LogConcat(f.getAbsolutePath(), second);
				writer.append(make.getPartResult());
				writer.append("\n");
				writer1.append(make.getLastFileResult());
				writer1.append("\n");
				System.gc();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doWork() {
		try {
			File dir[] = folder.listFiles();
			Arrays.sort(dir);
			for (int i = lower; i < upper; ++i) {
				File f = dir[i];
				process(f);
			}
			writer.close();
			writer1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
