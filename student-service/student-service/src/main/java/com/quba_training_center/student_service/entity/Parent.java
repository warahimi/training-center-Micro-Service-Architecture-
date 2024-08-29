package com.quba_training_center.student_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Parent {
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String address;
    private String email;
}
