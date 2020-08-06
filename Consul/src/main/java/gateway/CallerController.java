package gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class CallerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallerController.class);

    @Autowired
    Environment environment;

    @Autowired
    RestTemplate template;

    //route(localhost:8080/Filter)
    @GetMapping("/MyApp2")
    public String call() {

        //url 必須要是consul註冊的名稱
        String CallResponse = template.getForObject("http://SpringBoot", String.class);

        //response on "localhost:8080/caller" page
        return "I'm Caller running on port " +
                environment.getProperty("local.server.port")
                + " calling-> " + CallResponse;
    }

    //route(localhost:8080/Filter)
    @GetMapping("/Filter")
    public String slow() {

        String callmeResponse = template.getForObject("http://Filter/get", String.class);

        return "I'm Caller running on port " +
                environment.getProperty("local.server.port") +
                " calling-> " + callmeResponse;
    }
}