package com.diphot.siu.persistence;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
		// Este registro no es del Modelo, es solo de la tablet.
		nuevoRegistro.put("enviado", "0");
		Long id = db.insert("Auditoria", null, nuevoRegistro);
		db.close();
		
		// Marco que la inspeccion ya fue auditada.
		InspeccionDAO idao = new InspeccionDAO(context);
		idao.removeAudited(dto.getInspeccionID());
		
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
	
	public AuditoriaDTO getNotSent(){
		AuditoriaDTO adto = null;
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {};
		Cursor c = db.rawQuery("SELECT * FROM Auditoria WHERE enviado=0",args);
		if (c.moveToFirst()){
			adto = getSimpleDTO(c);
		}
		db.close();
		return adto;
	}
	
	public void removeSent(Long id){
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.execSQL("UPDATE Auditoria SET enviado = 1 where id=" + id );
		db.close();
	}
	
	/* Toma el primer registro del cursor y devuelve el dto
	 * Este metodo no avanza el cursor.
	 * */
	private AuditoriaDTO getSimpleDTO(Cursor c){
		AuditoriaDTO adto = new AuditoriaDTO();
		adto.setId(c.getLong(0));

		adto.setInspeccionID(c.getLong(1));
		adto.setImg1(c.getString(2));
		adto.setImg2(c.getString(3));
		adto.setImg3(c.getString(4));
		adto.setResuelto(c.getInt(5) == 1 ? true: false);
		adto.setObservaciones(c.getString(6));
		adto.setFecha(c.getString(7));
			
		return adto;
	}
	
}
