package cn.appsys.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.backend.AppService;
import cn.appsys.service.developer.AppCategoryService;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.service.developer.AppVersionService;
import cn.appsys.service.developer.DataDictionaryService;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("/manager/backend/app")


public class BackUserAppController {
	private Logger logger = Logger.getLogger(BackUserAppController.class);
	@Resource
	private AppInfoService appInfoService;
	@Resource 
	private DataDictionaryService dataDictionaryService;
	@Resource 
	private AppCategoryService appCategoryService;
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private AppService appService;
	
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
					categoryLevel3List = getCategoryList(appinfo.getCategoryLevel2().toString());
					model.addAttribute("categoryLevel3List", categoryLevel3List);
				}
		return "backend/applist";
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
	
	@RequestMapping("/check")
	public String check(@RequestParam(value="aid",required=false) String appId,
			    @RequestParam(value="vid",required=false) String versionId,Model model) 
	{
		
		AppVersion appver=null;
		AppInfo appinfo=null;
		try {
			 appver=appVersionService.getAppVersionById(Integer.parseInt(versionId));
			 appinfo=appService.getAppInfo(Integer.parseInt(appId));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("appInfo",appinfo);
		model.addAttribute("appVersion",appver);
		return "backend/appcheck";
	}
	
	@RequestMapping("/checksave")
	public String checksave(AppInfo appinfo) 
	{
		try {
			if(appService.updateSatus(appinfo.getId(), appinfo.getStatus())) 
			{
				return "redirect:/manager/backend/app/list";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
		        return "redirect:/manager/backend/app/list";
	}
	
}
