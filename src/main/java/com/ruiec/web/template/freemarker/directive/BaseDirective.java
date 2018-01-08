package com.ruiec.web.template.freemarker.directive;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.query.Sort;
import com.ruiec.web.util.FreemarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public abstract class BaseDirective implements TemplateDirectiveModel {

	private static final String ID = "id";
	private static final String COUNT = "count";
	private static final String ORDERBY = "orderBy";
	protected static final String USE_CACHE = "useCache";

	@SuppressWarnings("rawtypes")
	protected Long getId(Map params){
		return FreemarkerUtils.getParam(ID, params, Long.class);
	}
	
	@SuppressWarnings("rawtypes")
	protected Integer getCount(Map params){
		return FreemarkerUtils.getParam(COUNT, params, Integer.class);
	}
	
	@SuppressWarnings("rawtypes")
	protected Boolean isUseCache(Map params){
		Boolean bool = FreemarkerUtils.getParam(USE_CACHE, params, Boolean.class);
		return bool == null? Boolean.TRUE: bool;
	}
	
	protected void processBody(String key, Object value, Environment env, TemplateDirectiveBody body) {
		try {
			TemplateModel templateModel = FreemarkerUtils.getVariable(key, env);
			FreemarkerUtils.setVariable(key, value, env);
			body.render(env.getOut());
			FreemarkerUtils.setVariable(key, templateModel, env);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("rawtypes")
	protected List<Filter> getFilters(Map params,Class<?> clazz ,String[] ignoreFields){
		List<Filter> filters = new ArrayList<Filter>();
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(clazz);
		for(PropertyDescriptor localPropertyDescriptor: propertyDescriptors){
			String name = localPropertyDescriptor.getName();
			if(!ArrayUtils.contains(ignoreFields, name)&&params.containsKey(name)){
				Class<?> fieldType = localPropertyDescriptor.getPropertyType();
				Object value = FreemarkerUtils.getParam(name, params, fieldType);
				filters.add(Filter.eq(name, value));
			}
		}
		return filters;
	}
	
	@SuppressWarnings("rawtypes")
	protected List<Sort> getSorts(Map params ,String[] ignoreFields){
		List<Sort> sorts = new ArrayList<Sort>();
		String orderString = FreemarkerUtils.getParam(ORDERBY, params, String.class);
		if(StringUtils.isNotBlank(orderString)){
			String[] items = orderString.split("\\s*,\\s*");
			for(String item: items){
				if(StringUtils.isNotBlank(item)){
					String[] sortString = item.split("\\s+");
					if(sortString.length==2){
						String fieldName = sortString[0];
						String sortType = sortString[1];
						if(ArrayUtils.contains(ignoreFields, fieldName)){
							continue;
						}
						if("asc".equals(sortType)){
							sorts.add(Sort.asc(fieldName));
						}else if("desc".equals(sortType)){
							sorts.add(Sort.desc(fieldName));
						}else{
							
						}
					}
				}
			}
		}
		return sorts;
	}
}