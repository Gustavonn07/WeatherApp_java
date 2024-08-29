import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Weather_API {

    public static JSONObject getWeatherData(String locationName) {
        JSONArray locationData = getLocationData(locationName);

        return null;
    }

    public static JSONArray getLocationData(String locationName) {
        locationName = locationName.replaceAll("", "+");

        final String URL_STRING = "https://geocoding-api.open-meteo.com/v1/search?name=" +
                locationName + "&count=10&language=en&format=json";

        try {
            HttpURLConnection conn = fetchApiResponse(URL_STRING);
            if(conn.getResponseCode() != 200) {
                System.out.println("Error: could not connect to API");
                return null;
            }

            StringBuilder resultJSON = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());

            while(scanner.hasNext()) resultJSON.append(scanner.nextLine());

            scanner.close();
            conn.disconnect();

            JSONParser parser = new JSONParser();
            JSONObject resultsJsonObject = (JSONObject) parser.parse(String.valueOf(resultJSON));

            JSONArray locationData = (JSONArray) resultsJsonObject.get("results");
            return locationData;

        } catch (Exception err) {
            err.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchApiResponse(String URL_STRING) {
        try {
            URL url = new URL(URL_STRING);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.connect();
            return conn;

        } catch (IOException err) {
            err.printStackTrace();
        }

        return null;
    }
}
