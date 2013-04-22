package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.R.layout;
import com.diphot.siu.R.menu;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class TemaSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tema_selection);
		Bundle b = getIntent().getExtras();
		int tipoid = b.getInt("tipo");
		System.out.println("Selecionaron el area numero " + tipoid);
		createTemasCombos(tipoid);
	}

	private void createTemasCombos(int tipoid){
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.temalinerLayout);	
		TemaDAO tdao = new TemaDAO(this);
		CheckBox cbox;
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((CheckBox) v).getId());
				Bundle b = new Bundle();
				b.putInt("tema", ((CheckBox) v).getId());
				//Intent intent = new Intent(TipoSelection.this, TemaSelection.class);
				//intent.putExtras(b);
				//startActivity(intent);
			}
		};
		for (TemaDTO temaDTO : tdao.findbyParentID(Long.valueOf(tipoid))){
			cbox = new CheckBox(this);
			cbox.setText(temaDTO.getNombre());
			cbox.setId(Integer.parseInt(temaDTO.getId().toString()));
			cbox.setOnClickListener(o);
			ll.addView(cbox);
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tema_selection, menu);
		return true;
	}

}
