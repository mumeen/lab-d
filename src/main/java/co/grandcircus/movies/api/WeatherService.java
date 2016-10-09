package co.grandcircus.movies.api;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.grandcircus.movies.model.Weather;

/**
 * Responsibility: Get Weather data from weather.gov
 */
@Service
public class WeatherService {
	
	private final static String GRAND_CIRCUS_LAT = "42.335993";
	private final static String GRAND_CIRCUS_LON = "-83.049806";

	public Weather getCurrentWeatherAtGrandCircus() {
		return getCurrentWeatherAt(GRAND_CIRCUS_LAT, GRAND_CIRCUS_LON);
	}
	
	public Weather getCurrentWeatherAt(String lat, String lon) {
		String url = "http://forecast.weather.gov/MapClick.php?lat=" + lat + "&lon=" + lon + "&FcstType=json";
		// Use HTTP GET with the above URL
		try (BufferedReader reader = HttpHelper.doGet(url)) { // try with resources will auto close the reader
			if (reader == null) {
				throw new RuntimeException("Not found: " + url);
			}
			
			// parse the HTTP response body to JSON
			JsonElement root = new JsonParser().parse(reader);
			// pick the "currentobservation" key from the root JSON object.
			JsonObject currentObservation = root.getAsJsonObject().get("currentobservation").getAsJsonObject();

			Weather weather = new Weather();
			// pick the "Temp" key from the currentobservation object
			weather.setTemperature(currentObservation.get("Temp").getAsInt());
			// pick the "Weatherimage" key from the currentobservation object
			weather.setImage("http://forecast.weather.gov/newimages/medium/"
					+ currentObservation.get("Weatherimage").getAsString());

			return weather;
		} catch (IOException ex) {
			throw new RuntimeException("Error reading from URL: " + url, ex);
		}
	}

}