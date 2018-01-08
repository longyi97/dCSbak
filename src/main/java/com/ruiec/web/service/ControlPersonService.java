package com.ruiec.web.service;

import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.dto.ControlPersonDTO;
import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.entity.ControlPersonExtend;

/**
 * 重点人服务接口
 * @author Senghor<br>
 * @date 2017年12月1日 上午9:16:28
 */
public interface ControlPersonService extends BaseService<ControlPerson, String>{
	/**
	 * 获取重点人员列表分页
	 * @author Senghor<br>
	 * @date 2017年12月1日 上午9:30:08
	 */
	Page findByPage(Page page, ControlPerson controlPerson, ControlPersonDTO controlPersonDTO);
	
	/**
	 * 保存重点人数据
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午10:11:18
	 */
	ControlPerson save(ControlPerson controlPerson, ControlPersonExtend controlPersonExtend, ControlPersonDTO controlPersonDTO);
	
	/**
	 * 保存重点人员修改信息
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午10:13:45
	 */
	ControlPerson update(ControlPerson controlPerson,ControlPersonExtend controlPersonExtend, ControlPersonDTO controlPersonDTO);
	
	/**
	 * 根据身份证获取重点人员
	 * 
	 * @author bingo<br>
	 * @date 2017年12月25日 上午10:08:03
	 */
	ControlPerson getByIdCard(String idCard);
}