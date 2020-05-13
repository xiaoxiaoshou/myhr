package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.PoliticsstatusMapper;
import com.dpf.myvhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpf
 * @create 2020-05-03 20:25
 * @email 446933040@qq.com
 */
@Service
public class PoliticsstatusService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;

    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
