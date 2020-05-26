package com.dpf.myvhr.mailserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

@SpringBootTest
class MailServerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    public void sendSimpleMail(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("邮件对象：这是一封测试邮件");
        message.setFrom("446933040@qq.com");
        message.setTo("446933040@qq.com");
        message.setCc("446933040@qq.com");
        message.setBcc("446933040@qq.com");
        message.setSentDate(new Date());
        message.setText("测试邮件正文");
        javaMailSender.send(message);
    }
}
