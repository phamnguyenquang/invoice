package DataGenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.nd4j.linalg.io.ClassPathResource;

public class DictionaryReader {
	// ------------------------------
	// File
	private String NamePath;
	private String SureNamePath;
	private String OrganizationPath;
	private String CitiesPath;
	private String StatePath;
	private String CountryPath;
	// ------------------------------
	// Reader and file
	private BufferedReader fileReader;
	private File Name;
	private File SureName;
	private File Organization;
	private File Cities;
	private File States;
	private File Countries;

	// ------------------------------
	// counters
	private int snSize;
	private int nSize;
	private int Osize;
	private int Csize;
	private int CitySize;
	private int StateSize;

	public DictionaryReader(String dicDir) {
//		String dicDir = new ClassPathResource(dictDir).getPath();
		NamePath = dicDir + "Name.txt";
		SureNamePath = dicDir + "SureName.txt";
		OrganizationPath = dicDir + "Organization.txt";
		CitiesPath = dicDir + "Cities.txt";
		StatePath = dicDir + "States.txt";
		CountryPath = dicDir + "Countries.txt";
		try {
			Name = new File(NamePath);
			SureName = new File(SureNamePath);
			Organization = new File(OrganizationPath);
			Cities = new File(CitiesPath);
			States = new File(StatePath);
			Countries = new File(CountryPath);
			nSize = getSize(Name);
			snSize = getSize(SureName);
			Osize = getSize(Organization);
			CitySize = getSize(Cities);
			StateSize = getSize(States);
			Csize = getSize(Countries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getSize(File file) {
		int lineNumber = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			while (reader.readLine() != null) {
				lineNumber++;
			}
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineNumber;
	}

	private String getLineContent(File file, int i) {
		String line = "";
		try {
			fileReader = new BufferedReader(new FileReader(file));
			int count = 0;
			while (count < i - 1) {
				fileReader.readLine();
				count++;
			}
			line = fileReader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return line;
	}

	public String getRandomCompanyName() {
		int i = ThreadLocalRandom.current().nextInt(0, Osize);
		String line = getLineContent(Organization, i);
		return line;
	}

	public String getRandomSureName() {
		int i = ThreadLocalRandom.current().nextInt(0, snSize);
		String line = getLineContent(SureName, i);
		return line;
	}

	public String getRandomGivenName() {
		int i = ThreadLocalRandom.current().nextInt(0, nSize);
		String line = getLineContent(Name, i);
		return line;
	}

	public String getRandomCountry() {
		int i = ThreadLocalRandom.current().nextInt(0, Csize);
		String line = getLineContent(Countries, i);
		return line;
	}

	public String getRandomCity() {
		int i = ThreadLocalRandom.current().nextInt(0, CitySize);
		String line = getLineContent(Cities, i);
		return line;
	}

	public String getRandomStatte() {
		int i = ThreadLocalRandom.current().nextInt(0, StateSize);
		String line = getLineContent(States, i);
		return line;
	}

}
