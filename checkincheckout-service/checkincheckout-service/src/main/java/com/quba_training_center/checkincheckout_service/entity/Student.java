package com.quba_training_center.checkincheckout_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {
    private Integer id;
    private String firstName;
    private String lastName;
    private String fullName;
    private List<Parent> parents;
    private String dob;
    private String gender;
    private String className;
}