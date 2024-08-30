package com.quba_training_center.checkincheckout_service.utilities;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DateAndTimeUtil {
    public String getDate()
    {
        // Get today's date
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = today.format(dateFormatter);
        //System.out.println("Today's Date: " + formattedDate);
        return formattedDate;
    }
    public String getTime()
    {
        // Get the current time
        LocalTime now = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        String formattedTime = now.format(timeFormatter);
        //System.out.println("Current Time: " + formattedTime);
        return formattedTime;
    }
}
