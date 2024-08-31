package com.quba_training_center.attendance_service.utilities;

import com.quba_training_center.attendance_service.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
    @Component: The AttendanceScheduler class is marked as a Spring component so that it is automatically detected and managed by Spring's container.

    @Autowired: The AttendanceService is injected into the scheduler, allowing the scheduler to call the service methods.

    @Scheduled(cron = "0 0 0 * * SAT,SUN"): This cron expression schedules the method to run at midnight (00:00) on Saturdays and Sundays.

    "0 0 0 * * SAT,SUN":
    0 0 0: Run at midnight (00:00).
    * *: Every day of the month and every month.
    SAT,SUN: Only on Saturday and Sunday.
 */
@Component
public class AttendanceScheduler {

    @Autowired
    private AttendanceService attendanceService;

    /**
     * Scheduler to initialize attendance data every Saturday and Sunday at 00:00.
     */
    @Scheduled(cron = "0 0 0 * * SAT,SUN")
    public void scheduleAttendanceInitialization() {
        attendanceService.initializeAttendanceStudentData();
    }
}