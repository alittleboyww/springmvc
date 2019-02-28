package com.liu.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentController {

	@RequestMapping(value = "/student",method= RequestMethod.GET)
	public ModelAndView student() {
		return new ModelAndView("student","command",new Student());
	}
	
	@RequestMapping(value = "/addStu",method = RequestMethod.POST)
	public String addStudent(@ModelAttribute("webStudent") Student student,Model model) {
		model.addAttribute("age", student.getAge());
		model.addAttribute("name",student.getName());
		model.addAttribute("id",student.getId());
		return "result";
	}
}
