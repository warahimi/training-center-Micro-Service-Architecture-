package com.quba_training_center.checkincheckout_service.utilities;

import com.quba_training_center.checkincheckout_service.service.CheckInCheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckInCheckOutScheduler {

    @Autowired
    private CheckInCheckOutService checkInCheckOutService;

    // Run every Saturday and Sunday at 00:00
    @Scheduled(cron = "0 0 0 * * SAT,SUN")
    public void scheduleCheckInCheckOutInitialization() {
        checkInCheckOutService.initializeCheckInCheckOutData();
    }
}
