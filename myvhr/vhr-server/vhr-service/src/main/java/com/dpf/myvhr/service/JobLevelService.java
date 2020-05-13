package com.dpf.myvhr.service;


import com.dpf.myvhr.mapper.JObLevelMapper;
import com.dpf.myvhr.model.JobLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpf
 * @create 2020-05-03 20:27
 * @email 446933040@qq.com
 */
@Service
public class JobLevelService {

    @Autowired
    JObLevelMapper jObLevelMapper;

    public List<JobLevel> getAllJobLevel() {
        return jObLevelMapper.getAllJobLevel();
    }
}
