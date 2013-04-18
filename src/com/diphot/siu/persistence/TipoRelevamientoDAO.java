package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

public class TipoRelevamientoDAO implements DAOInterface<TipoRelevamientoDTO>{
	SiuDBHelper dbhelper;
	
	public TipoRelevamientoDAO(Context context){
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}
	
	@Override
	public void create(TipoRelevamientoDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("INSERT INTO TipoRelevamiento (id,nombre,areaid) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "'," + dto.getAreadto().getId() + ")" );
		db.execSQL("INSERT OR REPLACE INTO TipoRelevamiento (id,nombre,areaid) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "'," + dto.getAreadto().getId() + ")" );
	}
	@Override
	public TipoRelevamientoDTO findbyId(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {id.toString()};
		Cursor c = db.rawQuery("SELECT id, nombre FROM TipoRelevamiento WHERE id=?",args);
		TipoRelevamientoDTO dto = null;
		if (c.moveToFirst()){
			// TODO meter el Tema
			dto = new TipoRelevamientoDTO();
			dto.setId(c.getLong(0));
			dto.setNombre(c.getString(1));
		}
		return dto;
	}
	@Override
	public ArrayList<TipoRelevamientoDTO> getList() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM tiporelevamiento", new String[]{});
		ArrayList<TipoRelevamientoDTO> dtos = new ArrayList<TipoRelevamientoDTO>();
		if (c.moveToFirst()){
			do {
				TipoRelevamientoDTO tipo = new TipoRelevamientoDTO();
				tipo.setId(c.getLong(0));
				tipo.setNombre(c.getString(1));
				dtos.add(tipo);
			} while(c.moveToNext());
		}
		return dtos;
	}
	@Override
	public ArrayList<TipoRelevamientoDTO> findbyParentID(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM tiporelevamiento WHERE areaid = " + id, new String[]{});
		ArrayList<TipoRelevamientoDTO> dtos = new ArrayList<TipoRelevamientoDTO>();
		if (c.moveToFirst()){
			do {
				TipoRelevamientoDTO tipo = new TipoRelevamientoDTO();
				tipo.setId(c.getLong(0));
				tipo.setNombre(c.getString(1));
				dtos.add(tipo);
			} while(c.moveToNext());
		}
		return dtos;
	}
}
