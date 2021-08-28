package com.atgugui.springcloud.controller;


import com.atgugui.springcloud.service.PaymentService;
import com.atguigu.springcloud.entities.JsonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public JsonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("************插入结果：" + result);
        if (result > 0){
            return new JsonResult(200,"插入数据库成功",result);
        }else {
            return new JsonResult(444,"插入数据库失败");
        }
    }

    @GetMapping("/payment/get/{id}")
    public JsonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null){
            return new JsonResult(200,"查询成功,ServerPort: " + serverPort,payment);
        }else{
            return new JsonResult(444,"数据库没有对应记录");
        }
    }

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element : services){
            log.info("*********element:  " + element);
        }

        List<ServiceInstance> instances =  discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
