package Experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.nd4j.linalg.io.ClassPathResource;

public class DateAssessment {
	private String AssessDir = "/resources/coreNLP/data/processed/log.txt";
	private List<String> fileContent = new ArrayList<String>();
	private BufferedReader reader;
	private double result;

	public DateAssessment() {
		try {
			reader = new BufferedReader(new FileReader(new File(new ClassPathResource(AssessDir).getPath())));
			doWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DateAssessment(String dir) {
		AssessDir = dir;
		try {
			reader = new BufferedReader(new FileReader(new File(new ClassPathResource(AssessDir).getPath())));
			doWork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doWork() {
		try {
			String line = reader.readLine();
			int YearAnomaly = 0;
			int DateAnomaly = 0;
			int MonthAnomaly = 0;
			ArrayList<String> sentence = new ArrayList<String>();
			while (line != null) {
				if (!line.isEmpty()) {
					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
					fileContent.add(processedSentence);
				}
				line = reader.readLine();
			}
			for (int i = 0; i < fileContent.size(); ++i) {
				int size = countWordsUsingStringTokenizer(fileContent.get(i));
				sentence = split2(fileContent.get(i), size);
				if (sentence.get(2).toLowerCase().contains("number") || sentence.get(2).toLowerCase().contains("o")) {
					YearAnomaly++;
				}
				if (sentence.get(0).toLowerCase().contains("number") || sentence.get(0).toLowerCase().contains("o")) {
					DateAnomaly++;
				}
				if (sentence.get(1).toLowerCase().contains("number") || sentence.get(1).toLowerCase().contains("o")
						|| sentence.get(1).toLowerCase().contains("person") || sentence.get(1).contains("O")) {
					MonthAnomaly++;
				}
			}
			double total = fileContent.size() / 2;
			double correct = total - YearAnomaly;
			System.out.println("size " + total);
			System.out.println("correct data " + correct);
			System.out.println("wrong data " + YearAnomaly);
			double accuracy = correct / total;
			System.out.println("year Percentage: " + accuracy);
			System.out.println("-------------------------------");
			correct = total - DateAnomaly;
			accuracy = correct / total;
			System.out.println("size " + total);
			System.out.println("correct data " + correct);
			System.out.println("wrong data " + DateAnomaly);
			System.out.println("date Percentage: " + accuracy);
			result = accuracy;
			System.out.println("-------------------------------");
			correct = total - MonthAnomaly;
			accuracy = correct / total;
			System.out.println("size " + total);
			System.out.println("correct data " + correct);
			System.out.println("wrong data " + MonthAnomaly);
			System.out.println("month Percentage: " + accuracy);
			result = accuracy;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getAccuracy() {
		return result;
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

			return text.substring(text.lastIndexOf(" ") + 1); // Extract first word.

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

	public static int countWordsUsingStringTokenizer(String sentence) {
		if (sentence == null || sentence.isEmpty()) {
			return 0;
		}
		StringTokenizer tokens = new StringTokenizer(sentence);
		return tokens.countTokens();
	}

}
