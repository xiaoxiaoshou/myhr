package com.dpf.myvhr.controller.system.basic;

import com.dpf.myvhr.model.Department;
import com.dpf.myvhr.model.RespBean;
import com.dpf.myvhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author dpf
 * @create 2020-04-20 20:46
 * @email 446933040@qq.com
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    /**
     * 获取部门及子部门
     * @return
     */
    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    /**
     * 添加部门
     * @param department
     * @return
     */
    @PostMapping("/")
    public RespBean addDep(@RequestBody Department department){
        departmentService.addDep(department);
        if (department.getResult() == 1){
            return RespBean.ok("添加成功",department);
        }

        return RespBean.error("添加失败！");
    }

    /**
     * 非存储过程添加
     * @param department
     * @return
     */
    @PostMapping("/add")
    public RespBean addDep2(@RequestBody Department department){

        Department dep2 = departmentService.addDep2(department);
        if(dep2.getId()!=null){
            return RespBean.ok("添加成功！",dep2);
        }

        return RespBean.error("添加失败");
    }


    @DeleteMapping("/{id}")
    public RespBean deleteDepById(@PathVariable Integer id){

        Department dep = new Department();
        dep.setId(id);
        departmentService.deleteDepById(dep);
        if(dep.getResult() == -2){
            return  RespBean.error("该部门下有子部门，删除失败");
        }else if(dep.getResult() == -1){
            return RespBean.error("该部门下油员工，删除失败");
        }else if(dep.getResult() == 1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败！");
    }

    /**
     * 非存储过程删除
     * @param id
     * @return
     */
    @DeleteMapping("/del/{id}")
    public RespBean deleteDepById2(@PathVariable Integer id){
        Department dep = new Department();
        dep.setId(id);
        return departmentService.deleteDepById2(dep);
    }


}
