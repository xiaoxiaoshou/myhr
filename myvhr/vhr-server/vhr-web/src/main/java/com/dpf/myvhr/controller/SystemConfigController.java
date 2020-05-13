package com.dpf.myvhr.controller;

import com.dpf.myvhr.model.Menu;
import com.dpf.myvhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dpf
 * @create 2020-04-14 17:07
 * @email 446933040@qq.com
 */
@RestController
@RequestMapping("/system/config")
public class SystemConfigController {

    @Autowired
    MenuService menuService;

    @GetMapping("/menu")
    public List<Menu> getAllMenus(){
        return menuService.getMenusByHrId();
    }

}
