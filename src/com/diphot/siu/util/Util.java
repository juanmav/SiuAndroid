package com.diphot.siu.util;

import com.diphot.siu.R;

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

}
