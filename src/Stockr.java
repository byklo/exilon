import java.util.ArrayList;
import java.math.BigDecimal;

public class Stockr {

	public static void main (String[] args) {

		// CONFIG
		BigDecimal	WALLET			=	new BigDecimal(120);
		BigDecimal	WANTEDYIELD		=	new BigDecimal(0.05);
		BigDecimal	WANTEDPROFIT	=	new BigDecimal(0.5);
		int 		LISTINGCOUNT	=	3;
		boolean		LOOP			=	true;
		boolean		INFO			=	false;
		int			INTERVAL		=	500;
		String		LISTFILE		=	"./itemlist.txt";

		try {
			INFO = Boolean.parseBoolean(args[0]);
		} catch(Exception e){
			System.out.println("INFO defaulting to false.");
		}

		ListParser lp = new ListParser();
		ArrayList<Item> items;
		Scraper alfred = new Scraper();
		Notifier notify = new Notifier();
		int runtimes = 0;

		do {

			items = lp.parse(LISTFILE);

			for (Item item : items) {

				Prices itemPrices;

				try {
					itemPrices = alfred.getPrices(item, LISTINGCOUNT);
					
					FinancialAdvisor tips = new FinancialAdvisor(itemPrices, LISTINGCOUNT, WANTEDYIELD, WANTEDPROFIT, WALLET);

					if(tips.decide()){
						notify.deal(item, tips);
						notify.text(item, tips);
					}else if(INFO){
						notify.info(item, tips);
					}

					System.out.print(".");

					try {
						Thread.sleep(INTERVAL);
					} catch(InterruptedException e) {
						Thread.currentThread().interrupt();
					}

					// notify.text(item, tips);

				} catch(NumberFormatException e) {

					System.out.print("x");

					try {
						Thread.sleep( 4 * INTERVAL );
					} catch(InterruptedException ie) {
						Thread.currentThread().interrupt();
					}

					// notify.fail(item);
					continue;
				}

			}

			System.out.println("");
			System.out.println("\tn = " + ++runtimes);
			
			try {
				Thread.sleep( 2 * INTERVAL );
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		} while (LOOP);

	}

}
