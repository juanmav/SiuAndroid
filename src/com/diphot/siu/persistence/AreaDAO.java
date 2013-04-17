package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diphot.siuweb.shared.dtos.AreaDTO;

public class AreaDAO implements DAOInterface<AreaDTO>{
	
	SiuDBHelper dbhelper = new SiuDBHelper(null, "siudb", null, 1);
	
	@Override
	public void create(AreaDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("INSERTE INTO area (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
		db.execSQL("INSERTE INTO area (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
	}
	
	@Override
	public AreaDTO findbyId(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {id.toString()};
		Cursor c = db.rawQuery("SELECT id, nombre FROM area WHERE id=?",args);
		AreaDTO dto = null;
		if (c.moveToFirst()){
			dto = new AreaDTO(c.getLong(0), c.getString(1));
		}
		return dto;
	}
	
	@Override
	public ArrayList<AreaDTO> getList() {
		// TODO Auto-generated method stub
		return null;
	}
}
