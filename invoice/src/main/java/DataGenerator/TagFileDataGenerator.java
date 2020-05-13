package DataGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.ThreadLocalRandom;

import org.nd4j.linalg.io.ClassPathResource;

public class TagFileDataGenerator {
	private DictionaryReader name;
	private File file;
	private BufferedWriter writer;
	private int data = 500;

	private void addName() {
		try {
//			writer.append(name.getRandomGivenName());
//			writer.append(" ");
			writer.append(name.getRandomSureName());
			writer.append(" ");
			writer.append(name.getRandomSureName());
			writer.append(" ");
//			writer.append(name.getRandomSureName());
//			writer.append(" ");
			writer.append("Corp");
			writer.append(" ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addCompany() {
		try {
			writer.append(name.getRandomCompanyName());
			writer.append(" ");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addStreet() {
		try {
			int i = ThreadLocalRandom.current().nextInt(0, 101);
			writer.append("Scheidswald Str");
			writer.append(" ");
			writer.append(Integer.toString(i));
			writer.append(" ");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addLocation() {
		try {
			int i = ThreadLocalRandom.current().nextInt(100000, 999999);
			writer.append(Integer.toString(i));
			writer.append(" ");
			writer.append(name.getRandomCity());
			writer.append(" ");
			writer.append(name.getRandomStatte());
			writer.append(" ");
			writer.append(name.getRandomCountry());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addCurrency(String type) {
		try {
			int i = ThreadLocalRandom.current().nextInt(1, 100000);
			writer.append(type);
			writer.append(" ");
			writer.append("Money");
			;
			writer.append(Integer.toString(i));
			writer.append(" ");
			writer.append("Money");
			;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public TagFileDataGenerator(String filePath) {
		String absPath = new ClassPathResource(filePath).getPath();
		file = new File(absPath);
		name = new DictionaryReader("resources/Dictionary/");
	}

	public void setDataLength(int i) {
		data = i;
	}

	public void doWork() {
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < data; ++i) {
				addName();
//				addCompany();
				addStreet();
				addLocation();
				writer.append("\n");
			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
