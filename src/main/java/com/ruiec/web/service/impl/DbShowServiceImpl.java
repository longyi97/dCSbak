package com.ruiec.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.DbShow;
import com.ruiec.web.entity.DbTable;
import com.ruiec.web.service.DbShowService;
import com.ruiec.web.service.DbTableService;

/**
 * 数据展示服务实现类
 * @date 2017年12月18日 下午4:49:03
 */
@Service("dbShowServiceImpl")
public class DbShowServiceImpl extends BaseServiceImpl<DbShow, Integer> implements DbShowService {

	@Resource
	private DbTableService dbTableService;
	/**
	 * 根据数据导入表id查询数据展示子集
	 * @date 2017年12月21日 下午5:53:49
	 */
	@Override
	@Transactional
	public List<DbShow> findSon(Integer tid) {
		// 查询数据导入表详情
		DbTable dbTable = dbTableService.get(tid);
		// 根据数据导入表名查询数据展示id
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("dbName", dbTable.getAssociationTableName()));
		DbShow dbShow = super.get(filters);
		// 根据父级id查询子集
		List<DbShow> dbShows = super.findList(null, Filter.eq("parentId", dbShow.getId()), null);
		return dbShows;
	}
	/**
	 * 批量删除
	 * @date 2017年12月22日 上午9:32:15
	 */
	@Override
	@Transactional
	public void deleteByIds(Integer[] ids) {
		// 查询表内字段
		List<DbShow> dbShows = super.findList(null, Filter.in("parentId", ids), null);
		//删除字段
		for (DbShow dbShow : dbShows) {
			delete(dbShow);
		}
		// 删除表
		super.delete(ids);
		
	}

}
