package com.ruiec.web.service.impl;

import org.springframework.stereotype.Service;

import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.LoginLog;
import com.ruiec.web.service.LoginLogService;

@Service("loginLogServiceImpl")
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLog, Integer> implements LoginLogService{


}
