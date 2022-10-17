package com.flexpag.paymentscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PaymentSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentSchedulerApplication.class, args);
	}

}
