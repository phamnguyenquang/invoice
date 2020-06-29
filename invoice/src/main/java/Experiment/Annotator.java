package Experiment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.nd4j.linalg.io.ClassPathResource;

import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreNLPProtos;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Annotator {
	private String fileName;
	private String entityOutput;
	private String DateTime;
	private String otherOutput;
	private String cash;
	private String dataDir = "/resources/coreNLP/data/original/";
	private String outPutDIr = "/resources/coreNLP/data/processed/";
	// -------------------------------------------------------------
	// CoreNLP
	private Properties props;
	private StanfordCoreNLP pipeline;
	private String algorithm;
	private List<String> nerTags;
	// -------------------------------------------------------------
	private boolean name;
	private boolean organization;
	private boolean location;
	private boolean date;
	private boolean money;
	// -------------------------------------------------------------
	private BufferedWriter debugLog;
	private BufferedWriter trackerLog;

	public Annotator(String input, String algo) {
		try {
			// Setting up data Path
			algorithm = algo;
			setDataFile(input);
			setEntityOutput("recipient.txt");
			setOtherOutput("information.txt");
			setDateOutput("date.txt");
			setMoneyOutput("money.txt");

			// Loading the NER - Organization and name model
			props = new Properties();
			// set the list of annotators to run
			props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref");

			// ------------------------------------------------------------------------------------------------
			// properties, can be used to set custom Model
			// Load some valid pretrained model here
//			String fileModel = new ClassPathResource("resources/coreNLP/CustomAnnotator/eunews.de.crf.gz").getPath();
//			props.setProperty("ner.model", fileModel);
			// ------------------------------------------------------------------------------------------------
//			props.load(IOUtils.readerFromString("StanfordCoreNLP-german.properties"));
			// set a property for an annotator, in this case the coref annotator is being
			// set to use the neural algorithm
			props.setProperty("coref.algorithm", algorithm);
			// build pipeline
			pipeline = new StanfordCoreNLP(props);
			String trackdb = new ClassPathResource(outPutDIr + "logDebug.txt").getPath();
			debugLog = new BufferedWriter(new FileWriter(trackdb));
			String track = new ClassPathResource(outPutDIr + "log.txt").getPath();
			trackerLog = new BufferedWriter(new FileWriter(track));
			// create a document object
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setAlgorithm(String algo) {
		algorithm = algo;
		props.setProperty("coref.algorithm", algorithm);

	}

	public void setDataFile(String name) {
		fileName = dataDir + name;

	}

	public void setEntityOutput(String name) {
		entityOutput = outPutDIr + name;
	}

	public void setMoneyOutput(String name) {
		cash = outPutDIr + name;
	}

	public void setOtherOutput(String name) {
		otherOutput = outPutDIr + name;
	}

	public void setDateOutput(String name) {
		DateTime = outPutDIr + name;
	}

	public void Annotate(String s) {
		try {
			String processedSentence = s.replaceAll("[^€@a-zA-Z0-9\\s+]", " ");
			name = false;
			organization = false;
			location = false;
			date = false;
			money = false;
			CoreDocument document = new CoreDocument(processedSentence);
			// annnotate the document
			pipeline.annotate(document);
			CoreSentence sentence = document.sentences().get(0);
			debugLog.append(s);
			debugLog.append("\n");
			nerTags = sentence.nerTags();
			for (int i = 0; i < nerTags.size(); ++i) {
				if (i > 0) {
					debugLog.append(",");
					trackerLog.append(",");
				}
				trackerLog.append(nerTags.get(i));
				debugLog.append(nerTags.get(i));
				if (nerTags.get(i).toLowerCase().contains("person")) {
					name = true;
				}
				if (nerTags.get(i).toLowerCase().contains("organization")) {
					organization = true;
				}
				if (nerTags.get(i).toLowerCase().contains("money")) {
					money = true;
				}
				if (nerTags.get(i).toLowerCase().contains("date")) {
					date = true;
				}
				if (nerTags.get(i).toLowerCase().contains("city") || nerTags.get(i).toLowerCase().contains("country")
						|| nerTags.get(i).toLowerCase().contains("state")
						|| nerTags.get(i).toLowerCase().contains("province")) {
					location = true;
				}
			}
			debugLog.append("\n");
			debugLog.append("\n");
			trackerLog.append("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doWork() {
		doTask();
	}

	private void doTask() {
		try {
			List<String> entity = new ArrayList<String>();
			List<String> other = new ArrayList<String>();
			List<String> d_date = new ArrayList<String>();
			List<String> d_money = new ArrayList<String>();
			String input = new ClassPathResource(fileName).getPath();
			String entityOut = new ClassPathResource(entityOutput).getPath();
			String otherOut = new ClassPathResource(otherOutput).getPath();
			String DateOut = new ClassPathResource(DateTime).getPath();
			String Money = new ClassPathResource(cash).getPath();
			BufferedReader reader = new BufferedReader(new FileReader(new File(input)));
			String line = null;
			int j = 0;
			while ((line = reader.readLine()) != null) {
				name = false;
				organization = false;
				location = false;
				System.out.println("iteration: " + j);
				Annotate(line);
				if (name && location) {
					System.out.println("entity");
					entity.add(line);
				}
				if (money) {
					System.out.println("money");
					d_money.add(line);
				} else if (organization && location) {
					System.out.println("entity");
					entity.add(line);
				} else if (date) {
					System.out.println("date");
					d_date.add(line);
				} else {
					System.out.println("other");
					other.add(line);
				}
				++j;
			}
			reader.close();

			BufferedWriter entityWriter = new BufferedWriter(new FileWriter(entityOut));
			BufferedWriter otherWriter = new BufferedWriter(new FileWriter(otherOut));
			BufferedWriter DateWriter = new BufferedWriter(new FileWriter(DateOut));
			BufferedWriter MoneyWriter = new BufferedWriter(new FileWriter(Money));
			System.out.println("entity size: " + entity.size());
			System.out.println("other size: " + other.size());
			System.out.println("date size: " + d_date.size());
			for (int i = 0; i < entity.size(); ++i) {
				entityWriter.append(entity.get(i));
				entityWriter.append("\n");
			}
			for (int i = 0; i < other.size(); ++i) {
				otherWriter.append(other.get(i));
				otherWriter.append("\n");
			}
			for (int i = 0; i < d_date.size(); ++i) {
				DateWriter.append(d_date.get(i));
				DateWriter.append("\n");
			}
			for (int i = 0; i < d_money.size(); ++i) {
				MoneyWriter.append(d_money.get(i));
				MoneyWriter.append("\n");
			}
			entityWriter.close();
			otherWriter.close();
			DateWriter.close();
			MoneyWriter.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				debugLog.close();
				trackerLog.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
