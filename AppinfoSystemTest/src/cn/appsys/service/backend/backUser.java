package cn.appsys.service.backend;

import cn.appsys.pojo.BackendUser;

public interface backUser {
	BackendUser BackendUserLogin(String  userCode,String pwd);
}
