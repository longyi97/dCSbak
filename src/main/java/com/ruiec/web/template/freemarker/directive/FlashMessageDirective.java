package com.ruiec.web.template.freemarker.directive;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ruiec.web.common.CommonParam;
import com.ruiec.web.util.FreemarkerUtils;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
@Component("flashMessageDirective")
public class FlashMessageDirective extends BaseDirective {

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String message = FreemarkerUtils.getAttribute(CommonParam.FLASH_MESSAGE_KEY, env, String.class);
		if(message == null)
			return;
		String script = "<script type=\"text/javascript\">"+
							"$(function(){"+
								"$.message('"+ message +"');"+
							"});"+
						"</script>";
		env.getOut().write(script);
		env.removeCustomAttribute(CommonParam.FLASH_MESSAGE_KEY);
	}

}
