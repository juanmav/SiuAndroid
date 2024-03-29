package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class ObservacionSelect extends Activity {

	private EditText edittext1;
	private Button alto;
	private Button medio;
	private Button bajo;
	private Boolean riesgoSelected = false;

	private int riesgo = SiuConstants.ALTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_observacion);
		this.edittext1 = (EditText) this.findViewById(R.id.editText1);

		this.alto = (Button) this.findViewById(R.id.alto_btn);
		this.medio = (Button) this.findViewById(R.id.medio_btn);
		this.bajo = (Button) this.findViewById(R.id.bajo_btn);
		
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.observacion, menu);
		return true;
	}*/

	public void next(View v) {
		if (validateForm()){
			Intent returnIntent = new Intent();
			returnIntent.putExtra(SiuConstants.OBSERVACION_PROPERTY,this.edittext1.getText().toString());
			returnIntent.putExtra(SiuConstants.RIESGO_PROPERTY, this.riesgo);
			setResult(RESULT_OK,returnIntent);        
			finish();
		}
	}

	public void onClick(View view){
		this.riesgoSelected = true;
		this.alto.setText("Alto");
		this.medio.setText("Medio");
		this.bajo.setText("Bajo");
		Button btn = (Button) view;
		btn.setText("->" + btn.getText() + "<-");

		switch (btn.getId()) {
		case R.id.alto_btn:
			this.riesgo = SiuConstants.ALTO;
			break;
		case R.id.medio_btn:
			this.riesgo = SiuConstants.MEDIO;
			break;

		case R.id.bajo_btn:
			this.riesgo = SiuConstants.BAJO;
			break;

		default:
			this.riesgo = SiuConstants.ALTO;
			break;
		}

	}
	
	private Boolean validateForm(){
		Boolean result = true;
		if (!riesgoSelected){
			new AlertDialog.Builder(ObservacionSelect.this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("ERROR")
			.setMessage("Debe Seleccionar un grado de Riesgo")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
			result = false;
		}
		return result;
	}
	
}
