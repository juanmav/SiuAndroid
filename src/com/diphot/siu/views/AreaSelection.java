package com.diphot.siu.views;


import com.diphot.siu.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siu.util.Util;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class AreaSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_area_selection);
		createAreaCombos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.area_selection, menu);
		return true;
	}

	private void createAreaCombos(){
		TableLayout table = (TableLayout) this.findViewById(R.id.arealinerLayout);	
		AreaDAO adao = new AreaDAO(this);
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((Button) v).getId());
				Intent returnIntent = new Intent();
				returnIntent.putExtra(SiuConstants.AREA_ID_PROPERTY,((Button) v).getId());
				setResult(RESULT_OK,returnIntent);        
				finish();
			}
		};
		Button button;
		TableRow row = (TableRow) LayoutInflater.from(this).inflate(R.layout.custom_row_line, null);
		int count = 1;
		for (AreaDTO a : adao.getList()){
			if (count == 1) {
				button = (Button) row.findViewById(R.id.rowbutton1);
				button.setText(a.getNombre());
				button.setId(Integer.parseInt(a.getId().toString()));
				//Drawable img = this.getResources().getDrawable( R.drawable.btn_blue );
				
				//button.getBackground().setColorFilter(new LightingColorFilter(Util.getColor(a.getId()), Util.getColor(a.getId())));
				button.setBackgroundResource(Util.getColor(a.getId()));
				button.setOnClickListener(o);
			} else if (count == 2){
				button = (Button) row.findViewById(R.id.rowbutton2);
				button.setText(a.getNombre());
				button.setId(Integer.parseInt(a.getId().toString()));
				//button.getBackground().setColorFilter(new LightingColorFilter(Util.getColor(a.getId()), Util.getColor(a.getId())));
				button.setBackgroundResource(Util.getColor(a.getId()));
				button.setOnClickListener(o);
				count = 0;
				table.addView(row);
				row = (TableRow) LayoutInflater.from(this).inflate(R.layout.custom_row_line, null);
			}
			count++;
		}
	}

}
