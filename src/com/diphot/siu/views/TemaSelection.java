package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siu.util.Util;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class TemaSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tema_selection);
		Bundle b = getIntent().getExtras();
		int tipoid = b.getInt(SiuConstants.TIPO_ID_PROPERTY);
		System.out.println("Selecionaron el area numero " + tipoid);
		createTemasCombos(tipoid);
	}

	private void createTemasCombos(int tipoid){
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.temalinerLayout);	
		ScrollView screen = (ScrollView)this.findViewById(R.id.temaScreen);
		screen.setBackgroundResource(Util.getColor(tipoid));
		TemaDAO tdao = new TemaDAO(this);
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((Button) v).getId());
				Intent returnIntent = new Intent();
				returnIntent.putExtra(SiuConstants.TEMA_ID_PROPERTY,((Button) v).getId());
				setResult(RESULT_OK,returnIntent);        
				finish();
			}
		};
		Button button;
		for (TemaDTO temaDTO : tdao.findbyParentID(Long.valueOf(tipoid))){
			button = new Button(this);
			button.setText(temaDTO.getNombre());
			button.setId(Integer.parseInt(temaDTO.getId().toString()));
			button.setBackgroundResource(Util.getColor(temaDTO.getId()));
			button.setTextSize(25);
			button.setHeight(65);
			button.setTypeface(null, Typeface.BOLD);
			button.setOnClickListener(o);
			ll.addView(button);
		}	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tema_selection, menu);
		return true;
	}

}
