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
	private String dir = "/home/quang/html";
	private int totalDoc = 0;

	private void initialize() {
		try {
			doc = Jsoup.parse(input, "UTF-8", "localhost");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateHtml() {
		String path = "some random path here";
		exec.startCommand("pdftohtml -c " + path + " name.html");
		exec.startCommand("mv *-*.html read.html");
	}

	public DataExtractor(String path) {

		filepath = path;
		input = new File(filepath);
		initialize();
	}

	public DataExtractor(int num) {
		totalDoc = num;
	}

	public void Extractlabelled() {
		try {
			writer = new BufferedWriter(new FileWriter("/home7quang/labelled_invoices.txt"));
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
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ExtractUnlabelled() {
		try {
			if (totalDoc > 0) {
				writer = new BufferedWriter(new FileWriter("/home/quang/unlabelled_invoices.txt"));
				for (int i = 1; i <= totalDoc; ++i) {
					String file = dir+"/"+Integer.toString(i)+".html";
					input = new File(file);
					doc = Jsoup.parse(input, "UTF-8", "localhost");
					Element body = doc.select("body").first();
					Elements test = body.getElementsByTag("p");
					for (int j = 0; j < test.size(); ++j) {
						if (j == 0 || j == 2) {
							writer.append(test.get(j).text());
							writer.append("\n");
						}
					}
					
				}
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
