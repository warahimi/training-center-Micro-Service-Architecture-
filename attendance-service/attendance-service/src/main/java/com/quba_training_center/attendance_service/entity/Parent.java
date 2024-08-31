package com.quba_training_center.attendance_service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Parent {
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNumber;
    private String address;
    private String email;
}
