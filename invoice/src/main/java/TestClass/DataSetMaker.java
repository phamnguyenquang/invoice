package TestClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;

public class DataSetMaker {

	private DataSet data;
	private int len;
	private int type;
	// DataSet Initialization
	// ---------------------------
	private INDArray features;
	private INDArray label;
	private INDArray featureMask;
	private INDArray labelMask;
	// ---------------------------
	private List<String> sentences;
	// ---------------------------
	private BoW BoWVectorizer;
	private TokenizerFactory tokenizerFactory;
	private String path;
	private List<String>labelnames;

	public DataSetMaker(String filePath) {
		try {
			path = filePath;
			sentences = new ArrayList<String>();
			len = 0;
			labelnames = new ArrayList<String>();
			labelnames.add("name");
			labelnames.add("addr1");
			labelnames.add("addr2");
			labelnames.add("addr3");
			labelnames.add("addr4");
			labelnames.add("state");
			labelnames.add("zip");
			labelnames.add("phone");
			labelnames.add("mail");
			labelnames.add("city");

			BoWVectorizer = new BoW("/resources/text/JavaDataGen.txt");
			tokenizerFactory = new DefaultTokenizerFactory();
			tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
			makeDataSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void makeDataSet() {
		try {

			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sentences.add(line);
			}
			for (int i = 0; i < sentences.size(); ++i) {
				if (len < sentences.get(i).length()) {
					len = sentences.get(i).length();
				}
			}
			features = Nd4j.create(new int[] { sentences.size(), 10}, 'f');
			label = Nd4j.zeros(sentences.size(), 2);

			for (int i = 0; i < sentences.size(); ++i) {
				List<String> tokens = tokenizerFactory.create(sentences.get(i)).getTokens();
				INDArray vectors = BoWVectorizer.getFeature(sentences.get(i)).transpose();
				features.put(new INDArrayIndex[] { NDArrayIndex.point(i), NDArrayIndex.interval(0, 10) }, vectors);
			}
			data = new DataSet(features,label);
			data.setLabelNames(labelnames);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataSet getDataSet() {
		return data;
	}
}
