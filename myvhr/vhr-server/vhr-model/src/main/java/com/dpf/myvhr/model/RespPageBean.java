package com.dpf.myvhr.model;

import java.util.List;

/**
 * @author dpf
 * @create 2020-04-30 13:37
 * @email 446933040@qq.com
 */
public class RespPageBean {
    private Long total;
    private List<?> data;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> dat) {
        this.data = dat;
    }
}
