package com.xiaocui.dao.hibernate4;

import java.util.List;
import java.util.Map;

import com.xiaocui.vo.Pager;

public interface IBaseDao<T> {
	/**
	 * 添加实体对象
	 * 
	 * @param entity
	 */
	public void add(T entity);

	/**
	 * 更新实体对象
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 通过实体ID，删除实体对象
	 * 
	 * @param id
	 */
	public void delete(int id);

	// public void delete(Serializable id);

	/**
	 * 通过实体ID，获取单个普通实体对象
	 * 
	 * @param id
	 * @return
	 */
	public T load(int id);

	// public T load(Serializable id);

	/**
	 * 获取所有普通实体对象
	 * 
	 * @param id
	 * @return
	 */
	// public List<T> list();
	//
	// public List<T> list(int id);
	//
	// public List<T> list(Serializable id);

	/**
	 * 基于别名和查询参数的普通列表对象
	 * 
	 * @param hql
	 *            查询语句
	 * @param args
	 *            查询参数
	 * @param alias
	 *            别名对象
	 * @return
	 */
	public List<T> listAlias(String hql, Object[] args,
			Map<String, Object> alias);

	public List<T> listAlias(String hql, Map<String, Object> alias);

	public List<T> list(String hql, Object[] args);

	public List<T> list(String hql, Object arg);

	public List<T> list(String hql);

	/**
	 * 获取所有分页实体对象
	 * 
	 * @param id
	 * @return
	 */
	// public Pager<T> find();
	//
	// public Pager<T> find(int id);
	//
	// public Pager<T> find(Serializable id);

	/**
	 * 基于别名和查询参数的分页列表对象
	 * 
	 * @param hql
	 *            查询语句
	 * @param args
	 *            查询参数
	 * @param alias
	 *            别名对象
	 * @return
	 */
	public Pager<T> findAlias(String hql, Object[] args,
			Map<String, Object> alias);

	public Pager<T> findAlias(String hql, Map<String, Object> alias);

	public Pager<T> find(String hql, Object[] args);

	public Pager<T> find(String hql, Object arg);

	public Pager<T> find(String hql);

	/**
	 * 基于别名参数查询一组对象
	 * 
	 * @param hql
	 * @param args
	 * @param alias
	 * @return
	 */
	public Object queryAliasObject(String hql, Object[] args,
			Map<String, Object> alias);

	public Object queryAliasObject(String hql, Map<String, Object> alias);

	/**
	 * 根据HQL语句查询一组对象
	 * 
	 * @param hql
	 * @param args
	 * @return
	 */
	public Object queryObject(String hql, Object[] args);

	public Object queryObject(String hql, Object arg);

	public Object queryObject(String hql);

	/**
	 * 根据HQL语句更新一组对象
	 * 
	 * @param hql
	 * @param args
	 */
	public void updateByHql(String hql, Object[] args);

	public void updateByHql(String hql, Object arg);

	public void updateByHql(String hql);

	public <N extends Object> List<N> listAliasBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity);

	public <N extends Object> List<N> listAliasBySql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity);

	/**
	 * 根据SQL查询对象，不包含关联对象
	 * 
	 * @param sql
	 *            查询语句
	 * @param args
	 *            查询条件
	 * @param clz
	 *            查询实体
	 * @param hasEntity
	 *            当前对象是否是Hibernate所管理的实体，如果不是，则需要使用setResultTransform查询
	 */
	public <N extends Object> List<N> listBySql(String sql, Object[] args,
			Class<?> clz, boolean hasEntity);

	public <N extends Object> List<N> listBySql(String sql, Object arg,
			Class<?> clz, boolean hasEntity);

	public <N extends Object> List<N> listBySql(String sql, Class<?> clz,
			boolean hasEntity);

	public Pager<Object> findAliasBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity);

	public Pager<Object> findAliasBySql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity);

	/**
	 * 根据SQL查询对象，不包含关联对象
	 * 
	 * @param sql
	 *            查询语句
	 * @param args
	 *            查询条件
	 * @param clz
	 *            查询实体
	 * @param hasEntity
	 *            当前对象是否是Hibernate所管理的实体，如果不是，则需要使用setResultTransform查询
	 */
	public Pager<Object> findBySql(String sql, Object[] args,
			Class<Object> clz, boolean hasEntity);

	public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz,
			boolean hasEntity);

	public Pager<Object> findBySql(String sql, Class<Object> clz,
			boolean hasEntity);

}
