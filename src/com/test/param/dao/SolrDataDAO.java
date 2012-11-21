package com.test.param.dao;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to query data from Solr
 * 
 * And writing data into Solr.
 * 
 * @author param
 * 
 */
public class SolrDataDAO extends SolrBaseDAO {
	private HttpSolrServer server = null;
	private static Logger logger = LoggerFactory.getLogger(SolrBaseDAO.class);

	public SolrDataDAO() throws Exception {
		server = getSolrConnection();
	}

	/**
	 * Get Data from Solr
	 * 
	 * @param start
	 * @param rows
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public QueryResponse queryData(int start, int rows, String query)
			throws Exception {
		ModifiableSolrParams solrParams = new ModifiableSolrParams();
		solrParams.set("q", query);
		solrParams.set("start", start);
		solrParams.set("rows", rows);
		// solrParams.set("facet", facet); // Facets if required
		return server.query(solrParams);
	}

	/**
	 * Add data into Solr
	 * 
	 * @param id
	 * @param name
	 * @throws Exception
	 */
	public void addData(int id, String name) throws Exception {
		logger.info("Add to solr: ID = " + id + " name = " + name);
		// Populate solr document
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", id);
		doc.addField("name", name);
		server.add(doc);
		server.commit();
		logger.info("Data committed Successfully!");
		// If required from code call solr commit here.
		// Good idea is to batch you commits, otherwise it may slow down query
		// performance while commits are happening
		// I usually do it via Solr Auto Commit parameter in solrconfig.xml,
		// which gives option for Max number of documents or max time lapse.
		// Which ever happens first.
	}

	/**
	 * Delete records from Solr
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteData(int id) throws Exception {
		server.deleteByQuery("id:" + id);
		server.commit();
		logger.info("Data committed Successfully!");
	}
}
