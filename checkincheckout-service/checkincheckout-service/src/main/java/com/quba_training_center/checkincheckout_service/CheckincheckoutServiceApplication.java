package com.quba_training_center.checkincheckout_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableScheduling
public class CheckincheckoutServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckincheckoutServiceApplication.class, args);

	}

}
