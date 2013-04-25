package com.eventpool.common.dto;


public abstract class IdDTO extends AbstractDTO {

	private Long id;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String toString(){
		//has problmes to fetchtype=lazy
		//return ReflectionToStringBuilder.toString(this);
		return this.getClass().getSimpleName() + "id:"+ id;
	}

	@Override
	public int hashCode() {
		if(id == null)
			return super.hashCode();
		return id.intValue();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj instanceof IdDTO){ 
			if(id == null && ((IdDTO)obj).getId() == null)
				return true;
			if(id != null && ((IdDTO)obj).getId() != null && id.compareTo(((IdDTO)obj).getId()) == 0)
				return true;
		}
		return false;
	}

}
