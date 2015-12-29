/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.web.base;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.servicios.PropietarioServicio;
import java.util.ArrayList;
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
public class PropietarioControlador extends BaseControlador {
    
    private static final Logger LOGGER = Logger.getLogger(PropietarioControlador.class.getName());
    @EJB
    private PropietarioServicio propietarioServicio;
    
    private Propietario propietarioActual;
    private List<Propietario> listaPropietarios;
    private List<CatalogoDetalle> listaCatCiudades;
    private List<CatalogoDetalle> listaCatTipoPersonaJuridica;
    private boolean flagEditar;

    /**
     * Creates a new instance of PropietarioControlador
     */
    public PropietarioControlador() {
    }
    
    @PostConstruct
    public void inicializar() {
        try {
            flagEditar = false;
            propietarioActual = new Propietario();
            listaPropietarios = new ArrayList<Propietario>();
            listaCatCiudades = propietarioServicio.listarCiudades();
            listaCatTipoPersonaJuridica=propietarioServicio.listarTiposPersonasJuridicas();
            listarPropietarios();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    public void validarCedulaRuc(){
        try{
            if (propietarioServicio.esCedulaRucValida(propietarioActual.getProCi())) {
                addSuccessMessage("Cedula valida");
            }else{
                addErrorMessage("Cedula no valida");
                propietarioActual.setProCi(null);
            }
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    public List<CatalogoDetalle> listarCatDetalles(String valor){
        List<CatalogoDetalle> lstDet=new ArrayList<CatalogoDetalle>();
        try{
            return propietarioServicio.listarCiudadesPorTexto(valor);
        }catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return lstDet;
    }
    public void guardarPropietario() {
        try {
            if (propietarioServicio.esCedulaRucValida(propietarioActual.getProCi()) && propietarioServicio.esFechaNacimientoValida(propietarioActual.getProFechaNacimiento())) {
                if(propietarioActual.getProTipoPersona().equals("N")){
                    propietarioActual.setCatdetTipoperjur(null);
                }
                if (!flagEditar) {
                    propietarioActual.setUsuIdentificacion(obtenerUsuarioAutenticado());
                    propietarioServicio.crearPropietario(propietarioActual);
                    propietarioActual = new Propietario();
                    addSuccessMessage("Propietario creado correctamente");
                } else {
                    propietarioActual.setUsuIdentificacion(obtenerUsuarioAutenticado());
                    propietarioServicio.editarPropietario(propietarioActual);
                    addSuccessMessage("Propietario editado correctamente");
                }
                listarPropietarios();
            } else {
                addErrorMessage("Cedula o Fecha de Nacimiento no valida");
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public void seleccionarPropietario(Propietario vpropietario) {
        try {
            propietarioActual = vpropietario;
            flagEditar = true;
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void listarPropietarios() {
        try {
            flagEditar = false;
            listaPropietarios = propietarioServicio.listarPropietariosTodos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
    
    public Propietario getPropietarioActual() {
        return propietarioActual;
    }
    
    public void setPropietarioActual(Propietario propietarioActual) {
        this.propietarioActual = propietarioActual;
    }
    
    public List<Propietario> getListaPropietarios() {
        return listaPropietarios;
    }
    
    public void setListaPropietarios(List<Propietario> listaPropietarios) {
        this.listaPropietarios = listaPropietarios;
    }
    
    public List<CatalogoDetalle> getListaCatCiudades() {
        return listaCatCiudades;
    }
    
    public void setListaCatCiudades(List<CatalogoDetalle> listaCatCiudades) {
        this.listaCatCiudades = listaCatCiudades;
    }

    public List<CatalogoDetalle> getListaCatTipoPersonaJuridica() {
        return listaCatTipoPersonaJuridica;
    }

    public void setListaCatTipoPersonaJuridica(List<CatalogoDetalle> listaCatTipoPersonaJuridica) {
        this.listaCatTipoPersonaJuridica = listaCatTipoPersonaJuridica;
    }
    
    
    
}
