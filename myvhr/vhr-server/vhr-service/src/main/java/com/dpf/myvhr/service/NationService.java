package com.dpf.myvhr.service;

import com.dpf.myvhr.mapper.NationMapper;
import com.dpf.myvhr.model.Nation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dpf
 * @create 2020-05-03 20:24
 * @email 446933040@qq.com
 */
@Service
public class NationService {
    @Autowired
    NationMapper nationMapper;

    public List<Nation> getAllNation() {
        return nationMapper.getAllNation();
    }
}
