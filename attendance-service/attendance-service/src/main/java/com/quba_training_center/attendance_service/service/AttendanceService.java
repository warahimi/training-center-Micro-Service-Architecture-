package com.quba_training_center.attendance_service.service;

import com.quba_training_center.attendance_service.entity.AttendanceStudent;
import com.quba_training_center.attendance_service.entity.Student;
import com.quba_training_center.attendance_service.utilities.DateAndTimeUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired

    private EntityManager entityManager;

    @Autowired
    private DateAndTimeUtil dateAndTimeUtil;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Scheduled task to initialize attendance data every day.
     * Fetches student data from student-service and creates an attendance table for the current date.
     */
    //@Scheduled(cron = "0 0 6 * * ?") // Schedule to run every day at 6 AM
    @Transactional
    public void initializeAttendanceStudentData() {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        // Create the table if it doesn't exist
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "first_name VARCHAR(255), " +
                "last_name VARCHAR(255), " +
                "class_name VARCHAR(255), " +
                "is_present BOOLEAN, " +  // Removed the misplaced closing parenthesis
                "date VARCHAR(255))";  // Moved date column inside the column definitions
        entityManager.createNativeQuery(createTableSQL).executeUpdate();

        // Fetch student data from student-service
        String studentServiceUrl = "http://student-service/api/student";
        List<LinkedHashMap<String, Object>> response = restTemplate.getForObject(studentServiceUrl, List.class);

        List<Student> students = response.stream()
                .map(map -> {
                    Student student = new Student();
                    student.setFirstName((String) map.get("firstName"));
                    student.setLastName((String) map.get("lastName"));
                    student.setClassName((String) map.get("className"));
                    return student;
                })
                .collect(Collectors.toList());

        // Insert the fetched student data into the newly created table with is_present as false
        for (Student student : students) {
            String insertSQL = "INSERT INTO " + tableName + " (first_name, last_name, class_name, is_present, date) VALUES (?, ?, ?, ?, ?)";
            entityManager.createNativeQuery(insertSQL)
                    .setParameter(1, student.getFirstName())
                    .setParameter(2, student.getLastName())
                    .setParameter(3, student.getClassName())
                    .setParameter(4, false)  // Initialize as not present
                    .setParameter(5, date)  // Set the date value
                    .executeUpdate();
        }
    }
    /**
     * Retrieves all AttendanceStudent records for the current date.
     * @return A list of AttendanceStudent records.
     */
    public List<AttendanceStudent> getAllAttendanceStudentsForCurrentDate() {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName;
        return entityManager.createNativeQuery(selectSQL, AttendanceStudent.class).getResultList();
    }

    /**
     * Retrieves all AttendanceStudent records for a specific date.
     * @param date The date for which to retrieve the attendance records.
     * @return A list of AttendanceStudent records.
     */
    public List<AttendanceStudent> getAllAttendanceStudentsForSpecificDate(String date) {
        String tableName = "attendance_student_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName;
        return entityManager.createNativeQuery(selectSQL, AttendanceStudent.class).getResultList();
    }
    @Transactional
    public void markStudentAsPresent(int id) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        String updateSQL = "UPDATE " + tableName + " SET is_present = true WHERE id = ?";
        entityManager.createNativeQuery(updateSQL)
                .setParameter(1, id)
                .executeUpdate();
    }

    @Transactional
    public void markStudentAsAbsent(int id) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        String updateSQL = "UPDATE " + tableName + " SET is_present = false WHERE id = ?";
        entityManager.createNativeQuery(updateSQL)
                .setParameter(1, id)
                .executeUpdate();
    }

    /**
     * Retrieves all students by their class name.
     * @param className The class name to filter students.
     * @return A list of AttendanceStudent records.
     */
    @Transactional
    public List<AttendanceStudent> getAllStudentsByClassName(String className) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE class_name = ?";
        return entityManager.createNativeQuery(selectSQL, AttendanceStudent.class)
                .setParameter(1, className)
                .getResultList();
    }

    /**
     * Retrieves all present students in a specific class.
     * @param className The class name to filter students.
     * @return A list of present AttendanceStudent records.
     */
    @Transactional
    public List<AttendanceStudent> getPresentStudentsByClassName(String className) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE class_name = ? AND is_present = true";
        return entityManager.createNativeQuery(selectSQL, AttendanceStudent.class)
                .setParameter(1, className)
                .getResultList();
    }

    /**
     * Retrieves all absent students in a specific class.
     * @param className The class name to filter students.
     * @return A list of absent AttendanceStudent records.
     */
    @Transactional
    public List<AttendanceStudent> getAbsentStudentsByClassName(String className) {
        String date = dateAndTimeUtil.getDate();
        String tableName = "attendance_student_" + date.replace("-", "_");

        String selectSQL = "SELECT * FROM " + tableName + " WHERE class_name = ? AND is_present = false";
        return entityManager.createNativeQuery(selectSQL, AttendanceStudent.class)
                .setParameter(1, className)
                .getResultList();
    }

}
