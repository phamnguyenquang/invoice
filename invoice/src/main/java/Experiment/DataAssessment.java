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

public class DataAssessment {
	private String AssessDir = "/resources/coreNLP/data/processed/log.txt";
	private List<String> fileContent = new ArrayList<String>();
	private BufferedReader reader;
	private double result;
	private Pattern CompanyPattern;
	private Pattern MistagCompanyPattern;
	private String RegEx = "";
	private String MisTagRegEx = "";

	public DataAssessment() {
		try {
			reader = new BufferedReader(new FileReader(new File(new ClassPathResource(AssessDir).getPath())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataAssessment(String dir) {
		AssessDir = dir;
		try {
			reader = new BufferedReader(new FileReader(new File(new ClassPathResource(AssessDir).getPath())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void AssessFileByRegex(String Regex) {
		RegEx = Regex;
		doWork();
	}

	public void SetRegEx(String regex) {
		RegEx = regex;
	}

	public void SetMistagRegEx(String regex) {
		MisTagRegEx = regex;
	}

	public void getDetails() {
		geMistagtDetails();
	}
	
	public void getDetailsByMistagRegex(String regex)
	{
		SetMistagRegEx(regex);
		geMistagtDetails();
	}

	public void AssessFile() {
		doWork();
	}

	private void doWork() {
		try {
			CompanyPattern = Pattern.compile(RegEx);
			String line = reader.readLine();
			int correct = 0;
			ArrayList<String> sentence = new ArrayList<String>();
			while (line != null) {
				if (!line.isEmpty()) {
					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
					fileContent.add(processedSentence);
				}
				line = reader.readLine();
			}
			for (int i = 0; i < fileContent.size(); ++i) {
				if (fileContent.get(i) != " ") {
					if (CompanyPattern.matcher(fileContent.get(i)).find()) {
						correct++;
					}
				}
			}
			double total = fileContent.size() / 2;
			System.out.println("size " + total);
			System.out.println("correct data " + correct);
			double accuracy = correct / total;
			System.out.println("org Percentage: " + accuracy);
			result = accuracy;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getAccuracy() {
		return result;
	}

	private void geMistagtDetails() {
		try {
			int mistag = 0;
			MistagCompanyPattern = Pattern.compile(MisTagRegEx);
			String line = reader.readLine();
//			fileContent.clear();
//			while (line != null) {
//				if (!line.isEmpty()) {
//					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
//					fileContent.add(processedSentence);
//				}
//				line = reader.readLine();
//			}
			for (int i = 0; i < fileContent.size(); ++i) {
				if (fileContent.get(i) != " ") {
					if (MistagCompanyPattern.matcher(fileContent.get(i)).find()) {
						mistag++;
					}
				}

			}
			System.out.println(mistag);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
