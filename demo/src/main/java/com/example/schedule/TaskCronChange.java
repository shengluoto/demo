package com.example.schedule;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

//@Configuration
//@EnableScheduling
public class TaskCronChange implements SchedulingConfigurer {

    private String cron;

    public TaskCronChange() {
        // 默认情况是：每5秒执行一次
        cron = "0/5 * * * * ? ";
        // 开启新线程模拟外部更改了任务执行周期
        new Thread(() -> {
            try {
                Thread.sleep(15000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 修改为：每10秒执行一次
            cron = "0/10 * * * * ?";
            System.err.println("cron change to:" + cron);
        }).start();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        // 在Runnable中执行业务逻辑代码
        Runnable task = new Runnable() {
            @Override
            // 任务逻辑代码部分
            public void run() {
                System.out.println("TaskCronChange task is running ... " + new Date());
            }
        };
        // 在Trigger修改定时任务的执行周期
        Trigger trigger = new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 任务触发，可修改任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExec = trigger.nextExecutionTime(triggerContext);
                return nextExec;
            }
        };
        taskRegistrar.addTriggerTask(task, trigger);
    }

}
