package greenB.weather.controller;

import greenB.weather.Util;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivingWeather {
    @GetMapping("/test")
    public static Object f() {
        return Util.f(Util.getWeatherData());
    }
}
