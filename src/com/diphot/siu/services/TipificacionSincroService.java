package com.diphot.siu.services;

import java.util.ArrayList;
import android.content.Context;
import com.diphot.siu.connection.LinkChecker;
import com.diphot.siu.persistence.AreaDAO;
import com.diphot.siu.persistence.TemaDAO;
import com.diphot.siu.persistence.TipoRelevamientoDAO;
import com.diphot.siu.services.restlet.TipificacionRestLetInterface;
import com.diphot.siuweb.shared.dtos.AreaDTO;
import com.diphot.siuweb.shared.dtos.TemaDTO;
import com.diphot.siuweb.shared.dtos.TipoRelevamientoDTO;

public class TipificacionSincroService  extends AbstractService implements Runnable {

	private static Context context;
	private static TipificacionSincroService instance;
	private TipificacionSincroService (){
	}

	public static TipificacionSincroService getInstance(Context context){
		if (instance != null){
			return instance;
		} else {
			TipificacionSincroService.context = context;
			instance = new TipificacionSincroService();
			return instance;
		}
	}
	@Override
	public void run() {
		LinkChecker linkChecker = LinkChecker.getInstance(context);
		while(this.running){
			if (linkChecker.linkOK()){
				try {
					TipificacionRestLetInterface resource = WebServiceFactory.getTipificacionRestLetInterface();
					// Sincro Areas
					ArrayList<AreaDTO> areas = resource.getAreas();
					AreaDAO areaDAO = new AreaDAO(context);
					// Sincro Tipos
					ArrayList<TipoRelevamientoDTO> tipos = resource.getTiposRelevamiento();
					TipoRelevamientoDAO tipoDAO = new TipoRelevamientoDAO(context);
					// Sincro Temas
					ArrayList<TemaDTO> temas = resource.getTemas();
					TemaDAO temaDAO = new TemaDAO(context);
					// Persisto.
					areaDAO.massiveCreate(areas);
					tipoDAO.massiveCreate(tipos);
					temaDAO.massiveCreate(temas);
				}catch (Exception e) {
					e.printStackTrace();
				}catch (Error e) {
					localCreate();
				} finally{
					this.running = false;
				}
			}
			this.running = false;
		}
	}

