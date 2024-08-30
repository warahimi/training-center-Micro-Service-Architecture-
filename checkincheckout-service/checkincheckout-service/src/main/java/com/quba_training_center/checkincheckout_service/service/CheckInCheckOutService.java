package com.quba_training_center.checkincheckout_service.service;

import com.quba_training_center.checkincheckout_service.entity.Student;
import com.quba_training_center.checkincheckout_service.utilities.DateAndTimeUtil;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CheckInCheckOutService {

    //@Autowired
    private RestTemplate restTemplate;

    //@Autowired
    private EntityManager entityManager;
    //@Autowired
    private DateAndTimeUtil dateAndTimeUtil;

    public CheckInCheckOutService(RestTemplate restTemplate, EntityManager entityManager, DateAndTimeUtil dateAndTimeUtil) {
        this.restTemplate = restTemplate;
        this.entityManager = entityManager;
        this.dateAndTimeUtil = dateAndTimeUtil;
    }

//    @Transactional
//    public void initializeCheckInCheckOutData() {
//        String date = dateAndTimeUtil.getDate();
//        String tableName = "students_checkin_checkout_" + date.replace("-", "_");
//
//        // 1. Dynamically create the table
//        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
//                "id INT PRIMARY KEY AUTO_INCREMENT, " +
//                "first_name VARCHAR(255), " +
//                "last_name VARCHAR(255), " +
//                "class_name VARCHAR(255), " +
//                "date VARCHAR(255), " +
//                "check_in_time VARCHAR(255), " +
//                "drop_off_driver_name VARCHAR(255), " +
//                "check_out_time VARCHAR(255), " +
//                "pick_up_driver_name VARCHAR(255)" +
//                ")";
//        entityManager.createNativeQuery(createTableSQL).executeUpdate();
//
//        // 2. Pull student data from student-service
//        String studentServiceUrl = "http://student-service/student";
//        List<Student> students = restTemplate.getForObject(studentServiceUrl, List.class);
//
//        // 3. Insert students with initial null values for check-in and check-out times
//        for (Student student : students) {
//            String insertSQL = "INSERT INTO " + tableName + " (first_name, last_name, class_name, date) VALUES (?, ?, ?, ?)";
//            entityManager.createNativeQuery(insertSQL)
//                    .setParameter(1, student.getFirstName())
//                    .setParameter(2, student.getLastName())
//                    .setParameter(3, student.getClassName())
//                    .setParameter(4, date)
//                    .executeUpdate();
//        }
//    }

    @Transactional
    public void initializeCheckInCheckOutData() {
        String date = dateAndTimeUtil.getDate();
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        // 1. Dynamically create the table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "first_name VARCHAR(255), " +
                "last_name VARCHAR(255), " +
                "class_name VARCHAR(255), " +
                "date VARCHAR(255), " +
                "check_in_time VARCHAR(255), " +
                "drop_off_driver_name VARCHAR(255), " +
                "check_out_time VARCHAR(255), " +
                "pick_up_driver_name VARCHAR(255)" +
                ")";
        entityManager.createNativeQuery(createTableSQL).executeUpdate();

        // 2. Pull student data from student-service
        String studentServiceUrl = "http://student-service/student";  // Verify this URL
        try {
            ResponseEntity<List<Student>> response = restTemplate.exchange(
                    studentServiceUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Student>>() {}
            );
            List<Student> students = response.getBody();

            // 3. Insert students with initial null values for check-in and check-out times
            for (Student student : students) {
                String insertSQL = "INSERT INTO " + tableName + " (first_name, last_name, class_name, date) VALUES (?, ?, ?, ?)";
                entityManager.createNativeQuery(insertSQL)
                        .setParameter(1, student.getFirstName())
                        .setParameter(2, student.getLastName())
                        .setParameter(3, student.getClassName())
                        .setParameter(4, date)
                        .executeUpdate();
            }
        } catch (HttpClientErrorException e) {
            // Log the error and throw a custom exception if needed
            System.out.println("Error calling student-service: " + e.getMessage());
            throw new RuntimeException("Error calling student-service", e);
        } catch (ClassCastException e) {
            // Handle class cast exceptions
            System.out.println("Class cast error: " + e.getMessage());
            throw new RuntimeException("Class cast error", e);
        }
    }
}
