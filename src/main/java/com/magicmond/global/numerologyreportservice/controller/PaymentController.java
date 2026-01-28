package com.magicmond.global.numerologyreportservice.controller;

import com.magicmond.global.numerologyreportservice.dto.PaymentRequestDto;
import com.magicmond.global.numerologyreportservice.entities.OrderInfo;
import com.magicmond.global.numerologyreportservice.entities.PaymentStatus;
import com.magicmond.global.numerologyreportservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {


    private final OrderRepository orderRepository;

    @Autowired
    public PaymentController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {

        OrderInfo orderInfo = orderRepository.findByOrderId(paymentRequestDto.getOrderId());
        if (orderInfo == null) {
            return ResponseEntity.badRequest().body("Order not found");
        }

        // Check if payment was already processed
        if (orderInfo.getPaymentStatus() == PaymentStatus.SUCCESS) {
            return ResponseEntity.ok("Payment already successful");
        } else if (orderInfo.getPaymentStatus() == PaymentStatus.FAILED
                && "FAILED".equalsIgnoreCase(paymentRequestDto.getStatus())) {
            return ResponseEntity.ok("Payment already failed");
        }

        // Update payment status
        if ("SUCCESS".equalsIgnoreCase(paymentRequestDto.getStatus())) {
            orderInfo.setPaymentStatus(PaymentStatus.SUCCESS);
            orderRepository.save(orderInfo);

            // Trigger automation pipeline here if needed
            return ResponseEntity.ok("Payment successful");
        } else {
            orderInfo.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(orderInfo);
            return ResponseEntity.ok("Payment failed");
        }
    }
}
