package com.dpf.myvhr.controller.salary;

import com.dpf.myvhr.model.Employee;
import com.dpf.myvhr.model.RespBean;
import com.dpf.myvhr.model.RespPageBean;
import com.dpf.myvhr.model.Salary;
import com.dpf.myvhr.service.EmployeeService;
import com.dpf.myvhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dpf
 * @create 2020-05-06 23:08
 * @email 446933040@qq.com
 */

@RestController
@RequestMapping("/salary/sobcfg")
public class SobConfigController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SalaryService salaryService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeByPageWithSalary(page, size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(Integer eid, Integer sid) {
       Integer result = employeeService.updateEmployeeSalaryById(eid,sid);
       if(result == 1 || result == 2){
           return RespBean.ok("更新成功！");
       }
        return RespBean.error("更新失败！");
    }
}
