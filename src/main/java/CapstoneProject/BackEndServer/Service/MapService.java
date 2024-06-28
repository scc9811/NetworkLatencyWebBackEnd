package CapstoneProject.BackEndServer.Service;

import CapstoneProject.BackEndServer.Dto.GeoLocationData;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class MapService {

    @Value("${ipInfo.key}")
    private String token;

    public GeoLocationData getLocation(String ip) {
        String url = "https://ipinfo.io/" + ip + "?token=" + token;
        try{
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) return null;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
                System.out.println(inputLine);
            }

            in.close();

            // JSON 응답 처리
            String jsonResponse = response.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // "loc" 키로부터 위도와 경도 추출
            String loc = jsonObject.getString("loc");
            String[] latLong = loc.split(",");

            GeoLocationData geoLocationData = new GeoLocationData();
            geoLocationData.setLatitude(latLong[0]);
            geoLocationData.setLongitude(latLong[1]);

            return geoLocationData;
        }

        catch (Exception e) {
            return null;
        }

    }
}
