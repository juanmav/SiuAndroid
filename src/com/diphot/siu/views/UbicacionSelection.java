package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.R.layout;
import com.diphot.siu.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UbicacionSelection extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ubicacion_selection);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ubicacion_selection, menu);
		return true;
	}

}
