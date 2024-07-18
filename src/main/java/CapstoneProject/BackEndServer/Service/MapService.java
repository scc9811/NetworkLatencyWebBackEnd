package CapstoneProject.BackEndServer.Service;

import CapstoneProject.BackEndServer.Dto.GeoLocationData;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class MapService {

    @Value("${ipInfo.key}")
    private String token;

    public GeoLocationData getLocation(String ip) {
        String url = "https://api.ipregistry.co/" + ip + "?key=" + token;
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
            }

            in.close();

            // JSON 응답 처리
            String jsonResponse = response.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // response 형태
            //  {
            //      "location": {
            //          "latitude": ~~
            //          "longitude": ~~
            //      }
            //  }

            // location 객체
            JSONObject location = jsonObject.getJSONObject("location");

            log.info("\n" + "---- " + ip + " location----\n" + location.toString());

            GeoLocationData geoLocationData = new GeoLocationData();

            geoLocationData.setLatitude(String.valueOf(location.getDouble("latitude")));
            geoLocationData.setLongitude(String.valueOf(location.getDouble("longitude")));

            return geoLocationData;
        }

        catch (Exception e) {
            return null;
        }

    }
}
