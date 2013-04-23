package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((RadioButton) v).getId());
				Bundle b = new Bundle();
				b.putInt("tema", ((RadioButton) v).getId());
				Intent intent = new Intent(TemaSelection.this, Observacion.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		};
		RadioButton radio;
		RadioGroup radioGroup = new RadioGroup(this);
		for (TemaDTO temaDTO : tdao.findbyParentID(Long.valueOf(tipoid))){
			radio = new RadioButton(this);
			radio.setText(temaDTO.getNombre());
			radio.setId(Integer.parseInt(temaDTO.getId().toString()));
			radio.setOnClickListener(o);
			radioGroup.addView(radio);
		}	
		ll.addView(radioGroup);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tema_selection, menu);
		return true;
	}

}
