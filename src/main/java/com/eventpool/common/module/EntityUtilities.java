package com.eventpool.common.module;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.eventpool.common.entities.City;
import com.eventpool.common.entities.Country;
import com.eventpool.common.entities.State;

@Component
public class EntityUtilities {

	@Resource
	private RepositoryPool repositoryPool;
	
	private Map<Integer,String> cityMap = new HashMap<Integer,String>(39000);

	private Map<Integer,String> stateMap =new HashMap<Integer,String>();

	private Map<Integer,String> countryMap = new HashMap<Integer,String>();

	private boolean initAllMaps=false;
	
	@PostConstruct
	public void initMaps(){
		if(initAllMaps){
			List<City> allCities = repositoryPool.getCityRepository().findAll();
			if(allCities!=null && allCities.size()>0){
				for(City city:allCities){
					cityMap.put(city.getId(), city.getName());
				}
			}else{
				cityMap.clear();
			}
			List<State> allStates = repositoryPool.getStateRepository().findAll();
			if(allStates!=null && allStates.size()>0){
				for(State state:allStates){
					stateMap.put(state.getId(), state.getName());
				}
			}else{
				stateMap.clear();
			}
			List<Country> allCountries = repositoryPool.getCountryRepository().findAll();
			if(allCountries!=null && allCountries.size()>0){
				for(Country country:allCountries){
					countryMap.put(country.getId(), country.getName());
				}
			}else{
				countryMap.clear();
			}

		}
	}
	

	public Map<Integer, String> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<Integer, String> cityMap) {
		this.cityMap = cityMap;
	}

	public Map<Integer, String> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<Integer, String> stateMap) {
		this.stateMap = stateMap;
	}

	public Map<Integer, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<Integer, String> countryMap) {
		this.countryMap = countryMap;
	}

	public RepositoryPool getRepositoryPool() {
		return repositoryPool;
	}

	public void setRepositoryPool(RepositoryPool repositoryPool) {
		this.repositoryPool = repositoryPool;
	}
	
}
