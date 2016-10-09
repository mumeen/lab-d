package co.grandcircus.movies.api;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper {

	public static BufferedReader doGet(String urlString) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			BufferedReader body = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			if (connection.getResponseCode() == 200) {
				return body;
			} else {
				body.close();
				throw new RuntimeException(
							"HTTP Response " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}

		} catch (FileNotFoundException ex) {
			// 404 Result Status Code
			return null;
		} catch (IOException ex) {
			throw new RuntimeException("Error on GET " + urlString, ex);
		}
	}

	public static BufferedReader doPost(String urlString, String body) {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", Integer.toString(body.length()));

			connection.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.write(body.getBytes());
			}

			BufferedReader responseBody = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			if (connection.getResponseCode() == 201) {
				return responseBody;
			} else {
				responseBody.close();
				throw new RuntimeException(
							"HTTP Response " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}

		} catch (IOException ex) {
			throw new RuntimeException("Error on POST " + urlString, ex);
		}
	}
	
	public static void doPut(String urlString, String body) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Content-Length", Integer.toString(body.length()));

			connection.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.write(body.getBytes());
			}

			connection.connect();
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException(
							"HTTP Response " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}

		} catch (IOException ex) {
			throw new RuntimeException("Error on PUT " + urlString, ex);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	public static void doDelete(String urlString) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("DELETE");
			connection.connect();
			if (connection.getResponseCode() != 200) {
				throw new RuntimeException(
							"HTTP Response " + connection.getResponseCode() + " " + connection.getResponseMessage());
			}
		} catch (IOException ex) {
			throw new RuntimeException("Error on DELETE " + urlString, ex);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}