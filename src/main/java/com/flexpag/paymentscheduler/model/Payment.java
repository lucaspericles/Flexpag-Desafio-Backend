package com.flexpag.paymentscheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.Hibernate;
import com.flexpag.paymentscheduler.enumStatus.Status;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "payment")
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RequiredArgsConstructor
public class Payment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amountPayment")
    private Double amount;

    @NotNull
    @Column(name = "description")
    private String description;

    @Column(name = "datatime")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status paymentStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Payment that = (Payment) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    
    
    
    public void setPaymentStatus(com.flexpag.paymentscheduler.enumStatus.Status paid) {
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public Status getPaymentStatus() {
		return paymentStatus;
	}
    
    
    
    
}
