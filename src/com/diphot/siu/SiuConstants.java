package com.diphot.siu;

public interface SiuConstants {
	public static final int AREA_SELECT = 1;
	public static final int TIPO_SELECT = 2;
	public static final int TEMA_SELECT = 3;
	public static final int FOTO_SELECT = 4;
	public static final int OBSERVACION_SELECT = 6;
	public static final int UBICACION_SELECT = 5;

	public static final int ALTO = 3;
	public static final int MEDIO = 2;
	public static final int BAJO = 1;

	final public static int OBSERVADO = 1;
	final public static int CONFIRMADO = 2;
	final public static int EJECUTADO = 3;
	final public static int RESUELTO = 4;

	public static final String AREA_ID_PROPERTY = "areaid";
	public static final String TIPO_ID_PROPERTY = "tipoid";
	public static final String TEMA_ID_PROPERTY = "temaid";
	public static final String OBSERVACION_PROPERTY = "observacion";
	public static final String IMG1_PROPERTY = "IMG1";
	public static final String IMG2_PROPERTY = "IMG2";
	public static final String IMG3_PROPERTY = "IMG3";
	public static final String INSPECCION_PROPERTY = "inspeccion";

	public static final String CALLE_PROPERTY = "calle";
	public static final String ALTURA_PROPERTY = "altura";
	public static final String LATITUDE_PROPERTY = "latitude";
	public static final String LONGITUDE_PROPERTY = "longitude";
	public static final String LOCALIDAD_PROPERTY = "localidad";

	public static final String RIESGO_PROPERTY = "riesgo";
	public static final String ESTADO_PROPERTY = "estado";

	//public static final String URL_BACKED = "http://siuwebs.appspot.com/mobileendpointService";
	public static final String URL_BACKEND = "http://192.168.0.150:8888/mobileendpointService";
	public static final String HostIP = "http://siuwebs.appspot.com/";



	//public static String REST_BACKEND = "http://192.168.0.200:8888/rest";
	//public static String REST_BACKEND = "http://www.diphot.com.ar:8888/rest";
	
	//public static String REST_BACKEND = "http://192.168.0.200:8888/rest";
	public static final String REST_BACKEND = "http://siuwebstest.appspot.com/rest";
	public static final String REST_TIPIFICACION = "/tipificacion";
	public static final String REST_INSPECCION = "/inspecciones";
	public static final String REST_INSPECCION_DOS = "/inspeccionesdos";
	public static final String REST_AUDITORIA = "/auditorias";
	public static final String REST_USER = "/user";

	public static final String URL_INSPECCIONES = SiuConstants.REST_BACKEND + SiuConstants.REST_INSPECCION;
	public static final String URL_INSPECCIONES_DOS = SiuConstants.REST_BACKEND + SiuConstants.REST_INSPECCION_DOS;
	public static final String URL_TIPIFICACION = SiuConstants.REST_BACKEND + SiuConstants.REST_TIPIFICACION; 
	public static final String URL_AUDITORIAS = SiuConstants.REST_BACKEND + SiuConstants.REST_AUDITORIA;
	public static final String URL_USER = SiuConstants.REST_BACKEND + SiuConstants.REST_USER; 

	public static final Boolean debug = true;

	public interface ROLES {
		// Roles
		public static final String ADMIN = "ADMIN";
		public static final String SUPERVISOR = "SUPERVISOR";
		public static final String SECRETARIA = "SECRETARIA";
		public static final String INSPECTOR = "INSPECTOR";
		
	}
}
