package cn.com.dayang.suyou.base.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;


/**
 * DAO层封装类class/interface
 * @see 
 * @since TR-V1.0
 * @author ivor
 * @version 
 */
@Repository(value="baseDAOImpl")
public class BaseDAOImpl extends SqlSessionDaoSupport implements BaseDAO{

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
	    super.setSqlSessionFactory(sqlSessionFactory);
    }
	
	public int insert(String key, Object object) {
		return this.getSqlSession().insert(key, object);
	}

	public int update(String key, Object object) {
		return this.getSqlSession().update(key, object);
	}

	public int delete(String key, Serializable id) {
		return this.getSqlSession().delete(key, id);
	}

	public int delete(String key, Object object) {
		return this.getSqlSession().delete(key, object);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String key, Object params) {
		return (T) this.getSqlSession().selectOne(key, params);
	}

	public <T> List<T> getList(String key) {
		return this.getSqlSession().selectList(key);
	}

	public <T> List<T> getList(String key, Object params) {
		return this.getSqlSession().selectList(key, params);
	}

	public <T> List<T> getListOfOffset(String key, Object params, int offset, int limit) {
		RowBounds rb = new RowBounds(offset, limit);
		return this.getSqlSession().selectList(key, params, rb);
	}

	public <T> List<T> getListOfPage(String key, Object params, int page, int limit) {
		page = (page < 1) ? 1 : page;
		int offset = (page - 1) * limit;
		return this.getListOfOffset(key, params, offset, limit);
	}
}
