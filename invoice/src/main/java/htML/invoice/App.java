package htML.invoice;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		DataExtractor dd = new DataExtractor("/home/quang/PythonScript/invoices/test2s.html");
		dd.extract();
	}
}
