/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.CatalogoDetalle;

import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.Patente15xmilValoracion;
import ec.sirec.ejb.entidades.PatenteArchivo;

import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.entidades.SegUsuario;


import ec.sirec.ejb.servicios.PatenteArchivoServicio;
import ec.sirec.ejb.servicios.PatenteServicio;
import ec.sirec.ejb.servicios.PropietarioServicio;
import ec.sirec.web.base.BaseControlador;
import ec.sirec.web.util.ParametrosFile;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Darwin Aldas
 */
@ManagedBean
@ViewScoped
public class GestionTblAmortizaControlador extends BaseControlador {
    @EJB
    private PatenteArchivoServicio patenteArchivoServicio;

    
    @EJB
    private PropietarioServicio propietarioServicio;
 
    @EJB
    private PatenteServicio patenteServicio;
    
   
    private Patente patenteActual;
    private SegUsuario usuarioActual;
    private Propietario propietarioActual;
    private Patente15xmilValoracion patente15milValActual;
    private int verPanelDetalleImp;
    private boolean habilitaEdicion;
    private CatalogoDetalle catDetaActEconomicaActual;
    private List<CatalogoDetalle> listCatDetActEconomica;
    private static final Logger LOGGER = Logger.getLogger(GestionTblAmortizaControlador.class.getName());
    private List<ParametrosFile> listaFiles;
    private List<PatenteArchivo> listadoArchivos;
    private PatenteArchivo patenteArchivoActual;
    private int verArchivos;
    private Date fechaAdjudica;
    private Date fechaVencmiento;

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            listaFiles = new ArrayList<ParametrosFile>();
            listadoArchivos = new ArrayList<PatenteArchivo>();
             patenteArchivoActual=new PatenteArchivo();
            usuarioActual = (SegUsuario) this.getSession().getAttribute("usuario");
            patenteActual = new Patente();
            patente15milValActual = new Patente15xmilValoracion();
            verPanelDetalleImp = 0;
            habilitaEdicion = false;          
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionTblAmortizaControlador() {
    }

    

