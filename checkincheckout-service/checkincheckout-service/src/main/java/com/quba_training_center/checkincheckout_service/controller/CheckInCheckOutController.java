package com.quba_training_center.checkincheckout_service.controller;

import com.quba_training_center.checkincheckout_service.service.CheckInCheckOutService;
import com.quba_training_center.checkincheckout_service.utilities.DateAndTimeUtil;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
}
