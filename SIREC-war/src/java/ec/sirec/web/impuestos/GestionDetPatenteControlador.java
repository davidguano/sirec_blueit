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
    private PatenteValoracionExtras patValorExtra;
    private int verPanelDetalleImp;
    private BigDecimal valPatrimonio;
    private BigDecimal valImpPatente;
    private BigDecimal valImpBomberos;
    private BigDecimal valSubTotal;
    private BigDecimal valTotal;
    private BigDecimal valTasaProc;
    private BigDecimal valDeduciones;
    private BigDecimal valActivos;
    private BigDecimal valPasivos;
    private boolean habilitaEdicion;
    private String numPatente;
    DatoGlobal datoGlobalActual;
    private int buscNumPat;
    private int verBuscaPatente;
    private static final Logger LOGGER = Logger.getLogger(GestionDetPatenteControlador.class.getName());

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            verBuscaPatente=0;
            patValorExtra = new PatenteValoracionExtras();
            inicializarValores();
            datoGlobalActual = new DatoGlobal();
            patenteActual = new Patente();
            patenteValoracionActal = new PatenteValoracion();
            verPanelDetalleImp = 0;
            habilitaEdicion = false;
            numPatente = "";
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
            patenteValoracionActal = patenteServicio.buscaPatValoracion(patenteActual.getPatCodigo());
            patValorExtra = patenteServicio.buscaPatValExtraPorPatValoracion(patenteValoracionActal.getPatvalCodigo());
            valPatrimonio = BigDecimal.valueOf(valActivos.doubleValue() - valPasivos.doubleValue());
            valPatrimonio = valPatrimonio.setScale(2, RoundingMode.HALF_UP);
            calculaValorPatente();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void calculaValorDeduccion() {
        valDeduciones = patValorExtra.getPatenteBaseimpNegativa();
    }

    public void calculaValorPatente() {
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
            impExcede = (1 / 100);

        }
        if (valPatrimonio.doubleValue() >= 250000.01) {
            impFracBasica = 1885;
            impExcede = (1.30 / 100);
        }
        valImpPatente = BigDecimal.valueOf((valPatrimonio.doubleValue() - impFracBasica) * impExcede);
        valImpPatente = valImpPatente.setScale(2, RoundingMode.HALF_UP);
        calculaimpBomberos();
    }

    public void calculaimpBomberos() {
        double cuantia = (10 / 100);
        valImpBomberos = BigDecimal.valueOf(valImpPatente.doubleValue() * cuantia);
        valSubTotal = valImpPatente.add(valImpBomberos);
        valImpBomberos = valImpBomberos.setScale(2, RoundingMode.HALF_UP);
        valSubTotal = valSubTotal.setScale(2, RoundingMode.HALF_UP);
        calculaValorDeduccion();
        calculaTotal();
    }

    public void calculaTotal() {
        try {
            datoGlobalActual = patenteServicio.buscaMensajeTransaccion("Val_tasa_procesamiento");
            valTasaProc = BigDecimal.valueOf(Double.parseDouble(datoGlobalActual.getDatgloValor()));
            valTotal = BigDecimal.valueOf(valSubTotal.doubleValue() - valTasaProc.doubleValue() - valDeduciones.doubleValue());
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
              //  patenteValoracionActal.setPatCodigo(patenteActual);
                patenteValoracionActal.setPatvalPatrimonio(valPatrimonio);
                patenteValoracionActal.setPatvalImpuesto(valImpPatente);
                patenteValoracionActal.setPatvalSubtotal(valSubTotal);
                patenteValoracionActal.setPatvalTasaProc(valTasaProc);
                patenteValoracionActal.setPatvalDeducciones(valDeduciones);
                patenteValoracionActal.setPatvalTotal(valTotal);
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
            patenteActual = patenteServicio.cargarObjPatente(buscNumPat);
            numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void inicializarValores() {
        valActivos = null;
        valPasivos = null;
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

    public BigDecimal getValSubTotal() {
        return valSubTotal;
    }

    public void setValSubTotal(BigDecimal valSubTotal) {
        this.valSubTotal = valSubTotal;
    }

    public String getNumPatente() {
        return numPatente;
    }

    public void setNumPatente(String numPatente) {
        this.numPatente = numPatente;
    }

    public BigDecimal getValTotal() {
        return valTotal;
    }

    public void setValTotal(BigDecimal valTotal) {
        this.valTotal = valTotal;
    }

    public BigDecimal getValDeduciones() {
        return valDeduciones;
    }

    public void setValDeduciones(BigDecimal valDeduciones) {
        this.valDeduciones = valDeduciones;
    }

    public BigDecimal getValTasaProc() {
        return valTasaProc;
    }

    public void setValTasaProc(BigDecimal valTasaProc) {
        this.valTasaProc = valTasaProc;
    }

    public BigDecimal getValActivos() {
        return valActivos;
    }

    public void setValActivos(BigDecimal valActivos) {
        this.valActivos = valActivos;
    }

    public BigDecimal getValPasivos() {
        return valPasivos;
    }

    public void setValPasivos(BigDecimal valPasivos) {
        this.valPasivos = valPasivos;
    }

    public int getBuscNumPat() {
        return buscNumPat;
    }

    public void setBuscNumPat(int buscNumPat) {
        this.buscNumPat = buscNumPat;
    }

    public int getVerBuscaPatente() {
        return verBuscaPatente;
    }

    public void setVerBuscaPatente(int verBuscaPatente) {
        this.verBuscaPatente = verBuscaPatente;
    }

}
