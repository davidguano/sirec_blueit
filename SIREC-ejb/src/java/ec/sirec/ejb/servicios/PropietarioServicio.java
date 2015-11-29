/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.facade.PropietarioFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author DAVID GUAN
 */
@Stateless
@LocalBean
public class PropietarioServicio {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private PropietarioFacade propietarioDao;
    private final String ENTIDAD_PROPIETARIO="Propietario";
    
    @EJB
    private CatalogoDetalleServicio catalogoDetServicio;
    
    public void crearPropietario(Propietario vpropietario) throws Exception{
        propietarioDao.crear(vpropietario);
    }
    public void editarPropietario(Propietario vpropietario) throws Exception{
        propietarioDao.editar(vpropietario);
    }
    public List<Propietario> listarPropietariosTodos() throws Exception{
        return propietarioDao.listarOrdenada(ENTIDAD_PROPIETARIO , "proCi", "asc");
    }
    public List<CatalogoDetalle> listarCiudades() throws Exception{
        return catalogoDetServicio.listarPorNemonicoCatalogo("CIUDADES");
    }
}
