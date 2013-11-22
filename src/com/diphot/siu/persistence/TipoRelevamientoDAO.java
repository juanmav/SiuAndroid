package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

public class TipoRelevamientoDAO implements DAOInterface<TipoRelevamientoDTO>{
	SiuDBHelper dbhelper;
	private Context context;
	
	
	public TipoRelevamientoDAO(Context context){
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
		this.context = context;
	}
	
	@Override
	public Long create(TipoRelevamientoDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("INSERT INTO TipoRelevamiento (id,nombre,areaid) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "'," + dto.getAreadto().getId() + ")" );
		db.execSQL("INSERT OR REPLACE INTO TipoRelevamiento (id,nombre,areaid) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "'," + dto.getAreadto().getId() + ")" );
		db.close();
		return null;
	}
	@Override
	public TipoRelevamientoDTO findbyId(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {id.toString()};
		Cursor c = db.rawQuery("SELECT id, nombre, areaid FROM TipoRelevamiento WHERE id=?",args);
		TipoRelevamientoDTO dto = null;
		if (c.moveToFirst()){
			// TODO meter el Area
			dto = new TipoRelevamientoDTO();
			dto.setId(c.getLong(0));
			dto.setNombre(c.getString(1));
			dto.setAreadto(new AreaDAO(context).findbyId(Long.valueOf(c.getInt(2))));
		}
		db.close();
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
		db.close();
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
		db.close();
		return dtos;
	}

	@Override
	public void massiveCreate(ArrayList<TipoRelevamientoDTO> list) {
		for (TipoRelevamientoDTO t : list){
			System.out.println(t.getNombre());
			this.create(t);
		}
	}
}
