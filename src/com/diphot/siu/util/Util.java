package com.diphot.siu.util;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Switch;

import com.diphot.siu.R;
import com.diphot.siu.SiuConstants;

public class Util {

	public static int getColor(Long ident){
		int ID = Integer.parseInt(ident.toString());
		return getColor(ID);
	}

	public static int getColor(int i){
		if (i > 9){
			i = i % 10;
		}
		switch (i) {
		case 0:
			return R.drawable.btn_grey;
		case 1:
			return R.drawable.btn_red;
		case 2:
			return R.drawable.btn_green;
		case 3:
			return R.drawable.btn_blue;
		case 4:
			return R.drawable.btn_yellow;
		case 5:
			return R.drawable.btn_cyan;
		case 6:
			return R.drawable.btn_orange;
		case 7:
			return R.drawable.btn_ligth_blue;
		case 8:
			return R.drawable.btn_pink;
		case 9: 
			return R.drawable.btn_purple;
		default:
			return R.drawable.btn_black;
		}
	}

	public static String riesgoIDtoString(int riesgoid){
		switch (riesgoid) {
		case SiuConstants.ALTO:
			return "ALTO";
		case SiuConstants.MEDIO:
			return "MEDIO";
		case SiuConstants.BAJO:
			return "BAJO";
		default:
			return "";
		}

	}
	
	public static String stateIDToString (int lastStateIdentifier){
		
		switch (lastStateIdentifier) {
		case SiuConstants.OBSERVADO:
			return "Observado";
		case SiuConstants.CONFIRMADO:
			return "CONFIRMADO";
		case SiuConstants.EJECUTADO:
			return "EJECUTADO";
		case SiuConstants.RESUELTO:
			return "RESUELTO";
		default:
			return "";
		}
	}
	
	public static Bitmap getBitmap(String encodedString){
		if (encodedString != null && !encodedString.equals("")){
			byte[] decodedString = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
			return decodedByte;
		} else {
			return null;
		}	
	}
	
	public static String getEncodedImage(Bitmap bm){
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

}
