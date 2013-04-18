package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.diphot.siuweb.shared.dtos.InspeccionDTO;

public class InspeccionDAO implements DAOInterface<InspeccionDTO>{

	SiuDBHelper dbhelper; 

	public InspeccionDAO(Context context){
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}
	
	@Override
	public void create(InspeccionDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		//System.out.println("INSERT INTO area (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
		//db.execSQL("INSERT OR REPLACE INTO area (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
	}

	@Override
	public InspeccionDTO findbyId(Long dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<InspeccionDTO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<InspeccionDTO> findbyParentID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
