package com.test.runner;

import java.net.ConnectException;
import java.util.Iterator;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrException;

import com.test.param.dao.SolrDataDAO;

/**
 * Test class for Solr
 * 
 * @author param
 * 
 */
public class TestSolrData {

	public static void main(String[] args) {
		try {

			testAddData();

			testQueryData();

		} catch (SolrException solrEx) {
			System.out.println("Exception occured : " + solrEx.getMessage());
			solrEx.printStackTrace();

		} catch (ConnectException cexp) {
			System.out
					.println("Solr Connection Failed (Make sure solr is up and running): "
							+ cexp.getMessage());
			cexp.printStackTrace();
		} catch (Exception ex) {

			System.out.println("Generic Exception : " + ex.getMessage());
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
		System.out.println("Add Test data");
		dataDao.addData(1, "param");
		dataDao.addData(2, "param2");
		System.out.println("Added data successfully!!!");

	}

	/**
	 * Test Querying data
	 * 
	 * @throws Exception
	 */
	public static void testQueryData() throws Exception {
		SolrDataDAO dataDao = new SolrDataDAO();
		QueryResponse resp = dataDao.queryData(0, 10, "*:*");
		SolrDocumentList data = resp.getResults();

		// Iterate through solr response
		for (Iterator<SolrDocument> iterator = data.iterator(); iterator
				.hasNext();) {
			SolrDocument solrDocument = (SolrDocument) iterator.next();
			System.out.println("ID " + solrDocument.getFieldValue("id"));
			System.out.println("NAME " + solrDocument.getFieldValue("name"));
		}
	}
}
