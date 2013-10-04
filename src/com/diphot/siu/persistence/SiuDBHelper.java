package com.diphot.siu.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SiuDBHelper extends SQLiteOpenHelper{

	public SiuDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	String sqlAreaCreate = "CREATE TABLE Area (id INTEGER PRIMARY KEY, nombre TEXT)";
	String sqlTipoRelevamientoCreate = "CREATE TABLE TipoRelevamiento (id INTEGER PRIMARY KEY, nombre TEXT, areaid INTEGER)";
	String sqlTemaCreate = "CREATE TABLE Tema (id INTEGER PRIMARY KEY, nombre TEXT, tiporelevamientoid INTEGER)";
	String sqlInspeccionCreate = "CREATE TABLE Inspeccion (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
															"temaid INTEGER, " +
															"calle TEXT, " +
															"altura INTEGER, " +
															"latitude REAL, " +
															"longitude REAL, " +
															"fecha TEXT, " +
															"observacion TEXT," +
															"img1 TEXT, " +
															"img2 TEXT, " +
															"img3 TEXT, " +
															"riesgo INTEGER, " +
															"enviado INTEGER," +
															"uuid TEXT," +
															"localidadid INTEGER)";
	String sqlLocalidadCreate ="CREATE TABLE Localidad (id INTEGER PRIMARY KEY, nombre TEXT)";
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlAreaCreate);
		db.execSQL(sqlTipoRelevamientoCreate);
		db.execSQL(sqlTemaCreate);
		db.execSQL(sqlInspeccionCreate);
		db.execSQL(sqlLocalidadCreate);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}
}
