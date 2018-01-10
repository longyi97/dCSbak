package com.ruiec.web.service.impl;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.OperationLog;
import com.ruiec.web.service.OperationLogService;

/**
 * 操作日志服务实现类
 * Date 217-12-05 
 * */
@Service("operationLogServiceImpl")
public class OperationLogServiceImpl extends BaseServiceImpl<OperationLog, String> implements  OperationLogService{
        	  
	  
	  /**
	   * 插入操作日志
	   * date 2017-12-22
	   * */
	  public List<OperationLog> insertOperationLogs(){
		   try {
			 OperationLog operationLog=new OperationLog();
			 operationLog.setIp(InetAddress.getLocalHost().getHostAddress());
			 operationLog.setCreateDate(new Date());
			 
		} catch (Exception e) {
			e.printStackTrace();
			
			// TODO: handle exception
		}
		  return null;
	  }
}
