package com.dpf.myvhr.mapper;

import com.dpf.myvhr.model.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> selectByDepId(Integer depId);

    List<Employee> getEmployeeByPage(@Param("page") Integer page, @Param("size") Integer size,@Param("emp") Employee emp,@Param("beginDateScope") Date[] beginDateScope);

    long getTotal(@Param("emp") Employee emp,@Param("beginDateScope")Date[] beginDateScope);

    Integer getMaxWorkID();

    Integer addEmps(@Param("list") List<Employee> list);

    List<Employee> getEmployeeByPageWithSalary(@Param("page") Integer page, @Param("size") Integer size);

    Integer updateEmployeeSalaryById(@Param("eid") Integer eid, @Param("sid") Integer sid);

    Integer deleteEmpById(@Param("ids") Integer[] ids);

    Employee getEmployeeById(Integer id);
}
