package com.ruiec.web.service;

import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.common.JsonReturn;
import com.ruiec.web.entity.Db;

/**
 * 数据源服务接口
 * @date 2017年12月18日 下午4:47:35
 */
public interface DbService extends BaseService<Db, Integer> {
	/**
	 * 保存数据源
	 * @date 2017年12月18日 下午5:46:15
	 */
	public JsonReturn saveDb(Db db);
	
	/**
	 * 修改数据源
	 * @date 2017年12月18日 下午5:46:15
	 */
	public JsonReturn updateDb(Db db);
	
	/**
	 * 数据导入（库）
	 * @date 2017年12月20日 上午11:04:21
	 */
	public JsonReturn dataImport(Integer id);
	/**
	 * 数据导入（表）
	 * @date 2017年12月20日 上午11:04:21
	 */
	public JsonReturn tableImport(Integer id);
}
