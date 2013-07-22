package com.diphot.siu.views;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class AuditoriaDetail extends Activity {

	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private Bitmap bm1;
	private Bitmap bm2;
	private Bitmap bm3;
	private static final int CAMERA_REQUEST = 1888; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auditoria_detail);
		this.imageView1 = (ImageView)this.findViewById(R.id.au_img1);
		this.imageView2 = (ImageView)this.findViewById(R.id.au_img2);
		this.imageView3 = (ImageView)this.findViewById(R.id.au_img3);
		OnClickListener o = new OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST + view.getId()); 
			}
		};
		this.imageView1.setOnClickListener(o);
		this.imageView2.setOnClickListener(o);
		this.imageView3.setOnClickListener(o);
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
			Bitmap bm = (Bitmap) data.getExtras().get("data"); 
			if (requestCode == CAMERA_REQUEST + this.imageView1.getId()){
				bm1 = bm;
				imageView1.setImageBitmap(bm1);
				/*imageView1.getLayoutParams().height = 75;
				imageView1.getLayoutParams().width = 75;*/
			} else if (requestCode == CAMERA_REQUEST + this.imageView2.getId()){
				bm2 = bm;
				imageView2.setImageBitmap(bm2);
				/*imageView2.getLayoutParams().height = 75;
				imageView2.getLayoutParams().width = 75;*/
			} else if (requestCode == CAMERA_REQUEST + this.imageView3.getId()){
				bm3 = bm;
				imageView3.setImageBitmap(bm3);
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
		if (bm1 != null)
			imageView1.setImageBitmap(bm1);
		if (bm2 != null)
			imageView2.setImageBitmap(bm2);
		if (bm3 != null)
			imageView3.setImageBitmap(bm3);
	}

	public void send(View v){
		Toast.makeText(getBaseContext(),"Auditoria Generada con exito", Toast.LENGTH_LONG).show();
		this.finish();
	}
	
}
