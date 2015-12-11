/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.web.base;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredial;
import ec.sirec.ejb.entidades.CatastroPredialAreas;
import ec.sirec.ejb.entidades.CatastroPredialEdificacion;
import ec.sirec.ejb.entidades.CatastroPredialInfraestructura;
import ec.sirec.ejb.entidades.CatastroPredialUsosuelo;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.servicios.CatastroPredialServicio;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author DAVID GUAN
 */
@ManagedBean
@ViewScoped
public class CatastroControlador extends BaseControlador {

    private static final Logger LOGGER = Logger.getLogger(CatastroControlador.class.getName());
    @EJB
    private CatastroPredialServicio catastroServicio;

    private CatastroPredial catastroPredialActual;
    private CatastroPredialAreas catastroPredialAreaBloqueActual;
    private List<CatastroPredialAreas> listaCatastroPredialAreasBloque;

    private List<CatastroPredialInfraestructura> listaInfServicios;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado1;
    private List<CatastroPredialInfraestructura> listaInfUso;
    private List<CatastroPredialInfraestructura> listaInfMaterial;
    private List<CatastroPredialInfraestructura> listaInfSentido;
    private List<CatastroPredialInfraestructura> listaInfEnergiaElec;
    private List<CatastroPredialInfraestructura> listaInfAbasAgua;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado2;
    private List<CatastroPredialInfraestructura> listaInfOtrosServicios;

    private List<CatastroPredialInfraestructura> listaInfServiciosSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado1Seleccionado;
    private List<CatastroPredialInfraestructura> listaInfUsoSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfMaterialSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfSentidoSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfEnergiaElecSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfAbasAguaSeleccionado;
    private List<CatastroPredialInfraestructura> listaInfAlcantarillado2Seleccionado;
    private List<CatastroPredialInfraestructura> listaInfOtrosServiciosSeleccionado;

    private List<CatastroPredialUsosuelo> listaUsuSuelo;

    private int numBloquesEdif;
    private int numPisosEdif;
    private List<CatastroPredialEdificacion> listaEdifGrupo1_1;
    private List<CatastroPredialEdificacion> listaEdifGrupo1_2;
    private List<CatastroPredialEdificacion> listaEdifGrupo1_3;
    private List<CatastroPredialEdificacion> listaEdifGrupo1_4;
    private List<CatastroPredialEdificacion> listaEdifGrupo234;
    private List<CatastroPredialEdificacion> listaEdifGrupo5_14;
    private List<CatastroPredialEdificacion> listaEdifGrupo5_5;
    private List<CatastroPredialEdificacion> listaEdifGrupo5_6;
    private List<CatastroPredialEdificacion> listaEdifGrupo5_7;
    private List<CatastroPredialEdificacion> listaEdifGrupo5_8;
    private List<CatastroPredialEdificacion> listaEdifGrupo5_9;
    private List<CatalogoDetalle> listaOpcEdifGrupo1_1;
    private List<CatalogoDetalle> listaOpcEdifGrupo1_3;
    private List<CatalogoDetalle> listaOpcEdifGrupo234;
    private List<CatalogoDetalle> listaOpcEdifGrupo5_5;
    private List<CatalogoDetalle> listaOpcEdifGrupo5_6;
    private List<CatalogoDetalle> listaOpcEdifGrupo5_7;
    private List<CatalogoDetalle> listaOpcEdifGrupo5_8;
    private List<CatalogoDetalle> listaOpcEdifGrupo5_9;

    private List<CatalogoDetalle> listaTipoVia;
    private List<CatalogoDetalle> listaTipoUbicacion;
    private List<CatalogoDetalle> listaTipoProp1;
    private List<CatalogoDetalle> listaTipoProp2;
    private List<CatalogoDetalle> listaRefCartograficas;
    private List<CatalogoDetalle> listaTenenciaDominio;
    private List<CatalogoDetalle> listaTenenciaTraslacionDominio;
    private List<CatalogoDetalle> listaTerrenoOcupacion;
    private List<CatalogoDetalle> listaTerrenoNoEdificado;
    private List<CatalogoDetalle> listaTerrenoConstruccion;
    private List<CatalogoDetalle> listaTerrenoCaracteristicasSuelo;
    private List<CatalogoDetalle> listaTerrenoForma;
    private List<CatalogoDetalle> listaTerrenoTopografia;
    private List<CatalogoDetalle> listaTerrenoLocalizacion;
    private List<CatalogoDetalle> listaUsoSueloTipoNegocio;
    private List<CatalogoDetalle> listaUsoSueloTiempoFuncionamiento;
    private List<CatalogoDetalle> listaOtraInfoDimensiones;
    private List<CatalogoDetalle> listaOtraInfoAlicuota;
    private List<CatalogoDetalle> listaOtraInfoFuenteInfo;
    private List<CatalogoDetalle> listaTipoDocRelevamiento;

