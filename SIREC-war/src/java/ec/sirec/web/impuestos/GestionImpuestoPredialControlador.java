/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.clases.EjecutarValoracion;
import ec.sirec.ejb.entidades.AdicionalesDeductivos;
import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialEdificacion;
import ec.sirec.ejb.entidades.CatastroPredialValoracion;
import ec.sirec.ejb.entidades.CpValoracionExtras;
import ec.sirec.ejb.entidades.DatoGlobal;
import ec.sirec.ejb.entidades.FittoCorvini;
import ec.sirec.ejb.entidades.PredioArchivo;
import ec.sirec.ejb.entidades.RecaudacionCab;
import ec.sirec.ejb.entidades.RecaudacionDet;
import ec.sirec.ejb.entidades.SegUsuario;
import ec.sirec.ejb.servicios.AdicionalesDeductivosServicio;
import ec.sirec.ejb.servicios.CatastroPredialServicio;
import ec.sirec.ejb.servicios.CatastroPredialValoracionServicio;
import ec.sirec.ejb.servicios.CpValoracionExtrasServicio;
import ec.sirec.ejb.servicios.DatoGlobalServicio;
import ec.sirec.ejb.servicios.FittoCorviniServicio;
import ec.sirec.ejb.servicios.PredioArchivoServicio;
import ec.sirec.ejb.servicios.RecaudacionCabServicio;
import ec.sirec.ejb.servicios.RecaudacionDetServicio;
import ec.sirec.web.base.BaseControlador;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author vespinoza
 */
@ManagedBean
@ViewScoped
public class GestionImpuestoPredialControlador extends BaseControlador {

    /**
     * Creates a new instance of GestionConceptoControlador
     */
    //LOGGER 
    private static final Logger LOGGER = Logger.getLogger(GestionImpuestoPredialControlador.class.getName());
    // VARIABLES Y ATRIBUTOS

    private List<AdicionalesDeductivos> listaAdicionalesDeductivosRecargos;
    private List<String> listaAdicionalesDeductivosRecargosSeleccion;
    private List<AdicionalesDeductivos> listaAdicionalesDeductivosExoneraciones;
    private List<String> listaAdicionalesDeductivosExoneracionesSeleccion;
    private List<AdicionalesDeductivos> listaAdicionalesDeductivosDeducciones;
    private List<String> listaAdicionalesDeductivosDeduccionesSeleccion;
    private List<PredioArchivo> listaPredioArchivo;
    private List<CatastroPredial> listaCatastroPredialTablaValoracion;
    private List<CatastroPredial> listaCatastroPredialExoRecarDeduc;
    private List<CatastroPredial> listaCatastroPredialClavesCatastrales;
    private List<EjecutarValoracion> listaEjecutarValoracion;
    
    private List<CatastroPredialEdificacion> listaCatastroPredialEdificacion1_1;
    private List<CatastroPredialEdificacion> listaCatastroPredialEdificacion1_2;
    private List<CatastroPredialEdificacion> listaCatastroPredialEdificacion1_3;
    private List<CatastroPredialEdificacion> listaCatastroPredialEdificacion2;
    private List<CatastroPredialEdificacion> listaCatastroPredialEdificacion3;
    private List<CatastroPredialEdificacion> listaCatastroPredialEdificacion4;
    private List<CatalogoDetalle> listaParroquias;
    private List<CatalogoDetalle> listaSectores;

    private PredioArchivo predioArchivo;
    private CatastroPredial catastroPredialActual;
    private CatastroPredialValoracion catastroPredialValoracionActual;
    private SegUsuario usuarioActual;
    private AdicionalesDeductivos adicionalesDeductivosActual;
    private CpValoracionExtras cpValoracionExtrasActual;
    private StreamedContent archivo;
    private String criterio;
    private BigDecimal totalTotal;
    private FittoCorvini fittoCorvini;
    private RecaudacionCab recaudacioCab;
    private RecaudacionDet recaudacionDet;
    private int anio;
    private CatalogoDetalle catalogoParroquia;
    private CatalogoDetalle catalogoSector;
   
    private EjecutarValoracion ejecutarValoracionAcual;

    // SERVICIOS
    @EJB
    private AdicionalesDeductivosServicio adicionalesDeductivosServicio;
    @EJB
    private PredioArchivoServicio predioArchivoServicio;
    @EJB
    private CatastroPredialServicio catastroPredialServicio;
    @EJB
    private CpValoracionExtrasServicio cpValoracionExtrasServicio;
    @EJB
    private CatastroPredialValoracionServicio catastroPredialValoracionServicio;
    @EJB
    private FittoCorviniServicio fittoCorviniServicio;
    @EJB
    private RecaudacionCabServicio recaudacionCabServicio;
    @EJB
    private RecaudacionDetServicio recaudacionDetServicio;
    @EJB
    private DatoGlobalServicio datoGlobalServicio;

