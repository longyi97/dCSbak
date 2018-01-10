package com.ruiec.web.service;

import java.util.List;

import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.entity.DbShow;

/**
 * 数据展示服务接口
 * @date 2017年12月18日 下午4:47:35
 */
public interface DbShowService extends BaseService<DbShow, Integer> {

	/**
	 * 根据数据导入表id查询数据展示子集
	 * @date 2017年12月21日 下午5:53:49
	 */
	public List<DbShow> findSon(Integer tid);
	/**
	 * 批量删除
	 * @date 2017年12月22日 上午9:32:15
	 */
	public void deleteByIds(Integer[] ids);
}
