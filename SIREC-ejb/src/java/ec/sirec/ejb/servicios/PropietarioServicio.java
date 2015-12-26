/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.entidades.PropietarioPredio;
import ec.sirec.ejb.facade.PropietarioFacade;
import ec.sirec.ejb.facade.PropietarioPredioFacade;
import ec.sirec.ejb.util.Utilitarios;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

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
    private final String ENTIDAD_PROPIETARIO = "Propietario";

    @EJB
    private PropietarioPredioFacade propietarioPredioDao;

    @EJB
    private CatalogoDetalleServicio catalogoDetServicio;

    public void crearPropietario(Propietario vpropietario) throws Exception {
        propietarioDao.crear(vpropietario);
    }

    public void editarPropietario(Propietario vpropietario) throws Exception {
        propietarioDao.editar(vpropietario);
    }

    public List<Propietario> listarPropietariosTodos() throws Exception {
        return propietarioDao.listarOrdenada(ENTIDAD_PROPIETARIO, "proCi", "asc");
    }

    public List<CatalogoDetalle> listarCiudades() throws Exception {
        return catalogoDetServicio.listarPorNemonicoCatalogo("CIUDADES");
    }

    public Propietario buscarPropietario(String cedula) throws Exception {
        return propietarioDao.buscarPorCampo(ENTIDAD_PROPIETARIO, "proCi", cedula);
    }

    public Propietario buscarPropietarioPorCatastro(int codCatastro) throws Exception {
        return propietarioDao.retornaObjPropietarioPorCatastroPred(codCatastro);
    }

    public List<PropietarioPredio> listarPropietariosPredio(Integer idCatastroPre) throws Exception {
        return propietarioPredioDao.listarPorCampoOrdenada("PropietarioPredio", "catpreCodigo.catpreCodigo", idCatastroPre, "propreCodigo", "asc");
    }

    public List<PropietarioPredio> listarPropietariosPredioPorPropietario(Propietario prop) throws Exception {
        return propietarioPredioDao.listarPorCampoOrdenada("PropietarioPredio", "proCi", prop, "propreCodigo", "asc");
    }

    public List<PropietarioPredio> listarPropietariosPredioPorCedulaPropietario(String cedulaProp) throws Exception {
        return propietarioPredioDao.listarPorCampoOrdenada("PropietarioPredio", "proCi.proCi", cedulaProp, "propreCodigo", "asc");
    }

    public Propietario obtenerPropietarioPrincipalPredio(Integer idCatastroPre) throws Exception {
        Propietario p = new Propietario();
        List<PropietarioPredio> lstpp = new ArrayList<PropietarioPredio>();
        lstpp = this.listarPropietariosPredio(idCatastroPre);
        if (!lstpp.isEmpty()) {
            p = lstpp.get(0).getProCi();
        }
        return p;
    }

    public void guardarPropietarioPredio(PropietarioPredio vPP) throws Exception {
        propietarioPredioDao.crear(vPP);
    }

    public void eliminarPropietarioPredio(PropietarioPredio vPP) throws Exception {
        propietarioPredioDao.eliminar(vPP);
    }

    public boolean esCedulaRucValida(String vcedula) throws Exception {
        String c = "";
        if (vcedula.length() == 13) {
            c = vcedula.substring(0, 10);
            if (!vcedula.substring(10, 13).equals("001")) {
                return false;
            }
        } else {
            c = vcedula;
        }
        return Utilitarios.validarCedula(c);
    }

    public List<CatalogoDetalle> listarCiudadesPorTexto(String texto) throws Exception {
        return catalogoDetServicio.listarPorNemonicoyTextoContiene("CIUDADES", texto);
    }

    public boolean esFechaNacimientoValida(Date vfechaNac) throws Exception {
        Calendar fn = java.util.Calendar.getInstance();
        fn.setTime(vfechaNac);
        Calendar fa = java.util.Calendar.getInstance();
        if ((fa.getTimeInMillis()-fn.getTimeInMillis())>360000) {
            return true;
        } else {
           return false;
        }
    }
}
