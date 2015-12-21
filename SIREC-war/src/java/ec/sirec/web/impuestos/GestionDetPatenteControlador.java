/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.DatoGlobal;
import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.PatenteValoracion;
import ec.sirec.ejb.entidades.PatenteValoracionExtras;
import ec.sirec.ejb.servicios.PatenteServicio;
import ec.sirec.web.base.BaseControlador;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Darwin Aldas
 */
@ManagedBean
@ViewScoped
public class GestionDetPatenteControlador extends BaseControlador {

    @EJB
    private PatenteServicio patenteServicio;
    private Patente patenteActual;
    private PatenteValoracion patenteValoracionActal;
    private int verPanelDetalleImp;
    private BigDecimal valPatrimonio;
    private BigDecimal valImpPatente;
    private BigDecimal valImpBomberos;
    private BigDecimal valSubTotal;
    private BigDecimal valTotal;
    private BigDecimal valTasaProc;
    private BigDecimal valDeduciones;
    // private BigDecimal valActivos;
    // private BigDecimal valPasivos;
    private boolean habilitaEdicion;
    private String numPatente;
    DatoGlobal datoGlobalActual;
    private String buscNumPat;
    private int verBuscaPatente;
    private static final Logger LOGGER = Logger.getLogger(GestionDetPatenteControlador.class.getName());

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            verBuscaPatente = 0;
            inicializarValores();
            datoGlobalActual = new DatoGlobal();
            patenteActual = new Patente();
            patenteValoracionActal = new PatenteValoracion();
            verPanelDetalleImp = 0;
            habilitaEdicion = false;
            numPatente = "";
            buscNumPat = "";
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionDetPatenteControlador() {
    }

    public void activaPanelDetalleImpuestos() {
        verPanelDetalleImp = 1;
    }

    public void cargarNumPatente() {
        patenteActual = (Patente) this.getSession().getAttribute("patente");
        if (patenteActual == null) {
            numPatente = null;
        } else {
            numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
        }
    }

