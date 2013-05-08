package com.diphot.siu.views.inspecciones;

import java.util.ArrayList;

import com.diphot.siu.R;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;

import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;

public class InspeccionList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_list);
		InspeccionDAO idao = new InspeccionDAO(this);
		ArrayList<InspeccionDTO> dtos = idao.getList();
		InspeccionAdapter adapter = new InspeccionAdapter(this, dtos);
		setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspeccion_list, menu);
		return true;
	}
}
