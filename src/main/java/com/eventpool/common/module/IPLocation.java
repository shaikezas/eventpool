package com.eventpool.common.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.sql.rowset.spi.XmlReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import sun.misc.IOUtils;

import com.eventpool.event.module.EventApi;

@Component
public class IPLocation {

	private static final Logger logger = LoggerFactory.getLogger(EventApi.class);
	
	public void getLocation(String ip){
		URL url;
		try {
			String apiUrl = "http://api.ipinfodb.com/v3/ip-city/?key=ba5e0346a7c965f827474544deece85ef8e6a697667f64a4bb5e8de0665a23ca&ip="+ip+"&timezone=false&format=xml";
			url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setRequestProperty("charset", "utf-8");
			connection.setConnectTimeout(60*1000);
			int responseCode = connection.getResponseCode();
			
			try {
				
				XMLInputFactory factory = XMLInputFactory.newInstance();
				XMLStreamReader reader = factory.createXMLStreamReader(connection.getInputStream());
				while(reader.hasNext())
				{
				    if(reader.hasText())
				    {
				        System.out.println(reader.getText());
				    }
				    reader.next();
				}
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (MalformedURLException e) {
			logger.info("Catalogue Delete error ",e);
		} catch (IOException e) {
			logger.info("Catalogue Delete error ",e);
		}
	}
}
