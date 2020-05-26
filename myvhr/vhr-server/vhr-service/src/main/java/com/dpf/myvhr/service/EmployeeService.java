package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.EmployeeMapper;
import com.dpf.myvhr.model.Employee;
import com.dpf.myvhr.model.MailConstants;
import com.dpf.myvhr.model.MailSendLog;
import com.dpf.myvhr.model.RespPageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author dpf
 * @create 2020-04-30 13:36
 * @email 446933040@qq.com
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MailSendLogService mailSendLogService;

    public final static Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    DecimalFormat decimalFormat = new DecimalFormat("##.00");

    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] beginDateScope) {

        if (page != null && size != null) {
            page = (page - 1) * size;
        }

       List<Employee> employees = employeeMapper.getEmployeeByPage(page,size,employee,beginDateScope);
       long total = employeeMapper.getTotal(employee,beginDateScope) -1;
       RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(employees);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    @Transactional
    public int addEmp(Employee employee) {

       double month = (Double.parseDouble(yearFormat.format(employee.getEndContract())) - Double.parseDouble(yearFormat.format(employee.getBeginDate())))*12
               + (Double.parseDouble(monthFormat.format(employee.getEndContract()))-Double.parseDouble(monthFormat.format(employee.getBeginDate())));
       employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
        int result = employeeMapper.insertSelective(employee);
        if(result==1){
            Employee emp = employeeMapper.getEmployeeById(employee.getId());
            logger.info(emp.toString());

            //生成消息的唯一ID
            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog = new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setEmpId(emp.getId());
            mailSendLog.setCount(0);
            mailSendLog.setTryTime(new Date(System.currentTimeMillis() + 1000 * 60 * MailConstants.MSG_TIMEOUT));
            mailSendLogService.insert(mailSendLog);
            rabbitTemplate.convertAndSend(MailConstants.MAIL_QUEUE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));
        }

        return result;
    }


    public Integer getMaxWorkID() {
        return employeeMapper.getMaxWorkID();
    }

    public Integer delEmpById(Integer [] ids) {
        return employeeMapper.deleteEmpById(ids);
    }

    public Integer updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    public Integer addEmps(List<Employee> list) {
        return employeeMapper.addEmps(list);
    }

    public RespPageBean getEmployeeByPageWithSalary(Integer page, Integer size) {

        if (page != null && size != null) {
            page = (page - 1) * size;
        }

        List<Employee> list = employeeMapper.getEmployeeByPageWithSalary(page, size);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(list);
        respPageBean.setTotal(employeeMapper.getTotal(null, null));
        return respPageBean;
    }

    public Integer updateEmployeeSalaryById(Integer eid, Integer sid) {

        return employeeMapper.updateEmployeeSalaryById(eid,sid);
    }

    public Employee getEmployeeById(Integer empId) {
        return employeeMapper.getEmployeeById(empId);
    }
}
