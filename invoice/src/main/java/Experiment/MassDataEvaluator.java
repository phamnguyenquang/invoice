package Experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MassDataEvaluator {
	private String LogDir = "resources/coreNLP/data/Log/";
	private String Log1;
	private String Log2;
	private boolean[] result;
	private boolean[] isDate;
	private boolean[] isPerson;
	private boolean[] isCompany;
	private boolean[] isSenderLocation;
	private boolean[] isReceiverLocation;
	private BufferedReader reader1;
	private BufferedReader reader2;
	private List<String> first;
	private List<String> second;
	private Pattern datePattern;
	private Pattern CompanyPattern;
	private Pattern PersonPattern;
	private Pattern LocationPattern;
	private Matcher matcher;

	public MassDataEvaluator(String dir) {
		LogDir = dir;
		Log1 = dir + "logDebug.txt";
		Log2 = dir + "logDebugLast.txt";
		result = new boolean[10000];
		isPerson = new boolean[10000];
		isCompany = new boolean[10000];
		isSenderLocation = new boolean[10000];
		isReceiverLocation = new boolean[10000];
		isDate = new boolean[10000];
		initiailze();

	}

	public void doWork() {
		dataAssessment();
	}

	private void initiailze() {
		try {
			reader1 = new BufferedReader(new FileReader(new File(Log1)));
			reader2 = new BufferedReader(new FileReader(new File(Log2)));
			for (int i = 0; i < 10000; ++i) {
				result[i] = false;
				isPerson[i] = false;
				isCompany[i] = false;
				isSenderLocation[i] = false;
				isReceiverLocation[i] = false;
				isDate[i] = false;

			}
			ReadLogs();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void dataAssessment() {
		/*
		 * On the fact that both log file hold the same number of lines
		 */
		datePattern = Pattern.compile("(DATE)[\\s](DATE)[\\s]+(DATE)[\\s]*$");
//		CompanyPattern = Pattern.compile("^(ORGANIZATION)[\\s](ORGANIZATION)[\\s]((ORGANIZATION)[\\s])+");
		CompanyPattern = Pattern.compile("^((ORGANIZATION)[\\s])+");
		PersonPattern = Pattern.compile("^(PERSON)[\\s](PERSON)[\\s](PERSON)[\\s]");
		LocationPattern = Pattern.compile(".*((CITY)(\\s))+((LOCATION|STATE_OR_PROVINCE)(\\s))+(COUNTRY).*");

		int DateTrue = 0;
		int CompTrue = 0;
		int SenderLocTrue = 0;
		int PersonTrue = 0;
		int RecLocTrue = 0;
		int ResultTrue = 0;
		for (int i = 0; i < first.size(); ++i) {
			if (datePattern.matcher(first.get(i)).find()) {
				isDate[i] = true;
				++DateTrue;
			}

			if (LocationPattern.matcher(first.get(i)).find()) {
				isSenderLocation[i] = true;
				++SenderLocTrue;
			}

			if (LocationPattern.matcher(second.get(i)).find()) {
				isReceiverLocation[i] = true;
				++RecLocTrue;
			}
			//Switch between person and org here
			if (PersonPattern.matcher(first.get(i)).find()) {
				isPerson[i] = true;
				++PersonTrue;
			}
			if (CompanyPattern.matcher(second.get(i)).find()) {
				isCompany[i] = true;
				++CompTrue;
			}
		}
		for (int i = 0; i < result.length; ++i) {
			if (isDate[i] && isCompany[i] && isSenderLocation[i] && isPerson[i] && isReceiverLocation[i]) {
				result[i] = true;
				++ResultTrue;
			}
		}
		System.out.println("Date Acc: " + DateTrue);
		System.out.println("Company Acc: " + CompTrue);
		System.out.println("SenderLoc Acc: " + SenderLocTrue);
		System.out.println("Person Acc: " + PersonTrue);
		System.out.println("ReceiverLoc Acc: " + RecLocTrue);
		System.out.println("Result Acc: " + ResultTrue);
	}

	private void ReadLogs() {
		try {
			String line = reader1.readLine();
			first = new ArrayList<String>();
			second = new ArrayList<String>();
			while (line != null) {
				if (!line.isEmpty()) {
					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
					first.add(processedSentence);
				}
				line = reader1.readLine();
			}
			line = reader2.readLine();
			while (line != null) {
				if (!line.isEmpty()) {
					String processedSentence = line.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
					second.add(processedSentence);
				}
				line = reader2.readLine();
			}
			System.out.println("part size: " + first.size());
			System.out.println("full size: " + second.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
