package com.eventpool.common.module;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eventpool.common.dto.UserEventSettingDTO;
import com.eventpool.common.entities.MemberShip;
import com.eventpool.common.repositories.MemberShipRepository;
import com.google.gson.Gson;

@Component
public class CacheUtils {

	private static final Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	
	@Resource
	private MemberShipRepository memberShipRepository;
	
	private HashMap<Integer,UserEventSettingDTO> memberShipMap = new HashMap<Integer, UserEventSettingDTO>();
	
	@PostConstruct
	public void initCache(){
		List<MemberShip> memberShipList = memberShipRepository.findAll();
		if(memberShipList!=null && !memberShipList.isEmpty()){
			for(MemberShip memberShip:memberShipList){
			
				String settings = memberShip.getSettings();
				Gson gson = new Gson();
				UserEventSettingDTO userEventSettingDTO = gson.fromJson(settings, UserEventSettingDTO.class);
				memberShipMap.put(memberShip.getId(), userEventSettingDTO);
			}
		}
	}
}
