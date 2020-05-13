package com.dpf.myvhr.utils;

import com.dpf.myvhr.model.Hr;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author dpf
 * @create 2020-04-22 15:13
 * @email 446933040@qq.com
 */
public class HrUtils {

    public static Hr getCurrentHr() {
        return ((Hr) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
