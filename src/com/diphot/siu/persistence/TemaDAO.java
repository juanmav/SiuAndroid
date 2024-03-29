package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diphot.siuweb.shared.dtos.TemaDTO;

public class TemaDAO implements DAOInterface<TemaDTO> {
	SiuDBHelper dbhelper;

	private Context context;
			
	public TemaDAO(Context context){
		this.context = context;
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}
	
	@Override
	public Long create(TemaDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("INSERT INTO Tema (id,nombre,tiporelevamientoid) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "'," + dto.getTiporelevamientodto().getId() + ")" );
		db.execSQL("INSERT OR REPLACE INTO Tema (id,nombre,tiporelevamientoid) VALUES (" + dto.getId().toString() + ", '" + dto.getNombre() + "'," + dto.getTiporelevamientodto().getId() + ")" );
		db.close();
		return null;
	}
	@Override
	public TemaDTO findbyId(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {id.toString()};
		Cursor c = db.rawQuery("SELECT id, nombre, tiporelevamientoid FROM Tema WHERE id=?",args);
		TemaDTO dto = null;
		if (c.moveToFirst()){
			// TODO meter el TipoRelevamiento
			dto = new TemaDTO();
			dto.setId(c.getLong(0));
			dto.setNombre(c.getString(1));
			dto.setTiporelevamientodto(new TipoRelevamientoDAO(context).findbyId(c.getLong(2)));
		}
		db.close();
		return dto;
	}
	@Override
	public ArrayList<TemaDTO> getList() {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Tema", new String[]{});
		ArrayList<TemaDTO> dtos = new ArrayList<TemaDTO>();
		if (c.moveToFirst()){
			do {
				TemaDTO tema = new TemaDTO();
				tema.setId(c.getLong(0));
				tema.setNombre(c.getString(1));
				dtos.add(tema);
			} while(c.moveToNext());
		}
		db.close();
		return dtos;
	}
	@Override
	public ArrayList<TemaDTO> findbyParentID(Long id) {
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor c = db.rawQuery("SELECT * FROM Tema WHERE tiporelevamientoid = " + id, new String[]{});
		ArrayList<TemaDTO> dtos = new ArrayList<TemaDTO>();
		if (c.moveToFirst()){
			do {
				TemaDTO tema = new TemaDTO();
				tema.setId(c.getLong(0));
				tema.setNombre(c.getString(1));
				dtos.add(tema);
			} while(c.moveToNext());
		}
		db.close();
		return dtos;
	}

	@Override
	public void massiveCreate(ArrayList<TemaDTO> list) {
		for (TemaDTO t : list){
			System.out.println(t.getNombre());
			this.create(t);
		}
	}
}
