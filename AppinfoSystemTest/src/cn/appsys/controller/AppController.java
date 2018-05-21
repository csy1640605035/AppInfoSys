package cn.appsys.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mysql.jdbc.StringUtils;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.backend.AppService;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping(value="/dev/flatform/app")
public class AppController {
	private Logger logger = Logger.getLogger(AppController.class);
	@Resource
	private AppInfoService appInfoService;
	@Resource 
	private DataDictionaryService dataDictionaryService;
	@Resource 
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private AppService AppService;

	@RequestMapping("/list")
	public String appList(AppInfo appinfo,Model model,HttpServletRequest req) 
	{
		List<AppInfo>Applist=null;
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		int pageSize = Constants.pageSize;
		Integer currentPageNo = 1;
		String pageIndex=req.getParameter("pageIndex");
		if(pageIndex != null){	
			try{
				currentPageNo = Integer.valueOf(pageIndex);
			}catch (NumberFormatException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		List<AppCategory>Category1=new ArrayList<AppCategory>();
		int totalCount = 0;
		try {
			totalCount = appInfoService.getAppInfoCount(appinfo);
			Category1= appCategoryService.getAppCategoryListByParentId(null);
			Applist=appInfoService.getAppInfoList(appinfo, (currentPageNo-1)*5, pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//总页数
		PageSupport pages = new PageSupport();
		pages.setCurrentPageNo(currentPageNo);
		pages.setPageSize(pageSize);
		pages.setTotalCount(totalCount);
		int totalPageCount = pages.getTotalPageCount();
		//控制首页和尾页
		if(currentPageNo < 1){
			currentPageNo = 1;
		}else if(currentPageNo > totalPageCount){
			currentPageNo = totalPageCount;
		}
		List<DataDictionary>statusList=null;
		List<DataDictionary>flatFormList=null;
		statusList = this.getDataDictionaryList("APP_STATUS");
		flatFormList = this.getDataDictionaryList("APP_FLATFORM");
		model.addAttribute("appInfoList", Applist);
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("pages", pages);
		model.addAttribute("categoryLevel1List",Category1);
		model.addAttribute("queryCategoryLevel1", appinfo.getCategoryLevel1());
		model.addAttribute("queryCategoryLevel2", appinfo.getCategoryLevel2());
		model.addAttribute("queryCategoryLevel3", appinfo.getCategoryLevel3());
		if(appinfo.getCategoryLevel2()!= null && !appinfo.getCategoryLevel2().equals("")){
			categoryLevel2List = getCategoryList(appinfo.getCategoryLevel1().toString());
			model.addAttribute("categoryLevel2List", categoryLevel2List);
		}
		if(appinfo.getCategoryLevel3() != null && !appinfo.getCategoryLevel3().equals("")){
			categoryLevel3List = getCategoryList(appinfo.getCategoryLevel3().toString());
			model.addAttribute("categoryLevel3List", categoryLevel3List);
		}
		return "developer/appinfolist";
	}

	/**
	 * 根据parentId查询出相应的分类级别列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> getAppCategoryList (@RequestParam String pid){
		logger.debug("getAppCategoryList pid ============ " + pid);
		if(pid.equals("")) pid = null;
		return getCategoryList(pid);
	}

	public List<AppCategory> getCategoryList (String pid){
		List<AppCategory> categoryLevelList = null;
		try {
			categoryLevelList = appCategoryService.getAppCategoryListByParentId(pid==null?null:Integer.parseInt(pid));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categoryLevelList;
	}
	public List<DataDictionary> getDataDictionaryList(String typeCode){
		List<DataDictionary> dataDictionaryList = null;
		try {
			dataDictionaryList = dataDictionaryService.getDataDictionaryList(typeCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dataDictionaryList;
	}

	/**
	 * 根据typeCode查询出相应的数据字典列表
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<DataDictionary> getDataDicList (@RequestParam String tcode){
		logger.debug("getDataDicList tcode ============ " + tcode);
		return this.getDataDictionaryList(tcode);
	}
	/**
	 * 根据ID查询app信息
	 * @param appinfoid
	 * @param model
	 * @return
	 */

	@RequestMapping(value="/appview/{id}",method=RequestMethod.GET)
	public String view(@PathVariable String id,Model model){
		AppInfo appInfo = null;
		List<AppVersion> appVersionList = null;
		try {
			appInfo = appInfoService.getAppInfo(Integer.parseInt(id),null);
			appVersionList = appVersionService.getAppVersionList(Integer.parseInt(id));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute(appInfo);
		return "developer/appinfoview";
	}

	@RequestMapping(value="/appinfomodify",method=RequestMethod.GET)
	public String appinfomodify(@RequestParam(value="id") String id,Model model) 
	{
		AppInfo appInfo = null;

		try {
			appInfo = appInfoService.getAppInfo(Integer.parseInt(id),null);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute(appInfo);
		return "developer/appinfomodify";
	}

	/**
	 * 根据ID修改App信息
	 * @param appinfo
	 * @return
	 */
	@RequestMapping(value="appinfomodifysave",method=RequestMethod.POST)
	public String appinfomodifysave(AppInfo appinfo) 
	{
		try {
			if(appInfoService.modify(appinfo))
				return "redirect:/dev/flatform/app/list";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/dev/flatform/app/list";
	}
	/**
	 * 根据ID删除App信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delapp",method=RequestMethod.GET)
	@ResponseBody
	public Object DeleteApp(@RequestParam  String id) 
	{
		HashMap<String, String> resultMap = new HashMap<String, String>();

		try {
			if(appVersionService.getAppVersionByAppId(Integer.parseInt(id))>0) 
			{
				if(appInfoService.deleteAppInfoById(Integer.parseInt(id))&&appVersionService.deleteVersionByAppId(Integer.parseInt(id)))
				{

					resultMap.put("delResult", "true");

				}
				else { 
					resultMap.put("delResult","false");
				}
			}else 
			{
				if(appInfoService.deleteAppInfoById(Integer.parseInt(id)))
				{

					resultMap.put("delResult", "true");

				}
				else { 
					resultMap.put("delResult","false");
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 跳转到添加App页面
	 * @return
	 */
	@RequestMapping(value="appinfoadd")
	public String addinfo() 
	{

		return "developer/appinfoadd";
	}

	/**
	 * 判断App名字是否重复
	 * @param APKName
	 * @return
	 */
	@RequestMapping(value="apkexist.json")
	@ResponseBody
	public Object AppName(@RequestParam String APKName) 
	{
		HashMap<String, String> resultMap = new HashMap<String, String>();
		try {
			AppInfo appinfo=appInfoService.getAppInfo(null, APKName);
			if(appinfo!=null) 
			{
				resultMap.put("APKName", "exist");
			}else 
			{
				resultMap.put("APKName","noexist");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONArray.toJSONString(resultMap);
	}

	/**
	 * 添加App信息
	 * @param appinfo
	 * @return
	 */
	@RequestMapping(value="appinfoaddsave")

	public String appinfoaddsave(AppInfo appinfo) 
	{
		try {
			appInfoService.add(appinfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/dev/flatform/app/list";
	}

	/**
	 * 跳转到添加APP版本页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="appversionadd")
	public String appversionadd(@RequestParam(value="id")String id,Model model,AppVersion appVersion) 
	{
		try {
			List<AppVersion> appver=appVersionService.getAppVersionList(Integer.parseInt(id));
			//AppVersion appv=appVersionService.getAppVersionById(Integer.parseInt(id));
			appVersion.setAppId(Integer.parseInt(id));
			model.addAttribute("appVersion",appVersion);
			model.addAttribute("appVersionList",appver);
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return "developer/appversionadd";
	} 
	/**
	 * 添加APP版本信息
	 */
	
	@RequestMapping(value="addversionsave",method=RequestMethod.POST)
	public String addversionsave(AppVersion appversion,HttpServletRequest req) 
	{
		try {
			
			appVersionService.appsysadd(appversion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return "redirect:/dev/flatform/app/list";
	}
	
	@RequestMapping(value="appversionmodify")
	public String appversionmodify(@RequestParam(value="aid")String id,@RequestParam(value="vid")String vid,Model model) 
	{
		
		try {
			List<AppVersion> appver=appVersionService.getAppVersionList(Integer.parseInt(id));
			//AppVersion appv=appVersionService.getAppVersionById(Integer.parseInt(id));
			
			AppVersion appVersion=appVersionService.getAppVersionById(Integer.parseInt(id));
			model.addAttribute(appVersion);
			model.addAttribute("appVersion",appVersion);
			model.addAttribute("appVersionList",appver);
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "developer/appversionmodify";
	}
	
	@RequestMapping(value="appversionmodifysave")
	public String appversionmodifysave(AppVersion appversion) 
	{
		try {
			appVersionService.modify(appversion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/dev/flatform/app/list";
		
	}
	@RequestMapping(value="/{appid}/sale",method=RequestMethod.PUT)
	@ResponseBody
	public Object sale(@PathVariable String appid,HttpSession session){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Integer appIdInteger = 0;
		try{
			appIdInteger = Integer.parseInt(appid);
		}catch(Exception e){
			appIdInteger = 0;
		}
		resultMap.put("errorCode", "0");
		resultMap.put("appId", appid);
		if(appIdInteger>0){
			try {
				DevUser devUser = (DevUser)session.getAttribute(Constants.DEV_USER_SESSION);
				AppInfo appInfo = new AppInfo();
				appInfo.setId(appIdInteger);
				appInfo.setModifyBy(devUser.getId());
				if(appInfoService.appsysUpdateSaleStatusByAppId(appInfo)){
					resultMap.put("resultMsg", "success");
				}else{
					resultMap.put("resultMsg", "success");
				}		
			} catch (Exception e) {
				resultMap.put("errorCode", "exception000001");
			}
		}else{
			//errorCode:0为正常
			resultMap.put("errorCode", "param000001");
		}
		
		/*
		 * resultMsg:success/failed
		 * errorCode:exception000001
		 * appId:appId
		 * errorCode:param000001
		 */
		return resultMap;
	}

}
