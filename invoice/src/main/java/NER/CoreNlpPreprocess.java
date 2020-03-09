package NER;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.nd4j.linalg.io.ClassPathResource;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class CoreNlpPreprocess {
	private String fileName;
	private String entityOutput;
	private String otherOutput;
	private String dataDir = "/resources/coreNLP/data/original/";
	private String outPutDIr = "/resources/coreNLP/data/temp/";
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

	public CoreNlpPreprocess(String input, String entity, String other, String algo) {
		try {
			// Setting up data Path
			algorithm = algo;
			setDataFile(input);
			setEntityOutput(entity);
			setOtherOutput(other);
			// Loading the NER - Organization and name model
			props = new Properties();
			// set the list of annotators to run
			props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,depparse,coref");
			// set a property for an annotator, in this case the coref annotator is being
			// set to use the neural algorithm
			props.setProperty("coref.algorithm", algorithm);
			// build pipeline
			pipeline = new StanfordCoreNLP(props);
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

	public void setOtherOutput(String name) {
		otherOutput = outPutDIr + name;
	}

	public void Annotate(String s) {
		name = false;
		organization = false;
		location = false;
		CoreDocument document = new CoreDocument(s);
		// annnotate the document
		pipeline.annotate(document);
		CoreSentence sentence = document.sentences().get(0);
		nerTags = sentence.nerTags();
		for (int i = 0; i < nerTags.size(); ++i) {
			System.out.println("Tag found: "+nerTags.get(i).toLowerCase());
			if (nerTags.get(i).toLowerCase().contains("person")) {
				name = true;
			}
			if (nerTags.get(i).toLowerCase().contains("organization")) {
				organization = true;
			}
			if (nerTags.get(i).toLowerCase().contains("city") || nerTags.get(i).toLowerCase().contains("country")
					|| nerTags.get(i).toLowerCase().contains("state")
					|| nerTags.get(i).toLowerCase().contains("province")) {
				location = true;
			}
		}
	}

	public void doWork() {
		doTask();
	}

	private void doTask() {
		try {
			List<String> entity = new ArrayList<String>();
			List<String> other = new ArrayList<String>();
			String input = new ClassPathResource(fileName).getPath();
			String entityOut = new ClassPathResource(entityOutput).getPath();
			String otherOut = new ClassPathResource(otherOutput).getPath();
			BufferedReader reader = new BufferedReader(new FileReader(new File(input)));
			String line = null;
			int j = 0;
			while ((line = reader.readLine()) != null) {
				name = false;
				organization = false;
				location = false;
				Annotate(line);
				System.out.println("iteration: " + j);
				if (name && location) {
					System.out.println("entity");
					entity.add(line);
				} else if (organization && location) {
					System.out.println("entity");
					entity.add(line);
				} else {
					System.out.println("other");
					other.add(line);
				}
				++j;
			}
			reader.close();

			BufferedWriter entityWriter = new BufferedWriter(new FileWriter(entityOut));
			BufferedWriter otherWriter = new BufferedWriter(new FileWriter(otherOut));
			for (int i = 0; i < entity.size(); ++i) {
				entityWriter.append(entity.get(i));
				entityWriter.append("\n");
			}
			for (int i = 0; i < other.size(); ++i) {
				otherWriter.append(other.get(i));
				otherWriter.append("\n");
			}
			entityWriter.close();
			otherWriter.close();

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

}
