package DataGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TrainingDataGenerator {
	private String[] columns = { "sender", "receiver" };
	private String sender;
	private String receiver;
	private File output;
	private int total;
	private BufferedWriter writer;

	public TrainingDataGenerator(int number, String FileOutput) {

		try {
			output = new File(FileOutput);
			total = number;
			writer = new BufferedWriter(new FileWriter(output));
			sender = "";
			receiver = "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		generateContent();
	}

	private void generateContent() {
		try {
			for (int i = 0; i < total; ++i) {
				sender = "sender " + Integer.toString(i) + " [AddressSend " + Integer.toString(i) + "]"
						+ "[AddressMisc " + Integer.toString(i) + "]" + "[City " + Integer.toString(i) + "]"
						+ "[7200" + Integer.toString(i) + "]" + "[01245785" + Integer.toString(i) + "]"
						+ " [email@sender" + Integer.toString(i) + ".example].";
				receiver = "Bill to rec " + Integer.toString(i) + " [AddressRec " + Integer.toString(i) + "]"
						+ "[AddressMisc " + Integer.toString(i) + "]" + "[City " + Integer.toString(i) + "]"
						+ "[7700" + Integer.toString(i) + "]" + "[04245785" + Integer.toString(i) + "]"
						+ "[email@rec" + Integer.toString(i) + ".example].";

				writer.append(sender);
				writer.append('\t');
				writer.append("0");
				writer.append("\n");

				writer.append(receiver);
				writer.append('\t');
				writer.append("1");
				writer.append("\n");
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
