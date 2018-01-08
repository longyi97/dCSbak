package com.ruiec.web.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.DictionaryType;
import com.ruiec.web.service.DictionaryTypeService;


/**
 * @author yuankai
 * date 2017年11月30
 *  字典类型服务类
 * */
@Service("dictionaryTypeServiceImpl")
public class DictionaryTypeServiceImpl  extends BaseServiceImpl<DictionaryType, Integer> implements DictionaryTypeService{

	/**
	 * 修改字典数据默认状态
	 * @author yuankai<br>
	 * @date 2017年12月2日 上午9:49:17
	 */
	@Override
	@Transactional
	public void updateIsUse(Integer id) {
		// 查询字典类型
			DictionaryType dictionaryType = get(id);
				if(dictionaryType.getisUse()==1){
					dictionaryType.setisUse(0);
				}else {
					dictionaryType.setisUse(1);
				}
			dictionaryType.setModifyDate(new Date());
		// 修改字典类型
		update(dictionaryType);
		
	}
	
	
}
