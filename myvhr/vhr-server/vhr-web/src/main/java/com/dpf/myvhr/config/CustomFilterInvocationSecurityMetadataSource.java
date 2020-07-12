package com.dpf.myvhr.config;

import com.dpf.myvhr.model.Menu;
import com.dpf.myvhr.model.Role;
import com.dpf.myvhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * @author dpf
 * @create 2020-04-16 12:17
 * @email 446933040@qq.com
 *
 * 请求过滤器
 * 通过请求地址返回请求地址需要的角色
 *
 */
@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //获取请求路径
        String requestUrl = ((FilterInvocation) o).getFullRequestUrl();
        // 获取系统资源(菜单)
        List<Menu> allMenus = menuService.getAllMenusWithRole();

        for (Menu menu : allMenus) {
            //如果请求路径与菜单路径匹配
            if(pathMatcher.match(menu.getUrl(),requestUrl)){
                //获取对应菜单有哪些角色可访问
                List<Role> roles = menu.getRoles();
                String[] rolesStr = new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    rolesStr[i] = roles.get(i).getName();
                }
                return SecurityConfig.createList(rolesStr);
            }
        }
        //如果不是能匹配的接口，那么都是登录之后能访问的(定义标志，后面处理)
        return SecurityConfig.createList("ROLE_login");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
