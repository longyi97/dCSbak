
package com.ruiec.web.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Fetch;
import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.common.JsonReturn;
import com.ruiec.web.dto.IssuedJSONDTO;
import com.ruiec.web.entity.ControlPersonAlarm;
import com.ruiec.web.entity.ControlPersonInstructi;
import com.ruiec.web.entity.User;
import com.ruiec.web.entity.UserUnit;
import com.ruiec.web.service.ControlPersonAlarmService;
import com.ruiec.web.service.ControlPersonInstructiService;
import com.ruiec.web.service.UnitService;
import com.ruiec.web.service.UserService;
import com.ruiec.web.service.UserUnitService;

/**
 * 重点人员预警指令服务接口实现类
 * @date 2017年11月30日 上午9:01:37
 */
@Repository("controlPersonInstructiServiceImpl")
public class ControlPersonInstructiServiceImpl extends BaseServiceImpl<ControlPersonInstructi, Integer> implements ControlPersonInstructiService{

	@Resource
	private UserUnitService userUnitService;
	@Resource
	private UserService userService;
	@Resource
	private ControlPersonAlarmService controlPersonAlarmService;
	@Resource
	private UnitService unitService;
	/**
	 * 预警下发
	 * @date 2017年12月8日 下午4:23:33
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public JsonReturn issued(Integer[] ids, Integer id, User user, String content) {
		
		//1、查询管理员id
		JsonReturn jsonReturn = this.userUnitService.checkByIds(ids,user.getId());
		if (jsonReturn.getCode() == 400) {
			return jsonReturn;
		}
		List<UserUnit> userUnitList = (List<UserUnit>) jsonReturn.getData();
		
		user = userService.get(user.getId(),null,Fetch.alias("unit", "unitAlias", JoinType.LEFT_OUTER_JOIN));
		
		Date date = new Date();
		// 2、指令和反馈以JSON进行追加
		IssuedJSONDTO issuedJSONDTO = new IssuedJSONDTO();
		issuedJSONDTO.setContent(content);
		issuedJSONDTO.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
		issuedJSONDTO.setDepartment(user.getUnit().getUnitName());
		issuedJSONDTO.setPoliceName(user.getPoliceName());
		issuedJSONDTO.setType("1");
		JSONArray jsonObject = JSONArray.fromObject(issuedJSONDTO);
		//3、查询预警数据
		ControlPersonAlarm controlPersonAlarm = controlPersonAlarmService.get(id);
		if (controlPersonAlarm.getDistributeStatus() == 0) {
			// 4、修改预警下发状态
			controlPersonAlarm.setDistributeStatus(1);
			controlPersonAlarmService.update(controlPersonAlarm);
		}
		// 5、查询预警指令已下发单位
		/*List<ControlPersonInstructi> cpiList = findList(null, Filter.eq("controlPersonAlarm", controlPersonAlarm), null);
		for (UserUnit userUnit:userUnitList) {
			for (ControlPersonInstructi cpi : cpiList) {
				if (cpi.getInstructionRecipientUnitId()==userUnit.getUnit().getId()) {
					return new JsonReturn(400,userUnit.getUnit().getUnitName()+"已下发");
				}
			}
		}*/
		// 6、新增下发预警指令
		for (UserUnit userUnit:userUnitList) {
			// 添加下发实体信息
			ControlPersonInstructi controlPersonInstructi = new ControlPersonInstructi();
			// 身份证号  
			controlPersonInstructi.setControlPerson(controlPersonAlarm.getControlPerson());
			// 动态信息 
			controlPersonInstructi.setDynamicsInformation(controlPersonAlarm.getDynamicInfoName());
			// 四色预警，1是红色2是橙色3是黄色4是橙色  
			controlPersonInstructi.setFourColourWarning(controlPersonAlarm.getWarningLevel());
			// 指令状态 0为未签收 1为已签收 2为待审核 3未审核通过 4未审核拒绝 
			controlPersonInstructi.setInstructionsState(0);
			// 下发人的民警ID 
			controlPersonInstructi.setIssuedPolicePrikey(user.getId());
			// 下发人的单位ID 
			controlPersonInstructi.setIssuedPoliceUnitId(user.getUnit().getId());
			controlPersonInstructi.setCreateDate(date);
			// 关联预警
			controlPersonInstructi.setcontrolPersonAlarm(controlPersonAlarm);
			// 指令和反馈以JSON进行追加
			controlPersonInstructi.setInstructionFeedbackInformati(jsonObject.toString());
			// 指令接受人的民警ID 
			controlPersonInstructi.setInstructionRecipientPrikey(userUnit.getUser().getId());
			// 指令接受人的单位ID
			controlPersonInstructi.setInstructionRecipientUnitId(userUnit.getUnit().getId());
			save(controlPersonInstructi);
		}
		
