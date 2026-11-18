package weatherNow.dto;

public class ForecastItem {

    private String dt_txt;
    private ForecastMain main;
    private WeatherDescription[] weather;

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public ForecastMain getMain() {
        return main;
    }

    public void setMain(ForecastMain main) {
        this.main = main;
    }

    public WeatherDescription[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherDescription[] weather) {
        this.weather = weather;
    }
}