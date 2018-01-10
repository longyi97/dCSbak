package com.ruiec.web.service;

import java.util.List;
import java.util.Map;

import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.common.LoginUserUnit;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.entity.User;

/**
 * 警员信息服务
 * @date 2017年11月29日 下午10:34:14
 */
public interface UserService extends BaseService<User, Integer>{

	/**
	 * 登录
	 * @param User
	 * @return User
	 * @date 2017年11月29日 下午10:37:09
	 */
	public User login(User user);
	
	/**
	 * 根据单位查询下面所面所有的警员
	 * @date 2017年12月11日 上午9:58:54
	 */
	List<Map<String, Object>> getUnitPerson(Integer areaId,Integer townId);

	/**
	 * 根据姓名或者身份证查询用户信息
	 * @date 2017年12月11日 下午4:41:21
	 */
	public List<Map<String, Object>> findUserList(Page page, String searchContent);
	

	/**
	 * 根据登录用户获取单位数据
	 * @date 2017年12月22日 上午10:17:20
	 */
	public List<Unit> findUserUnit(User user, LoginUserUnit loginUserUnit,Integer isCity);
}
