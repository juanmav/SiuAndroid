package com.diphot.siu.views;

import java.util.Date;

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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Aca me vuelven los resultados de las distintas activities y voy armando la Inspeccion.
		if (resultCode == RESULT_OK){
			Bundle b;
			Intent intent;
			switch (requestCode){
			case SiuConstants.AREA_SELECT:
				int areaid = data.getIntExtra(SiuConstants.AREA_ID_PROPERTY,0);
				b = new Bundle();
				b.putInt(SiuConstants.AREA_ID_PROPERTY,areaid );
				intent = new Intent(SelectionController.this, TipoSelection.class);
				intent.putExtras(b);
				startActivityForResult(intent, SiuConstants.TIPO_SELECT);
				break;
			case SiuConstants.TIPO_SELECT:
				int tipoid = data.getIntExtra(SiuConstants.TIPO_ID_PROPERTY,0);
				b = new Bundle();
				b.putInt(SiuConstants.TIPO_ID_PROPERTY,tipoid);
				intent = new Intent(SelectionController.this, TemaSelection.class);
				intent.putExtras(b);
				startActivityForResult(intent, SiuConstants.TEMA_SELECT);
				break;
			case SiuConstants.TEMA_SELECT:
				int temaid = data.getIntExtra(SiuConstants.TEMA_ID_PROPERTY,0);
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
			// TODO aca se viene porque no hubo una seleccion exitosa
			// o porque se apreto el boton retroceder.
		}
	}   

	// Creo La inspeccion y salgo
	private void creacionTerminada(){
		this.inspeccion.setFecha(new Date());
		new InspeccionDAO(this).create(this.inspeccion);
		Toast.makeText(getBaseContext(),"Inspeccion Generada con exito", Toast.LENGTH_LONG).show();  
		finish();
	}

}
