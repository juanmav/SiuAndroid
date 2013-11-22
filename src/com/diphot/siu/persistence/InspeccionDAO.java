package com.diphot.siu.persistence;

import java.util.ArrayList;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.LocalidadDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;

public class InspeccionDAO implements DAOInterface<InspeccionDTO>{
	SiuDBHelper dbhelper; 
	
	private Context context;
	
	public InspeccionDAO(Context context){
		this.context = context;
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}

	@Override
	public Long create(InspeccionDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		dto.UUID = UUID.randomUUID().toString().replaceAll("-", "");
		ContentValues nuevoRegistro = new ContentValues();
		
		if (dto.getId() != null){
			nuevoRegistro.put("id", dto.getId().toString());
		}
		
		nuevoRegistro.put("temaid", dto.getTema().getId().toString());
		nuevoRegistro.put("calle", dto.getCalle() );
		nuevoRegistro.put("altura", dto.getAltura());
		nuevoRegistro.put("latitude", dto.getLatitude());
		nuevoRegistro.put("longitude", dto.getLongitude());
		nuevoRegistro.put("fecha", dto.getFecha().toString());
		nuevoRegistro.put("img1", dto.getImg1());
		nuevoRegistro.put("img2", dto.getImg2());
		nuevoRegistro.put("img3", dto.getImg3());
		nuevoRegistro.put("observacion", dto.getObservacion());
		nuevoRegistro.put("riesgo", dto.getRiesgo());
		nuevoRegistro.put("enviado", "0");
		nuevoRegistro.put("uuid", dto.UUID);
		nuevoRegistro.put("localidadid", dto.getLocalidad().getId().toString());
		nuevoRegistro.put("calle1", dto.getEntreCalleUno());
		nuevoRegistro.put("calle2", dto.getEntreCalleDos());
		nuevoRegistro.put("auditar", "0");
		nuevoRegistro.put("lastStateIdentifier", dto.getLastStateIdentifier());
		
		
		Long id = db.insert("Inspeccion", null, nuevoRegistro);
		db.close();
		return id;
	}
	
	@Override
	public InspeccionDTO findbyId(Long dto) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<InspeccionDTO> getList() {
		ArrayList<InspeccionDTO> idtos = new ArrayList<InspeccionDTO>();
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {};
		// TODO ajustar query.
		Cursor c = db.rawQuery("SELECT * FROM Inspeccion",args);
		if (c.moveToFirst()) {
			do {
				idtos.add(getSimpleDTO(c));
			} while(c.moveToNext());
		}
		return idtos;
	}

	/* Toma el primer registro del cursor y devuelve el dto
	 * Este metodo no avanza el cursor.
	 * */
	private InspeccionDTO getSimpleDTO(Cursor c){
		InspeccionDTO idto = new InspeccionDTO();
		idto.setId(c.getLong(0));
		
		TemaDTO temaDTO = new TemaDAO(context).findbyId(c.getLong(1));
			
		idto.setTema(temaDTO);
		idto.setCalle(c.getString(2));
		idto.setAltura(c.getInt(3));
		idto.setLatitude(c.getDouble(4));
		idto.setLongitude(c.getDouble(5));
		idto.setFecha(c.getString(6));
		idto.setObservacion(c.getString(7));
		idto.setImg1(c.getString(8));
		idto.setImg2(c.getString(9));
		idto.setImg3(c.getString(10));
		idto.setRiesgo(c.getInt(11));
		idto.UUID = c.getString(13);
		LocalidadDTO localidadDTO = new LocalidadDTO(c.getLong(14), "");
		idto.setLocalidad(localidadDTO);
		idto.setEntreCalleUno(c.getString(15));
		idto.setEntreCalleDos(c.getString(16));
		idto.setLastStateIdentifier(c.getInt(18));
		return idto;
	}

	@Override
	public ArrayList<InspeccionDTO> findbyParentID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public InspeccionDTO getNotSent(){
		InspeccionDTO idto = null;
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {};
		Cursor c = db.rawQuery("SELECT * FROM Inspeccion WHERE enviado=0",args);
		if (c.moveToFirst()){
			idto = getSimpleDTO(c);
		}
		db.close();
		return idto;
	}

	public void removeSent(Long id){
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("UPDATE Inspeccion SET enviado = 1 where id=" +id);
		db.execSQL("UPDATE Inspeccion SET enviado = 1 where id=" +id);
		db.close();
	}
	
	public void removeAudited(long id){
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println( "UPDATE Inspeccion SET auditar = 0 where id=" +id);
		db.execSQL("UPDATE Inspeccion SET auditar = 0 where id=" +id);
		db.close();
	}
	
	@Override
	public void massiveCreate(ArrayList<InspeccionDTO> list) {
		// TODO Auto-generated method stub
	}

	public void createToAudit(InspeccionDTO dto){
		this.create(dto);
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		ContentValues valores = new ContentValues();
		// Para auditar
		valores.put("auditar",1);
		// Para que el Inspeccion Sender no las envie.
		valores.put("enviado", 1);
		db.update("Inspeccion", valores, "id=" + dto.getId().toString(), null);
		db.close();
	}
	
	public ArrayList<InspeccionDTO> getToAudit(){
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {};
		Cursor c = db.rawQuery("SELECT * FROM Inspeccion WHERE auditar=1",args);	
		ArrayList<InspeccionDTO> lista = new ArrayList<InspeccionDTO>();
		while(c.moveToNext()){
			lista.add(getSimpleDTO(c));
		}
		db.close();
		return lista;
	}
	
}
