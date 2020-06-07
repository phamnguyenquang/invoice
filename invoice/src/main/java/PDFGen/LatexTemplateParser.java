package PDFGen;

import java.io.File;
import java.io.IOException;

import de.nixosoft.jlr.JLRConverter;
import de.nixosoft.jlr.JLRGenerator;

public class LatexTemplateParser {
	private String filePath = "resources/Template";
	private String pdfOutPath = "resources/pdf";
	private String pdfFile;
	private String textOutPath = "";
	private String templatePath = filePath + "/invoiceTemplate.tex";
	private String outputName;
	private JLRConverter converter;
	private JLRGenerator pdfGen = new JLRGenerator();

	private File template;
	private File workingDirectory;
	private File tempDir;
	private File texOutput;
	private File pdfOutput;

	private void initialize() {
		template = new File(templatePath);
		workingDirectory = new File(filePath);

		tempDir = new File(workingDirectory.getAbsolutePath() + "/temp");
		if (!tempDir.isDirectory()) {
			tempDir.mkdir();
		}
		textOutPath = tempDir + "/" + outputName + ".tex";
		pdfFile = pdfOutPath + "/" + outputName + ".pdf";

		converter = new JLRConverter(workingDirectory);
		pdfGen = new JLRGenerator();
	}

	public LatexTemplateParser(String templateName, String nameOut) {
		templatePath = filePath + "/" + templateName;
		outputName = nameOut;
		initialize();
	}

	public void setTemplte(String template) {
		templatePath = filePath + "/" + template;
		initialize();
	}

	public void setNameOut(String nameOut) {
		outputName = nameOut;
		initialize();
	}

	public void setPdfPath(String name) {
		pdfOutPath = name;
		File f = new File(pdfOutPath);
		if (!f.exists() || !f.isDirectory()) {
			f.mkdir();
		}
		initialize();
	}

	public void setSenderInfo(String senderName, String senderAddr, String senderLocation) {
		converter.replace("SenderName", senderName);
		converter.replace("SenderAddr", senderAddr);
		converter.replace("SenderLocation", senderLocation);
	}

	public void setDate(String date) {
		converter.replace("Date", date);
	}

	public void setReceiverInfo(String receiverName, String receiverAddr, String receiverLocation) {
		converter.replace("ReceiverName", receiverName);
		converter.replace("ReceiverAddr", receiverAddr);
		converter.replace("ReceiverLocation", receiverLocation);
	}

	public void parseOutput() {
		try {
			texOutput = new File(textOutPath);
			converter.parse(template, texOutput);
			pdfOutput = new File(pdfFile);
			File pdfDir = new File(pdfOutPath);
			pdfGen.generate(texOutput, pdfDir, workingDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void ClearLog() {
		// delete other
		File pdfDir = new File(pdfOutPath);
		File listFile[] = pdfDir.listFiles();
		for (int i = 0; i < listFile.length; ++i) {
			File fileName = listFile[i];
			if (fileName.getName().endsWith(".aux") || fileName.getName().endsWith(".log")) {
				boolean success = (listFile[i].delete());
			}
			for (File f : tempDir.listFiles()) {
				f.delete();
			}
		}
		for (File f : tempDir.listFiles()) {
			f.delete();
		}
	}
}
