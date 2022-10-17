package com.flexpag.paymentscheduler.controller;

import com.flexpag.paymentscheduler.dto.PaymentDTO;
import com.flexpag.paymentscheduler.enumStatus.Status;
import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import com.flexpag.paymentscheduler.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/payment", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Payment> allPayments(){
        return paymentService.searchAll();
    }

    @GetMapping("/{id}")
    public Payment searchPaymentById(@PathVariable Long id){
        log.info("Payment for id not found" + (id));
        return paymentService.searchById(id).orElseThrow(()-> new RuntimeException("Scheduled payment not found"));
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentDTO> savePayment(@Valid @RequestBody PaymentDTO paymentDTO){
        paymentDTO.setPaymentStatus(Status.pending);
        return new ResponseEntity<>(paymentService.createPayment(paymentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Payment> updatePayment(@Valid @RequestBody Payment payment){

        log.info("Updating the payment as the status allows ", payment.getDescription());
        return new ResponseEntity<>(paymentService.updatePayment(payment), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletingPayment(@PathVariable Long id){
        log.info("Excluding payment for id ", id);
        paymentService.deletePayment(id);
    }

}
