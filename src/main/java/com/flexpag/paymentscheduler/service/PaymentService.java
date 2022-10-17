package com.flexpag.paymentscheduler.service;

import com.flexpag.paymentscheduler.exception.NotFoundException;
import com.flexpag.paymentscheduler.dto.PaymentDTO;

import com.flexpag.paymentscheduler.enumStatus.Status;
import com.flexpag.paymentscheduler.model.Payment;
import com.flexpag.paymentscheduler.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    @Autowired
    private final PaymentRepository repository;

    public List<Payment> searchAll(){
        log.info("Looking for all registered payments");
        return repository.findAll();
    }

    public Optional<Payment> searchById(Long id){
        Payment payment = repository.findById(id).orElse(null);
        log.info("Looking for payment by id ", payment.getId());
        return repository.findById(id);
    }

    public Payment statusChange (Payment payment){
        payment.setPaymentStatus(Status.paid);
        log.info("Successfully paid");
        return repository.save(payment);
    }

    private Payment buildingPayment(PaymentDTO paymentDto){
        return Payment.builder().id(paymentDto.getId()).description(paymentDto.getDescription()).
                dateTime(paymentDto.getDateTime()).paymentStatus(Status.pending).amount
                        (paymentDto.getAmount()).build();
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public Payment updatePayment(Payment payment){
        log.info("Updating the payment for the Id ", payment.getId(), payment.getDescription());

        LocalDateTime localDateTime = payment.getDateTime();

        if (payment.getPaymentStatus().equals(Status.valueOf("paid"))){
            throw new RuntimeException("Payment has already been made and cannot be edited or deleted");
        }

        payment.setDateTime(localDateTime);
        payment.setPaymentStatus(Status.paid);
        return repository.save(payment);
    }

    public void deletePayment(Long id){
        Payment payment = searchById(id).orElseThrow(()-> new NotFoundException("Could not find the ID for this search: " + id));
        Status status = payment.getPaymentStatus();

        if (payment.getPaymentStatus().equals(Status.paid)){
            throw new RuntimeException("Payment has already been made and cannot be deleted");
        }
        log.info("Deleting the payment for the Id ", payment.getId());
        repository.deleteById(id);
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public PaymentDTO createPayment(PaymentDTO paymentDto){
        log.info("Creating the payment for the id " + paymentDto.getId() + paymentDto.getDescription());
        Payment payment = repository.save(buildingPayment(paymentDto));
        return paymentDto;
    }

}
