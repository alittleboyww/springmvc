1. ����ide-eclipse
2. web.xml����
```
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd" id="WebApp_ID" version="3.1">
  <display-name>SpringMvcDemo2</display-name>
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>
  <servlet>
  	<servlet-name>
  		SpringMvc
  	</servlet-name>
  	<servlet-class>
  		org.springframework.web.servlet.DispatcherServlet
  	</servlet-class>
  	<load-on-startup>1</load-on-startup>
  </servlet>
  <servlet-mapping>
  <!-- ���������õ�������ͬ -->
      <servlet-name>SpringMvc</servlet-name>
      <url-pattern>/</url-pattern>
  </servlet-mapping>
  
</web-app>
```
3. SpringMvc-servlet.xml����
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
   xmlns:context="http://www.springframework.org/schema/context"
   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
   xmlns:mvc="http://www.springframework.org/schema/mvc"
   xsi:schemaLocation="
   http://www.springframework.org/schema/beans     
   http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
   http://www.springframework.org/schema/context 
   http://www.springframework.org/schema/context/spring-context-3.0.xsd
   http://www.springframework.org/schema/mvc
   http://www.springframework.org/schema/mvc/spring-mvc.xsd">

	<context:component-scan base-package="com.liu.springmvc" />
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<!--prefix ǰ׺-->
    	<property name="prefix">
        	<value>/WEB-INF/pages/</value>
    	</property>
    	<!--suffix ��׺-->
    	<property name="suffix">
        	<value>.jsp</value>
    	</property>
	</bean>
	<bean id="multipartResolver"
        class="org.springframework.web.multipart.commons.CommonsMultipartResolver" />
	
	<!--���뾲̬��Դ�ļ�ʹ�õ�����-->
    <mvc:resources mapping="/jsp/**" location="/WEB-INF/jsp/"/>
    <mvc:annotation-driven/>
    <!--����ʹ��<mvc:resources ..../>�����ӳ�侲̬ҳ�档ӳ�����Ա�����ָ��http�����URLģʽ��Antģʽ��location���Ա���ָ��һ��������Ч����ԴĿ¼λ�ã����а�����̬ҳ�棬����ͼƬ����ʽ��JavaScript��������̬���ݡ�����ʹ�ö��ŷָ���ֵ�б�ָ�������Դλ�á�-->
</beans>

```
4. FileModel.java
```
package com.liu.springmvc;

import org.springframework.web.multipart.MultipartFile;

public class FileModel {
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}

```
5. FileController.java
```
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
			 * context.getRealPath("")��ȡ���Ƿ���Ŀ¼
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

```
6. fileUpload.jsp
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form:form method="POST" modelAttribute="fileUpload"
        enctype="multipart/form-data">
      ��ѡ��һ���ļ��ϴ� : 
      <input type="file" name="file" />
        <input type="submit" value="�ύ�ϴ�" />
    </form:form>
</body>
</html>
```
7. success.jsp
```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
�ļ����� :
    <b> ${fileName} </b> - �ϴ��ɹ���
</body>
</html>
����һ��
```