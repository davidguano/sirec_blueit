/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.facade.CatastroPredialFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Darwin
 */
@Stateless
@LocalBean
public class CatastroPredialServicio {
    @EJB
    private CatastroPredialFacade catastroPredialDao;
     private String ENTIDAD_CATASTRO = "CatastroPredial";
    public List<CatastroPredial> listarClaveCatastral() throws Exception{
    return catastroPredialDao.listarTodos();
    }
    public CatastroPredial cargarObjetoCatastro(int codCatastro) throws Exception{
        return catastroPredialDao.buscarPorCampo(ENTIDAD_CATASTRO, "catpreCodigo", codCatastro);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
