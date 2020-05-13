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

public class OverallAssessment {
	private String AssessDir = "/resources/coreNLP/data/processed/log.txt";
	private List<String> fileContent = new ArrayList<String>();
	private BufferedReader reader;

	public OverallAssessment() {
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
			int anomaly = 0;
			ArrayList<String> sentence = new ArrayList<String>();
			boolean location = false;
			boolean country = false;
			while (line != null) {
				if (!line.isEmpty()) {
					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
					fileContent.add(processedSentence);
				}
				line = reader.readLine();
			}
			for (int i = 0; i < fileContent.size(); ++i) {
				if (fileContent.get(i) != " ") {
					int size = countWordsUsingStringTokenizer(fileContent.get(i));
					sentence = split2(fileContent.get(i), size);
					location = false;
					country = false;
					for (int j = 0; j < sentence.size(); ++j) {
						System.out.println(sentence.get(j));
						if (sentence.get(j).toLowerCase().contains("country")) {
							country = true;
						}
						if (sentence.get(j).toLowerCase().contains("location")
								|| sentence.get(j).toLowerCase().contains("state")
								|| sentence.get(j).toLowerCase().contains("city")) {
							location = true;
						}
					}
					if (country&&location) {
						anomaly++;
					}
				}
			}
			double total = fileContent.size() / 2;
			System.out.println("size " + total);
			System.out.println("correct data " + anomaly);
			double accuracy = anomaly / total;
			System.out.println("name Percentage: " + accuracy);

		} catch (Exception e) {
			e.printStackTrace();
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
