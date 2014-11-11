import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;

public class FinancialAdvisor {

	private int DATAPOINTS;

	private MathContext mc = new MathContext(6);

	private BigDecimal currentPrice;
	private int dataSize;
	
	private BigDecimal breakEven;

	private BigDecimal averageRawValue;
	private BigDecimal averageRealValue;
	
	private BigDecimal potentialProfit;
	private BigDecimal yieldOnCost;

	private BigDecimal deltaBreakEven;
	private BigDecimal deltaRaw;
	private BigDecimal deltaReal;

	private BigDecimal wantedYield;
	private BigDecimal wantedProfit;

	private boolean shouldBuy;

	public FinancialAdvisor(Prices prices, int size, BigDecimal wantedYield, BigDecimal wantedProfit, BigDecimal wallet) {

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

		this.breakEven = this.currentPrice.multiply(new BigDecimal(1.15), this.mc).setScale(2, RoundingMode.HALF_EVEN);

		this.deltaBreakEven = this.averageRawValue.subtract(this.breakEven);
		this.deltaRaw = this.averageRawValue.subtract(this.currentPrice);
		this.deltaReal = this.averageRealValue.subtract(this.currentPrice);

		this.potentialProfit = this.averageRealValue.subtract(this.currentPrice, this.mc);

		this.yieldOnCost = this.potentialProfit.divide(this.currentPrice, this.mc);

		this.wantedYield = wantedYield;
		this.wantedProfit = wantedProfit;

		this.shouldBuy = this.judgeByPercentYield(wantedYield, wantedProfit, wallet);

	}

	boolean judgeByPercentYield(BigDecimal wantedYield, BigDecimal wantedProfit, BigDecimal wallet) {

		return	this.yieldOnCost.compareTo(wantedYield) > 0
					&&
				this.potentialProfit.compareTo(wantedProfit) > 0 
					&&
				this.currentPrice.compareTo(wallet) < 0
				;
	}

	BigDecimal getAverage(ArrayList<BigDecimal> value) {

		BigDecimal temp = new BigDecimal(0);

		// System.out.println("");
		// System.out.print("Dividing... SUM(");

		for (int i = 0; i < this.dataSize; i++) {

			// System.out.print(value.get(i) + " ");

			temp = temp.add(value.get(i), this.mc);
		}

		// System.out.print(") by " + new BigDecimal(this.dataSize));
		// System.out.println("");

		return temp.divide(new BigDecimal(this.dataSize), this.mc).setScale(2, RoundingMode.HALF_EVEN);
	}

	BigDecimal getCurrentPrice() {
		return this.currentPrice;
	}

	BigDecimal getBreakEven() {
		return this.breakEven;
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
		return this.yieldOnCost.multiply(new BigDecimal(100.00), this.mc).setScale(2, RoundingMode.HALF_EVEN) + "%";
	}

	String getDeltaBreakEven() {
		if(this.deltaBreakEven.compareTo(new BigDecimal(0)) > 0){
			return "+" + this.deltaBreakEven;
		}else{
			return "" + this.deltaBreakEven;
		}
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
