package com.flexpag.paymentscheduler.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.flexpag.paymentscheduler.enumStatus.Status;
import com.flexpag.paymentscheduler.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO implements Serializable {

    private Long id;
    private String description;
    private Double amount;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private Status paymentStatus;

    public static PaymentDTO convertDto(Payment payment){
        return PaymentDTO.builder().id(payment.getId()).description
                        (payment.getDescription()).paymentStatus(payment.getPaymentStatus()).
                dateTime(payment.getDateTime()).amount(payment.getAmount()).build();
    }
}
