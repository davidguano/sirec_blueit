/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.Patente15xmilValoracion;
import ec.sirec.ejb.entidades.PatenteValoracion;
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
public class GestionDetPatenteUnoCincoporMilControlador extends BaseControlador {

    @EJB
    private PatenteServicio patenteServicio;

    private Patente patenteActual;
    private Patente15xmilValoracion patente15milValActual;

    private int verPanelDetalleImp;
    private BigDecimal valBaseImponible;
    private BigDecimal valRecargos;
    private BigDecimal valImpuesto15xMil;
    private BigDecimal valTotal;
    private BigDecimal valTasaProc;
    private BigDecimal valSubTotal;
    private boolean habilitaEdicion;

    private static final Logger LOGGER = Logger.getLogger(GestionDetPatenteUnoCincoporMilControlador.class.getName());

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            patenteActual = new Patente();
            patente15milValActual = new Patente15xmilValoracion();
            verPanelDetalleImp = 0;
            habilitaEdicion = false;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionDetPatenteUnoCincoporMilControlador() {
    }

    public void activaPanelDetalleImpuestos() {
        verPanelDetalleImp = 1;
    }

    public void calcularBaseImponible() {
        valBaseImponible = BigDecimal.valueOf(patente15milValActual.getPat15valActivos().doubleValue() - patente15milValActual.getPat15valPasivosCorriente().doubleValue() - patente15milValActual.getPat15valPasivosConting().doubleValue() - patente15milValActual.getPat15valOtrasDeducciones().doubleValue());
        calculaValorImpuesto15xMil();
    }

    public void calculaValorImpuesto15xMil() {
        valImpuesto15xMil = BigDecimal.valueOf((valBaseImponible.doubleValue() * 1.5) / (1000));
        calculaSubtotal();
    }

    public void calculaSubtotal() {
        valSubTotal = BigDecimal.valueOf(valImpuesto15xMil.doubleValue() + 2);
        valSubTotal = valSubTotal.setScale(2, RoundingMode.HALF_UP);
    }

    public void guardaPatenteDet15xMil() {
        try {
            if (habilitaEdicion == false) {
                if (patenteServicio.existePatente15milValoracion(patente15milValActual.getPat15valCodigo())) {
                    addWarningMessage("Existe Código");
                } else {
                    patente15milValActual.setPat15valBaseImponible(valBaseImponible);
                    patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
                    patente15milValActual.setPat15valSubtotal(valSubTotal);
                    patenteServicio.crearPatente15milValoracion(patente15milValActual);
                    addSuccessMessage("Patente Valoración 1.5xmil Guardado");
                    patente15milValActual = new Patente15xmilValoracion();
                }
            } else {
                patenteServicio.editarPatente15milValoracion(patente15milValActual);
                addSuccessMessage("Patente Valoración  Actualizado");
                patente15milValActual = new Patente15xmilValoracion();
                habilitaEdicion = false;
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
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

    public BigDecimal getValBaseImponible() {
        return valBaseImponible;
    }

    public void setValBaseImponible(BigDecimal valBaseImponible) {
        this.valBaseImponible = valBaseImponible;
    }

    public BigDecimal getValImpuesto15xMil() {
        return valImpuesto15xMil;
    }

    public void setValImpuesto15xMil(BigDecimal valImpuesto15xMil) {
        this.valImpuesto15xMil = valImpuesto15xMil;
    }

    public BigDecimal getValTotal() {
        return valTotal;
    }

    public void setValTotal(BigDecimal valTotal) {
        this.valTotal = valTotal;
    }

    public BigDecimal getValSubTotal() {
        return valSubTotal;
    }

    public void setValSubTotal(BigDecimal valSubTotal) {
        this.valSubTotal = valSubTotal;
    }

   
    public Patente15xmilValoracion getPatente15milValActual() {
        return patente15milValActual;
    }

    public void setPatente15milValActual(Patente15xmilValoracion patente15milValActual) {
        this.patente15milValActual = patente15milValActual;
    }

}
