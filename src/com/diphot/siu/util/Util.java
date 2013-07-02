package com.diphot.siu.util;

public class Util {

	public static int getColor(Long ident){
		int ID = Integer.parseInt(ident.toString());

		switch (ID) {
		case 1:
			return 0xFF0000;
		case 2:
			return 0x00FF00;
		case 3:
			return 0x0000FF;
		case 4:
			return 0xFFFF00;
		case 5:
			return 0x00FFFF;
		case 6:
			return 0xFF00FF;
		default:
			return 0xFFFFFF;
		}
	}

}
