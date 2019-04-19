package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.JobService;
import com.example.task.MessageRemindJobTask;


@RestController
@RequestMapping("weixin/send")
public class SendController {

	@Autowired
	private JobService jobService;
	
    @PostMapping("/sendTemplateData2")
    public void sendTemplateData(String openID) {
        System.out.println(openID);
    }
    
    @PostMapping("/schedul")
    public void sendschedul(String crom) {
    	jobService.deleteJob("my_injtest", "my_injtest");
    	jobService.scheduleCronJob("my_injtest", "my_injtest", MessageRemindJobTask.class, crom, null, true);
    }
}
