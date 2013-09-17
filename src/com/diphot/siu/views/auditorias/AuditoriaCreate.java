package com.diphot.siu.views.auditorias;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.UserContainer;
import com.diphot.siu.services.WebServiceFactory;
import com.diphot.siu.services.restlet.AuditoriaRestLetInterface;
import com.diphot.siu.util.AsyncFunctionWrapper;
import com.diphot.siu.util.Util;
import com.diphot.siu.util.AsyncFunctionWrapper.Callable;
import com.diphot.siuweb.shared.dtos.AuditoriaDTO;
import com.diphot.siuweb.shared.dtos.InspeccionDTO;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class AuditoriaCreate extends Activity {

	private ImageView au_img1;
	private ImageView au_img2;
	private ImageView au_img3;
	private ImageView au_mapImg;
	private TextView au_ins_id;
	private EditText au_observacion_text;
	private Bitmap bm1;
	private Bitmap bm2;
	private Bitmap bm3;
	private RadioButton radiosi;
	private Boolean picturetaken = false;

	private static final int CAMERA_REQUEST = 1888; 

	private InspeccionDTO idto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditoria_create);
		Bundle b = getIntent().getExtras();
		idto = (InspeccionDTO) b.getSerializable(SiuConstants.INSPECCION_PROPERTY);

		this.au_img1 = (ImageView)this.findViewById(R.id.au_img1);
		this.au_img2 = (ImageView)this.findViewById(R.id.au_img2);
		this.au_img3 = (ImageView)this.findViewById(R.id.au_img3);
		this.au_mapImg =  (ImageView)this.findViewById(R.id.au_mapImg);
		this.radiosi  = (RadioButton) this.findViewById(R.id.radio_si);
		this.au_observacion_text = (EditText) this.findViewById(R.id.au_observacion_text);

		this.au_mapImg.setImageBitmap(Util.getBitmap(this.idto.getImgMap()));
		this.au_ins_id =  (TextView)this.findViewById(R.id.au_ins_id);
		this.au_ins_id.setText(idto.getId().toString());

		addListeners();
	}


	private void addListeners (){

		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST + view.getId()); 
			}
		};
		this.au_img1.setOnClickListener(o);
		this.au_img2.setOnClickListener(o);
		this.au_img3.setOnClickListener(o);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.auditoria_detail, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		// TODO hacer tres fotos distintas.
		if (resultCode == RESULT_OK) {
			this.picturetaken = true;
			Bitmap bm = (Bitmap) data.getExtras().get("data"); 
			if (requestCode == CAMERA_REQUEST + this.au_img1.getId()){
				bm1 = bm;
				au_img1.setImageBitmap(bm1);
				/*imageView1.getLayoutParams().height = 75;
				imageView1.getLayoutParams().width = 75;*/
			} else if (requestCode == CAMERA_REQUEST + this.au_img2.getId()){
				bm2 = bm;
				au_img2.setImageBitmap(bm2);
				/*imageView2.getLayoutParams().height = 75;
				imageView2.getLayoutParams().width = 75;*/
			} else if (requestCode == CAMERA_REQUEST + this.au_img3.getId()){
				bm3 = bm;
				au_img3.setImageBitmap(bm3);
				/*imageView3.getLayoutParams().height = 75;
				imageView3.getLayoutParams().width = 75;*/
			}
		}  
	}

	// Guarda las imagenes si por algun motivo se destruye y se vuelve a crear
	// la Activity. Es suele suceder en las rotaciones de pantalla.
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putParcelable(SiuConstants.IMG1_PROPERTY, bm1);
		savedInstanceState.putParcelable(SiuConstants.IMG2_PROPERTY, bm2);
		savedInstanceState.putParcelable(SiuConstants.IMG3_PROPERTY, bm3);
		// al final llamamos a la super clase.
		super.onSaveInstanceState(savedInstanceState);  
	}  

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		bm1 = savedInstanceState.getParcelable(SiuConstants.IMG1_PROPERTY);
		bm2 = savedInstanceState.getParcelable(SiuConstants.IMG2_PROPERTY);
		bm3 = savedInstanceState.getParcelable(SiuConstants.IMG3_PROPERTY);
		if (bm1 != null){
			this.picturetaken = true;
			au_img1.setImageBitmap(bm1);
		}
		if (bm2 != null){
			this.picturetaken = true;
			au_img2.setImageBitmap(bm2);
		}
		if (bm3 != null){
			this.picturetaken = true;
			au_img3.setImageBitmap(bm3);
		}

	}

	public void send(View v){
		if (validateForm()){
			final AuditoriaDTO audto = new AuditoriaDTO();

			audto.setInspeccionID(idto.getId());
			audto.setResuelto(radiosi.isChecked());
			audto.setObservaciones(au_observacion_text.getText().toString());

			audto.setImg1(Util.getEncodedImage(bm1));
			audto.setImg2(Util.getEncodedImage(bm2));
			audto.setImg3(Util.getEncodedImage(bm3));

			new AsyncFunctionWrapper(this).execute("Creando Auditoria", "Procesando...", new Callable() {
				@Override
				public Integer call() {
					Integer result = Callable.NOTOK;
					try {
						AuditoriaRestLetInterface resource = WebServiceFactory.getAuditoriaRestLetInterface();
						audto.token = UserContainer.getUserDTO().getToken();
						// Bloqueante
						resource.create(audto);
						// Poner el resultado depues de la funcion bloqueante por si arroja una excepcion.
						result = Callable.OK;
					} catch (Exception e){
						result = Callable.NOTOK;
					}
					return result;
				}
			}, new Callable(){
				@Override
				public Integer call() {
					Toast.makeText(getBaseContext(),"Auditoria Generada con exito", Toast.LENGTH_LONG).show();
					AuditoriaCreate.this.finish();
					return Callable.OK;
				}

			});
		}
	}

	private Boolean validateForm(){
		if (!picturetaken){ // No se tomo foto.
			new AlertDialog.Builder(AuditoriaCreate.this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("ERROR")
			.setMessage("Por lo menos debe tomar una imagen")
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();
		}
		return picturetaken;
	}

}
