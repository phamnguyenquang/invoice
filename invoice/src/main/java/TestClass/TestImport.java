package TestClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.graph.ComputationGraph;
import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.preprocessing.text.KerasTokenizer;
import org.deeplearning4j.nn.modelimport.keras.preprocessing.text.TokenizerMode;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;

public class TestImport {

	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private List<String> train;
	private List<String> test;

	public TestImport() {

		train = new ArrayList<String>();
		test = new ArrayList<String>();
		doWork();
	}

	private void doWork() {
		try {
			String pythonScript = new ClassPathResource("/resources/python/exported/model_config.json").getPath();
			String pythonH5 = new ClassPathResource("/resources/python/exported/model_weights.h5").getPath();
			String kerasToken = new ClassPathResource("/resources/python/exported/tokenizer.json").getPath();
			String trainS = new ClassPathResource("/resources/coreNLP/data/temp/unlabelled_invoices.txt").getPath();
			String trainR = new ClassPathResource("/resources/dataSet/train/receiver.txt").getPath();
			String testS = new ClassPathResource("/resources/dataSet/train/sender1.txt").getPath();
			String testR = new ClassPathResource("/resources/dataSet/train/receiver1.txt").getPath();

			fileReader = new FileReader(trainS);
			bufferedReader = new BufferedReader(fileReader);
			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				train.add(line);
			}
			bufferedReader.close();
			String[] listTrain = new String[train.size()];
			listTrain = train.toArray(listTrain);

			float[][] label = new float[train.size()][2];
			for (int i = 0; i < train.size(); ++i) {
				label[i][0] = 0;
				label[i][1] = 0;
			}

//			MultiLayerConfiguration model = KerasModelImport.importKerasSequentialConfiguration(pythonScript);

			KerasTokenizer tokenizer = KerasTokenizer.fromJson(kerasToken);
			System.out.println("before: " + tokenizer.getNumWords());
//			tokenizer.fitOnTexts(listTrain);
			
			int k = tokenizer.getWordIndex().size();
			System.out.println("vocab size = "+k);

			Integer[][] test = tokenizer.textsToSequences(listTrain);

			System.out.println("row: "+test.length);

			for (int i = 0; i < test[0].length; ++i) {
				System.out.println(test[0][i]);
			}

			float[][] featureData = new float[test.length][100]; // 500 is file length, 100 is max padding length

			for (int i = 0; i < test.length; ++i) {
				for (int j = 0; j < 100; ++j) {
//					System.out.println(j);
					if (j < test[i].length) {
						featureData[i][j] = test[i][j];
					} else {
						featureData[i][j] = 0;
					}
				}
			}
//
//			INDArray Feature = tokenizer.textsToMatrix(listTrain, TokenizerMode.TFIDF).transpose();
//			float dat[][] = Feature.toFloatMatrix();
//			INDArray lab = Nd4j.create(label);
//			System.out.println(Feature.rows());
//			System.out.println(Feature.columns());
			
			INDArray Feature1 = Nd4j.create(featureData);
			System.out.println("ndarray row: "+Feature1.rows());
			System.out.println("ndarray col: "+Feature1.columns());
			

//			DataSet newData = new DataSet(Feature, lab);
//
			MultiLayerNetwork net = KerasModelImport.importKerasSequentialModelAndWeights(pythonScript,pythonH5);
			
			net.init();
			System.out.println("done init");	
//			net.fit(newData);

			INDArray testOutput = net.output(Feature1);
			
			System.out.println(testOutput.rows());
			System.out.println(testOutput.columns());
			
			for(int i=0;i<testOutput.rows();++i)
			{
				System.out.println(testOutput.getRow(i).toFloatVector()[0]);
			}
//			
			System.out.println("attempt predict");
			
			int p[] = net.predict(Feature1);
//			INDArray[] p=modelTest.output(Feature1);
//			
			System.out.println("file length: "+p.length);

			for (int i = 0; i < p.length; ++i) {
				System.out.println(p[i]);
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
