package com.ruiec.web.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruiec.framework.server.support.query.Filter;
import com.ruiec.framework.server.support.service.impl.BaseServiceImpl;
import com.ruiec.web.common.JsonReturn;
import com.ruiec.web.entity.Db;
import com.ruiec.web.entity.DbField;
import com.ruiec.web.entity.DbTable;
import com.ruiec.web.service.DbService;
import com.ruiec.web.service.DbTableService;

/**
 * 数据源服务实现类
 * 
 * @date 2017年12月18日 下午4:49:03
 */
@Service("dbServiceImpl")
public class DbServiceImpl extends BaseServiceImpl<Db, Integer> implements DbService {
	
	private static final Logger LOGGER = Logger.getLogger(DbServiceImpl.class);
	@Resource
	private DbTableService dbTableService;
	@Value("${jdbc.driver}")
	private String driver;
	@Value("${jdbc.url}")
	private String url;
	@Value("${jdbc.username}")
	private String username;
	@Value("${jdbc.password}")
	private String password;
	// 每次数据导入条数
	private int size = 100;
	/**
	 * 保存数据源
	 * @date 2017年12月18日 下午5:46:15
	 */
	@Override
	@Transactional
	public JsonReturn saveDb(Db db) {
		try {
			Connection conn = getConnection(db);
			if (conn!=null) {
				conn.close();
				db.setCreateDate(new Date());
				super.save(db);
				return new JsonReturn(200, "保存成功");
			}
			return new JsonReturn(400, "保存失败,无法建立链接");
		} catch (SQLException e) {
			return new JsonReturn(400, "保存失败,无法建立链接");
		} catch (ClassNotFoundException e) {
			return new JsonReturn(400, "保存失败,找不到方法");
		} catch (Exception e) {
			return new JsonReturn(400, "保存失败");
		}
	}
	/**
	 * 修改数据源
	 * @date 2017年12月18日 下午5:46:15
	 */
	@Override
	@Transactional
	public JsonReturn updateDb(Db db) {
		try {
			Connection conn = getConnection(db);
			if (conn!=null) {
				conn.close();
				db.setCreateDate(new Date());
				super.update(db);
				return new JsonReturn(200, "保存成功");
			}
			return new JsonReturn(400, "修改失败,无法建立链接");
		} catch (SQLException e) {
			return new JsonReturn(400, "修改失败,无法建立链接");
		} catch (ClassNotFoundException e) {
			return new JsonReturn(400, "修改失败,找不到方法");
		} catch (Exception e) {
			return new JsonReturn(400, "修改失败");
		}
	}

