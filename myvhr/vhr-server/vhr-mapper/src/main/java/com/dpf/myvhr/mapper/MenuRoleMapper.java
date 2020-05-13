package com.dpf.myvhr.mapper;

import com.dpf.myvhr.model.MenuRole;

import java.util.List;

public interface MenuRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MenuRole record);

    int insertSelective(MenuRole record);

    MenuRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MenuRole record);

    int updateByPrimaryKey(MenuRole record);

    Integer insertRecord(Integer rid, Integer[] mids);

    void deleteByRid(Integer rid);
}
