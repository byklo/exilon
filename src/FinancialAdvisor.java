import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class FinancialAdvisor {

	private BigDecimal currentPrice;
	private int datapoints;
	
	private BigDecimal averageRawValue;
	private BigDecimal averageRealValue;
	
	private BigDecimal potentialProfit;
	private BigDecimal yieldOnCost;

	private BigDecimal deltaRaw;
	private BigDecimal deltaReal;

	private boolean shouldBuy;

	public FinancialAdvisor(Prices prices) {

		ArrayList<BigDecimal> raws = prices.raw;
		ArrayList<BigDecimal> real = prices.real;

		this.currentPrice = raws.get(0);
		this.datapoints = raws.size();
		this.averageRawValue = this.getAverage(raws);
		this.averageRealValue = this.getAverage(real);

		this.deltaRaw = this.averageRawValue.subtract(this.currentPrice);
		this.deltaReal = this.averageRealValue.subtract(this.currentPrice);

		this.potentialProfit = this.averageRealValue.subtract(this.currentPrice);

		MathContext mc = new MathContext(2, RoundingMode.HALF_UP);
		this.yieldOnCost = this.potentialProfit.divide(this.currentPrice, mc);

		this.shouldBuy = this.judgeByPercentYield();

	}

	boolean judgeByPercentYield() {

		BigDecimal wantedYield = new BigDecimal(0.1);

		return this.yieldOnCost.compareTo(wantedYield) > 0;
	}

	BigDecimal getAverage(ArrayList<BigDecimal> value) {

		BigDecimal temp = new BigDecimal(2);

		for (int i = 0; i < this.datapoints; i++) {
			temp = temp.add(value.get(i));
		}

		MathContext mc = new MathContext(4);

		return temp.divide(new BigDecimal(this.datapoints), mc);
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

	BigDecimal getYieldOnCost() {
		return this.yieldOnCost;
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