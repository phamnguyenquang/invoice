package NER;

import edu.stanford.nlp.ie.crf.CRFClassifier;

public class CustomModelTagging {

	private CRFClassifier model;

	public CustomModelTagging(String path) {
		getModel(path);
	}

	public void getModel(String modelPath) {
		model = CRFClassifier.getClassifierNoExceptions(modelPath);
	}

	public String doTagging(String input) {
		input = input.trim();
		String result = model.classifyToString(input);
		System.out.println(input + "=>" + result);
		return result;
	}
}
