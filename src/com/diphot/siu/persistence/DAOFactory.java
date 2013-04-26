package com.diphot.siu.persistence;

import java.lang.reflect.InvocationTargetException;
import android.content.Context;
import com.diphot.siuweb.shared.dtos.InterfaceDTO;

public class DAOFactory {
	
	private DAOFactory(){
		
	}
	
	public static DAOInterface<InterfaceDTO> getDAOImpl(Object dto, Context context){
		String clazzName = dto.getClass().getSimpleName();
		System.out.println("El dto que me llega es el siguiente: " + clazzName);
		DAOInterface<InterfaceDTO> dao = null;
		try {
			// TODO mejorar esto tiene que ser un archivo de configurarcion o algo similar.
			clazzName = clazzName.replace("DTO", "DAO");
			System.out.println("Nombre del Dao: ");
			System.out.println("com.diphot.siu.persistence."+clazzName);
			Class<DAOInterface<InterfaceDTO>> c = (Class<DAOInterface<InterfaceDTO>>) Class.forName("com.diphot.siu.persistence."+clazzName);
			dao = (DAOInterface<InterfaceDTO>) c.getConstructor(Context.class).newInstance(context);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dao;
	}
	
}
