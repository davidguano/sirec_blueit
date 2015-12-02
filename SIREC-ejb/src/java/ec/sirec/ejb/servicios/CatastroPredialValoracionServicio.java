/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.CatastroPredialValoracion;
import ec.sirec.ejb.facade.CatastroPredialValoracionFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author vespinoza
 */
@Stateless
@LocalBean
public class CatastroPredialValoracionServicio {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private CatastroPredialValoracionFacade catastroPredialValoracionDao;
    private final String CATASTRO_PREDIAL_VALORACION="CatastroPredialValoracion";
    
    public String crearAplicacion(CatastroPredialValoracion catastroPredialValoracion) throws Exception {
        catastroPredialValoracionDao.crear(catastroPredialValoracion);
        return "Se ha creado la aplicación" + catastroPredialValoracion.getCatpreCodigo();
    }

    public String editarAplicacion(CatastroPredialValoracion catastroPredialValoracion) throws Exception {
        catastroPredialValoracionDao.editar(catastroPredialValoracion);
        return "Se ha modificado la aplicación" + catastroPredialValoracion.getCatpreCodigo();
    }
}
