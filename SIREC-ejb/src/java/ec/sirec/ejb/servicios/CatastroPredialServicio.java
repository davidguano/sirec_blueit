/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialAreas;
import ec.sirec.ejb.entidades.CatastroPredialEdificacion;
import ec.sirec.ejb.entidades.CatastroPredialInfraestructura;
import ec.sirec.ejb.entidades.CatastroPredialUsosuelo;
import ec.sirec.ejb.entidades.CatastroPredialValoracion;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.entidades.PropietarioPredio;
import ec.sirec.ejb.facade.CatastroPredialAreasFacade;
import ec.sirec.ejb.facade.CatastroPredialEdificacionFacade;
import ec.sirec.ejb.facade.CatastroPredialFacade;
import ec.sirec.ejb.facade.CatastroPredialInfraestructuraFacade;
import ec.sirec.ejb.facade.CatastroPredialUsosueloFacade;
import java.util.ArrayList;
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
    @EJB
    private PropietarioServicio propietarioServicio;
    @EJB
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private CatastroPredialAreasFacade catastroPredialAreasDao;
    @EJB
    private CatastroPredialInfraestructuraFacade catastroPredialInfDao;
    @EJB
    private CatastroPredialUsosueloFacade catastroPredialUsoSueloDao;
    @EJB
    private CatastroPredialEdificacionFacade catastroPredialEdificacionDao;
    @EJB
    private CatastroPredialValoracionServicio valoracionPredioServicio;

    private final String ENTIDAD_CATASTRO = "CatastroPredial";

    public List<CatastroPredial> listarClaveCatastral() throws Exception {
        return catastroPredialDao.listarTodos();
    }

    public CatastroPredial cargarObjetoCatastro(int codCatastro) throws Exception {
        return catastroPredialDao.buscarPorCampo(ENTIDAD_CATASTRO, "catpreCodigo", codCatastro);
    }

    public CatastroPredial buscarCatastroPorCodigosClave(String vcodigoNacional, String vcodigoLocal) throws Exception {
        return catastroPredialDao.buscarPor2Campos(ENTIDAD_CATASTRO, "catpreCodNacional", vcodigoNacional, "catpreCodLocal", vcodigoLocal);
    }
    public List<CatastroPredial> listarCatastroPorCedulaPropietario(String vcedula) throws Exception {
        List<CatastroPredial> lstC=new ArrayList<CatastroPredial>();
        List<PropietarioPredio> lstPP= propietarioServicio.listarPropietariosPredioPorCedulaPropietario(vcedula);
        if(!lstPP.isEmpty()){
            for(PropietarioPredio pp:lstPP){
                lstC.add(pp.getCatpreCodigo());
            }
        }
        return lstC;
    }

    public boolean existeCatastroPorCodigosClave(String vcodigoNacional, String vcodigoLocal) throws Exception {
        return catastroPredialDao.existePor2Campos(ENTIDAD_CATASTRO, "catpreCodNacional", vcodigoNacional, "catpreCodLocal", vcodigoLocal);
    }

    public Propietario buscarPropietarioPorCi(String vci) throws Exception {
        return propietarioServicio.buscarPropietario(vci);
    }

    public void guardarCatastroPredial(CatastroPredial vcatastro) throws Exception {
        controlDetCatalogosNulos(vcatastro);
        catastroPredialDao.crear(vcatastro);
    }

    public CatastroPredialValoracion obtenerValoracionPredio(CatastroPredial vcatastro) throws Exception {
        CatastroPredialValoracion val = valoracionPredioServicio.buscarPorCatastroPredial(vcatastro);
        if (val == null) {
            return new CatastroPredialValoracion();
        } else {
            return val;
        }
    }

    public void controlDetCatalogosNulos(CatastroPredial vcatastro) {
        if (vcatastro.getCatdetDominio().getCatdetCodigo() == null) {
            vcatastro.setCatdetDominio(null);
        }
        if (vcatastro.getCatdetTraslacionDominio().getCatdetCodigo() == null) {
            vcatastro.setCatdetTraslacionDominio(null);
        }
        if (vcatastro.getCatdetOcupacion() != null) {
            if (vcatastro.getCatdetOcupacion().getCatdetCodigo() == null) {
                vcatastro.setCatdetOcupacion(null);
            }
        }
        if (vcatastro.getCatdetNoEdificado() != null) {
            if (vcatastro.getCatdetNoEdificado().getCatdetCodigo() == null) {
                vcatastro.setCatdetNoEdificado(null);
            }
        }
        if (vcatastro.getCatdetEnConstruccion() != null) {
            if (vcatastro.getCatdetEnConstruccion().getCatdetCodigo() == null) {
                vcatastro.setCatdetEnConstruccion(null);
            }
        }
        if (vcatastro.getCatdetCaracteristicasSuelo() != null) {
            if (vcatastro.getCatdetCaracteristicasSuelo().getCatdetCodigo() == null) {
                vcatastro.setCatdetCaracteristicasSuelo(null);
            }
        }
        if (vcatastro.getCatdetForma() != null) {
            if (vcatastro.getCatdetForma().getCatdetCodigo() == null) {
                vcatastro.setCatdetForma(null);
            }
        }
        if (vcatastro.getCatdetTopografia() != null) {
            if (vcatastro.getCatdetTopografia().getCatdetCodigo() == null) {
                vcatastro.setCatdetTopografia(null);
            }
        }
        if (vcatastro.getCatdetLocalizacion() != null) {
            if (vcatastro.getCatdetLocalizacion().getCatdetCodigo() == null) {
                vcatastro.setCatdetLocalizacion(null);
            }
        }
        if (vcatastro.getCatdetTipoNegocio() != null) {
            if (vcatastro.getCatdetTipoNegocio().getCatdetCodigo() == null) {
                vcatastro.setCatdetTipoNegocio(null);
            }
        }
        if (vcatastro.getCatdetTipoFuncNegocio() != null) {
            if (vcatastro.getCatdetTipoFuncNegocio().getCatdetCodigo() == null) {
                vcatastro.setCatdetTipoFuncNegocio(null);
            }
        }
        if (vcatastro.getCatdetTipoFuncNegocio() != null) {
            if (vcatastro.getCatdetTipoFuncNegocio().getCatdetCodigo() == null) {
                vcatastro.setCatdetTipoFuncNegocio(null);
            }
        }

        if (vcatastro.getCatdetAlicuotas() != null) {
            if (vcatastro.getCatdetAlicuotas().getCatdetCodigo() == null) {
                vcatastro.setCatdetAlicuotas(null);
            }
        }
        if (vcatastro.getCatdetDimension() != null) {
            if (vcatastro.getCatdetDimension().getCatdetCodigo() == null) {
                vcatastro.setCatdetDimension(null);
            }
        }

        if (vcatastro.getCatdetFuenteInformacion() != null) {
            if (vcatastro.getCatdetFuenteInformacion().getCatdetCodigo() == null) {
                vcatastro.setCatdetFuenteInformacion(null);
            }
        }
        if (vcatastro.getCatdetDocRelevamiento() != null) {
            if (vcatastro.getCatdetDocRelevamiento().getCatdetCodigo() == null) {
                vcatastro.setCatdetDocRelevamiento(null);
            }
        }
        if (vcatastro.getCatdetParroquia() != null) {
            if (vcatastro.getCatdetParroquia().getCatdetCodigo() == null) {
                vcatastro.setCatdetParroquia(null);
            }
        }
        if (vcatastro.getCatdetSector() != null) {
            if (vcatastro.getCatdetSector().getCatdetCodigo() == null) {
                vcatastro.setCatdetSector(null);
            }
        }
    }

    public void editarCatastroPredial(CatastroPredial vcatastro) throws Exception {
        controlDetCatalogosNulos(vcatastro);
        catastroPredialDao.editar(vcatastro);
    }

    //PROPIETARIOS:
    public void cargarListaPropietariosPredio(CatastroPredial catPred) throws Exception {
        if (catPred != null) {
            if (catPred.getCatpreCodigo() != null) {
                List<PropietarioPredio> lstPP = propietarioServicio.listarPropietariosPredio(catPred.getCatpreCodigo());
                if (!lstPP.isEmpty()) {
                    catPred.setListaPropietariosPredio(lstPP);
                }
            }
        }
    }

    public void guardarPropietarioPredio(PropietarioPredio vPP) throws Exception {
        propietarioServicio.guardarPropietarioPredio(vPP);
    }

    public void eliminarPropietarioPredio(PropietarioPredio vPP) throws Exception {
        propietarioServicio.eliminarPropietarioPredio(vPP);
    }

    public Propietario obtenerPropietarioPrincipalPredio(Integer idCatastroPre) throws Exception {
        return propietarioServicio.obtenerPropietarioPrincipalPredio(idCatastroPre);
    }

    //INFRAESTRUCTURA
    /*public void guardarItemsInfraestructura(CatastroPredial vcatastro, int vitem, List<CatastroPredialInfraestructura> lstItems) throws Exception {
     if (!lstItems.isEmpty()) {
     for (CatastroPredialInfraestructura inf : lstItems) {
     inf.setCatpreCodigo(vcatastro);
     inf.setCatpreinfItem(vitem);
     catastroPredialInfDao.crear(inf);
     }
     }
     }*/
    public void guardarItemsInfraestructura(CatastroPredial vcatastro, int vitem, List<CatalogoDetalle> lstDets) throws Exception {
        if (!lstDets.isEmpty()) {
            if (catastroPredialInfDao.existePor2Campos("CatastroPredialInfraestructura", "catpreCodigo", vcatastro, "catpreinfItem", vitem)) {
                catastroPredialInfDao.eliminarPor2Campos("CatastroPredialInfraestructura", "catpreCodigo", vcatastro, "catpreinfItem", vitem);
            }
            for (CatalogoDetalle det : lstDets) {
                CatastroPredialInfraestructura inf = new CatastroPredialInfraestructura();
                inf.setCatpreCodigo(vcatastro);
                inf.setCatpreinfItem(vitem);
                inf.setCatdetCodigo(det);
                catastroPredialInfDao.crear(inf);
            }
        }
    }

    public List<CatalogoDetalle> listarInfraestructuraPorCatastroItemSeleccionados(CatastroPredial vcatastro, int vitem) throws Exception {

        List<CatalogoDetalle> lstCat = new ArrayList<CatalogoDetalle>();
        List<CatastroPredialInfraestructura> lstInf = new ArrayList<CatastroPredialInfraestructura>();
        lstInf = catastroPredialInfDao.listarPor2CamposOrdenada("CatastroPredialInfraestructura", "catpreCodigo", vcatastro, "catpreinfItem", vitem, "catdetCodigo.catdetOrden", "asc");
        if (!lstInf.isEmpty()) {
            for (CatastroPredialInfraestructura inf : lstInf) {
                lstCat.add(inf.getCatdetCodigo());
            }
        }
        return lstCat;
    }

    public List<CatalogoDetalle> listarInfServicios() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_SERVICIOS");
    }

    public List<CatalogoDetalle> listarInfAlcantarillado1() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_ALCANTARILLADO1");
    }

    public List<CatalogoDetalle> listarInfUso() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_USO");
    }

    public List<CatalogoDetalle> listarInfMaterial() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_MATERIAL");
    }

    public List<CatalogoDetalle> listarInfSentido() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_SENTIDO");
    }

    public List<CatalogoDetalle> listarInfEnergiaElect() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_ENERGIA_ELEC");
    }

    public List<CatalogoDetalle> listarInfAbasAgua() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_ABAS_AGUA");
    }

    public List<CatalogoDetalle> listarInfAlcantarillado2() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_ALCANTARILLADO2");
    }

    public List<CatalogoDetalle> listarInfOtrosServicios() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("INF_OTROS_SERV");
    }

    //USO DE SUELO
    public void crearRegistrosUsoSueloCatastro(CatastroPredial vcatastro) throws Exception {
        List<CatalogoDetalle> lstDets = new ArrayList<CatalogoDetalle>();
        lstDets = catalogoDetalleServicio.listarPorNemonicoCatalogo("USO_SUELO");
        for (CatalogoDetalle det : lstDets) {
            CatastroPredialUsosuelo us = new CatastroPredialUsosuelo();
            us.setCatdetCodigo(det);
            us.setCatpreCodigo(vcatastro);
            us.setCatpreusuItem(det.getCatdetOrden());
            catastroPredialUsoSueloDao.crear(us);
        }
    }

    public List<CatastroPredialUsosuelo> listarRegistrosUsuSueloPorCatastro(CatastroPredial vcatastro) throws Exception {
        return catastroPredialUsoSueloDao.listarPorCampoOrdenada("CatastroPredialUsosuelo", "catpreCodigo", vcatastro, "catpreusuItem", "asc");
    }
    public List<CatastroPredialUsosuelo> listarRegistrosUsuSueloPorCatastroySubgrupo(CatastroPredial vcatastro, String vsubgrupo) throws Exception {
        return catastroPredialUsoSueloDao.listarPor1Campo1IniciaOrdenada("CatastroPredialUsosuelo", "catpreCodigo", vcatastro, "catdetCodigo.catdetCod",vsubgrupo,"catpreusuItem", "asc");
    }

    public void editarRegistroUsuSuelo(List<CatastroPredialUsosuelo> lstUsosuelo) throws Exception {
        for (CatastroPredialUsosuelo vusosuelo : lstUsosuelo) {
            if (vusosuelo.getCatpreusuAplica()) {
                catastroPredialUsoSueloDao.editar(vusosuelo);
            }
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    //AREAS BLOQUE
    public void guardarAreaBloque(CatastroPredial vcatastro, CatastroPredialAreas vcatPreArea) throws Exception {
        vcatPreArea.setCatpreCodigo(vcatastro);
        catastroPredialAreasDao.crear(vcatPreArea);
    }

    public List<CatastroPredialAreas> listarAreasPorCatastro(Integer vidCatPred) throws Exception {
        return catastroPredialAreasDao.listarPorCampoOrdenada("CatastroPredialAreas", "catpreCodigo.catpreCodigo", vidCatPred, "catpreareBloque", "asc");
    }

    public void editarAreaBloque(CatastroPredialAreas vareaBloque) throws Exception {
        catastroPredialAreasDao.editar(vareaBloque);
    }

    public void eliminarAreaBloque(CatastroPredialAreas vareaBloque) throws Exception {
        catastroPredialAreasDao.eliminar(vareaBloque);
    }

    //EDIFICACIONES
    public void crearRegistrosEdificacionesPorNumBloquesYPisos(CatastroPredial vcatastro) throws Exception {

        int numBloques = vcatastro.getCatpreNumBloques();
        int numPisos = vcatastro.getCatpreNumPisos();
        // 1 (4 items)

        for (int i = 1; i <= 4; i++) {
            for (int b = 1; b <= numBloques; b++) {
                for (int p = 1; p <= numPisos; p++) {
                    CatastroPredialEdificacion edif = new CatastroPredialEdificacion();
                    edif.setCatpreCodigo(vcatastro);
                    edif.setCatpreediGrupo("1");
                    edif.setCatpreediSubgrupo(String.valueOf(i));
                    edif.setCatpreediBloque("" + b);
                    edif.setCatpreediPiso("" + p);
                    catastroPredialEdificacionDao.crear(edif);
                }
            }
        }
        // 2 (6 items)
        for (int i = 1; i <= 6; i++) {
            for (int b = 1; b <= numBloques; b++) {
                for (int p = 1; p <= numPisos; p++) {
                    CatastroPredialEdificacion edif = new CatastroPredialEdificacion();
                    edif.setCatpreCodigo(vcatastro);
                    edif.setCatpreediGrupo("2");
                    edif.setCatpreediSubgrupo(String.valueOf(i));
                    edif.setCatpreediBloque("" + b);
                    edif.setCatpreediPiso("" + p);
                    catastroPredialEdificacionDao.crear(edif);
                }
            }
        }
        // 3 (10 items)
        for (int i = 1; i <= 10; i++) {
            for (int b = 1; b <= numBloques; b++) {
                for (int p = 1; p <= numPisos; p++) {
                    CatastroPredialEdificacion edif = new CatastroPredialEdificacion();
                    edif.setCatpreCodigo(vcatastro);
                    edif.setCatpreediGrupo("3");
                    edif.setCatpreediSubgrupo(String.valueOf(i));
                    edif.setCatpreediBloque("" + b);
                    edif.setCatpreediPiso("" + p);
                    catastroPredialEdificacionDao.crear(edif);
                }
            }
        }
        // 4 (3 items)
        for (int i = 1; i <= 3; i++) {
            for (int b = 1; b <= numBloques; b++) {
                for (int p = 1; p <= numPisos; p++) {
                    CatastroPredialEdificacion edif = new CatastroPredialEdificacion();
                    edif.setCatpreCodigo(vcatastro);
                    edif.setCatpreediGrupo("4");
                    edif.setCatpreediSubgrupo(String.valueOf(i));
                    edif.setCatpreediBloque("" + b);
                    edif.setCatpreediPiso("" + p);
                    catastroPredialEdificacionDao.crear(edif);
                }
            }
        }
        // 5 (9 items) sin bloques y pisos
        for (int i = 1; i <= 9; i++) {
            CatastroPredialEdificacion edif = new CatastroPredialEdificacion();
            edif.setCatpreCodigo(vcatastro);
            edif.setCatpreediGrupo("5");
            edif.setCatpreediSubgrupo(String.valueOf(i));
            edif.setCatpreediBloque("0");
            edif.setCatpreediPiso("0");
            catastroPredialEdificacionDao.crear(edif);
        }
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_1(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "1", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_2(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "2", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_3(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "3", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_4(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "4", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo234(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        List<CatastroPredialEdificacion> lst = new ArrayList<CatastroPredialEdificacion>();
        lst.addAll(catastroPredialEdificacionDao.listarPor4CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "2", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor4CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "3", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor4CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "4", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));

        return lst;
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        List<CatastroPredialEdificacion> lst = new ArrayList<CatastroPredialEdificacion>();
        lst.addAll(catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "1", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "2", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "3", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "4", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc"));

        return lst;
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_5(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "5", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_6(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "6", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_7(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "7", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_8(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "8", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_9(CatastroPredial vcatastro, String bloque, String piso) throws Exception {
        return catastroPredialEdificacionDao.listarPor5CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "9", "catpreediBloque",bloque,"catpreediPiso",piso,"catpreediCodigo", "asc");
    }

    public void editarCatastroPredEdificacion(List<CatastroPredialEdificacion> lstEdif) throws Exception {
        for (CatastroPredialEdificacion edif : lstEdif) {
            catastroPredialEdificacionDao.editar(edif);
        }
    }

    //EXTRACCION DE CATALOGOS
    public List<CatalogoDetalle> listaCatParroquias() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("PARROQUIAS");
    }

    public List<CatalogoDetalle> listaCatSectores() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("SECTORES");
    }

    public List<CatalogoDetalle> listaCatTipoVia() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_VIA");
    }

    public List<CatalogoDetalle> listaCatTipoUbicacion() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_UBICACION");
    }

    public List<CatalogoDetalle> listaCatTipoProp1() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_PROP1");
    }

    public List<CatalogoDetalle> listaCatTipoProp2() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_PROP2");
    }

    public List<CatalogoDetalle> listaCatRefCartograficas() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("REF_CARTOGRAFICA");
    }

    public List<CatalogoDetalle> listaTenenciaDominio() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("DOMINIO");
    }

    public List<CatalogoDetalle> listaTenenciaTraslacionDominio() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TRAS_DOMINIO");
    }

    public List<CatalogoDetalle> listaTerrenoOcupacion() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_OCUPACION");
    }

    public List<CatalogoDetalle> listaTerrenoNoEdificado() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_NO_EDIFICADO");
    }

    public List<CatalogoDetalle> listaTerrenoConstruccion() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_EN_CONS");
    }

    public List<CatalogoDetalle> listaTerrenoCaracteristicasSuelo() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_CAR_SUELO");
    }

    public List<CatalogoDetalle> listaTerrenoForma() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_FORMA");
    }

    public List<CatalogoDetalle> listaTerrenoTopografia() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_TOPOGRAFIA");
    }

    public List<CatalogoDetalle> listaTerrenoLocalizacion() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TERR_LOCALIZACION");
    }

    public List<CatalogoDetalle> listaUsoSueloTipoNegocio() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("USO_SUELO_TIPO_NEG");
    }

    public List<CatalogoDetalle> listaUsoSueloTiempoFuncionamiento() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("USO_SUELO_T_FUNC");
    }

    public List<CatalogoDetalle> listaOtraInfoDimensiones() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("OTRO_DIMEN");
    }

    public List<CatalogoDetalle> listaOtraInfoAlicuota() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("OTRO_ALICUOTA");
    }

    public List<CatalogoDetalle> listaOtraInfoFuenteInf() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("OTRO_FUENTE");
    }

    public List<CatalogoDetalle> listaTipoDocRelevamiento() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_DOCREL");
    }

    public List<CatalogoDetalle> listaOpcionesEdificacion(String vnum) throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("EDIF_" + vnum);
    }

    public List<CatalogoDetalle> listaOpcionesEdificacionConLista(String vnum, String lista) throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoyItemsIn("EDIF_" + vnum, lista);
    }

    public List<CatastroPredial> listarCatastroXCodigo(Integer codigo) throws Exception {
        return catastroPredialDao.listarPorCampoOrdenada(ENTIDAD_CATASTRO, "catpreCodigo", codigo, "catpreCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_SubGrupo2(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "2", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_SubGrupo3(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "3", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo2(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor2CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "2", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo3(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor2CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "3", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo4(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor2CamposOrdenadaMenosSanitaria("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "4", "catpreediCodigo", "asc");
    }
        
    public List<CatalogoDetalle> listarTipoDeTarifa() throws Exception {
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("TIPO_TARIF");
    }
    public CatalogoDetalle cargarObjetoCatalogoDetalle(int codCatDet) throws Exception {
        return catalogoDetalleServicio.buscarPorCodigoCatDet(codCatDet);
    }
}
