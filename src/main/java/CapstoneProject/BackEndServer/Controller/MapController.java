package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.GeoLocationData;
import CapstoneProject.BackEndServer.Service.MapService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
@Slf4j
public class MapController {

    private final MapService mapservice;

    @GetMapping("/getClientLocation")
    public ResponseEntity<GeoLocationData> getClientLocation(HttpServletRequest request) {
        GeoLocationData clientLocation = mapservice.getLocation(request.getRemoteAddr());
        log.info("addr = " + request.getRemoteAddr());
        if(clientLocation == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(clientLocation);
    }

    @GetMapping("/getServerLocation")
    public ResponseEntity<GeoLocationData> getServerLocation(HttpServletRequest request) {
        GeoLocationData serverLocation = mapservice.getLocation("54.180.58.154");
        if(serverLocation == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.ok().body(serverLocation);
    }

}
