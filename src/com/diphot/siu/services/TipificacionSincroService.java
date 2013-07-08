package com.diphot.siu.services;

import java.util.ArrayList;

import org.restlet.resource.ClientResource;
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
					ClientResource cr = new ClientResource(TipificacionRestLetInterface.URL);
					TipificacionRestLetInterface resource = cr.wrap(TipificacionRestLetInterface.class);
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
					pause(3600);
				}
			}
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
		tipos.add(new TipoRelevamientoDTO(1L, "V�a P�blica en General", areaDAO.findbyId(1L)));
		tipos.add(new TipoRelevamientoDTO(2L, "Arbolado", areaDAO.findbyId(1L)));
		tipos.add(new TipoRelevamientoDTO(3L, "Alumbrado", areaDAO.findbyId(1L)));
		tipos.add(new TipoRelevamientoDTO(4L, "Servicio El�ctrico", areaDAO.findbyId(1L)));
		tipos.add(new TipoRelevamientoDTO(5L, "Sem�foros", areaDAO.findbyId(1L)));
		tipos.add(new TipoRelevamientoDTO(6L, "URGENCIA", areaDAO.findbyId(1L)));

		// Control urbano
		tipos.add(new TipoRelevamientoDTO(7L, "V�a P�blica en General",  areaDAO.findbyId(2L)));

		// Proteccion
		tipos.add(new TipoRelevamientoDTO(8L, "V�a P�blica en General",  areaDAO.findbyId(3L)));
		tipos.add(new TipoRelevamientoDTO(9L, "Urgencia",  areaDAO.findbyId(3L)));

		// Insercion
		tipos.add(new TipoRelevamientoDTO(10L, "Obras",  areaDAO.findbyId(4L)));
		
		TipoRelevamientoDAO tipoDAO = new TipoRelevamientoDAO(context);
		tipoDAO.massiveCreate(tipos);
			
		// Creando Temas
		
		
		ArrayList<TemaDTO> temas = new ArrayList<TemaDTO>();
		
		/*********************
		 * SERVICIOS P�BLICOS*
		 ********************/
		// Via Publica General
		temas.add(new TemaDTO(1L,"Residuos domiciliarios",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(2L,"Mont�culos de Tierra y/o escombros",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(3L,"Mont�culos de Ramas y/o Basura",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(4L,"Residuos Acumulados en Esquina y/o bald�os",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(5L,"Cestos de Residuos / cumplimiento horario sacar residuos",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(6L,"Transitabilidad de calles de tierra",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(7L,"Baches en Asfalto Vecinal",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(8L,"Baches o Losas en Asfaltos de Hormig�n",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(9L,"Cordones rotos",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(10L,"Tomado de Juntas",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(11L,"Estado de rampas para discapacitados",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(12L,"Estado de Sendas peatonales",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(13L,"Pintura amarilla vial en esquinas c�ntricas",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(14L,"Pintura de L�neas de divisi�n y pare en avenidas",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(15L,"Veredas rotas o inexistentes",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(16L,"Limpiezas de zanjas y/o Aguas estancadas",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(17L,"Limpieza de sumideros y/o desag�es pluviales",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(18L,"Limpieza de Arroyos",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(19L,"Falta de tapas de c�mara y/o sumideros",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(20L,"Refugios de parada de colectivos rotos o faltantes",tipoDAO.findbyId(1L)));
		temas.add(new TemaDTO(21L,"OTROS",tipoDAO.findbyId(1L)));

		// Arbolado
		temas.add(new TemaDTO(22L,"Mantenimiento de Parquizaci�n y arboleda",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(23L,"Estado de seguridad y pintura de juegos",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(24L,"Estado de higiene de residuos peligrosos en areneros",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(25L,"Estado de higiene en general",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(26L,"Estado de Limpieza y pintura de bancos y monumentos",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(27L,"Funcionamiento de Fuentes",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(28L,"�rbol Ca�do / o en riesgo",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(29L,"Ra�ces levantan vereda",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(30L,"Poda de �rboles/Ligustros",tipoDAO.findbyId(2L)));
		temas.add(new TemaDTO(31L,"OTROS",tipoDAO.findbyId(2L)));

		// Alumbrado
		temas.add(new TemaDTO(32L,"Fallas en L�mparas",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(33L,"Columnas de Alumbrado Oxidadas en la base",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(34L,"Ausencia de Jabalina",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(35L,"Columnas de alumbrado Despintadas",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(36L,"Tulipas Rotas",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(37L,"Tulipas Sucias",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(38L,"Columnas de Alumbrado dobladas/Torcidas",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(39L,"Columnas de Alumbrado con Riesgos El�ctricos a terceros",tipoDAO.findbyId(3L)));
		temas.add(new TemaDTO(40L,"OTROS",tipoDAO.findbyId(3L)));
		
		//Servicio El�ctrico
		
		temas.add(new TemaDTO(41L,"Presunto robo de energ�a el�ctrica con cableados precarios",tipoDAO.findbyId(4L)));
		temas.add(new TemaDTO(42L,"Funcionamiento Bombas Depresoras de napas",tipoDAO.findbyId(4L)));
		temas.add(new TemaDTO(43L,"Conexiones no reglamentarias",tipoDAO.findbyId(4L)));
		temas.add(new TemaDTO(44L,"Tendidos el�ctricos deteriorados",tipoDAO.findbyId(4L)));
		temas.add(new TemaDTO(45L,"OTROS",tipoDAO.findbyId(4L)));
		
		// Sem�foros
		
		temas.add(new TemaDTO(46L,"No funciona",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(47L,"Luz apagada",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(48L,"Todo encendido",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(49L,"Aparato da�ado",tipoDAO.findbyId(5L)));
		temas.add(new TemaDTO(50L,"OTROS",tipoDAO.findbyId(5L)));
		
		// Urgencias 
		
		temas.add(new TemaDTO(51L,"Columna de Alumbrado ca�da",tipoDAO.findbyId(6L)));
		temas.add(new TemaDTO(52L,"Columna de alumbrado electrificada",tipoDAO.findbyId(6L)));
		temas.add(new TemaDTO(53L,"Cables de ALUMBRADO cortado ( no EDENOR)",tipoDAO.findbyId(6L)));
		temas.add(new TemaDTO(54L,"�rbol ca�do o por caer",tipoDAO.findbyId(6L)));
		temas.add(new TemaDTO(55L,"Aparato da�ado",tipoDAO.findbyId(6L)));
		temas.add(new TemaDTO(56L,"Sem�foro no funciona en cruce peligroso",tipoDAO.findbyId(6L)));
		temas.add(new TemaDTO(57L,"Poste de Edenor / Tel�fono chocado",tipoDAO.findbyId(6L)));
		
		/*********************
		 * CONTROL URBANO Y AMBIENTAL*
		 ********************/
		//V�a P�blica en General
		
		temas.add(new TemaDTO(58L,"Ocupaci�n Indebida del espacio p�blico",tipoDAO.findbyId(7L)));
		temas.add(new TemaDTO(59L,"Inspecci�n General",tipoDAO.findbyId(7L)));
		temas.add(new TemaDTO(60L,"Malos olores o gases t�xicos en la v�a p�blica",tipoDAO.findbyId(7L)));
		temas.add(new TemaDTO(61L,"OTROS",tipoDAO.findbyId(7L)));
		
		/*********************
		 * PROTECCION CIUDADANA*
		 ********************/
		//V�a P�blica en General
		
		temas.add(new TemaDTO(62L,"Desarmaderos de autos / Lugares de actividades Ilegales",tipoDAO.findbyId(8L)));
		temas.add(new TemaDTO(63L,"Estado de Carteles de Se�alizaci�n Vial",tipoDAO.findbyId(8L)));
		temas.add(new TemaDTO(64L,"Autos abandonados / Quemados",tipoDAO.findbyId(8L)));
		temas.add(new TemaDTO(65L,"D�rsena de parada de colectivos",tipoDAO.findbyId(8L)));
		temas.add(new TemaDTO(66L,"Carga y Descarga / Estacionamiento doble fila",tipoDAO.findbyId(8L)));
		temas.add(new TemaDTO(67L,"OTROS",tipoDAO.findbyId(8L)));
		
		// URGENCIA
		
		temas.add(new TemaDTO(68L,"Escape de Gas",tipoDAO.findbyId(9L)));
		temas.add(new TemaDTO(69L,"Cables de Edenor cortados",tipoDAO.findbyId(9L)));
		temas.add(new TemaDTO(70L,"Accidente de tr�nsito",tipoDAO.findbyId(9L)));
		temas.add(new TemaDTO(71L,"Emergencia m�dica",tipoDAO.findbyId(9L)));
		
		/*********************
		 * INERSI�N P�BLICA*
		 ********************/
		// Obras
		
		temas.add(new TemaDTO(72L,"Se�alamiento y/o balizamiento de obras en v�a p�blica",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(73L,"Calles nuevas de Asfalto Vecinal",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(74L,"Calles nuevas de asfalto de hormig�n",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(75L,"Limpieza de costas y /o residuos flotando en arroyos",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(76L,"Entubamientos",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(77L,"Desag�es",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(78L,"Obras de Edenor",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(79L,"Obras de Gas Natural",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(80L,"Obras de video cable",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(91L,"Obras de telefon�a",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(92L,"Antenas de telefon�a",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(93L,"Obras de Agua Corriente y/o cloacas",tipoDAO.findbyId(10L)));
		temas.add(new TemaDTO(94L,"OTROS",tipoDAO.findbyId(10L)));
		
		TemaDAO temaDAO = new TemaDAO(context);
		temaDAO.massiveCreate(temas);
		
	}
}
