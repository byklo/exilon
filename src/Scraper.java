import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.util.Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.math.BigDecimal;

public class Scraper {

	String formatSpaces(String input) {

		return input.replace(" ", "%20");
	}


	String generateUrl(Item item, int size) {

		int listingCount = size;

		String url = "http://steamcommunity.com/market/listings/";

		// CSGO
		url += "730/";

		// stattrak
		if(item.stattrak.equals("yes")){
			url += "StatTrak™%20";
		}else{

		}

		// model
		url += this.formatSpaces(item.model);
		url += "%20%7C%20";

		// variant
		url += this.formatSpaces(item.variant);
		url += "%20%28";

		// grade
		url += this.formatSpaces(item.grade);
		url += "%29";

		// currency = CDN = 20
		// /render shoots out JSON object
		// you can specify how many listings you want with count=n 5 IS MINIMUM
		url += "/render?&currency=20&count=" + listingCount;

		return url;
	}


	JsonObject getJsonResponse(String url) {

		JsonObject jObject = null;

		try {

			URL urlObject = new URL(url);
			HttpURLConnection request = (HttpURLConnection) urlObject.openConnection();
			request.connect();

			JsonParser jParser = new JsonParser();
			JsonElement jElement = jParser.parse( new InputStreamReader( (InputStream) request.getContent() ) );
			jObject = jElement.getAsJsonObject();

		} catch(IOException e) {

			System.out.println("Could not connect to URL.");

			e.printStackTrace();
		}

		return jObject;
	}


	Document getHtmlReponse(JsonObject jsonReponseObject) {

		Document document = Jsoup.parse( jsonReponseObject.get("results_html").getAsString() );

		return document;
	}


	String formatHtml(String html) {

		return html.replace("CDN$ ", "");
	}


	ArrayList<BigDecimal> getNumbers(String[] priceStrings) {

		ArrayList<BigDecimal> priceNumbers = new ArrayList<BigDecimal>();

		for (String priceString : priceStrings) {
			
			// System.out.println(priceString);
			
			priceNumbers.add(new BigDecimal(priceString));
		}

		return priceNumbers;
	}


	Prices getPrices(Item item, int size) {

		String queryUrl = this.generateUrl(item, size);

		JsonObject jsonReponseObject = this.getJsonResponse(queryUrl);

		Document document = this.getHtmlReponse(jsonReponseObject);

		Elements rawNodes	= document.select("span.market_listing_price.market_listing_price_with_fee");
		Elements realNodes	= document.select("span.market_listing_price.market_listing_price_without_fee");

		String rawPricesList	= formatHtml(rawNodes.html());
		String realPricesList	= formatHtml(realNodes.html());

		String[] rawPriceStrings	= rawPricesList.split("\n");
		String[] realPriceStrings	= realPricesList.split("\n");

		// System.out.println(Arrays.toString(rawPriceStrings));
		// System.out.println(Arrays.toString(realPriceStrings));

		Prices prices = new Prices(getNumbers(rawPriceStrings), getNumbers(realPriceStrings));

		return prices;
	}

}