    public void calcularValorPatrimonio() {
        try {
            valPatrimonio = BigDecimal.valueOf(patenteValoracionActal.getPatvalActivos().doubleValue() - patenteValoracionActal.getPatvalPasivos().doubleValue());
            valPatrimonio = valPatrimonio.setScale(2, RoundingMode.HALF_UP);
            patenteValoracionActal.setPatvalPatrimonio(valPatrimonio);
            calculaImpuestoPatente();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void calculaValorDeduccion() {
        try {
            BigDecimal valReduccion = null; //Valor Reduccion
            BigDecimal valDatFalso = null; //Valor Falsedad Datos
            BigDecimal valBaseImponible = null;
            PatenteValoracion objPatValoracionAux = new PatenteValoracion();
            objPatValoracionAux = patenteServicio.buscaPatValoracion(patenteActual.getPatCodigo());
            PatenteValoracionExtras objPatValorExtAux = new PatenteValoracionExtras();
            objPatValorExtAux = patenteServicio.buscaPatValExtraPorPatValoracion(objPatValoracionAux.getPatvalCodigo());
            //Reduccin de impuesto Articulo 20----------------------------
            if (objPatValorExtAux.getPatvalextReduccionMitad() == true) {
                valReduccion = BigDecimal.valueOf(valImpPatente.doubleValue() / 2);
            }
            if (objPatValorExtAux.getPatvalextReduccion3eraparte() == true) {
                valReduccion = BigDecimal.valueOf(valImpPatente.doubleValue() / 3);
            }
            //Exoneracion Artesano Calificado---------------------------
            if (objPatValorExtAux.getPatvalextExonArtCalificado() == true) {
                valImpPatente = BigDecimal.ZERO;
                patenteValoracionActal.setPatvalImpuesto(valImpPatente);
            }
            //Falsedad de datos------------------------------------------
            if (objPatValorExtAux.getPatentePorcDatosfalsos() != 0) {
                DatoGlobal objDatglobAux = new DatoGlobal();
                objDatglobAux = patenteServicio.cargarObjPorNombre("Val_sueldo_basico");
                double valSueldoBasico = Double.parseDouble(objDatglobAux.getDatgloValor());
                valDatFalso = BigDecimal.valueOf((valSueldoBasico) * ((double)objPatValorExtAux.getPatentePorcDatosfalsos() / 100));
                objDatglobAux = new DatoGlobal();
            }
            //Evacion Tributaria-----------------------------------
            if (objPatValorExtAux.getPatentePorcIngreso() != 0) {
                valBaseImponible = BigDecimal.valueOf(((double)objPatValorExtAux.getPatentePorcIngreso() / 100) * (valPatrimonio.doubleValue()));
            }
            valDeduciones = BigDecimal.valueOf(valReduccion.doubleValue() + valImpPatente.doubleValue() + valDatFalso.doubleValue() + valBaseImponible.doubleValue());
            valDeduciones.setScale(2, RoundingMode.HALF_UP);
            patenteValoracionActal.setPatvalDeducciones(valDeduciones);
            objPatValorExtAux = new PatenteValoracionExtras();
            objPatValoracionAux = new PatenteValoracion();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void calculaImpuestoPatente() {
        double impFracBasica = 0.00;
        double impExcede = 0.00;
        if (valPatrimonio.doubleValue() >= 0 && valPatrimonio.doubleValue() <= 50000) {
            impFracBasica = 10;
            impExcede = (0.25 / 100);
        }
        if (valPatrimonio.doubleValue() >= 50000.01 && valPatrimonio.doubleValue() <= 10000000) {
            impFracBasica = 135;
            impExcede = (0.50 / 100);
        }
        if (valPatrimonio.doubleValue() >= 100000.01 && valPatrimonio.doubleValue() <= 250000) {
            impFracBasica = 385;
            impExcede = ((double)1 / 100);

        }
        if (valPatrimonio.doubleValue() >= 250000.01) {
            impFracBasica = 1885;
            impExcede = (1.30 / 100);
        }
        valImpPatente = BigDecimal.valueOf((valPatrimonio.doubleValue() - impFracBasica) * impExcede);
        valImpPatente = valImpPatente.setScale(2, RoundingMode.HALF_UP);
        patenteValoracionActal.setPatvalImpuesto(valImpPatente);
        calculaimpBomberos();
    }

    public void calculaimpBomberos() {
        double valCuantia = 0.00;
        System.out.println("valCuantia" +  valCuantia);
        valCuantia = (double)10/100;
         System.out.println("valCuantia Despues" +  valCuantia);
        valImpBomberos = BigDecimal.valueOf(valImpPatente.doubleValue() * valCuantia);
        valSubTotal = valImpPatente.add(valImpBomberos);
        valImpBomberos = valImpBomberos.setScale(2, RoundingMode.HALF_UP);
        valSubTotal = valSubTotal.setScale(2, RoundingMode.HALF_UP);
        patenteValoracionActal.setPatvalSubtotal(valSubTotal);
        calculaValorDeduccion();
        calculaTotal();
    }

    public void calculaTotal() {
        try {
            datoGlobalActual = patenteServicio.cargarObjPorNombre("Val_tasa_procesamiento");
            valTasaProc = BigDecimal.valueOf(Double.parseDouble(datoGlobalActual.getDatgloValor()));
            patenteValoracionActal.setPatvalTasaProc(valTasaProc);
            valTotal = BigDecimal.valueOf(valSubTotal.doubleValue() - valTasaProc.doubleValue() - valDeduciones.doubleValue());
            valTotal.setScale(2, RoundingMode.HALF_UP);
            patenteValoracionActal.setPatvalTotal(valTotal);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void guardaPatenteValoracion() {
        try {
            if (habilitaEdicion == false) {
//                if (patenteServicio.existePatenteValoracionExtra(patenteValoracionActal.getPatvalCodigo())) {
//                    addWarningMessage("Existe Código");
//                } else {
                patenteValoracionActal.setPatCodigo(patenteActual);
                patenteServicio.editarPatenteValoracion(patenteValoracionActal);
                addSuccessMessage("Patente Valoración Guardado");
                patenteValoracionActal = new PatenteValoracion();
                inicializarValores();
//                }
//            } else {
//                patenteServicio.editarPatenteValoracion(patenteValoracionActal);
//                addSuccessMessage("Patente Valoración  Actualizado");
//                patenteValoracionActal = new PatenteValoracion();
//                habilitaEdicion = false;
            }
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
            patenteActual = patenteServicio.cargarObjPatente(Integer.parseInt(buscNumPat));
            if (patenteActual == null) {
                numPatente = null;
            } else {
                numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }


    public void inicializarValores() {
        valImpBomberos = null;
        valImpPatente = null;
        valSubTotal = null;
        valDeduciones = null;
        valTotal = null;
        valTasaProc = null;

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

    public PatenteValoracion getPatenteValoracionActal() {
        return patenteValoracionActal;
    }

    public void setPatenteValoracionActal(PatenteValoracion patenteValoracionActal) {
        this.patenteValoracionActal = patenteValoracionActal;
    }

    public BigDecimal getValPatrimonio() {
        return valPatrimonio;
    }

    public void setValPatrimonio(BigDecimal valPatrimonio) {
        this.valPatrimonio = valPatrimonio;
    }

    public BigDecimal getValImpPatente() {
        return valImpPatente;
    }

    public void setValImpPatente(BigDecimal valImpPatente) {
        this.valImpPatente = valImpPatente;
    }

    public BigDecimal getValImpBomberos() {
        return valImpBomberos;
    }

    public void setValImpBomberos(BigDecimal valImpBomberos) {
        this.valImpBomberos = valImpBomberos;
    }

    public String getNumPatente() {
        return numPatente;
    }

    public void setNumPatente(String numPatente) {
        this.numPatente = numPatente;
    }

    public String getBuscNumPat() {
        return buscNumPat;
    }

    public void setBuscNumPat(String buscNumPat) {
        this.buscNumPat = buscNumPat;
    }

    public int getVerBuscaPatente() {
        return verBuscaPatente;
    }

    public void setVerBuscaPatente(int verBuscaPatente) {
        this.verBuscaPatente = verBuscaPatente;
    }

}
