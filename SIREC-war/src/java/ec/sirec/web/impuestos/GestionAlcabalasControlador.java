/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.AdicionalesDeductivos;
import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialAlcabalaValoracion;
import ec.sirec.ejb.entidades.CatastroPredialPlusvaliaValoracion;
import ec.sirec.ejb.entidades.CatastroPredialValoracion;
import ec.sirec.ejb.entidades.CpAlcabalaValoracionExtras;
import ec.sirec.ejb.entidades.PredioArchivo;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.entidades.SegUsuario;
import ec.sirec.ejb.servicios.AdicionalesDeductivosServicio;
import ec.sirec.ejb.servicios.CatalogoDetalleServicio;
import ec.sirec.ejb.servicios.CatastroPredialAlcabalaValoracionServicio;
import ec.sirec.ejb.servicios.CatastroPredialServicio;
import ec.sirec.ejb.servicios.CatastroPredialValoracionServicio;
import ec.sirec.ejb.servicios.CpAlcabalaValoracionExtrasServicio;
import ec.sirec.ejb.servicios.CpValoracionExtrasServicio;
import ec.sirec.ejb.servicios.PredioArchivoServicio;
import ec.sirec.web.base.BaseControlador;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author vespinoza
 */
@ManagedBean
@ViewScoped
public class GestionAlcabalasControlador extends BaseControlador {

    /**
     * Creates a new instance of GestionConceptoControlador
     */
    //LOGGER 
    private static final Logger LOGGER = Logger.getLogger(GestionAlcabalas.class.getName());
    // VARIABLES Y ATRIBUTOS

    private SegUsuario usuarioActual;
    private CatastroPredial catastroPredialActual;
    private CatastroPredialAlcabalaValoracion catastroPredialAlcabalaValoracion;
    private CatalogoDetalle catalogoDetalleConcepto;

    private List<CatastroPredial> listaCatastroPredial;
    private List<CatalogoDetalle> listaCatalogoDetalleConcepto;
    private CatastroPredialValoracion catastroPredialValoracionActual;

    private List<AdicionalesDeductivos> listaAdicionalesDeductivosDeducciones;
    private List<String> listaAdicionalesDeductivosDeduccionesSeleccion;
    private List<AdicionalesDeductivos> listaAdicionalesDeductivosExcenciones;
    private List<String> listaAdicionalesDeductivosExcencionesSeleccion;

    private AdicionalesDeductivos adicionalesDeductivosActual;
    private CpAlcabalaValoracionExtras cpAlcabalaValoracionExtrasActual;
    private StreamedContent archivo;
    private Propietario propietario;
    private List<PredioArchivo> listaAlcabalasArchivo;
    private PredioArchivo predioArchivo;

    /// ATRIBUTOS PLUSVALIA
    
    private List<CatalogoDetalle> listaTipoDeTarifa;
    private CatastroPredialPlusvaliaValoracion catastroPredialPlusvaliaValoracion;
    
    // SERVICIOS ALCABALA
    @EJB
    private CatastroPredialServicio catastroPredialServicio;
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private CatastroPredialValoracionServicio catastroPredialValoracionServicio;
    @EJB
    private CatastroPredialAlcabalaValoracionServicio catastroPredialAlcabalaValoracionServicio;
    @EJB
    private CpAlcabalaValoracionExtrasServicio cpAlcabalaValoracionExtrasServicio;
    @EJB
    private AdicionalesDeductivosServicio adicionalesDeductivosServicio;
    @EJB
    private PredioArchivoServicio predioArchivoServicio;
    
    /// SERVICIOS PLUSVALIA
    
    
    

