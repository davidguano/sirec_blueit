/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DAVID GUAN
 */
@Entity
@Table(name = "sirec.catastro_predial")
@NamedQueries({
    @NamedQuery(name = "CatastroPredial.findAll", query = "SELECT c FROM CatastroPredial c")})
public class CatastroPredial implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "catpre_codigo")
    private Integer catpreCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "catpre_cod_nacional")
    private String catpreCodNacional;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "catpre_cod_local")
    private String catpreCodLocal;
    @Size(max = 10)
    @Column(name = "catpre_numero")
    private String catpreNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "catpre_nombre_sector")
    private String catpreNombreSector;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "catpre_coord_latitud")
    private Double catpreCoordLatitud;
    @Column(name = "catpre_coord_longitud")
    private Double catpreCoordLongitud;
    @Column(name = "catpre_longitud_frente_principal")
    private Double catpreLongitudFrentePrincipal;
    @Column(name = "catpre_area_total")
    private Double catpreAreaTotal;
    @Column(name = "catpre_area_total_esc")
    private Double catpreAreaTotalEsc;
    @Column(name = "catpre_area_total_cons")
    private Double catpreAreaTotalCons;
    @Size(max = 2147483647)
    @Column(name = "catpre_colindante_norte")
    private String catpreColindanteNorte;
    @Size(max = 2147483647)
    @Column(name = "catpre_colindante_sur")
    private String catpreColindanteSur;
    @Size(max = 2147483647)
    @Column(name = "catpre_colindante_este")
    private String catpreColindanteEste;
    @Size(max = 2147483647)
    @Column(name = "catpre_colindante_oeste")
    private String catpreColindanteOeste;
    @Column(name = "catpre_existe_escrituras")
    private Boolean catpreExisteEscrituras;
    @Size(max = 10)
    @Column(name = "catpre_num_notaria")
    private String catpreNumNotaria;
    @Size(max = 100)
    @Column(name = "catpre_lugar_escritura")
    private String catpreLugarEscritura;
    @Column(name = "catpre_fecha_reg_propiedad")
    @Temporal(TemporalType.DATE)
    private Date catpreFechaRegPropiedad;
    @Column(name = "catpre_tiene_legalizacion")
    private Boolean catpreTieneLegalizacion;
    @Column(name = "catpre_num_medidores_luz")
    private Integer catpreNumMedidoresLuz;
    @Column(name = "catpre_num_medidores_agua")
    private Integer catpreNumMedidoresAgua;
    @Column(name = "catpre_num_carriles_acceso")
    private Integer catpreNumCarrilesAcceso;
    @Column(name = "catpre_negocio_vivienda")
    private Boolean catpreNegocioVivienda;
    @Column(name = "catpre_porc_vivienda")
    private Integer catprePorcVivienda;
    @Column(name = "catpre_porc_bodega")
    private Integer catprePorcBodega;
    @Column(name = "catpre_porc_garage")
    private Integer catprePorcGarage;
    @Column(name = "catpre_porc_otros")
    private Integer catprePorcOtros;
    @Column(name = "catpre_linderos_definidos")
    private Boolean catpreLinderosDefinidos;
    @Column(name = "catpre_colaboracion_prop")
    private Boolean catpreColaboracionProp;
    @Size(max = 2147483647)
    @Column(name = "catpre_nombre_fuente")
    private String catpreNombreFuente;
    @Column(name = "catpre_per_discapacidad")
    private Boolean catprePerDiscapacidad;
    @Size(max = 20)
    @Column(name = "catpre_disc_cicarnet")
    private String catpreDiscCicarnet;
    @Size(max = 200)
    @Column(name = "catpre_disc_nombre")
    private String catpreDiscNombre;
    @Column(name = "catpre_disc_porc")
    private Integer catpreDiscPorc;
    @Size(max = 200)
    @Column(name = "catpre_empadronador")
    private String catpreEmpadronador;
    @Size(max = 10)
    @Column(name = "catpre_empadronador_ci")
    private String catpreEmpadronadorCi;
    @Size(max = 200)
    @Column(name = "catpre_supervisor")
    private String catpreSupervisor;
    @Size(max = 10)
    @Column(name = "catpre_supervisor_ci")
    private String catpreSupervisorCi;
    @Size(max = 200)
    @Column(name = "catpre_qcqa")
    private String catpreQcqa;
    @Size(max = 2147483647)
    @Column(name = "ultacc_detalle")
    private String ultaccDetalle;
    @Column(name = "ultacc_marcatiempo")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultaccMarcatiempo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialAreas> catastroPredialAreasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialValoracion> catastroPredialValoracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialPlusvaliaValoracion> catastroPredialPlusvaliaValoracionList;
    @JoinColumn(name = "usu_identificacion", referencedColumnName = "usu_identificacion")
    @ManyToOne(optional = false)
    private SegUsuario usuIdentificacion;
    @JoinColumn(name = "pro_ci", referencedColumnName = "pro_ci")
    @ManyToOne(optional = false)
    private Propietario proCi;
    @JoinColumn(name = "catdet_fuente_informacion", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetFuenteInformacion;
    @JoinColumn(name = "catdet_alicuotas", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetAlicuotas;
    @JoinColumn(name = "catdet_dimension", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetDimension;
    @JoinColumn(name = "catdet_tipo_func_negocio", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetTipoFuncNegocio;
    @JoinColumn(name = "catdet_tipo_negocio", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetTipoNegocio;
    @JoinColumn(name = "catdet_localizacion", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetLocalizacion;
    @JoinColumn(name = "catdet_topografia", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetTopografia;
    @JoinColumn(name = "catdet_forma", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetForma;
    @JoinColumn(name = "catdet_doc_relevamiento", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetDocRelevamiento;
    @JoinColumn(name = "catdet_tipo_via", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetTipoVia;
    @JoinColumn(name = "catdet_tipo_ubicacion", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetTipoUbicacion;
    @JoinColumn(name = "catdet_tipo_prop1", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetTipoProp1;
    @JoinColumn(name = "catdet_tipo_prop2", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetTipoProp2;
    @JoinColumn(name = "catdet_ref_cartografica", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetRefCartografica;
    @JoinColumn(name = "catdet_dominio", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetDominio;
    @JoinColumn(name = "catdet_traslacion_dominio", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetTraslacionDominio;
    @JoinColumn(name = "catdet_ocupacion", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetOcupacion;
    @JoinColumn(name = "catdet_no_edificado", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetNoEdificado;
    @JoinColumn(name = "catdet_en_construccion", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetEnConstruccion;
    @JoinColumn(name = "catdet_caracteristicas_suelo", referencedColumnName = "catdet_codigo")
    @ManyToOne
    private CatalogoDetalle catdetCaracteristicasSuelo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialUsosuelo> catastroPredialUsosueloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<Patente> patenteList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialAlcabalaValoracion> catastroPredialAlcabalaValoracionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<PredioArchivo> predioArchivoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialEdificacion> catastroPredialEdificacionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialInfraestructura> catastroPredialInfraestructuraList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<CatastroPredialInfAnt> catastroPredialInfAntList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "catpreCodigo")
    private List<Mejora> mejoraList;

    public CatastroPredial() {
    }

    public CatastroPredial(Integer catpreCodigo) {
        this.catpreCodigo = catpreCodigo;
    }

    public CatastroPredial(Integer catpreCodigo, String catpreCodNacional, String catpreCodLocal, String catpreNombreSector) {
        this.catpreCodigo = catpreCodigo;
        this.catpreCodNacional = catpreCodNacional;
        this.catpreCodLocal = catpreCodLocal;
        this.catpreNombreSector = catpreNombreSector;
    }

    public Integer getCatpreCodigo() {
        return catpreCodigo;
    }

    public void setCatpreCodigo(Integer catpreCodigo) {
        this.catpreCodigo = catpreCodigo;
    }

    public String getCatpreCodNacional() {
        return catpreCodNacional;
    }

    public void setCatpreCodNacional(String catpreCodNacional) {
        this.catpreCodNacional = catpreCodNacional;
    }

    public String getCatpreCodLocal() {
        return catpreCodLocal;
    }

    public void setCatpreCodLocal(String catpreCodLocal) {
        this.catpreCodLocal = catpreCodLocal;
    }

    public String getCatpreNumero() {
        return catpreNumero;
    }

    public void setCatpreNumero(String catpreNumero) {
        this.catpreNumero = catpreNumero;
    }

    public String getCatpreNombreSector() {
        return catpreNombreSector;
    }

    public void setCatpreNombreSector(String catpreNombreSector) {
        this.catpreNombreSector = catpreNombreSector;
    }

    public Double getCatpreCoordLatitud() {
        return catpreCoordLatitud;
    }

    public void setCatpreCoordLatitud(Double catpreCoordLatitud) {
        this.catpreCoordLatitud = catpreCoordLatitud;
    }

    public Double getCatpreCoordLongitud() {
        return catpreCoordLongitud;
    }

    public void setCatpreCoordLongitud(Double catpreCoordLongitud) {
        this.catpreCoordLongitud = catpreCoordLongitud;
    }

    public Double getCatpreLongitudFrentePrincipal() {
        return catpreLongitudFrentePrincipal;
    }

    public void setCatpreLongitudFrentePrincipal(Double catpreLongitudFrentePrincipal) {
        this.catpreLongitudFrentePrincipal = catpreLongitudFrentePrincipal;
    }

    public Double getCatpreAreaTotal() {
        return catpreAreaTotal;
    }

    public void setCatpreAreaTotal(Double catpreAreaTotal) {
        this.catpreAreaTotal = catpreAreaTotal;
    }

    public Double getCatpreAreaTotalEsc() {
        return catpreAreaTotalEsc;
    }

    public void setCatpreAreaTotalEsc(Double catpreAreaTotalEsc) {
        this.catpreAreaTotalEsc = catpreAreaTotalEsc;
    }

    public Double getCatpreAreaTotalCons() {
        return catpreAreaTotalCons;
    }

    public void setCatpreAreaTotalCons(Double catpreAreaTotalCons) {
        this.catpreAreaTotalCons = catpreAreaTotalCons;
    }

    public String getCatpreColindanteNorte() {
        return catpreColindanteNorte;
    }

    public void setCatpreColindanteNorte(String catpreColindanteNorte) {
        this.catpreColindanteNorte = catpreColindanteNorte;
    }

    public String getCatpreColindanteSur() {
        return catpreColindanteSur;
    }

    public void setCatpreColindanteSur(String catpreColindanteSur) {
        this.catpreColindanteSur = catpreColindanteSur;
    }

    public String getCatpreColindanteEste() {
        return catpreColindanteEste;
    }

    public void setCatpreColindanteEste(String catpreColindanteEste) {
        this.catpreColindanteEste = catpreColindanteEste;
    }

    public String getCatpreColindanteOeste() {
        return catpreColindanteOeste;
    }

    public void setCatpreColindanteOeste(String catpreColindanteOeste) {
        this.catpreColindanteOeste = catpreColindanteOeste;
    }

    public Boolean getCatpreExisteEscrituras() {
        return catpreExisteEscrituras;
    }

    public void setCatpreExisteEscrituras(Boolean catpreExisteEscrituras) {
        this.catpreExisteEscrituras = catpreExisteEscrituras;
    }

    public String getCatpreNumNotaria() {
        return catpreNumNotaria;
    }

    public void setCatpreNumNotaria(String catpreNumNotaria) {
        this.catpreNumNotaria = catpreNumNotaria;
    }

    public String getCatpreLugarEscritura() {
        return catpreLugarEscritura;
    }

    public void setCatpreLugarEscritura(String catpreLugarEscritura) {
        this.catpreLugarEscritura = catpreLugarEscritura;
    }

    public Date getCatpreFechaRegPropiedad() {
        return catpreFechaRegPropiedad;
    }

    public void setCatpreFechaRegPropiedad(Date catpreFechaRegPropiedad) {
        this.catpreFechaRegPropiedad = catpreFechaRegPropiedad;
    }

    public Boolean getCatpreTieneLegalizacion() {
        return catpreTieneLegalizacion;
    }

    public void setCatpreTieneLegalizacion(Boolean catpreTieneLegalizacion) {
        this.catpreTieneLegalizacion = catpreTieneLegalizacion;
    }

    public Integer getCatpreNumMedidoresLuz() {
        return catpreNumMedidoresLuz;
    }

    public void setCatpreNumMedidoresLuz(Integer catpreNumMedidoresLuz) {
        this.catpreNumMedidoresLuz = catpreNumMedidoresLuz;
    }

    public Integer getCatpreNumMedidoresAgua() {
        return catpreNumMedidoresAgua;
    }

    public void setCatpreNumMedidoresAgua(Integer catpreNumMedidoresAgua) {
        this.catpreNumMedidoresAgua = catpreNumMedidoresAgua;
    }

    public Integer getCatpreNumCarrilesAcceso() {
        return catpreNumCarrilesAcceso;
    }

    public void setCatpreNumCarrilesAcceso(Integer catpreNumCarrilesAcceso) {
        this.catpreNumCarrilesAcceso = catpreNumCarrilesAcceso;
    }

    public Boolean getCatpreNegocioVivienda() {
        return catpreNegocioVivienda;
    }

    public void setCatpreNegocioVivienda(Boolean catpreNegocioVivienda) {
        this.catpreNegocioVivienda = catpreNegocioVivienda;
    }

    public Integer getCatprePorcVivienda() {
        return catprePorcVivienda;
    }

    public void setCatprePorcVivienda(Integer catprePorcVivienda) {
        this.catprePorcVivienda = catprePorcVivienda;
    }

    public Integer getCatprePorcBodega() {
        return catprePorcBodega;
    }

    public void setCatprePorcBodega(Integer catprePorcBodega) {
        this.catprePorcBodega = catprePorcBodega;
    }

    public Integer getCatprePorcGarage() {
        return catprePorcGarage;
    }

    public void setCatprePorcGarage(Integer catprePorcGarage) {
        this.catprePorcGarage = catprePorcGarage;
    }

    public Integer getCatprePorcOtros() {
        return catprePorcOtros;
    }

    public void setCatprePorcOtros(Integer catprePorcOtros) {
        this.catprePorcOtros = catprePorcOtros;
    }

    public Boolean getCatpreLinderosDefinidos() {
        return catpreLinderosDefinidos;
    }

    public void setCatpreLinderosDefinidos(Boolean catpreLinderosDefinidos) {
        this.catpreLinderosDefinidos = catpreLinderosDefinidos;
    }

    public Boolean getCatpreColaboracionProp() {
        return catpreColaboracionProp;
    }

    public void setCatpreColaboracionProp(Boolean catpreColaboracionProp) {
        this.catpreColaboracionProp = catpreColaboracionProp;
    }

    public String getCatpreNombreFuente() {
        return catpreNombreFuente;
    }

    public void setCatpreNombreFuente(String catpreNombreFuente) {
        this.catpreNombreFuente = catpreNombreFuente;
    }

    public Boolean getCatprePerDiscapacidad() {
        return catprePerDiscapacidad;
    }

    public void setCatprePerDiscapacidad(Boolean catprePerDiscapacidad) {
        this.catprePerDiscapacidad = catprePerDiscapacidad;
    }

    public String getCatpreDiscCicarnet() {
        return catpreDiscCicarnet;
    }

    public void setCatpreDiscCicarnet(String catpreDiscCicarnet) {
        this.catpreDiscCicarnet = catpreDiscCicarnet;
    }

    public String getCatpreDiscNombre() {
        return catpreDiscNombre;
    }

    public void setCatpreDiscNombre(String catpreDiscNombre) {
        this.catpreDiscNombre = catpreDiscNombre;
    }

    public Integer getCatpreDiscPorc() {
        return catpreDiscPorc;
    }

    public void setCatpreDiscPorc(Integer catpreDiscPorc) {
        this.catpreDiscPorc = catpreDiscPorc;
    }

    public String getCatpreEmpadronador() {
        return catpreEmpadronador;
    }

    public void setCatpreEmpadronador(String catpreEmpadronador) {
        this.catpreEmpadronador = catpreEmpadronador;
    }

    public String getCatpreEmpadronadorCi() {
        return catpreEmpadronadorCi;
    }

    public void setCatpreEmpadronadorCi(String catpreEmpadronadorCi) {
        this.catpreEmpadronadorCi = catpreEmpadronadorCi;
    }

    public String getCatpreSupervisor() {
        return catpreSupervisor;
    }

    public void setCatpreSupervisor(String catpreSupervisor) {
        this.catpreSupervisor = catpreSupervisor;
    }

    public String getCatpreSupervisorCi() {
        return catpreSupervisorCi;
    }

    public void setCatpreSupervisorCi(String catpreSupervisorCi) {
        this.catpreSupervisorCi = catpreSupervisorCi;
    }

    public String getCatpreQcqa() {
        return catpreQcqa;
    }

    public void setCatpreQcqa(String catpreQcqa) {
        this.catpreQcqa = catpreQcqa;
    }

    public String getUltaccDetalle() {
        return ultaccDetalle;
    }

    public void setUltaccDetalle(String ultaccDetalle) {
        this.ultaccDetalle = ultaccDetalle;
    }

    public Date getUltaccMarcatiempo() {
        return ultaccMarcatiempo;
    }

    public void setUltaccMarcatiempo(Date ultaccMarcatiempo) {
        this.ultaccMarcatiempo = ultaccMarcatiempo;
    }

    public List<CatastroPredialAreas> getCatastroPredialAreasList() {
        return catastroPredialAreasList;
    }

    public void setCatastroPredialAreasList(List<CatastroPredialAreas> catastroPredialAreasList) {
        this.catastroPredialAreasList = catastroPredialAreasList;
    }

    public List<CatastroPredialValoracion> getCatastroPredialValoracionList() {
        return catastroPredialValoracionList;
    }

    public void setCatastroPredialValoracionList(List<CatastroPredialValoracion> catastroPredialValoracionList) {
        this.catastroPredialValoracionList = catastroPredialValoracionList;
    }

    public List<CatastroPredialPlusvaliaValoracion> getCatastroPredialPlusvaliaValoracionList() {
        return catastroPredialPlusvaliaValoracionList;
    }

    public void setCatastroPredialPlusvaliaValoracionList(List<CatastroPredialPlusvaliaValoracion> catastroPredialPlusvaliaValoracionList) {
        this.catastroPredialPlusvaliaValoracionList = catastroPredialPlusvaliaValoracionList;
    }

    public SegUsuario getUsuIdentificacion() {
        return usuIdentificacion;
    }

    public void setUsuIdentificacion(SegUsuario usuIdentificacion) {
        this.usuIdentificacion = usuIdentificacion;
    }

    public Propietario getProCi() {
        return proCi;
    }

    public void setProCi(Propietario proCi) {
        this.proCi = proCi;
    }

    public CatalogoDetalle getCatdetFuenteInformacion() {
        return catdetFuenteInformacion;
    }

    public void setCatdetFuenteInformacion(CatalogoDetalle catdetFuenteInformacion) {
        this.catdetFuenteInformacion = catdetFuenteInformacion;
    }

    public CatalogoDetalle getCatdetAlicuotas() {
        return catdetAlicuotas;
    }

    public void setCatdetAlicuotas(CatalogoDetalle catdetAlicuotas) {
        this.catdetAlicuotas = catdetAlicuotas;
    }

    public CatalogoDetalle getCatdetDimension() {
        return catdetDimension;
    }

    public void setCatdetDimension(CatalogoDetalle catdetDimension) {
        this.catdetDimension = catdetDimension;
    }

    public CatalogoDetalle getCatdetTipoFuncNegocio() {
        return catdetTipoFuncNegocio;
    }

    public void setCatdetTipoFuncNegocio(CatalogoDetalle catdetTipoFuncNegocio) {
        this.catdetTipoFuncNegocio = catdetTipoFuncNegocio;
    }

    public CatalogoDetalle getCatdetTipoNegocio() {
        return catdetTipoNegocio;
    }

    public void setCatdetTipoNegocio(CatalogoDetalle catdetTipoNegocio) {
        this.catdetTipoNegocio = catdetTipoNegocio;
    }

    public CatalogoDetalle getCatdetLocalizacion() {
        return catdetLocalizacion;
    }

    public void setCatdetLocalizacion(CatalogoDetalle catdetLocalizacion) {
        this.catdetLocalizacion = catdetLocalizacion;
    }

    public CatalogoDetalle getCatdetTopografia() {
        return catdetTopografia;
    }

    public void setCatdetTopografia(CatalogoDetalle catdetTopografia) {
        this.catdetTopografia = catdetTopografia;
    }

    public CatalogoDetalle getCatdetForma() {
        return catdetForma;
    }

    public void setCatdetForma(CatalogoDetalle catdetForma) {
        this.catdetForma = catdetForma;
    }

    public CatalogoDetalle getCatdetDocRelevamiento() {
        return catdetDocRelevamiento;
    }

    public void setCatdetDocRelevamiento(CatalogoDetalle catdetDocRelevamiento) {
        this.catdetDocRelevamiento = catdetDocRelevamiento;
    }

    public CatalogoDetalle getCatdetTipoVia() {
        return catdetTipoVia;
    }

    public void setCatdetTipoVia(CatalogoDetalle catdetTipoVia) {
        this.catdetTipoVia = catdetTipoVia;
    }

    public CatalogoDetalle getCatdetTipoUbicacion() {
        return catdetTipoUbicacion;
    }

    public void setCatdetTipoUbicacion(CatalogoDetalle catdetTipoUbicacion) {
        this.catdetTipoUbicacion = catdetTipoUbicacion;
    }

    public CatalogoDetalle getCatdetTipoProp1() {
        return catdetTipoProp1;
    }

    public void setCatdetTipoProp1(CatalogoDetalle catdetTipoProp1) {
        this.catdetTipoProp1 = catdetTipoProp1;
    }

    public CatalogoDetalle getCatdetTipoProp2() {
        return catdetTipoProp2;
    }

    public void setCatdetTipoProp2(CatalogoDetalle catdetTipoProp2) {
        this.catdetTipoProp2 = catdetTipoProp2;
    }

    public CatalogoDetalle getCatdetRefCartografica() {
        return catdetRefCartografica;
    }

    public void setCatdetRefCartografica(CatalogoDetalle catdetRefCartografica) {
        this.catdetRefCartografica = catdetRefCartografica;
    }

    public CatalogoDetalle getCatdetDominio() {
        return catdetDominio;
    }

    public void setCatdetDominio(CatalogoDetalle catdetDominio) {
        this.catdetDominio = catdetDominio;
    }

    public CatalogoDetalle getCatdetTraslacionDominio() {
        return catdetTraslacionDominio;
    }

    public void setCatdetTraslacionDominio(CatalogoDetalle catdetTraslacionDominio) {
        this.catdetTraslacionDominio = catdetTraslacionDominio;
    }

    public CatalogoDetalle getCatdetOcupacion() {
        return catdetOcupacion;
    }

    public void setCatdetOcupacion(CatalogoDetalle catdetOcupacion) {
        this.catdetOcupacion = catdetOcupacion;
    }

    public CatalogoDetalle getCatdetNoEdificado() {
        return catdetNoEdificado;
    }

    public void setCatdetNoEdificado(CatalogoDetalle catdetNoEdificado) {
        this.catdetNoEdificado = catdetNoEdificado;
    }

    public CatalogoDetalle getCatdetEnConstruccion() {
        return catdetEnConstruccion;
    }

    public void setCatdetEnConstruccion(CatalogoDetalle catdetEnConstruccion) {
        this.catdetEnConstruccion = catdetEnConstruccion;
    }

    public CatalogoDetalle getCatdetCaracteristicasSuelo() {
        return catdetCaracteristicasSuelo;
    }

    public void setCatdetCaracteristicasSuelo(CatalogoDetalle catdetCaracteristicasSuelo) {
        this.catdetCaracteristicasSuelo = catdetCaracteristicasSuelo;
    }

    public List<CatastroPredialUsosuelo> getCatastroPredialUsosueloList() {
        return catastroPredialUsosueloList;
    }

    public void setCatastroPredialUsosueloList(List<CatastroPredialUsosuelo> catastroPredialUsosueloList) {
        this.catastroPredialUsosueloList = catastroPredialUsosueloList;
    }

    public List<Patente> getPatenteList() {
        return patenteList;
    }

    public void setPatenteList(List<Patente> patenteList) {
        this.patenteList = patenteList;
    }

    public List<CatastroPredialAlcabalaValoracion> getCatastroPredialAlcabalaValoracionList() {
        return catastroPredialAlcabalaValoracionList;
    }

    public void setCatastroPredialAlcabalaValoracionList(List<CatastroPredialAlcabalaValoracion> catastroPredialAlcabalaValoracionList) {
        this.catastroPredialAlcabalaValoracionList = catastroPredialAlcabalaValoracionList;
    }

    public List<PredioArchivo> getPredioArchivoList() {
        return predioArchivoList;
    }

    public void setPredioArchivoList(List<PredioArchivo> predioArchivoList) {
        this.predioArchivoList = predioArchivoList;
    }

    public List<CatastroPredialEdificacion> getCatastroPredialEdificacionList() {
        return catastroPredialEdificacionList;
    }

    public void setCatastroPredialEdificacionList(List<CatastroPredialEdificacion> catastroPredialEdificacionList) {
        this.catastroPredialEdificacionList = catastroPredialEdificacionList;
    }

    public List<CatastroPredialInfraestructura> getCatastroPredialInfraestructuraList() {
        return catastroPredialInfraestructuraList;
    }

    public void setCatastroPredialInfraestructuraList(List<CatastroPredialInfraestructura> catastroPredialInfraestructuraList) {
        this.catastroPredialInfraestructuraList = catastroPredialInfraestructuraList;
    }

    public List<CatastroPredialInfAnt> getCatastroPredialInfAntList() {
        return catastroPredialInfAntList;
    }

    public void setCatastroPredialInfAntList(List<CatastroPredialInfAnt> catastroPredialInfAntList) {
        this.catastroPredialInfAntList = catastroPredialInfAntList;
    }

    public List<Mejora> getMejoraList() {
        return mejoraList;
    }

    public void setMejoraList(List<Mejora> mejoraList) {
        this.mejoraList = mejoraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catpreCodigo != null ? catpreCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatastroPredial)) {
            return false;
        }
        CatastroPredial other = (CatastroPredial) object;
        if ((this.catpreCodigo == null && other.catpreCodigo != null) || (this.catpreCodigo != null && !this.catpreCodigo.equals(other.catpreCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.sirec.ejb.entidades.CatastroPredial[ catpreCodigo=" + catpreCodigo + " ]";
    }
    
}
