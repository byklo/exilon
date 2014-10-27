import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class FinancialAdvisor {

	private int DATAPOINTS;

	private BigDecimal currentPrice;
	private int dataSize;
	
	private BigDecimal averageRawValue;
	private BigDecimal averageRealValue;
	
	private BigDecimal potentialProfit;
	private BigDecimal yieldOnCost;

	private BigDecimal deltaRaw;
	private BigDecimal deltaReal;

	private BigDecimal wantedYield;
	private BigDecimal wantedProfit;

	private boolean shouldBuy;

	public FinancialAdvisor(Prices prices, int size, BigDecimal wantedYield, BigDecimal wantedProfit) {

		this.DATAPOINTS = size;

		ArrayList<BigDecimal> raws = prices.raw;
		ArrayList<BigDecimal> real = prices.real;

		this.currentPrice = raws.get(0);

		if(raws.size() < this.DATAPOINTS){
			this.dataSize = raws.size();
		}else{
			this.dataSize = this.DATAPOINTS;
		}

		this.averageRawValue = this.getAverage(raws);
		this.averageRealValue = this.getAverage(real);

		this.deltaRaw = this.averageRawValue.subtract(this.currentPrice);
		this.deltaReal = this.averageRealValue.subtract(this.currentPrice);

		this.potentialProfit = this.averageRealValue.subtract(this.currentPrice);

		MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
		this.yieldOnCost = this.potentialProfit.divide(this.currentPrice, mc);

		this.wantedYield = wantedYield;
		this.wantedProfit = wantedProfit;

		this.shouldBuy = this.judgeByPercentYield(wantedYield, wantedProfit);

	}

	boolean judgeByPercentYield(BigDecimal wantedYield, BigDecimal wantedProfit) {

		return this.yieldOnCost.compareTo(wantedYield) > 0 && this.potentialProfit.compareTo(wantedProfit) > 0 ;
	}

	BigDecimal getAverage(ArrayList<BigDecimal> value) {

		BigDecimal temp = new BigDecimal(0);

		// System.out.println("");
		// System.out.print("Dividing... SUM(");

		for (int i = 0; i < this.dataSize; i++) {

			// System.out.print(value.get(i) + " ");

			temp = temp.add(value.get(i));
		}

		// System.out.print(") by " + new BigDecimal(this.dataSize));
		// System.out.println("");

		MathContext mc = new MathContext(4);

		return temp.divide(new BigDecimal(this.dataSize), mc);
	}

	BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	BigDecimal getAverageRawValue() {
		return this.averageRawValue;
	}

	BigDecimal getAverageRealValue() {
		return this.averageRealValue;
	}

	BigDecimal getPotentialProfit() {
		return this.potentialProfit;
	}

	String getYieldOnCost() {
		return this.yieldOnCost.multiply(new BigDecimal(100)) + "%";
	}

	String getDeltaRaw() {
		if(this.deltaRaw.compareTo(new BigDecimal(0)) > 0){
			return "+" + this.deltaRaw;
		}else{
			return "" + this.deltaRaw;
		}
	}

	String getDeltaReal() {
		if(this.deltaReal.compareTo(new BigDecimal(0)) > 0){
			return "+" + this.deltaReal;
		}else{
			return "" + this.deltaReal;
		}
	}

	boolean decide() {
		return this.shouldBuy;
	}

}
