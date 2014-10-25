import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

import java.util.ArrayList;

public class ListParser {

	ArrayList<Item> parse(String listFilePath) {

		ArrayList<Item> items = new ArrayList<Item>();

		try {

			File listFile = new File(listFilePath);
			FileReader fr = new FileReader(listFile);
			BufferedReader reader = new BufferedReader(fr);

			String line = null;

			while( (line = reader.readLine()) != null ) {

				String[] attr = line.split("\t");

				Item newItem = new Item(attr[0], attr[1], attr[2], attr[3]);

				items.add(newItem);

			}

			reader.close();

		}catch(Exception e) {

			e.printStackTrace();
		
		}

		return items;

	}

	void testParse(String listFilePath) {

		ArrayList<Item> items = this.parse(listFilePath);

		for (Item item : items) {

			System.out.println("Model: " + item.model);
			System.out.println("Variant: " + item.variant);
			System.out.println("Grade: " + item.grade);
			System.out.println("Stattrak: " + item.stattrak);
			System.out.println("");

		}

	}

}