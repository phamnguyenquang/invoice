package TestClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerStandardize;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class CSVTest {
	private BoW bow;
	private int labelIndex;
	private int noOfClasses;
	private int batchSize;

	private String pathToCSV;
	private File file;

	public CSVTest(int size) {
		doWork();
		labelIndex = size;
	}

	private void doWork() {
		try {
			pathToCSV = new ClassPathResource("/resources/dataSet/temp/sender.txt").getPath();
			RecordReader recordReader = new CSVRecordReader(0, ',');
			recordReader.initialize(new FileSplit(new File(pathToCSV)));
			noOfClasses = 2;
			batchSize = 100;
			DataSetIterator iterator = new RecordReaderDataSetIterator(recordReader, batchSize, labelIndex,
					noOfClasses);
			DataSet allData = iterator.next();
			allData.shuffle();
			SplitTestAndTrain testAndTrain = allData.splitTestAndTrain(0.65); // Use 65% of data for training

			DataSet trainingData = testAndTrain.getTrain();
			DataSet testData = testAndTrain.getTest();

			DataNormalization normalizer = new NormalizerStandardize();
			normalizer.fit(trainingData); // Collect the statistics (mean/stdev) from the training data. This does not
											// modify the input data
			normalizer.transform(trainingData); // Apply normalization to the training data
			normalizer.transform(testData);

			final int numInputs = labelIndex;
			int outputNum = 2;
			long seed = 6;

			MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(seed)
					.activation(Activation.SIGMOID).weightInit(WeightInit.XAVIER).updater(new Sgd(0.1)).l2(1e-4).list()
					.layer(new DenseLayer.Builder().nIn(numInputs).nOut(2).build())
					.layer(new DenseLayer.Builder().nIn(2).nOut(2).build())
					.layer(new OutputLayer.Builder(LossFunctions.LossFunction.XENT).activation(Activation.SIGMOID)
							.nIn(2).nOut(2).build())
					.build();

			// run the model
			MultiLayerNetwork model = new MultiLayerNetwork(conf);
			model.init();
			model.setListeners(new ScoreIterationListener(100));

			model.fit(trainingData);

			String pathToTest = new ClassPathResource("/resources/dataSet/temp/fit.txt").getPath();
			RecordReader fitReader = new CSVRecordReader(0, ',');
			fitReader.initialize(new FileSplit(new File(pathToTest)));

			DataSetIterator fitIterator = new RecordReaderDataSetIterator(fitReader, batchSize);
			DataSet fitData = fitIterator.next();
			fitData.shuffle();

			List<String> label = new ArrayList<String>();
			label.add("send");
			label.add("receive");
			fitData.setLabelNames(label);

			List<String> result = model.predict(fitData);
			System.out.println(result.size());
			for (int i = 0; i < result.size(); ++i) {
				System.out.println(result.get(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
