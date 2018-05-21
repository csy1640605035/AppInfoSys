package cn.appsys.service.backend;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.pojo.AppInfo;

@Service
public class AppServiceImpl implements AppService {
	@Resource
	private AppInfoMapper mapper;
	
	@Override
	public AppInfo getAppInfo(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfo(id, null);
	}

	@Override
	public List<AppInfo> getAppInfoList(AppInfo appInfo,Integer currentPageNo,
									Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfoList( appInfo, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int getAppInfoCount(AppInfo appinfo)
							throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfoCount( appinfo);
	}

	@Override
	public boolean updateSatus(Integer id, Integer status) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.updateSatus(status, id) > 0 ){
			flag = true;
		}
		return flag;
	}
}
