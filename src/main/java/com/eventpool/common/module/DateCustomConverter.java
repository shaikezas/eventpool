package com.eventpool.common.module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
		if (source != null && !source.isEmpty()) {
			String pattern = "dd-MMM-yyyy HH:mm";
			try {
				destination = getDate(source,pattern);
			} catch (Exception e) {
				logger.error("date parse exception",e);
			}
		}
		return destination;
	}
	
	public Date convertTo(String source) {
		Date destination = null;
		if (source != null && !source.isEmpty()) {
			String pattern = "dd-MMM-yyyy HH:mm";
			try {
				destination = getDate(source,pattern);
			} catch (Exception e) {
				logger.error("date parse exception",e);
			}
		}
		return destination;
	}


	private Date getDate(String source,  String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getDefault());
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getDefault());
			cal.setTime(sdf.parse(source));
			System.out.println("date:"+cal.getTime());
			return cal.getTime();
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
		if(source==null){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		destination = sdf.format(source);
		return destination;
	}
	
	public  Date getTimeZoneDate(String timeZone) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		String pattern = "dd-MMM-yyyy HH:mm";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		String format = df.format(calendar.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(format);
	}

	public  Date getTimeZoneDate(String timeZone,Date date) throws ParseException{
		String pattern = "dd-MMM-yyyy HH:mm";
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		df.setTimeZone(TimeZone.getTimeZone(timeZone));
		String format = df.format(date.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.parse(format);
	}
}
