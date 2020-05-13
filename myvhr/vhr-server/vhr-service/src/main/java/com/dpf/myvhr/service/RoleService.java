package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.RoleMapper;
import com.dpf.myvhr.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author dpf
 * @create 2020-04-19 21:20
 * @email 446933040@qq.com
 */
@Service
public class RoleService {

    @Autowired
    RoleMapper roleMapper;


    public List<Role> getAllRoles() {
        return roleMapper.getAllRoles();
    }

    public boolean deleteRoleById(Integer rid) {
        return roleMapper.deleteByPrimaryKey(rid)==1;
    }

    public int addRole(Role role) {
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        return roleMapper.insert(role);
    }
}
