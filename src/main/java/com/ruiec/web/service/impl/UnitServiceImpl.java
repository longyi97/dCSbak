/*
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */

package com.ruiec.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.query.Sort;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.service.UnitService;

/**
 * 单位服务接口实现类
 * @author Senghor<br>
 * @date 2017年11月30日 上午9:01:37
 */
/**
 * 
 * @author Senghor<br>
 * @date 2017年12月11日 下午2:21:47
 */
@Service("unitServiceImpl")
public class UnitServiceImpl extends BaseServiceImpl<Unit, Integer> implements UnitService{

	/**
	 * 获取子孙级
	 * @author Senghor<br>
	 * @date 2017年12月9日 上午10:45:06
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Object> findSonId(Integer id) {
		List<Object> list=new ArrayList<Object>();
		Unit unit=super.get(id);
		/*String hql1="select PRIKEY from T_SYS_UNIT where PRIKEY = :id ";
					if (unit.getIsPoliceSection()==1) { hql +=" AND IS_POLICE_SECTION=1 "; }
					hql+=" union all "+
					" select PRIKEY from T_SYS_UNIT where ";
					if (unit.getIsPoliceSection()==1) { hql +=" POLICE_TYPES_PARENT_ID = :id AND IS_POLICE_SECTION=1 "; }else { hql +=" PARENT_ID = :id "; }
					hql+=" union all "+
					" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where";
					if (unit.getIsPoliceSection()==1) { hql +=" POLICE_TYPES_PARENT_ID = :id AND IS_POLICE_SECTION=1 "; }else { hql +=" PARENT_ID = :id "; }
					hql+=" )"+
					" union all "+
					" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where";
					if (unit.getIsPoliceSection()==1) { hql +=" POLICE_TYPES_PARENT_ID = :id AND IS_POLICE_SECTION=1 "; }else { hql +=" PARENT_ID = :id "; }
					hql+= " ))"+
					" union all "+
					" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where";
					if (unit.getIsPoliceSection()==1) { hql +=" POLICE_TYPES_PARENT_ID = :id AND IS_POLICE_SECTION=1 "; }else { hql +=" PARENT_ID = :id "; }
					hql+= " )))"+
					" union all "+
					" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where";
					if (unit.getIsPoliceSection()==1) { hql +=" POLICE_TYPES_PARENT_ID = :id AND IS_POLICE_SECTION=1 "; }else { hql +=" PARENT_ID = :id "; }
					hql+= " ))))";
					*/
		String hql=null;
		//判断是否是警种单位，执行不同的查询语句
		if (unit.getIsPoliceSection()==1) {
			hql=" select PRIKEY from T_SYS_UNIT where PRIKEY = :id "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT where POLICE_TYPES_PARENT_ID = :id "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT where POLICE_TYPES_PARENT_ID = :id) "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT where POLICE_TYPES_PARENT_ID = :id)) "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT where POLICE_TYPES_PARENT_ID = :id))) "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where POLICE_TYPES_PARENT_ID in (select PRIKEY from T_SYS_UNIT where POLICE_TYPES_PARENT_ID = :id)))) ";
		}else {
			hql=" select PRIKEY from T_SYS_UNIT where PRIKEY = :id "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT where PARENT_ID = :id "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :id) "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :id)) "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :id))) "+
				" union all "+
				" select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :id)))) ";
		}
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("id", id);
		list = (List<Object>)query.list();
		return list;
	}
	
	/**
	 * 获取父祖级
	 * @author Senghor<br>
	 * @date 2017年12月9日 上午10:45:06
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Integer> findParentId(Integer id) {
		List<Integer> list=new ArrayList<Integer>();
		String sql="select PRIKEY from T_SYS_UNIT where PRIKEY = :PRIKEY"+
				"union all"+
				"select PRIKEY from T_SYS_UNIT where PARENT_ID = 2 "+
				"union all"+
				"select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :PRIKEY)"+
				"union all"+
				"select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :PRIKEY))"+
				"union all"+
				"select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :PRIKEY)))"+
				"union all"+
				"select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT unit where PARENT_ID in (select PRIKEY from T_SYS_UNIT where PARENT_ID = :PRIKEY))))";
		Query query = getSession().createQuery(sql);
		query.setParameter("PRIKEY", id);
		list = query.list();
		return list;
	}
	
	/**
	 * 获取区级所有的单位
	 * isCity : 1将市级单位获取   ，0不获取市级单位
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午10:44:04
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Unit> getUnitArea(Integer isCity) {
		List<Unit> areaList = new ArrayList<Unit>();
		// 获取到市级公安局的主键id
		Unit unit = super.get(1);
		if (isCity==1) {
			areaList.add(unit);
		}
		// 查询市级公安局，没有市级公安局则不查询下级单位
		if (unit != null) {
			Integer cityPrikey = unit.getId();
			areaList.addAll( super.findList(null, Filter.eq("parentId", cityPrikey), Sort.asc("id")));
		}
		// 根据市级公安局主键id查询下属区级公安局信息
		return areaList;
	}

	/**
	 * 镇级单位列表查询方法
	 * @author Senghor<br>
	 * @date 2017年12月11日 下午1:39:15
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getUnitTown(Integer areaPrikey, Integer isPolice) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 存储镇级单位list
		List<Unit> townList = new ArrayList<Unit>();
		// 判断是否根据警种查询镇级单位
		if (isPolice == 0) {
			// 根据区级公安局主键id查询镇级公安局信息
			townList = super.findList(null, Filter.eq("parentId", areaPrikey),  Sort.asc("id"));
		} else if (isPolice == 1) {
			// 根据区级公安局主键id和是警钟部门的查询镇级公安局信息
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("policeTypesParentId", areaPrikey));
			filters.add(Filter.eq("isPoliceSection", isPolice));
			List<Sort> sorts=new ArrayList<Sort>();
			sorts.add(Sort.asc("id"));
			townList = super.findList(null, filters, sorts);
		}
		if (townList.size() > 0) {
			for (int i = 0; i < townList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", townList.get(i).getId());
				map.put("unitName", townList.get(i).getUnitName());
				map.put("isPolice", townList.get(i).getIsPoliceSection());
				listMap.add(map);
			}
		}
		return listMap;
	}

	/**
	 * 根据警员所属单位级别查询可下发警种部门
	 * @author qinzhitian<br>
	 * @date 2017年12月7日 下午9:25:06
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Unit> getUnitByRank(String unitRank, int isPoliceSection) {
		List<Filter> filters = new ArrayList<Filter>();
		// 是否为警种
		filters.add(Filter.eq("isPoliceSection", isPoliceSection));
		if ("city".equals(unitRank) || "area".equals(unitRank)) {//区级以上
			filters.add(Filter.eq("unitRank", "area"));
		}else if ("town".equals(unitRank)) {//镇级
			filters.add(Filter.eq("unitRank", "town"));
		}
		List<Sort> sorts=new ArrayList<Sort>();
		sorts.add(Sort.asc("id"));
		return findList(null, filters, sorts);
	}
	
	
	/**
	 * 根据单位查询下级单位
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午9:54:10
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getNextUnit(Integer areaId) {
		List<Unit> list=new ArrayList<Unit>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if (areaId!=null) {
			Unit unit=super.get(areaId);
			if (unit.getIsPoliceSection()==0) {
				list=super.findList(null, Filter.eq("parentId", areaId), Sort.asc("id"));
				for (int i = 0; i < list.size(); i++) {
					Map< String, Object> map=new HashMap<String, Object>();
					map.put("id", list.get(i).getId());
					map.put("name", list.get(i).getUnitName());
					listMap.add(map);
				}
			}else {
				list=super.findList(null, Filter.eq("policeTypesParentId", areaId), Sort.asc("id"));
				for (int i = 0; i < list.size(); i++) {
					Map< String, Object> map=new HashMap<String, Object>();
					map.put("id", list.get(i).getId());
					map.put("name", list.get(i).getUnitName());
					listMap.add(map);
				}
			}
		}
		return listMap;
	}
	
	/**
	 * 保存单位添加信息
	 * @author Senghor<br>
	 * @date 2017年12月11日 下午2:18:33
	 */
	@Override
	@Transactional(readOnly = true)
	public Unit save(Unit addUnit, Integer prikey, Integer level, String code, Integer policeUnitId) {
		// 程序严谨性判断，判断获取的值是否有空
		if (addUnit == null) {
			return null;
		}
		if (level == null) {
			return null;
		}
		if (code == null) {
			return null;
		}
		if (prikey == null) {
			prikey = 1;
		}
		// 根据id查询
		Unit unit = super.get(prikey);
		if (unit == null) {
			// 根据id获取不到单位
			return null;
		}
		// 获取省级编码
		addUnit.setProvinceCode(unit.getProvinceCode());
		// 获取市级编码
		addUnit.setCityCode(unit.getCityCode());
		if (addUnit.getIsPoliceSection() == 1) {
			//警种添加下级则只存警种父级id
			if (unit.getIsPoliceSection() == 0) {
				// 获取要添加的父级id
				addUnit.setParentId(prikey);
			}
			// 获取要添加的警种父级id
			addUnit.setPoliceTypesParentId(policeUnitId);
		} else {
			// 获取要添加的父级id
			addUnit.setParentId(prikey);
		}
		switch (level) {
		case 3:
			// 添加区级单位
			addUnit.setUnitRank("area");
			addUnit.setAreaCode(code.length() == 1 ? "0" + code : code);
			addUnit.setTownCode("00");
			addUnit.setOther1Code("00");
			addUnit.setOther2Code("00");
			break;
		case 4:
			// 添加镇级单位
			addUnit.setUnitRank("town");
			addUnit.setAreaCode(unit.getAreaCode());
			addUnit.setTownCode(code.length() == 1 ? "0" + code : code);
			addUnit.setOther1Code("00");
			addUnit.setOther2Code("00");
			break;
		case 5:
			// 添加其他级单位1
			addUnit.setUnitRank("other1Code");
			addUnit.setAreaCode(unit.getAreaCode());
			addUnit.setTownCode(unit.getTownCode());
			addUnit.setOther1Code(code.length() == 1 ? "0" + code : code);
			addUnit.setOther2Code("00");
			break;
		case 6:
			// 添加其他级单位2
			addUnit.setUnitRank("other2Code");
			addUnit.setAreaCode(unit.getAreaCode());
			addUnit.setTownCode(unit.getTownCode());
			addUnit.setOther1Code(unit.getOther1Code());
			addUnit.setOther2Code(code.length() == 1 ? "0" + code : code);
			break;
		default:
			// 添加其他单位则不添加
			return null;
		}
		// 创建新增时间
		addUnit.setCreateDate(new Date());
		return addUnit;
	}
	
	/**
	 * 保存单位修改信息
	 * @author Senghor<br>
	 * @date 2017年12月11日 下午2:21:49
	 */
	@Override
	@Transactional(readOnly = true)
	public Unit update(Unit unit, String code, Integer level, Integer policeUnitId) {
		Unit parentUnit = super.get(unit.getId());
		//是否是警种
		if (unit.getIsPoliceSection() == 1) {
			//父级id
			unit.setParentId(parentUnit.getParentId());
			//是警种则修改警种父级id,原父级id不变
			unit.setPoliceTypesParentId(policeUnitId);
		}else {
			//父级id
			unit.setParentId(parentUnit.getParentId());
		}
		// 获取省级编码
		unit.setProvinceCode(parentUnit.getProvinceCode());
		// 获取市级编码
		unit.setCityCode(parentUnit.getCityCode());
		switch (level) {
		case 3:
			// 修改区级单位
			unit.setAreaCode(code.length() == 1 ? "0" + code : code);
			unit.setTownCode("00");
			unit.setOther1Code("00");
			unit.setOther2Code("00");
			break;
		case 4:
			// 修改镇级单位
			unit.setAreaCode(parentUnit.getAreaCode());
			unit.setTownCode(code.length() == 1 ? "0" + code : code);
			unit.setOther1Code("00");
			unit.setOther2Code("00");
			break;
		case 5:
			// 修改其他级单位1
			unit.setAreaCode(parentUnit.getAreaCode());
			unit.setTownCode(parentUnit.getTownCode());
			unit.setOther1Code(code.length() == 1 ? "0" + code : code);
			unit.setOther2Code("00");
			break;
		case 6:
			// 修改其他级单位2
			unit.setAreaCode(parentUnit.getAreaCode());
			unit.setTownCode(parentUnit.getTownCode());
			unit.setOther1Code(parentUnit.getOther1Code());
			unit.setOther2Code(code.length() == 1 ? "0" + code : code);
			break;
		default:
			// 修改没有的单位则不修改
			return null;
		}
		// 更新修改时间
		unit.setModifyDate(new Date());
		return unit;
	}

	/**
	 * 查询所有区县级和乡镇级单位
	 * @author 贺云<br>
	 * @date 2017年12月11日 下午3:19:37
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Unit> findUnitList() {
		String hql="from Unit where unitRank=:unitRank1 or unitRank=:unitRank2";
		Query query = getSession().createQuery(hql);
		query.setParameter("unitRank1", "area");
		query.setParameter("unitRank2", "town");
		return query.list();
	}
	
	/**
	 * 获取所有的区级警种单位
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午9:54:10
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getPoliceUnit() {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		List< Filter> filters=new ArrayList<Filter>();
		filters.add(Filter.eq("parentId", 1));
		filters.add(Filter.eq("isPoliceSection", 1));
		List<Unit> list=super.findList(null, filters, null);
		for (int i = 0; i < list.size(); i++) {
			Map< String, Object> map=new HashMap<String, Object>();
			map.put("id", list.get(i).getId());
			map.put("name", list.get(i).getUnitName());
			listMap.add(map);
		}
		return listMap;
	}
}
