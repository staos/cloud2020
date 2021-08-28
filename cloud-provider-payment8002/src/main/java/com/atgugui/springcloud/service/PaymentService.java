package com.atgugui.springcloud.service;


import com.atguigu.springcloud.entities.Payment;

public interface PaymentService {
    Integer create(Payment payment);

    Payment getPaymentById(Long id);
}
