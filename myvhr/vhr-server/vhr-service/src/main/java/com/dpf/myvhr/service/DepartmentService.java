package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.DepartmentMapper;
import com.dpf.myvhr.mapper.EmployeeMapper;
import com.dpf.myvhr.model.Department;
import com.dpf.myvhr.model.Employee;
import com.dpf.myvhr.model.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dpf
 * @create 2020-04-20 20:48
 * @email 446933040@qq.com
 */
@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    public void deleteDepById(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    public List<Department> getAllDepartmentsWithOutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }

    public void addDep(Department department) {
        department.setEnabled(true);
        departmentMapper.addDep(department);
    }

    @Transactional
    public Department addDep2(Department department) {
        department.setEnabled(true);
        //1添加，并返回id
        departmentMapper.addDep2(department);
        //2.更新depPath
        Department faDep = departmentMapper.selectByPrimaryKey(department.getParentId());
        String depPath = faDep.getDepPath().concat("."+department.getId());
        department.setDepPath(depPath);
        departmentMapper.updateByPrimaryKey(department);

        //3.更新父部门 isParent
        faDep.setParent(true);
        departmentMapper.updateByPrimaryKey(faDep);

        return department;
    }

    public RespBean deleteDepById2(Department dep) {
        //1.判断是否有子部门
        Department dbDep = departmentMapper.selectByPrimaryKey(dep.getId());
        if(dbDep.getParent()!=null){
            return RespBean.error("该部门下有子部门，删除失败！");
        }
        //2.判断是否有员工
        List<Employee> emps = employeeMapper.selectByDepId(dep.getId());
        if(emps.size()>0){
            return RespBean.error("该部门下有员工，删除失败！");
        }
        departmentMapper.deleteByPrimaryKey(dep.getId());
        return RespBean.ok("删除成功！");
    }
}
