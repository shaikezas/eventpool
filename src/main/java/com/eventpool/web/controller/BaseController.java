package com.eventpool.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import sun.net.util.IPAddressUtil;

public class BaseController {
	
	public String getRemoteIp(HttpServletRequest request) {
		if(request==null) return null;
		String clientIp = request.getRemoteAddr();
		String xForwardedFor = request.getHeader("X-FORWARDED-FOR");
		if (StringUtils.isBlank(xForwardedFor))
			return clientIp;

		String[] ips = xForwardedFor.split(",");
		for (String ip : ips) {
			ip = ip.trim();
			if (isValidIpAddress(ip)) {
				return ip;
			}
		}

		return clientIp;
	}

	public boolean isValidIpAddress(String ip) {
		return (IPAddressUtil.isIPv4LiteralAddress(ip) || IPAddressUtil.isIPv6LiteralAddress(ip));
	}


}
