package Experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

public class LogConcat {
	private String result;
	private String result1;
	private String temp;
	private File folder;
	private boolean second;

	public LogConcat(String folderDir, boolean secondLine) {
		folder = new File(folderDir);
		second = secondLine;
		doWork();
	}

	public LogConcat(File folderDir, boolean secondLine) {
		folder = folderDir;
		second = secondLine;
		doWork();
	}

	private void doWork() {
		File[] list = folder.listFiles();
		int i = list.length - 1;
		String index = Integer.toString(i);
		BufferedReader reader;
		result = "";
		result1 = "";
		temp = "";
		Arrays.sort(list);
		for (File f : list) {

			try {
				reader = new BufferedReader(new FileReader(f));
				String line;
				if (second) {
					reader.readLine();
					temp = reader.readLine();
				} else {
					temp = reader.readLine();
				}
				if (!f.getName().contains(index)) {
					result += temp + ",";
				} else {
					result1 += temp;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public String getPartResult() {
		return result;
	}

	public String getLastFileResult() {
		return result1;
	}
}
