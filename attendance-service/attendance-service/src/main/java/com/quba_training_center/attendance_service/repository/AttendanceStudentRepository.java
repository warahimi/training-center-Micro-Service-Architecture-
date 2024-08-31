package com.quba_training_center.attendance_service.repository;

import com.quba_training_center.attendance_service.entity.AttendanceStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceStudentRepository extends JpaRepository<AttendanceStudent,Integer> {
}
