package com.liu.springmvc;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileController {
	@Autowired
	ServletContext context;
	@RequestMapping(value = "/fileUploadPage", method = RequestMethod.GET)
	public ModelAndView file() {
		return new ModelAndView("fileUpload","command",new FileModel());
	}
	
	@RequestMapping(value="/fileUploadPage", method = RequestMethod.POST)
	public String uploadPage(@Validated FileModel file,BindingResult result,Model model,HttpServletRequest r) throws IOException{
		if(result.hasErrors()) {
			System.out.println("error open");
			return "fileUpload";
		}else {
			System.out.println("run zhengc");
			System.out.println(new String(file.getFile().getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8"));
			MultipartFile mfile = file.getFile();
			/*
			 * 
			 * context.getRealPath("")获取的是发布目录
			 */
			String uploadPath = context.getRealPath("") + "temp" + File.separator;
			System.out.println(context.getContextPath());
			
			File testfile = new File(uploadPath+new String(file.getFile().getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8"));
			System.out.println(testfile.toPath()+ " " + testfile.getAbsolutePath());
			FileCopyUtils.copy(file.getFile().getBytes(), testfile);
			String fileName =new String(mfile.getOriginalFilename().getBytes("ISO-8859-1"),"UTF-8");
			model.addAttribute("fileName",fileName);
			return "success";
		}
	}
	
	
	
}
