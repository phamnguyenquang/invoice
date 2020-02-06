package DataGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TrainingDataGenerator {
	private String sender;
	private String receiver;
	private File s1;
	private File s2;
	private File r1;
	private File r2;
	private int total;
	private BufferedWriter writerS1;
	private BufferedWriter writerS2;
	private BufferedWriter writerR1;
	private BufferedWriter writerR2;

	public TrainingDataGenerator(int number, String FileOutputDir, boolean separate) {

		try {

			s1 = new File(FileOutputDir + "sender1.txt");
			s2 = new File(FileOutputDir + "sender2.txt");
			r1 = new File(FileOutputDir + "receiver1.txt");
			r2 = new File(FileOutputDir + "receiver2.txt");
			total = number;
			writerS1 = new BufferedWriter(new FileWriter(s1));
			writerS2 = new BufferedWriter(new FileWriter(s2));
			writerR1 = new BufferedWriter(new FileWriter(r1));
			writerR2 = new BufferedWriter(new FileWriter(r2));
			sender = "";
			receiver = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (separate) {
			generateTrainingContent();
		} else {
			generateContent();
		}
	}

	private void generateContent() {
		try {
			for (int i = 0; i < total; ++i) {
				sender = "sender " + Integer.toString(i) + " [AddressSend " + Integer.toString(i) + "]"
						+ "[AddressMisc " + Integer.toString(i) + "]" + "[City " + Integer.toString(i) + "]" + "[7200"
						+ Integer.toString(i) + "]" + "[01245785" + Integer.toString(i) + "]" + " [email@sender"
						+ Integer.toString(i) + ".example].";
				receiver = "Bill to rec " + Integer.toString(i) + " [AddressRec " + Integer.toString(i) + "]"
						+ "[AddressMisc " + Integer.toString(i) + "]" + "[City " + Integer.toString(i) + "]" + "[7700"
						+ Integer.toString(i) + "]" + "[04245785" + Integer.toString(i) + "]" + "[email@rec"
						+ Integer.toString(i) + ".example].";

				writerS1.append(sender);
				writerS1.append('\t');
				writerS1.append("0");
				writerS1.append("\n");

				writerS1.append(receiver);
				writerS1.append('\t');
				writerS1.append("1");
				writerS1.append("\n");
			}
			writerS1.close();
			writerR1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateTrainingContent() {
		try {
			for (int i = 0; i < total; ++i) {
				sender = "sender " + Integer.toString(i) + " [AddressSend " + Integer.toString(i) + "]"
						+ "[AddressMisc " + Integer.toString(i) + "]" + "[City " + Integer.toString(i) + "]" + "[7200"
						+ Integer.toString(i) + "]" + "[01245785" + Integer.toString(i) + "]" + " [email@sender"
						+ Integer.toString(i) + ".example].";
				receiver = "Bill to rec " + Integer.toString(i) + " [AddressRec " + Integer.toString(i) + "]"
						+ "[AddressMisc " + Integer.toString(i) + "]" + "[City " + Integer.toString(i) + "]" + "[7700"
						+ Integer.toString(i) + "]" + "[04245785" + Integer.toString(i) + "]" + "[email@rec"
						+ Integer.toString(i) + ".example].";

				writerS1.append(sender);
				writerS1.append("\n");

				writerR1.append(receiver);
				writerR1.append("\n");
			}
			writerS1.close();
			writerR1.close();
			for (int i = 0; i < total; ++i) {
				sender = "sender " + Integer.toString(i + total) + " [AddressSend " + Integer.toString(i + total) + "]"
						+ "[AddressMisc " + Integer.toString(i + total) + "]" + "[City " + Integer.toString(i + total)
						+ "]" + "[7200" + Integer.toString(i + total) + "]" + "[01245785" + Integer.toString(i) + "]"
						+ " [email@sender" + Integer.toString(i + total) + ".example].";
				receiver = "Bill to rec " + Integer.toString(i + total) + " [AddressRec " + Integer.toString(i + total)
						+ "]" + "[AddressMisc " + Integer.toString(i + total) + "]" + "[City "
						+ Integer.toString(i + total) + "]" + "[7700" + Integer.toString(i + total) + "]" + "[04245785"
						+ Integer.toString(i + total) + "]" + "[email@rec" + Integer.toString(i + total) + ".example].";

				writerS2.append(sender);
				writerS2.append("\n");

				writerR2.append(receiver);
				writerR2.append("\n");
			}
			writerS2.close();
			writerR2.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
