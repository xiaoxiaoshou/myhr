package com.dpf.myvhr.controller;

import com.dpf.myvhr.model.ChatMsg;
import com.dpf.myvhr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.lang.invoke.ConstantCallSite;
import java.util.Date;

/**
 * @author dpf
 * @create 2020-05-09 22:34
 * @email 446933040@qq.com
 */
@Controller
public class WsController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMsg chatMsg){

        Hr hr =  ((Hr) authentication.getPrincipal());
       chatMsg.setFrom(hr.getUsername());
       chatMsg.setDate(new Date());
       chatMsg.setFromNickname(hr.getName());
       simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);    }
}
