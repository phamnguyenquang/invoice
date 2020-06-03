package htML.invoice;

import java.io.InputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.hadoop.hdfs.protocol.proto.ClientNamenodeProtocolProtos.MkdirsRequestProto;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.tools.PDFText2HTML;
import org.nd4j.linalg.io.ClassPathResource;

public class pdf2html {

	private File output;
	private BufferedWriter writer;
	private String dir;
	private String outDir;

	public pdf2html(String baseDir, String out) {
		try {
			dir = new ClassPathResource(baseDir).getPath();
			outDir = new ClassPathResource(out).getPath();
			File f = new File(outDir);
			if (!f.exists() || !f.isDirectory()) {
				f.mkdir();
			}
			dowork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dowork() {
		try {

			File list = new File(dir);

			for (File f : list.listFiles()) {
				if (!f.isDirectory()) {
					System.out.println("processing: " + f.getName());
					output = new File(outDir + f.getName().replace(".pdf", "") + ".html");
					writer = new BufferedWriter(new FileWriter(output));
					InputStream is = new FileInputStream(dir + f.getName());// ..... Read PDF
																			// file
					PDDocument pdd = PDDocument.load(is); // This is the in-memory representation of the PDF document.
					PDFText2HTML converter = new PDFText2HTML(); // the converter
//				converter.setSortByPosition(true);
					String html = converter.getText(pdd); // That's it!
					System.out.println(html);
					writer.append(html);
					pdd.close();
					is.close();
					writer.close();
				}
			}

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
}
