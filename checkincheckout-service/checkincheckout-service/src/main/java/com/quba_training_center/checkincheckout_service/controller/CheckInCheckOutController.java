package com.quba_training_center.checkincheckout_service.controller;

import com.quba_training_center.checkincheckout_service.entity.CheckInCheckOutStudent;
import com.quba_training_center.checkincheckout_service.service.CheckInCheckOutService;
import com.quba_training_center.checkincheckout_service.utilities.DateAndTimeUtil;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/checkincheckout")
public class CheckInCheckOutController {

    //@Autowired
    private EntityManager entityManager;

    private CheckInCheckOutService service;

    //@Autowired
    private DateAndTimeUtil dateAndTimeUtil;

    public CheckInCheckOutController(EntityManager entityManager,
                                     DateAndTimeUtil dateAndTimeUtil,
                                     CheckInCheckOutService service) {
        this.entityManager = entityManager;
        this.dateAndTimeUtil = dateAndTimeUtil;
        this.service=service;
    }


    //Put http://localhost:8084/api/checkincheckout/checkin/1/Wahid
    @PutMapping("/checkin/{id}/{driverName}")
    @Transactional
    public ResponseEntity<?> updateCheckIn(@PathVariable Integer id,
                                           @PathVariable String driverName) {
        String date = dateAndTimeUtil.getDate();
        String time = dateAndTimeUtil.getTime();
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        String updateSQL = "UPDATE " + tableName + " SET " +
                "check_in_time = ?, drop_off_driver_name = ? WHERE id = ?";
        entityManager.createNativeQuery(updateSQL)
                .setParameter(1, time)
                .setParameter(2, driverName)
                .setParameter(3, id)
                .executeUpdate();

        return ResponseEntity.ok("Update Successful");
    }


    // PUT http://localhost:8084/api/checkincheckout/checkout/1/Wahid
    @PutMapping("/checkout/{id}/{driverName}")
    @Transactional
    public ResponseEntity<?> updateCheckOut(@PathVariable Integer id,
                                            @PathVariable String driverName) {
        String date = dateAndTimeUtil.getDate();
        String time = dateAndTimeUtil.getTime();
        String tableName = "students_checkin_checkout_" + date.replace("-", "_");

        String updateSQL = "UPDATE " + tableName + " SET " +
                "check_out_time = ?, pick_up_driver_name = ? WHERE id = ?";
        entityManager.createNativeQuery(updateSQL)
                .setParameter(1, time)
                .setParameter(2, driverName)
                .setParameter(3, id)
                .executeUpdate();

        return ResponseEntity.ok("Update Successful");
    }

    @GetMapping("/create")
    public String create()
    {
        service.initializeCheckInCheckOutData();
        return "sucess create";
    }

   /*
   To retrieve a CheckInCheckOutStudent by id, we can create a method in our
    CheckInCheckOutController that uses the EntityManager to execute a query and return the result.
    Since the table name is dynamic and includes the date, you will need to generate the table name
     based on the current date or a provided date.

     the typical approach using the CheckInCheckOutStudentRepository is not going to work in this scenario
      this approach would be much more straightforward if the table name is not dynamically generated based on the date.
      However, if the table name is dynamic (e.g., students_checkin_checkout_08_29_2024), then the JpaRepository approach will
      not work directly because JPA expects the table name to be fixed and defined at compile-time in the entity class.

        Generating the Table Name: The table name is generated using the current date (you could modify this to use a specific date
         if needed).

        Query Execution: The method uses a native SQL query to fetch the record with the specified ID from the dynamically named table.

        Handling the Result: If the record is found, it's returned with an HTTP status 200 OK. If not, a 404 Not Found status is returned.
    */

    // http://localhost:8081/api/checkincheckout/student/3

    /**
     * Endpoint to get a CheckInCheckOutStudent by ID for the current date.
     * @param id The ID of the student.
     * @return A ResponseEntity containing the student if found, or a 404 status if not found.
     */
    @GetMapping("/student/{id}")
    public ResponseEntity<?> getCheckInCheckOutStudentById(@PathVariable Integer id) {
        CheckInCheckOutStudent student = service.getCheckInCheckOutStudentById(id);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return new ResponseEntity<>("Student not found for ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get a CheckInCheckOutStudent by ID for a specific date.
     * @param id The ID of the student.
     * @param date The date for which to retrieve the student record.
     * @return A ResponseEntity containing the student if found, or a 404 status if not found.
     */
    @GetMapping("/student/{id}/{date}")
    public ResponseEntity<?> getCheckInCheckOutStudentByIdAndDate(@PathVariable Integer id, @PathVariable String date) {
        CheckInCheckOutStudent student = service.getCheckInCheckOutStudentByIdAndDate(id, date);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return new ResponseEntity<>("Student not found for ID: " + id, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get CheckInCheckOutStudent by first name with dynamic table name
    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<List<CheckInCheckOutStudent>> getStudentsByFirstName(@PathVariable String firstName) {
        List<CheckInCheckOutStudent> students = service.getStudentsByFirstName(firstName);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get CheckInCheckOutStudent by last name with dynamic table name
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<List<CheckInCheckOutStudent>> getStudentsByLastName(@PathVariable String lastName) {
        List<CheckInCheckOutStudent> students = service.getStudentsByLastName(lastName);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
