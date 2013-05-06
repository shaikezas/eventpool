package com.eventpool.common.module;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
