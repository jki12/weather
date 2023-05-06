package greenB.weather.controller;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import greenB.weather.Key;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivingWeather {
    private static final StringBuilder SB = new StringBuilder();

    private static final String X = "98";
    private static final String Y = "78";

    private static String testDate = "20230503";

    @GetMapping("/y")
    public static String getResponse() throws Exception// rename, return value?
    {
        SB.setLength(0);

        var urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); // url

        urlBuilder.append("?serviceKey=" + Key.KEY);
        urlBuilder.append("&dataType=" + "JSON");
        urlBuilder.append("&numOfRows=" + "10");
        urlBuilder.append("&pageNo=" + "1");
        urlBuilder.append("&base_date=" + testDate); // need to modify.
        urlBuilder.append("&base_time=" + "0600");
        urlBuilder.append("&nx=" + X);
        urlBuilder.append("&ny=" + Y);

        var url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300)
        {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        else
        {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        String line;
        while ((line = rd.readLine()) != null)
        {
            SB.append(line);
        }

        rd.close();
        conn.disconnect();

        String res = SB.toString();

        // to object, return.

        return SB.toString();
    }
}
