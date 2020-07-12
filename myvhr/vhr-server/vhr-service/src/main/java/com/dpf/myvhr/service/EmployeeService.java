package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.EmployeeMapper;
import com.dpf.myvhr.model.Employee;
import com.dpf.myvhr.model.RespPageBean;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author dpf
 * @create 2020-04-30 13:36
 * @email 446933040@qq.com
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

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

    public int addEmp(Employee employee) {

       double month = (Double.parseDouble(yearFormat.format(employee.getEndContract())) - Double.parseDouble(yearFormat.format(employee.getBeginDate())))*12
               + (Double.parseDouble(monthFormat.format(employee.getEndContract()))-Double.parseDouble(monthFormat.format(employee.getBeginDate())));
       employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
       return employeeMapper.insertSelective(employee);
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
}
