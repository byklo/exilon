import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.math.BigDecimal;

public class Scraper {

	//BigDecimal[] getPrices() {
//
	//}

	String spaceFormat(String input) {

		return input.replace(" ", "%20");
	}

	String generateUrl(Item item) {

		String url = "http://steamcommunity.com/market/listings/";

		// CSGO
		url += "730/";

		// stattrak
		if(item.stattrak.equals("yes")){
			url += "StatTrak™%20";
		}else{

		}

		// model
		url += this.spaceFormat(item.model);
		url += "%20%7C%20";

		// variant
		url += this.spaceFormat(item.variant);
		url += "%20%28";

		// grade
		url += this.spaceFormat(item.grade);
		url += "%29";

		// currency = CDN = 20
		// /render shoots out JSON object
		// you can specify how many listings you want with count=n
		url += "/render?&currency=20&count=20";

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

			e.printStackTrace();
		}

		return jObject;
	}

	Document getHtmlReponse(JsonObject jsonReponseObject) {

		Document document = Jsoup.parse( jsonReponseObject.get("results_html").getAsString() );

		return document;
	}

	String getRawWithFee(Item item) {

		String queryUrl = this.generateUrl(item);

		JsonObject jsonReponseObject = this.getJsonResponse(queryUrl);

		Document document = this.getHtmlReponse(jsonReponseObject);

		Elements priceNodes = document.select("span.market_listing_price.market_listing_price_with_fee");

		String rawData = priceNodes.html();

		return rawData;
	}

}
