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
//        String url = "https://ipinfo.io/" + ip + "?token=" + token;
//        String url = "https://api.ip2location.io/?key=" + token + "&ip=" + ip;
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
                log.info(inputLine);
            }

            in.close();

            // JSON 응답 처리
            String jsonResponse = response.toString();
            JSONObject jsonObject = new JSONObject(jsonResponse);

            // response 형태 :
            // {
            //	"location": {
            //		"region": {
            //			"latitude": 37.7749,
            //			"longitude": -122.4194
            //		}
            //	}
            //}

            // location 객체
            JSONObject location = jsonObject.getJSONObject("location");

            // region 객체
            JSONObject region = location.getJSONObject("region");



            // "loc" 키로부터 위도와 경도 추출
//            String loc = jsonObject.getString("loc");
//            String[] latLong = loc.split(",");

            GeoLocationData geoLocationData = new GeoLocationData();
//            geoLocationData.setLatitude(latLong[0]);
//            geoLocationData.setLongitude(latLong[1]);

            geoLocationData.setLatitude(String.valueOf(region.getDouble("latitude")));
            geoLocationData.setLongitude(String.valueOf(region.getDouble("longitude")));

            log.info(String.valueOf(region.getDouble("latitude")));
            log.info(String.valueOf(region.getDouble("longitude")));

            return geoLocationData;
        }

        catch (Exception e) {
            return null;
        }

    }
}
