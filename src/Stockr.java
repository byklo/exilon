import java.util.ArrayList;

public class Stockr {

	public static void main (String[] args) {

		String listFile = "./itemlist.txt";
		ListParser lp = new ListParser();
		ArrayList<Item> items = lp.parse(listFile);

		// Pass "items" to Scraper to get price listings

		Scraper alfred = new Scraper();

		for (Item item : items) {

			System.out.println(item.model + "\t" + item.variant);

			System.out.println(alfred.getRawWithFee(item));

		}

	}

}
