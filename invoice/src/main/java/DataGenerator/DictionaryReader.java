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
	private String MiddleNamePath;
	private String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
			"October", "November", "December", "Jan", "Feb", "Mar", "Apr", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
			"Dec" };
	// ------------------------------
	// Reader and file
	private BufferedReader fileReader;
	private File Name;
	private File SureName;
	private File Organization;
	private File Cities;
	private File States;
	private File Countries;
	private File MiddleName;

	// ------------------------------
	// counters
	private int snSize;
	private int nSize;
	private int Osize;
	private int Csize;
	private int CitySize;
	private int StateSize;
	private int MidNameSize;
	private int monthSize = months.length;

	public DictionaryReader(String dicDir) {
//		String dicDir = new ClassPathResource(dictDir).getPath();
		NamePath = dicDir + "Name.txt";
		SureNamePath = dicDir + "SureName.txt";
		OrganizationPath = dicDir + "Organization.txt";
		CitiesPath = dicDir + "Cities.txt";
		StatePath = dicDir + "States.txt";
		CountryPath = dicDir + "Countries.txt";
		MiddleNamePath = dicDir + "MidName.txt";
		try {
			Name = new File(NamePath);
			SureName = new File(SureNamePath);
			Organization = new File(OrganizationPath);
			Cities = new File(CitiesPath);
			States = new File(StatePath);
			Countries = new File(CountryPath);
			MiddleName = new File(MiddleNamePath);
			nSize = getSize(Name);
			snSize = getSize(SureName);
			Osize = getSize(Organization);
			CitySize = getSize(Cities);
			StateSize = getSize(States);
			Csize = getSize(Countries);
			MidNameSize = getSize(MiddleName);
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

	public String getCompanyNameAt(int index) {
		String line;
		if (index > Osize) {
			System.out.println("index out of size");
			line = getLineContent(Organization, Osize - 1);
		} else {
			line = getLineContent(Organization, index);
		}
		return line;
	}

	public String getGeneratedCompanyName(String endWord) {
		int i = ThreadLocalRandom.current().nextInt(0, snSize);
		String line = getLineContent(SureName, i);
		line += " ";
		i = ThreadLocalRandom.current().nextInt(0, nSize);
		line += getLineContent(Name, i);
		line += " ";
		line += endWord;
		return line;
	}

	public String getRandomMiddleName() {
		int i = ThreadLocalRandom.current().nextInt(0, MidNameSize);
		String line = getLineContent(MiddleName, i);
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

	public String getRandomDate() {
		String line = "";
		int i = ThreadLocalRandom.current().nextInt(0, monthSize);
		int j = ThreadLocalRandom.current().nextInt(0, 31);
		int k = ThreadLocalRandom.current().nextInt(2000, 3000);
		line = Integer.toString(j) + ", " + months[i] + ", " + Integer.toString(k);
		return line;
	}

	// -----------------------------------------------------------
	// controlled output from List
	public String getSureNameAt(int index) {
		String line = getLineContent(SureName, index);
		return line;
	}

	public String getMidNameAt(int index) {
		String line = getLineContent(MiddleName, index);
		return line;
	}

	public String getNameAt(int index) {
		String line = getLineContent(Name, index);
		return line;
	}

	public String getCompanyAt(int index) {
		String line = getLineContent(Organization, index);
		return line;
	}

	public String getStateAt(int index) {
		String line = getLineContent(States, index);
		return line;
	}

	public String getCityAt(int index) {
		String line = getLineContent(Cities, index);
		return line;
	}

	public String getCountrAt(int index) {
		String line = getLineContent(Countries, index);
		return line;
	}

}
