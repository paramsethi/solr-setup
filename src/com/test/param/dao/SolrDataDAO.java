package com.test.param.dao;

import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;

/**
 * This class is used to query data from Solr
 * 
 * And writing data into Solr.
 * 
 * @author param
 * 
 */
@SuppressWarnings("deprecation")
public class SolrDataDAO extends SolrBaseDAO {

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
		return getSolrConnection().query(solrParams);
	}

	/**
	 * Add data into Solr
	 * 
	 * @param id
	 * @param name
	 * @throws Exception
	 */
	public void addData(int id, String name) throws Exception {
		// Populate solr document
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", id);
		doc.addField("name", name);
		CommonsHttpSolrServer server = getSolrConnection();
		server.add(doc);
		server.commit();
		// If required from code call solr commit here.
		// Good idea is to batch you commits, otherwise it may slow down query
		// performance while commits are happening
		// I usually do it via Solr Auto Commit parameter in solrconfig.xml,
		// which gives option for Max number of documents or max time lapse.
		// Which ever happens first.
	}
}
