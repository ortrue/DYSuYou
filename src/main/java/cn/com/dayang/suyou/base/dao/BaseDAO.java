package cn.com.dayang.suyou.base.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseDAO {

	public int insert(String key, Object object);

	public int update(String key, Object object);

	public int delete(String key, Serializable id);

	public int delete(String key, Object object);

	public <T> T get(String key, Object params);

	public <T> List<T> getList(String key);

	public <T> List<T> getList(String key, Object params);

	public <T> List<T> getListOfOffset(String key, Object params, int offset, int limit);

	public <T> List<T> getListOfPage(String key, Object params, int page, int limit);
}
