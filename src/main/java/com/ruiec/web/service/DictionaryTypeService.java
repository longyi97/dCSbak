package com.ruiec.web.service;



import com.ruiec.framework.server.support.service.BaseService;
import com.ruiec.web.entity.DictionaryType;
/**
 * 字典类型接口
 * date 2017年11月30
 * */
public interface DictionaryTypeService extends BaseService<DictionaryType, Integer>{

	/**
	 * 修改字典数据是否默认
	 * @date 2017年12月2日 上午9:49:17
	 */
	public void updateIsUse(Integer id);
   
	
}
