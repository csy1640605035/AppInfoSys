package cn.appsys.service.backend;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cn.appsys.pojo.AppInfo;

public interface AppService {
	
	/**
	 * 根据条件查询出待审核的APP列表并分页显示
	 * @param querySoftwareName
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AppInfo> getAppInfoList(AppInfo appInfo,
										@Param(value="from")Integer currentPageNo,
										@Param(value="pageSize")Integer pageSize)throws Exception;
	/**
	 * 查询出待审核（status=1）的APP数量
	 * @param querySoftwareName
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @return
	 * @throws Exception
	 */
	public int getAppInfoCount(AppInfo appinfo)throws Exception;
	
	/**
	 * 根据id获取app详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public AppInfo getAppInfo(@Param(value="id")Integer id)throws Exception;
	
	/**
	 * 根据id更新app的satus
	 * @param status
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public boolean updateSatus(@Param(value="id")Integer id,@Param(value="status")Integer status)throws Exception;
	
}
