package com.eventpool.common.dto;

import java.util.List;

import com.eventpool.common.type.QuestionType;

public class EventInfoSettings {

	private String name;
	
	private QuestionType type;
	
	private String group;
	
	private List<String> options;
	
	private String value;
	
	private Boolean isValue;
	
	private Boolean isRequired;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public QuestionType getType() {
		return type;
	}

	public void setType(QuestionType type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List getOptions() {
		return options;
	}

	public void setOptions(List options) {
		this.options = options;
	}

	public Boolean getIsValue() {
		return isValue;
	}

	public void setIsValue(Boolean isValue) {
		this.isValue = isValue;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventInfoSettings other = (EventInfoSettings) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}


