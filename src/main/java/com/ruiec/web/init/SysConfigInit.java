package com.ruiec.web.init;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;

import com.ruiec.server.common.entity.SysConfig;
import com.ruiec.server.common.service.SysConfigService;



/**
 * 
 * 版权所有：深圳源中瑞科技有限公司<br>
 * 网 址：www.ruiec.com<br>
 * 电 话：0755-33581131<br><br>
 * 
 * 系统配置项初始化
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2015年12月24日
 */
//@Component
public class SysConfigInit implements ApplicationListener<ContextRefreshedEvent>{

	@Resource
	SysConfigService sysConfigService;
	
	@SuppressWarnings("unchecked")
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() != null){
			return;
		}
		List<SysConfig> localList = sysConfigService.getAll();
		Map<String, SysConfig> localMap = new HashMap<String, SysConfig>();
		for(SysConfig localSysConfig : localList){
			localMap.put(localSysConfig.getName(), localSysConfig);
		}
		try {
			File file = new ClassPathResource("ruiec.xml").getFile();
			Document document = new SAXReader().read(file);
			List<Element> list = document.selectNodes("/ruiec/sysConfig");
			Iterator<Element> iterator = list.iterator();
			while(iterator.hasNext()){
				Element element = iterator.next();
				String name = element.attributeValue("name");
				String value = element.attributeValue("value");
				String desc = element.attributeValue("desc");
				if(localMap.get(name) == null){
					SysConfig localSysConfig = new SysConfig();
					localSysConfig.setName(name);
					localSysConfig.setValue(value);
					localSysConfig.setDesc(desc);
					sysConfigService.save(localSysConfig);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
		}
	}

}
