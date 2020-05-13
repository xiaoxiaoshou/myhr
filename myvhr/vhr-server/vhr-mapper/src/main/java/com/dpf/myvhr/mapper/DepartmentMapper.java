package com.dpf.myvhr.mapper;

import com.dpf.myvhr.model.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> getAllDepartmentsByParentId(int pid);

    void addDep(Department dep);

    List<Department> getAllDepartmentsWithOutChildren();

    void addDep2(Department department);
}
