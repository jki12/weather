package greenB.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;

public class Util {
    private static final StringBuilder SB = new StringBuilder();

    public static String getFormattedDate(@NonNull OffsetDateTime date)
    {
        SB.setLength(0); // init.

        SB.append(date.getYear());
        if (date.getMonthValue() < 10) {
            SB.append(0);
        }
        SB.append(date.getMonthValue());
        if (date.getDayOfMonth() < 10) {
            SB.append(0);
        }
        SB.append(date.getDayOfMonth());

        return SB.toString();
    }

    /**
     * return an empty string, if exception occurs.
     * */
    public static String getWeatherData() {
        SB.setLength(0);

        var urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst");

        urlBuilder.append("?serviceKey=" + Key.KEY);
        urlBuilder.append("&dataType=" + "JSON");
        urlBuilder.append("&numOfRows=" + "10");
        urlBuilder.append("&pageNo=" + "1");
        urlBuilder.append("&base_date=" + getFormattedDate(OffsetDateTime.now()));
        urlBuilder.append("&base_time=" + "0600"); // 임시값으로 0600으로 넣어두었음.
        urlBuilder.append("&nx=" + "98");
        urlBuilder.append("&ny=" + "78");

        try {
            var url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            while ((line = rd.readLine()) != null) {
                SB.append(line);
            }

            rd.close();
            conn.disconnect();
        }
        catch (Exception e) {
            System.err.println("error : getWeatherData");

            return "";
        }

        return SB.toString();
    }

    public static ArrayList<WeatherDto> f(String json)
    {
        var list = new ArrayList<WeatherDto>();

        if (json.equals("")) {
            return list;
        }

        SB.setLength(0);

        try {
            for (int i = json.indexOf('['); i < json.length(); ++i) { // indexOf return value?
                SB.append(json.charAt(i));

                if (json.charAt(i) == ']') {
                    break;
                }
            }

            String parsedJson = SB.toString();
            var objectMapper = new ObjectMapper();

            list = objectMapper.readValue(parsedJson, new TypeReference<ArrayList<WeatherDto>>() {});

            System.out.println(list.size()); // for test.
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
