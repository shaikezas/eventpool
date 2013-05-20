package com.eventpool.common.module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DateCustomConverter extends DozerConverter<String, Date> implements
		MapperAware {
	private static final Logger logger = LoggerFactory.getLogger(DateCustomConverter.class);
	
	private Mapper mapper;

	public DateCustomConverter() {
		super(String.class, Date.class);
	}

	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public Date convertTo(String source, Date destination) {
		if (source != null) {
			String pattern = "dd-MMM-yyyy HH:mm";
			destination = getDate(source,pattern);
		}
		return destination;
	}

	private Date getDate(String source,  String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(source);
		} catch (ParseException e) {
			logger.error("date parse exception",e);
		}
		return null;
	}

	public String convertFrom(Date source,String destination) {
		String pattern = "dd-MMM-yyyy HH:mm";
		return getDateString(source, pattern);
	}
	
	public String convertFrom(Date source) {
		return convertFrom(source,"");
	}

	private String getDateString(Date source, String pattern) {
		String destination;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		destination = sdf.format(source);
		return destination;
	}
	


}
