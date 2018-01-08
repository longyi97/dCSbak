/*
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 */

package com.ruiec.web.init;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ruiec.web.common.UnitCodeUtil;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.service.UnitService;

/**
 * 单位编码全局静态变量初始化
 * @author 贺云<br>
 * @date 2017年12月7日 上午10:42:20
 */
public class UnitCodeInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(UnitCodeInit.class);

	@Resource
	private UnitService unitService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		LOGGER.info("初始化单位编码全局静态变量开始 ...");
		try {
			if(UnitCodeUtil.getMap()==null||UnitCodeUtil.getMap().size()==0){
				HashMap<String, Unit> map=new HashMap<String, Unit>();
				List<Unit> units=unitService.getAll();
				for (Unit u : units) {
					if(u!=null&&u.getId()!=null){
						map.put((u.getProvinceCode()+u.getCityCode()+u.getAreaCode()+u.getTownCode()+u.getOther1Code()+u.getOther2Code()), u);
					}
				}
				UnitCodeUtil.setMap(map);
				LOGGER.info("初始化单位编码全局静态变量成功，共找到"+UnitCodeUtil.getMap().size()+"个部门");
			}
		} catch (Exception e) {
			LOGGER.info("初始化单位编码全局静态变量时，出现错误："+e);
		}
	}
	
}
