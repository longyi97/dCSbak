
package com.ruiec.web.service.impl;

import org.springframework.stereotype.Service;

import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.ControlPersonType;
import com.ruiec.web.service.ControlPersonTypeService;

/**
 * 重点人员服务接口实现类
 * @author Senghor<br>
 * @date 2017年11月30日 上午9:01:37
 */
@Service("controlPersonTypeServiceImpl")
public class ControlPersonTypeServiceImpl extends BaseServiceImpl<ControlPersonType, String> implements ControlPersonTypeService{
}
