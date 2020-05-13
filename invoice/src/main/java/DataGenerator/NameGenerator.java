package DataGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class NameGenerator {
//	private String fullName = "";
	private String[] sureName = { "Müller", "Schmidt", "Schneider", "Fischer", "Weber", "Meyer", "Wagner", "Becker",
			"Schulz", "Hoffmann", "Schäfer", "Koch", "Bauer", "Richter", "Klein", "Wolf", "Schröder", "Neumann",
			"Schwarz", "Zimmermann", "Braun", "Krüger", "Hofmann", "Hartmann", "Lange", "Schmitt", "Werner", "Schmitz",
			"Krause", "Meier", "Lehmann", "Schmid", "Schulze", "Maier", "Köhler", "Herrmann", "König", "Walter",
			"Mayer", "Huber", "Kaiser", "Fuchs", "Peters", "Lang", "Scholz", "Möller", "Weiß", "Jung", "Hahn",
			"Schubert", "Vogel", "Friedrich", "Keller", "Günther", "Frank", "Berger", "Winkler", "Roth", "Beck",
			"Lorenz", "Baumann", "Franke", "Albrecht", "Schuster", "Simon", "Ludwig", "Böhm", "Winter", "Kraus",
			"Martin", "Schumacher", "Krämer", "Vogt", "Stein", "Jäger", "Otto", "Sommer", "Groß", "Seidel", "Heinrich",
			"Brandt", "Haas", "Schreiber", "Graf", "Schulte", "Dietrich", "Ziegler", "Kuhn", "Kühn", "Pohl", "Engel",
			"Horn", "Busch", "Bergmann", "Thomas", "Voigt", "Sauer", "Arnold", "Wolff", "Pfeiffer" };
	private String[] givenName = { "Finn", "Jan", "Jannik", "Jonas", "Leon", "Luca", "Lukas", "Niklas", "Tim", "Tom",
			"Anna", " Hannah", " Julia", " Lara", " Laura", " Lea", " Lena", " Lisa", " Michelle", " Sarah",
			"Alexander", " Christian", " Daniel", " Dennis", " Jan", " Martin", " Michael", " Sebastian", " Stefan",
			" Thomas", "Anja", " Christina", " Julia", " Katrin", " Melanie", " Nadine", " Nicole", " Sabrina",
			" Sandra", " Stefanie" };

	private String[] Orgarnization = { "China’s Industrial & Commercial Bank of China", "China Construction Bank",
			" JPMorgan Chase", "General Electric", " Exxon Mobil", "HSBC", "Royal Dutch Shell",
			"Agricultural Bank of China", "Berkshire Hathaway", "PetroChina", "Bank of China", "Wells Fargo", "Chevron",
			"Volkswagen Group", "Apple", "Wal-Mart Stores", "Gazprom", "BP", "Citigroup", "Petrobras",
			"Samsung Electronics", "BNP Paribas", "Total", "AT&T", "Allianz" };
	private String[] Countries = { "Australia", "Canada", "Saudi Arabia", "United State", "India", "Russia",
			"South Africa", "Turkey", "Argentina", "Mexico", "Brazil", "France", "Germany", "Italy", "United Kingdom",
			"China", "Indonesia", "Japan", "South Korea" };
	private String[] Cities = {"Frankfurt am Main", "London", "New York", "Venice", "Berlin"};
	private String[] States = {"Frankfurt am Main", "London", "New York", "Venice", "Berlin"};
	private int snSize = sureName.length;
	private int nSize = givenName.length;
	private int Osize = Orgarnization.length;
	private int Csize = Countries.length;
	private int CitySize = Cities.length;
	private int StateSize = States.length;


	public NameGenerator() {

	}

	public String getRandomCompanyName() {
		int i = ThreadLocalRandom.current().nextInt(0, Osize);
		return Orgarnization[i];
	}

	public String getRandomSureName() {
		int i = ThreadLocalRandom.current().nextInt(0, snSize);
		return sureName[i];
	}

	public String getRandomGivenName() {
		int i = ThreadLocalRandom.current().nextInt(0, nSize);
		return givenName[i];
	}

	public String getRandomCountry() {
		int i = ThreadLocalRandom.current().nextInt(0, Csize);
		return Countries[i];
	}
	public String getRandomCity() {
		int i = ThreadLocalRandom.current().nextInt(0, CitySize);
		return Cities[i];
	}
	public String getRandomStatte() {
		int i = ThreadLocalRandom.current().nextInt(0, StateSize);
		return States[i];
	}

}
