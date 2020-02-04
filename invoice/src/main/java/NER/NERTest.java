package NER;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class NERTest {

	public NERTest() {
		try {
			dowork();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dowork() throws Exception {
		// Loading the NER - Person model
		InputStream inputStreamNameFinder = new FileInputStream("resources/coreNLP/en-ner-organization.bin");
		// Loading the tokenizer model
		InputStream inputStreamTokenizer = new FileInputStream("resources/coreNLP/en-token.bin");
		TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);

		// Instantiating the TokenizerME class
		TokenizerME tokenizer = new TokenizerME(tokenModel);

		// Tokenizing the sentence in to a string array
//		String sentence = "Mike is senior programming manager and John is a clerk both are working at Tutorialspoint";
		String sentence = "Volkswagen car manufacturer is located in Frankfurt, Germany";
		String tokens[] = tokenizer.tokenize(sentence);

		// Loading the NER-person model

		TokenNameFinderModel model = new TokenNameFinderModel(inputStreamNameFinder);

		// Instantiating the NameFinderME class
		NameFinderME nameFinder = new NameFinderME(model);

		// Finding the names in the sentence
		Span nameSpans[] = nameFinder.find(tokens);

		// Printing the names and their spans in a sentence
		for (Span s : nameSpans)
			System.out.println(s.toString() + "  " + tokens[s.getStart()]);
	}
}