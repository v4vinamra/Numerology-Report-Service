package com.magicmond.global.numerologyreportservice.controller;

import com.magicmond.global.numerologyreportservice.dto.PaymentRequestDto;
import com.magicmond.global.numerologyreportservice.entities.NumerologyReport;
import com.magicmond.global.numerologyreportservice.entities.OrderInfo;
import com.magicmond.global.numerologyreportservice.entities.PaymentStatus;
import com.magicmond.global.numerologyreportservice.repository.OrderRepository;
import com.magicmond.global.numerologyreportservice.service.NumerologyReportService;
import com.magicmond.global.numerologyreportservice.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {


    private final OrderRepository orderRepository;
    private final NumerologyReportService numerologyReportService;
    private final PdfService pdfService;

    @Autowired
    public PaymentController(OrderRepository orderRepository, NumerologyReportService numerologyReportService, PdfService pdfService) {
        this.orderRepository = orderRepository;
        this.numerologyReportService =  numerologyReportService;
        this.pdfService = pdfService;
    }

    @PostMapping("/pay")
    public ResponseEntity<?> processPayment(@RequestBody PaymentRequestDto paymentRequestDto) {

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

            NumerologyReport numerologyReport = numerologyReportService.generateReport(orderInfo);
//            System.out.println(numerologyReport);

            byte[] pdfContent = pdfService.createNumerologyPdf(numerologyReport, orderInfo);

            // 3. Set Response Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Numerology_Report.pdf");

            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

//            return ResponseEntity.ok("Payment successful");
        } else {
            orderInfo.setPaymentStatus(PaymentStatus.FAILED);
            orderRepository.save(orderInfo);
            return ResponseEntity.ok("Payment failed");
        }
    }
}
