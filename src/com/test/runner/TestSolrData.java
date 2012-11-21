package com.test.runner;

import java.net.ConnectException;
import java.util.Iterator;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.param.dao.SolrDataDAO;

/**
 * Test class for Solr
 * 
 * @author param
 * 
 */
public class TestSolrData {

	private static Logger logger = LoggerFactory.getLogger(TestSolrData.class);

	public static void main(String[] args) {
		try {

			testAddData();

			testQueryData();

			testDeleteData();

			testQueryData();

		} catch (SolrException solrEx) {
			logger.error("Exception occured : " + solrEx.getMessage());
			solrEx.printStackTrace();

		} catch (ConnectException cexp) {
			logger.error("Solr Connection Failed (Make sure solr is up and running): "
					+ cexp.getMessage());
			cexp.printStackTrace();
		} catch (Exception ex) {

			logger.error("Generic Exception : " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * Test Add data
	 * 
	 * @throws Exception
	 */
	public static void testAddData() throws Exception {
		SolrDataDAO dataDao = new SolrDataDAO();
		logger.info("Add Test data");
		dataDao.addData(1, "param");
		dataDao.addData(2, "param2");
		logger.info("Added data successfully!!!");

	}

	/**
	 * Test Querying data
	 * 
	 * @throws Exception
	 */
	public static void testQueryData() throws Exception {
		logger.info("Query Test data.");
		SolrDataDAO dataDao = new SolrDataDAO();
		QueryResponse resp = dataDao.queryData(0, 10, "*:*");
		SolrDocumentList data = resp.getResults();

		// Iterate through solr response
		for (Iterator<SolrDocument> iterator = data.iterator(); iterator
				.hasNext();) {
			SolrDocument solrDocument = (SolrDocument) iterator.next();
			logger.info("ID " + solrDocument.getFieldValue("id"));
			logger.info("NAME " + solrDocument.getFieldValue("name"));
		}
		logger.info("Exiting Query Test data.");
	}

	/**
	 * Test Delete data
	 * 
	 * @throws Exception
	 */
	public static void testDeleteData() throws Exception {
		logger.info("Delete records for ID = 1");
		SolrDataDAO dataDao = new SolrDataDAO();
		dataDao.deleteData(1);
		logger.info("Data deleted successfully!!");
	}
}
