package com.diphot.siu.views;

import java.io.ByteArrayOutputStream;

import com.diphot.siu.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.graphics.Bitmap;

public class FotoSelection extends Activity {

	private static final int CAMERA_REQUEST = 1888; 
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private Bundle bundle;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_foto_selection);
		this.imageView1 = (ImageView)this.findViewById(R.id.result01);
		this.imageView2 = (ImageView)this.findViewById(R.id.result02);
		this.imageView3 = (ImageView)this.findViewById(R.id.result03);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foto_selection, menu);
		return true;
	}

	public void tomarFoto(View v){
		 Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
         startActivityForResult(cameraIntent, CAMERA_REQUEST); 
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        // TODO hacer tres fotos distintas.
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap bm = (Bitmap) data.getExtras().get("data"); 
            imageView1.setImageBitmap(bm);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();  
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object   
            byte[] b = baos.toByteArray(); 
            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            System.out.println(encodedImage);
            this.bundle = new Bundle();
            bundle.putString(SiuConstants.IMG1_PROPERTY, encodedImage);
            bundle.putString(SiuConstants.IMG2_PROPERTY, encodedImage);
            bundle.putString(SiuConstants.IMG3_PROPERTY, encodedImage);
        }  
    } 
	
	public void next(View v){
		Intent returnIntent = new Intent();
		returnIntent.putExtras(this.bundle);
		setResult(RESULT_OK,returnIntent);        
		finish();
	}
	
}
