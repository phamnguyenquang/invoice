package PDFGen;

import de.nixosoft.jlr.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JRLTex {

	public static void main(String[] args) {

		String filePath = "resources/Template";
		String fileOutPath = "resources/pdf";
		String templatePath = filePath + "/invoiceTemplate.tex";

		File workingDirectory = new File(filePath);

		File template = new File(templatePath);

		File tempDir = new File(workingDirectory.getAbsolutePath() + "/temp");
		if (!tempDir.isDirectory()) {
			tempDir.mkdir();
		}

		File invoice1 = new File(tempDir.getAbsolutePath() + "/invoice1.tex");
		File invoice2 = new File(tempDir.getAbsolutePath() + "/invoice2.tex");

		try {
			JLRConverter converter = new JLRConverter(workingDirectory);

			converter.replace("Number", "1");
			converter.replace("CustomerName", "Ivan Pfeiffer");
			converter.replace("CustomerStreet", "Schwarzer Weg 4");
			converter.replace("CustomerZip", "13505 Berlin");

			// make some table, refer to the template file for more info

			ArrayList<ArrayList<String>> services = new ArrayList<ArrayList<String>>();

			ArrayList<String> subservice1 = new ArrayList<String>();
			ArrayList<String> subservice2 = new ArrayList<String>();
			ArrayList<String> subservice3 = new ArrayList<String>();

			subservice1.add("Software");
			subservice1.add("50");
			subservice2.add("Hardware1");
			subservice2.add("500");
			subservice3.add("Hardware2");
			subservice3.add("850");

			services.add(subservice1);
			services.add(subservice2);
			services.add(subservice3);

			converter.replace("services", services);

			// end table

			converter.parse(template, invoice1);

			converter.replace("Number", "2");
			converter.replace("CustomerName", "Mike Mueller");
			converter.replace("CustomerStreet", "Prenzlauer Berg 12");
			converter.replace("CustomerZip", "10405 Berlin");

			services = new ArrayList<ArrayList<String>>();

			subservice1 = new ArrayList<String>();
			subservice2 = new ArrayList<String>();
			subservice3 = new ArrayList<String>();

			subservice1.add("Software");
			subservice1.add("150");
			subservice2.add("Hardware");
			subservice2.add("500");
			subservice3.add("Test");
			subservice3.add("350");

			services.add(subservice1);
			services.add(subservice2);
			services.add(subservice3);

			converter.replace("services", services);

			converter.parse(template, invoice2);
			File desktop = new File(fileOutPath);

			JLRGenerator pdfGen = new JLRGenerator();

			pdfGen.generate(invoice1, desktop, workingDirectory);
			File pdf1 = pdfGen.getPDF();

			pdfGen.generate(invoice2, desktop, workingDirectory);
			File pdf2 = pdfGen.getPDF();

		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
	}

}
