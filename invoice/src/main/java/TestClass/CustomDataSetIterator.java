package TestClass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.deeplearning4j.bagofwords.vectorizer.BagOfWordsVectorizer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.io.ClassPathResource;

public class CustomDataSetIterator implements DataSetIterator {

	private WordVectors wordVectors;
	private int batchSize;
	private int vectorSize = 10;
	private int truncateLength;
	private BoW BoWVectorizer;

	private File sender;
	private File receiver;

	private int cursor = 0;
	private TokenizerFactory tokenizerFactory;

	private List<String> senderData;
	private List<String> receiverData;

	public CustomDataSetIterator(String dataDirectory, int batchSize, int truncateLength, int type) {
		try {
			this.batchSize = batchSize;

			if (type == 0) {
				sender = new File(dataDirectory + "/train/sender.txt");
				receiver = new File(dataDirectory + "/train/receiver.txt");
			} else if (type == 1) {
				sender = new File(dataDirectory + "/test/sender.txt");
				receiver = new File(dataDirectory + "/test/receiver.txt");
			} else if (type == 2) {
				sender = new File(dataDirectory + "/predict/unlabelled_invoices.txt");
				receiver = new File(dataDirectory + "/predict/receiver.txt");
			} else {
				System.out.println("option not exist");
			}

//			sender = new File(FilenameUtils.concat(dataDirectory, (train ? "train" : "test") + "/sender/") + "/");
//			receiver = new File(FilenameUtils.concat(dataDirectory, (train ? "train" : "test") + "/receiver/") + "/");
//			sender = s.listFiles();
//			receiver = r.listFiles();

			this.truncateLength = truncateLength;

			tokenizerFactory = new DefaultTokenizerFactory();
			tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

			BoWVectorizer = new BoW("/resources/text/JavaDataGen.txt");

			InitializeData();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void InitializeData() {
		try {
			senderData = new ArrayList<String>();
			receiverData = new ArrayList<String>();
			BufferedReader sReader = new BufferedReader(new FileReader(sender));
			BufferedReader rReader = new BufferedReader(new FileReader(receiver));
			String line = null;
			while ((line = sReader.readLine()) != null) {
				senderData.add(line);
			}
			line = null;
			while ((line = rReader.readLine()) != null) {
				receiverData.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//------------------------------------------------------------------------------------------------------------------
//Private Methods go here
//------------------------------------------------------------------------------------------------------------------

	private DataSet nextDataSet(int num) throws IOException {
		// First: load reviews to String. Alternate positive and negative reviews
		List<String> reviews = new ArrayList<String>(num);
		boolean[] positive = new boolean[num];
		for (int i = 0; i < num && cursor < senderData.size() + receiverData.size(); i++) {
			if (cursor % 2 == 0) {
				// Load positive review
				int senderPos = cursor / 2;
				String review = senderData.get(senderPos);
				reviews.add(review);
				positive[i] = true;
			} else {
				// Load negative review
				int receiverPos = cursor / 2;
				String review = receiverData.get(receiverPos);
				reviews.add(review);
				positive[i] = false;
			}
			cursor++;
		}
		// Second: tokenize reviews and filter out unknown words
		int maxLength = 10;

		// Create data for training
		// Here: we have reviews.size() examples of varying lengths
		INDArray features = Nd4j.create(new int[] { reviews.size(), 10 }, 'f');

		INDArray labels = Nd4j.create(new int[] { reviews.size(), 2 }, 'f'); // Two labels: positive or
																				// negative
		// Because we are dealing with reviews of different lengths and only one output
		// at the final time step: use padding arrays
		// Mask arrays contain 1 if data is present at that time step for that example,
		// or 0 if data is just padding
		INDArray featuresMask = Nd4j.zeros(reviews.size(), maxLength);
		INDArray labelsMask = Nd4j.zeros(reviews.size(), maxLength);

		for (int i = 0; i < reviews.size(); i++) {
			List<String> tokens = tokenizerFactory.create(reviews.get(i)).getTokens();

			// Get the truncated sequence length of document (i)
			int seqLength = Math.min(tokens.size(), maxLength);

			// Get all wordvectors for the current document and transpose them to fit the
			// 2nd and 3rd feature shape
			final INDArray vectors = BoWVectorizer.getFeature(reviews.get(i)).transpose();

			// Put wordvectors into features array at the following indices:
			// 1) Document (i)
			// 2) All vector elements which is equal to NDArrayIndex.interval(0, vectorSize)
			// 3) All elements between 0 and the length of the current sequence
			features.put(new INDArrayIndex[] { NDArrayIndex.point(i), NDArrayIndex.interval(0, 10) }, vectors);

			// Assign "1" to each position where a feature is present, that is, in the
			// interval of [0, seqLength)
			featuresMask.get(new INDArrayIndex[] { NDArrayIndex.point(i), NDArrayIndex.interval(0, seqLength) })
					.assign(1);

			int idx = (positive[i] ? 0 : 1);
			int lastIdx = Math.min(tokens.size(), maxLength);
			labels.putScalar(new int[] { i, idx }, 1.0); // Set label: [0,1] for negative, [1,0] for
															// positive
			labelsMask.putScalar(new int[] { i, lastIdx - 1 }, 1.0); // Specify that an output exists at the final time
																		// step for this example
		}

		System.out.println("Feature row " + features.rows());
		System.out.println("Feature col " + features.columns());
		System.out.println("label row " + labelsMask.rows());
		System.out.println("label row " + labelsMask.columns());

		return new DataSet(features, labelsMask);

	}
//------------------------------------------------------------------------------------------------------------------	
//------------------------------------------------------------------------------------------------------------------
//Public and Inherited Methods go here
//------------------------------------------------------------------------------------------------------------------

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return cursor < (senderData.size() + receiverData.size());
	}

	public DataSet next() {
		// TODO Auto-generated method stub
		return next(batchSize);
	}

	public DataSet next(int num) {
		if (cursor >= senderData.size() + receiverData.size())
			throw new NoSuchElementException();
		try {
			return nextDataSet(num);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public int inputColumns() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int totalOutcomes() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean resetSupported() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean asyncSupported() {
		// TODO Auto-generated method stub
		return true;
	}

	public void reset() {
		cursor = 0;

	}

	public int batch() {
		// TODO Auto-generated method stub
		return batchSize;
	}

	public void setPreProcessor(DataSetPreProcessor preProcessor) {
		throw new UnsupportedOperationException();

	}

	public DataSetPreProcessor getPreProcessor() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getLabels() {
		// TODO Auto-generated method stub
		return null;
	}

}
