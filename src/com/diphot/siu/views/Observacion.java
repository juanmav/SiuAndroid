package com.diphot.siu.views;

import com.diphot.siu.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Observacion extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_observacion);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.observacion, menu);
		return true;
	}
	
	public void next(View v) {
		Intent intent = new Intent(Observacion.this, FotoSelection.class);
		Bundle b = getIntent().getExtras();
		intent.putExtras(b);
		System.out.println("Tema id seleccionado al final" + b.getInt("tema"));
		EditText e = (EditText)this.findViewById(R.id.editText1);
		b.putString("observacion", e.getText().toString());
		startActivity(intent);
	}

}