    /**
     * Creates a new instance of CatastroControlador
     */
    public CatastroControlador() {
    }

    @PostConstruct
    public void inicializar() {
        try {
            catastroPredialActual = new CatastroPredial();
            catastroPredialAreaBloqueActual = new CatastroPredialAreas();
            listaCatastroPredialAreasBloque = new ArrayList<CatastroPredialAreas>();
            listaUsuSuelo = new ArrayList<CatastroPredialUsosuelo>();
            listaInfServiciosSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfAlcantarillado1Seleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfUsoSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfMaterialSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfSentidoSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfEnergiaElecSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfAbasAguaSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfAlcantarillado2Seleccionado = new ArrayList<CatastroPredialInfraestructura>();
            listaInfOtrosServiciosSeleccionado = new ArrayList<CatastroPredialInfraestructura>();
            cargarCatalogos();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarRegistroPrincipal() {
        try {

            catastroPredialActual.setUsuIdentificacion(obtenerUsuarioAutenticado());
            catastroPredialActual.setUltaccMarcatiempo(java.util.Calendar.getInstance().getTime());
            if (catastroPredialActual.getCatpreCodigo() == null) {
                String codNac = catastroPredialActual.getCatpreCodNacional();
                String codLoc = catastroPredialActual.getCatpreCodLocal();
                boolean b = false;
                if (codNac != null && codLoc != null) {
                    b = catastroServicio.existeCatastroPorCodigosClave(codNac, codLoc);
                }
                if (!b) {
                    catastroPredialActual.setUltaccDetalle("Se ha creado el registro");
                    catastroServicio.guardarCatastroPredial(catastroPredialActual);
                    crearRegistrosUsoSuelo();
                    addSuccessMessage("Correcto", "Ficha creada exitosamente");
                } else {
                    addErrorMessage("Clave ya existe, presione Buscar.");
                }
            } else {
                catastroPredialActual.setUltaccDetalle("Se ha editado el registro");
                catastroServicio.editarCatastroPredial(catastroPredialActual);
                addSuccessMessage("Correcto", "Ficha editada exitosamente");
            }

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearRegistrosUsoSuelo() {
        try {
            catastroServicio.crearRegistrosUsoSueloCatastro(catastroPredialActual);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowEdit(RowEditEvent event) {
        try {
            editarRegistroUsoSuelo((CatastroPredialUsosuelo) event.getObject());
            addSuccessMessage("Editado Correctamente");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void onRowCancel(RowEditEvent event) {

    }

    public void editarRegistroUsoSuelo(CatastroPredialUsosuelo vuso) {
        try {
            catastroServicio.editarRegistroUsuSuelo(vuso);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarRegistrodeArea() {
        try {
            if (catastroPredialAreaBloqueActual.getCatpreareBloque() != 0 && catastroPredialActual.getCatpreCodigo() != null) {
                catastroServicio.guardarAreaBloque(catastroPredialActual, catastroPredialAreaBloqueActual);
                listaCatastroPredialAreasBloque = catastroServicio.listarAreasPorCatastro(catastroPredialActual.getCatpreCodigo());
                catastroPredialAreaBloqueActual = new CatastroPredialAreas();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void guardarRegistrosInfraestructura() {
        try {
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 1, listaInfServiciosSeleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 3, listaInfAlcantarillado1Seleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 11, listaInfUsoSeleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 12, listaInfMaterialSeleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 14, listaInfSentidoSeleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 20, listaInfEnergiaElecSeleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 30, listaInfAbasAguaSeleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 40, listaInfAlcantarillado2Seleccionado);
            catastroServicio.guardarItemsInfraestructura(catastroPredialActual, 50, listaInfOtrosServiciosSeleccionado);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void crearRegistrosEdificaciones() {
        try {
            if (numBloquesEdif != 0 && numPisosEdif != 0) {
                catastroServicio.crearRegistrosEdificacionesPorNumBloquesYPisos(catastroPredialActual, numBloquesEdif, numPisosEdif);
                recuperarDatosdeEdificaciones();
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void editarRegistroEdificacion(CatastroPredialEdificacion vedif) {
        try {
            catastroServicio.editarCatastroPredEdificacion(vedif);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void recuperarDatosDeCatastro() {
        try {
            String codNac = catastroPredialActual.getCatpreCodNacional();
            String codLoc = catastroPredialActual.getCatpreCodLocal();
            if (codNac != null && codLoc != null) {
                catastroPredialActual = catastroServicio.buscarCatastroPorCodigosClave(codNac, codLoc);
                if (catastroPredialActual != null) {
                    listaCatastroPredialAreasBloque = catastroServicio.listarAreasPorCatastro(catastroPredialActual.getCatpreCodigo());
                    recuperarDatosDeCatastroInfraestructura();
                    listaUsuSuelo = catastroServicio.listarRegistrosUsuSueloPorCatastro(catastroPredialActual);
                    recuperarDatosdeEdificaciones();
                } else {
                    inicializar();
                    addErrorMessage("Error", "No existe registro con esta clave");
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerPropietario() {
        try {
            String ci = catastroPredialActual.getProCi().getProCi();
            if (!ci.isEmpty()) {
                Propietario prop = catastroServicio.buscarPropietarioPorCi(ci);
                if (prop != null) {
                    catastroPredialActual.setProCi(prop);
                }
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void recuperarDatosDeCatastroInfraestructura() {
        try {
            listaInfServiciosSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 1);
            listaInfAlcantarillado1Seleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 3);
            listaInfUsoSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 11);
            listaInfMaterialSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 12);
            listaInfSentidoSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 14);
            listaInfEnergiaElecSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 20);
            listaInfAbasAguaSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 30);
            listaInfAlcantarillado2Seleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 40);
            listaInfOtrosServiciosSeleccionado = catastroServicio.listarInfraestructuraPorCatastroItemSeleccionados(catastroPredialActual, 50);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void recuperarDatosdeEdificaciones() {
        try {
            listaEdifGrupo1_1 = catastroServicio.listarEdificacionesGrupo1_1(catastroPredialActual);
            listaEdifGrupo1_2 = catastroServicio.listarEdificacionesGrupo1_2(catastroPredialActual);
            listaEdifGrupo1_3 = catastroServicio.listarEdificacionesGrupo1_3(catastroPredialActual);
            listaEdifGrupo1_4 = catastroServicio.listarEdificacionesGrupo1_4(catastroPredialActual);
            listaEdifGrupo234 = catastroServicio.listarEdificacionesGrupo234(catastroPredialActual);
            listaEdifGrupo5_14 = catastroServicio.listarEdificacionesGrupo5(catastroPredialActual);
            listaEdifGrupo5_5 = catastroServicio.listarEdificacionesGrupo5_5(catastroPredialActual);
            listaEdifGrupo5_6 = catastroServicio.listarEdificacionesGrupo5_6(catastroPredialActual);
            listaEdifGrupo5_7 = catastroServicio.listarEdificacionesGrupo5_7(catastroPredialActual);
            listaEdifGrupo5_8 = catastroServicio.listarEdificacionesGrupo5_8(catastroPredialActual);
            listaEdifGrupo5_9 = catastroServicio.listarEdificacionesGrupo5_9(catastroPredialActual);

            listaOpcEdifGrupo1_1 = catastroServicio.listaOpcionesEdificacion("1_1");
            listaOpcEdifGrupo1_3 = catastroServicio.listaOpcionesEdificacion("1_3");
            listaOpcEdifGrupo234 = catastroServicio.listaOpcionesEdificacion("234");
            listaOpcEdifGrupo5_5 = catastroServicio.listaOpcionesEdificacion("5_5");
            listaOpcEdifGrupo5_6 = catastroServicio.listaOpcionesEdificacion("5_6");
            listaOpcEdifGrupo5_7 = catastroServicio.listaOpcionesEdificacion("5_7");
            listaOpcEdifGrupo5_8 = catastroServicio.listaOpcionesEdificacion("5_8");
            listaOpcEdifGrupo5_9 = catastroServicio.listaOpcionesEdificacion("5_9");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public void cargarCatalogos() {
        try {
            listaTipoVia = catastroServicio.listaCatTipoVia();
            listaTipoUbicacion = catastroServicio.listaCatTipoUbicacion();
            listaTipoProp1 = catastroServicio.listaCatTipoProp1();
            listaTipoProp2 = catastroServicio.listaCatTipoProp2();
            listaRefCartograficas = catastroServicio.listaCatRefCartograficas();
            listaTenenciaDominio = catastroServicio.listaTenenciaDominio();
            listaTenenciaTraslacionDominio = catastroServicio.listaTenenciaTraslacionDominio();
            listaTerrenoOcupacion = catastroServicio.listaTerrenoOcupacion();
            listaTerrenoNoEdificado = catastroServicio.listaTerrenoNoEdificado();
            listaTerrenoConstruccion = catastroServicio.listaTerrenoConstruccion();
            listaTerrenoCaracteristicasSuelo = catastroServicio.listaTerrenoCaracteristicasSuelo();
            listaTerrenoForma = catastroServicio.listaTerrenoForma();
            listaTerrenoTopografia = catastroServicio.listaTerrenoTopografia();
            listaTerrenoLocalizacion = catastroServicio.listaTerrenoLocalizacion();
            listaUsoSueloTipoNegocio = catastroServicio.listaUsoSueloTipoNegocio();
            listaUsoSueloTiempoFuncionamiento = catastroServicio.listaUsoSueloTiempoFuncionamiento();
            listaOtraInfoDimensiones = catastroServicio.listaOtraInfoDimensiones();
            listaOtraInfoAlicuota = catastroServicio.listaOtraInfoAlicuota();
            listaOtraInfoFuenteInfo = catastroServicio.listaOtraInfoFuenteInf();
            listaTipoDocRelevamiento = catastroServicio.listaTipoDocRelevamiento();

            listaInfServicios = catastroServicio.listarInfServicios();
            listaInfAlcantarillado1 = catastroServicio.listarInfAlcantarillado1();
            listaInfUso = catastroServicio.listarInfUso();
            listaInfMaterial = catastroServicio.listarInfMaterial();
            listaInfSentido = catastroServicio.listarInfSentido();
            listaInfEnergiaElec = catastroServicio.listarInfEnergiaElect();
            listaInfAbasAgua = catastroServicio.listarInfAbasAgua();
            listaInfAlcantarillado2 = catastroServicio.listarInfAlcantarillado2();
            listaInfOtrosServicios = catastroServicio.listarInfOtrosServicios();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    //GETTER AND SETTERS
    public CatastroPredial getCatastroPredialActual() {
        return catastroPredialActual;
    }

    public void setCatastroPredialActual(CatastroPredial catastroPredialActual) {
        this.catastroPredialActual = catastroPredialActual;
    }

    public CatastroPredialAreas getCatastroPredialAreaBloqueActual() {
        return catastroPredialAreaBloqueActual;
    }

    public void setCatastroPredialAreaBloqueActual(CatastroPredialAreas catastroPredialAreaBloqueActual) {
        this.catastroPredialAreaBloqueActual = catastroPredialAreaBloqueActual;
    }

    public List<CatastroPredialAreas> getListaCatastroPredialAreasBloque() {
        return listaCatastroPredialAreasBloque;
    }

    public void setListaCatastroPredialAreasBloque(List<CatastroPredialAreas> listaCatastroPredialAreasBloque) {
        this.listaCatastroPredialAreasBloque = listaCatastroPredialAreasBloque;
    }

    public List<CatastroPredialInfraestructura> getListaInfServicios() {
        return listaInfServicios;
    }

    public void setListaInfServicios(List<CatastroPredialInfraestructura> listaInfServicios) {
        this.listaInfServicios = listaInfServicios;
    }

    public List<CatastroPredialInfraestructura> getListaInfAlcantarillado1() {
        return listaInfAlcantarillado1;
    }

    public void setListaInfAlcantarillado1(List<CatastroPredialInfraestructura> listaInfAlcantarillado1) {
        this.listaInfAlcantarillado1 = listaInfAlcantarillado1;
    }

    public List<CatastroPredialInfraestructura> getListaInfUso() {
        return listaInfUso;
    }

    public void setListaInfUso(List<CatastroPredialInfraestructura> listaInfUso) {
        this.listaInfUso = listaInfUso;
    }

    public List<CatastroPredialInfraestructura> getListaInfMaterial() {
        return listaInfMaterial;
    }

    public void setListaInfMaterial(List<CatastroPredialInfraestructura> listaInfMaterial) {
        this.listaInfMaterial = listaInfMaterial;
    }

    public List<CatastroPredialInfraestructura> getListaInfSentido() {
        return listaInfSentido;
    }

    public void setListaInfSentido(List<CatastroPredialInfraestructura> listaInfSentido) {
        this.listaInfSentido = listaInfSentido;
    }

    public List<CatastroPredialInfraestructura> getListaInfEnergiaElec() {
        return listaInfEnergiaElec;
    }

    public void setListaInfEnergiaElec(List<CatastroPredialInfraestructura> listaInfEnergiaElec) {
        this.listaInfEnergiaElec = listaInfEnergiaElec;
    }

    public List<CatastroPredialInfraestructura> getListaInfAbasAgua() {
        return listaInfAbasAgua;
    }

    public void setListaInfAbasAgua(List<CatastroPredialInfraestructura> listaInfAbasAgua) {
        this.listaInfAbasAgua = listaInfAbasAgua;
    }

    public List<CatastroPredialInfraestructura> getListaInfAlcantarillado2() {
        return listaInfAlcantarillado2;
    }

    public void setListaInfAlcantarillado2(List<CatastroPredialInfraestructura> listaInfAlcantarillado2) {
        this.listaInfAlcantarillado2 = listaInfAlcantarillado2;
    }

    public List<CatastroPredialInfraestructura> getListaInfOtrosServicios() {
        return listaInfOtrosServicios;
    }

    public void setListaInfOtrosServicios(List<CatastroPredialInfraestructura> listaInfOtrosServicios) {
        this.listaInfOtrosServicios = listaInfOtrosServicios;
    }

    public List<CatastroPredialInfraestructura> getListaInfServiciosSeleccionado() {
        return listaInfServiciosSeleccionado;
    }

    public void setListaInfServiciosSeleccionado(List<CatastroPredialInfraestructura> listaInfServiciosSeleccionado) {
        this.listaInfServiciosSeleccionado = listaInfServiciosSeleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfAlcantarillado1Seleccionado() {
        return listaInfAlcantarillado1Seleccionado;
    }

    public void setListaInfAlcantarillado1Seleccionado(List<CatastroPredialInfraestructura> listaInfAlcantarillado1Seleccionado) {
        this.listaInfAlcantarillado1Seleccionado = listaInfAlcantarillado1Seleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfUsoSeleccionado() {
        return listaInfUsoSeleccionado;
    }

    public void setListaInfUsoSeleccionado(List<CatastroPredialInfraestructura> listaInfUsoSeleccionado) {
        this.listaInfUsoSeleccionado = listaInfUsoSeleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfMaterialSeleccionado() {
        return listaInfMaterialSeleccionado;
    }

    public void setListaInfMaterialSeleccionado(List<CatastroPredialInfraestructura> listaInfMaterialSeleccionado) {
        this.listaInfMaterialSeleccionado = listaInfMaterialSeleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfSentidoSeleccionado() {
        return listaInfSentidoSeleccionado;
    }

    public void setListaInfSentidoSeleccionado(List<CatastroPredialInfraestructura> listaInfSentidoSeleccionado) {
        this.listaInfSentidoSeleccionado = listaInfSentidoSeleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfEnergiaElecSeleccionado() {
        return listaInfEnergiaElecSeleccionado;
    }

    public void setListaInfEnergiaElecSeleccionado(List<CatastroPredialInfraestructura> listaInfEnergiaElecSeleccionado) {
        this.listaInfEnergiaElecSeleccionado = listaInfEnergiaElecSeleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfAbasAguaSeleccionado() {
        return listaInfAbasAguaSeleccionado;
    }

    public void setListaInfAbasAguaSeleccionado(List<CatastroPredialInfraestructura> listaInfAbasAguaSeleccionado) {
        this.listaInfAbasAguaSeleccionado = listaInfAbasAguaSeleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfAlcantarillado2Seleccionado() {
        return listaInfAlcantarillado2Seleccionado;
    }

    public void setListaInfAlcantarillado2Seleccionado(List<CatastroPredialInfraestructura> listaInfAlcantarillado2Seleccionado) {
        this.listaInfAlcantarillado2Seleccionado = listaInfAlcantarillado2Seleccionado;
    }

    public List<CatastroPredialInfraestructura> getListaInfOtrosServiciosSeleccionado() {
        return listaInfOtrosServiciosSeleccionado;
    }

    public void setListaInfOtrosServiciosSeleccionado(List<CatastroPredialInfraestructura> listaInfOtrosServiciosSeleccionado) {
        this.listaInfOtrosServiciosSeleccionado = listaInfOtrosServiciosSeleccionado;
    }

    public List<CatastroPredialUsosuelo> getListaUsuSuelo() {
        return listaUsuSuelo;
    }

    public void setListaUsuSuelo(List<CatastroPredialUsosuelo> listaUsuSuelo) {
        this.listaUsuSuelo = listaUsuSuelo;
    }

    public int getNumBloquesEdif() {
        return numBloquesEdif;
    }

    public void setNumBloquesEdif(int numBloquesEdif) {
        this.numBloquesEdif = numBloquesEdif;
    }

    public int getNumPisosEdif() {
        return numPisosEdif;
    }

    public void setNumPisosEdif(int numPisosEdif) {
        this.numPisosEdif = numPisosEdif;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo1_1() {
        return listaEdifGrupo1_1;
    }

    public void setListaEdifGrupo1_1(List<CatastroPredialEdificacion> listaEdifGrupo1_1) {
        this.listaEdifGrupo1_1 = listaEdifGrupo1_1;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo1_2() {
        return listaEdifGrupo1_2;
    }

    public void setListaEdifGrupo1_2(List<CatastroPredialEdificacion> listaEdifGrupo1_2) {
        this.listaEdifGrupo1_2 = listaEdifGrupo1_2;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo1_3() {
        return listaEdifGrupo1_3;
    }

    public void setListaEdifGrupo1_3(List<CatastroPredialEdificacion> listaEdifGrupo1_3) {
        this.listaEdifGrupo1_3 = listaEdifGrupo1_3;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo1_4() {
        return listaEdifGrupo1_4;
    }

    public void setListaEdifGrupo1_4(List<CatastroPredialEdificacion> listaEdifGrupo1_4) {
        this.listaEdifGrupo1_4 = listaEdifGrupo1_4;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo234() {
        return listaEdifGrupo234;
    }

    public void setListaEdifGrupo234(List<CatastroPredialEdificacion> listaEdifGrupo234) {
        this.listaEdifGrupo234 = listaEdifGrupo234;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo5_14() {
        return listaEdifGrupo5_14;
    }

    public void setListaEdifGrupo5_14(List<CatastroPredialEdificacion> listaEdifGrupo5_14) {
        this.listaEdifGrupo5_14 = listaEdifGrupo5_14;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo5_5() {
        return listaEdifGrupo5_5;
    }

    public void setListaEdifGrupo5_5(List<CatastroPredialEdificacion> listaEdifGrupo5_5) {
        this.listaEdifGrupo5_5 = listaEdifGrupo5_5;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo5_6() {
        return listaEdifGrupo5_6;
    }

    public void setListaEdifGrupo5_6(List<CatastroPredialEdificacion> listaEdifGrupo5_6) {
        this.listaEdifGrupo5_6 = listaEdifGrupo5_6;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo5_7() {
        return listaEdifGrupo5_7;
    }

    public void setListaEdifGrupo5_7(List<CatastroPredialEdificacion> listaEdifGrupo5_7) {
        this.listaEdifGrupo5_7 = listaEdifGrupo5_7;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo5_8() {
        return listaEdifGrupo5_8;
    }

    public void setListaEdifGrupo5_8(List<CatastroPredialEdificacion> listaEdifGrupo5_8) {
        this.listaEdifGrupo5_8 = listaEdifGrupo5_8;
    }

    public List<CatastroPredialEdificacion> getListaEdifGrupo5_9() {
        return listaEdifGrupo5_9;
    }

    public void setListaEdifGrupo5_9(List<CatastroPredialEdificacion> listaEdifGrupo5_9) {
        this.listaEdifGrupo5_9 = listaEdifGrupo5_9;
    }

    public List<CatalogoDetalle> getListaTipoVia() {
        return listaTipoVia;
    }

    public void setListaTipoVia(List<CatalogoDetalle> listaTipoVia) {
        this.listaTipoVia = listaTipoVia;
    }

    public List<CatalogoDetalle> getListaTipoUbicacion() {
        return listaTipoUbicacion;
    }

    public void setListaTipoUbicacion(List<CatalogoDetalle> listaTipoUbicacion) {
        this.listaTipoUbicacion = listaTipoUbicacion;
    }

    public List<CatalogoDetalle> getListaTipoProp1() {
        return listaTipoProp1;
    }

    public void setListaTipoProp1(List<CatalogoDetalle> listaTipoProp1) {
        this.listaTipoProp1 = listaTipoProp1;
    }

    public List<CatalogoDetalle> getListaTipoProp2() {
        return listaTipoProp2;
    }

    public void setListaTipoProp2(List<CatalogoDetalle> listaTipoProp2) {
        this.listaTipoProp2 = listaTipoProp2;
    }

    public List<CatalogoDetalle> getListaRefCartograficas() {
        return listaRefCartograficas;
    }

    public void setListaRefCartograficas(List<CatalogoDetalle> listaRefCartograficas) {
        this.listaRefCartograficas = listaRefCartograficas;
    }

    public List<CatalogoDetalle> getListaTenenciaDominio() {
        return listaTenenciaDominio;
    }

    public void setListaTenenciaDominio(List<CatalogoDetalle> listaTenenciaDominio) {
        this.listaTenenciaDominio = listaTenenciaDominio;
    }

    public List<CatalogoDetalle> getListaTenenciaTraslacionDominio() {
        return listaTenenciaTraslacionDominio;
    }

    public void setListaTenenciaTraslacionDominio(List<CatalogoDetalle> listaTenenciaTraslacionDominio) {
        this.listaTenenciaTraslacionDominio = listaTenenciaTraslacionDominio;
    }

    public List<CatalogoDetalle> getListaTerrenoOcupacion() {
        return listaTerrenoOcupacion;
    }

    public void setListaTerrenoOcupacion(List<CatalogoDetalle> listaTerrenoOcupacion) {
        this.listaTerrenoOcupacion = listaTerrenoOcupacion;
    }

    public List<CatalogoDetalle> getListaTerrenoNoEdificado() {
        return listaTerrenoNoEdificado;
    }

    public void setListaTerrenoNoEdificado(List<CatalogoDetalle> listaTerrenoNoEdificado) {
        this.listaTerrenoNoEdificado = listaTerrenoNoEdificado;
    }

    public List<CatalogoDetalle> getListaTerrenoConstruccion() {
        return listaTerrenoConstruccion;
    }

    public void setListaTerrenoConstruccion(List<CatalogoDetalle> listaTerrenoConstruccion) {
        this.listaTerrenoConstruccion = listaTerrenoConstruccion;
    }

    public List<CatalogoDetalle> getListaTerrenoCaracteristicasSuelo() {
        return listaTerrenoCaracteristicasSuelo;
    }

    public void setListaTerrenoCaracteristicasSuelo(List<CatalogoDetalle> listaTerrenoCaracteristicasSuelo) {
        this.listaTerrenoCaracteristicasSuelo = listaTerrenoCaracteristicasSuelo;
    }

    public List<CatalogoDetalle> getListaTerrenoForma() {
        return listaTerrenoForma;
    }

    public void setListaTerrenoForma(List<CatalogoDetalle> listaTerrenoForma) {
        this.listaTerrenoForma = listaTerrenoForma;
    }

    public List<CatalogoDetalle> getListaTerrenoTopografia() {
        return listaTerrenoTopografia;
    }

    public void setListaTerrenoTopografia(List<CatalogoDetalle> listaTerrenoTopografia) {
        this.listaTerrenoTopografia = listaTerrenoTopografia;
    }

    public List<CatalogoDetalle> getListaTerrenoLocalizacion() {
        return listaTerrenoLocalizacion;
    }

    public void setListaTerrenoLocalizacion(List<CatalogoDetalle> listaTerrenoLocalizacion) {
        this.listaTerrenoLocalizacion = listaTerrenoLocalizacion;
    }

    public List<CatalogoDetalle> getListaUsoSueloTipoNegocio() {
        return listaUsoSueloTipoNegocio;
    }

    public void setListaUsoSueloTipoNegocio(List<CatalogoDetalle> listaUsoSueloTipoNegocio) {
        this.listaUsoSueloTipoNegocio = listaUsoSueloTipoNegocio;
    }

    public List<CatalogoDetalle> getListaUsoSueloTiempoFuncionamiento() {
        return listaUsoSueloTiempoFuncionamiento;
    }

    public void setListaUsoSueloTiempoFuncionamiento(List<CatalogoDetalle> listaUsoSueloTiempoFuncionamiento) {
        this.listaUsoSueloTiempoFuncionamiento = listaUsoSueloTiempoFuncionamiento;
    }

    public List<CatalogoDetalle> getListaOtraInfoDimensiones() {
        return listaOtraInfoDimensiones;
    }

    public void setListaOtraInfoDimensiones(List<CatalogoDetalle> listaOtraInfoDimensiones) {
        this.listaOtraInfoDimensiones = listaOtraInfoDimensiones;
    }

    public List<CatalogoDetalle> getListaOtraInfoAlicuota() {
        return listaOtraInfoAlicuota;
    }

    public void setListaOtraInfoAlicuota(List<CatalogoDetalle> listaOtraInfoAlicuota) {
        this.listaOtraInfoAlicuota = listaOtraInfoAlicuota;
    }

    public List<CatalogoDetalle> getListaOtraInfoFuenteInfo() {
        return listaOtraInfoFuenteInfo;
    }

    public void setListaOtraInfoFuenteInfo(List<CatalogoDetalle> listaOtraInfoFuenteInfo) {
        this.listaOtraInfoFuenteInfo = listaOtraInfoFuenteInfo;
    }

    public List<CatalogoDetalle> getListaTipoDocRelevamiento() {
        return listaTipoDocRelevamiento;
    }

    public void setListaTipoDocRelevamiento(List<CatalogoDetalle> listaTipoDocRelevamiento) {
        this.listaTipoDocRelevamiento = listaTipoDocRelevamiento;
    }

    public List<CatalogoDetalle> getListaOpcEdifGrupo1_1() {
        return listaOpcEdifGrupo1_1;
    }

    public void setListaOpcEdifGrupo1_1(List<CatalogoDetalle> listaOpcEdifGrupo1_1) {
        this.listaOpcEdifGrupo1_1 = listaOpcEdifGrupo1_1;
    }


    public List<CatalogoDetalle> getListaOpcEdifGrupo1_3() {
        return listaOpcEdifGrupo1_3;
    }

    public void setListaOpcEdifGrupo1_3(List<CatalogoDetalle> listaOpcEdifGrupo1_3) {
        this.listaOpcEdifGrupo1_3 = listaOpcEdifGrupo1_3;
    }


    public List<CatalogoDetalle> getListaOpcEdifGrupo234() {
        return listaOpcEdifGrupo234;
    }

    public void setListaOpcEdifGrupo234(List<CatalogoDetalle> listaOpcEdifGrupo234) {
        this.listaOpcEdifGrupo234 = listaOpcEdifGrupo234;
    }


    public List<CatalogoDetalle> getListaOpcEdifGrupo5_5() {
        return listaOpcEdifGrupo5_5;
    }

    public void setListaOpcEdifGrupo5_5(List<CatalogoDetalle> listaOpcEdifGrupo5_5) {
        this.listaOpcEdifGrupo5_5 = listaOpcEdifGrupo5_5;
    }

    public List<CatalogoDetalle> getListaOpcEdifGrupo5_6() {
        return listaOpcEdifGrupo5_6;
    }

    public void setListaOpcEdifGrupo5_6(List<CatalogoDetalle> listaOpcEdifGrupo5_6) {
        this.listaOpcEdifGrupo5_6 = listaOpcEdifGrupo5_6;
    }

    public List<CatalogoDetalle> getListaOpcEdifGrupo5_7() {
        return listaOpcEdifGrupo5_7;
    }

    public void setListaOpcEdifGrupo5_7(List<CatalogoDetalle> listaOpcEdifGrupo5_7) {
        this.listaOpcEdifGrupo5_7 = listaOpcEdifGrupo5_7;
    }

    public List<CatalogoDetalle> getListaOpcEdifGrupo5_8() {
        return listaOpcEdifGrupo5_8;
    }

    public void setListaOpcEdifGrupo5_8(List<CatalogoDetalle> listaOpcEdifGrupo5_8) {
        this.listaOpcEdifGrupo5_8 = listaOpcEdifGrupo5_8;
    }

    public List<CatalogoDetalle> getListaOpcEdifGrupo5_9() {
        return listaOpcEdifGrupo5_9;
    }

    public void setListaOpcEdifGrupo5_9(List<CatalogoDetalle> listaOpcEdifGrupo5_9) {
        this.listaOpcEdifGrupo5_9 = listaOpcEdifGrupo5_9;
    }

}
