package com.diphot.siu.views;

import java.util.Date;

import com.diphot.siu.SiuConstants;
import com.diphot.siu.custom.ConsoleOnScreen;
import com.diphot.siu.persistence.InspeccionDAO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;

import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

// Dejamos esta clase suspendida por el momento.
public class SelectionController extends Activity {

	private InspeccionDTO inspeccion = new InspeccionDTO();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent areaIntent = new Intent(SelectionController.this, AreaSelection.class);
		startActivityForResult(areaIntent, SiuConstants.AREA_SELECT);
	}

	private int areaid;
	private int tipoid;
	private int temaid;
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Aca me vuelven los resultados de las distintas activities y voy armando la Inspeccion.
		Bundle b;
		Intent intent;
		
		if (resultCode == RESULT_OK){
			switch (requestCode){
			case SiuConstants.AREA_SELECT:
				areaid = data.getIntExtra(SiuConstants.AREA_ID_PROPERTY,0);
				b = new Bundle();
				b.putInt(SiuConstants.AREA_ID_PROPERTY,areaid );
				intent = new Intent(SelectionController.this, TipoSelection.class);
				intent.putExtras(b);
				startActivityForResult(intent, SiuConstants.TIPO_SELECT);
				break;
			case SiuConstants.TIPO_SELECT:
				tipoid = data.getIntExtra(SiuConstants.TIPO_ID_PROPERTY,0);
				b = new Bundle();
				b.putInt(SiuConstants.TIPO_ID_PROPERTY,tipoid);
				intent = new Intent(SelectionController.this, TemaSelection.class);
				intent.putExtras(b);
				startActivityForResult(intent, SiuConstants.TEMA_SELECT);
				break;
			case SiuConstants.TEMA_SELECT:
				temaid = data.getIntExtra(SiuConstants.TEMA_ID_PROPERTY,0);
				this.inspeccion.setTema(new TemaDTO(temaid));
				intent = new Intent(SelectionController.this, FotoSelection.class);
				startActivityForResult(intent, SiuConstants.FOTO_SELECT);
				break;
			case SiuConstants.FOTO_SELECT:
				String img1 = data.getStringExtra(SiuConstants.IMG1_PROPERTY);
				String img2 = data.getStringExtra(SiuConstants.IMG2_PROPERTY);
				String img3 = data.getStringExtra(SiuConstants.IMG3_PROPERTY);
				this.inspeccion.setImg1(img1);
				this.inspeccion.setImg2(img2);
				this.inspeccion.setImg3(img3);
				intent = new Intent(SelectionController.this, ObservacionSelect.class);
				startActivityForResult(intent, SiuConstants.OBSERVACION_SELECT);
				break;
			case SiuConstants.OBSERVACION_SELECT:
				String observacion = data.getStringExtra(SiuConstants.OBSERVACION_PROPERTY);
				this.inspeccion.setObservacion(observacion);
				int riesgo = data.getIntExtra(SiuConstants.RIESGO_PROPERTY, SiuConstants.ALTO);
				this.inspeccion.setRiesgo(riesgo);
				intent = new Intent(SelectionController.this, UbicacionSelection.class);
				startActivityForResult(intent, SiuConstants.UBICACION_SELECT);
				break;
			case SiuConstants.UBICACION_SELECT:
				this.inspeccion.setCalle(data.getStringExtra(SiuConstants.CALLE_PROPERTY));
				this.inspeccion.setAltura(Integer.parseInt(data.getStringExtra(SiuConstants.ALTURA_PROPERTY)));
				this.inspeccion.setLatitude(Double.parseDouble(data.getStringExtra(SiuConstants.LATITUDE_PROPERTY)));
				this.inspeccion.setLongitude(Double.parseDouble(data.getStringExtra(SiuConstants.LONGITUDE_PROPERTY)));
				creacionTerminada();
				break;
			default:

				break;
			}
		} else {
			switch (requestCode){
			case SiuConstants.AREA_SELECT:
				intent = new Intent(SelectionController.this, MainScreen.class);
				startActivity(intent);
				break;
			case SiuConstants.TIPO_SELECT:
				Intent areaIntent = new Intent(SelectionController.this, AreaSelection.class);
				startActivityForResult(areaIntent, SiuConstants.AREA_SELECT);
				break;
			case SiuConstants.TEMA_SELECT:
				b = new Bundle();
				b.putInt(SiuConstants.AREA_ID_PROPERTY,areaid );
				intent = new Intent(SelectionController.this, TipoSelection.class);
				intent.putExtras(b);
				startActivityForResult(intent, SiuConstants.TIPO_SELECT);
				break;
			case SiuConstants.FOTO_SELECT:
				b = new Bundle();
				b.putInt(SiuConstants.TIPO_ID_PROPERTY,tipoid);
				intent = new Intent(SelectionController.this, TemaSelection.class);
				intent.putExtras(b);
				startActivityForResult(intent, SiuConstants.TEMA_SELECT);
				break;
			case SiuConstants.OBSERVACION_SELECT:
				intent = new Intent(SelectionController.this, FotoSelection.class);
				startActivityForResult(intent, SiuConstants.FOTO_SELECT);
				break;
			case SiuConstants.UBICACION_SELECT:
				intent = new Intent(SelectionController.this, ObservacionSelect.class);
				startActivityForResult(intent, SiuConstants.OBSERVACION_SELECT);
				break;
			default:
				intent = new Intent(SelectionController.this, MainScreen.class);
				startActivity(intent);
				break;
			}
		}
	}   

	// Creo La inspeccion y salgo
	private void creacionTerminada(){
		// TODO ver fecha
		this.inspeccion.setFecha(new Date().toString());
		new InspeccionDAO(this).create(this.inspeccion);
		Toast.makeText(getBaseContext(),"Inspeccion Generada con exito", Toast.LENGTH_LONG).show();
		ConsoleOnScreen.addText("Inspeccion Generada con exito");
		finish();
	}

}
