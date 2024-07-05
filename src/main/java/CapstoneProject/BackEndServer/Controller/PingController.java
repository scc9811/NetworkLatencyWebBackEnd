package CapstoneProject.BackEndServer.Controller;


import CapstoneProject.BackEndServer.Dto.ClientAvgTimeData;
import CapstoneProject.BackEndServer.Dto.ICMPInboundAccessData;
import CapstoneProject.BackEndServer.Entity.TestResult;
import CapstoneProject.BackEndServer.Service.PingTestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/ping")
@RequiredArgsConstructor
//@CrossOrigin(origins="*")
@Slf4j
public class PingController {

    @Value("${jwt.secret}")
    private String secretKey;
    
    private final PingTestService pingTestService;

    @GetMapping("/isICMPInboundAllowed")
    public ResponseEntity<ICMPInboundAccessData> isICMPInboundAllowed(HttpServletRequest request){
        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed(request.getRemoteAddr());
//        boolean isAllowedICMP = pingTestService.getIcmpPacketAllowed("127.0.0.1");
        ICMPInboundAccessData data = new ICMPInboundAccessData();
        data.setAllowed(isAllowedICMP);
        log.info("isAllowed = " + isAllowedICMP);
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("/getClientIP")
    public String getClientIP(HttpServletRequest request){
        log.info("request getClientIP");
        return request.getRemoteAddr();
    }

    @PostMapping("/storeResult")
    public ResponseEntity<String> storeResult(HttpServletRequest request, @RequestBody ClientAvgTimeData clientAvgTimeData) {
        log.info("client's avg time : " + clientAvgTimeData.getAverageLatency());
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        boolean saveResult =  pingTestService.saveAndReturnStatus(token, clientAvgTimeData);
        return ResponseEntity.status(saveResult ? HttpStatus.ACCEPTED : HttpStatus.BAD_REQUEST).body("");
    }

    @PostMapping("/getTestResult")
    public ResponseEntity<List<TestResult>> getTestResult(HttpServletRequest request) {
        log.info("==== /ping/getTestResult ====");
        // token -> email -> userId -> find TestResult -> response
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).split(" ")[1];
        List<TestResult> testResultList = pingTestService.getTestResults(token);
        log.info(testResultList.toString());
        return ResponseEntity.ok().body(testResultList);
    }

}
