package com.city.testobjwar.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/freemarker")
public class FreemarkerController {

	@RequestMapping("/test.htm")
	public ModelAndView test(){
		ModelAndView mv = new ModelAndView("test");
		
		mv.addObject("name", "张三");
		
		
		List<String> list = Arrays.asList("小张","小陈","小罗","小丁");
		mv.addObject("nameList", list);
		
		mv.addObject("date", new Date());
		
		return mv;
	}
}
