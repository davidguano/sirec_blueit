/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.servicios;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialAreas;
import ec.sirec.ejb.entidades.CatastroPredialInfraestructura;
import ec.sirec.ejb.entidades.CatastroPredialUsosuelo;
import ec.sirec.ejb.facade.CatastroPredialAreasFacade;
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
    private CatalogoDetalleServicio catalogoDetalleServicio;
    @EJB
    private CatastroPredialAreasFacade catastroPredialAreasDao;
    @EJB
    private CatastroPredialInfraestructuraFacade catastroPredialInfDao;
    @EJB
    private CatastroPredialUsosueloFacade catastroPredialUsoSueloDao;

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

    public void guardarCatastroPredial(CatastroPredial vcatastro) throws Exception {
        catastroPredialDao.crear(vcatastro);
    }

    public void editarCatastroPredial(CatastroPredial vcatastro) throws Exception {
        catastroPredialDao.editar(vcatastro);
    }
    
    //INFRAESTRUCTURA
    public void guardarItemsInfraestructura(CatastroPredial vcatastro,int vitem, List<CatastroPredialInfraestructura> lstItems) throws Exception{
        if(!lstItems.isEmpty()){
            for(CatastroPredialInfraestructura inf: lstItems){
                inf.setCatpreCodigo(vcatastro);
                inf.setCatpreinfItem(vitem);
                catastroPredialInfDao.crear(inf);
            }
        }
    }
    public List<CatastroPredialInfraestructura> listarInfraestructuraPorCatastroItemSeleccionados(CatastroPredial vcatastro,int vitem)throws Exception{
        return catastroPredialInfDao.listarPor2CamposOrdenada("CatastroPredialInfraestructura", "catpreCodigo", vcatastro, "catpreinfItem", vitem, "catdetCodigo.catdetOrden", "asc");
    }
    public List<CatastroPredialInfraestructura> listarInfraestructuraPorCatastroItemIniciales(String vnemonicoCatDetalle, int item)throws Exception{
        List<CatalogoDetalle> lstDets=new ArrayList<CatalogoDetalle>();
        lstDets=catalogoDetalleServicio.listarPorNemonicoCatalogo(vnemonicoCatDetalle);
        List<CatastroPredialInfraestructura> lstInf=new ArrayList<CatastroPredialInfraestructura>();
        for(CatalogoDetalle det:lstDets){
            CatastroPredialInfraestructura inf=new CatastroPredialInfraestructura();
            inf.setCatpreinfItem(item);
            inf.setCatdetCodigo(det);
            lstInf.add(inf);
        }
        return lstInf;
    }
    public List<CatastroPredialInfraestructura> listarInfServicios() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_SERVICIOS", 1);
    }
    public List<CatastroPredialInfraestructura> listarInfAlcantarillado1() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_ALCANTARILLADO1", 3);
    }
    public List<CatastroPredialInfraestructura> listarInfUso() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_USO", 11);
    }
    public List<CatastroPredialInfraestructura> listarInfMaterial() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_MATERIAL", 12);
    }
    public List<CatastroPredialInfraestructura> listarInfSentido() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_SENTIDO", 14);
    }
    public List<CatastroPredialInfraestructura> listarInfEnergiaElect() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_ENERGIA_ELEC", 2);
    }
    public List<CatastroPredialInfraestructura> listarInfAbasAgua() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_ABAS_AGUA", 3);
    }
    public List<CatastroPredialInfraestructura> listarInfAlcantarillado2() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_ALCANTARILLADO2", 4);
    }
    public List<CatastroPredialInfraestructura> listarInfOtrosServicios() throws Exception{
        return listarInfraestructuraPorCatastroItemIniciales("INF_OTROS_SERV", 5);
    }
    
    public void editarItemsInfraestructura(List<CatastroPredialInfraestructura> lstItems) throws Exception{
        if(!lstItems.isEmpty()){
            for(CatastroPredialInfraestructura inf: lstItems){
                catastroPredialInfDao.editar(inf);
            }
        }
    }
    
    //USO DE SUELO
    public void crearRegistrosUsoSueloCatastro(CatastroPredial vcatastro) throws Exception{
        List<CatalogoDetalle> lstDets=new ArrayList<CatalogoDetalle>();
        lstDets=catalogoDetalleServicio.listarPorNemonicoCatalogo("USO_SUELO");
        for(CatalogoDetalle det:lstDets){
            CatastroPredialUsosuelo us=new CatastroPredialUsosuelo();
            us.setCatdetCodigo(det);
            us.setCatpreCodigo(vcatastro);
            us.setCatpreusuItem(det.getCatdetCodigo());
            catastroPredialUsoSueloDao.crear(us);
        }
    }
    public List<CatastroPredialUsosuelo> listarRegistrosUsuSueloPorCatastro(CatastroPredial vcatastro) throws Exception{
        return catastroPredialUsoSueloDao.listarPorCampoOrdenada("CatastroPredialUsosuelo", "catpreCodigo", vcatastro, "catpreusuItem", "asc");
    }
    public void editarRegistroUsuSuelo(CatastroPredialUsosuelo vusosuelo) throws Exception{
        catastroPredialUsoSueloDao.editar(vusosuelo);
    }
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    //AREAS BLOQUE
    public void guardarAreaBloque(CatastroPredial vcatastro,CatastroPredialAreas vcatPreArea) throws Exception {
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
    
    public List<CatastroPredial> listarCatastroXCodigo(Integer codigo) throws Exception {
        return catastroPredialDao.listarPorCampoOrdenada(ENTIDAD_CATASTRO, "catpreCodigo", codigo, "catpreCodigo", "asc");
    }
}