    @PostConstruct
    public void inicializar() {
        try {
            catastroPredialAlcabalaValoracion = new CatastroPredialAlcabalaValoracion();
            catastroPredialActual = new CatastroPredial();
            catalogoDetalleConcepto = new CatalogoDetalle();
            listaAlcabalasArchivo = new ArrayList<PredioArchivo>();
            predioArchivo = new PredioArchivo();
            listarCatastroPredial();
            obtenerUsuario();
            listarConceptos();
            listarCatalogosDetalle();
            
            // INICIALIZAR PLUSVALIA
            catastroPredialPlusvaliaValoracion = new CatastroPredialPlusvaliaValoracion();
            
            listarTipoTarifa();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerUsuario() {
        usuarioActual = new SegUsuario();
        usuarioActual = (SegUsuario) getSession().getAttribute("usuario");
        //System.out.println(usuarioActual.getUsuIdentificacion());         
    }

    public GestionAlcabalasControlador() {
    }

    public void listarCatastroPredial() {
        try {
            listaCatastroPredial = new ArrayList<CatastroPredial>();
            listaCatastroPredial = catastroPredialServicio.listarClaveCatastral();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarConceptos() {
        try {

            listaCatalogoDetalleConcepto = new ArrayList<CatalogoDetalle>();
            listaCatalogoDetalleConcepto = catalogoDetalleServicio.listarPorNemonicoCatalogo("CONCEPTOS");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerCamposCatPredial() {
        try {

            catastroPredialActual = catastroPredialServicio.cargarObjetoCatastro(catastroPredialActual.getCatpreCodigo());
            catastroPredialActual.setCatpreAreaTotal(catastroPredialActual.getCatpreAreaTotalEsc() + catastroPredialActual.getCatpreAreaTotalCons());

            propietario = new Propietario();
            propietario = catastroPredialServicio.obtenerPropietarioPrincipalPredio(catastroPredialActual.getCatpreCodigo());
            System.out.println("s: " + propietario.getProCi());
            System.out.println("m: " + propietario.getProNombres());

            catastroPredialValoracionActual = new CatastroPredialValoracion();
            catastroPredialValoracionActual = catastroPredialValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public BigDecimal valorMayorBaseImponible() {
        BigDecimal baseImponible = new BigDecimal(BigInteger.ONE);
        try {
            if (catastroPredialValoracionActual.getCatprevalAvaluoTot().compareTo(catastroPredialAlcabalaValoracion.getCatprealcvalPrecioventa()) == -1) {
                baseImponible = catastroPredialAlcabalaValoracion.getCatprealcvalPrecioventa();
            } else {
                baseImponible = catastroPredialValoracionActual.getCatprevalAvaluoTot();
            }
        catastroPredialPlusvaliaValoracion.setCatprepluvalPrecioventa(baseImponible); 
        
            System.out.println("");
            
      } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return baseImponible;
    }
    
    public void calcularDeterminacion() {
        try {

            BigDecimal baseImponible = valorMayorBaseImponible();
//            if (catastroPredialValoracionActual.getCatprevalAvaluoTerr().compareTo(catastroPredialAlcabalaValoracion.getCatprealcvalPrecioventa()) == -1) {
//                baseImponible = catastroPredialAlcabalaValoracion.getCatprealcvalPrecioventa();
//            } else {
//                baseImponible = catastroPredialValoracionActual.getCatprevalAvaluoTerr();
//            }
            BigDecimal impuesto = baseImponible.multiply(new BigDecimal(1)).divide(new BigDecimal(100));
            BigDecimal conProv = baseImponible.multiply(new BigDecimal(0.01)).divide(new BigDecimal(100));
           //conProv = conProv.setScale(2, RoundingMode.CEILING);

            catastroPredialAlcabalaValoracion.setCatprealcvalBaseimp(baseImponible);
            catastroPredialAlcabalaValoracion.setCatprealcvalImpuesto(impuesto);
            catastroPredialAlcabalaValoracion.setCatprealcvalConsejoProv(conProv);
            catastroPredialAlcabalaValoracion.setCatprealcvalTasaProc(new BigDecimal(2));
            catastroPredialAlcabalaValoracion.setCatprealcvalTotal(impuesto.add(conProv).add(catastroPredialAlcabalaValoracion.getCatprealcvalTasaProc()).setScale(2, RoundingMode.CEILING));

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarAlcabala() {
        try {

            catastroPredialAlcabalaValoracion.setCatpreCodigo(catastroPredialActual);
            catastroPredialAlcabalaValoracionServicio.crearCatastroPredialAlcabalaValoracion(catastroPredialAlcabalaValoracion);
            addSuccessMessage("Guardado Exitosamente!");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarCatalogosDetalle() {
        try {
            listaAdicionalesDeductivosDeducciones = new ArrayList<AdicionalesDeductivos>();
            listaAdicionalesDeductivosDeducciones = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("D", "AL");
            listaAdicionalesDeductivosExcenciones = new ArrayList<AdicionalesDeductivos>();
            listaAdicionalesDeductivosExcenciones = adicionalesDeductivosServicio.listarAdicionesDeductivosTipo("E", "AL");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarArchivos() {
        try {
            if (catastroPredialActual != null) {
                listaAlcabalasArchivo = new ArrayList<PredioArchivo>();
                listaAlcabalasArchivo = predioArchivoServicio.listarArchivosXImpuesto(catastroPredialActual, "AL");
            } else {
                listaAlcabalasArchivo = new ArrayList<PredioArchivo>();
                addWarningMessage("Eliga la clave Catastral!");

            }
        } catch (Exception ex) {
            addWarningMessage("Eliga la clave Catastral!");
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
            predioArchivo = new PredioArchivo();
            predioArchivo.setPrearcNombre(event.getFile().getFileName());
            predioArchivo.setCatpreCodigo(catastroPredialActual);
            predioArchivo.setPrearcData(event.getFile().getContents());
            predioArchivo.setPrearcTipo("AL");
            predioArchivo.setUsuIdentificacion(usuarioActual);
            predioArchivo.setUltaccDetalle("Documento justificativo de la deducción o exención - Alcabala");
            predioArchivo.setUltaccMarcatiempo(new Date());

            predioArchivoServicio.crearPredioArchivo(predioArchivo);

            FacesMessage msg = new FacesMessage("El documento ", event.getFile().getFileName() + " ha sido cargado satisfactoriamente.");
            FacesContext.getCurrentInstance().addMessage(null, msg);

            listarArchivos();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
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
//            System.out.println("kkkkkkkkkjdjdjdjd");
//            LOGGER.log(Level.SEVERE, null, ioex);            
//        }
//    }   
    public void descargarArchivo(PredioArchivo patArchivoActual) {
        predioArchivo = patArchivoActual;
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

    public void guardarDeduccionesExcenciones() {
        try {

            adicionalesDeductivosActual = new AdicionalesDeductivos();
            //catastroPredialValoracionActual = new CatastroPredialValoracion();

            if (catastroPredialActual != null) {
                try {
                    if (listaAlcabalasArchivo.size() > 0) {

                        catastroPredialAlcabalaValoracion = catastroPredialAlcabalaValoracionServicio.buscarPorCatastroPredial(catastroPredialActual);
                        if (catastroPredialAlcabalaValoracion != null) {

                            for (int i = 0; i < listaAdicionalesDeductivosDeduccionesSeleccion.size(); i++) {
                                cpAlcabalaValoracionExtrasActual = new CpAlcabalaValoracionExtras();
                                adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosDeduccionesSeleccion.get(i)));
                                cpAlcabalaValoracionExtrasActual.setCatprealcvalCodigo(catastroPredialAlcabalaValoracion);
                                cpAlcabalaValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
                                cpAlcabalaValoracionExtrasActual.setCpalcvalextBase(BigDecimal.ZERO);
                                cpAlcabalaValoracionExtrasActual.setCpalcvalextValor(BigDecimal.ZERO);
                                cpAlcabalaValoracionExtrasServicio.crearCpAlcabalaValoracionExtras(cpAlcabalaValoracionExtrasActual);
                            }

                            for (int i = 0; i < listaAdicionalesDeductivosExcencionesSeleccion.size(); i++) {
                                cpAlcabalaValoracionExtrasActual = new CpAlcabalaValoracionExtras();
                                adicionalesDeductivosActual = adicionalesDeductivosServicio.buscarAdicionesDeductivosXCodigo(Integer.parseInt(listaAdicionalesDeductivosExcencionesSeleccion.get(i)));
                                cpAlcabalaValoracionExtrasActual.setCatprealcvalCodigo(catastroPredialAlcabalaValoracion);
                                cpAlcabalaValoracionExtrasActual.setAdidedCodigo(adicionalesDeductivosActual);
                                cpAlcabalaValoracionExtrasActual.setCpalcvalextBase(BigDecimal.ZERO);
                                cpAlcabalaValoracionExtrasActual.setCpalcvalextValor(BigDecimal.ZERO);
                                cpAlcabalaValoracionExtrasServicio.crearCpAlcabalaValoracionExtras(cpAlcabalaValoracionExtrasActual);
                            }

                            addSuccessMessage("Guardado Exitosamente!");

                        } else {
                            addErrorMessage("No existe Determinacion del Alcabala");

                        }

                    } else {
                        addSuccessMessage("No se han cargado documentos!");
                    }
                } catch (NullPointerException exNull) {
                    // LOGGER.log(Level.SEVERE, null, exNull);
                    addSuccessMessage("No se han cargado documentos!");
//              FacesMessage msg = new FacesMessage("No se han cargado documentos!");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            } else {
                addErrorMessage("No existe Clave Catastral");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    //////////////////////////////////// METODOS PLUSVALIA  ////////////////////////////////////
    
    
    
    public void listarTipoTarifa() {
        
        try {
           listaTipoDeTarifa = new ArrayList<CatalogoDetalle>();
           listaTipoDeTarifa = catastroPredialServicio.listarTipoDeTarifa();
            
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    
    

    ////////////////////////////////////// METODOS SET Y GET ALCABALA  ////////////////////////////////////
    
    public StreamedContent getArchivo() {
        return archivo;
    }

    public void setArchivo(StreamedContent archivo) {
        this.archivo = archivo;
    }

    public List<CatastroPredial> getListaCatastroPredial() {
        return listaCatastroPredial;
    }

    public void setListaCatastroPredial(List<CatastroPredial> listaCatastroPredial) {
        this.listaCatastroPredial = listaCatastroPredial;
    }

    public CatastroPredial getCatastroPredialActual() {
        return catastroPredialActual;
    }

    public void setCatastroPredialActual(CatastroPredial catastroPredialActual) {
        this.catastroPredialActual = catastroPredialActual;
    }

    public CatastroPredialAlcabalaValoracion getCatastroPredialAlcabalaValoracion() {
        return catastroPredialAlcabalaValoracion;
    }

    public void setCatastroPredialAlcabalaValoracion(CatastroPredialAlcabalaValoracion catastroPredialAlcabalaValoracion) {
        this.catastroPredialAlcabalaValoracion = catastroPredialAlcabalaValoracion;
    }

    public List<CatalogoDetalle> getListaCatalogoDetalleConcepto() {
        return listaCatalogoDetalleConcepto;
    }

    public void setListaCatalogoDetalleConcepto(List<CatalogoDetalle> listaCatalogoDetalleConcepto) {
        this.listaCatalogoDetalleConcepto = listaCatalogoDetalleConcepto;
    }

    public CatalogoDetalle getCatalogoDetalleConcepto() {
        return catalogoDetalleConcepto;
    }

    public void setCatalogoDetalleConcepto(CatalogoDetalle catalogoDetalleConcepto) {
        this.catalogoDetalleConcepto = catalogoDetalleConcepto;
    }

    public CatastroPredialValoracion getCatastroPredialValoracionActual() {
        return catastroPredialValoracionActual;
    }

    public void setCatastroPredialValoracionActual(CatastroPredialValoracion catastroPredialValoracionActual) {
        this.catastroPredialValoracionActual = catastroPredialValoracionActual;
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

    public List<AdicionalesDeductivos> getListaAdicionalesDeductivosExcenciones() {
        return listaAdicionalesDeductivosExcenciones;
    }

    public void setListaAdicionalesDeductivosExcenciones(List<AdicionalesDeductivos> listaAdicionalesDeductivosExcenciones) {
        this.listaAdicionalesDeductivosExcenciones = listaAdicionalesDeductivosExcenciones;
    }

    public List<String> getListaAdicionalesDeductivosExcencionesSeleccion() {
        return listaAdicionalesDeductivosExcencionesSeleccion;
    }

    public void setListaAdicionalesDeductivosExcencionesSeleccion(List<String> listaAdicionalesDeductivosExcencionesSeleccion) {
        this.listaAdicionalesDeductivosExcencionesSeleccion = listaAdicionalesDeductivosExcencionesSeleccion;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public List<PredioArchivo> getListaAlcabalasArchivo() {
        return listaAlcabalasArchivo;
    }

    public void setListaAlcabalasArchivo(List<PredioArchivo> listaAlcabalasArchivo) {
        this.listaAlcabalasArchivo = listaAlcabalasArchivo;
    }
    
     ////////////////////////////////////// METODOS SET Y GET PLUSVALIA  ////////////////////////////////////

    public List<CatalogoDetalle> getListaTipoDeTarifa() {
        return listaTipoDeTarifa;
    }

    public void setListaTipoDeTarifa(List<CatalogoDetalle> listaTipoDeTarifa) {
        this.listaTipoDeTarifa = listaTipoDeTarifa;
    }

    public CatastroPredialPlusvaliaValoracion getCatastroPredialPlusvaliaValoracion() {
        return catastroPredialPlusvaliaValoracion;
    }

    public void setCatastroPredialPlusvaliaValoracion(CatastroPredialPlusvaliaValoracion catastroPredialPlusvaliaValoracion) {
        this.catastroPredialPlusvaliaValoracion = catastroPredialPlusvaliaValoracion;
    }

}