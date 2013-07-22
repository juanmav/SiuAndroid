package com.diphot.siu.views;

import java.io.ByteArrayOutputStream;
import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.graphics.Bitmap;

public class FotoSelection extends Activity {

	private static final int CAMERA_REQUEST = 1888; 
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private Bitmap bm1;
	private Bitmap bm2;
	private Bitmap bm3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto_selection);
		this.imageView1 = (ImageView)this.findViewById(R.id.result01);
		this.imageView2 = (ImageView)this.findViewById(R.id.result02);
		this.imageView3 = (ImageView)this.findViewById(R.id.result03);
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
		// Inicializo el bundle para poner las fotos.
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foto_selection, menu);
		return true;
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
			imageView1.setImageBitmap(bm1);
			imageView1.getLayoutParams().height = 250;
			imageView1.getLayoutParams().width = 250;
		}
		if (bm2 != null){
			imageView2.setImageBitmap(bm2);
			imageView2.getLayoutParams().height = 250;
			imageView2.getLayoutParams().width = 250;
		}
		if (bm3 != null){
			imageView3.setImageBitmap(bm3);
			imageView3.getLayoutParams().height = 250;
			imageView3.getLayoutParams().width = 250;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		// TODO hacer tres fotos distintas.
		if (resultCode == RESULT_OK) {
			Bitmap bm = (Bitmap) data.getExtras().get("data"); 
			if (requestCode == CAMERA_REQUEST + this.imageView1.getId()){
				bm1 = bm;
				imageView1.setImageBitmap(bm1);
				imageView1.getLayoutParams().height = 250;
				imageView1.getLayoutParams().width = 250;
			} else if (requestCode == CAMERA_REQUEST + this.imageView2.getId()){
				bm2 = bm;
				imageView2.setImageBitmap(bm2);
				imageView2.getLayoutParams().height = 250;
				imageView2.getLayoutParams().width = 250;
			} else if (requestCode == CAMERA_REQUEST + this.imageView3.getId()){
				bm3 = bm;
				imageView3.setImageBitmap(bm3);
				imageView3.getLayoutParams().height = 250;
				imageView3.getLayoutParams().width = 250;
			}
		}  
	}

	public String getEncodedImage(Bitmap bm){
		String encodedImage;
		if (bm != null){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
			byte[] b = baos.toByteArray(); 
			encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		}else {
			encodedImage = "";
		}
		return encodedImage;
	}


	public void next(View v){
		Intent returnIntent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(SiuConstants.IMG1_PROPERTY, getEncodedImage(bm1));
		bundle.putString(SiuConstants.IMG2_PROPERTY, getEncodedImage(bm2));
		bundle.putString(SiuConstants.IMG3_PROPERTY, getEncodedImage(bm3));
		returnIntent.putExtras(bundle);
		setResult(RESULT_OK,returnIntent);        
		finish();
	}
}
