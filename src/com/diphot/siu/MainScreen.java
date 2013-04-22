package com.diphot.siu;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.diphot.siu.Json.JsonService;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class MainScreen extends Activity implements Observer{

	private JsonService<AreaDTO> jsonserviceArea = new JsonService<AreaDTO>(new AreaDTO(), new TypeToken<ArrayList<AreaDTO>>(){}.getType());
	private JsonService<TipoRelevamientoDTO> jsonserviceTipoRelevamiento = new JsonService<TipoRelevamientoDTO>(new TipoRelevamientoDTO(), new TypeToken<ArrayList<TipoRelevamientoDTO>>(){}.getType());
	private JsonService<TemaDTO> jsonserviceTema = new JsonService<TemaDTO>(new TemaDTO(), new TypeToken<ArrayList<TemaDTO>>(){}.getType());
	private JsonService<InspeccionDTO> jsonserviceInspeccion = new JsonService<InspeccionDTO>(new InspeccionDTO(), new TypeToken<ArrayList<InspeccionDTO>>(){}.getType());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
		jsonserviceArea.addObserver(this);
		jsonserviceTipoRelevamiento.addObserver(this);
		jsonserviceTema.addObserver(this);
		jsonserviceInspeccion.addObserver(this);
		this.createAreaCombos();
		
	}

	private void createAreaCombos(){
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.linerLayout);
		
		AreaDAO adao = new AreaDAO(this);
		CheckBox cbox;
		for (AreaDTO a : adao.getList()){
			cbox = new CheckBox(this);
			cbox.setText(a.getNombre());
			cbox.setId(Integer.parseInt(a.getId().toString()));
			ll.addView(cbox);
		}
		
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
		TemaDAO temaDAO = new TemaDAO(this);
		TemaDTO tdto = temaDAO.findbyId(3L);
		InspeccionDAO idao = new InspeccionDAO(this);
		InspeccionDTO idto = new InspeccionDTO(null, "Calle Android", 100, tdto, 22.2, 3.0, new Date());
		idao.create(idto);
	}

	public void enviarIns (View view){
		InspeccionDAO idao = new InspeccionDAO(this);
		InspeccionDTO idto = idao.getNotSended();
		if (idto != null ){
			// TODO hacer el envio aca.
			jsonserviceInspeccion.create(idto);
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable observable, Object data) {
		System.out.println("Me viene la respueta del backend");
		if ( observable == jsonserviceArea){
			ArrayList<AreaDTO> areas = (ArrayList<AreaDTO>)data;
			AreaDAO adao = new AreaDAO(this);
			adao.massiveCreate(areas);
		} else if (observable == jsonserviceTipoRelevamiento){
			ArrayList<TipoRelevamientoDTO> tipos = (ArrayList<TipoRelevamientoDTO>)data;
			TipoRelevamientoDAO tdao = new TipoRelevamientoDAO(this);
			tdao.massiveCreate(tipos);
		} else if (observable == jsonserviceTema){
			ArrayList<TemaDTO> temas = (ArrayList<TemaDTO>)data;
			TemaDAO tdao = new TemaDAO(this);
			tdao.massiveCreate(temas);
		} else if (observable == jsonserviceInspeccion){
			System.out.println("Y aca que hago");
			System.out.println(data);
		}
	}
}
