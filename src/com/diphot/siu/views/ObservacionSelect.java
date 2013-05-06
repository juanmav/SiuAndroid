package com.diphot.siu.views;

import com.diphot.siu.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ObservacionSelect extends Activity {
	
	private EditText edittext1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_observacion);
		this.edittext1 = (EditText) this.findViewById(R.id.editText1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.observacion, menu);
		return true;
	}

	public void next(View v) {
		// TODO agtregar la observacion al DTO
		Intent returnIntent = new Intent();
		returnIntent.putExtra(SiuConstants.OBSERVACION_PROPERTY,this.edittext1.getText().toString());
		setResult(RESULT_OK,returnIntent);        
		finish();
	}

}
