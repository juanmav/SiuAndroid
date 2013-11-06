package com.diphot.siu.views;

import java.util.ArrayList;
import java.util.Calendar;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.UserContainer;
import com.diphot.siu.persistence.LocalidadDAO;
import com.diphot.siu.views.adapters.LocalidadAdapter;
import com.diphot.siu.views.inspecciones.InspeccionList;
import com.diphot.siuweb.shared.dtos.LocalidadDTO;
import com.diphot.siuweb.shared.dtos.RoleDTO;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class InspeccionFilter extends Activity {

	private Spinner localidades;
	private LocalidadAdapter ladapter;
	private RadioGroup estadosGroup;
	private RadioGroup riesgosGroup;
	private DatePicker desde;
	private DatePicker hasta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inspeccion_filter);
		//Restriccion por roles.
		restricRole();

		// Cargo lista de localidades.
		localidades = (Spinner) findViewById(R.id.localidades);
		ArrayList<LocalidadDTO> localidadesDTO = new ArrayList<LocalidadDTO>();
		localidadesDTO.add(new LocalidadDTO(-1L, "Cualquiera"));
		localidadesDTO.addAll(new LocalidadDAO(this).getList());
		ladapter = new LocalidadAdapter(this, localidadesDTO);
		localidades.setAdapter(ladapter);
		estadosGroup = (RadioGroup) this.findViewById(R.id.estadoRadioGroup);
		riesgosGroup = (RadioGroup)this.findViewById(R.id.riesgoRadioGroup);

		this.desde = (DatePicker) this.findViewById(R.id.desde); 
		this.hasta = (DatePicker) this.findViewById(R.id.hasta);


	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.inspeccion_filter, menu);
		return true;
	}*/

	public void find(View v){


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

		int riesgo = -1;
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
		case R.id.todos_rad:
			riesgo = SiuConstants.TODOS;
		default:
			break;
		}

		// Validacion y llamada a busqueda.
		if (estado != 0 && riesgo != -1){
			busqueda(estado, riesgo);
		} else {
			new AlertDialog.Builder(InspeccionFilter.this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("Validación")
			.setMessage("Porfavor seleccione un estado de inspeccion y un grado de riesgo para la busqueda.")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
		}
	}

	private void restricRole(){
		RoleDTO role = UserContainer.getUserDTO().getRolesDTO().get(0);
		if (role.equals(new RoleDTO(SiuConstants.ROLES.ADMIN))){
			// Dejo todos los botones.
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.INSPECTOR))) {
			// Dejo todos los botones.
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.SUPERVISOR))) {
			// Dejo todos los botones.
		} else if (role.equals(new RoleDTO(SiuConstants.ROLES.SECRETARIA))) {
			// Oculto el boton de Oculto el RadioButton de Observado.
			RadioButton obs = (RadioButton) findViewById(R.id.obs_rad);
			obs.setVisibility(View.INVISIBLE);
		}
	}

	private void busqueda(int estado, int riesgo){
		Intent intent = new Intent(InspeccionFilter.this, InspeccionList.class);
		intent.putExtra(SiuConstants.RIESGO_PROPERTY, riesgo);
		intent.putExtra(SiuConstants.ESTADO_PROPERTY, estado);
		intent.putExtra(SiuConstants.LOCALIDAD_PROPERTY, this.ladapter.getItem(localidades.getSelectedItemPosition()).getId());
		intent.putExtra(SiuConstants.FECHA_DESDE, desde.getDayOfMonth() + "/" +   (desde.getMonth() + 1) + "/" + desde.getYear());
		intent.putExtra(SiuConstants.FECHA_HASTA, hasta.getDayOfMonth() + "/" +   (hasta.getMonth() + 1) + "/" + hasta.getYear());
		startActivity(intent);
	}
}
