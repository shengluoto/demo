package com.example.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//@Configuration
//@EnableScheduling
//@Service
public class ScheduleTaskService {
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0/10 * * * * ? ")
    public void task1() {
        System.out.println("MyTask1Cron:" + sdf.format(new Date()));
    }

    @Scheduled(fixedRate = 6000)
    public void task2() { 
        System.out.println("每隔6s固定时间执行:" + sdf.format(new Date()));
    }
}
