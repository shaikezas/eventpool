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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				destination = sdf.parse(source);
			} catch (ParseException e) {
				logger.error("date parse exception",e);
			}
		}
		return destination;
	}

	public String convertFrom(Date source,String destination) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		destination = sdf.format(source);
		return destination;
	}


}
