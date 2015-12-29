/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.web.base;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.servicios.CatalogoDetalleServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author DAVID GUAN
 */
@ManagedBean
@ViewScoped
public class CatalogoDetControlador extends BaseControlador{
    
    private static final Logger LOGGER = Logger.getLogger(CatalogoDetControlador.class.getName());
    
    @EJB
    private CatalogoDetalleServicio catdetServicio;
    
    private List<CatalogoDetalle> listaCatDetallesEdificacion;
    /**
     * Creates a new instance of CatalogoDetControlador
     */
    public CatalogoDetControlador() {
    }
    
    public void inicializar(){
        try{
            listaCatDetallesEdificacion=new ArrayList<CatalogoDetalle>();
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,null,ex);
        }
    }
    public void listarCatEdificacion(){
        try{
            listaCatDetallesEdificacion=new ArrayList<CatalogoDetalle>();
            listaCatDetallesEdificacion=catdetServicio.listarPorCatalogosIn("28,29,30");
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,null,ex);
        }
    }
    
    public void onRowEdit(RowEditEvent event){
        try{
            catdetServicio.editarCatDetalle((CatalogoDetalle)event.getObject());
            addSuccessMessage("Valor editado correctamente");
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,null,ex);
        }
    }
    public void onRowCancel(RowEditEvent event){
        
    }

    public List<CatalogoDetalle> getListaCatDetallesEdificacion() {
        return listaCatDetallesEdificacion;
    }

    public void setListaCatDetallesEdificacion(List<CatalogoDetalle> listaCatDetallesEdificacion) {
        this.listaCatDetallesEdificacion = listaCatDetallesEdificacion;
    }
    
    
}
