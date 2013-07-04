package com.diphot.siu.views;

public interface SiuConstants {
	public static final int AREA_SELECT = 1;
	public static final int TIPO_SELECT = 2;
	public static final int TEMA_SELECT = 3;
	public static final int FOTO_SELECT = 4;
	public static final int OBSERVACION_SELECT = 6;
	public static final int UBICACION_SELECT = 5;
			
	public static String AREA_ID_PROPERTY = "areaid";
	public static String TIPO_ID_PROPERTY = "tipoid";
	public static String TEMA_ID_PROPERTY = "temaid";
	public static String OBSERVACION_PROPERTY = "observacion";
	public static String IMG1_PROPERTY = "IMG1";
	public static String IMG2_PROPERTY = "IMG2";
	public static String IMG3_PROPERTY = "IMG3";
	
	public static String CALLE_PROPERTY = "calle";
	public static String ALTURA_PROPERTY = "altura";
	public static String LATITUDE_PROPERTY = "latitude";
	public static String LONGITUDE_PROPERTY = "longitude";
	
	//public static String URL_BACKED = "http://siuwebs.appspot.com/mobileendpointService";
	public static String URL_BACKEND = "http://192.168.0.225:8888/mobileendpointService";
	public static String HostIP = "8.8.8.8";
	
	public static String REST_BACKEND = "http://192.168.0.132:8888/rest";
	public static String REST_TIPIFICACION = "/tipificacion";
	public static String REST_INSPECCION = "/inspecciones";
	public static String REST_AUDITORIA = "/auditorias";
	
	public static Boolean debug = true;
}
