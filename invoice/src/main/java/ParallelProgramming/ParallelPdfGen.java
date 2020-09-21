package ParallelProgramming;

import DataGenerator.TrainingPdfGenerator;

public class ParallelPdfGen implements Runnable {
	private TrainingPdfGenerator pdfgen;
	private int begin;
	private int end;
	private String pdfName;

	public ParallelPdfGen(String template, String outDir, int SenderType, int ReceiverType, int SLength, int RLength,
			int amount, int batch) {
		pdfgen = new TrainingPdfGenerator(template, outDir, SenderType, ReceiverType);
		pdfgen.SetSenderNameLength(SLength);
		pdfgen.SetReceiverNameLength(RLength);
		begin = amount * (batch - 1);
		end = amount * batch;
		pdfName = outDir;
	}

	public void run() {
		for (int i = begin; i < end; ++i) {
			System.out.println(i);
			String name = pdfName + Integer.toString(i);
			pdfgen.setLineIndex(i);
			pdfgen.setPdfName(name);
			pdfgen.generateData();
		}
		pdfgen.ClearAux();

	}

}
