import java.util.ArrayList;

public class Stockr {

	public static void main (String[] args) {

		int 		LISTINGCOUNT	=	3;
		boolean		LOOP			=	true;
		boolean		INFO			=	false;
		int			INTERVAL		=	1500;
		String		LISTFILE		=	"./itemlist.txt";

		try {
			INFO = Boolean.parseBoolean(args[0]);
		} catch(Exception e){
			System.out.println("INFO defaulting to false.");
		}

		ListParser lp = new ListParser();
		ArrayList<Item> items = lp.parse(LISTFILE);
		Scraper alfred = new Scraper();
		int runtimes = 0;

		do {

			for (Item item : items) {

				Prices itemPrices;

				try {
					itemPrices = alfred.getPrices(item, LISTINGCOUNT);
				} catch(NumberFormatException e){
					infoFail(item);
					continue;
				}

				FinancialAdvisor tips = new FinancialAdvisor(itemPrices, LISTINGCOUNT);

				if(tips.decide()){
					notify(item, tips);
				}else if(INFO){
					info(item, tips);
				}

				System.out.print(".");

				try {
					Thread.sleep(INTERVAL);
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}

			System.out.println("");
			System.out.println("Number of times ran: " + ++runtimes);
			
			try {
				Thread.sleep( 2 * INTERVAL );
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		} while (LOOP);

	}

	public static void infoFail(Item item) {

		System.out.println("");
		System.out.println("FAIL : Could not fetch prices for " + item.model + " " + item.variant + " " + item.grade);
	}

	public static void info(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println(item.model + "\t\t" + item.variant + "\t\t" + item.grade + "\t\t" + ( item.stattrak.matches("yes") ? "STATTRAK" : "" ) );
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Current Price:\t\t"		+ tips.getCurrentPrice());
		System.out.println("Average Raw Value:\t"	+ tips.getAverageRawValue()		+ "\tDELTA\t" + tips.getDeltaRaw()		);
		System.out.println("Average Real Value:\t"	+ tips.getAverageRealValue()	+ "\tDELTA\t" + tips.getDeltaReal()	);

	}

	public static void notify(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
		
		info(item, tips);
		System.out.println("Potential Profit:\t"	+ tips.getPotentialProfit());
		System.out.println("Yield on Cost:\t\t"		+ tips.getYieldOnCost());

		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
	}

}
