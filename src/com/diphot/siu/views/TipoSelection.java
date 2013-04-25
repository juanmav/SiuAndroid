package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class TipoSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tipo_selection);
		Bundle b = getIntent().getExtras();
		int areaid = b.getInt(SiuConstants.AREA_ID_PROPERTY);
		System.out.println("Selecionaron el area numero " + areaid);
		createTiposCombos(areaid);
		
	}

	private void createTiposCombos(int areaid){
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.tipolinerLayout);	
		TipoRelevamientoDAO tdao = new TipoRelevamientoDAO(this);
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((RadioButton) v).getId());
				Intent returnIntent = new Intent();
				returnIntent.putExtra(SiuConstants.TIPO_ID_PROPERTY,((RadioButton) v).getId());
				setResult(RESULT_OK,returnIntent);        
				finish();
			}
		};
		RadioButton radio;
		RadioGroup radioGroup = new RadioGroup(this);
		for (TipoRelevamientoDTO tipoDTO : tdao.findbyParentID(Long.valueOf(areaid))){
			radio = new RadioButton(this);
			radio.setText(tipoDTO.getNombre());
			radio.setId(Integer.parseInt(tipoDTO.getId().toString()));
			radio.setOnClickListener(o);
			radioGroup.addView(radio);
		}	
		ll.addView(radioGroup);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_selection, menu);
		return true;
	}

}
