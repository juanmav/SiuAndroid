package com.diphot.siu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import android.content.Context;
import com.diphot.siuweb.shared.dtos.UserDTO;

// http://stackoverflow.com/questions/4118751/how-do-i-serialize-an-object-and-save-it-to-a-file-in-android
public class UserContainer {
	private static UserDTO userDTO;
	private static Context context;
	private static Boolean userOnline = false;
	
	public static UserDTO getUserDTO() {
		return userDTO;
	}

	public static void setUserDTO(UserDTO userDTO, Context con) {
		UserContainer.userDTO = userDTO;
		userOnline = true;
		// Guardo en un archivo
		try {
			FileOutputStream fos;
			context = con;
			fos = context.openFileOutput("usuario", Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(userDTO);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * Este metodo se llama cuando no se puede acceder al servidor.
	 * Verifica si los datos ingresados coinciden con el ultimo login
	 * de ser asi momentaneamente activa ese usuario.
	 * */
	public static Boolean setLastUserDTO(UserDTO failedUserDTO, Context con){
		UserDTO simpleClass = null;
		context = con;
		userOnline = false;
		try {
			FileInputStream fis;
			fis = context.openFileInput("usuario");
			ObjectInputStream is = new ObjectInputStream(fis);
			simpleClass = (UserDTO) is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (failedUserDTO.getPassword().equalsIgnoreCase(simpleClass.getPassword())){
			userDTO = simpleClass;
			return true;
		} else {
			return false;
		}
	}
	
	public static Boolean isUserOnline(){
		return userOnline;
	}
}
