package com.eventpool.web.controller;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.core.CoreContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.annotation.PreDestroy;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


@Component
public class SearchServer {

    //private static HttpSolrServer httpSolrServer = null;
    //private static EmbeddedSolrServer embeddedSolrServer = null;
	
	@Value("$EVENT_POOL{solr.server}")
	private String server ;

	@Value("$EVENT_POOL{solr.port}")
	private String port;

	
    public SolrServer solrServer = null;

    private static final Logger logger = LoggerFactory.getLogger(SearchServer.class);

    public SearchServer() throws ParserConfigurationException, SAXException, IOException {
    	// String solrUrl = "http://10.50.27.13:7923/solr";
         //solrServer = new HttpSolrServer(solrUrl);
        this(System.getProperty("useHttpSolrServer","true").equals("true"));
    }

    public SearchServer(boolean useHttpSolrServer) throws ParserConfigurationException, SAXException, IOException {
        logger.info("Using httpSolrServer:" + useHttpSolrServer);
        if(useHttpSolrServer){
            String solrUrl = "http://"+server+":"+port+"/solr";
            solrServer = new HttpSolrServer(solrUrl);
        }else{
            System.setProperty("solr.solr.home", "C:/solr-suggest-4.0-logs/solr");
            CoreContainer coreContainer = new CoreContainer.Initializer().initialize();
            solrServer = new EmbeddedSolrServer(coreContainer, "collection1");
        }
    }

    @PreDestroy
    public static void destroy() throws InterruptedException {
        logger.info("Shutting down SimilarRecordsProcessor");
        //Thread.sleep(5000l);
        //solrServer.shutdown();
    }

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}
