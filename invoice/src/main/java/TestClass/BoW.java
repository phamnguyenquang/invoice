package TestClass;

import java.io.File;

import org.apache.spark.ml.feature.CountVectorizer;
import org.nd4j.linalg.io.ClassPathResource;
import org.deeplearning4j.bagofwords.vectorizer.BagOfWordsVectorizer;
import org.deeplearning4j.bagofwords.vectorizer.BaseTextVectorizer;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

@SuppressWarnings("deprecation")
public class BoW {

	private String directory;
	private BagOfWordsVectorizer bow;
	private LabelsSource test;

	public BoW(String dir) {

		test = new LabelsSource();
		test.storeLabel("sender");
		test.storeLabel("receiver");
		directory = dir;
		dowork();
	}

	private void dowork() {
		try {
			String test = "This is a test string for bag of word";
			String baseDir = new ClassPathResource(directory).getPath();

			TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
			tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

			SentenceIterator iterator = new BasicLineIterator(new File(baseDir));

			bow = new BagOfWordsVectorizer.Builder().setMinWordFrequency(10).setTokenizerFactory(tokenizerFactory)
					.setIterator(iterator).build();
			bow.fit();

			System.out.println("done fitting");
			System.out.println("transform");

//			
//			long i = bow.numWordsEncountered();
//			System.out.println(i);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public INDArray getFeature(String sentence) {
		return bow.transform(sentence);
	}
}
