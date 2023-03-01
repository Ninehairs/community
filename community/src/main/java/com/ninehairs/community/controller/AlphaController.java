package com.ninehairs.community.controller;

import com.ninehairs.community.service.AlphaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.format.Printer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.ReferenceQueue;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData(){
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response){
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String>enumeration=request.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String name=enumeration.nextElement();
            String value=request.getHeader(name);
            System.out.println(name+":"+value);
        }
        System.out.println(request.getParameter("code"));

        //返回响应数据
        response.setContentType("text/html;charset=utf-8");  //表明返回网页，字符集支持中文
        try(
                PrintWriter writer=response.getWriter();
                ) {
            writer.write("<h1>论坛</h1>");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //GET请求

    // /students?current=1&limit=20
    @RequestMapping(path="/students",method = RequestMethod.GET)    //强制用GET请求访问呢
    @ResponseBody
    public String getStudent(
            @RequestParam(name="current",required = false,defaultValue = "1")int current,
            @RequestParam(name="limit",required = false,defaultValue = "10")int limit){
        System.out.println(limit);
        System.out.println(current);
        return "some students";
    }

    // /student/123
    @RequestMapping(path="/student/{id}",method=RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id")int id){
        System.out.println(id);
        return "a student";
    }


    //POST请求
    @RequestMapping(path="/student",method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name,int age){
        System.out.println(name);
        System.out.println(age);
        return "Success";
    }


    //响应HTML数据

    @RequestMapping(path ="/teacher",method=RequestMethod.GET)
    public ModelAndView getTeacher(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("name","张三");
        modelAndView.addObject("age","22");
        modelAndView.setViewName("/demo/view");
        return modelAndView;
    }


    @RequestMapping(path="/school",method=RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","XJTU");
        model.addAttribute("age",80);
        return "demo/view";
    }

    //响应JSON数据（异步请求）
    //Java对象 ->JSON字符串->JS对象

    @RequestMapping(path="/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp=new HashMap<>();
        emp.put("name","mgw");
        emp.put("age",23);
        emp.put("salary",8000.00);
        return emp;
    }

    @RequestMapping(path="/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list=new ArrayList<>();
        Map<String,Object> emp=new HashMap<>();
        emp.put("name","mgw");
        emp.put("age",23);
        emp.put("salary",8000.00);
        list.add(emp);

        emp.put("name","mg");
        emp.put("age",24);
        emp.put("salary",9000.00);
        list.add(emp);

        return list;
    }

}
