package com.ruiec.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.query.Sort;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.ControlPersonRelation;
import com.ruiec.web.entity.LeavePerson;
import com.ruiec.web.service.ControlPersonRelationService;
import com.ruiec.web.service.LeavePersonService;
/**
 * 离堰人员服务实现类
 * @author qinzhitian<br>
 * @date 2017年12月6日 下午8:17:34
 */
@Service("leavePersonServiceImpl")
public class LeavePersonServiceImpl extends BaseServiceImpl<LeavePerson, Integer> implements LeavePersonService{

	@Resource
	private ControlPersonRelationService controlPersonRelationService;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page findByPageOfLeavePerson(Page page){
		// 查询离堰人员列表
		page.add(Sort.asc("createDate"));
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LeavePerson.class,"leavePersonAlias");
		detachedCriteria.createAlias("user", "userAlias");
		detachedCriteria.createAlias("unit", "unitAlias");
		page = findByPage(page, detachedCriteria);
		List<LeavePerson> leavePersons = (List<LeavePerson>) page.getList();
		// 取出所有身份证
		String[] idCards = new String[10];
		int i = 0;
		for(LeavePerson leavePerson:leavePersons){
			idCards[i] = leavePerson.getIdCard();
			i++;
		}
		// 查询重点人关系
		DetachedCriteria detached = DetachedCriteria.forClass(ControlPersonRelation.class);
		detached.add(Restrictions.in("idCard", idCards));
		//detached.createAlias("", "");
		List<ControlPersonRelation> controlPersonRelations = controlPersonRelationService.findList(detached, null, null, null);

		List<LeavePerson> leavePersonList = new ArrayList<LeavePerson>();
		// 将重点人关系添加到对应的离堰人员实体
		for(LeavePerson leavePerson:leavePersons){
			for (ControlPersonRelation controlPersonRelation:controlPersonRelations) {
				if(controlPersonRelation.getIdCard().equals(leavePerson.getIdCard())){
					leavePerson.setControlPersonRelation(controlPersonRelation);
				}
			}
			leavePersonList.add(leavePerson);
		}
		
		page.setList(leavePersonList);
		return page;
	}
}
