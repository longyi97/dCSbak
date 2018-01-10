/* 
 */

package com.ruiec.web.service;

import java.util.List;
import java.util.Map;

import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.entity.Unit;

/**
 * 单位服务接口
 * @date 2017年11月30日 上午8:50:31
 */
public interface UnitService extends BaseService<Unit, Integer>{

	/**
	 * 获取子孙级
	 * @date 2017年12月9日 上午10:45:06
	 */
	public List<Object> findSonId(Integer id);

	/**
	 * 获取父祖级
	 * @date 2017年12月9日 上午10:45:06
	 */
	public List<Integer> findParentId(Integer id);
	
	/**
	 * 获取区级单位的数据
	 * isCity : 1将市级单位获取   ，0不获取市级单位
	 * @date 2017年11月30日 上午8:52:54
	 */
	public List<Unit> getUnitArea(Integer isCity);

	/**
	 * 获取镇级单位的数据
	 * @date 2017年11月30日 上午8:52:54
	 */
	public List<Map<String, Object>> getUnitTown(Integer areaPrikey, Integer isPolice);
	
	/**
	 * 根据警员所属单位级别查询可下发部门
	 * @date 2017年12月7日 下午9:25:06
	 */
	public List<Unit> getUnitByRank(String unitRank, int isPoliceSection);
	

	/**
	 * 根据单位查询下级单位
	 * @date 2017年12月11日 上午9:55:19
	 */
	public List<Map<String, Object>> getNextUnit(Integer areaId);
	
	/**
	 * 保存单位添加信息
	 * @date 2017年12月11日 下午2:19:44
	 */
	public Unit save(Unit addUnit, Integer prikey, Integer level, String code, Integer policeUnitId);

	/**
	 * 保存单位修改信息
	 * @date 2017年12月11日 下午2:21:49
	 */
	public Unit update(Unit unit, String code, Integer level, Integer policeUnitId);
	
	/**
	 * 查询所有区县级和乡镇级单位
	 * @date 2017年12月11日 下午3:18:44
	 */
	List<Unit> findUnitList();
	
	/**
	 * 获取所有的区级警种单位
	 * @date 2017年12月13日 上午11:10:35
	 */
	public List<Map<String, Object>> getPoliceUnit();
}
