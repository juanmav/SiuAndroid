package com.diphot.siu.views.popup;

import com.diphot.siu.R;
import com.diphot.siu.views.inspecciones.InspeccionDetail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class ImagePopup extends PopupWindow{

	public ImagePopup(Context context, Bitmap bitmap){
		super(context);
		LinearLayout viewGroup = (LinearLayout) ((Activity) context).findViewById(R.id.imagePopup);
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = layoutInflater.inflate(R.layout.image_popup, viewGroup);

		this.setContentView(layout);
		this.setWidth(600);
		this.setHeight(700);
		this.setFocusable(true);
		this.showAtLocation(layout, Gravity.CENTER, 0, 0);

		// Clear the default translucent background
		this.setBackgroundDrawable(new BitmapDrawable());

		ImageView img = (ImageView) layout.findViewById(R.id.imgpop);
		img.setImageBitmap(bitmap);
		
		// Getting a reference to Close button, and close the popup when clicked.
		Button close = (Button) layout.findViewById(R.id.imgClose);
		close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ImagePopup.this.dismiss();
			}
		});
	}

}
