package ML;

import java.io.File;
import java.util.List;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.Convolution1DLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.EmbeddingLayer;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.InvocationType;
import org.deeplearning4j.optimize.listeners.EvaluativeListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ops.impl.layers.convolution.Conv1D;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.io.ClassPathResource;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Sgd;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import TestClass.CustomDataSetIterator;
import TestClass.DataSetMaker;

public class CNN {

	private String path = new ClassPathResource("/resources/dataSet").getPath();

	public CNN() {
		doWork();
	}

	private void doWork() {
		int batchSize = 64; // Number of examples in each minibatch
		int vectorSize = 300; // Size of the word vectors. 300 in the Google News model
		int nEpochs = 1; // Number of epochs (full passes of training data) to train on
		int truncateReviewsToLength = 256; // Truncate reviews with length (# words) greater than this
		final int seed = 0; // Seed for reproducibility

		MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder().seed(seed).activation(Activation.SIGMOID)
				.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT).weightInit(WeightInit.XAVIER)
				.updater(new Sgd(0.1)).l2(1e-4).list()
				// .layer(new
				// Convolution1DLayer.Builder().nIn(10).nOut(10).kernelSize(5).activation(Activation.RELU).build())
				.layer(new DenseLayer.Builder().nIn(10).nOut(2).activation(Activation.RELU).build())
				.layer(new DenseLayer.Builder().nIn(2).nOut(2).activation(Activation.RELU).build())
				.layer(new OutputLayer.Builder(LossFunctions.LossFunction.RECONSTRUCTION_CROSSENTROPY)
						.activation(Activation.RELU).nIn(2).nOut(2).build())
				.build();

		MultiLayerNetwork net = new MultiLayerNetwork(conf);
		net.init();

		CustomDataSetIterator train = new CustomDataSetIterator(path, batchSize, truncateReviewsToLength, 0);
		CustomDataSetIterator test = new CustomDataSetIterator(path, batchSize, truncateReviewsToLength, 1);

		System.out.println("Starting training");
		net.setListeners(new ScoreIterationListener(1), new EvaluativeListener(test, 1, InvocationType.EPOCH_END));
		net.fit(train, nEpochs);
		System.out.println("done");

		String dataPredict = new ClassPathResource("/resources/dataSet/predict/unlabelled_invoices.txt").getPath();

		DataSetMaker predict = new DataSetMaker(dataPredict);
		DataSet unlabelled = predict.getDataSet();

		System.out.println("test predict");
		List<String> preditcted = net.predict(unlabelled);

		for (int i = 0; i < preditcted.size(); ++i) {
			System.out.println(preditcted.get(i));
		}

		System.out.println("done predict");

	}

}
