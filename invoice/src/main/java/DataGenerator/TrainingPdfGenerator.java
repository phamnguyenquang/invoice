package DataGenerator;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import PDFGen.LatexTemplateParser;

public class TrainingPdfGenerator {
	private LatexTemplateParser tex2pdf;
	private String SenderLocation = "";
	private String SenderName = "";
	private String SenderAddress = "";
	private String ReceiverName = "";
	private String ReceiverAddress = "";
	private String ReceiverLocation = "";
	private String templateName = "";
	private String Date = "";
	private String nameOut = "";
	private String dirOut = "";
	private int senderType;
	private int receiverType;
	private String[] OrgKeyword = { "Inc", "Ltd", "Pte Ltd", "Corp", "LLC" };
	private int totalKeyword = OrgKeyword.length;

	private DictionaryReader name;

	private void generateSenderNameContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		SenderName = name.getRandomGivenName() + " " + name.getRandomMiddleName() + " " + name.getRandomSureName();
//		SenderAddress = "Scheidswald str 61, ";
		SenderAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		SenderLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateSenderCompanyContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		SenderName = name.getRandomCompanyName();
		SenderAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		SenderLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateVirtualSenderCompanyContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		int j = ThreadLocalRandom.current().nextInt(0, totalKeyword - 1);
		SenderName = name.getRandomGivenName() + " " + name.getRandomMiddleName() + " " + name.getRandomSureName() + " "
				+ OrgKeyword[j];
//		SenderName = name.getRandomCompanyName();
		SenderAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		SenderLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateRecNameContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		ReceiverName = name.getRandomGivenName() + " " + name.getRandomMiddleName() + " " + name.getRandomSureName();
//		ReceiverAddress = "Scheidswald str 61";
		ReceiverAddress = name.getRandomGivenName() + " "
				+ Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		ReceiverLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateRecCompanyContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		ReceiverName = name.getRandomGivenName() + " " + name.getRandomSureName() + " " + "Ltd";
//		ReceiverAddress = "Scheidswald str 61";
		ReceiverAddress = name.getRandomGivenName() + " "
				+ Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		ReceiverLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateVirtualRecCompanyContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		int j = ThreadLocalRandom.current().nextInt(0, totalKeyword - 1);
		ReceiverName = name.getRandomGivenName() + " " + name.getRandomMiddleName() + " " + name.getRandomSureName()
				+ " " + OrgKeyword[j];
		ReceiverAddress = name.getRandomGivenName() + " "
				+ Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		ReceiverLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateDate() {
		Date = name.getRandomDate();
	}

	private void generatePdf() {
		try {
			tex2pdf.setSenderInfo(SenderName, SenderAddress, SenderLocation);
			tex2pdf.setReceiverInfo(ReceiverName, ReceiverAddress, ReceiverLocation);
			tex2pdf.setDate(Date);
			tex2pdf.parseOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TrainingPdfGenerator(String template, String nameOutput, int sType, int rType) {
		templateName = template;
		nameOut = nameOutput;
		dirOut = nameOutput;
		senderType = sType;
		receiverType = rType;
		name = new DictionaryReader("resources/Dictionary/");
	}

	public void generateData() {
		tex2pdf = new LatexTemplateParser(templateName, nameOut);
		tex2pdf.setPdfPath("resources/pdf/" + dirOut);
		if (senderType == 0) {
			generateSenderNameContent();
		} else if (senderType == 1) {
			generateSenderCompanyContent();
		} else {
			generateVirtualSenderCompanyContent();
		}
		if (receiverType == 0) {
			generateRecNameContent();
		} else if (receiverType == 1) {
			generateRecCompanyContent();
		} else {
			generateVirtualRecCompanyContent();
		}
		generateDate();
		generatePdf();

	}

	public void ClearAux() {
		tex2pdf.ClearLog();
	}

	public void setPdfName(String name) {
		nameOut = name;
	}
}
