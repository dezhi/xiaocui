package com.xiaocui.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

public class AbstractDbunitTestCase {

	public static IDatabaseConnection dbunitConn;

	private File tempFile;

	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException {
		dbunitConn = new DatabaseConnection(Dbunit.getConnection());
	}

	protected IDataSet createDataSet(String tableName) throws DataSetException {
		InputStream is = AbstractDbunitTestCase.class.getClassLoader()
				.getResourceAsStream(tableName + ".xml");

		Assert.assertNotNull("dbunit获取的数据不存在", is);

		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
	}

	/**
	 * 备份所有表
	 * 
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected void backupAllTable() throws SQLException, IOException,
			DataSetException {
		IDataSet ds = dbunitConn.createDataSet();

		tempFile = File.createTempFile("back", "xml");

		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}

	/**
	 * 备份自选表
	 * 
	 * @param tableName
	 * @throws DataSetException
	 * @throws IOException
	 */
	protected void backupCustomTable(String[] tableName)
			throws DataSetException, IOException {
		QueryDataSet qds = new QueryDataSet(dbunitConn);

		for (String string : tableName) {
			qds.addTable(string);
		}

		tempFile = File.createTempFile("back", "xml");

		FlatXmlDataSet.write(qds, new FileWriter(tempFile));
	}

	/**
	 * 备份一张表
	 * 
	 * @param tableName
	 * @throws DataSetException
	 * @throws IOException
	 */
	protected void backupOneTable(String tableName) throws DataSetException,
			IOException {
		backupCustomTable(new String[] { tableName });
	}

	/**
	 * 还原数据表
	 * 
	 * @throws FileNotFoundException
	 * @throws DatabaseUnitException
	 * @throws SQLException
	 */
	protected void resumeTable() throws FileNotFoundException,
			DatabaseUnitException, SQLException {

		IDataSet ds = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
				new FileInputStream(tempFile))));

		DatabaseOperation.CLEAN_INSERT.execute(dbunitConn, ds);
	}

	@AfterClass
	public static void destory() {
		try {
			if (dbunitConn != null)
				dbunitConn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
