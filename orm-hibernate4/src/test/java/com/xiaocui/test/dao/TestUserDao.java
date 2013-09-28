package com.xiaocui.test.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.xiaocui.test.AbstractDbunitTestCase;
import com.xiaocui.test.EntitiesHelper;
import com.xiaocui.test.vo.User;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/beans.xml")
//@TestExecutionListeners({ DbUnitTestExecutionListener.class,
//		DependencyInjectionTestExecutionListener.class })
public class TestUserDao extends AbstractDbunitTestCase {
	@Inject
	private SessionFactory sessionFactory;

	@Inject
	private IUserDao userDao;

	@Before
	public void setUp() throws DataSetException, SQLException, IOException {

		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(s));
		this.backupAllTable();
	}

	//@Test
	public void testLoad() throws DatabaseUnitException, SQLException {
		IDataSet ids = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ids);

		User user = userDao.load(1);

		EntitiesHelper.assertUser(user);
	}

	//@Test
	public void testDelete() throws DatabaseUnitException, SQLException {
		IDataSet ids = createDataSet("t_user");
		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ids);

		userDao.delete(5);

		User user = userDao.load(5);

		System.out.println(user.getName());
	}

	@After
	public void tearDown() throws FileNotFoundException, DatabaseUnitException,
			SQLException {

		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager
				.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		this.resumeTable();
	}

}
