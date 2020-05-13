package Experiment;

import java.io.File;

import org.spark_project.guava.io.Files;

public class LogBackup {
	private String source;
	private String dest;

	public LogBackup(String s, String d) {
		source = s;
		dest = d;
		doWork();
	}

	private void doWork() {
		try {
			File s = new File(source);
			File d = new File(dest);
			com.google.common.io.Files.copy(s, d);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
