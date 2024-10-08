import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Weather_API {

    public static JSONObject getWeatherData(String locationName) {
        JSONArray locationData = getLocationData(locationName);

        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (double) location.get("latitude");
        double longitude = (double) location.get("longitude");

        final String URL_STRING = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relativehumidity_2m,weather_code,windspeed_10m&timezone=America%2FLos_Angeles";

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
            JSONObject resultJsonObject = (JSONObject) parser.parse(String.valueOf(resultJSON));
            JSONObject hourly = (JSONObject) resultJsonObject.get("hourly");

            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            JSONArray weather_code = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weather_code.get(index));

            JSONArray relativeHumidity = (JSONArray) hourly.get("relativehumidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            JSONArray windspeedData = (JSONArray) hourly.get("windspeed_10m");
            double windspeed = (double) windspeedData.get(index);

            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;

        } catch (Exception err) {
            err.printStackTrace();
        }

        return null;
    }

    public static JSONArray getLocationData(String locationName) {
        locationName = locationName.replaceAll(" ", "+");

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
            System.out.println(URL_STRING);
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

    private static int findIndexOfCurrentTime(JSONArray timeList) {
        String currentTime = getCurrentTime();

        for(int i = 0; i < timeList.size(); i++) {
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)) return i;
        }

        return 0;
    }

    private static String getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();

        //AAAA-DD-MMTHH:MM
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;
    }

    private static String convertWeatherCode(long weather_code) {
        String weatherCondition = "";

        if(weather_code == 0L)weatherCondition = "Clear";

        if(weather_code > 0L && weather_code <= 3L) weatherCondition = "Cloudy";

        if((weather_code >= 51L && weather_code <= 67L)
                || (weather_code >= 80L && weather_code <= 99L)) weatherCondition = "Rain";

        if(weather_code >= 71L && weather_code <= 77L) weatherCondition = "Snow";

        return weatherCondition;
    }
}
