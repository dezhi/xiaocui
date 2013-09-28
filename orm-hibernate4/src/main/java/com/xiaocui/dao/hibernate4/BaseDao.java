package com.xiaocui.dao.hibernate4;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.xiaocui.vo.PageContext;
import com.xiaocui.vo.Pager;

@SuppressWarnings("unchecked")
public class BaseDao<T> implements IBaseDao<T> {

	private SessionFactory sessionFactory;
	/**
	 * 创建一个Class泛型对象
	 */
	private Class<T> clz;

	/**
	 * 通过一个Class泛型对象，获取泛型的Class
	 * 
	 * @return
	 */
	public Class<T> getClz() {
		if (clz == null) {
			// 获取泛型的Class对象
			clz = ((Class<T>) (((ParameterizedType) (this.getClass()
					.getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clz;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		// 事务必须是开启的，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 设置排序
	 * 
	 * @param hql
	 * @return
	 */
	private String setSort(String hql) {
		// 排序字段
		String sort = PageContext.getSort();
		// 排序方式
		String order = PageContext.getOrder();
		if (sort != null && !"".equals(sort.trim())) {
			hql += " order by " + sort;
			if (!"desc".equals(order)) {
				hql += " asc";
			} else {
				hql += " desc";
			}
		}
		return hql;
	}

	/**
	 * 设置普通参数查询
	 * 
	 * @param query
	 * @param args
	 */
	private void setParameter(Query query, Object[] args) {
		// 1 参数不能为空
		// 2 参数数大于0
		if (args != null && args.length > 0) {
			// 3 JDBC从1开始，Hibernate从0开始
			int index = 0;
			for (Object arg : args) {
				query.setParameter(index++, arg);
			}
		}
	}

	/**
	 * 设置别名参数查询
	 * 
	 * @param query
	 * @param alias
	 */
	private void setAliasParameter(Query query, Map<String, Object> alias) {
		// 1 获取的别名参数不能为空
		if (alias != null) {
			// 2 获取别名参数的key
			Set<String> keys = alias.keySet();
			for (String key : keys) {
				Object value = alias.get(key);

				if (value instanceof Collection) {
					// 查询条件是列表集合
					// query.setParameterList(key, (Collection) value);
					query.setParameterList(key, (Collection<Object>) value);
				} else {
					query.setParameter(key, value);
				}
			}
		}
	}

	/**
	 * 组装HQL
	 * 
	 * @param hql
	 * @return
	 */
	private String getCountByHql(String hql, boolean isHql) {

		String fetch = hql.substring(hql.indexOf("from"));

		String count = "select count(*) " + fetch;

		if (isHql)
			count = count.replaceAll("fetch", "");

		return count;
	}

	/**
	 * 设置分页
	 * 
	 * @param query
	 * @param pager
	 */
	private void setPager(Query query, Pager<T> pager) {
		// 获取页面起始页
		Integer pageOffset = PageContext.getOffset();

		// 获取页面大小
		Integer pageSize = PageContext.getSize();

		// 获取页面起始页不能小于0
		if (pageOffset == null || pageOffset < 0)
			pageOffset = 0;

		if (pageSize == null || pageSize < 0)
			pageSize = 15;

		pager.setOffset(pageOffset);

		pager.setSize(pageSize);

		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}

	private void setPagers(Query query, Pager<Object> pager) {
		// 获取页面起始页
		Integer pageOffset = PageContext.getOffset();

		// 获取页面大小
		Integer pageSize = PageContext.getSize();

		// 获取页面起始页不能小于0
		if (pageOffset == null || pageOffset < 0)
			pageOffset = 0;

		if (pageSize == null || pageSize < 0)
			pageSize = 15;

		pager.setOffset(pageOffset);

		pager.setSize(pageSize);

		query.setFirstResult(pageOffset).setMaxResults(pageSize);
	}

	@Override
	public void add(T entity) {
		this.getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		this.getSession().update(entity);
	}

	@Override
	public void delete(int id) {
		getSession().delete(this.load(id));
	}

	// @Override
	// public void delete(Serializable id) {
	// this.getSession().delete(this.load(id));
	// }

	@Override
	public T load(int id) {
		return (T) getSession().load(getClz(), id);
	}

	@Override
	public List<T> listAlias(String hql, Object[] args,
			Map<String, Object> alias) {
		// 1 设置排序
		hql = setSort(hql);

		Query query = this.getSession().createQuery(hql);

		// 2 设置别名参数
		setAliasParameter(query, alias);

		// 3 设置参数
		setParameter(query, args);

		return query.list();
	}

	@Override
	public List<T> listAlias(String hql, Map<String, Object> alias) {
		return this.listAlias(hql, null, alias);
	}

	@Override
	public List<T> list(String hql, Object[] args) {
		return this.listAlias(hql, args, null);
	}

	@Override
	public List<T> list(String hql, Object arg) {
		return this.list(hql, new Object[] { arg });
	}

	@Override
	public List<T> list(String hql) {
		return this.list(hql, null);
	}

	@Override
	public Pager<T> findAlias(String hql, Object[] args,
			Map<String, Object> alias) {
		// 1 设置排序
		hql = setSort(hql);

		String countHql = getCountByHql(hql, true);

		Query countQuery = this.getSession().createQuery(countHql);

		Query query = this.getSession().createQuery(hql);

		// 设置别名参数
		setAliasParameter(countQuery, alias);
		setAliasParameter(query, alias);

		// 设置普通参数
		setParameter(countQuery, args);
		setParameter(query, args);

		// 创建分页
		Pager<T> pager = new Pager<T>();
		// 设置分页
		setPager(query, pager);

		// 创建数据集
		List<T> datas = query.list();
		// 设置数据集
		pager.setDatas(datas);

		// 获取单一结果集
		long total = (Long) countQuery.uniqueResult();

		pager.setTotal(total);

		return pager;
	}

	@Override
	public Pager<T> findAlias(String hql, Map<String, Object> alias) {
		return this.findAlias(hql, null, alias);
	}

	@Override
	public Pager<T> find(String hql, Object[] args) {
		return this.findAlias(hql, args, null);
	}

	@Override
	public Pager<T> find(String hql, Object arg) {
		return this.find(hql, new Object[] { arg });
	}

	@Override
	public Pager<T> find(String hql) {
		return this.find(hql, null);
	}

	@Override
	public Object queryAliasObject(String hql, Object[] args,
			Map<String, Object> alias) {
		Query query = this.getSession().createQuery(hql);

		// 设置别名参数
		setAliasParameter(query, alias);
		// 设置普通参数
		setParameter(query, args);

		return query.uniqueResult();
	}

	@Override
	public Object queryAliasObject(String hql, Map<String, Object> alias) {
		return this.queryAliasObject(hql, null, alias);
	}

	@Override
	public Object queryObject(String hql, Object[] args) {
		return this.queryAliasObject(hql, args, null);
	}

	@Override
	public Object queryObject(String hql, Object arg) {
		return this.queryObject(hql, new Object[] { arg });
	}

	@Override
	public Object queryObject(String hql) {
		return this.queryObject(hql, null);
	}

	@Override
	public void updateByHql(String hql, Object[] args) {
		// 创建条件查询
		Query query = this.getSession().createQuery(hql);

		// 设置普通参数
		setParameter(query, args);

		// 执行更新
		query.executeUpdate();
	}

	@Override
	public void updateByHql(String hql, Object arg) {
		this.updateByHql(hql, new Object[] { arg });
	}

	@Override
	public void updateByHql(String hql) {
		this.updateByHql(hql, null);
	}

	@Override
	public <N extends Object> List<N> listAliasBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		// 设置排序
		sql = setSort(sql);
		// 创建查询
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		// 设置别名参数
		setAliasParameter(sqlQuery, alias);
		// 设置普通参数
		setParameter(sqlQuery, args);

		if (hasEntity) {
			sqlQuery.addEntity(clz);
		} else {
			sqlQuery.setResultTransformer(Transformers.aliasToBean(clz));
		}

		return sqlQuery.list();
	}

	@Override
	public <N extends Object> List<N> listAliasBySql(String sql,
			Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return this.listAliasBySql(sql, null, alias, clz, hasEntity);
	}

	@Override
	public <N extends Object> List<N> listBySql(String sql, Object[] args,
			Class<?> clz, boolean hasEntity) {
		return this.listAliasBySql(sql, args, null, clz, hasEntity);
	}

	@Override
	public <N extends Object> List<N> listBySql(String sql, Object arg,
			Class<?> clz, boolean hasEntity) {
		return this.listBySql(sql, new Object[] { arg }, clz, hasEntity);
	}

	@Override
	public <N extends Object> List<N> listBySql(String sql, Class<?> clz,
			boolean hasEntity) {
		return this.listBySql(sql, null, clz, hasEntity);
	}

	@Override
	public Pager<Object> findAliasBySql(String sql, Object[] args,
			Map<String, Object> alias, Class<Object> clz, boolean hasEntity) {

		String countSql = getCountByHql(sql, false);

		countSql = setSort(countSql);

		sql = setSort(sql);

		Query countQuery = this.getSession().createSQLQuery(countSql);

		Query query = this.getSession().createQuery(sql);

		// 设置别名参数
		setAliasParameter(countQuery, alias);

		setAliasParameter(query, alias);
		// 设置普通参数
		setParameter(countQuery, args);

		setParameter(query, args);

		Pager<Object> pager = new Pager<Object>();

		setPagers(query, pager);
		//
		List<Object> datas = query.list();
		// 设置数据集
		pager.setDatas(datas);
		// 设置
		long total = (Long) countQuery.uniqueResult();

		pager.setTotal(total);

		return pager;
	}

	@Override
	public Pager<Object> findAliasBySql(String sql, Map<String, Object> alias,
			Class<Object> clz, boolean hasEntity) {
		return this.findAliasBySql(sql, null, alias, clz, hasEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Object[] args,
			Class<Object> clz, boolean hasEntity) {
		return this.findAliasBySql(sql, null, clz, hasEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Object arg, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql, new Object[] { arg }, clz, hasEntity);
	}

	@Override
	public Pager<Object> findBySql(String sql, Class<Object> clz,
			boolean hasEntity) {
		return this.findBySql(sql, null, clz, hasEntity);
	}

}
