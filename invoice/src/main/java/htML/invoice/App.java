package htML.invoice;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		DataExtractor dd = new DataExtractor("/home/quang/Downloads/testc.html");
		dd.extract();
	}
}
