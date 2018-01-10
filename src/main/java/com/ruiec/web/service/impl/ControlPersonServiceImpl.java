
package com.ruiec.web.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Fetch;
import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.query.Page;
import com.ruiec.framework.server.support.query.Sort;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.dto.ControlPersonDTO;
import com.ruiec.web.entity.ControlPerson;
import com.ruiec.web.entity.ControlPersonExtend;
import com.ruiec.web.entity.Unit;
import com.ruiec.web.entity.User;
import com.ruiec.web.service.ControlPersonService;
import com.ruiec.web.service.DictionaryService;
import com.ruiec.web.service.DictionaryTypeService;
import com.ruiec.web.service.UnitService;
import com.ruiec.web.service.UserService;
import com.ruiec.web.util.DateUtil;

/**
 * 重点人员服务接口实现类
 * @author Senghor<br>
 * @date 2017年11月30日 上午9:01:37
 */
@Service("controlPersonServiceImpl")
public class ControlPersonServiceImpl extends BaseServiceImpl<ControlPerson, String> implements ControlPersonService{

	@Resource
	private UnitService unitService;
	@Resource
	private UserService usertService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private DictionaryTypeService dictionaryTypeService;

	/**
	 * 根据条件查询page返回页面
	 * @author Senghor<br>
	 * @date 2017年12月22日 下午1:43:46
	 */
	@Override
	@Transactional(readOnly = true)
	public Page findByPage(Page page, ControlPerson controlPerson, ControlPersonDTO controlPersonDTO) {
		// 复杂查询条件
		DetachedCriteria cp = DetachedCriteria.forClass(ControlPerson.class);
		cp.setFetchMode("controlPersonTypes", FetchMode.SELECT);
		// 根据人员类型查询数据
		if (controlPerson.getPersonnelType() != null) {
			page.add(Filter.eq("personnelType", controlPerson.getPersonnelType()));
		}
		// 根据性别查询
		if (StringUtils.isNotBlank(controlPerson.getSex())) {
			page.add(Filter.eq("sex", controlPerson.getSex()));
		}
		// 根据是否在控查询
		if (controlPerson.getIsControl() != null) {
			page.add(Filter.eq("isControl", controlPerson.getIsControl()));
		}
		// 根据是否列管查询
		if (StringUtils.isNotBlank(controlPerson.getColumnTubeState())) {
			page.add(Filter.eq("columnTubeState", controlPerson.getColumnTubeState()));
		}
		// 根据是否审核查询
		if (StringUtils.isNotBlank(controlPerson.getIsAuditKeyPersonColumnTub())) {
			page.add(Filter.eq("isAuditKeyPersonColumnTub", controlPerson.getIsAuditKeyPersonColumnTub()));
		}
		// 根据危险级别查询
		if (controlPerson.getDangerousLevel() != null) {
			page.add(Filter.eq("dangerousLevel", controlPerson.getDangerousLevel()));
		}
		if (controlPersonDTO.getOutType()!=null) {
			switch (controlPersonDTO.getOutType()) {
				case 1:
					//未下发
					page.add(Filter.eq("registerState","1"));
					break;
				case 2:
					//已下发
					String registerState[]={"3","4"};
					page.add(Filter.in("registerState",registerState));
					break;
				case 3:
					//已下发未完善
					page.add(Filter.eq("registerState","3"));
					break;
				case 4:
					//已下发已完善
					page.add(Filter.eq("registerState","4"));
					break;

				default:
					break;
			}
		}
		// 根据姓名查询
		if (StringUtils.isNotBlank(controlPerson.getName())) {
			page.add(Filter.like("name", controlPerson.getName()));
		}
		// 根据身份证号查询
		if (StringUtils.isNotBlank(controlPerson.getIdCard())) {
			page.add(Filter.like("idCard", controlPerson.getIdCard()));
		}
		// 根据时间段查询
		if (DateUtil.isNotDate(controlPersonDTO.getStartDate())) {
			// 开始时间
			page.add(Filter.ge("createDate", controlPersonDTO.getStartDate()));
		}
		if ( DateUtil.isNotDate(controlPersonDTO.getEndDate())) {
			// 结束时间
			page.add(Filter.le("createDate", DateUtil.addDateSecond(controlPersonDTO.getEndDate(), 59)));
		}
		//默认查询没有被逻辑删除的代码
		page.add(Filter.le("isDelete", 0));
		
		//根据单位id查询单位下的重点人
		if(controlPersonDTO.getUnitId() != null || controlPersonDTO.getUnitIds()!=null && controlPersonDTO.getUnitIds().size()>0){
			if (controlPersonDTO.getUnitId() != null) {
				// 根据单位得到所有下级单位id
				controlPersonDTO.getUnitIds().addAll((this.getAdminUnits(controlPersonDTO.getUnitId())));
			}
			cp.createAlias("unit", "u");
			cp.add(Restrictions.in("u.id", controlPersonDTO.getUnitIds().toArray()));
		}
		// 根据人员类别id查询下级所有数据
		if (controlPersonDTO.getPersonTypeId() != null) {
			// 判断是否有下级数据
			Integer[] personType = dictionaryService.findSonId(controlPersonDTO.getPersonTypeId());
			if (personType!=null&&personType.length>0) {
				cp.createAlias("controlPersonTypes", "c");
				cp.add(Restrictions.in("c.dictionaryId", personType));
			}
		}
		// 根据Id倒序排序
		page.add(Sort.desc("id"));
		super.findByPage(page, cp,
				Fetch.alias("user", "userAlias", JoinType.LEFT_OUTER_JOIN),
				Fetch.alias("controlPersonExtend", "controlPersonExtendAlias", JoinType.LEFT_OUTER_JOIN));
		
		return page;
	}
	
