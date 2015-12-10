/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.AdicionalesDeductivos;
import ec.sirec.ejb.entidades.DatoGlobal;
import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.PatenteArchivo;
import ec.sirec.ejb.entidades.PatenteValoracion;
import ec.sirec.ejb.entidades.PatenteValoracionExtras;
import ec.sirec.ejb.entidades.SegUsuario;
import ec.sirec.ejb.servicios.AdicionalesDeductivosServicio;
import ec.sirec.ejb.servicios.PatenteArchivoServicio;
import ec.sirec.ejb.servicios.PatenteServicio;
import ec.sirec.web.base.BaseControlador;
import ec.sirec.web.util.ParametrosFile;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
public class GestionExoDedMulPatenteControlador extends BaseControlador {

    @EJB
    private PatenteArchivoServicio patenteArchivoServicio;
    @EJB
    private AdicionalesDeductivosServicio adicionalesDeductivosServicio;
    @EJB
    private PatenteServicio patenteServicio;
    private Patente patenteActual;
    private PatenteValoracionExtras patValExActual;
    private PatenteValoracion patenteValoracionActal;
    private String numPatente = "";
    private boolean habilitaEdicion;
    private static final Logger LOGGER = Logger.getLogger(GestionExoDedMulPatenteControlador.class.getName());
    private int verArchivos;
    private int cargarArchivos;
    private int verBuscaPatente;
    private AdicionalesDeductivos adiDeductivoActual;
    private List<AdicionalesDeductivos> listAdicionalDeductivo;
    private List<ParametrosFile> listaFiles;
    private List<PatenteArchivo> listadoArchivos;
    private DatoGlobal datoGlobalActual;
    private SegUsuario usuarioActual;
    private PatenteArchivo patenteArchivoActual;

    private int buscNumPat;

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            patenteArchivoActual = new PatenteArchivo();
            adiDeductivoActual = new AdicionalesDeductivos();
            patenteActual = new Patente();
            patenteValoracionActal = new PatenteValoracion();
            verArchivos = 0;
            cargarArchivos = 0;
            patValExActual = new PatenteValoracionExtras();
            habilitaEdicion = false;
            listaFiles = new ArrayList<ParametrosFile>();
            listadoArchivos = new ArrayList<PatenteArchivo>();
            listAdicionalDeductivo = new ArrayList<AdicionalesDeductivos>();
            listarAdicionalDeductivo();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionExoDedMulPatenteControlador() {
    }

    public void cargarNumPatente() {
        patenteActual = (Patente) this.getSession().getAttribute("patente");
        if (patenteActual == null) {
            numPatente = null;
        } else {
            numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
        }
    }

    public void guardaPatenteValExtra() {
        try {
            if (habilitaEdicion == false) {
//                if (patenteServicio.existePatenteValoracionExtra(patValExActual.getPatvalextCodigo())) {
//                    addWarningMessage("Existe Código");
//                } else {
                guardaPatenteValoracion();
                patValExActual.setAdidedCodigo(adiDeductivoActual);
                patValExActual.setPatvalCodigo(patenteValoracionActal);
                patenteServicio.crearPatenteValoracionExtra(patValExActual);
                addSuccessMessage("Patente Valoración Extra Guardado");
                patValExActual = new PatenteValoracionExtras();
                cargaObjetosBitacora();
                guardarArchivos();
            }
//            } else {
//                patenteServicio.editarPatenteValoracionExtra(patValExActual);
//                addSuccessMessage("Patente Valoración  Actualizado");
//                patValExActual = new PatenteValoracionExtras();
//                habilitaEdicion = false;
//            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void buscarPatente() {
        try {
            verBuscaPatente = 1;

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void cagarPatenteActual() {
        try {
            patenteActual = patenteServicio.cargarObjPatente(buscNumPat);
            numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void guardaPatenteValoracion() {
        BigDecimal valTemporal;
        valTemporal = BigDecimal.valueOf(0.00);
        try {
            patenteValoracionActal.setPatCodigo(patenteActual);
            patenteValoracionActal.setPatvalPatrimonio(valTemporal);
            patenteValoracionActal.setPatvalImpuesto(valTemporal);
            patenteValoracionActal.setPatvalSubtotal(valTemporal);
            patenteValoracionActal.setPatvalTasaProc(valTemporal);
            patenteValoracionActal.setPatvalDeducciones(valTemporal);
            patenteValoracionActal.setPatvalPasivos(valTemporal);
            patenteValoracionActal.setPatvalActivos(valTemporal);
            patenteValoracionActal.setPatvalTotal(valTemporal);
            patenteValoracionActal.setPatvalAnio(0);
            patenteServicio.crearPatenteValoracion(patenteValoracionActal);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    //Carga los objetos para guardar en la tabla bitacora
    public void cargaObjetosBitacora() {
        try {
            datoGlobalActual = new DatoGlobal();
            usuarioActual = new SegUsuario();
            datoGlobalActual = patenteServicio.buscaMensajeTransaccion("Msj_Pat_In");
            usuarioActual = (SegUsuario) this.getSession().getAttribute("usuario");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

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
                patArchivo.setUltaccDetalle(datoGlobalActual.getDatgloDescripcion());
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

    public void descargarArchivo(PatenteArchivo patArchivo) {
        patenteArchivoActual = patArchivo;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("datoArchivo", patenteArchivoActual.getPatarcData());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("nombreArchivo", patenteArchivoActual.getPatarcNombre());
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

    public void listarAdicionalDeductivo() throws Exception {
        listAdicionalDeductivo = adicionalesDeductivosServicio.listarAdicionesDeductivosTipoImpuesto("PA");
    }

    public void listarArchivosPatente() throws Exception {
        listadoArchivos = patenteArchivoServicio.listarArchivoPorPatente(patenteActual);
    }

    public void activPanelCargrArchivos() {
        cargarArchivos = 1;

    }

    public void activaPanerVerArchivos() {
        try {
            verArchivos = 1;
            listarArchivosPatente();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public PatenteValoracionExtras getPatValExActual() {
        return patValExActual;
    }

    public void setPatValExActual(PatenteValoracionExtras patValExActual) {
        this.patValExActual = patValExActual;
    }

    public int getVerArchivos() {
        return verArchivos;
    }

    public void setVerArchivos(int verArchivos) {
        this.verArchivos = verArchivos;
    }

    public int getCargarArchivos() {
        return cargarArchivos;
    }

    public void setCargarArchivos(int cargarArchivos) {
        this.cargarArchivos = cargarArchivos;
    }

    public String getNumPatente() {
        return numPatente;
    }

    public void setNumPatente(String numPatente) {
        this.numPatente = numPatente;
    }

    public AdicionalesDeductivos getAdiDeductivoActual() {
        return adiDeductivoActual;
    }

    public void setAdiDeductivoActual(AdicionalesDeductivos adiDeductivoActual) {
        this.adiDeductivoActual = adiDeductivoActual;
    }

    public List<AdicionalesDeductivos> getListAdicionalDeductivo() {
        return listAdicionalDeductivo;
    }

    public void setListAdicionalDeductivo(List<AdicionalesDeductivos> listAdicionalDeductivo) {
        this.listAdicionalDeductivo = listAdicionalDeductivo;
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

    public int getVerBuscaPatente() {
        return verBuscaPatente;
    }

    public void setVerBuscaPatente(int verBuscaPatente) {
        this.verBuscaPatente = verBuscaPatente;
    }

    public int getBuscNumPat() {
        return buscNumPat;
    }

    public void setBuscNumPat(int buscNumPat) {
        this.buscNumPat = buscNumPat;
    }

}
