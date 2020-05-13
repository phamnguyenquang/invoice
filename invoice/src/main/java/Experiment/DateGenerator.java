package Experiment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.nd4j.linalg.io.ClassPathResource;

public class DateGenerator {
	private String dataDir = "/resources/coreNLP/data/original/ExperimentalData.txt";
	private String[] Months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private String[] MonthsFull = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };
	private String[] MonthsGer = { "Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September",
			"Oktober", "November", "Dezember" };
	private int MonthMode;
	private int yearBegin;
	private int yearEnd;
	private BufferedWriter writerS1;
	private File s1;

	public DateGenerator(int begin, int end, int full, boolean reverse) {
		MonthMode = full;
		yearBegin = begin;
		yearEnd = end;
		try {
			String filePath = new ClassPathResource(dataDir).getPath();
			s1 = new File(filePath);
			writerS1 = new BufferedWriter(new FileWriter(s1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (reverse) {
			doWorkR();
		} else {
			doWork();
		}

	}

	private void doWork() {
		String data = "";
		int mm, dd;
		try {
			for (int i = yearBegin; i < yearEnd; ++i) {
				mm = ThreadLocalRandom.current().nextInt(0, 10);
				dd = ThreadLocalRandom.current().nextInt(0, 30);
				if (MonthMode == 0) {
					data = Integer.toString(dd) + " " + MonthsFull[mm] + " " + Integer.toString(i);
				} else if (MonthMode == 1) {
					data = Integer.toString(dd) + " " + Months[mm] + " " + Integer.toString(i);
				} else if (MonthMode == 2) {
					data = Integer.toString(dd) + " " + MonthsGer[mm] + " " + Integer.toString(i);
				} else {
					data = Integer.toString(dd) + " " + Integer.toString(mm) + " " + Integer.toString(i);
				}

				writerS1.append(data);
				writerS1.append("\n");
			}
			writerS1.close();
		} catch (Exception e) {
			e.printStackTrace();
			;
		}
	}

	private void doWorkR() {
		String data = "";
		int mm, dd;
		try {
			for (int i = yearBegin; i < yearEnd; ++i) {
				mm = ThreadLocalRandom.current().nextInt(0, 10);
				dd = ThreadLocalRandom.current().nextInt(0, 30);
				if (MonthMode == 0) {
					data = MonthsFull[mm] + " " + Integer.toString(dd) + " " + Integer.toString(i);
				} else if (MonthMode == 1) {
					data = Months[mm] + " " + Integer.toString(dd) + " " + Integer.toString(i);
				} else if (MonthMode == 2) {
					data = Integer.toString(dd) + " " + MonthsGer[mm] + " " + Integer.toString(i);
				} else {
					data = Integer.toString(mm) + " " + Integer.toString(dd) + " " + Integer.toString(i);
				}

				writerS1.append(data);
				writerS1.append("\n");
			}

			writerS1.close();
		} catch (

		Exception e) {
			e.printStackTrace();
			;
		}
	}

}
