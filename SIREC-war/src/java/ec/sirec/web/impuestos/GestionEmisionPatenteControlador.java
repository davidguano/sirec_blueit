/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.AdicionalesDeductivos;
import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.DatoGlobal;
import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.PatenteArchivo;
import ec.sirec.ejb.entidades.PatenteValoracion;
import ec.sirec.ejb.entidades.PatenteValoracionExtras;
import ec.sirec.ejb.entidades.SegUsuario;
import ec.sirec.ejb.servicios.AdicionalesDeductivosServicio;
import ec.sirec.ejb.servicios.CatalogoDetalleServicio;
import ec.sirec.ejb.servicios.PatenteArchivoServicio;
import ec.sirec.ejb.servicios.PatenteServicio;
import ec.sirec.web.base.BaseControlador;
import ec.sirec.web.util.ParametrosFile;
import java.io.InputStream;
import java.io.Serializable;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import static org.apache.poi.ss.util.CellUtil.createCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Darwin Aldas
 */
@ManagedBean
@ViewScoped
public class GestionEmisionPatenteControlador extends BaseControlador {

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;

    @EJB
    private PatenteServicio patenteServicio;

    private Patente patenteActual;
    private PatenteValoracion patValoracionActual;
    private CatalogoDetalle catDetParroquias;
    private List<CatalogoDetalle> listDetParroquias;
    private static final Logger LOGGER = Logger.getLogger(GestionEmisionPatenteControlador.class.getName());
    private List<Object[]> listaEmisionPatente;
    private String numPatente;

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            numPatente = "";
            patenteActual = new Patente();
            patValoracionActual= new PatenteValoracion();
            listaEmisionPatente = new ArrayList<Object[]>();
            listarParroquias();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionEmisionPatenteControlador() {
    }

//    public void guardaPatenteValExtra() {
//        try {
//            if (habilitaEdicion == false) {
////                if (patenteServicio.existePatenteValoracionExtra(patValExActual.getPatvalextCodigo())) {
////                    addWarningMessage("Existe Código");
////                } else {
//                guardaPatenteValoracion();
//                patValExActual.setAdidedCodigo(adiDeductivoActual);
//                patValExActual.setPatvalCodigo(patenteValoracionActal);
//                patenteServicio.crearPatenteValoracionExtra(patValExActual);
//                addSuccessMessage("Patente Valoración Extra Guardado");
//                patValExActual = new PatenteValoracionExtras();
//                cargaObjetosBitacora();
//                guardarArchivos();
//            }
////            } else {
////                patenteServicio.editarPatenteValoracionExtra(patValExActual);
////                addSuccessMessage("Patente Valoración  Actualizado");
////                patValExActual = new PatenteValoracionExtras();
////                habilitaEdicion = false;
////            }
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, null, e);
//        }
//    }
//    public void buscarPatente() {
//        try {
//            verBuscaPatente = 1;
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, null, e);
//        }
//    }
    public void listarEmisionPatente() {
        try {
            listaEmisionPatente = patenteServicio.listarEmisionPatente(patenteActual.getPatCodigo());
            if (listaEmisionPatente!=null) {
                numPatente = "Positivo";
            } else {
                numPatente = null;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void postProcessXLS(Object document) {
        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0); //Creo variable  hoja ()contiene los atributos para la hoja de calculo
        List<String> encabezadoColumna = new ArrayList<String>();
        for (Row row : sheet) { //Recorre los valores de la fila 1 (encabezado) pero en dataTable=0
            if (row.getRowNum() == 0) {
                for (Cell cell : row) {
                    encabezadoColumna.add(cell.getStringCellValue() + " ");
                }
            } else {
                break;
            }
        }
        //----inicio crea estilo
        XSSFCellStyle style = wb.createCellStyle(); //Se crea el estilo
        XSSFFont font = wb.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        byte[] rgb = new byte[3];
        rgb[0] = (byte) 076;
        rgb[1] = (byte) 145;
        rgb[2] = (byte) 065;
        XSSFColor myColor = new XSSFColor(rgb);
        style.setFillForegroundColor(myColor);
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        XSSFRow row0 = sheet.createRow((short) 0); //Creo una fila en la posicion 0
        //----fin crea estilo
        for (int i = 0; i <= encabezadoColumna.size() - 1; i++) {
            createCell(row0, i, encabezadoColumna.get(i), style); //agrego celdas en la posicion indicada con los valores de los encabezados
        }
//Ajusta el ancho de las columnas
        for (int i = 0; i < 20; i++) {
            sheet.autoSizeColumn((short) i);
        }
    }

    public void listarParroquias() throws Exception {
        listDetParroquias = catalogoDetalleServicio.listarPorNemonicoCatalogo("CIUDADES");
    }

//    public void listarArchivosPatente() throws Exception {
//        listadoArchivos = patenteArchivoServicio.listarArchivoPorPatente(patenteActual);
//    }
//    public void activPanelCargrArchivos() {
//        cargarArchivos = 1;
//    }
//    public void activaPanelVerArchivos() {
//        try {
//            listarArchivosPatente();
//            if (listadoArchivos.isEmpty()) {
//                verArchivos = 1;
//            } else {
//                verArchivos = 0;
//            }
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, null, e);
//        }
//    }
//    public PatenteValoracionExtras getPatValExActual() {
//        return patValExActual;
//    }
//    public void setPatValExActual(PatenteValoracionExtras patValExActual) {
//        this.patValExActual = patValExActual;
//    }
//    public int getVerArchivos() {
//        return verArchivos;
//    }
//    public void setVerArchivos(int verArchivos) {
//        this.verArchivos = verArchivos;
//    }
//    public int getCargarArchivos() {
//        return cargarArchivos;
//    }
//    public void setCargarArchivos(int cargarArchivos) {
//        this.cargarArchivos = cargarArchivos;
//    }
//    public String getNumPatente() {
//        return numPatente;
//    }
//
//    public void setNumPatente(String numPatente) {
//        this.numPatente = numPatente;
//    }
//
//    public AdicionalesDeductivos getAdiDeductivoActual() {
//        return adiDeductivoActual;
//    }
//
//    public void setAdiDeductivoActual(AdicionalesDeductivos adiDeductivoActual) {
//        this.adiDeductivoActual = adiDeductivoActual;
//    }
//    public List<AdicionalesDeductivos> getListAdicionalDeductivo() {
//        return listAdicionalDeductivo;
//    }
//
//    public void setListAdicionalDeductivo(List<AdicionalesDeductivos> listAdicionalDeductivo) {
//        this.listAdicionalDeductivo = listAdicionalDeductivo;
//    }
//
//    public List<ParametrosFile> getListaFiles() {
//        return listaFiles;
//    }
//
//    public void setListaFiles(List<ParametrosFile> listaFiles) {
//        this.listaFiles = listaFiles;
//    }
//    public List<PatenteArchivo> getListadoArchivos() {
//        return listadoArchivos;
//    }
//
//    public void setListadoArchivos(List<PatenteArchivo> listadoArchivos) {
//        this.listadoArchivos = listadoArchivos;
//    }
//
//    public int getVerBuscaPatente() {
//        return verBuscaPatente;
//    }
//
//    public void setVerBuscaPatente(int verBuscaPatente) {
//        this.verBuscaPatente = verBuscaPatente;
//    }
//    public String getBuscNumPat() {
//        return buscNumPat;
//    }
//
//    public void setBuscNumPat(String buscNumPat) {
//        this.buscNumPat = buscNumPat;
//    }
    public List<Object[]> getListaEmisionPatente() {
        return listaEmisionPatente;
    }

    public void setListaEmisionPatente(List<Object[]> listaEmisionPatente) {
        this.listaEmisionPatente = listaEmisionPatente;
    }

    public CatalogoDetalle getCatDetParroquias() {
        return catDetParroquias;
    }

    public void setCatDetParroquias(CatalogoDetalle catDetParroquias) {
        this.catDetParroquias = catDetParroquias;
    }

    public List<CatalogoDetalle> getListDetParroquias() {
        return listDetParroquias;
    }

    public void setListDetParroquias(List<CatalogoDetalle> listDetParroquias) {
        this.listDetParroquias = listDetParroquias;
    }

    public Patente getPatenteActual() {
        return patenteActual;
    }

    public void setPatenteActual(Patente patenteActual) {
        this.patenteActual = patenteActual;
    }

    public String getNumPatente() {
        return numPatente;
    }

    public void setNumPatente(String numPatente) {
        this.numPatente = numPatente;
    }

    public PatenteValoracion getPatValoracionActual() {
        return patValoracionActual;
    }

    public void setPatValoracionActual(PatenteValoracion patValoracionActual) {
        this.patValoracionActual = patValoracionActual;
    }

}
