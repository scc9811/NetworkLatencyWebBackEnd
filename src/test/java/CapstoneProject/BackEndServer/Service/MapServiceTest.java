package CapstoneProject.BackEndServer.Service;

import CapstoneProject.BackEndServer.Dto.GeoLocationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MapServiceTest {

    @Autowired
    private MapService mapService;

    @BeforeEach
    public void setup() {
//        MockitoAnnotations.openMocks(this);
//        ReflectionTestUtils.setField(mapService, "token", token);
    }

    @Test
    @DisplayName("ip 기반 위치 테스트")
    void getLocation(){
        // server IP
        String ip = "54.180.58.154";

        // loc: "37.4565, 126.7052"
        GeoLocationData result = mapService.getLocation(ip);

        assertNotNull(result);
        assertEquals("37.4565", result.getLatitude());
        assertEquals("126.7052", result.getLongitude());


    }
}