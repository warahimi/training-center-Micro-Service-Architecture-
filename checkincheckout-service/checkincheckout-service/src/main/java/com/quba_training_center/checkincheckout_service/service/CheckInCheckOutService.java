package com.quba_training_center.checkincheckout_service.service;

import com.quba_training_center.checkincheckout_service.entity.CheckInCheckOutStudent;
import com.quba_training_center.checkincheckout_service.entity.Student;
import com.quba_training_center.checkincheckout_service.repository.CheckInCheckOutStudentRepository;
import com.quba_training_center.checkincheckout_service.utilities.DateAndTimeUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CheckInCheckOutService {

    //@Autowired
    private RestTemplate restTemplate;

    //@Autowired
    private EntityManager entityManager;
    //@Autowired
    private DateAndTimeUtil dateAndTimeUtil;
    @Autowired
    private CheckInCheckOutStudentRepository repository;

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
        String studentServiceUrl = "http://student-service/api/student";
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

    public Optional<CheckInCheckOutStudent> getStudentById(int id)
    {
        return repository.findById(id);
    }



    /**
     * Fetches a CheckInCheckOutStudent by ID for the current date.
     * @param id The ID of the student.
     * @return The student if found; otherwise null.
     */
    public CheckInCheckOutStudent getCheckInCheckOutStudentById(int id) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE id = ?";
        try {
            return (CheckInCheckOutStudent) entityManager.createNativeQuery(selectSQL, CheckInCheckOutStudent.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No student found with the given ID
        }
    }

    /**
     * Fetches a CheckInCheckOutStudent by ID for a specific date.
     * @param id The ID of the student.
     * @param date The date for which to retrieve the student record.
     * @return The student if found; otherwise null.
     */
    public CheckInCheckOutStudent getCheckInCheckOutStudentByIdAndDate(int id, String date) {
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE id = ?";
        try {
            return (CheckInCheckOutStudent) entityManager.createNativeQuery(selectSQL, CheckInCheckOutStudent.class)
                    .setParameter(1, id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // No student found with the given ID
        }
    }

    // Service method to get students by first name with dynamic table name
    public List<CheckInCheckOutStudent> getStudentsByFirstName(String firstName) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE first_name = :firstName";
        Query query = entityManager.createNativeQuery(selectSQL, CheckInCheckOutStudent.class);
        query.setParameter("firstName", firstName);

        return query.getResultList();
    }

    // Service method to get students by last name with dynamic table name
    public List<CheckInCheckOutStudent> getStudentsByLastName(String lastName) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE last_name = :lastName";
        Query query = entityManager.createNativeQuery(selectSQL, CheckInCheckOutStudent.class);
        query.setParameter("lastName", lastName);

        return query.getResultList();
    }
}
