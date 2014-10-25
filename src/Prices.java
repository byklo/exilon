import java.util.ArrayList;
import java.math.BigDecimal;

public class Prices {

	ArrayList<BigDecimal> raw;
	ArrayList<BigDecimal> real;

	public Prices(ArrayList<BigDecimal> raw, ArrayList<BigDecimal> real) {

		this.raw = raw;
		this.real = real;

	}

	void print() {

		System.out.println("// INFO - Prices");

		System.out.print("RAW : [ ");

		for (int i = 0; i < this.raw.size(); i++) {

			System.out.print(this.raw.get(i));

			if( i == this.raw.size() - 1 ){

			}else{
				System.out.print(", ");
			}
		}

		System.out.println(" ]");

		System.out.print("REAL : [ ");

		for (int i = 0; i < this.real.size(); i++) {

			System.out.print(this.real.get(i));

			if( i == this.real.size() - 1 ){

			}else{
				System.out.print(", ");
			}
		}

		System.out.println(" ]");

	}

}