	/**
	 * 保存重点人数据
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午10:10:12
	 */
	@Override
	@Transactional(readOnly = true)
	public ControlPerson save(ControlPerson controlPerson, ControlPersonExtend controlPersonExtend,ControlPersonDTO controlPersonDTO) {
		// 创建新增时间
		controlPerson.setCreateDate(new Date());
		controlPersonExtend.setControlPerson(controlPerson);
		// 将重点人扩展放在重点人里进行
		controlPerson.setControlPersonExtend(controlPersonExtend);

		if (controlPersonDTO.getUnitId() == 1 && controlPersonDTO.getUserId() == null) {
			//未下发
			controlPerson.setRegisterState("1");
		}
		if (!controlPerson.emiptyControlPerson()) {
			//已下发未完善
			controlPerson.setRegisterState("3");
		}
		if (controlPerson.emiptyControlPerson()) {
			//已下发已完善
			controlPerson.setRegisterState("4");
		}
		//责任民警数据
		if (controlPersonDTO.getUserId()!=null) {
			User user=new User();
			user.setId(controlPersonDTO.getUserId());
			controlPerson.setUser(user);
		}
		if (controlPersonDTO.getUnitId()!=null) {
			Unit unit=new Unit();
			unit.setId(controlPersonDTO.getUnitId());
			controlPerson.setUnit(unit);
		}
		return controlPerson;
	}
	
	/**
	 * 保存重点人员修改信息
	 * @author Senghor<br>
	 * @date 2017年12月11日 上午10:10:12
	 */
	@Override
	@Transactional(readOnly = true)
	public ControlPerson update(ControlPerson controlPerson,ControlPersonExtend controlPersonExtend,ControlPersonDTO controlPersonDTO) {
		//修改修改数据时间
		controlPerson.setModifyDate(new Date());
		controlPersonExtend.setControlPerson(controlPerson);
		controlPerson.setControlPersonExtend(controlPersonExtend);
		
		if (controlPersonDTO.getUnitId() == 1 && controlPersonDTO.getUserId() == null) {
			//未下发
			controlPerson.setRegisterState("1");
		}
		if (!controlPerson.emiptyControlPerson()) {
			//已下发未完善
			controlPerson.setRegisterState("3");
		}
		if (controlPerson.emiptyControlPerson()) {
			//已下发已完善
			controlPerson.setRegisterState("4");
		}
		//责任民警数据
		if (controlPersonDTO.getUserId()!=null) {
			User user=usertService.get(controlPersonDTO.getUserId());
			controlPerson.setUser(user);
		}
		//责任单位数据
		Unit unit=unitService.get(controlPersonDTO.getUnitId());
		controlPerson.setUnit(unit);
		return controlPerson;
	}
	/**
	 * 根据单位id获取下级所有id
	 * @author Senghor<br>
	 * @date 2017年12月20日 下午10:24:24
	 */
	private List<Integer> getAdminUnits(Integer unitId) {
		List<Integer> adminUnits = new ArrayList<Integer>();
		List<Object> unitIds = unitService.findSonId(unitId);
		if (unitIds.size() > 0) {
			for (int i = 0; i < unitIds.size(); i++) {
				adminUnits.add(Integer.valueOf(unitIds.get(i).toString()));
			}
		}
		return adminUnits;
	}

	/**
	 * 根据身份证获取重点人员
	 * 
	 * @author bingo<br>
	 * @date 2017年12月25日 上午10:08:03
	 */
	@Override
	@Transactional(readOnly = true)
	public ControlPerson getByIdCard(String idCard) {
		List<Filter> filters = new ArrayList<Filter>();
		filters.add(Filter.eq("idCard", idCard));
		return get(filters);
//		String hql = "from ControlPerson cp where ID_CARD = :idCard";
//		return (ControlPerson)getSession().createQuery(hql).setParameter("idCard", idCard).list().get(0);
	}
	
}
