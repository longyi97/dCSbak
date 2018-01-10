
package com.ruiec.web.init;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ruiec.web.common.UnitUtil;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.service.UnitService;

/**
 * 部门初始化
 * @author 贺云<br>
 * @date 2017年12月7日 上午10:42:20
 */
public class UnitInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(UnitInit.class);

	@Resource
	private UnitService unitService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		LOGGER.info("初始化部门的全局静态变量开始 ...");
		try {
			if(UnitUtil.getMap()==null||UnitUtil.getMap().size()==0){
				HashMap<String, Unit> map=new HashMap<String, Unit>();
				List<Unit> units=unitService.getAll();
				for (Unit u : units) {
					if(u!=null&&u.getId()!=null){
						map.put(u.getId().toString(), u);
					}
				}
				UnitUtil.setMap(map);
				LOGGER.info("初始化部门的全局静态变量成功，共找到"+UnitUtil.getMap().size()+"个部门");
			}
		} catch (Exception e) {
			LOGGER.info("初始化部门的全局静态变量时，出现错误："+e);
		}
	}
	
}
