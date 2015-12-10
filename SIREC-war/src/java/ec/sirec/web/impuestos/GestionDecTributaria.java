/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.impuestos;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.entidades.Patente15xmilValoracion;
import ec.sirec.ejb.entidades.PatenteValoracion;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.entidades.SegUsuario;
import ec.sirec.ejb.servicios.CatalogoDetalleServicio;
import ec.sirec.ejb.servicios.CatastroPredialServicio;
import ec.sirec.ejb.servicios.PatenteServicio;
import ec.sirec.ejb.servicios.PropietarioServicio;
import ec.sirec.web.base.BaseControlador;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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
public class GestionDecTributaria extends BaseControlador {

    @EJB
    private CatastroPredialServicio catastroPredialServicio;

    @EJB
    private PropietarioServicio propietarioServicio;

    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;

    @EJB
    private PatenteServicio patenteServicio;
    private CatastroPredial catastroPredialActual;
    private Patente patenteActual;
    private SegUsuario usuarioActual;
    private Propietario propietarioActual;
    private Patente15xmilValoracion patente15milValActual;
    private int verPanelDetalleImp;
    private boolean habilitaEdicion;
    private CatalogoDetalle catDetaActEconomicaActual;
     private CatalogoDetalle catDeTipoDeclaracion;
    private List<CatalogoDetalle> listCatDetActEconomica;
    private List<CatalogoDetalle> lisCatDeTipoDeclara;
    private static final Logger LOGGER = Logger.getLogger(GestionDecTributaria.class.getName());

    /**
     * Creates a new instance of GestionDetPatenteControlador
     */
    @PostConstruct
    public void inicializar() {
        try {
            catDeTipoDeclaracion= new CatalogoDetalle();
            catastroPredialActual = new CatastroPredial();
            usuarioActual = (SegUsuario) this.getSession().getAttribute("usuario");
            patenteActual = new Patente();
            patente15milValActual = new Patente15xmilValoracion();
            verPanelDetalleImp = 0;
            habilitaEdicion = false;
            listarActividadEconomica();
            listarTipoDeclaracion();

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public GestionDecTributaria() {
    }

    public void cargarInfoPropietario() {
        try {
            propietarioActual = propietarioServicio.buscarPropietario(usuarioActual.getUsuIdentificacion());
            catastroPredialActual = catastroPredialServicio.cargarObjCatPorPropietario(propietarioActual.getProCi());
            patenteActual = patenteServicio.cargarObjPatentePorCatastro(catastroPredialActual.getCatpreCodigo());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

    }

    public void activaPanelDetalleImpuestos() {
        verPanelDetalleImp = 1;
    }

    public void listarActividadEconomica() throws Exception {
        listCatDetActEconomica = catalogoDetalleServicio.listarPorNemonicoCatalogo("ACT_ECONOMICA");
    }
     public void listarTipoDeclaracion() throws Exception {
        lisCatDeTipoDeclara = catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_DCLARA");
    }

    public void guardaEmiPatente15xMil() {
        try {
            if (habilitaEdicion == false) {
//////                if (patenteServicio.existePatente15milValoracion(patente15milValActual.getPat15valCodigo())) {
//////                    addWarningMessage("Existe Código");
//////                } else {
////////                    patente15milValActual.setPat15valBaseImponible(valBaseImponible);
////////                    patente15milValActual.setPat15valImpuesto(valImpuesto15xMil);
////////                    patente15milValActual.setPat15valSubtotal(valSubTotal);
////////                    patenteServicio.crearPatente15milValoracion(patente15milValActual);
//////                    addSuccessMessage("Patente Valoración 1.5xmil Guardado");
//////                    patente15milValActual = new Patente15xmilValoracion();
//////                }
            } else {
                ///   patenteServicio.editarPatente15milValoracion(patente15milValActual);
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

    public CatalogoDetalle getCatDeTipoDeclaracion() {
        return catDeTipoDeclaracion;
    }

    public void setCatDeTipoDeclaracion(CatalogoDetalle catDeTipoDeclaracion) {
        this.catDeTipoDeclaracion = catDeTipoDeclaracion;
    }

    public List<CatalogoDetalle> getLisCatDeTipoDeclara() {
        return lisCatDeTipoDeclara;
    }

    public void setLisCatDeTipoDeclara(List<CatalogoDetalle> lisCatDeTipoDeclara) {
        this.lisCatDeTipoDeclara = lisCatDeTipoDeclara;
    }

}
