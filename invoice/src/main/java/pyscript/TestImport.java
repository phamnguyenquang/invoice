package pyscript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
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
			String pythonH5 = new ClassPathResource("/resources/python/exported/model.h5").getPath();
			String kerasToken = new ClassPathResource("/resources/python/exported/tokenizer.json").getPath();
			String trainS = new ClassPathResource("/resources/dataSet/train/sender.txt").getPath();
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
			tokenizer.fitOnTexts(listTrain);

			INDArray Feature = tokenizer.textsToMatrix(listTrain, TokenizerMode.COUNT);
			INDArray lab = Nd4j.create(label);

			DataSet newData = new DataSet(Feature, lab);

			MultiLayerNetwork net = KerasModelImport.importKerasSequentialModelAndWeights(pythonH5, false);
			net.init();

//			net.fit(newData);

			INDArray testOutput = net.output(Feature);

		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}
}
