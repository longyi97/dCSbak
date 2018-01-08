package com.ruiec.web.template.freemarker.directive;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ruiec.web.common.EhcacheNames;
import com.ruiec.web.util.EhcacheUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

@Component("areaNameMethod")
public class AreaNameMethod implements TemplateMethodModel {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List args) throws TemplateModelException {
		if(args != null && args.size() > 0 && args.get(0) != null){
			String areaId = args.get(0).toString();
			String areaName = EhcacheUtils.get(EhcacheNames.AREA, areaId, String.class);
			return new SimpleScalar(areaName);
		}
		return null;
	}

}
