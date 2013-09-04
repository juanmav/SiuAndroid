package com.diphot.siuweb.shared.dtos;

import java.io.Serializable;
import java.util.ArrayList;

public class UserDTO implements Serializable, InterfaceDTO {

	private static final long serialVersionUID = 7669063489261772143L;
	private Long id;
	private String username;
	private String password;
	private String token;
	private ArrayList<RoleDTO> rolesDTO = new ArrayList<RoleDTO>();
	
	
	public UserDTO(){
		
	}
	
	public UserDTO(Long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
	public UserDTO(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	@Override
	public Long getId() {
		return this.id;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<RoleDTO> getRolesDTO() {
		return rolesDTO;
	}

	public void setRoleDTO(ArrayList<RoleDTO> roleDTO) {
		this.rolesDTO = roleDTO;
	}
	
	public void addRoleDTO(RoleDTO roleDTO){
		this.rolesDTO.add(roleDTO);
	}
}
