package Experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogConcat {
	private String SenderResult;
	private String LastResult;
	private String ReceiverResult;
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
		List<String> IgnoreList = new ArrayList<String>();
		int endIndex = list.length - 11;
		String index = Integer.toString(endIndex);
		BufferedReader reader;
		SenderResult = "";
		LastResult = "";
		ReceiverResult = "";
		temp = "";
		boolean ignore = false;
		boolean skip = false;
		Arrays.sort(list);
		for (int i = endIndex + 1; i <= list.length - 1; ++i) {
			for (File f : list) {
				if (f.getName().contains(Integer.toString(i))) {
					IgnoreList.add(f.getName());
				}
			}
		}
		for (File f : list) {
			ignore = false;
			try {
				reader = new BufferedReader(new FileReader(f));
				String line;
				if (second) {
					if (reader.readLine().equals("Invoice")) {
						skip = true;
						temp = "";
					} else {
						temp = reader.readLine();
					}
				} else {
					temp = reader.readLine();
					if (temp.contains("Invoice")) {
						skip = true;
						temp = "";
					}
				}
				for (String s : IgnoreList) {
					if (f.getName().equals(s)) {
						ignore = true;
//						System.out.println(f.getName() + " ignore");
					}
				}
				if (!f.getName().contains(index) && !ignore) {
					if (!skip) {
						SenderResult += temp + ",";
					} else {
						ReceiverResult += temp + ",";
					}
				} else if (f.getName().contains(index) && !ignore) {
					LastResult += temp;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public String getSenderResult() {
		return SenderResult;
	}

	public String getReceiverResult() {
		return ReceiverResult;
	}

	public String getLastFileResult() {
		return LastResult;
	}
}
