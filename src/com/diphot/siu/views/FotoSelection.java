package com.diphot.siu.views;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;
import com.diphot.siu.util.Util;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class FotoSelection extends Activity {

	private static final int CAMERA_REQUEST = 1888; 
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private Boolean picturetaken = false;
	private Boolean image1Take = false;
	private Boolean image2Take = false;
	private Boolean image3Take = false;


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
				/*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
				startActivityForResult(cameraIntent, CAMERA_REQUEST + view.getId());*/
				openImageIntent(CAMERA_REQUEST + view.getId());
			}
		};
		this.imageView1.setOnClickListener(o);
		this.imageView2.setOnClickListener(o);
		this.imageView3.setOnClickListener(o);
		// Inicializo el bundle para poner las fotos.
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.foto_selection, menu);
		return true;
	}*/

	// Guarda las imagenes si por algun motivo se destruye y se vuelve a crear
	// la Activity. Es suele suceder en las rotaciones de pantalla.
	public void onSaveInstanceState(Bundle savedInstanceState) {
		savedInstanceState.putParcelable(SiuConstants.IMG1_PROPERTY, ((BitmapDrawable)imageView1.getDrawable()).getBitmap());
		savedInstanceState.putParcelable(SiuConstants.IMG2_PROPERTY, ((BitmapDrawable)imageView2.getDrawable()).getBitmap());
		savedInstanceState.putParcelable(SiuConstants.IMG3_PROPERTY, ((BitmapDrawable)imageView3.getDrawable()).getBitmap());
		// al final llamamos a la super clase.
		super.onSaveInstanceState(savedInstanceState);  
	}  

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Bitmap bm1 = null;
		Bitmap bm2 = null;
		Bitmap bm3 = null;

		if (image1Take)
			bm1 = savedInstanceState.getParcelable(SiuConstants.IMG1_PROPERTY);
		if (image2Take)
			bm2 = savedInstanceState.getParcelable(SiuConstants.IMG2_PROPERTY);
		if (image3Take)
			bm3 = savedInstanceState.getParcelable(SiuConstants.IMG3_PROPERTY);

		if (bm1 != null){
			this.picturetaken = true;
			imageView1.setImageBitmap(bm1);
			imageView1.getLayoutParams().height = 250;
			imageView1.getLayoutParams().width = 250;
		}
		if (bm2 != null){
			this.picturetaken = true;
			imageView2.setImageBitmap(bm2);
			imageView2.getLayoutParams().height = 250;
			imageView2.getLayoutParams().width = 250;
		}
		if (bm3 != null){
			this.picturetaken = true;
			imageView3.setImageBitmap(bm3);
			imageView3.getLayoutParams().height = 250;
			imageView3.getLayoutParams().width = 250;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		if (resultCode == RESULT_OK) {
			this.picturetaken = true;
			if (data != null){
				this.outputFileUri = data.getData();
			}
			if (requestCode == CAMERA_REQUEST + this.imageView1.getId()){
				imageView1.getLayoutParams().height = 250;
				imageView1.getLayoutParams().width = 250;
				imageView1.setImageURI(outputFileUri);
				this.image1Take = true;
			} else if (requestCode == CAMERA_REQUEST + this.imageView2.getId()){
				imageView2.getLayoutParams().height = 250;
				imageView2.getLayoutParams().width = 250;
				imageView2.setImageURI(outputFileUri);
				this.image2Take = true;
			} else if (requestCode == CAMERA_REQUEST + this.imageView3.getId()){
				imageView3.getLayoutParams().height = 250;
				imageView3.getLayoutParams().width = 250;
				imageView3.setImageURI(outputFileUri);
				this.image3Take = true;
			}
		}  
	}

	public void next(View v){
		if (validateForm()){
			Intent returnIntent = new Intent();
			Bundle bundle = new Bundle();
			if (image1Take)
				bundle.putString(SiuConstants.IMG1_PROPERTY, Util.getEncodedImage(((BitmapDrawable)imageView1.getDrawable()).getBitmap()));
			if (image2Take)
				bundle.putString(SiuConstants.IMG2_PROPERTY, Util.getEncodedImage(((BitmapDrawable)imageView2.getDrawable()).getBitmap()));
			if (image3Take)
				bundle.putString(SiuConstants.IMG3_PROPERTY, Util.getEncodedImage(((BitmapDrawable)imageView3.getDrawable()).getBitmap()));
			returnIntent.putExtras(bundle);
			setResult(RESULT_OK,returnIntent);        
			finish();
		}
	}

	private Boolean validateForm(){
		if (!picturetaken){ // No se tomo foto.
			new AlertDialog.Builder(FotoSelection.this)
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

	Uri outputFileUri; 
	private void openImageIntent(int requestCode) {

		// Determine Uri of camera image to save.
		final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MUTIMAGES" + File.separator);
		root.mkdirs();
		final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
		final File sdImageMainDirectory = new File(root, fname);
		outputFileUri = Uri.fromFile(sdImageMainDirectory);

		// Camera.
		final List<Intent> cameraIntents = new ArrayList<Intent>();
		final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		final PackageManager packageManager = getPackageManager();
		final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
		for(ResolveInfo res : listCam) {
			final String packageName = res.activityInfo.packageName;
			final Intent intent = new Intent(captureIntent);
			intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
			intent.setPackage(packageName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
			cameraIntents.add(intent);
		}

		// Filesystem.
		final Intent galleryIntent = new Intent();
		galleryIntent.setType("image/*");
		galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

		// Chooser of filesystem options.
		final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

		// Add the camera options.
		chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[]{}));

		startActivityForResult(chooserIntent, requestCode);
	}

}
