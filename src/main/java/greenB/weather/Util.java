package greenB.weather;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

public class Util {
    private static final StringBuilder SB = new StringBuilder();

    public static String getFormattedDate() { // url part.
        // 02:10, 05:10, 08:10, 11:10, 14:10, 17:10, 20:10, 23:10

        SB.setLength(0);

        var now = OffsetDateTime.now();
        var baseDate = OffsetDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 23, 10, 0, 0, now.getOffset());

        while (now.compareTo(baseDate) == -1) {
            baseDate = baseDate.minusHours(3);
        }

        // append date.
        SB.append("&base_date=");
        SB.append(String.format("%d%02d%02d", baseDate.getYear(), baseDate.getMonthValue(), baseDate.getDayOfMonth()));

        // append time.
        SB.append("&base_time=");
        SB.append(String.format("%02d%02d", baseDate.getHour(), baseDate.getMinute()));

        return SB.toString();
    }

    /**
     * return an empty string, if exception occurs.
     * */
    public static String getWeatherData() {
        SB.setLength(0);

        var urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst");
        var now = OffsetDateTime.now();

        urlBuilder.append("?serviceKey=" + Key.KEY);
        urlBuilder.append("&dataType=" + "JSON");
        urlBuilder.append("&numOfRows=" + "240");
        urlBuilder.append("&pageNo=" + "1");
        urlBuilder.append(getFormattedDate());
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
            e.printStackTrace();

            SB.setLength(0);
        }

        return SB.toString();
    }

    public static String parse(String json) {
        SB.setLength(0);

        for (int i = json.indexOf('['); i < json.length(); ++i) {
            SB.append(json.charAt(i));

            if (json.charAt(i) == ']') {
                break;
            }
        }

        return SB.toString();
    }

    public static ArrayList<WeatherDto> f(String json)
    {
        var list = new ArrayList<WeatherDto>();

        SB.setLength(0);

        if (json.isEmpty() || json.indexOf('[') == -1) {
            return list;
        }

        try {
            String parsedJson = parse(json);
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
