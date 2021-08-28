package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.JsonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public JsonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,JsonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public JsonResult<Payment> getPayment(@PathVariable Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id , JsonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public JsonResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<JsonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id,JsonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else{
            return new JsonResult<>(444,"操作失败");
        }
    }


}
