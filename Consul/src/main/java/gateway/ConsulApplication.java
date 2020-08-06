package gateway;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class ConsulApplication {



    //啟動器
    public static void main(String[] args) {
        SpringApplication.run(ConsulApplication.class, args);
    }

    //Route("/")
    @RequestMapping("/")
    public String home() {
        return "Hello World";
    }

    @Autowired
    private DiscoveryClient discoveryClient;

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

    //spring-cloud-bus
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping("/send")
    public void send(){
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }


}