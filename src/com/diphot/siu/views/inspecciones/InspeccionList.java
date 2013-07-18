package com.diphot.siu.views.inspecciones;

import java.util.ArrayList;

import org.restlet.resource.ClientResource;

import com.diphot.siu.R;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siu.services.restlet.InspeccionRestLetInterface;
import com.diphot.siu.services.restlet.TipificacionRestLetInterface;
import com.diphot.siu.views.SiuConstants;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.filters.InspeccionFilterDTO;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class InspeccionList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_list);
		Bundle b = getIntent().getExtras();
		int riesgo = b.getInt(SiuConstants.RIESGO_PROPERTY);
		int estado = b.getInt(SiuConstants.ESTADO_PROPERTY);
		
		//InspeccionDAO idao = new InspeccionDAO(this);
		//ArrayList<InspeccionDTO> dtos = idao.getList();
		
		ArrayList<InspeccionDTO> dtos = getlist(estado, riesgo);
		InspeccionAdapter adapter = new InspeccionAdapter(this, dtos);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspeccion_list, menu);
		return true;
	}
	
	private ArrayList<InspeccionDTO> getlist(int estado, int riesgo){
		ArrayList<InspeccionDTO> result;
		ClientResource cr = new ClientResource(InspeccionRestLetInterface.URL);
		InspeccionRestLetInterface resource = cr.wrap(InspeccionRestLetInterface.class);
		InspeccionFilterDTO filter = new InspeccionFilterDTO();
		filter.riesgo = riesgo;
		filter.estadoID = estado;
		result = resource.getDTOByQuery(filter);
		return result;
	}
}
