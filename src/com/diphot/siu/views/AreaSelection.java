package com.diphot.siu.views;


import com.diphot.siu.R;
import android.view.View;
import android.view.View.OnClickListener;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.LinearLayout;

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
		LinearLayout ll = (LinearLayout) this.findViewById(R.id.arealinerLayout);	
		AreaDAO adao = new AreaDAO(this);
		CheckBox cbox;
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View v) {
				System.out.println(((CheckBox) v).getId());
				Bundle b = new Bundle();
				b.putInt("area", ((CheckBox) v).getId());
				Intent intent = new Intent(AreaSelection.this, TipoSelection.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		};
		for (AreaDTO a : adao.getList()){
			cbox = new CheckBox(this);
			cbox.setText(a.getNombre());
			cbox.setId(Integer.parseInt(a.getId().toString()));
			cbox.setOnClickListener(o);
			ll.addView(cbox);
		}	
	}
}
