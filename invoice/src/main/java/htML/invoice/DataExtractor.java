package htML.invoice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Backend.CommandExecutor;

public class DataExtractor {

	private File input;
	private File output;
	private BufferedWriter writer;
	private String filepath;
	private Document doc;
	private CommandExecutor exec;

	private void initialize() {
		try {
			doc = Jsoup.parse(input, "UTF-8", "localhost");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeInit() {
		try {
			output = new File("/home/quang/testinfo.txt");
			writer = new BufferedWriter(new FileWriter(output));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void writeTerm() {
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DataExtractor(String path) {

		filepath = path;
		input = new File(filepath);
		initialize();
		writeInit();
	}

	public void extract() {
		try {
			Element body = doc.select("body").first();
			Elements test = body.getElementsByTag("p");
			System.out.println("printing");
			System.out.println(test.size());
			for (int i = 0; i < test.size(); ++i) {
				writer.append(test.get(i).text());
				writer.append(" ");
				if (i == 2) {
					writer.append("0");
					writer.append("\n");
				} else if (i == 4) {
					writer.append("1");
					writer.append("\n");
				}
			}
			writer.append("2");
			writeTerm();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
