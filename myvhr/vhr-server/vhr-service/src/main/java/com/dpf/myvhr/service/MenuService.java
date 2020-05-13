package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.MenuMapper;
import com.dpf.myvhr.mapper.MenuRoleMapper;
import com.dpf.myvhr.model.Hr;
import com.dpf.myvhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dpf
 * @create 2020-04-14 18:47
 * @email 446933040@qq.com
 */
@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getMenusByHrId() {
        Hr hr = (Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuMapper.getMenusByHrId(hr.getId());
    }

    //@Cacheable
    public List<Menu> getAllMenusWithRole() {
        return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getAllMenus() {

        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid) {
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        //先删除角色对应菜单，然后再设置
        menuRoleMapper.deleteByRid(rid);
        if(mids==null || mids.length == 0){
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid,mids);
        return result==mids.length;
    }
}
