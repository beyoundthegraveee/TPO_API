/**
 *
 *  @author Kurzau Kiryl S24911
 *
 */

package zad1;


import com.google.gson.*;
import jdk.nashorn.internal.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

public class Service {


    private String country;


    public Service(String country) {
        this.country = country;
    }


    public String getWeather(String warsaw) {
        String apiKey = "a75707af412ca90bdca30b10bf5a195e";
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
                warsaw, apiKey);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new URI(url).toURL().openConnection().getInputStream()
        ))){
            String json = bufferedReader.lines().collect(Collectors.joining());
            return json;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getRateFor(String code) {
        String tmp = "";
        for (Locale availableLocale : Locale.getAvailableLocales()) {
            if (availableLocale.getDisplayCountry(Locale.ENGLISH).equals(country)) {
                tmp = String.valueOf(Currency.getInstance(availableLocale));
            }
        }
        String apiKey = "4c15a927efa53b4cbcae2f16";
        String url = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", apiKey, tmp, code);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                new URI(url).toURL().openConnection().getInputStream()
        ))){
            JsonObject jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();
            double rate = jsonObject.get("conversion_rate").getAsDouble();
            return rate;
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getNBPRate() {
        String tmp = "";
        double rate = 0;
        for (Locale availableLocale : Locale.getAvailableLocales()) {
            if (availableLocale.getDisplayCountry(Locale.ENGLISH).equals(country)) {
                tmp = String.valueOf(Currency.getInstance(availableLocale));
            }
        }
        if (!tmp.equals("PLN")) {
            String url = String.format("http://api.nbp.pl/api/exchangerates/rates/%s/%s/?format=json", "a", tmp);
            try {
                URL apiUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        JsonObject jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();
                        JsonArray array = jsonObject.getAsJsonArray("rates");
                        for (JsonElement element : array) {
                            JsonObject rateObject = element.getAsJsonObject();
                            rate = rateObject.get("mid").getAsDouble();
                            break;
                        }
                    }
                }else {
                    rate = getNBPRateForB();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            rate = 1;
        }
        return rate;
    }

    public Double getNBPRateForB(){
        String tmp = "";
        double rate = 0;
        for (Locale availableLocale : Locale.getAvailableLocales()) {
            if (availableLocale.getDisplayCountry(Locale.ENGLISH).equals(country)) {
                tmp = String.valueOf(Currency.getInstance(availableLocale));
            }
        }
        if (!tmp.equals("PLN")) {
            String url = String.format("http://api.nbp.pl/api/exchangerates/rates/%s/%s/?format=json", "b", tmp);
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    new URI(url).toURL().openConnection().getInputStream()
            ))) {
                JsonObject jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();
                JsonArray array = jsonObject.getAsJsonArray("rates");
                for (JsonElement element : array) {
                    JsonObject rateObject = element.getAsJsonObject();
                    rate = rateObject.get("mid").getAsDouble();
                    break;
                }
            } catch (IOException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return rate;


    }



}
