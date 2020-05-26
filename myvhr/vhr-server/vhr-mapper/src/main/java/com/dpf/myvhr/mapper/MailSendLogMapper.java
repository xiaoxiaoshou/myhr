package com.dpf.myvhr.mapper;

import com.dpf.myvhr.model.MailSendLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author dpf
 * @create 2020-05-15 17:13
 * @email 446933040@qq.com
 */
public interface MailSendLogMapper {

    Integer updateMailSendLogStatus(@Param("msgId") String msgId, @Param("status") Integer status);

    Integer insert(MailSendLog mailSendLog);

    List<MailSendLog> getMailSendLogsByStatus();

    Integer updateCount(@Param("msgId") String msgId, @Param("date") Date date);

}
