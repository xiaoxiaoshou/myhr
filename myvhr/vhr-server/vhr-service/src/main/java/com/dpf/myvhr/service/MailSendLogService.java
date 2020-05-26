package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.MailSendLogMapper;
import com.dpf.myvhr.model.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author dpf
 * @create 2020-05-15 17:12
 * @email 446933040@qq.com
 */
@Service
public class MailSendLogService {

    @Autowired
    MailSendLogMapper mailSendLogMapper;

    public Integer updateMailSendLogStatus(String msgId, Integer status) {
        return mailSendLogMapper.updateMailSendLogStatus(msgId, status);
    }

    public Integer insert(MailSendLog mailSendLog) {
        return mailSendLogMapper.insert(mailSendLog);
    }

    public List<MailSendLog> getMailSendLogsByStatus() {
        return mailSendLogMapper.getMailSendLogsByStatus();
    }

    public Integer updateCount(String msgId, Date date) {
        return mailSendLogMapper.updateCount(msgId,date);
    }

}
