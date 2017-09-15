package com.example.controller;

//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.entity.RequestMessage;
import com.example.entity.ResponseMessage;

@Controller
public class WsController {
//    @MessageMapping("/ws")
//    @SendTo("/topic/getResponse")
    public ResponseMessage say(RequestMessage message) {
        System.out.println(message.getName());
        return new ResponseMessage("welcome," + message.getName() + " !");
    }
}
