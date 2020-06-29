package htML.invoice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nd4j.linalg.io.ClassPathResource;

import Backend.CommandExecutor;

public class MassDataExtractor {
	private File fileList;
	private File subFolder;
	private BufferedWriter[] writer;
	private String filepath;
	private String outDir;
	private String subF;
	private Document doc;
	private CommandExecutor exec;
	private int totalDoc = 0;
	private int totalParam = 0;
	private Element body;
	private Elements test;

	private void initialize(File file) {
		try {
			doc = Jsoup.parse(file, "UTF-8", "localhost");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeOutput() {
		int max = 0;
		Element b_body;
		Elements b_test;
		File f;
		for (int i = 0; i < fileList.listFiles().length; ++i) {
			f = fileList.listFiles()[i];
			initialize(f);
			b_body = doc.select("body").first();
			b_test = b_body.getElementsByTag("p");
			if (max < b_test.size()) {
				max = b_test.size();
			}
		}
		writer = new BufferedWriter[max - 4];
		for (int j = 0; j < max - 4; ++j) {
			try {
				writer[j] = new BufferedWriter(new FileWriter(outDir + "file" + Integer.toString(j) + ".txt"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void initializeOutput(File file) {
		initialize(file);
		body = doc.select("body").first();
		test = body.getElementsByTag("p");
		totalParam = test.size();

		subF = outDir + file.getName() + "/";
		subFolder = new File(subF);
		if (!subFolder.isDirectory()) {
			subFolder.mkdir();
		}
	}

	public MassDataExtractor(String baseDir, String out) {

		filepath = new ClassPathResource(baseDir).getPath();
		outDir = new ClassPathResource(out).getPath();
		fileList = new File(filepath);
//		initializeOutput();

	}

	public void extract() {
		try {
			initializeOutput();
			for (File f : fileList.listFiles()) {
				initialize(f);
				Element body = doc.select("body").first();
				Elements test = body.getElementsByTag("p");
				for (int i = 0; i < test.size() - 4; ++i) {
					if (!test.get(i).text().toLowerCase().equals("invoice")) {
						writer[i].append(test.get(i).text());
						writer[i].append("\n");
					}
				}

			}
			for (int i = 0; i < writer.length; ++i) {
				writer[i].close();
			}

			fileList = new File(outDir);

			for (File f : fileList.listFiles()) {
				System.out.println(f.length());
				if (f.length() == 0) {
					f.delete();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void extractPart() {
		try {
			for (File f : fileList.listFiles()) {
				initializeOutput(f);
				BufferedWriter[] bwriter = new BufferedWriter[totalParam - 4];
				for (int j = 0; j < totalParam - 4; ++j) {
					try {
						bwriter[j] = new BufferedWriter(new FileWriter(subF + "part" + Integer.toString(j) + ".txt"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				for (int i = 0; i < test.size() - 4; ++i) {
					System.out.println(test.get(i).text());
					bwriter[i].append(test.get(i).text());
					bwriter[i].close();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
