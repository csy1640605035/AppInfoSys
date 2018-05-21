package cn.appsys.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.backUser;
import cn.appsys.service.backend.backUserImpl;

@Controller
@RequestMapping("/manager")
public class BackUserLogin {
@Resource
public backUserImpl BackUser;

@RequestMapping("/dologin")
public String Backlogin(HttpServletRequest req,HttpSession session) 
{
	String userCode=req.getParameter("userCode");
	String userPassword=req.getParameter("userPassword");
	BackendUser backUser=BackUser.BackendUserLogin(userCode,userPassword);
	if(backUser!=null) 
	{
		session.setAttribute("userSession", backUser);
		return "backend/main";
	}
	return "403";
}
  @RequestMapping("/login")
  public String loginlist() 
   {
    return "backendlogin";	
  }
  
  @RequestMapping("/logout")
  public String logout( HttpSession session) 
  {
	  session.removeAttribute("userSession");
	  
	  return "redirect:/manager/login";
  }
}
