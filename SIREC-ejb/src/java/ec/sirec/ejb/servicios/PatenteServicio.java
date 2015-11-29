/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.Patente;
import ec.sirec.ejb.facade.PatenteFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author Darwin
 */
@Stateless
@LocalBean
public class PatenteServicio {

    @EJB
    private PatenteFacade patenteDao;
    private String ENTIDAD_AGENCIA = "Patente";

    public boolean existeCodigoPatente(int codPatente) throws Exception {
        return patenteDao.existePorCampo(ENTIDAD_AGENCIA, "patCodigo", codPatente);
    }

    public String crearPatente(Patente codPatente) throws Exception {
        patenteDao.crear(codPatente);
        return "se ha creado la patente" + codPatente;
    }

    public String editarPatente(Patente codPatente) throws Exception {
        patenteDao.editar(codPatente);
        return "se ha modificado la patente" + codPatente;
    }

    public String eliminarPatente(Patente codPatente) throws Exception {
        patenteDao.eliminarGenerico(ENTIDAD_AGENCIA, "ageCodigo", codPatente.getPatCodigo());
        return "se ha eliminado la patente" + codPatente;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
