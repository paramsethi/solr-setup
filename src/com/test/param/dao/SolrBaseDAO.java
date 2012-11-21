package com.test.param.dao;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.core.CoreContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for connecting to Solr
 * 
 * @author param
 * 
 */
public class SolrBaseDAO {
	private static Logger logger = LoggerFactory.getLogger(SolrBaseDAO.class);

	/**
	 * 
	 * Creates SOLR connection from Web deployed Solr using HttpClient
	 * internally
	 * 
	 * @return will return the SolrServer connection instance.
	 */
	public HttpSolrServer getSolrConnection() throws Exception {
		HttpSolrServer solrServer = null;
		try {
			// configure a server object with actual solr values.
			if (solrServer == null) {

				solrServer = new HttpSolrServer("http://localhost:8080/solr");
				solrServer.setParser(new XMLResponseParser());
				solrServer.setSoTimeout(5000);
				solrServer.setConnectionTimeout(5000);

				// Other commonly used properties
				// solrServer.setDefaultMaxConnectionsPerHost(100);
				// solrServer.setMaxTotalConnections(100);
				// solrServer.setFollowRedirects(false); // defaults to false
				// // allowCompression defaults to false.
				// // Server side must support gzip or deflate for this to have
				// any effect.
				// solrServer.setAllowCompression(true);
				// solrServer.setMaxRetries(1); // defaults to 0. > 1 not
				// recommended.
			}

		} catch (Exception exc) {
			logger.error(" Exception in getting Solr Connection: "
					+ exc.getMessage());
			exc.printStackTrace();
		}
		return solrServer;
	}

	/**
	 * Creates Solr connection without requiring an HTTP connection
	 * 
	 * @return EmbeddedSolrServer
	 */
	public EmbeddedSolrServer getEmbeddedSolrConnection() {
		EmbeddedSolrServer server = null;
		try {

			if (server == null) {
				// If not already set in Tomcat Catalina.sh or eclipse JVM
				// arguments
				// explicilty set this property
				System.setProperty("solr.solr.home",
						"/Users/param/apache-solr/example/solr");
				CoreContainer.Initializer initializer = new CoreContainer.Initializer();
				CoreContainer coreContainer = initializer.initialize();
				server = new EmbeddedSolrServer(coreContainer, "");
			}

		} catch (Exception ex) {
			logger.error(" Exception in getting Solr Connection: "
					+ ex.getMessage());
			ex.printStackTrace();
		}

		return server;
	}
}
