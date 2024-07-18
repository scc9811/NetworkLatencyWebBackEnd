package CapstoneProject.BackEndServer.Service;

import CapstoneProject.BackEndServer.Dto.GeoLocationData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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
    @DisplayName("ip 기반 위치정보 추출 테스트")
    void getLocation(){
        // server IP
        String ip = "54.180.58.154";

        // loc: "37.4565, 126.7052"
        GeoLocationData result = mapService.getLocation(ip);

        assertNotNull(result);
        assertEquals("37.456", result.getLatitude());
        assertEquals("126.7052", result.getLongitude());


    }
}