	private void localCreate(){
		//Creadon AREAS

		ArrayList<AreaDTO> areas = new ArrayList<AreaDTO>();
		areas.add(new AreaDTO(1L,"SERVICIOS P�BLICOS")); //  Y CONSERVACI�N DE INFRAESTRUCTURA
		areas.add(new AreaDTO(2L,"CONTROL URBANO")); //Y AMBIENTAL
		areas.add(new AreaDTO(3L,"PROTECCION CIUDADANA"));
		areas.add(new AreaDTO(4L,"INSERCI�N P�BLICA"));// Y PLANEAMIENTO URBANO

		AreaDAO areaDAO = new AreaDAO(context);
		areaDAO.massiveCreate(areas);


		// Creado TIPOS

		ArrayList<TipoRelevamientoDTO> tipos = new ArrayList<TipoRelevamientoDTO>();
		// Servicios Publicos
		AreaDTO areaDTO =  areaDAO.findbyId(1L);
		tipos.add(new TipoRelevamientoDTO(1L, "V�a P�blica en General", areaDTO));
		tipos.add(new TipoRelevamientoDTO(2L, "Arbolado", areaDTO));
		tipos.add(new TipoRelevamientoDTO(3L, "Alumbrado", areaDTO));
		tipos.add(new TipoRelevamientoDTO(4L, "Servicio El�ctrico", areaDTO));
		tipos.add(new TipoRelevamientoDTO(5L, "Sem�foros", areaDTO));
		tipos.add(new TipoRelevamientoDTO(6L, "URGENCIA",areaDTO));

		// Control urbano
		areaDTO =  areaDAO.findbyId(2L);
		tipos.add(new TipoRelevamientoDTO(7L, "V�a P�blica en General",  areaDTO));

		// Proteccion
		areaDTO =  areaDAO.findbyId(3L);
		tipos.add(new TipoRelevamientoDTO(8L, "V�a P�blica en General",  areaDTO));
		tipos.add(new TipoRelevamientoDTO(9L, "Urgencia",  areaDTO));

		// Insercion
		areaDTO =  areaDAO.findbyId(4L);
		tipos.add(new TipoRelevamientoDTO(10L, "Obras", areaDTO));

		TipoRelevamientoDAO tipoDAO = new TipoRelevamientoDAO(context);
		tipoDAO.massiveCreate(tipos);

		// Creando Temas


		ArrayList<TemaDTO> temas = new ArrayList<TemaDTO>();

		/*********************
		 * SERVICIOS P�BLICOS*
		 ********************/
		// Via Publica General
		TipoRelevamientoDTO tipoDTO = tipoDAO.findbyId(1L); 
		temas.add(new TemaDTO(1L,"Residuos domiciliarios",tipoDTO));
		temas.add(new TemaDTO(2L,"Mont�culos de Tierra y/o escombros",tipoDTO));
		temas.add(new TemaDTO(3L,"Mont�culos de Ramas y/o Basura",tipoDTO));
		temas.add(new TemaDTO(4L,"Residuos Acumulados en Esquina y/o bald�os",tipoDTO));
		temas.add(new TemaDTO(5L,"Cestos de Residuos / cumplimiento horario sacar residuos",tipoDTO));
		temas.add(new TemaDTO(6L,"Transitabilidad de calles de tierra",tipoDTO));
		temas.add(new TemaDTO(7L,"Baches en Asfalto Vecinal",tipoDTO));
		temas.add(new TemaDTO(8L,"Baches o Losas en Asfaltos de Hormig�n",tipoDTO));
		temas.add(new TemaDTO(9L,"Cordones rotos",tipoDTO));
		temas.add(new TemaDTO(10L,"Tomado de Juntas",tipoDTO));
		temas.add(new TemaDTO(11L,"Estado de rampas para discapacitados",tipoDTO));
		temas.add(new TemaDTO(12L,"Estado de Sendas peatonales",tipoDTO));
		temas.add(new TemaDTO(13L,"Pintura amarilla vial en esquinas c�ntricas",tipoDTO));
		temas.add(new TemaDTO(14L,"Pintura de L�neas de divisi�n y pare en avenidas",tipoDTO));
		temas.add(new TemaDTO(15L,"Veredas rotas o inexistentes",tipoDTO));
		temas.add(new TemaDTO(16L,"Limpiezas de zanjas y/o Aguas estancadas",tipoDTO));
		temas.add(new TemaDTO(17L,"Limpieza de sumideros y/o desag�es pluviales",tipoDTO));
		temas.add(new TemaDTO(18L,"Limpieza de Arroyos",tipoDTO));
		temas.add(new TemaDTO(19L,"Falta de tapas de c�mara y/o sumideros",tipoDTO));
		temas.add(new TemaDTO(20L,"Refugios de parada de colectivos rotos o faltantes",tipoDTO));
		temas.add(new TemaDTO(21L,"OTROS",tipoDTO));

		// Arbolado
		tipoDTO = tipoDAO.findbyId(2L);
		temas.add(new TemaDTO(22L,"Mantenimiento de Parquizaci�n y arboleda",tipoDTO));
		temas.add(new TemaDTO(23L,"Estado de seguridad y pintura de juegos",tipoDTO));
		temas.add(new TemaDTO(24L,"Estado de higiene de residuos peligrosos en areneros",tipoDTO));
		temas.add(new TemaDTO(25L,"Estado de higiene en general",tipoDTO));
		temas.add(new TemaDTO(26L,"Estado de Limpieza y pintura de bancos y monumentos",tipoDTO));
		temas.add(new TemaDTO(27L,"Funcionamiento de Fuentes",tipoDTO));
		temas.add(new TemaDTO(28L,"�rbol Ca�do / o en riesgo",tipoDTO));
		temas.add(new TemaDTO(29L,"Ra�ces levantan vereda",tipoDTO));
		temas.add(new TemaDTO(30L,"Poda de �rboles/Ligustros",tipoDTO));
		temas.add(new TemaDTO(31L,"OTROS",tipoDTO));

		// Alumbrado
		tipoDTO = tipoDAO.findbyId(3L);
		temas.add(new TemaDTO(32L,"Fallas en L�mparas",tipoDTO));
		temas.add(new TemaDTO(33L,"Columnas de Alumbrado Oxidadas en la base",tipoDTO));
		temas.add(new TemaDTO(34L,"Ausencia de Jabalina",tipoDTO));
		temas.add(new TemaDTO(35L,"Columnas de alumbrado Despintadas",tipoDTO));
		temas.add(new TemaDTO(36L,"Tulipas Rotas",tipoDTO));
		temas.add(new TemaDTO(37L,"Tulipas Sucias",tipoDTO));
		temas.add(new TemaDTO(38L,"Columnas de Alumbrado dobladas/Torcidas",tipoDTO));
		temas.add(new TemaDTO(39L,"Columnas de Alumbrado con Riesgos El�ctricos a terceros",tipoDTO));
		temas.add(new TemaDTO(40L,"OTROS",tipoDTO));

		//Servicio El�ctrico
		tipoDTO = tipoDAO.findbyId(4L);
		temas.add(new TemaDTO(41L,"Presunto robo de energ�a el�ctrica con cableados precarios",tipoDTO));
		temas.add(new TemaDTO(42L,"Funcionamiento Bombas Depresoras de napas",tipoDTO));
		temas.add(new TemaDTO(43L,"Conexiones no reglamentarias",tipoDTO));
		temas.add(new TemaDTO(44L,"Tendidos el�ctricos deteriorados",tipoDTO));
		temas.add(new TemaDTO(45L,"OTROS",tipoDTO));

		// Sem�foros
		tipoDTO = tipoDAO.findbyId(5L);
		temas.add(new TemaDTO(46L,"No funciona",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(47L,"Luz apagada",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(48L,"Todo encendido",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(49L,"Aparato da�ado",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(50L,"OTROS",tipoDAO.findbyId(5L)));

		// Urgencias 
		tipoDTO = tipoDAO.findbyId(6L);
		temas.add(new TemaDTO(51L,"Columna de Alumbrado ca�da",tipoDTO));
		temas.add(new TemaDTO(52L,"Columna de alumbrado electrificada",tipoDTO));
		temas.add(new TemaDTO(53L,"Cables de ALUMBRADO cortado ( no EDENOR)",tipoDTO));
		temas.add(new TemaDTO(54L,"�rbol ca�do o por caer",tipoDTO));
		temas.add(new TemaDTO(55L,"Aparato da�ado",tipoDTO));
		temas.add(new TemaDTO(56L,"Sem�foro no funciona en cruce peligroso",tipoDTO));
		temas.add(new TemaDTO(57L,"Poste de Edenor / Tel�fono chocado",tipoDTO));

		/*********************
		 * CONTROL URBANO Y AMBIENTAL*
		 ********************/
		//V�a P�blica en General
		tipoDTO = tipoDAO.findbyId(7L);
		temas.add(new TemaDTO(58L,"Ocupaci�n Indebida del espacio p�blico",tipoDTO));
		temas.add(new TemaDTO(59L,"Inspecci�n General",tipoDTO));
		temas.add(new TemaDTO(60L,"Malos olores o gases t�xicos en la v�a p�blica",tipoDTO));
		temas.add(new TemaDTO(61L,"OTROS",tipoDTO));

		/*********************
		 * PROTECCION CIUDADANA*
		 ********************/
		//V�a P�blica en General
		tipoDTO = tipoDAO.findbyId(8L);
		temas.add(new TemaDTO(62L,"Desarmaderos de autos / Lugares de actividades Ilegales",tipoDTO));
		temas.add(new TemaDTO(63L,"Estado de Carteles de Se�alizaci�n Vial",tipoDTO));
		temas.add(new TemaDTO(64L,"Autos abandonados / Quemados",tipoDTO));
		temas.add(new TemaDTO(65L,"D�rsena de parada de colectivos",tipoDTO));
		temas.add(new TemaDTO(66L,"Carga y Descarga / Estacionamiento doble fila",tipoDTO));
		temas.add(new TemaDTO(67L,"OTROS",tipoDTO));

		// URGENCIA
		tipoDTO = tipoDAO.findbyId(9L);
		temas.add(new TemaDTO(68L,"Escape de Gas",tipoDTO));
		temas.add(new TemaDTO(69L,"Cables de Edenor cortados",tipoDTO));
		temas.add(new TemaDTO(70L,"Accidente de tr�nsito",tipoDTO));
		temas.add(new TemaDTO(71L,"Emergencia m�dica",tipoDTO));

		/*********************
		 * INERSI�N P�BLICA*
		 ********************/
		// Obras
		tipoDTO = tipoDAO.findbyId(10L);
		temas.add(new TemaDTO(72L,"Se�alamiento y/o balizamiento de obras en v�a p�blica",tipoDTO));
		temas.add(new TemaDTO(73L,"Calles nuevas de Asfalto Vecinal",tipoDTO));
		temas.add(new TemaDTO(74L,"Calles nuevas de asfalto de hormig�n",tipoDTO));
		temas.add(new TemaDTO(75L,"Limpieza de costas y /o residuos flotando en arroyos",tipoDTO));
		temas.add(new TemaDTO(76L,"Entubamientos",tipoDTO));
		temas.add(new TemaDTO(77L,"Desag�es",tipoDTO));
		temas.add(new TemaDTO(78L,"Obras de Edenor",tipoDTO));
		temas.add(new TemaDTO(79L,"Obras de Gas Natural",tipoDTO));
		temas.add(new TemaDTO(80L,"Obras de video cable",tipoDTO));
		temas.add(new TemaDTO(91L,"Obras de telefon�a",tipoDTO));
		temas.add(new TemaDTO(92L,"Antenas de telefon�a",tipoDTO));
		temas.add(new TemaDTO(93L,"Obras de Agua Corriente y/o cloacas",tipoDTO));
		temas.add(new TemaDTO(94L,"OTROS",tipoDTO));

		TemaDAO temaDAO = new TemaDAO(context);
		temaDAO.massiveCreate(temas);

	}
}
