package com.example.eurekaClient.SpringBootEurekaClient.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestRestController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/v1/test")
    public String getHelloMessage(){
        return "Hi hello";
    }

    @GetMapping("/v1/eurekaCall1")
    public String callToClient2(){
        System.out.println("baseURL value:"+getBaseURL());
        return restTemplate.getForObject(getBaseURL()+"/v1/eurekaClient2",String.class);
    }

    @GetMapping("/v1/eurekaClient1")
    public String getEurekaClient1(){
        return "Response From EurekaClient 1";
    }

    private String getBaseURL(){
        final ServiceInstance baseUrl=loadBalancerClient.choose("EUREKA-CLIEN-2");
        return baseUrl.getUri().toString();
    }

}
