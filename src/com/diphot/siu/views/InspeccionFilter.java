package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.views.inspecciones.InspeccionList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioGroup;

public class InspeccionFilter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_filter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspeccion_filter, menu);
		return true;
	}

	public void find(View v){
		RadioGroup estadosGroup = (RadioGroup) this.findViewById(R.id.estadoRadioGroup);
		RadioGroup riesgosGroup = (RadioGroup)this.findViewById(R.id.riesgoRadioGroup);

		//RadioButton estado = (RadioButton)this.findViewById(estados.getCheckedRadioButtonId());
		//RadioButton riesgo = (RadioButton)this.findViewById(riesgos.getCheckedRadioButtonId());

		int estado = 0;
		switch (estadosGroup.getCheckedRadioButtonId()) {
		case R.id.obs_rad:
			estado = SiuConstants.OBSERVADO;
			break;
		case R.id.con_rad:
			estado = SiuConstants.CONFIRMADO;
			break;
		case R.id.ejec_rad:
			estado = SiuConstants.EJECUTADO;
			break;
		case R.id.res_rad:
			estado = SiuConstants.RESUELTO;
			break;
		default:
			break;

		}
		
		int riesgo = 0;
		switch (riesgosGroup.getCheckedRadioButtonId()) {
		case R.id.alto_rad:
			riesgo = SiuConstants.ALTO;
			break;
		case R.id.medio_rad:
			riesgo = SiuConstants.MEDIO;
			break;

		case R.id.bajo_rad:
			riesgo = SiuConstants.BAJO;
			break;
		default:
			break;
		}

		if (estado != 0 && riesgo != 0){
			busqueda(estado, riesgo);
		} else {
			// TODO debe seleccionar valores.
		}
	}
	private void busqueda(int estado, int riesgo){
		Intent intent = new Intent(InspeccionFilter.this, InspeccionList.class);
		intent.putExtra(SiuConstants.RIESGO_PROPERTY, riesgo);
		intent.putExtra(SiuConstants.ESTADO_PROPERTY, riesgo);
		startActivity(intent);
	}
}
