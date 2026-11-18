package weatherNow;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import weatherNow.service.WeatherService;
import weatherNow.dto.DailyForecastResponse;
//import weatherNow.dto.WeatherApiResponse;
import weatherNow.dto.WeatherResponse;

@RestController
public class HelloController {

    private final WeatherService weatherService;

    public HelloController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from WeatherNow!";
    }

    @GetMapping("/weather")
    public WeatherResponse getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }
    @GetMapping("/forecast")
    public List<DailyForecastResponse> getForecast(@RequestParam String city) {
        return weatherService.getForecast(city);    
    }
}
