package com.test.param.dao;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.core.CoreContainer;

/**
 * Base class for connecting to Solr
 * 
 * @author param
 * 
 */
@SuppressWarnings("deprecation")
public class SolrBaseDAO {

	private static EmbeddedSolrServer server = null;

	/**
	 * 
	 * Creates SOLR connection from Web deployed Solr using HttpClient
	 * internally
	 * 
	 * @return will return the SolrServer connection instance.
	 */
	private static CommonsHttpSolrServer commonServer = null;

	public CommonsHttpSolrServer getSolrConnection() throws Exception {
		try {
			// configure a server object with actual solr values.
			if (commonServer == null) {

				commonServer = new CommonsHttpSolrServer(
						"http://localhost:8080/solr");
				commonServer.setParser(new XMLResponseParser());
				commonServer.setSoTimeout(5000);
				commonServer.setConnectionTimeout(5000);
			}

		} catch (MalformedURLException exc) {
			System.out
					.println(" MalformedURLException in getting Solr Connection: "
							+ exc.getMessage());
		} catch (Exception exc) {
			System.out.println(" Exception in getting Solr Connection: "
					+ exc.getMessage());
		}
		return commonServer;
	}

	/**
	 * Creates Solr connection without requiring an HTTP connection
	 * 
	 * @return EmbeddedSolrServer
	 */
	public EmbeddedSolrServer getEmbeddedSolrConnection() {

		try {

			if (server == null) {
				// If not already set in Tomcat Catalina.sh or eclipse JVM arguments
				// explicilty set this property
				System.setProperty("solr.solr.home",
						"/Users/param/apache-solr/example/solr");
				CoreContainer.Initializer initializer = new CoreContainer.Initializer();
				CoreContainer coreContainer = initializer.initialize();
				server = new EmbeddedSolrServer(
						coreContainer, "");
			}

		} catch (Exception ex) {
			System.out.println(" Exception in getting Solr Connection: "
					+ ex.getMessage());
		}

		return server;
	}
}
