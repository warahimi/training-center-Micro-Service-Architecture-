package com.quba_training_center.checkincheckout_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CheckInCheckOutStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String className;
    private String date; // Format: 08-29-2024
    private String checkInTime; // Format: 9:58 PM
    private String dropOffDriverName;
    private String checkOutTime; // Format: 9:58 PM
    private String pickUpDriverName;
}
