package com.dpf.myvhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dpf
 * @create 2020-04-11 10:53
 * @email 446933040@qq.com
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/employee/basic/hello2")
    public String hello2(){
        return "/employee/basic/hello3";
    }

    @GetMapping("/employee/basic/hello3")
    public String hello3(){
        return "/employee/basic/hello3";
    }

}
