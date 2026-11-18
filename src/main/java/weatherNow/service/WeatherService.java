package weatherNow.service;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import weatherNow.dto.ForecastApiResponse;
import weatherNow.dto.ForecastItem;
import weatherNow.dto.DailyForecastResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import weatherNow.dto.WeatherApiResponse;
import weatherNow.dto.WeatherResponse;

@Service
public class WeatherService {

    private final RestClient restClient;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherService() {
        this.restClient = RestClient.create();
    }

    public WeatherResponse getWeather(String city) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q="
                + city + "&appid=" + apiKey + "&units=metric";

        WeatherApiResponse response = restClient.get()
                .uri(url)
                .retrieve()
                .body(WeatherApiResponse.class);

        WeatherResponse weatherResponse = new WeatherResponse(
                response.getName(),
                response.getMain().getTemp(),
                response.getMain().getFeels_like(),
                response.getMain().getHumidity(),
                response.getWeather()[0].getDescription(),
                response.getWind().getSpeed()

        );

        return weatherResponse;
    }
    public List<DailyForecastResponse> getForecast(String city) {

    String url = "https://api.openweathermap.org/data/2.5/forecast?q="
            + city + "&appid=" + apiKey + "&units=metric";

    ForecastApiResponse response = restClient.get()
            .uri(url)
            .retrieve()
            .body(ForecastApiResponse.class);

    Map<LocalDate, List<ForecastItem>> dailyData = new TreeMap<>();

    for (ForecastItem item : response.getList()) {

        LocalDate date = LocalDate.parse(
                item.getDt_txt().substring(0, 10));

        dailyData.computeIfAbsent(date, k -> new ArrayList<>())
                .add(item);
    }

    List<DailyForecastResponse> forecast = new ArrayList<>();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE");

    for (Map.Entry<LocalDate, List<ForecastItem>> entry : dailyData.entrySet()) {

        List<ForecastItem> items = entry.getValue();

        double minTemp = Double.MAX_VALUE;
        double maxTemp = Double.MIN_VALUE;

        ForecastItem noonItem = items.get(0);
        long closestTime = Long.MAX_VALUE;

        for (ForecastItem item : items) {

            minTemp = Math.min(minTemp, item.getMain().getTemp_min());
            maxTemp = Math.max(maxTemp, item.getMain().getTemp_max());

            LocalTime time = LocalTime.parse(
                    item.getDt_txt().substring(11));

            long difference = Math.abs(
                    time.toSecondOfDay() - LocalTime.NOON.toSecondOfDay());

            if (difference < closestTime) {
                closestTime = difference;
                noonItem = item;
            }
        }

        String day = entry.getKey().format(formatter);

        String description = noonItem.getWeather()[0].getDescription();

            forecast.add(new DailyForecastResponse(
                day, minTemp, maxTemp, description));
            }

        return forecast.stream().limit(5).toList();
    }
}