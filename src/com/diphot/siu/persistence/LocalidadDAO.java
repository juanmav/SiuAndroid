package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diphot.siuweb.shared.dtos.LocalidadDTO;

public class LocalidadDAO  implements DAOInterface<LocalidadDTO>{

	SiuDBHelper dbhelper; 
	public LocalidadDAO(Context context){
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}
	
	@Override
	public Long create(LocalidadDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("INSERT INTO Localidad (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
		db.execSQL("INSERT OR REPLACE INTO Localidad (id,nombre) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "')" );
		db.close();
		return null;
	}

	@Override
	public void massiveCreate(ArrayList<LocalidadDTO> list) {
		for (LocalidadDTO l : list){
			System.out.println("Localidad Creada: " + l.getNombre() );
			this.create(l);
		}
	}

	@Override
	public LocalidadDTO findbyId(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {id.toString()};
		Cursor c = db.rawQuery("SELECT id, nombre FROM Localidad WHERE id=?",args);
		LocalidadDTO dto = null;
		if (c.moveToFirst()){
			dto = new LocalidadDTO(c.getLong(0), c.getString(1));
		}
		db.close();
		return dto;
	}

	@Override
	public ArrayList<LocalidadDTO> getList() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Localidad", new String[]{});
		ArrayList<LocalidadDTO> dtos = new ArrayList<LocalidadDTO>();
		if (c.moveToFirst()){
			do {
				dtos.add(new LocalidadDTO(c.getLong(0),c.getString(1)));
			} while(c.moveToNext());
		}
		db.close();
		return dtos;
	}

	@Override
	public ArrayList<LocalidadDTO> findbyParentID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
