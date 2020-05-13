package com.dpf.myvhr.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author dpf
 * @create 2020-04-16 12:56
 * @email 446933040@qq.com
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判断是否拥有权限的决策方法,该方法没有抛异常说明有权限
     * @param authentication
     * @param o          包含客户端发起的请求的requset信息,可转换为 HttpServletRequest request = ((FilterInvocation) o).getHttpRequest();
     * @param collection 过滤器传过来的许可角色
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            //如果是前面没有匹配的请求路径
            if(configAttribute.getAttribute().equals("ROLE_login")){
                //如果是游客
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("尚未登录，请登录");
                }else {
                    return;
                }
            }

            //获取当前登录用户所具有的权限
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                //如果说当前登录用户所具有的权限有跟数据库当前用户权限匹配，那么放行
                if(authority.getAuthority().equals(configAttribute)){
                    return;
                }
            }

        }
        throw new AccessDeniedException("权限不足，请联系管理员!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
