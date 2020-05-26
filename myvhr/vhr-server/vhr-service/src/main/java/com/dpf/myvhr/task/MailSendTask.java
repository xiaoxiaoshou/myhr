package com.dpf.myvhr.task;

import com.dpf.myvhr.model.Employee;
import com.dpf.myvhr.model.MailConstants;
import com.dpf.myvhr.model.MailSendLog;
import com.dpf.myvhr.service.EmployeeService;
import com.dpf.myvhr.service.MailSendLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author dpf
 * @create 2020-05-15 21:13
 * @email 446933040@qq.com
 */
@Component
public class MailSendTask {
    @Autowired
    MailSendLogService mailSendLogService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    EmployeeService employeeService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void mailReSendTask(){
        List<MailSendLog> logs = mailSendLogService.getMailSendLogsByStatus();
        if (logs == null || logs.size() == 0){
            return;
        }
        logs.forEach(mailSendLog -> {
            if(mailSendLog.getCount()>=3){
                //如果重试次数大于等于3直接设置为失败
                mailSendLogService.updateMailSendLogStatus(mailSendLog.getMsgId(),2);
            }else{
                mailSendLogService.updateCount(mailSendLog.getMsgId(),new Date());
                Employee employee = employeeService.getEmployeeById(mailSendLog.getEmpId());
                rabbitTemplate.convertAndSend(MailConstants.MAIL_ROUTING_KEY_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,employee,new CorrelationData(mailSendLog.getMsgId()));
            }
        });
    }
}
