package weatherNow.dto;

public class WeatherApiResponse {

    private String name;
    private MainWeather main;
    private WeatherDescription[] weather;
    private WindWeather wind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainWeather getMain() {
        return main;
    }

    public void setMain(MainWeather main) {
        this.main = main;
    }

    public WeatherDescription[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherDescription[] weather) {
        this.weather = weather;
    }

    public WindWeather getWind() {
        return wind;
    }

    public void setWind(WindWeather wind) {
        this.wind = wind;
    }
}