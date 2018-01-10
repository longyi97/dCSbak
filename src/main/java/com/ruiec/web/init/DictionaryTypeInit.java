
package com.ruiec.web.init;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.web.common.DictionaryTypeUtil;
import com.ruiec.web.entity.DictionaryType;
import com.ruiec.web.service.DictionaryTypeService;

/**
 * 字典类型初始化
 * @author 贺云<br>
 * @date 2017年12月7日 上午10:42:20
 */
public class DictionaryTypeInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(DictionaryTypeInit.class);

	@Resource
	private DictionaryTypeService dictionaryTypeService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		LOGGER.info("初始化字典类型的全局静态变量开始 ...");
		try {
			if(DictionaryTypeUtil.getMap()==null||DictionaryTypeUtil.getMap().size()==0){
				HashMap<String, DictionaryType> map=new HashMap<String, DictionaryType>();
				List<DictionaryType> dics=dictionaryTypeService.findList(null, Filter.eq("isUse", 1), null);
				for (DictionaryType dic : dics) {
					map.put(dic.getItemCode(), dic);
				}
				DictionaryTypeUtil.setMap(map);
				LOGGER.info("初始化字典类型的全局静态变量成功，共找到"+DictionaryTypeUtil.getMap().size()+"种字典");
			}
		} catch (Exception e) {
			LOGGER.info("初始化字典类型的全局静态变量时，出现错误："+e);
		}
	}
	
}
