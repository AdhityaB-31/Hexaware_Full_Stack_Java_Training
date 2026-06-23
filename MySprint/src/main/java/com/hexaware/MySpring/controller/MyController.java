package com.hexaware.MySpring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;

@Controller
@RequestMapping("/MyController")

public class MyController {
	
	@RequestMapping("/HelloController")
	@ResponseBody
	public String helloController() {
		return "This is from MyController's HelloController";
		
	}
	
	@RequestMapping(value="/addPassenger" , method=RequestMethod.POST)
	  @ResponseBody
	  public String  addEmp(@RequestParam  String ,@RequestParam String ename , @RequestParam String salary) {
		  
		int eid1 =  	Integer.parseInt(eid);
		 double sal = Double.parseDouble(salary); 
		 
		 emp.setEid(eid1); emp.setEname(ename); emp.setSalary(sal);
		 
		 
		 
		  return "Employee Details inserted in DB  "+ emp;
		  
		  
	  }

}
