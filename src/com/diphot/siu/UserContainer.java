package com.diphot.siu;

import com.diphot.siuweb.shared.dtos.UserDTO;

public class UserContainer {

	private static UserDTO userDTO;

	public static UserDTO getUserDTO() {
		return userDTO;
	}

	public static void setUserDTO(UserDTO userDTO) {
		UserContainer.userDTO = userDTO;
	}
}
