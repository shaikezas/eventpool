package com.eventpool.common.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eventpool.event.module.EventApi;
import com.google.gson.Gson;

@Component
public class IPLocation {

	private static final Logger logger = LoggerFactory.getLogger(EventApi.class);
	
	@Resource
	EntityUtilities entityUtilities;
	
	public Map getLocation(String ip){
		if(ip==null) return null;
		URL url;
		try {
			String apiUrl = "http://api.ipinfodb.com/v3/ip-city/?key=ba5e0346a7c965f827474544deece85ef8e6a697667f64a4bb5e8de0665a23ca&ip="+ip+"&timezone=false&format=json";
			url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setInstanceFollowRedirects(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Content-Type", "text/plain");
			connection.setRequestProperty("charset", "utf-8");
			connection.setConnectTimeout(60*1000);
			int responseCode = connection.getResponseCode();
			InputStream inputStream = connection.getInputStream();
			String json = getStringFromInputStream(inputStream);
			Gson gson = new Gson();
			return gson.fromJson(json, Map.class);
		} catch (MalformedURLException e) {
			logger.info("Catalogue Delete error ",e);
		} catch (IOException e) {
			logger.info("Catalogue Delete error ",e);
		}
		return null;
	}
	
	public Integer getCountryId(String ip){
		Map locationMap = getLocation(ip);
		if(locationMap!=null){
			String countryName = (String)locationMap.get("countryName");
			if(countryName==null) return null;
			Integer countryId = entityUtilities.getCountryNameMap().get(countryName.toLowerCase());
			return countryId;
		}
		return null;
	}
	
	public Map getCountryNameIdMap(String ip){
		Map locationMap = getLocation(ip);
		Map countryNameIdMap = new HashMap();
		if(locationMap!=null){
			String countryName = (String)locationMap.get("countryName");
			countryNameIdMap.put("countryName", countryName);
			if(countryName==null) return null;
			countryNameIdMap.put("countryId",entityUtilities.getCountryNameMap().get(countryName.toLowerCase()));
			return countryNameIdMap;
		}
		return null;
	}
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
 
}
