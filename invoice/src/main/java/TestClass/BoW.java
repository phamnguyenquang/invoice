package TestClass;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.apache.spark.ml.feature.CountVectorizer;
import org.nd4j.linalg.io.ClassPathResource;
import org.deeplearning4j.bagofwords.vectorizer.BagOfWordsVectorizer;
import org.deeplearning4j.bagofwords.vectorizer.BaseTextVectorizer;
import org.deeplearning4j.text.documentiterator.LabelsSource;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.Tokenizer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;

@SuppressWarnings("deprecation")
public class BoW {

	private String directory;
	private BagOfWordsVectorizer bow;
	private int maxlength = 15;
	private boolean writeOpen = false;
	private BufferedWriter writer;
	private BufferedReader reader;

	public BoW(String dir) {
		directory = dir;
		dowork();
	}

	private void dowork() {
		try {
			String test = "sender 0 [AddressSend 0][AddressMisc 0][City 0][72000][012457850][email@sender0.example].";
			String baseDir = new ClassPathResource(directory).getPath();

			System.out.println(baseDir);

			TokenizerFactory tokenizerFactory = new DefaultTokenizerFactory();
			tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

			SentenceIterator iterator = new BasicLineIterator(new File(baseDir));

			bow = new BagOfWordsVectorizer.Builder().setTokenizerFactory(tokenizerFactory).setIterator(iterator)
					.build();
			bow.fit();
			System.out.println(bow.getVocabCache().containsWord("Bill"));
			INDArray testArr = bow.transform(test);
			System.out.println("column="+testArr.columns());
			System.out.println("row="+testArr.rows());
//			INDArray padded = Nd4j.pad(testArr, new int[] {0,2},Nd4j.PadMode.CONSTANT);
//			float[] testF = testArr.toFloatVector();
//			for (int i = 0; i < testF.length; ++i) {
//				System.out.println(testF[i]);
//			}
//			testArr = Nd4j.create(testF);
//			System.out.println(testArr.columns());
//			System.out.println(testArr.rows());
//			System.out.println("-----------------------------------------");
//			for (int i = 0; i < testArr.columns(); ++i) {
//				System.out.println(testArr.getColumn(i).toString());
//			}
//			System.out.println(padded.columns());
//			System.out.println(padded.rows());
//			System.out.println("-----------------------------------------");
//			for (int i = 0; i < padded.columns(); ++i) {
//				System.out.println(padded.getColumn(i).toString());
//			}

//			
//			long i = bow.numWordsEncountered();
//			System.out.println(i);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void WriteToCSV(String train, String dest, String label) {
		try {
			String s_source = new ClassPathResource(train).getPath();
			String s_dest = new ClassPathResource(dest).getPath();

			reader = new BufferedReader(new FileReader(new File(s_source)));
			if (!writeOpen) {
				writer = new BufferedWriter(new FileWriter(s_dest));
				writeOpen = true;
			}
			String line = null;
			INDArray tempArray;
			float[] tempFloat;
			String tempString = "";
			while ((line = reader.readLine()) != null) {
				tempArray = bow.transform(line);
				int i = tempArray.columns();
				int padLength = maxlength - i;
				tempFloat = tempArray.toFloatVector();
				tempString = Arrays.toString(tempFloat);
				tempString = tempString.replaceAll("\\[", "").replaceAll("\\]", "");
				writer.append(tempString);
//				for (int j = 0; j < padLength; ++j) {
//					writer.append(",0");
//				}
				writer.append(", " + label);
				writer.append("\n");
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void FinishTrainingWrite() throws IOException {
		writer.close();
	}

	public void WriteToTestCSV(String test, String dest) {
		try {
			String s_source = new ClassPathResource(test).getPath();
			String s_dest = new ClassPathResource(dest).getPath();

			BufferedReader reader1 = new BufferedReader(new FileReader(new File(s_source)));
			BufferedWriter writer1 = new BufferedWriter(new FileWriter(s_dest));
			String line = null;
			INDArray tempArray;
			float[] tempFloat;
			String tempString = "";
			while ((line = reader1.readLine()) != null) {
				tempArray = bow.transform(line);
				int i = tempArray.columns();
				int padLength = maxlength - i;
				tempFloat = tempArray.toFloatVector();
				tempString = Arrays.toString(tempFloat);
				tempString = tempString.replaceAll("\\[", "").replaceAll("\\]", "");
				writer1.append(tempString);
//				for (int j = 0; j < padLength; ++j) {
//					writer1.append(",0");
//				}
				writer1.append("\n");
			}

			writer1.close();
			reader1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public int getArraySize()
	{
		return bow.getVocabCache().numWords();
	}

	public INDArray getFeature(String sentence) {
		return bow.transform(sentence);
	}
}
