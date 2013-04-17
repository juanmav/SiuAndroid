package com.diphot.siu.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SiuDBHelper extends SQLiteOpenHelper{

	public SiuDBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	String sqlAreaCreate = "CREATE TABLE Area (id INTEGER, nombre TEXT)";
	String sqlTipoRelevamientoCreate = "CREATE TABLE TipoRelevamiento (id INTEGER, nombre TEXT, areaid INTEGER)";
	String sqlTemaCreate = "CREATE TABLE Tema (id INTEGER, nombre TEXT, tiporelevamientoid)";
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(sqlAreaCreate);
		db.execSQL(sqlTipoRelevamientoCreate);
		db.execSQL(sqlTemaCreate);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

}
