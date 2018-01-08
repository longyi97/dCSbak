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

import com.ruiec.web.common.DictionaryUtil;
import com.ruiec.web.entity.Dictionary;
import com.ruiec.web.service.DictionaryService;

/**
 * 字典类型初始化
 * @author 贺云<br>
 * @date 2017年12月7日 上午10:42:20
 */
public class DictionaryInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(DictionaryInit.class);

	@Resource
	private DictionaryService dictionaryService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		LOGGER.info("初始化字典数据的全局静态变量开始 ...");
		try {
			if(DictionaryUtil.getMap()==null||DictionaryUtil.getMap().size()==0){
				HashMap<String, Dictionary> map=new HashMap<String, Dictionary>();
				List<Dictionary> dics=dictionaryService.getAll();
				for (Dictionary dic : dics) {
					if(dic!=null&&dic.getId()!=null){
						map.put(dic.getId().toString(), dic);
					}
				}
				DictionaryUtil.setMap(map);
				LOGGER.info("初始化字典数据的全局静态变量成功，共找到"+DictionaryUtil.getMap().size()+"个字典数据");
			}
		} catch (Exception e) {
			LOGGER.info("初始化字典数据的全局静态变量时，出现错误："+e);
		}
	}
	
}
