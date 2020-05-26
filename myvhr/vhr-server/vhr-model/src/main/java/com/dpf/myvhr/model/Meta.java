package com.dpf.myvhr.model;

import java.io.Serializable;

/**
 * @author dpf
 * @create 2020-04-14 19:04
 * @email 446933040@qq.com
 */
public class Meta implements Serializable {

    private Boolean keepAlive;

    private Boolean requireAuth;

    public Boolean getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(Boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public Boolean getRequireAuth() {
        return requireAuth;
    }

    public void setRequireAuth(Boolean requireAuth) {
        this.requireAuth = requireAuth;
    }
}
