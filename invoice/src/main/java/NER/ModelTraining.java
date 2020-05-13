package NER;

import java.util.Properties;

import org.nd4j.linalg.io.ClassPathResource;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.util.StringUtils;

public class ModelTraining {

	public ModelTraining(String modelOutPath, String prop, String trainingFilePath) {
		try {
			String absolutePath = new ClassPathResource(trainingFilePath).getPath();
			String absoluteOutPath = new ClassPathResource(modelOutPath).getPath();
			String propPath = new ClassPathResource(prop).getPath();
			Properties props = StringUtils.propFileToProperties(propPath);
			props.setProperty("serializeTo", absoluteOutPath);
			if (trainingFilePath != null) {
				props.setProperty("trainFIle", absolutePath);
			}
			SeqClassifierFlags flags = new SeqClassifierFlags(props);
			CRFClassifier<CoreLabel> crf = new CRFClassifier<CoreLabel>(flags);
			System.out.println("training");
			crf.train();
			System.out.println("serializing");
			crf.serializeClassifier(absoluteOutPath);
			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
