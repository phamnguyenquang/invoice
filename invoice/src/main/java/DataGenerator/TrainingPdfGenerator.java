package DataGenerator;

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
	private String nameOut = "";
	private int senderType;
	private int receiverType;

	private DictionaryReader name;

	private void generateSenderNameContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		SenderName = name.getRandomGivenName() + " " + name.getRandomSureName();
//		SenderAddress = "Scheidswald str 61, ";
		SenderAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		SenderLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateSenderCompanyContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
//		SenderName = name.getRandomGivenName() + " " + name.getRandomSureName() + " " + "Ltd";
		SenderName = name.getRandomCompanyName();
		SenderAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		SenderLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateRecNameContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		ReceiverName = name.getRandomGivenName() + " " + name.getRandomSureName();
//		ReceiverAddress = "Scheidswald str 61";
		ReceiverAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		ReceiverLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generateRecCompanyContent() {
		int i = ThreadLocalRandom.current().nextInt(10000, 99999);
		ReceiverName = name.getRandomGivenName() + " " + name.getRandomSureName() + " " + "Ltd";
//		ReceiverAddress = "Scheidswald str 61";
		ReceiverAddress = name.getRandomGivenName() + " " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 100));
		ReceiverLocation = Integer.toString(i) + ", " + name.getRandomCity() + ", " + name.getRandomStatte() + ", "
				+ name.getRandomCountry();
	}

	private void generatePdf() {
		try {
			tex2pdf.setSenderInfo(SenderName, SenderAddress, SenderLocation);
			tex2pdf.setReceiverInfo(ReceiverName, ReceiverAddress, ReceiverLocation);
			tex2pdf.parseOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TrainingPdfGenerator(String template, String nameOutput, int sType, int rType) {
		templateName = template;
		nameOut = nameOutput;
		senderType = sType;
		receiverType = rType;

		name = new DictionaryReader("resources/Dictionary/");
	}

	public void generateData() {
		tex2pdf = new LatexTemplateParser(templateName, nameOut);
		if (senderType == 0) {
			generateSenderNameContent();
		} else {
			generateSenderCompanyContent();
		}
		if (receiverType == 0) {
			generateRecNameContent();
		} else {
			generateRecCompanyContent();
		}
		generatePdf();

	}

	public void setPdfName(String name) {
		nameOut = name;
	}
}
