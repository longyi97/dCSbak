package com.ruiec.web.service;

import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.entity.LeavePerson;

/**
 * 离堰人员服务接口
 * @date 2017年12月6日 下午8:15:13
 */
public interface LeavePersonService extends BaseService<LeavePerson, Integer>{

	public Page findByPageOfLeavePerson(Page page);
}
