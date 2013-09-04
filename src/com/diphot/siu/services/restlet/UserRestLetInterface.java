package com.diphot.siu.services.restlet;

import org.restlet.resource.Get;
import com.diphot.siuweb.shared.dtos.UserDTO;

public interface UserRestLetInterface {
	@Get
	public UserDTO login(UserDTO userDTO);
}
