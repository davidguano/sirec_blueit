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
    }

    public void editarCatastroPredial(CatastroPredial vcatastro) throws Exception {
        controlDetCatalogosNulos(vcatastro);
        catastroPredialDao.editar(vcatastro);
    }
    
    //PROPIETARIOS:
    public void cargarListaPropietariosPredio(CatastroPredial catPred) throws Exception{
        if(catPred!=null){
            if(catPred.getCatpreCodigo()!=null){
                List<PropietarioPredio> lstPP=propietarioServicio.listarPropietariosPredio(catPred.getCatpreCodigo());
                if(!lstPP.isEmpty()){
                    catPred.setListaPropietariosPredio(lstPP);
                }
            }
        }
    }
    public void guardarPropietarioPredio(PropietarioPredio vPP) throws Exception{
         propietarioServicio.guardarPropietarioPredio(vPP);
     }
     public void eliminarPropietarioPredio(PropietarioPredio vPP) throws Exception{
         propietarioServicio.eliminarPropietarioPredio(vPP);
     }
     public Propietario obtenerPropietarioPrincipalPredio(Integer idCatastroPre) throws Exception{
         return propietarioServicio.obtenerPropietarioPrincipalPredio(idCatastroPre);
     }

    //INFRAESTRUCTURA
    public void guardarItemsInfraestructura(CatastroPredial vcatastro, int vitem, List<CatastroPredialInfraestructura> lstItems) throws Exception {
        if (!lstItems.isEmpty()) {
            for (CatastroPredialInfraestructura inf : lstItems) {
                inf.setCatpreCodigo(vcatastro);
                inf.setCatpreinfItem(vitem);
                catastroPredialInfDao.crear(inf);
            }
        }
    }

    public List<CatastroPredialInfraestructura> listarInfraestructuraPorCatastroItemSeleccionados(CatastroPredial vcatastro, int vitem) throws Exception {
        return catastroPredialInfDao.listarPor2CamposOrdenada("CatastroPredialInfraestructura", "catpreCodigo", vcatastro, "catpreinfItem", vitem, "catdetCodigo.catdetOrden", "asc");
    }

    public List<CatastroPredialInfraestructura> listarInfraestructuraPorCatastroItemIniciales(String vnemonicoCatDetalle, int item) throws Exception {
        List<CatalogoDetalle> lstDets = new ArrayList<CatalogoDetalle>();
        lstDets = catalogoDetalleServicio.listarPorNemonicoCatalogo(vnemonicoCatDetalle);
        List<CatastroPredialInfraestructura> lstInf = new ArrayList<CatastroPredialInfraestructura>();
        for (CatalogoDetalle det : lstDets) {
            CatastroPredialInfraestructura inf = new CatastroPredialInfraestructura();
            inf.setCatpreinfItem(item);
            inf.setCatdetCodigo(det);
            lstInf.add(inf);
        }
        return lstInf;
    }

    public List<CatastroPredialInfraestructura> listarInfServicios() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_SERVICIOS", 1);
    }

    public List<CatastroPredialInfraestructura> listarInfAlcantarillado1() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_ALCANTARILLADO1", 3);
    }

    public List<CatastroPredialInfraestructura> listarInfUso() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_USO", 11);
    }

    public List<CatastroPredialInfraestructura> listarInfMaterial() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_MATERIAL", 12);
    }

    public List<CatastroPredialInfraestructura> listarInfSentido() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_SENTIDO", 14);
    }

    public List<CatastroPredialInfraestructura> listarInfEnergiaElect() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_ENERGIA_ELEC", 20);
    }

    public List<CatastroPredialInfraestructura> listarInfAbasAgua() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_ABAS_AGUA", 30);
    }

    public List<CatastroPredialInfraestructura> listarInfAlcantarillado2() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_ALCANTARILLADO2", 40);
    }

    public List<CatastroPredialInfraestructura> listarInfOtrosServicios() throws Exception {
        return listarInfraestructuraPorCatastroItemIniciales("INF_OTROS_SERV", 50);
    }

    public void editarItemsInfraestructura(List<CatastroPredialInfraestructura> lstItems) throws Exception {
        if (!lstItems.isEmpty()) {
            for (CatastroPredialInfraestructura inf : lstItems) {
                catastroPredialInfDao.editar(inf);
            }
        }
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

    public void editarRegistroUsuSuelo(CatastroPredialUsosuelo vusosuelo) throws Exception {
        catastroPredialUsoSueloDao.editar(vusosuelo);
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
    public void crearRegistrosEdificacionesPorNumBloquesYPisos(CatastroPredial vcatastro, int numBloques, int numPisos) throws Exception {
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

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_1(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "1", "catpreediSubgrupo", "1", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_2(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "2", "catpreediSubgrupo", "1", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_3(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "3", "catpreediSubgrupo", "1", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo1_4(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "4", "catpreediSubgrupo", "1", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo234(CatastroPredial vcatastro) throws Exception {
        List<CatastroPredialEdificacion> lst = new ArrayList<CatastroPredialEdificacion>();
        lst.addAll(catastroPredialEdificacionDao.listarPor2CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "2", "catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor2CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "3", "catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor2CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "4", "catpreediCodigo", "asc"));

        return lst;
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5(CatastroPredial vcatastro) throws Exception {
        List<CatastroPredialEdificacion> lst = new ArrayList<CatastroPredialEdificacion>();
        lst.addAll(catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "1", "catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "2", "catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "3", "catpreediCodigo", "asc"));
        lst.addAll(catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "4", "catpreediCodigo", "asc"));

        return lst;
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_5(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "5", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_6(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "6", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_7(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "7", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_8(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "8", "catpreediCodigo", "asc");
    }

    public List<CatastroPredialEdificacion> listarEdificacionesGrupo5_9(CatastroPredial vcatastro) throws Exception {
        return catastroPredialEdificacionDao.listarPor3CamposOrdenada("CatastroPredialEdificacion", "catpreCodigo", vcatastro, "catpreediGrupo", "5", "catpreediSubgrupo", "9", "catpreediCodigo", "asc");
    }

    public void editarCatastroPredEdificacion(CatastroPredialEdificacion vedif) throws Exception {
        catastroPredialEdificacionDao.editar(vedif);
    }

    //EXTRACCION DE CATALOGOS
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
        return catalogoDetalleServicio.listarPorNemonicoCatalogo("EDIF_"+vnum);
    }

    public List<CatastroPredial> listarCatastroXCodigo(Integer codigo) throws Exception {
        return catastroPredialDao.listarPorCampoOrdenada(ENTIDAD_CATASTRO, "catpreCodigo", codigo, "catpreCodigo", "asc");
    }

 
}