		return new JsonReturn(200, "下发预警成功");
	}

	/**
	 * 预警指令反馈
	 * @date 2017年12月9日 下午4:54:20
	 */
	@Override
	@Transactional
	public JsonReturn feedBack(Integer id, String json, User user) {
		// 查询指令信息
		ControlPersonInstructi controlPersonInstructi = get(id);
		if (controlPersonInstructi != null) {
			if (controlPersonInstructi.getInstructionsState() != 1) {
				return new JsonReturn(400, "预警指令已反馈");
			}
			controlPersonInstructi.setModifyDate(new Date());
			//追加指令信息
			IssuedJSONDTO issuedJSONDTO = new IssuedJSONDTO();
			issuedJSONDTO.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(controlPersonInstructi.getModifyDate()));
			issuedJSONDTO.setDepartment(user.getUnit().getUnitName());
			issuedJSONDTO.setJson(JSONObject.fromObject(json));
			issuedJSONDTO.setPoliceName(user.getPoliceName());
			issuedJSONDTO.setType("2");
			JSONObject jsonObject = JSONObject.fromObject(issuedJSONDTO);
			JSONArray jsonArray = JSONArray.fromObject(controlPersonInstructi.getInstructionFeedbackInformati());
			jsonArray.add(jsonObject);
			controlPersonInstructi.setInstructionFeedbackInformati(jsonArray.toString());
			//修改指令状态()
			controlPersonInstructi.setInstructionsState(2);
			update(controlPersonInstructi);
			return new JsonReturn(200, "反馈成功");
		}
		return new JsonReturn(400, "反馈失败");
	}

	/**
	 * 预警反馈详情
	 * @date 2017年12月9日 下午4:54:20
	 */
	@Override
	@Transactional
	public List<JSONObject> findFeedBackDetail(Integer id) {
		// 根据id查询预警信息详情
		ControlPersonInstructi cpi = get(id);
		// 取出指令信息
		String jsonStr = cpi.getInstructionFeedbackInformati();
		//String[] strings = jsonStr.split(";");
		List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		// 取出反馈指令
		for (Object object : jsonArray) {
			JSONObject jsonObject = JSONObject.fromObject(object);
			if(jsonObject.getInt("type")==2){
				jsonObjects.add(jsonObject.getJSONObject("json"));
			}
		}
		/*List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = null;
		// 数据类型转换
		for(JSONObject json:jsonObjects){
			map = new HashMap<String, Object>();
			Iterator<String> iterator = json.keys(); 
			String key = null;
			while(iterator.hasNext()){
				key=iterator.next();
				map.put("key", json.get(key));
			}
			
		}*/
		return jsonObjects;
	}
	
	/**
	 * 获取最后一次反馈信息
	 * @date 2017年12月13日 上午11:19:38
	 */
	@Override
	@Transactional
	public JSONObject findLastFeedBack(Integer id) {
		// 根据id查询预警信息详情
		ControlPersonInstructi cpi = get(id);
		// 取出指令信息
		String jsonStr = cpi.getInstructionFeedbackInformati();
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return JSONObject.fromObject(jsonArray.get(jsonArray.size()-1));
	}
	/**
	 * 查询可下发目标
	 * @date 2017年12月17日 下午4:14:28
	 */
	@Override
	@Transactional
	public List<Map<String, Object>> findIssued(User user) {
		// 查询用户担任管理员的单位
		List<UserUnit> userUnitList = userUnitService.findList(null,Filter.eq("user", user), null);
		// 返回结果
		List<Map<String, Object>> unitList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> units = null;
		if(userUnitList.size()>0){
			for (UserUnit userUnit : userUnitList) {
				if ("city".equals(userUnit.getUnit().getUnitRank())) {
					// 用户为市级管理员，查询所有区级单位
					unitList = unitService.getNextUnit(userUnit.getUnit().getId());
					break;
				}
				if ("area".equals(userUnit.getUnit().getUnitRank())) {
					// 用户为区级管理员，查询所有镇级单位
					units = new ArrayList<Map<String, Object>>();
					units = unitService.getNextUnit(userUnit.getUnit().getId());
					unitList.addAll(units);
					// 去重
					unitList = new ArrayList<Map<String, Object>>(new LinkedHashSet<>(unitList));
				}
			}
			if (unitList.size()==0) {
				// 用户为管理员,下级无可下发单位，查询用户可下发警员
				for (UserUnit userUnit : userUnitList) {
					if ("area".equals(userUnit.getUnit().getUnitRank())) {
						// 用户为市级管理员，查询所有区级单位
						units = userService.getUnitPerson(userUnit.getUnit().getId(), null);
						unitList.addAll(units);
					}
					if ("town".equals(userUnit.getUnit().getUnitRank())) {
						// 用户为区级管理员，查询所有镇级单位
						units = new ArrayList<Map<String, Object>>();
						units = userService.getUnitPerson(null, userUnit.getUnit().getId());
						unitList.addAll(units);
					}
				}
				// 去重
				unitList = new ArrayList<Map<String, Object>>(new LinkedHashSet<>(unitList));
			}
		}
		return unitList;
	}

	/**
	 * 预警指令详情
	 * @date 2017年12月18日 下午4:54:20
	 */
	@Override
	@Transactional
	public JSONObject findInstructionDetail(Integer id) {
		// 根据id查询预警信息详情
		ControlPersonInstructi cpi = get(id);
		// 取出指令信息
		String jsonStr = cpi.getInstructionFeedbackInformati();
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return JSONObject.fromObject(jsonArray.get(0));
	}

	/**
	 * 签收
	 * @date 2017年12月18日 下午9:00:05
	 */
	@Override
	@Transactional
	public JsonReturn updateInstructionsState(Integer id, Integer state) {
		JsonReturn jsonReturn = null;
		ControlPersonInstructi cpi = super.get(id);
		int instructionsState = cpi.getInstructionsState();
		if (state == 1) {
			// 签收时
			if (instructionsState == 0) {
				cpi.setInstructionsState(state);
				super.update(cpi);
				jsonReturn = new JsonReturn(200, "签收成功");
			} else {
				jsonReturn = new JsonReturn(400, "预警已签收");
			}
		}else {
			jsonReturn = new JsonReturn(400, "签收失败");
		}
		return jsonReturn;
	}
	
	/**
	 * 审核
	 * @date 2017年12月18日 下午9:00:05
	 */
	@Override
	@Transactional
	public JsonReturn audit(Integer id, Integer state, String content) {
		JsonReturn jsonReturn = null;
		ControlPersonInstructi cpi = super.get(id);
		int instructionsState = cpi.getInstructionsState();
		// 审核
		if (instructionsState == 2) {
			cpi.setInstructionsState(state);
			if (state==4) {
				// 取出指令信息
				String jsonStr = cpi.getInstructionFeedbackInformati();
				JSONArray jsonArray = JSONArray.fromObject(jsonStr);
				// 拼接反馈信息
				int last = jsonArray.size()-1;
				Object object = jsonArray.get(last);
				JSONObject json = JSONObject.fromObject(object);
				json.put("content", content);
				jsonArray.remove(last);
				jsonArray.add(json);
				cpi.setInstructionFeedbackInformati(jsonArray.toString());
			}
			// 修改预警下发指令
			super.update(cpi);
			jsonReturn = new JsonReturn(200, "审核成功");
		} else if (instructionsState == 0) {
			jsonReturn = new JsonReturn(400, "预警未签收");
		} else if (instructionsState == 1) {
			jsonReturn = new JsonReturn(400, "预警未反馈");
		} else {
			jsonReturn = new JsonReturn(400, "预警已审核");
		}
		return jsonReturn;
	}
}
