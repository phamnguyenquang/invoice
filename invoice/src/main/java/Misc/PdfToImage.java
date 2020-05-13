package Misc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PdfToImage {
	private String path;
	private String OUTPUT_DIR = "/home/quang/";

	private void doWork() {
		try {
			PDDocument document = PDDocument.load(new File(path));
			PDFRenderer pdfRenderer = new PDFRenderer(document);
			for (int page = 0; page < document.getNumberOfPages(); ++page) {
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
				String fileName = OUTPUT_DIR + "image-" + page + ".jpg";
				ImageIOUtil.writeImage(bim, fileName, 300);
			}
			document.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PdfToImage(String filePath) {
		path = filePath;
		doWork();
	}

}
