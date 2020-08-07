package gateway.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
@RequestMapping("/")
public class CallerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CallerController.class);

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate template;

    @Autowired
    private DiscoveryClient discoveryClient;
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
    //Route("/")

    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    //此功能為獲取consul上service id 為 MyApp2
    @RequestMapping("/getUrl")
    public List<ServiceInstance> serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("MyApp2");
        if (list != null && list.size() > 0 ) {
            return list;
        }
        return null;
    }
    //LoadBalanced
    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}