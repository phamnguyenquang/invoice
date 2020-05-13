package Experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nd4j.linalg.io.ClassPathResource;

public class LocationAssessment {
	private String AssessDir = "/resources/coreNLP/data/processed/log.txt";
	private List<String> fileContent = new ArrayList<String>();
	private BufferedReader reader;
	private double accuracy;

	public LocationAssessment() {
		try {
			reader = new BufferedReader(new FileReader(new File(new ClassPathResource(AssessDir).getPath())));
//			doWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doWork() {
		try {
			String line = reader.readLine();
			int anomaly = 0;
			ArrayList<String> sentence = new ArrayList<String>();

			while (line != null) {
				if (!line.isEmpty()) {
					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
					fileContent.add(processedSentence);
				}
				line = reader.readLine();
			}
			for (int i = 0; i < fileContent.size(); ++i) {
				if (getLastWord(fileContent.get(i)).toLowerCase().contains("country")) {
					anomaly++;
				}
			}
			double total = fileContent.size() / 3;
			System.out.println("size " + total);
			System.out.println("correct data " + anomaly);
			accuracy = anomaly / total;
			System.out.println("country Percentage: " + accuracy);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getAccucary() {
		return accuracy;
	}

	private String getFirstWord(String text) {

		int index = text.indexOf(' ');

		if (index > -1) { // Check if there is more than one word.

			return text.substring(0, index).trim(); // Extract first word.

		} else {

			return text; // Text is the first word itself.
		}
	}

	private String getLastWord(String text) {

		int index = text.indexOf(' ');

		if (index > -1) { // Check if there is more than one word.

			return text.substring(text.lastIndexOf(" ") - 7); // Extract first word.

		} else {

			return text; // Text is the first word itself.
		}
	}

	public static ArrayList<String> split2(String line, int n) {
		line += " ";
		Pattern pattern = Pattern.compile("\\w*\\s");
		Matcher matcher = pattern.matcher(line);
		ArrayList<String> list = new ArrayList<String>();
		int i = 0;
		while (matcher.find()) {
			if (i != n)
				list.add(matcher.group());
			else
				break;
			i++;
		}
		return list;
	}

}
