package cn.appsys.service.backend;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.pojo.BackendUser;
import cn.appsys.dao.backenduser.BackendUserMapper;

@Service
public class backUserImpl implements backUser{

	@Resource
	public BackendUserMapper BackendUserMapper;
	@Override
	public BackendUser BackendUserLogin(String userCode, String pwd)  {
		// TODO Auto-generated method stub
		BackendUser backUser=null;
		backUser=BackendUserMapper.getLoginUser(userCode);
		// TODO Auto-generated catch block
		if(backUser!=null)
		if(!backUser.getUserPassword().equals(pwd)) 
		{
			backUser=null;
		}

		return backUser;
	}

}
