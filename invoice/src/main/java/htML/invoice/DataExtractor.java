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


public class DataExtractor {

	private File fileList;
	private File output;
	private BufferedWriter writer;
	private String filepath;
	private String outDir;
	private Document doc;
	private int totalDoc = 0;

	private void initialize(File file) {
		try {
			doc = Jsoup.parse(file, "UTF-8", "localhost");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataExtractor(String baseDir, String out) {

		filepath = new ClassPathResource(baseDir).getPath();
		outDir = new ClassPathResource(out).getPath();
		fileList = new File(filepath);

	}

	public void Extractlabelled() {
		try {
			writer = new BufferedWriter(new FileWriter(outDir + "labelled_invoices.txt"));
			for (File f : fileList.listFiles()) {
				initialize(f);
				Element body = doc.select("body").first();
				Elements test = body.getElementsByTag("p");
				System.out.println("printing");
				System.out.println(test.size());
				for (int i = 0; i < test.size(); ++i) {
					writer.append(test.get(i).text());
					writer.append(" ");
					if (i == 1) {
						writer.append("0");
						writer.append("\n");
					} else if (i == 3) {
						writer.append("1");
						writer.append("\n");
					}
				}
				writer.append("2");
				writer.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ExtractUnlabelled() {
		try {

			writer = new BufferedWriter(new FileWriter(outDir + "unlabelled_invoices.txt"));

			for (File f : fileList.listFiles()) {
				initialize(f);
				Element body = doc.select("body").first();
				Elements test = body.getElementsByTag("p");
				for (int j = 0; j < test.size(); ++j) {
					writer.append(test.get(j).text());
					writer.append("\n");
				}

			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
