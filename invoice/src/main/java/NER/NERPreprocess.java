package NER;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.nd4j.linalg.io.ClassPathResource;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class NERPreprocess {

	private String fileName;
	private String entityOutput;
	private String otherOutput;
	private String dataDir = "/resources/coreNLP/data/original/";
	private String outPutDIr = "/resources/coreNLP/data/temp/";
	private InputStream inputStreamNameFinder;
	private InputStream inputStreamorganizationFinder;
	private InputStream inputStreamTokenizer;
	private TokenizerME tokenizer;
	private TokenizerModel tokenModel;
	private TokenNameFinderModel oranizationModel;
	private TokenNameFinderModel nameModel;
	private NameFinderME nameFinder;
	private NameFinderME organizationFinder;

	public NERPreprocess(String input, String entity, String other) {
		try {
			// Setting up data Path
			setDataFile(input);
			setEntityOutput(entity);
			setOtherOutput(other);
			// Loading the NER - Organization and name model
			inputStreamorganizationFinder = new FileInputStream("resources/coreNLP/pretrained/en-ner-organization.bin");
			inputStreamNameFinder = new FileInputStream("resources/coreNLP/pretrained/en-ner-person.bin");
			// Loading the tokenizer model
			inputStreamTokenizer = new FileInputStream("resources/coreNLP/pretrained/en-token.bin");
			tokenModel = new TokenizerModel(inputStreamTokenizer);

			// Instantiating the TokenizerME class
			tokenizer = new TokenizerME(tokenModel);
			nameModel = new TokenNameFinderModel(inputStreamNameFinder);
			oranizationModel = new TokenNameFinderModel(inputStreamorganizationFinder);
			nameFinder = new NameFinderME(nameModel);
			organizationFinder = new NameFinderME(oranizationModel);
		} catch (Exception e) {
			e.printStackTrace();
		}

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

	public void doWork() {
		doWTask();
	}

	private void doWTask() {
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
				System.out.println("iteration: " + j);
				if (containEntity(line)) {
					entity.add(line);
				} else {
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

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean containEntity(String sentence) {
		boolean result = false;
		try {
			String tokens[] = tokenizer.tokenize(sentence);
			Span nameSpans[] = nameFinder.find(tokens);
			Span organizationSpan[] = organizationFinder.find(tokens);
			for (Span s : nameSpans) {
				System.out.println(s.toString());
				if (s.toString().contains("person")) {
					result = true;
				}
			}
			for (Span s : organizationSpan) {
				System.out.println(s.toString());
				if (s.toString().contains("organization")) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

}
