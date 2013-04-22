package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.R.layout;
import com.diphot.siu.R.menu;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class TipoSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_selection);
		Bundle b = getIntent().getExtras();
		int areaid = b.getInt("area");
		System.out.println("Selecionaron el area numero " + areaid);
		createTiposCombos(areaid);
		
	}

	private void createTiposCombos(int areaid){
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.tipolinerLayout);	
		TipoRelevamientoDAO tdao = new TipoRelevamientoDAO(this);
		CheckBox cbox;
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((CheckBox) v).getId());
				Bundle b = new Bundle();
				b.putInt("tipo", ((CheckBox) v).getId());
				Intent intent = new Intent(TipoSelection.this, TemaSelection.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		};
		for (TipoRelevamientoDTO tipoDTO : tdao.findbyParentID(Long.valueOf(areaid))){
			cbox = new CheckBox(this);
			cbox.setText(tipoDTO.getNombre());
			cbox.setId(Integer.parseInt(tipoDTO.getId().toString()));
			cbox.setOnClickListener(o);
			ll.addView(cbox);
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_selection, menu);
		return true;
	}

}
