package com.quba_training_center.attendance_service.controller;

import com.quba_training_center.attendance_service.entity.AttendanceStudent;
import com.quba_training_center.attendance_service.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * Endpoint to get all AttendanceStudent records for the current date.
     * @return A ResponseEntity containing the list of students, or a 404 status if no records are found.
     */
    @GetMapping("/current")
    public ResponseEntity<List<AttendanceStudent>> getAllAttendanceStudentsForCurrentDate() {
        List<AttendanceStudent> students = attendanceService.getAllAttendanceStudentsForCurrentDate();
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get all AttendanceStudent records for a specific date.
     * @param date The date for which to retrieve the attendance records.
     * @return A ResponseEntity containing the list of students, or a 404 status if no records are found.
     */
    @GetMapping("/{date}")
    public ResponseEntity<List<AttendanceStudent>> getAllAttendanceStudentsForSpecificDate(@PathVariable String date) {
        List<AttendanceStudent> students = attendanceService.getAllAttendanceStudentsForSpecificDate(date);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to create attendance records for the current date.
     * @return A success message.
     */
    @GetMapping("/create")
    public String create() {
        attendanceService.initializeAttendanceStudentData();
        return "Successfully created";
    }

    /**
     * Endpoint to mark a student as present by ID.
     * @param id The ID of the student to mark as present.
     * @return A ResponseEntity indicating success or failure.
     */
    @PutMapping("/present/{id}")
    public ResponseEntity<String> markStudentAsPresent(@PathVariable int id) {
        attendanceService.markStudentAsPresent(id);
        return new ResponseEntity<>("Student marked as present.", HttpStatus.OK);
    }

    /**
     * Endpoint to mark a student as absent by ID.
     * @param id The ID of the student to mark as absent.
     * @return A ResponseEntity indicating success or failure.
     */
    @PutMapping("/absent/{id}")
    public ResponseEntity<String> markStudentAsAbsent(@PathVariable int id) {
        attendanceService.markStudentAsAbsent(id);
        return new ResponseEntity<>("Student marked as absent.", HttpStatus.OK);
    }

    /**
     * Endpoint to get all students by their class name.
     * @param className The class name to filter students.
     * @return A ResponseEntity containing the list of students, or a 404 status if no records are found.
     */
    @GetMapping("/class/{className}")
    public ResponseEntity<List<AttendanceStudent>> getAllStudentsByClassName(@PathVariable String className) {
        List<AttendanceStudent> students = attendanceService.getAllStudentsByClassName(className);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get all present students in a specific class.
     * @param className The class name to filter students.
     * @return A ResponseEntity containing the list of present students, or a 404 status if no records are found.
     */
    @GetMapping("/class/{className}/present")
    public ResponseEntity<List<AttendanceStudent>> getPresentStudentsByClassName(@PathVariable String className) {
        List<AttendanceStudent> students = attendanceService.getPresentStudentsByClassName(className);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get all absent students in a specific class.
     * @param className The class name to filter students.
     * @return A ResponseEntity containing the list of absent students, or a 404 status if no records are found.
     */
    @GetMapping("/class/{className}/absent")
    public ResponseEntity<List<AttendanceStudent>> getAbsentStudentsByClassName(@PathVariable String className) {
        List<AttendanceStudent> students = attendanceService.getAbsentStudentsByClassName(className);
        if (!students.isEmpty()) {
            return new ResponseEntity<>(students, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
