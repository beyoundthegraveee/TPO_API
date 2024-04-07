package zad1;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppForm extends JFrame {
    private JButton pressButton;
    private JTextField cityTextField;
    private JTextField currencyTextField;
    private JLabel temperatureValue;
    private JPanel mainPanel;
    private JLabel exchangeValue;
    private JLabel NBPvalue;
    private JLabel humidityValue;
    private JLabel windSpeedValue;
    private JTextField NewCountry;
    private JPanel webPanel;

    private MainWeather mainWeather;

    private Double newExchangeRate;

    private Double newNBPRate;

    private Service service;

    private final JFXPanel jfxPanel;

    private JFrame frame;


    public AppForm(Service service, String json, Double exchangeRate, Double NBPRate) throws HeadlessException {
        this.frame = this;
        this.service = service;
        mainWeather = new Gson().fromJson(json, MainWeather.class);
        this.setVisible(true);
        this.newExchangeRate = exchangeRate;
        this.newNBPRate = NBPRate;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.pack();
        jfxPanel = new JFXPanel();
        webPanel.add(jfxPanel);
        this.webPanel.add(jfxPanel);
        pressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityTextField.getText();
                mainWeather = new Gson().fromJson(service.getWeather(city), MainWeather.class);
                temperatureValue.setText(String.valueOf(mainWeather.main.temp));
                humidityValue.setText(String.valueOf(mainWeather.main.humidity));
                windSpeedValue.setText(String.valueOf(mainWeather.wind.speed));
                CreateNewService();
                exchangeValue.setText(String.valueOf(newExchangeRate));
                CreateNewServiceNBP();
                NBPvalue.setText(String.valueOf(newNBPRate));
                Platform.runLater(()->{
                    createJFXContent(city);
                    frame.pack();
                });

            }
        });
    }

    public void CreateNewService(){
        String newCountry = NewCountry.getText();
        service = new Service(newCountry);
        newExchangeRate = service.getRateFor(currencyTextField.getText());
    }

    public void CreateNewServiceNBP(){
        String newCountry = NewCountry.getText();
        service = new Service(newCountry);
        newNBPRate = service.getNBPRate();
    }

    private void createJFXContent(String cityName) {
        WebView webView = new WebView();
        webView.getEngine().load("https://en.wikipedia.org/wiki/"+cityName);
        Scene scene = new Scene(webView);
        jfxPanel.setScene(scene);
    }




}