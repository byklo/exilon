public class Notifier {

	void info(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println(item.model + "\t\t" + item.variant + "\t\t" + item.grade + "\t\t" + ( item.stattrak.matches("yes") ? "STATTRAK" : "" ) );
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Current Price:\t\t"		+ tips.getCurrentPrice());
		System.out.println("Average Raw Value:\t"	+ tips.getAverageRawValue()		+ "\tDELTA\t" + tips.getDeltaRaw()		);
		System.out.println("Average Real Value:\t"	+ tips.getAverageRealValue()	+ "\tDELTA\t" + tips.getDeltaReal()	);
	}

	void deal(Item item, FinancialAdvisor tips) {

		System.out.println("");
		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
		
		this.info(item, tips);
		System.out.println("Potential Profit:\t"	+ tips.getPotentialProfit());
		System.out.println("Yield on Cost:\t\t"		+ tips.getYieldOnCost());

		System.out.println("DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL DEAL");
	}

	void fail(Item item){

		System.out.println("");
		System.out.println("No listings found for " + item.model + " " + item.variant + " " + item.grade + ( item.stattrak.matches("yes") ? " STATTRAK" : "" ) );
	}


}