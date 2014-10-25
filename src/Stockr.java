import java.util.ArrayList;

public class Stockr {

	public static void main (String[] args) {

		boolean		LOOP		= true;
		boolean		NOTIFY		= true;
		int			INTERVAL	= 100;
		String		LISTFILE	= "./itemlist.txt";

		ListParser lp = new ListParser();
		ArrayList<Item> items = lp.parse(LISTFILE);
		Scraper alfred = new Scraper();
		int runtimes = 0;

		do {

			for (Item item : items) {

				Prices itemPrices = alfred.getPrices(item);

				FinancialAdvisor tips = new FinancialAdvisor(itemPrices);

				if(tips.decide()){
					notify(item, tips);
				}else if(NOTIFY){
					info(item, tips);
				}

				try {
					Thread.sleep(1200);
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}

			}

			System.out.println("Number of times ran: " + ++runtimes);

		} while (LOOP);

	}

	public static void info(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println(item.model + "\t\t" + item.variant + "\t\t" + item.grade);
		System.out.println("---------------------------------------------------------------");
		System.out.println("Stattrak:\t\t"			+ item.stattrak);
		System.out.println("Current Price:\t\t"		+ tips.getCurrentPrice());
		System.out.println("Average Raw Value:\t"	+ tips.getAverageRawValue()		+ "\t" + tips.getDeltaRaw()		);
		System.out.println("Average Real Value:\t"	+ tips.getAverageRealValue()	+ "\t" + tips.getDeltaReal()	);

	}

	public static void notify(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
		
		info(item, tips);
		System.out.println("Potential Profit:\t"	+ tips.getPotentialProfit());
		System.out.println("Yield on Cost:\t"		+ tips.getYieldOnCost());

		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
	}

}
