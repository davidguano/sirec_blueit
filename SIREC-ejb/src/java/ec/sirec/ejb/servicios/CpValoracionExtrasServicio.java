/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.AdicionalesDeductivos;
import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CpValoracionExtras;
import ec.sirec.ejb.entidades.SegPermiso;
import ec.sirec.ejb.facade.AdicionalesDeductivosFacade;
import ec.sirec.ejb.facade.CatalogoDetalleFacade;
import ec.sirec.ejb.facade.CpValoracionExtrasFacade;
import ec.sirec.ejb.facade.SegPermisoFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;

/**
 *
 * @author vespinoza
 */
@Stateless
@LocalBean
public class CpValoracionExtrasServicio {

    @EJB
    private CpValoracionExtrasFacade cpValoracionExtrasDao;
    private String ENTIDAD_VALORACION_EXTRAS = "CpValoracionExtras";
    
    
     public String crearCpValoracionExtras(CpValoracionExtras cpValoracionExtras) throws Exception {
        cpValoracionExtrasDao.crear(cpValoracionExtras);
        return "Se ha creado el permiso" + cpValoracionExtras.getCpvalextCodigo();
    }

    public String editarCpValoracionExtras(CpValoracionExtras cpValoracionExtras) throws Exception {
        cpValoracionExtrasDao.editar(cpValoracionExtras);
        return "Se ha modificado el permiso" + cpValoracionExtras.getCpvalextCodigo();
    }
    
    public List<CpValoracionExtras> listarPermisos() throws Exception {
        return cpValoracionExtrasDao.listarTodos();
    }

//    public List<CpValoracionExtras> listarCatalogosPorNemonico(String tipo) throws Exception {
//     return cpValoracionExtrasDao.listarPorCampoOrdenada(nemonico, "catdetOrden", "asc");
//    }
    
    
//    public List<AdicionalesDeductivos> listarAdicionalesDeducibles() throws Exception {
//        return adicionalesDeductivosDao.listarTodos();
//    }
//
//    public List<AdicionalesDeductivos> listarCatalogosPorNemonico(String nemonico) throws Exception {
//     return adicionalesDeductivosDao.listarCatalogosPorNemonico(nemonico, "catdetOrden", "asc");
//    }
    
 
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
