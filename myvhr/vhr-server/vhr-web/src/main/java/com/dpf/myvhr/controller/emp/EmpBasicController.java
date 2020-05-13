package com.dpf.myvhr.controller.emp;

import com.dpf.myvhr.model.*;
import com.dpf.myvhr.service.*;
import com.dpf.myvhr.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author dpf
 * @create 2020-04-30 13:36
 * @email 446933040@qq.com
 */
@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PositionService positionService;
    @Autowired
    PoliticsstatusService politicsstatusService;
    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    NationService nationService;
    @Autowired
    DepartmentService departmentService;


    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size, Employee emp,Date[] beginDateScope){

        return employeeService.getEmployeeByPage(page, size, emp,beginDateScope);
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if(employeeService.addEmp(employee) == 1){
            return RespBean.ok("添加成功！");
        }
        return RespBean.error("添加失败!");
    }

    @DeleteMapping("/{ids}")
    public RespBean delEmpById(@PathVariable("ids") Integer [] ids){
        if(employeeService.delEmpById(ids)==ids.length){
            return RespBean.ok("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){

        if(employeeService.updateEmp(employee)==1){
            return RespBean.ok("更新成功！");
        }

        return RespBean.error("更新失败！");
    }



    /**
     * 获取所有政治面貌
     * @return
     */
    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){

        return politicsstatusService.getAllPoliticsstatus();
    }

    /**
     * 获取所有名族
     * @return
     */
    @GetMapping("/nations")
    public List<Nation> getAllNation(){

        return nationService.getAllNation();
    }

    /**
     * 获取所有职位
     * @return
     */
    @GetMapping("/positions")
    public List<Position> getAllPositions(){
        return positionService.getAllPositions();
    }

    /**
     * 获取所有职称
     * @returns
     */
    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevel(){
        return jobLevelService.getAllJobLevel();
    }

    /**
     * 获取最大工号
     * @return
     */
    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkID(){
        RespBean respBean = RespBean.build()
                .setStatus(200)
                .setObj(String.format("%08d",employeeService.getMaxWorkID()+1));
        return respBean;
    }

    /**
     * 获取所有部门
     * @return
     */
    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }


    /**
     * Excel导出员工数据
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData(){
        List<Employee> list = (List<Employee>) employeeService.getEmployeeByPage(null, null, new Employee(), null).getData();
        return POIUtils.employee2Excel(list);
    }

    @PostMapping("/import")
    public RespBean importData(MultipartFile file){
        List<Employee> list = POIUtils.excel2Employee(file, nationService.getAllNation(), politicsstatusService.getAllPoliticsstatus(), departmentService.getAllDepartmentsWithOutChildren(), positionService.getAllPositions(), jobLevelService.getAllJobLevel());

        if(employeeService.addEmps(list) == list.size()){
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }
}
