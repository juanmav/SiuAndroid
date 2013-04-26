package com.diphot.siu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.diphot.siu.Json.JsonCreateService;
import com.diphot.siu.Json.JsonListService;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siu.views.AreaSelection;
import com.diphot.siu.views.SelectionController;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainScreen extends Activity implements Observer{

	private JsonListService<AreaDTO> jsonserviceArea;
	private JsonListService<TipoRelevamientoDTO> jsonserviceTipoRelevamiento;
	private JsonListService<TemaDTO> jsonserviceTema;
	private JsonCreateService<InspeccionDTO> jsonserviceInspeccion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		/*CReacion de los servicios*/
		this.jsonserviceArea = new JsonListService<AreaDTO>(new AreaDTO(), new TypeToken<ArrayList<AreaDTO>>(){}.getType(),this);
		this.jsonserviceTipoRelevamiento = new JsonListService<TipoRelevamientoDTO>(new TipoRelevamientoDTO(), new TypeToken<ArrayList<TipoRelevamientoDTO>>(){}.getType(),this);
		this.jsonserviceTema = new JsonListService<TemaDTO>(new TemaDTO(), new TypeToken<ArrayList<TemaDTO>>(){}.getType(),this);
		this.jsonserviceInspeccion = new JsonCreateService<InspeccionDTO>(new InspeccionDTO(), new TypeToken<ArrayList<InspeccionDTO>>(){}.getType(),this);
		/*Registracion de observables*/
		jsonserviceArea.addObserver(this);
		jsonserviceTipoRelevamiento.addObserver(this);
		jsonserviceTema.addObserver(this);
		jsonserviceInspeccion.addObserver(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	public void addAreas(View view) {
		jsonserviceArea.getList();
	}
	public void addTipoRelevamientos(View view){
		jsonserviceTipoRelevamiento.getList();
	}
	public void addTemas(View view){
		jsonserviceTema.getList();
	}
	public void createIns(View view){
		Intent intent = new Intent(MainScreen.this, SelectionController.class);
		startActivity(intent);      
	}
	public void enviarIns (View view){
		InspeccionDAO idao = new InspeccionDAO(this);
		InspeccionDTO idto = idao.getNotSended();
		if (idto != null ){
			// TODO hacer el envio aca.
			jsonserviceInspeccion.create(idto);
		}
	}

	@Override
	public void update(Observable observable, Object data) {
		System.out.println("Me viene la respueta del backend");
		if ( observable == jsonserviceArea){
			// Sincro OK
		} else if (observable == jsonserviceTipoRelevamiento){
			// Sincro OK
		} else if (observable == jsonserviceTema){
			// Sincro OK
		} else if (observable == jsonserviceInspeccion){
			// Creacion OK
		}
	}
	
	public void actualizaCuenta(){
		
	}
}
