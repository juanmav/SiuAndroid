package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.diphot.siuweb.shared.dtos.AuditoriaDTO;

public class AuditoriaDAO implements DAOInterface<AuditoriaDTO>{

	SiuDBHelper dbhelper; 
	
	private Context context;
	
	public AuditoriaDAO(Context context){
		this.context = context;
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}
	
	@Override
	public Long create(AuditoriaDTO dto) {
	
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues nuevoRegistro = new ContentValues();
		nuevoRegistro.put("inspeccionID",dto.getInspeccionID());
		nuevoRegistro.put("img1",dto.getImg1());
		nuevoRegistro.put("img2",dto.getImg2());
		nuevoRegistro.put("img3",dto.getImg3());
		nuevoRegistro.put("resuelto",dto.getResuelto());
		nuevoRegistro.put("observaciones",dto.getObservaciones());
		nuevoRegistro.put("fecha",dto.getFecha());
		nuevoRegistro.put("enviado", "0");
		Long id = db.insert("Auditoria", null, nuevoRegistro);
		db.close();
		return id;
		
	}

	@Override
	public void massiveCreate(ArrayList<AuditoriaDTO> list) {
		// TODO Auto-generated method stub
	}

	@Override
	public AuditoriaDTO findbyId(Long dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AuditoriaDTO> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AuditoriaDTO> findbyParentID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
