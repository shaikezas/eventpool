package com.eventpool.common.module;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtils.class);
	public Date getGMTDate(Date date){
		if(date ==null) throw new IllegalArgumentException("input date can't be null");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	    String format = sdf.format(date);
	    Date gmtDate = null;
		try {
			SimpleDateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			gmtDate = gmtFormat.parse(format);
		} catch (ParseException e) {
			logger.error("parse exception of getting gmtdate",e);
		}
		return gmtDate;
	}
	
	public Date getTimeZoneDate(Date date,String timeZone){
		if(date ==null) throw new IllegalArgumentException("input date can't be null");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
	    String format = sdf.format(date);
	    Date localDate = null;
		try {
			SimpleDateFormat gmtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			localDate = gmtFormat.parse(format);
		} catch (ParseException e) {
			logger.error("parse exception of getting gmtdate",e);
		}
		return localDate;
	}
}
