/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;


import ec.sirec.ejb.entidades.AdicionalesDeductivos;
import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialValoracion;
import ec.sirec.ejb.entidades.CpValoracionExtras;
import ec.sirec.ejb.entidades.PredioArchivo;
import ec.sirec.ejb.entidades.SegUsuario;
import ec.sirec.ejb.servicios.AdicionalesDeductivosServicio;
import ec.sirec.ejb.servicios.CatalogoDetalleServicio;
import ec.sirec.ejb.servicios.CatastroPredialValoracionServicio;
import ec.sirec.ejb.servicios.CpValoracionExtrasServicio;
import ec.sirec.ejb.servicios.PredioArchivoServicio;
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
import javax.servlet.http.HttpServletResponse;
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
     
     private PredioArchivo predioArchivo;
     private CatastroPredial catastroPredialActual;
     private CatastroPredialValoracion catastroPredialValoracionActual;
     private SegUsuario usuarioActual;
     private AdicionalesDeductivos adicionalesDeductivosActual;
     private CpValoracionExtras cpValoracionExtrasActual;
     private StreamedContent archivo;
     
    
    // SERVICIOS
     
    @EJB
    private AdicionalesDeductivosServicio adicionalesDeductivosServicio;     
    @EJB
    private PredioArchivoServicio predioArchivoServicio;   
    //@EJB
    //private AdicionalesDeductivosServicio adicionalesDeductivosServicio;
    @EJB
    private CpValoracionExtrasServicio cpValoracionExtrasServicio;
    @EJB
    private CatastroPredialValoracionServicio catastroPredialValoracionServicio;
     

    @PostConstruct
    public void inicializar() {
        try {                        
            listarCatalogosDetalle();            
            obtenerUsuario();                      
             catastroPredialActual = new CatastroPredial();
             catastroPredialActual.setCatpreCodigo(1); 
             listaPredioArchivo =new ArrayList<PredioArchivo>();
             
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerUsuario(){
    usuarioActual = new SegUsuario (); 
     usuarioActual = (SegUsuario) getSession().getAttribute("usuario");           
           //System.out.println(usuarioActual.getUsuIdentificacion());         
    }

    public GestionImpuestoPredialControlador() {
    }

      public void listarCatalogosDetalle(){
       try {
           listaAdicionalesDeductivosRecargos = new ArrayList<AdicionalesDeductivos>();
           listaAdicionalesDeductivosRecargos = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("R");
           listaAdicionalesDeductivosExoneraciones = new ArrayList<AdicionalesDeductivos>();
           listaAdicionalesDeductivosExoneraciones = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("E");
           listaAdicionalesDeductivosDeducciones = new ArrayList<AdicionalesDeductivos>();
           listaAdicionalesDeductivosDeducciones = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("D");                                   
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
      }
    
       public void listarArchivos(){
       try {
           listaPredioArchivo = new ArrayList<PredioArchivo>();
           listaPredioArchivo = predioArchivoServicio.listarArchivos(usuarioActual);
           
           System.out.println("s:  "+listaPredioArchivo.size());
           
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
       
       public void handleFileUpload(FileUploadEvent event){
           
            try {
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
        
        
         } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
      
    public void startDownload(PredioArchivo archivo) {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance()
                .getExternalContext().getResponse();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + archivo.getPrearcNombre());
            response.getOutputStream().write(archivo.getPrearcData());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ioex) {
            LOGGER.log(Level.SEVERE, null, ioex);
        }
    }   
    
    
    public void guardarAdicionalesDeductivos(){        
     try {
                   
       adicionalesDeductivosActual = new AdicionalesDeductivos();               
       catastroPredialValoracionActual = new CatastroPredialValoracion();
          try {
      if(listaPredioArchivo.size()>0){
            
         for (int i = 0; i < listaAdicionalesDeductivosRecargosSeleccion.size(); i++) {
             cpValoracionExtrasActual = new CpValoracionExtras();
             adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosRecargosSeleccion.get(i)));             
             catastroPredialValoracionActual = catastroPredialValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);
             cpValoracionExtrasActual.setCatprevalCodigo(catastroPredialValoracionActual);
             cpValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
//            cpValoracionExtrasActual.setCpvalextBase(BigDecimal.ONE); 
//            cpValoracionExtrasActual.setCpvalextValor(BigDecimal.ZERO);             
             cpValoracionExtrasServicio.crearCpValoracionExtras(cpValoracionExtrasActual);
         }
         
         for (int i = 0; i < listaAdicionalesDeductivosExoneracionesSeleccion.size(); i++) {
             cpValoracionExtrasActual = new CpValoracionExtras();
             adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosExoneracionesSeleccion.get(i)));             
             catastroPredialValoracionActual = catastroPredialValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);
             cpValoracionExtrasActual.setCatprevalCodigo(catastroPredialValoracionActual);
             cpValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
//            cpValoracionExtrasActual.setCpvalextBase(BigDecimal.ONE); 
//            cpValoracionExtrasActual.setCpvalextValor(BigDecimal.ZERO);             
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
          
         
         }else{
          addSuccessMessage("No existen documentos cargados!");
          
//          FacesMessage msg = new FacesMessage("No se han cargado documentos!");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
      
      }
         } catch (NullPointerException exNull) {
           // LOGGER.log(Level.SEVERE, null, exNull);
             addSuccessMessage("No se han cargado documentos!");
//              FacesMessage msg = new FacesMessage("No se han cargado documentos!");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
         
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
    
    
}
