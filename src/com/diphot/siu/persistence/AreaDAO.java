package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.diphot.siuweb.shared.dtos.AreaDTO;

public class AreaDAO implements DAOInterface<AreaDTO>{

	SiuDBHelper dbhelper; 

	public AreaDAO(Context context){
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}

	@Override
	public void create(AreaDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("INSERT INTO area (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
		db.execSQL("INSERT OR REPLACE INTO area (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
		db.close();
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
		db.close();
		return dto;
	}

	@Override
	public ArrayList<AreaDTO> getList() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM area", new String[]{});
		ArrayList<AreaDTO> dtos = new ArrayList<AreaDTO>();
		if (c.moveToFirst()){
			do {
				dtos.add(new AreaDTO(c.getLong(0),c.getString(1)));
			} while(c.moveToNext());
		}
		db.close();
		return dtos;
	}

	@Override
	public ArrayList<AreaDTO> findbyParentID(Long id) {
		// No tiene Padres
		return null;
	}

	@Override
	public void massiveCreate(ArrayList<AreaDTO> list) {
		for (AreaDTO a : list) {
			System.out.println(a.getNombre());
			this.create(a);
		}
		
	}
}
