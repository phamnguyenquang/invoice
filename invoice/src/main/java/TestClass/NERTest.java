package TestClass;

import java.io.FileInputStream;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class NERTest {
	public NERTest() {
		doWork();
	}

	private void doWork() {
		try {
			InputStream inputStreamorganizationFinder = new FileInputStream(
					"resources/coreNLP/pretrained/en-ner-organization.bin");
			InputStream inputStreamNameFinder = new FileInputStream("resources/coreNLP/pretrained/en-ner-person.bin");
			InputStream inputStreamTokenizer = new FileInputStream("resources/coreNLP/pretrained/en-token.bin");
			TokenizerModel tokenModel = new TokenizerModel(inputStreamTokenizer);
			TokenizerME tokenizer = new TokenizerME(tokenModel);
			TokenNameFinderModel nameModel = new TokenNameFinderModel(inputStreamNameFinder);
			TokenNameFinderModel oranizationModel = new TokenNameFinderModel(inputStreamorganizationFinder);
			NameFinderME nameFinder = new NameFinderME(nameModel);
			NameFinderME organizationFinder = new NameFinderME(oranizationModel);

			String sentence = "Total 2800.0";
			
			String tokens[] = tokenizer.tokenize(sentence);
			Span nameSpans[] = nameFinder.find(tokens);
			Span organizationSpan[] = organizationFinder.find(tokens);
			for (Span s : nameSpans) {
				System.out.println(s.toString());
			}
			for (Span s : organizationSpan) {
				System.out.println(s.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
