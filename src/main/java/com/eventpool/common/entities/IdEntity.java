package com.eventpool.common.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdEntity extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
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
		if(obj instanceof IdEntity){ 
			if(id == null && ((IdEntity)obj).getId() == null)
				return true;
			if(id != null && ((IdEntity)obj).getId() != null && id.compareTo(((IdEntity)obj).getId()) == 0)
				return true;
		}
		return false;
	}

}