    public void activaPanelDetalleImpuestos() {
        verPanelDetalleImp = 1;
    }

  

//    public void guardaEmiPatente15xMil() {
//        try {
//            if (habilitaEdicion == false) {
////////                if (patenteServicio.existePatente15milValoracion(patente15milValActual.getPat15valCodigo())) {
////////                    addWarningMessage("Existe Código");
////////                } else {
//////////                    patente15milValActual.setPat15valBaseImponible(valBaseImponible);
//////////                    patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
//////////                    patente15milValActual.setPat15valSubtotal(valSubTotal);
//////////                    patenteServicio.crearPatente15milValoracion(patente15milValActual);
////////                    addSuccessMessage("Patente Valoración 1.5xmil Guardado");
////////                    patente15milValActual = new Patente15xmilValoracion();
////////                }
//            } else {
//                ///   patenteServicio.editarPatente15milValoracion(patente15milValActual);
//                addSuccessMessage("Patente Valoración  Actualizado");
//                patente15milValActual = new Patente15xmilValoracion();
//                habilitaEdicion = false;
//            }
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, null, e);
//        }
//    }

    public void guardarArchivos() {
        Iterator<ParametrosFile> itera = listaFiles.iterator();
        try {
            while (itera.hasNext()) {
                ParametrosFile elemento = itera.next();
                PatenteArchivo patArchivo = new PatenteArchivo();
                patArchivo.setPatCodigo(patenteActual);
                patArchivo.setPatarcNombre(elemento.getName());
                patArchivo.setPatarcData(elemento.getData());
                patArchivo.setPatarcTipo("EX"); //Archivo de Patentes
                patArchivo.setUsuIdentificacion(usuarioActual);
            //    patArchivo.setUltaccDetalle(datoGlobalActual.getDatgloDescripcion());
                patArchivo.setUltaccMarcatiempo(java.util.Calendar.getInstance().getTime());
                patenteArchivoServicio.guardarArchivo(patArchivo);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    //-----Carga de archivos

    public void handleFileUpload(FileUploadEvent event) throws Exception {
        try {
            InputStream is = event.getFile().getInputstream();
            ParametrosFile archivo = new ParametrosFile();
            archivo.setLength(event.getFile().getSize());
            archivo.setName(event.getFile().getFileName());
            archivo.setData(event.getFile().getContents());
            listaFiles.add(archivo);
            addSuccessMessage(event.getFile().getFileName() + "Archivo Cargado");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void confirmaEliminarArchivo(ParametrosFile archivo) {
        try {
            listaFiles.remove(archivo);
            addSuccessMessage("Archivo Eliminado");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el regitro");
        }
    }

    public void confirmaEliminarPatArchivo(PatenteArchivo file) {
        try {
            patenteArchivoServicio.eliminarArchivo(file);
            addSuccessMessage("Registro Eliminado");
            listadoArchivos = patenteArchivoServicio.listarArchivoPorPatente(patenteActual);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
            addWarningMessage("No se puede eliminar el regitro");
        }
    }
    //Preparamos archivo para descarga

    public void descargarArchivo(PatenteArchivo patArchivoActual) {
        patenteArchivoActual = patArchivoActual;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("datoArchivo", patArchivoActual.getPatarcData());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreArchivo", patArchivoActual.getPatarcNombre());
    }
//Se descarga archivo por medio de  Servlet

    public String download() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-disposition", "attachment; filename=\"" + "c://subido" + patenteArchivoActual.getPatarcNombre() + "\"");
        try {
            ServletOutputStream os = response.getOutputStream();
            os.write(patenteArchivoActual.getPatarcData());
            os.flush();
            os.close();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }

    public int getVerPanelDetalleImp() {
        return verPanelDetalleImp;
    }

    public void setVerPanelDetalleImp(int verPanelDetalleImp) {
        this.verPanelDetalleImp = verPanelDetalleImp;
    }

    public Patente getPatenteActual() {
        return patenteActual;
    }

    public void setPatenteActual(Patente patenteActual) {
        this.patenteActual = patenteActual;
    }

    public Patente15xmilValoracion getPatente15milValActual() {
        return patente15milValActual;
    }

    public void setPatente15milValActual(Patente15xmilValoracion patente15milValActual) {
        this.patente15milValActual = patente15milValActual;
    }

    public CatalogoDetalle getCatDetaActEconomicaActual() {
        return catDetaActEconomicaActual;
    }

    public void setCatDetaActEconomicaActual(CatalogoDetalle catDetaActEconomicaActual) {
        this.catDetaActEconomicaActual = catDetaActEconomicaActual;
    }

    public List<CatalogoDetalle> getListCatDetActEconomica() {
        return listCatDetActEconomica;
    }

    public void setListCatDetActEconomica(List<CatalogoDetalle> listCatDetActEconomica) {
        this.listCatDetActEconomica = listCatDetActEconomica;
    }

    public Propietario getPropietarioActual() {
        return propietarioActual;
    }

    public void setPropietarioActual(Propietario propietarioActual) {
        this.propietarioActual = propietarioActual;
    }

    public List<ParametrosFile> getListaFiles() {
        return listaFiles;
    }

    public void setListaFiles(List<ParametrosFile> listaFiles) {
        this.listaFiles = listaFiles;
    }

    public List<PatenteArchivo> getListadoArchivos() {
        return listadoArchivos;
    }

    public void setListadoArchivos(List<PatenteArchivo> listadoArchivos) {
        this.listadoArchivos = listadoArchivos;
    }

    public int getVerArchivos() {
        return verArchivos;
    }

    public void setVerArchivos(int verArchivos) {
        this.verArchivos = verArchivos;
    }

    public Date getFechaAdjudica() {
        return fechaAdjudica;
    }

    public void setFechaAdjudica(Date fechaAdjudica) {
        this.fechaAdjudica = fechaAdjudica;
    }

    public Date getFechaVencmiento() {
        return fechaVencmiento;
    }

    public void setFechaVencmiento(Date fechaVencmiento) {
        this.fechaVencmiento = fechaVencmiento;
    }

}
