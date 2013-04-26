package com.diphot.siu.persistence;

import java.util.ArrayList;
import java.util.Date;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;

public class InspeccionDAO implements DAOInterface<InspeccionDTO>{
	SiuDBHelper dbhelper; 
	public InspeccionDAO(Context context){
		this.dbhelper = new SiuDBHelper(context, "siudb", null, 1);
	}
	@Override
	public void create(InspeccionDTO dto) {
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		String sqlString = "INSERT INTO Inspeccion (temaid, calle, altura, latitude, longitude, fecha, img1, img2, img3, enviado) " +
				"VALUES ("+ dto.getTema().getId().toString() + ",'" +
				dto.getCalle() + "'," +
				dto.getAltura() + "," +
				dto.getLatitude() + "," +
				dto.getLongitude() + ",'" +
				dto.getFecha().toString() + "','" +
				dto.getImg1() + "','" +
				dto.getImg2() + "','" +
				dto.getImg3()	+ "'," +
				"0" +
				")";
		System.out.println(sqlString); 
		db.execSQL(sqlString);
		db.close();
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

	public InspeccionDTO getNotSended(){
		InspeccionDTO idto = null;
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String[] args = new String[] {};
		Cursor c = db.rawQuery("SELECT * FROM Inspeccion WHERE enviado=0",args);
		
		if (c.moveToFirst()){
			idto = new InspeccionDTO();
			idto.setId(c.getLong(0));
			TemaDTO temaDTO = new TemaDTO();
			temaDTO.setId(c.getLong(1));
			idto.setTema(temaDTO);
			idto.setCalle(c.getString(2));
			idto.setAltura(c.getInt(3));
			idto.setLatitude(c.getDouble(4));
			idto.setLongitude(c.getDouble(5));
			// TODO crear la fecha.
			idto.setFecha(new Date());
			idto.setImg1(c.getString(7));
			idto.setImg2(c.getString(8));
			idto.setImg3(c.getString(9));
		}
		db.close();
		return idto;
	}

	public void updateToSended(Long id){
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		System.out.println("UPDATE Inspeccion SET enviado = 1 where id=" +id);
		db.execSQL("UPDATE Inspeccion SET enviado = 1 where id=" +id);
		db.close();
	}
	@Override
	public void massiveCreate(ArrayList<InspeccionDTO> list) {
		// TODO Auto-generated method stub
		
	}
}
