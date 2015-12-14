/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.DatoGlobal;
import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.Patente15xmilValoracion;
import ec.sirec.ejb.entidades.Patente15xmilValoracionExtras;
import ec.sirec.ejb.entidades.PatenteValoracion;
import ec.sirec.ejb.servicios.PatenteServicio;
import ec.sirec.ejb.servicios.UnoPCinoPorMilServicio;
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
    @EJB
    private UnoPCinoPorMilServicio unoPCinoPorMilServicio;
    private Patente patenteActual;
    private Patente15xmilValoracion patente15milValActual;
    private Patente15xmilValoracionExtras pat15milValExtrActual;
    DatoGlobal datoGlobalActal;
    private int verPanelDetalleImp;
    private BigDecimal valBaseImponible;
    private BigDecimal valRecargos;
    private BigDecimal valImpuesto15xMil;
    //private BigDecimal valTotalActivos;
    private BigDecimal valTasaProc;
    private BigDecimal valSubTotal;
    //  private BigDecimal valOtrasDeduc;
    //  private BigDecimal valPasivoCorriente;
    // private BigDecimal valPasivoContingente;
    private BigDecimal valTotal;
    private BigDecimal valTotAnual;
    private boolean habilitaEdicion;
    private String buscNumPat;
    private int verBuscaPatente;
    String numPatente;

    private static final Logger LOGGER = Logger.getLogger(GestionDetPatenteUnoCincoporMilControlador.class.getName());

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            numPatente = "";
            verBuscaPatente = 0;
            buscNumPat = "";
            datoGlobalActal = new DatoGlobal();
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

    public void buscarPatente() {
        try {
            verBuscaPatente = 1;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void cargarNumPatente() {
        patenteActual = (Patente) this.getSession().getAttribute("patente");
        if (patenteActual == null) {
            numPatente = null;
        } else {
            numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
        }
    }

    public void cagarPatenteActual() {
        try {
            patenteActual = patenteServicio.cargarObjPatente(Integer.parseInt(buscNumPat));
            numPatente = "AE-MPM-" + patenteActual.getPatCodigo();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    public void activaPanelDetalleImpuestos() {
        verPanelDetalleImp = 1;
    }

    public void calcularBaseImponible() {
        try {
            //   patente15milValActual = unoPCinoPorMilServicio.buscaPatValoracion15xMil(patenteActual.getPatCodigo());
            //   pat15milValExtrActual = unoPCinoPorMilServicio.buscaPatVal15xMilExtraPorPatValoracion(patente15milValActual.getPat15valCodigo());
            //   valBaseImponible = BigDecimal.valueOf((valTotalActivos.doubleValue() - valPasivoCorriente.doubleValue() - valPasivoContingente.doubleValue() - valOtrasDeduc.doubleValue()));
            valBaseImponible = BigDecimal.valueOf((patente15milValActual.getPat15valActivos().doubleValue() - patente15milValActual.getPat15valPasivosCorriente().doubleValue() - patente15milValActual.getPat15valPasivosConting().doubleValue() - patente15milValActual.getPat15valOtrasDeducciones().doubleValue()));
            valBaseImponible.setScale(2, RoundingMode.HALF_UP);
            patente15milValActual.setPat15valBaseImponible(valBaseImponible);
            datoGlobalActal = unoPCinoPorMilServicio.buscaMensajeTransaccion("Val_tasa_procesamiento");
            valTasaProc = BigDecimal.valueOf(Double.parseDouble(datoGlobalActal.getDatgloValor()));
            patente15milValActual.setPat15valTasaProc(valTasaProc);
            calculaValorImpuesto15xMil();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void calculaValorImpuesto15xMil() {
        valImpuesto15xMil = BigDecimal.valueOf((valBaseImponible.doubleValue() * 1.5) / (1000));
        valImpuesto15xMil.setScale(2, RoundingMode.HALF_UP);
        patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
        calculaDeducciones();
    }

    public void calculaTotalSubtotal() {
        valSubTotal = BigDecimal.valueOf(valTasaProc.doubleValue() + valImpuesto15xMil.doubleValue());
        valSubTotal.setScale(2, RoundingMode.HALF_UP);
        patente15milValActual.setPat15valSubtotal(valSubTotal);
        valTotal = BigDecimal.valueOf(valSubTotal.doubleValue() + valRecargos.doubleValue());
        valTotal.setScale(2, RoundingMode.HALF_UP);
        patente15milValActual.setPat15valTotal(valTotal);
    }
//--Falta calcular los recargos

    public void calculaDeducciones() {
        try {
            Patente15xmilValoracion objPat15MilAux = new Patente15xmilValoracion();
            objPat15MilAux = unoPCinoPorMilServicio.buscaPatValoracion15xMil(patenteActual.getPatCodigo());
            Patente15xmilValoracionExtras objPat15MilExtAux = new Patente15xmilValoracionExtras();
            objPat15MilExtAux = unoPCinoPorMilServicio.buscaPatVal15xMilExtraPorPatValoracion(objPat15MilAux.getPat15valCodigo());
            //--Exoneracion entidad publica
            if (objPat15MilExtAux.getPat15valextEntiPub() == true) {
                valImpuesto15xMil = BigDecimal.ZERO;
                patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
            }
            //--Exoneracion Fundaciones
            if (objPat15MilExtAux.getPat15valextFunBenEdu() == true) {
                valImpuesto15xMil = BigDecimal.ZERO;
                patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
            }
            //--Exoneracion Artesano
            if (objPat15MilExtAux.getPat15valextLeyFomArtes() == true) {
                valImpuesto15xMil = BigDecimal.ZERO;
                patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
            }
            //--Exoneracion Agro Industria
            if (objPat15MilExtAux.getPat15valextActAgro() == true) {
                valImpuesto15xMil = BigDecimal.ZERO;
                patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
            }
            //--Exoneracion coop
            if (objPat15MilExtAux.getPat15valextCoop() == true) {
                valImpuesto15xMil = BigDecimal.ZERO;
                patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
            }
            //--Exoneracion Multi Nacional
            if (objPat15MilExtAux.getPat15valextMultiNac() == true) {
                valImpuesto15xMil = BigDecimal.ZERO;
                patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
            }
            valRecargos = objPat15MilExtAux.getPat15valextBase();
            objPat15MilAux = new Patente15xmilValoracion();
            objPat15MilExtAux = new Patente15xmilValoracionExtras();
            calculaTotalSubtotal();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, null, e);
        }

    }

    public void inicializarValores() {
        valBaseImponible = null;
        valRecargos = null;
        valImpuesto15xMil = null;
        valTasaProc = null;
        valSubTotal = null;
        valTotal = null;
        valTotAnual = null;
    }

    public void guardaPatenteDet15xMil() {
        try {
            if (habilitaEdicion == false) {
//                if (patenteServicio.existePatenteValoracionExtra(patenteValoracionActal.getPatvalCodigo())) {
//                    addWarningMessage("Existe Código");
//                } else {
                patente15milValActual.setPatCodigo(patenteActual);
                //Año declaracion
                //Numero de sucursales
                //Año balance
                unoPCinoPorMilServicio.editarPatenteValoracion15xMil(patente15milValActual);
                addSuccessMessage("Patente 1.5 Mil Valoración Guardado");
                patente15milValActual = new Patente15xmilValoracion();
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

    public String getNumPatente() {
        return numPatente;
    }

    public void setNumPatente(String numPatente) {
        this.numPatente = numPatente;
    }

    public BigDecimal getValTasaProc() {
        return valTasaProc;
    }

    public void setValTasaProc(BigDecimal valTasaProc) {
        this.valTasaProc = valTasaProc;
    }

    public BigDecimal getValRecargos() {
        return valRecargos;
    }

    public void setValRecargos(BigDecimal valRecargos) {
        this.valRecargos = valRecargos;
    }

}
