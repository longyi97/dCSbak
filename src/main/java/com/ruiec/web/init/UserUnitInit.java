package com.ruiec.web.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ruiec.web.common.UserUnitUtil;
import com.ruiec.web.common.UserUtil;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.entity.User;
import com.ruiec.web.entity.UserUnit;
import com.ruiec.web.service.UnitService;
import com.ruiec.web.service.UserService;
import com.ruiec.web.service.UserUnitService;

/**
 * 管理员管理单位初始化
 * @author Senghor<br>
 * @date 2017年12月18日 上午11:20:30
 */
public class UserUnitInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(UserUnitInit.class);

	@Resource
	private UserService userService;
	@Resource
	private UnitService unitService;
	@Resource
	private UserUnitService userUnitService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		LOGGER.info("初始化管理员管理单位的全局静态变量开始 ...");
		try {
			if(UserUnitUtil.getMap()==null||UserUnitUtil.getMap().size()==0){
				//全局静态变量的类型
				HashMap<Integer, List<Map<String, Object>>> map=new HashMap<Integer, List<Map<String, Object>>>();
				//所有警员对应的单位数据
				List<Object[]> userUnits=userUnitService.getUserUnits();
				//数据大于一条时开始解析数据
				if (userUnits!=null && userUnits.size()>0) {
					for (int i = 0; i < userUnits.size(); i++) {
						//存储一个管理员所管理的单位
						List<Map<String, Object>> userMaps=new ArrayList<Map<String,Object>>();
						//获取管理员id
						Integer userId=Integer.valueOf(userUnits.get(i)[0].toString());
						//获取单位数组id
						String[] unitId=userUnits.get(i)[1].toString().split(",");
						for (int j = 0; j < unitId.length; j++) {
							Map<String, Object> userUnitMap=new HashMap<String, Object>();
							Unit unit=unitService.get(Integer.valueOf(unitId[j].toString()));
							userUnitMap.put("id", unit.getId());//单位id
							userUnitMap.put("unitRank", unit.getUnitRank());//单位级别
							userUnitMap.put("unitName", unit.getUnitName());//单位名称
							userMaps.add(userUnitMap);
						}
						map.put(userId, userMaps);
					}
				}
				UserUnitUtil.setMap(map);
				LOGGER.info("初始化管理员管理单位的全局静态变量成功，共找到"+UserUnitUtil.getMap().size()+"个管理员警员");
			}
		} catch (Exception e) {
			LOGGER.info("初始化管理员管理单位的全局静态变量时，出现错误："+e);
		}
	}
	
}