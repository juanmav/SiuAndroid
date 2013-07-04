package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siu.util.Util;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


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
		RelativeLayout screen = (RelativeLayout)this.findViewById(R.id.tipoScreen);
		screen.setBackgroundResource(Util.getColor(areaid));
		TipoRelevamientoDAO tdao = new TipoRelevamientoDAO(this);
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((Button) v).getId());
				Intent returnIntent = new Intent();
				returnIntent.putExtra(SiuConstants.TIPO_ID_PROPERTY,((Button) v).getId());
				setResult(RESULT_OK,returnIntent);        
				finish();
			}
		};
		Button button;
		for (TipoRelevamientoDTO tipoDTO : tdao.findbyParentID(Long.valueOf(areaid))){
			button = new Button(this);
			button.setText(tipoDTO.getNombre());
			button.setId(Integer.parseInt(tipoDTO.getId().toString()));
			button.setBackgroundResource(Util.getColor(tipoDTO.getId()));
			button.setOnClickListener(o);
			ll.addView(button);
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tipo_selection, menu);
		return true;
	}

}
