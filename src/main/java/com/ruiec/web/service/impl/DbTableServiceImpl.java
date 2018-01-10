package com.ruiec.web.service.impl;

import org.springframework.stereotype.Service;

import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.entity.DbTable;
import com.ruiec.web.service.DbTableService;

/**
 * 数据导入表服务实现类
 * @date 2017年12月18日 下午4:49:03
 */
@Service("dbTableServiceImpl")
public class DbTableServiceImpl extends BaseServiceImpl<DbTable, Integer> implements DbTableService {

}
