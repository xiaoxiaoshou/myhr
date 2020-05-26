package com.dpf.myvhr.config;

import com.dpf.myvhr.model.MailConstants;
import com.dpf.myvhr.service.MailSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.event.FocusEvent;

/**
 * @author dpf
 * @create 2020-05-15 17:22
 * @email 446933040@qq.com
 */
@Configuration
public class RabbitConfig {

    public final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    MailSendLogService mailSendLogService;

    @Bean
    RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        rabbitTemplate.setConfirmCallback((data,ack, cause)->{
            String msgId = data.getId();
            if(ack){
                logger.info(msgId+"：消息发送成功");
                mailSendLogService.updateMailSendLogStatus(msgId,1);
            }else {
                logger.info(msgId + ":消息发送失败");
            }
        });
        return rabbitTemplate;
    }

    @Bean
    Queue mailQueue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME,true);
    }

    @Bean
    DirectExchange mailExchange(){
        return new DirectExchange(MailConstants.MAIL_QUEUE_NAME,true,false);
    }

    @Bean
    Binding mailBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }

}
