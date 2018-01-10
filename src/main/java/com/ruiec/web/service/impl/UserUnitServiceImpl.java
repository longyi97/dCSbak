package com.ruiec.web.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Fetch;
import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.common.JsonReturn;
import com.ruiec.web.dto.UserUnitDTO;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.entity.User;
import com.ruiec.web.entity.UserUnit;
import com.ruiec.web.service.UnitService;
import com.ruiec.web.service.UserService;
import com.ruiec.web.service.UserUnitService;

/**
 * 管理员管理接口实现类
 * @date 2017年12月5日 下午4:02:09
 */
@Service
public class UserUnitServiceImpl extends BaseServiceImpl<UserUnit, Integer> implements UserUnitService {

	@Resource
	private UserService userService;
	
	@Resource
	private UnitService unitService;
	
	/**
	 * 查询单位管理员是否存在
	 * @date 2017年12月8日 上午10:12:29
	 */
	@Override
	@Transactional(readOnly=true)
	public JsonReturn checkByIds(Integer[] ids, Integer userId) {
		// 返回结果
		List<UserUnit> userUnitList = new ArrayList<UserUnit>();
		for (int id:ids) {
			// 查询单位管理员
			//构造复杂查询entity
			DetachedCriteria criteria=DetachedCriteria.forClass(UserUnit.class);
			//给entity中的复杂属性取别名
			criteria.createAlias("unit", "ut");
			//通过别名完成关联查询
			criteria.add(Restrictions.eq("ut.id",id));
			List<UserUnit> uus = findList(criteria,null,null,null);
			List<UserUnit> userUnits = new ArrayList<UserUnit>();
			// 移除与操作用户相同的管理员
			for (UserUnit uu : uus) {
				if (uu.getUser().getId() != userId) {
					userUnits.add(uu);
				}
			}
			if (userUnits.size() == 0) {
				return new JsonReturn(400, "没有可签收的管理员");
			}else {
				userUnitList.addAll(userUnits);
				/*if(userUnitList.size()==0){
				}else {
					//判断是否有相同的管理员
					boolean result = true;
					for (UserUnit us:userUnits) {
						for (UserUnit ul:userUnitList) {
							if(us.getUser().getId()==ul.getUser().getId()){
								result = false;
							}
						}
						if(result){//将不相同的添加到列表
							userUnitList.add(us);
						}
						result = true;
					}
				}*/
			}
		}
		return new JsonReturn(200, "ok", userUnitList);
	}
	
	/**
	 * 查询单位管理员是否存在
	 * @date 2017年12月8日 上午10:12:29
	 */
	@Override
	@Transactional(readOnly=true)
	public boolean checkById(Integer id) {
		// 返回结果
		//List<UserUnit> userUnitList = new ArrayList<UserUnit>();
		// 查询单位管理员
		// 构造复杂查询entity
		DetachedCriteria criteria = DetachedCriteria.forClass(UserUnit.class);
		// 给entity中的复杂属性取别名
		criteria.createAlias("unit", "ut");
		// 通过别名完成关联查询
		criteria.add(Restrictions.eq("ut.id", id));
		List<UserUnit> userUnits = super.findList(criteria, null, null, null);
		if (userUnits.size() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 新增管理员
	 * @date 2017年12月12日 上午10:25:16
	 */
	@Override
	@Transactional
	public JsonReturn saveUserUnit(Integer userId, Integer unitId) {
		try {
			User user=userService.get(userId);
			if(user==null){
				return new JsonReturn(500, "用户不存在");
			}
			Unit unit=unitService.get(unitId);
			if(unit==null){
				return new JsonReturn(500, "单位不存在");
			}
			//构建新增管理员的对象
			UserUnit userUnit=new UserUnit();
			userUnit.setUser(user);
			userUnit.setUnit(unit);
			save(userUnit);
			return new JsonReturn(200,"创建成功");
		} catch (Exception e) {
			return new JsonReturn(500,"创建失败");
		}
	}

	/**
	 * 管理员列表
	 * @date 2017年12月13日 上午9:35:59
	 */
	@Override
	@Transactional
	public void findUserUnitList(Page page, UserUnitDTO dto) {
		//构造复杂查询entity
				DetachedCriteria criteria=DetachedCriteria.forClass(UserUnit.class);
				//给entity中的复杂属性取别名
				criteria.createAlias("user", "u");
				criteria.createAlias("unit", "ut");
				//通过别名完成关联查询
				if(dto.getType()!=null&&dto.getSearchContent()!=null){
					if(dto.getType()==1){
						criteria.add(Restrictions.like("u.idCard","%"+dto.getSearchContent()+"%"));
					}
					if(dto.getType()==2){
						criteria.add(Restrictions.like("u.policeName","%"+dto.getSearchContent()+"%"));
					}
				}
				if(dto.getUnitId()!=null){
					criteria.add(Restrictions.eq("ut.id",dto.getUnitId()));
				}
				findByPage(page, criteria,Fetch.alias("u.unit", "unitAlias", JoinType.LEFT_OUTER_JOIN));
	}

	/**
	 * 查询用户关联管理单位
	 * @date 2017年12月17日 下午2:50:55
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Map<String, Object>> findListByUser(User user) {
		// 查询用户担任管理员的单位
		List<UserUnit> userUnitList = super.findList(null, Filter.eq("user", user), null);
		// 返回结果
		List<Map<String, Object>> unitList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> units = null;
		for(UserUnit userUnit:userUnitList){
			if("city".equals(userUnit.getUnit().getUnitRank())){
				// 用户为市级管理员，查询所有区级单位
				unitList = unitService.getNextUnit(userUnit.getUnit().getId());
				break;
			}
			if("area".equals(userUnit.getUnit().getUnitRank())){
				// 用户为区级管理员，查询所有镇级单位
				units = new ArrayList<Map<String,Object>>();
				units = unitService.getNextUnit(userUnit.getUnit().getId());
				unitList.addAll(units);
				// 去重
				unitList = new ArrayList<Map<String, Object>>(new LinkedHashSet<>(unitList));
			}
		}
		return unitList;
	}
	
	/**
	 * 获取用户对应单位的数据
	 * @date 2017年12月20日 下午4:38:52
	 */
	@Override
	@Transactional(readOnly=true)
	public List<Object[]> getUserUnits() {
		List<Object[]> userUnits=new ArrayList<Object[]>();
		String hql="SELECT USER_ID userId, listagg(unit_id,',')within group (order by USER_ID) unitId FROM T_SYS_USER_UNIT GROUP BY USER_ID";
		Query query = getSession().createSQLQuery(hql);
		userUnits = (List<Object[]>)query.list();
		return userUnits;
	}
}