    @PostConstruct
    public void inicializar() {
        try {
            listarCatalogosDetalle();
            obtenerUsuario();
            catastroPredialActual = new CatastroPredial();
            listaPredioArchivo = new ArrayList<PredioArchivo>();
            listarTodasComboClaves();
            listarCatastroPredialERD();
            criterio = "";
           
            listarParroquias();
            listarSectores();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarCatastroPredialERD() {
        try {
            listaCatastroPredialExoRecarDeduc = catastroPredialServicio.listarClaveCatastral();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarTodasComboClaves() {

        try {
            listaCatastroPredialClavesCatastrales = catastroPredialServicio.listarClaveCatastral();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void listarParroquias() {
        try {
            listaParroquias = catastroPredialServicio.listaCatParroquias();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    public void listarSectores() {
        try {
            listaSectores = catastroPredialServicio.listaCatSectores();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerUsuario() {
        usuarioActual = new SegUsuario();
        usuarioActual = (SegUsuario) getSession().getAttribute("usuario");
        //System.out.println(usuarioActual.getUsuIdentificacion());         
    }

    public GestionImpuestoPredialControlador() {
    }

    public void listarCatalogosDetalle() {
        try {
            listaAdicionalesDeductivosRecargos = new ArrayList<AdicionalesDeductivos>();
            listaAdicionalesDeductivosRecargos = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("R", "PA");
            listaAdicionalesDeductivosExoneraciones = new ArrayList<AdicionalesDeductivos>();
            listaAdicionalesDeductivosExoneraciones = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("E", "PA");
            listaAdicionalesDeductivosDeducciones = new ArrayList<AdicionalesDeductivos>();
            listaAdicionalesDeductivosDeducciones = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("D", "PA");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarArchivos() {
        try {            
            if (catastroPredialActual != null) {                
                listaPredioArchivo = new ArrayList<PredioArchivo>();
               // listaPredioArchivo = predioArchivoServicio.listarArchivos(usuarioActual);
               listaPredioArchivo = predioArchivoServicio.listarArchivosXImpuesto(catastroPredialActual, "PR");                                
            }else{
                listaPredioArchivo = new ArrayList<PredioArchivo>();
                addWarningMessage("Eliga la clave Catastral!"); 
            
            }                        
            System.out.println("s:  " + listaPredioArchivo.size());

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarArchivo(PredioArchivo archivo) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            predioArchivoServicio.eliminarPredioArchivo(archivo);
            context.addMessage(null, new FacesMessage("Mensaje:", "Se Elimino el Archivo  " + archivo.getPrearcNombre()));
            listarArchivos();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {

        try {

            if (catastroPredialActual.getCatpreCodigo() != null) {
                predioArchivo = new PredioArchivo();
                predioArchivo.setPrearcNombre(event.getFile().getFileName());
                predioArchivo.setCatpreCodigo(catastroPredialActual);
                predioArchivo.setPrearcData(event.getFile().getContents());
                predioArchivo.setPrearcTipo("PR");
                predioArchivo.setUsuIdentificacion(usuarioActual);
                predioArchivo.setUltaccDetalle("");
                predioArchivo.setUltaccMarcatiempo(new Date());

                predioArchivoServicio.crearPredioArchivo(predioArchivo);

                FacesMessage msg = new FacesMessage("El documento ", event.getFile().getFileName() + " ha sido cargado satisfactoriamente.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

                listarArchivos();
            } else {
                addErrorMessage("Seleccione Clave Catastral!!!");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //Preparamos archivo para descarga
    
       public void descargarArchivo(PredioArchivo patArchivoActual) {
        predioArchivo  = patArchivoActual;        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("datoArchivo", patArchivoActual.getPrearcData());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreArchivo", patArchivoActual.getPrearcNombre());
    }
     
     //Se descarga archivo por medio de  Servlet

    public String download() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=\"" + "c://subido" + predioArchivo.getPrearcNombre() + "\"");
        try {
            ServletOutputStream os = response.getOutputStream();
            os.write(predioArchivo.getPrearcData());
            os.flush();
            os.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }
    
//    public void startDownload(PredioArchivo archivo) {
//        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
//                .getExternalContext().getResponse();
//        try {
//            response.setContentType("application/pdf");
//            response.setHeader("Content-Disposition", "attachment;filename=" + archivo.getPrearcNombre());
//            response.getOutputStream().write(archivo.getPrearcData());
//            response.getOutputStream().flush();
//            response.getOutputStream().close();
//            FacesContext.getCurrentInstance().responseComplete();
//        } catch (IOException ioex) {
//            LOGGER.log(Level.SEVERE, null, ioex);
//        }
//    }
    
     public void valoracionConstruccion() {
         try {                           
          catastroPredialActual = catastroPredialServicio.cargarObjetoCatastro(catastroPredialActual.getCatpreCodigo());   
             
          listaCatastroPredialEdificacion1_1 = new ArrayList<CatastroPredialEdificacion>();                      
          listaCatastroPredialEdificacion1_1 = catastroPredialServicio.listarEdificacionesGrupo1_1(catastroPredialActual,"1","1");
           
          List <Integer>  vidaUtil = new ArrayList<Integer>();
          
             for (int i = 0; i < listaCatastroPredialEdificacion1_1.size(); i++) {
                 CatastroPredialEdificacion CPEdif = listaCatastroPredialEdificacion1_1.get(i);
                 try {
                   //  System.out.println("valor: " + CPEdif.getCatdetCodigo().getCatdetValor());
                     vidaUtil.add(CPEdif.getCatdetCodigo().getCatdetValor());
                 } catch (NullPointerException nex) {
                     vidaUtil.add(0);
                     // LOGGER.log(Level.SEVERE, null, nex);
                 }
             }
          
          listaCatastroPredialEdificacion1_2 = new ArrayList<CatastroPredialEdificacion>();                      
          listaCatastroPredialEdificacion1_2 = catastroPredialServicio.listarEdificacionesGrupo1_SubGrupo2(catastroPredialActual);           
          List <Integer> Edad = new ArrayList<Integer>();  
          for (int i = 0; i < listaCatastroPredialEdificacion1_2.size(); i++) {
                 CatastroPredialEdificacion CPEdif = listaCatastroPredialEdificacion1_2.get(i);
                 try {
                    // System.out.println("valor: " + CPEdif.getCatdetCodigo().getCatdetValor());
                     Edad.add(CPEdif.getCatpreediValor());
                 } catch (NullPointerException nex) {
                     Edad.add(0);
                     // LOGGER.log(Level.SEVERE, null, nex);
                 }
          }
          
          listaCatastroPredialEdificacion1_3 = new ArrayList<CatastroPredialEdificacion>();                      
          listaCatastroPredialEdificacion1_3 = catastroPredialServicio.listarEdificacionesGrupo1_SubGrupo3(catastroPredialActual);           
          List <String> estadoCons = new ArrayList<String>();  
          for (int i = 0; i < listaCatastroPredialEdificacion1_3.size(); i++) {
                 CatastroPredialEdificacion CPEdif = listaCatastroPredialEdificacion1_3.get(i);
                 try {
                    // System.out.println("valor: " + CPEdif.getCatdetCodigo().getCatdetValor());
                     estadoCons.add(CPEdif.getCatdetCodigo().getCatdetTexto());
                 } catch (NullPointerException nex) {
                     estadoCons.add("");
                     // LOGGER.log(Level.SEVERE, null, nex);
                 }
             }

            // List<Integer> FactorDepreciacion = new ArrayList<Integer>();
             List<Double> FD = new ArrayList<Double>();
             for (int i = 0; i < vidaUtil.size(); i++) {
                 int factoresInt = (int) Math.round(((double) Edad.get(i) / (double) vidaUtil.get(i)) * 100);                
                // System.out.println("factoresInt: "+factoresInt);                 
                 fittoCorvini = new FittoCorvini();
                 fittoCorvini = fittoCorviniServicio.obtenerValoresClase(factoresInt);

                 if (estadoCons.get(i).equals("ESTABLE")) {
                     FD.add(fittoCorvini.getClase1()/100);
                 } else {
                     if (estadoCons.get(i).equals("A REPARAR")) {
                         FD.add(fittoCorvini.getClase3()/100);
                     } else {
                         if (estadoCons.get(i).equals("OBSOLETO")) {
                             FD.add(fittoCorvini.getClase5()/100);
                         }
                     }
                 }
             }
          
          for (int i = 0; i < FD.size(); i++) {   
              System.out.println(" DF: "+ FD.get(i));                                
          }                              
          
          // GRUPO 2
          listaCatastroPredialEdificacion2 = new ArrayList<CatastroPredialEdificacion>();                      
          listaCatastroPredialEdificacion2 = catastroPredialServicio.listarEdificacionesGrupo2(catastroPredialActual);          
          List <Integer> Grupo2 = new ArrayList<Integer>();
          List <Integer> Grupo2AUX = new ArrayList<Integer>();
          int inicio2 = 0;
          int fin2 = vidaUtil.size();
          for (int i = 0; i < listaCatastroPredialEdificacion2.size(); i++) {
                 CatastroPredialEdificacion CPEdif = listaCatastroPredialEdificacion2.get(i);                 
                 if(inicio2==fin2){
                     inicio2=0;
                 }                 
                 if(inicio2<fin2){                     
                     if(i<fin2){
                          Grupo2.add(inicio2, CPEdif.getCatdetCodigo().getCatdetValor()); 
                          Grupo2AUX.add(inicio2, CPEdif.getCatdetCodigo().getCatdetValor());                           
                          // System.out.println("Grupo2.get(inicio2): "+ Grupo2.get(inicio2));                          
                     }else{                                                                                                    
                         Grupo2.set(inicio2, CPEdif.getCatdetCodigo().getCatdetValor() + Grupo2AUX.get(inicio2));                          
                         //System.out.println(Grupo2.get(inicio2)+" = "+ CPEdif.getCatdetCodigo().getCatdetValor() +" + "+ Grupo2AUX.get(inicio2));                             
                         Grupo2AUX.set(inicio2, Grupo2.get(inicio2));                                                                           
                         //System.out.println("total: "+ Grupo2AUX.get(inicio2));                         
                     }                     
                     inicio2++;
                 }                
          }
          // GRUPO 3          
          listaCatastroPredialEdificacion3 = new ArrayList<CatastroPredialEdificacion>();                      
          listaCatastroPredialEdificacion3 = catastroPredialServicio.listarEdificacionesGrupo3(catastroPredialActual);          
          List <Integer> Grupo3 = new ArrayList<Integer>();
          List <Integer> Grupo3AUX = new ArrayList<Integer>();
          int inicio3 = 0;
          int fin3 = vidaUtil.size();
          for (int i = 0; i < listaCatastroPredialEdificacion3.size(); i++) {
                 CatastroPredialEdificacion CPEdif = listaCatastroPredialEdificacion3.get(i);                 
                 if(inicio3==fin3){
                     inicio3=0;
                 }                 
                 if(inicio3<fin3){                     
                     if(i<fin3){
                          Grupo3.add(inicio3, CPEdif.getCatdetCodigo().getCatdetValor()); 
                          Grupo3AUX.add(inicio3, CPEdif.getCatdetCodigo().getCatdetValor());                                                                           
                     }else{                                                                                                    
                         Grupo3.set(inicio3, CPEdif.getCatdetCodigo().getCatdetValor() + Grupo3AUX.get(inicio3));                                               
                         Grupo3AUX.set(inicio3, Grupo3.get(inicio3));                                                                                                             
                     }                     
                     inicio3++;
                 }                
          }
          
          listaCatastroPredialEdificacion4 = new ArrayList<CatastroPredialEdificacion>();                      
          listaCatastroPredialEdificacion4 = catastroPredialServicio.listarEdificacionesGrupo4(catastroPredialActual);          
          List <Integer> Grupo4 = new ArrayList<Integer>();
          List <Integer> Grupo4AUX = new ArrayList<Integer>();
          int inicio4 = 0;
          int fin4 = vidaUtil.size();
          for (int i = 0; i < listaCatastroPredialEdificacion4.size(); i++) {
                 CatastroPredialEdificacion CPEdif = listaCatastroPredialEdificacion4.get(i);                 
                 if(inicio4==fin4){
                     inicio4=0;
                 }                 
                 if(inicio4<fin4){                     
                     if (i < fin4) {
                         try {
                             Grupo4.add(inicio4, CPEdif.getCatdetCodigo().getCatdetValor());
                             Grupo4AUX.add(inicio4, CPEdif.getCatdetCodigo().getCatdetValor());
                         } catch (NullPointerException ex) {
                             Grupo4.add(inicio4, 0);
                             Grupo4AUX.add(inicio4, 0);
                         }
                     }else{
                         try{
                         Grupo4.set(inicio4, CPEdif.getCatdetCodigo().getCatdetValor() + Grupo4AUX.get(inicio4));                                               
                         Grupo4AUX.set(inicio4, Grupo4.get(inicio4));  
                         } catch (NullPointerException ex) {
                             Grupo4.set(inicio4, 0 + Grupo4AUX.get(inicio4));                                               
                             Grupo4AUX.set(inicio4, Grupo4.get(inicio4));  
                         }
                     }                     
                     inicio4++;
                 }                
          }
          
          List <Integer> VN = new ArrayList<Integer>();                                         
          for (int i = 0; i < Grupo2.size(); i++) {   
              VN.add(Grupo2.get(i)+Grupo3.get(i)+Grupo4.get(i));                                   
          }
          
          BigDecimal valorAvaluoConstruccion = BigDecimal.ZERO;
          BigDecimal valorAvaluoTerrero = new BigDecimal(1000);
          
          for (int i = 0; i < vidaUtil.size(); i++) {                
             double d = (VN.get(i)-(VN.get(i)*FD.get(i)))*catastroPredialActual.getCatpreAreaTotalCons();   
//             System.out.println(" "+ VN.get(i)+" - "+VN.get(i) +" * "+ FD.get(i)+") *"+catastroPredialActual.getCatpreAreaTotalCons());           
//             System.out.println("d: "+ d); 
          valorAvaluoConstruccion = valorAvaluoConstruccion.add(new BigDecimal((VN.get(i)-(VN.get(i)*FD.get(i)))*catastroPredialActual.getCatpreAreaTotalCons())) ;                                                    
          }
          
          catastroPredialValoracionActual = new CatastroPredialValoracion();          
          catastroPredialValoracionActual.setCatpreCodigo(catastroPredialActual);
          catastroPredialValoracionActual.setCatprevalAvaluoEdif(valorAvaluoConstruccion); 
          catastroPredialValoracionActual.setCatprevalAvaluoTerr(valorAvaluoTerrero); 
          catastroPredialValoracionActual.setCatprevalAvaluoTot(valorAvaluoConstruccion.add(valorAvaluoTerrero)); 
          catastroPredialValoracionActual.setCatprevalValorPropieda(valorAvaluoConstruccion.add(valorAvaluoTerrero));           
          catastroPredialValoracionActual.setCatprevalBaseImponible(valorAvaluoConstruccion.add(valorAvaluoTerrero));                            //                
          catastroPredialValoracionActual.setCatprevalImpuesto(valorAvaluoConstruccion.add(valorAvaluoTerrero).multiply(new BigDecimal(datoGlobalServicio.obtenerDatoGlobal("Banda_Impositiva_Urbana").getDatgloValor()).divide(new BigDecimal(100))));  
          catastroPredialValoracionActual.setCatprevalBomberos(valorAvaluoConstruccion.add(valorAvaluoTerrero).multiply(new BigDecimal(datoGlobalServicio.obtenerDatoGlobal("Bomberos").getDatgloValor()).divide(new BigDecimal(100))));  
          catastroPredialValoracionActual.setCatprevalSolarNoedificado(valorAvaluoConstruccion.add(valorAvaluoTerrero).multiply(new BigDecimal(datoGlobalServicio.obtenerDatoGlobal("Solar_No_Edif").getDatgloValor()).divide(new BigDecimal(100))));  
          catastroPredialValoracionActual.setCatprevalTasaAdm(new BigDecimal(datoGlobalServicio.obtenerDatoGlobal("Tasa_Administrativa").getDatgloValor()));  
          catastroPredialValoracionActual.setCatprevalAnio(anio);
          
          catastroPredialValoracionServicio.crearAplicacion(catastroPredialValoracionActual);
          
          System.out.println("valorConstruccionT: "+ valorAvaluoConstruccion);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarAdicionalesDeductivos() {
        try {

           valoracionConstruccion();
            
            adicionalesDeductivosActual = new AdicionalesDeductivos();
            catastroPredialValoracionActual = new CatastroPredialValoracion();

            if (catastroPredialActual.getCatpreCodigo() != null || catastroPredialActual != null) {

                try {
                    if (listaPredioArchivo.size() > 0) {

                        for (int i = 0; i < listaAdicionalesDeductivosRecargosSeleccion.size(); i++) {
                            cpValoracionExtrasActual = new CpValoracionExtras();
                            adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosRecargosSeleccion.get(i)));
                            catastroPredialValoracionActual = catastroPredialValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);
                            cpValoracionExtrasActual.setCatprevalCodigo(catastroPredialValoracionActual);
                            cpValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
                            cpValoracionExtrasServicio.crearCpValoracionExtras(cpValoracionExtrasActual);
                        }

                        for (int i = 0; i < listaAdicionalesDeductivosExoneracionesSeleccion.size(); i++) {
                            cpValoracionExtrasActual = new CpValoracionExtras();
                            adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosExoneracionesSeleccion.get(i)));
                            catastroPredialValoracionActual = catastroPredialValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);
                            cpValoracionExtrasActual.setCatprevalCodigo(catastroPredialValoracionActual);
                            cpValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
                            cpValoracionExtrasServicio.crearCpValoracionExtras(cpValoracionExtrasActual);
                        }

                        for (int i = 0; i < listaAdicionalesDeductivosDeduccionesSeleccion.size(); i++) {
                            cpValoracionExtrasActual = new CpValoracionExtras();
                            adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosDeduccionesSeleccion.get(i)));
                            catastroPredialValoracionActual = catastroPredialValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);
                            cpValoracionExtrasActual.setCatprevalCodigo(catastroPredialValoracionActual);
                            cpValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
//            cpValoracionExtrasActual.setCpvalextBase(BigDecimal.ONE); 
//            cpValoracionExtrasActual.setCpvalextValor(BigDecimal.ZERO);             
                            cpValoracionExtrasServicio.crearCpValoracionExtras(cpValoracionExtrasActual);
                        }

                        addSuccessMessage("Guardado Exitosamente!");
                    } else {
                        addSuccessMessage("No existen documentos cargados!");
//          FacesMessage msg = new FacesMessage("No se han cargado documentos!");
//        FacesContext.getCurrentInstance().addMessage(null, msg);      
                    }
                } catch (NullPointerException exNull) {
                    // LOGGER.log(Level.SEVERE, null, exNull);
                    addSuccessMessage("No existen documentos cargados!");
//              FacesMessage msg = new FacesMessage("No se han cargado documentos!");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                addErrorMessage("Seleccione Clave Catastral o no existen catastro predial Valoracion!");
            }
        } catch (Exception ex) {
            addErrorMessage("Seleccione los campos");
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void buscarClaveCatastral() {
        try {
            catastroPredialActual = catastroPredialServicio.cargarObjetoCatastro(catastroPredialActual.getCatpreCodigo());
        } catch (Exception ex) {
            catastroPredialActual = new CatastroPredial();
            // System.out.println("alma de hombre");            
            //LOGGER.log(Level.SEVERE, null, ex);         
        }
    }

    

    public void ejecutarValoracion() {
        try {
            totalTotal = new BigDecimal(0);
            listaEjecutarValoracion = new ArrayList<EjecutarValoracion>();
            if (criterio.equals("C")) {
                listaCatastroPredialTablaValoracion = catastroPredialServicio.listarCatastroXCodigo(catastroPredialActual.getCatpreCodigo());
            } else {
                if (criterio.equals("T")) {
                    listaCatastroPredialTablaValoracion = catastroPredialServicio.listarClaveCatastral();
                }else{
                    if (criterio.equals("P")) {                                                                        
                    listaCatastroPredialTablaValoracion = catastroPredialServicio.listarCatastroXParroquia(catalogoParroquia); 
                }else{
                        if (criterio.equals("S")) {                                                                        
                    listaCatastroPredialTablaValoracion = catastroPredialServicio.listarCatastroXSector(catalogoSector); 
                }
                                        
                    }
                }
            }

            for (int i = 0; i < listaCatastroPredialTablaValoracion.size(); i++) {
                EjecutarValoracion eVal = new EjecutarValoracion();
                CatastroPredial CP = listaCatastroPredialTablaValoracion.get(i);
                
                eVal.setCatastroPredial(CP);
                eVal.setCatpreCodigo(CP.getCatpreCodigo());
                eVal.setCatpreClaveCatastal(CP.getCatpreCodNacional() + CP.getCatpreCodLocal());
                eVal.setProCi(catastroPredialServicio.obtenerPropietarioPrincipalPredio(CP.getCatpreCodigo()));
                                               
                if (CP.getCatpreAreaTotal() != null) {
                    eVal.setCatpreAreaTotal(CP.getCatpreAreaTotal());
                }else{
                     eVal.setCatpreAreaTotal(0.0);
                }
                if (CP.getCatpreAreaTotalCons() != null) {
                    eVal.setCatpreAreaTotalCons(CP.getCatpreAreaTotalCons());
                }else{
                     eVal.setCatpreAreaTotalCons(0.0);
                }
                
                CatastroPredialValoracion CPV = catastroPredialValoracionServicio.buscarPorCatastroPredial(CP);                
                if(CPV!=null){                  
                    if (CPV.getCatprevalAvaluoEdif() == null) {
                         CPV.setCatprevalAvaluoEdif(BigDecimal.ZERO);
                    }
                    if (CPV.getCatprevalAvaluoTerr() == null) {
                         CPV.setCatprevalAvaluoTerr(BigDecimal.ZERO);
                    } 
                    if (CPV.getCatprevalValorPropieda() == null) {
                         CPV.setCatprevalValorPropieda(BigDecimal.ZERO);
                    } 
                    if (CPV.getCatprevalBaseImponible() == null) {
                         CPV.setCatprevalBaseImponible(BigDecimal.ZERO);
                    } 
                    if (CPV.getCatprevalImpuesto() == null) {
                         CPV.setCatprevalImpuesto(BigDecimal.ZERO);
                    } 
                    if (CPV.getCatprevalBomberos() == null) {
                         CPV.setCatprevalBomberos(BigDecimal.ZERO);
                    } 
                    if (CPV.getCatprevalSolarNoedificado() == null) {
                         CPV.setCatprevalSolarNoedificado(BigDecimal.ZERO);
                    } 
                    if (CPV.getCatprevalTasaAdm() == null) {
                         CPV.setCatprevalTasaAdm(BigDecimal.ZERO);
                    }                                                                                             
                eVal.setCatastroPredialValoracion(CPV);    
                }else{
                      CPV = new CatastroPredialValoracion();
                      CPV.setCatprevalAvaluoEdif(BigDecimal.ZERO);
                      CPV.setCatprevalAvaluoTerr(BigDecimal.ZERO);
                      CPV.setCatprevalValorPropieda(BigDecimal.ZERO);
                      CPV.setCatprevalBaseImponible(BigDecimal.ZERO);
                      CPV.setCatprevalImpuesto(BigDecimal.ZERO);
                      CPV.setCatprevalBomberos(BigDecimal.ZERO);
                      CPV.setCatprevalSolarNoedificado(BigDecimal.ZERO);
                      CPV.setCatprevalTasaAdm(BigDecimal.ZERO);                                            
                      eVal.setCatastroPredialValoracion(CPV);                
                }

                if (adicionalesDeductivosServicio.obteneValorXAdicional(CP.getCatpreCodigo(), "PA", "R") != null) {
                    eVal.setTotalRecargos(adicionalesDeductivosServicio.obteneValorXAdicional(CP.getCatpreCodigo(), "PA", "R"));
                } else {
                    eVal.setTotalRecargos(BigDecimal.ZERO);
                }
                if (adicionalesDeductivosServicio.obteneValorXAdicional(CP.getCatpreCodigo(), "PA", "D") != null) {
                    eVal.setTotalDeduciones(adicionalesDeductivosServicio.obteneValorXAdicional(CP.getCatpreCodigo(), "PA", "D"));
                } else {
                    eVal.setTotalDeduciones(BigDecimal.ZERO);
                }
                if (adicionalesDeductivosServicio.obteneValorXAdicional(CP.getCatpreCodigo(), "PA", "E") != null) {
                    eVal.setTotalExoneracion(adicionalesDeductivosServicio.obteneValorXAdicional(CP.getCatpreCodigo(), "PA", "E"));
                } else {
                    eVal.setTotalExoneracion(BigDecimal.ZERO);
                }
                
               // eVal.setTotalRegistro(eVal.getTotalRecargos().add(eVal.getTotalDeduciones().add(eVal.getTotalExoneracion().add(new BigDecimal(eVal.getCatpreAreaTotal()).add(new BigDecimal(eVal.getCatpreAreaTotalCons())).add(eVal.getCatastroPredialValoracion().getCatprevalAvaluoTerr()).add(eVal.getCatastroPredialValoracion().getCatprevalAvaluoEdif())))));
                eVal.setTotalRegistro(new BigDecimal(eVal.getCatpreAreaTotal()).add(new BigDecimal(eVal.getCatpreAreaTotalCons()))
                        .add(eVal.getCatastroPredialValoracion().getCatprevalAvaluoTerr()).add(eVal.getCatastroPredialValoracion().getCatprevalAvaluoEdif())
                        .add(eVal.getCatastroPredialValoracion().getCatprevalValorPropieda()).add(eVal.getCatastroPredialValoracion().getCatprevalBaseImponible())
                        .add(eVal.getCatastroPredialValoracion().getCatprevalImpuesto()).add(eVal.getCatastroPredialValoracion().getCatprevalBomberos())
                        .add(eVal.getCatastroPredialValoracion().getCatprevalSolarNoedificado())
                        .add(eVal.getCatastroPredialValoracion().getCatprevalTasaAdm()).add(eVal.getTotalRecargos()).add(eVal.getTotalDeduciones())
                        .subtract(eVal.getTotalExoneracion()));   

                listaEjecutarValoracion.add(eVal);
                
                totalTotal = totalTotal.add(eVal.getTotalRegistro());
                
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void preEmisionValoracion() {
        try {

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void postProcessXLS(Object document) throws IOException {

        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet hoja = wb.getSheetAt(0);
        CellStyle style = wb.createCellStyle();
        style.setFillPattern(CellStyle.NO_FILL);
        org.apache.poi.ss.usermodel.Font font = wb.createFont();
        font.setFontName("Times Roman");
        font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        /**
         * ** ConfiguraciÃ³n del estilo de la celda header de la tabla. *****
         */
        CellStyle styleHeaderTable = wb.createCellStyle();
        styleHeaderTable.setFillPattern(CellStyle.NO_FILL);

        org.apache.poi.ss.usermodel.Font fontHeaderTable = wb.createFont();
        fontHeaderTable.setFontName("Times Roman");
        fontHeaderTable.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);
        fontHeaderTable.setColor(IndexedColors.BLACK.getIndex());
        styleHeaderTable.setFont(fontHeaderTable);
        Sheet sheet = wb.getSheetAt(0);
        sheet.autoSizeColumn((short) 0); //ajusta el ancho de la primera columna
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        for (int i = 0; i < 20; i++) {
            hoja.autoSizeColumn((short) i);
        }
    }

    public void emision() {
        try {
             for (int i = 0; i < listaEjecutarValoracion.size(); i++) {
                 recaudacioCab = new RecaudacionCab();
                 EjecutarValoracion eje = listaEjecutarValoracion.get(i);
                 recaudacioCab.setProCi(eje.getProCi()); 
                 recaudacioCab.setRecFecha(new Date()); 
                 recaudacioCab.setRecTotal(eje.getTotalRegistro());
                 recaudacioCab.setRecEstado("A");
                 recaudacioCab.setUsuIdentificacion(usuarioActual);                  
                 recaudacionCabServicio.crearRecaudacionCab(recaudacioCab);
                 
                 recaudacionDet = new RecaudacionDet();
                 recaudacionDet.setRecCodigo(recaudacioCab); 
                 recaudacionDet.setRecdetTipo("PR"); 
                 recaudacionDet.setRecdetReferencia("OK");
                 recaudacionDet.setRecdetValor(eje.getTotalRegistro());                  
                 recaudacionDetServicio.crearRecaudacionDet(recaudacionDet);
                 
                 addSuccessMessage("Emisión Realizada"); 
                 
             }
                                    
        } catch (Exception ex) {
                      
            //LOGGER.log(Level.SEVERE, null, ex);         
        }
    }
    
    // METODOS
    public List<AdicionalesDeductivos> getListaAdicionalesDeductivosRecargos() {
        return listaAdicionalesDeductivosRecargos;
    }

    public void setListaAdicionalesDeductivosRecargos(
            List<AdicionalesDeductivos> listaAdicionalesDeductivosRecargos) {
        this.listaAdicionalesDeductivosRecargos = listaAdicionalesDeductivosRecargos;
    }

    public List<String> getListaAdicionalesDeductivosRecargosSeleccion() {
        return listaAdicionalesDeductivosRecargosSeleccion;
    }

    public void setListaAdicionalesDeductivosRecargosSeleccion(List<String> listaAdicionalesDeductivosRecargosSeleccion) {
        this.listaAdicionalesDeductivosRecargosSeleccion = listaAdicionalesDeductivosRecargosSeleccion;
    }

    public List<AdicionalesDeductivos> getListaAdicionalesDeductivosExoneraciones() {
        return listaAdicionalesDeductivosExoneraciones;
    }

    public void setListaAdicionalesDeductivosExoneraciones(List<AdicionalesDeductivos> listaAdicionalesDeductivosExoneraciones) {
        this.listaAdicionalesDeductivosExoneraciones = listaAdicionalesDeductivosExoneraciones;
    }

    public List<String> getListaAdicionalesDeductivosExoneracionesSeleccion() {
        return listaAdicionalesDeductivosExoneracionesSeleccion;
    }

    public void setListaAdicionalesDeductivosExoneracionesSeleccion(List<String> listaAdicionalesDeductivosExoneracionesSeleccion) {
        this.listaAdicionalesDeductivosExoneracionesSeleccion = listaAdicionalesDeductivosExoneracionesSeleccion;
    }

    public List<AdicionalesDeductivos> getListaAdicionalesDeductivosDeducciones() {
        return listaAdicionalesDeductivosDeducciones;
    }

    public void setListaAdicionalesDeductivosDeducciones(List<AdicionalesDeductivos> listaAdicionalesDeductivosDeducciones) {
        this.listaAdicionalesDeductivosDeducciones = listaAdicionalesDeductivosDeducciones;
    }

    public List<String> getListaAdicionalesDeductivosDeduccionesSeleccion() {
        return listaAdicionalesDeductivosDeduccionesSeleccion;
    }

    public void setListaAdicionalesDeductivosDeduccionesSeleccion(List<String> listaAdicionalesDeductivosDeduccionesSeleccion) {
        this.listaAdicionalesDeductivosDeduccionesSeleccion = listaAdicionalesDeductivosDeduccionesSeleccion;
    }

    public List<PredioArchivo> getListaPredioArchivo() {
        return listaPredioArchivo;
    }

    public void setListaPredioArchivo(List<PredioArchivo> listaPredioArchivo) {
        this.listaPredioArchivo = listaPredioArchivo;
    }

    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public CatastroPredial getCatastroPredialActual() {
        return catastroPredialActual;
    }

    public void setCatastroPredialActual(CatastroPredial catastroPredialActual) {
        this.catastroPredialActual = catastroPredialActual;
    }

    public List<CatastroPredial> getListaCatastroPredialTablaValoracion() {
        return listaCatastroPredialTablaValoracion;
    }

    public void setListaCatastroPredialTablaValoracion(List<CatastroPredial> listaCatastroPredialTablaValoracion) {
        this.listaCatastroPredialTablaValoracion = listaCatastroPredialTablaValoracion;
    }

    public List<CatastroPredial> getListaCatastroPredialClavesCatastrales() {
        return listaCatastroPredialClavesCatastrales;
    }

    public void setListaCatastroPredialClavesCatastrales(List<CatastroPredial> listaCatastroPredialClavesCatastrales) {
        this.listaCatastroPredialClavesCatastrales = listaCatastroPredialClavesCatastrales;
    }

    public List<CatastroPredial> getListaCatastroPredialExoRecarDeduc() {
        return listaCatastroPredialExoRecarDeduc;
    }

    public void setListaCatastroPredialExoRecarDeduc(List<CatastroPredial> listaCatastroPredialExoRecarDeduc) {
        this.listaCatastroPredialExoRecarDeduc = listaCatastroPredialExoRecarDeduc;
    }

    public List<EjecutarValoracion> getListaEjecutarValoracion() {
        return listaEjecutarValoracion;
    }

    public void setListaEjecutarValoracion(List<EjecutarValoracion> listaEjecutarValoracion) {
        this.listaEjecutarValoracion = listaEjecutarValoracion;
    }

    public BigDecimal getTotalTotal() {
        return totalTotal;
    }

    public void setTotalTotal(BigDecimal TotalTotal) {
        this.totalTotal = TotalTotal;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<CatalogoDetalle> getListaParroquias() {
        return listaParroquias;
    }

    public void setListaParroquias(List<CatalogoDetalle> listaParroquias) {
        this.listaParroquias = listaParroquias;
    }

    public CatalogoDetalle getCatalogoParroquia() {
        return catalogoParroquia;
    }

    public void setCatalogoParroquia(CatalogoDetalle catalogoParroquia) {
        this.catalogoParroquia = catalogoParroquia;
    }

    public List<CatalogoDetalle> getListaSectores() {
        return listaSectores;
    }

    public void setListaSectores(List<CatalogoDetalle> listaSectores) {
        this.listaSectores = listaSectores;
    }

    public CatalogoDetalle getCatalogoSector() {
        return catalogoSector;
    }

    public void setCatalogoSector(CatalogoDetalle catalogoSector) {
        this.catalogoSector = catalogoSector;
    }
    
    
}