	/**
	 * 检查是否可以建立数据库连接
	 * @date 2017年12月18日 下午5:57:54
	 */
	public Connection getConnection(Db db) throws Exception {
		Connection conn = null;
		Integer type = db.getType();// 数据库类型
		String urll = db.getUrl();// 数据库链接地址
		String user = db.getUserName();// 用户名
		String pass = db.getPassword();// 密码
		if (type == 1) {
			// 加载mysql驱动程序
			Class.forName("com.mysql.jdbc.Driver");
		} else if (type == 2) {
			// 加载Oracle驱动程序
			Class.forName("oracle.jdbc.OracleDriver");
		}
		// 建立连接
		conn = DriverManager.getConnection(urll, user, pass);
		return conn;

	}
	/**
	 * 数据导入(库)
	 * @date 2017年12月21日 上午9:56:40
	 * @param id 数据源id
	 */
	@Override
	@Transactional
	public JsonReturn dataImport(Integer id) {
		try {
			// 查询数据源
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DbTable.class);
			detachedCriteria.createCriteria("db");
			detachedCriteria.createCriteria("dbFields");
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("isUse", 1));
			filters.add(Filter.eq("db.id", id));
			List<DbTable> dbTables = dbTableService.findList(detachedCriteria, null, filters, null);
			// 导入数据
			for (DbTable dbTable : dbTables) {
				Long count = getCount(dbTable);
				LOGGER.info("表"+dbTable.getTableName()+"可导入数据为："+count+"条");
				saveData(count,dbTable);
			}
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return new JsonReturn(400,"导入数据失败");
		}
		return new JsonReturn(200,"导入数据成功");
	}
	/**
	 * 数据导入(库)
	 * @date 2017年12月21日 上午9:56:40
	 * @param id 数据源id
	 */
	@Override
	@Transactional
	public JsonReturn tableImport(Integer id) {
		try {
			// 查询数据源
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DbTable.class);
			detachedCriteria.createCriteria("db");
			detachedCriteria.createCriteria("dbFields");
			List<Filter> filters = new ArrayList<Filter>();
			filters.add(Filter.eq("isUse", 1));
			filters.add(Filter.eq("id", id));
			List<DbTable> dbTables = dbTableService.findList(detachedCriteria, null, filters, null);
			// 导入数据
			for (DbTable dbTable : dbTables) {
				Long count = getCount(dbTable);
				LOGGER.info("表"+dbTable.getTableName()+"可导入数据为："+count+"条");
				saveData(count,dbTable);
			}
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return new JsonReturn(400,"导入数据失败");
		}
		return new JsonReturn(200,"导入数据成功");
	}
	/**
	 * 查询总条数
	 * @date 2017年12月21日 上午9:56:12
	 */
	public Long getCount(DbTable dbTable) throws SQLException {
		Long count = 0L;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// 获取连接
			conn = getConnection(dbTable.getDb());
			pst = null;
			res = null;
			String sql = "select count(*) from"+dbTable.getTableName();
			pst = conn.prepareStatement(sql);
			// 执行语句
			res = pst.executeQuery();
			res.next();
			count = res.getLong(1);
		} catch (Exception e) {
			throw new RuntimeException("总数量查询失败");
		} finally {
			// 关闭连接
			if (res != null) {
				res.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return count;
	}
	/**
	 * 保存数据
	 * @date 2017年12月20日 下午3:12:08
	 */
	@Transactional
	public boolean saveData(Long count,DbTable dbTable) throws SQLException {
		Connection conn1 = null;
		Connection conn2 = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		try {
			// 获取连接
			conn1 = getConnection(dbTable.getDb());
			Class.forName(driver);
			conn2 = DriverManager.getConnection(url, username, password);
			// 总页数
			int pageCount = (int)(count/size);
			if(count % size>0){
				pageCount+=1;
			}
			List<DbField> dbFields = new ArrayList<DbField>();
			dbFields = dbTable.getDbFields();
			for (int i = 0; i < pageCount; i++) {
				if (dbTable.getDb().getType()==1) {
					// mysql数据库分页查询
					int begin = i*size;
					String sql = "select * from " + dbTable.getTableName() + " limit "+ begin +", " + size;
					/*for (DbField dbField : dbTable.getDbFields()) {
						sql = sql + dbField.getFieldName()+" ";
					}*/
					
					pst = conn1.prepareStatement(sql);
					// 执行语句
					res = pst.executeQuery();
				} else if (dbTable.getDb().getType()==2) {
					// oracle数据库分页查询
					long begin = i*size;
					long end = (i+1)*size;
					String sql = "SELECT * FROM "
							+ "(SELECT a.*, ROWNUM rn FROM "
							+ "(SELECT * FROM " + dbTable.getTableName() + ") a " 
							+ "WHERE ROWNUM <= " + end + ") "
							+ "WHERE rn >= " + begin;
					pst = conn1.prepareStatement(sql);
					// 执行语句
					res = pst.executeQuery();
				}
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = null;
				// 获取查询结果
				while (res.next()) {
					map = new HashMap<String, Object>();
					for (DbField dbField : dbFields) {
						map.put(dbField.getAssociationFieldName(), res.getObject(dbField.getFieldName()));
					}
					LOGGER.info("保存数据：" + map.toString());
					mapList.add(map);
				}
				// 关闭res与pst
				if (res != null) {
					res.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (mapList.size() > 0) {
					// 查询主键最大值
					String sql = "select max(PRIKEY) from "+dbTable.getAssociationTableName();
					pst = conn2.prepareStatement(sql);
					res = pst.executeQuery();
					long id = 1;
					if (res.next()) {
						id = res.getLong(1) + 100;
					}
					// 关闭res与pst
					if (res != null) {
						res.close();
					}
					if (pst != null) {
						pst.close();
					}
					// 保存到数据库
					sql = new String();
					sql = "insert into " + dbTable.getAssociationTableName() +" ( PRIKEY";
					for (int j = 0; j < dbFields.size(); j++) {
						DbField dbField = dbFields.get(j);
						sql = sql + ", " + dbField.getAssociationFieldName();
					}
					sql = sql + ") values ";
					int k = 0;
					for (Map<String, Object> map2 : mapList) {
						k++;
						id = id + 1;
						sql = sql + "( " + id;
						for (int j = 0; j < dbFields.size(); j++) {
							DbField dbField = dbFields.get(j);
							if (dbField.getFieldType()==1) {
								// 字符串
								sql = sql + ", '" + map2.get(dbField.getAssociationFieldName()) + "'";
							} else if (dbField.getFieldType()==2) {
								// 日期
								SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
								sql = sql + ", to_date('" + dateFormat.format(map2.get(dbField.getAssociationFieldName())) + "','yyyy-MM-dd')";
							} else {
								// 整型
								sql = sql + ", " + map2.get(dbField.getAssociationFieldName());
							}
						}
						if (k==mapList.size()) {
							sql = sql + ")";
						} else {
							sql = sql + "), ";
						}
					}
					pst = conn2.prepareStatement(sql);
					// 执行语句 
					pst.executeUpdate();
					// 关闭pst
					if (pst != null) {
						pst.close();
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			if (res != null) {
				res.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (conn1 != null) {
				conn1.close();
			}
			if (conn2 != null) {
				conn2.close();
			}
		}
		return false;
	}
}
