package com.diphot.siuweb.shared.dtos;

import java.io.Serializable;


public class RoleDTO implements InterfaceDTO, Serializable{

	private static final long serialVersionUID = 6258380099167902318L;
	private Long id;
	private String name;
	
	public RoleDTO(Long id, String name){
		this.setId(id);
		this.setName(name);
	}
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object o){
		RoleDTO other = (RoleDTO)o;
		return this.getName().equalsIgnoreCase(other.getName());
	}
	
	public int hashCode(){
		return this.id.intValue();
	}
}
