Task: network service clients 
Write an application that provides a GUI in which, after entering the city and country name, the following are displayed: 
Information about the current weather in this city. 
Information about the exchange rate of the country's currencies to the currency entered by the user. 
Information about the NBP exchange rate of the złoty to this currency of the given country. 
Wiki page with a description of the city. 
In point 1, use the api.openweathermap.org service, in point 2 - the open.er-api.com service (doc: https://www.exchangerate-api.com/docs/free), in point 3 - information from the NBP website: https://nbp.pl/statystyka-i-głośzosc/kursy/tabela-a/ and similarly for tables B and C. In point 4, use the WebEngine class from JavaFX to embed the browser in the Swing application.

The program should contain a Service class with the Service(String country) constructor and the following methods: 
String getWeather(String city) - returns weather information in the given city of a given country in JSON format (this should be the full information obtained from the openweather service - just text in JSON format), Double getRateFor(String kod_waluty) - returns the exchange rate of the given country's currency against the currency given as an argument, Double getNBPRate() - returns the Polish zloty exchange rate against the currency of a given country. 
The following example class shows a possible use of these methods: 
public class Main { 
public static void main(String[] args) { 
Service s = new Service("Poland"); 
String weatherJson = s.getWeather("Warsaw"); 
Double rate1 = s.getRateFor("USD"); 
Double rate2 = s.getNBPRate(); // ... // GUI startup part 
} 
}

Note 1: defining the methods shown in a GUI-independent way is mandatory. Note 2: In the project directory (e.g. in the lib subdirectory) you must place the JARs you use (otherwise the program will not compile) and configure the Build Path so that the pointers to these JARs are included in the Build Path.
