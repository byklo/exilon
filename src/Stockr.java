import java.util.ArrayList;

public class Stockr {

	public static void main (String[] args) {

		int INTERVAL = 1000;
		String LISTFILE = "./itemlist.txt";

		ListParser lp = new ListParser();
		ArrayList<Item> items = lp.parse(LISTFILE);

		// Pass "items" to Scraper to get price listings

		Scraper alfred = new Scraper();

		for (Item item : items) {

			Prices itemPrices = alfred.getPrices(item);

			FinancialAdvisor tips = new FinancialAdvisor(itemPrices);

			if(tips.decide()){
				notify(item, tips);
			}else{
				info(item, tips);
			}

			try {
				Thread.sleep(1200);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}

		}

	}

	public static void info(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println(item.model + "\t\t" + item.variant + "\t\t" + item.grade);
		System.out.println("---------------------------------------------------------------");
		System.out.println("Stattrak:\t" + item.stattrak);
		System.out.println("Current Price:\t" + tips.getCurrentPrice());
		System.out.println("Average Raw Value:\t" + tips.getAverageRawValue());
		System.out.println("Average Real Value:\t" + tips.getAverageRealValue());

	}

	public static void notify(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
		
		info(item, tips);
		System.out.println("Potential Profit:\t" + tips.getPotentialProfit());
		System.out.println("Yield on Cost:\t" + tips.getYieldOnCost());

		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
	}

}
