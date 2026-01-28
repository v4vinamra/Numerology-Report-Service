package com.magicmond.global.numerologyreportservice.controller;


import com.magicmond.global.numerologyreportservice.dto.OrderInfoDto;
import com.magicmond.global.numerologyreportservice.entities.OrderInfo;
import com.magicmond.global.numerologyreportservice.entities.PaymentStatus;
import com.magicmond.global.numerologyreportservice.repository.OrderRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderRepository orderRepository;
    private final ChatClient chatClient;

    public OrderController(OrderRepository orderRepository, ChatClient chatClient) {
        this.orderRepository = orderRepository;
        this.chatClient = chatClient;
    }

    @PostMapping("/report")
    public ResponseEntity<?> generateReport(@RequestBody OrderInfoDto orderInfoDto) {

        OrderInfo orderInfo = OrderInfo.builder()
                .orderId("ORD" + System.currentTimeMillis())
                .amount(99)
                .paymentStatus(PaymentStatus.PENDING)
                .fullName(orderInfoDto.getFullname())
                .emailId(orderInfoDto.getEmail())
                .dob(orderInfoDto.getDob()).build();

        orderRepository.save(orderInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderInfo);
    }


    @GetMapping("/test")
    public String test() {
        return "hello";
    }


}
