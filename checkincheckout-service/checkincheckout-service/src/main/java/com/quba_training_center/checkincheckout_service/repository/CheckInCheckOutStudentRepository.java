package com.quba_training_center.checkincheckout_service.repository;

import com.quba_training_center.checkincheckout_service.entity.CheckInCheckOutStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckInCheckOutStudentRepository extends JpaRepository<CheckInCheckOutStudent, Integer> {
    // You can add custom queries if needed
}
