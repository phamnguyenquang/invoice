package htML.invoice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nd4j.linalg.io.ClassPathResource;

import Backend.CommandExecutor;

public class MassDataExtractor {
	private File fileList;
	private BufferedWriter[] writer;
	private String filepath;
	private String outDir;
	private Document doc;
	private CommandExecutor exec;
	private int totalDoc = 0;

	private void initialize(File file) {
		try {
			doc = Jsoup.parse(file, "UTF-8", "localhost");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeOutput() {
		File f = fileList.listFiles()[0];
		initialize(f);
		Element body = doc.select("body").first();
		Elements test = body.getElementsByTag("p");
		int i = test.size();
		writer = new BufferedWriter[i];
		for (int j = 0; j < i; ++j) {
			try {
				writer[j] = new BufferedWriter(new FileWriter(outDir + "file" + Integer.toString(j) + ".txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public MassDataExtractor(String baseDir, String out) {

		filepath = new ClassPathResource(baseDir).getPath();
		outDir = new ClassPathResource(out).getPath();
		fileList = new File(filepath);
		initializeOutput();

	}

	public void extract() {
		try {
			for (File f : fileList.listFiles()) {
				initialize(f);
				Element body = doc.select("body").first();
				Elements test = body.getElementsByTag("p");
				System.out.println("printing");
				System.out.println(test.size());
				for (int i = 0; i < test.size(); ++i) {
					writer[i].append(test.get(i).text());
					writer[i].append("\n");
				}

			}
			for (int i = 0; i < writer.length; ++i) {
				writer[i].close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}