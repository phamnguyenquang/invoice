package pyscript;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.deeplearning4j.nn.modelimport.keras.KerasModelImport;
import org.deeplearning4j.nn.modelimport.keras.preprocessing.text.KerasTokenizer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.io.ClassPathResource;

public class PythonModel {
	// -------------------------
	// Things that made model
	private String h5File;
	private String modelJson;
	private String tokenName;
	// -------------------------
	// some base dir
	private String baseDir;
	private String fullPathToTokenFile;
	private String fullPathToh5;
	private KerasTokenizer tokenizer;
	private MultiLayerNetwork model;
	private String[] data;
	private int PaddingLength;
	private int p[] = null;

	private void setBaseDir(String dir) {
		baseDir = new ClassPathResource(dir).getPath();

	}

	private String[] readFile(String filePath) throws IOException {
		FileReader fileReader = new FileReader(filePath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line = null;
		List<String> sentences = new ArrayList<String>();

		while ((line = bufferedReader.readLine()) != null) {
			sentences.add(line);
		}
		bufferedReader.close();
		String[] listTrain = new String[sentences.size()];
		listTrain = sentences.toArray(listTrain);
		return listTrain;
	}

	private float[][] TokenizeData(String[] data) {
		Integer[][] test = tokenizer.textsToSequences(data);
		float[][] featureData = new float[data.length][PaddingLength]; // 500 is file length, 100 is max padding length

		for (int i = 0; i < data.length; ++i) {
			for (int j = 0; j < PaddingLength; ++j) {
				if (j < test[i].length) {
					featureData[i][j] = test[i][j];
				} else {
					featureData[i][j] = 0;
				}
			}
		}
		return featureData;
	}

	public PythonModel(String token, String parameter, int pad) {
		try {
			tokenName = token;
			h5File = parameter;
			setBaseDir("resources/python/exported/");
			fullPathToTokenFile = new ClassPathResource(baseDir + tokenName).getPath();
			fullPathToh5 = new ClassPathResource(baseDir + h5File).getPath();
			tokenizer = KerasTokenizer.fromJson(fullPathToTokenFile);
			PaddingLength = pad;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int[] GetPredictResult(String filepath) {
		try {
			String[] list = readFile(filepath);
			INDArray Feature = Nd4j.create(TokenizeData(list));
			model = KerasModelImport.importKerasSequentialModelAndWeights(fullPathToh5, false);
			model.init();
			p = model.predict(Feature);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public void WriteResultToFile() {
		try {
			String outDir = new ClassPathResource("/resources/coreNLP/data/temp/").getPath();
			BufferedWriter writer = new BufferedWriter(new FileWriter(outDir + "labelled_invoices.txt"));
			for (int i = 0; i < p.length; ++i) {
				writer.append(String.valueOf(p[i]));
				writer.append("\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
