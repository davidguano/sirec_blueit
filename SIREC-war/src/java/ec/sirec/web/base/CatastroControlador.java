/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.web.base;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialAreas;
import ec.sirec.ejb.entidades.CatastroPredialInfraestructura;
import ec.sirec.ejb.entidades.CatastroPredialUsosuelo;
import ec.sirec.ejb.servicios.CatastroPredialServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author DAVID GUAN
 */
@ManagedBean
@ViewScoped
public class CatastroControlador extends BaseControlador{
    
    
    private static final Logger LOGGER = Logger.getLogger(CatastroControlador.class.getName());
    @EJB
    private CatastroPredialServicio catastroServicio;
    
    private CatastroPredial catastroPredialActual;
    private CatastroPredialAreas catastroPredialAreaBloqueActual;
    private List<CatastroPredialAreas> listaCatastroPredialAreasBloque;
    
    private List<CatastroPredialInfraestructura> listaInfServicios;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado1;
    private List<CatastroPredialInfraestructura> listaInfUso;
    private List<CatastroPredialInfraestructura> listaInfMaterial;
    private List<CatastroPredialInfraestructura> listaInfSentido;
    private List<CatastroPredialInfraestructura> listaInfEnergiaElec;
    private List<CatastroPredialInfraestructura> listaInfAbasAgua;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado2;
    private List<CatastroPredialInfraestructura> listaInfOtrosServicios;
    
    private List<CatastroPredialInfraestructura> listaInfServiciosSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado1Seleccionado;
    private List<CatastroPredialInfraestructura> listaInfUsoSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfMaterialSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfSentidoSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfEnergiaElecSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfAbasAguaSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado2Seleccionado;
    private List<CatastroPredialInfraestructura> listaInfOtrosServiciosSeleccionado;
    
    
    private List<CatastroPredialUsosuelo> listaUsuSuelo;
    
    private List<CatalogoDetalle> listaTipoVia;
    private List<CatalogoDetalle> listaTipoUbicacion;
    private List<CatalogoDetalle> listaTipoProp1;
    private List<CatalogoDetalle> listaTipoProp2;
    private List<CatalogoDetalle> listaRefCartograficas;
    private List<CatalogoDetalle> listaTenenciaDominio;
    private List<CatalogoDetalle> listaTenenciaTraslacionDominio;
    private List<CatalogoDetalle> listaTerrenoOcupacion;
    private List<CatalogoDetalle> listaTerrenoNoEdificado;
    private List<CatalogoDetalle> listaTerrenoConstruccion;
    private List<CatalogoDetalle> listaTerrenoCaracteristicasSuelo;
    private List<CatalogoDetalle> listaTerrenoForma;
    private List<CatalogoDetalle> listaTerrenoTopografia;
    private List<CatalogoDetalle> listaTerrenoLocalizacion;
    private List<CatalogoDetalle> listaUsoSueloTipoNegocio;
    private List<CatalogoDetalle> listaUsoSueloTiempoFuncionamiento;
    
    
    /**
     * Creates a new instance of CatastroControlador
     */
    public CatastroControlador() {
    }
    
    @PostConstruct
    public void inicializar(){
        try{
            
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,null,ex);
        }
    }
}
