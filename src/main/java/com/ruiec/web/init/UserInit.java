
package com.ruiec.web.init;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.ruiec.web.common.UserUtil;
import com.ruiec.web.entity.User;
import com.ruiec.web.service.UserService;

/**
 * 字典类型初始化
 * @date 2017年12月7日 上午10:42:20
 */
public class UserInit implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger LOGGER = Logger.getLogger(UserInit.class);

	@Resource
	private UserService userService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			return;
		}
		LOGGER.info("初始化用户的全局静态变量开始 ...");
		try {
			if(UserUtil.getMap()==null||UserUtil.getMap().size()==0){
				HashMap<String, User> map=new HashMap<String, User>();
				List<User> dics=userService.getAll();
				for (User dic : dics) {
					if(dic!=null&&dic.getId()!=null){
						map.put(dic.getId().toString(), dic);
					}
				}
				UserUtil.setMap(map);
				LOGGER.info("初始化用户的全局静态变量成功，共找到"+UserUtil.getMap().size()+"个用户");
			}
		} catch (Exception e) {
			LOGGER.info("初始化用户的全局静态变量时，出现错误："+e);
		}
	}
	
}
