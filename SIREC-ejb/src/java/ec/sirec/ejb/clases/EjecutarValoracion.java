/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.clases;

import ec.sirec.ejb.entidades.CatalogoDetalle;
import ec.sirec.ejb.entidades.CatastroPredialAreas;
import ec.sirec.ejb.entidades.CatastroPredialPlusvaliaValoracion;
import ec.sirec.ejb.entidades.CatastroPredialValoracion;
import ec.sirec.ejb.entidades.Propietario;
import ec.sirec.ejb.entidades.SegUsuario;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author new
 */


public class EjecutarValoracion {
   
    private String catpreClaveCatastal;
    
    private CatastroPredialValoracion catastroPredialValoracion;
    private BigDecimal totalRecargos;
    private BigDecimal totalDeduciones;
    private BigDecimal totalExoneracion;
    private BigDecimal totalRegistro;
                                        
    private Integer catpreCodigo;
  
    private String catpreCodNacional;
   
    private String catpreCodLocal;
   
    private String catpreNumero;
   
    private String catpreNombreSector;
    
    private Double catpreCoordLatitud;

    private Double catpreCoordLongitud;
   
    private Double catpreLongitudFrentePrincipal;
  
    private Double catpreAreaTotal;
   
    private Double catpreAreaTotalEsc;
 
    private Double catpreAreaTotalCons;
   
    private String catpreColindanteNorte;
   
    private String catpreColindanteSur;
    
    private String catpreColindanteEste;
  
    private String catpreColindanteOeste;
  
    private Boolean catpreExisteEscrituras;
   
    private String catpreNumNotaria;
    
    private String catpreLugarEscritura;
    
    private Date catpreFechaRegPropiedad;
   
    private Boolean catpreTieneLegalizacion;
    
    private Integer catpreNumMedidoresLuz;
   
    private Integer catpreNumMedidoresAgua;
   
    private Integer catpreNumCarrilesAcceso;
   
    private Boolean catpreNegocioVivienda;
   
    private Integer catprePorcVivienda;
   
    private Integer catprePorcBodega;
    
    private Integer catprePorcGarage;
   
    private Integer catprePorcOtros;
   
    private Boolean catpreLinderosDefinidos;
   
    private Boolean catpreColaboracionProp;
   
    private String catpreNombreFuente;
   
    private Boolean catprePerDiscapacidad;
   
    private String catpreDiscCicarnet;
  
    private String catpreDiscNombre;
   
    private Integer catpreDiscPorc;
    
    private String catpreEmpadronador;
    
    private String catpreEmpadronadorCi;
   
    private String catpreSupervisor;
  
    private String catpreSupervisorCi;
   
    private String catpreQcqa;
  
    private String ultaccDetalle;
  
    private Date ultaccMarcatiempo;
 
    private List<CatastroPredialAreas> catastroPredialAreasList;
  
    private List<CatastroPredialValoracion> catastroPredialValoracionList;
  
    private List<CatastroPredialPlusvaliaValoracion> catastroPredialPlusvaliaValoracionList;
  
    private SegUsuario usuIdentificacion;
  
    private Propietario proCi;
   
    private CatalogoDetalle catdetFuenteInformacion;
  
    private CatalogoDetalle catdetAlicuotas;
   
    private CatalogoDetalle catdetDimension;
    
    private CatalogoDetalle catdetTipoFuncNegocio;
    
    private CatalogoDetalle catdetTipoNegocio;
    
    private CatalogoDetalle catdetLocalizacion;
    
    private CatalogoDetalle catdetTopografia;
   
    private CatalogoDetalle catdetForma;
   
    private CatalogoDetalle catdetDocRelevamiento;
   
    private CatalogoDetalle catdetTipoVia;
    
    private CatalogoDetalle catdetTipoUbicacion;
   
    private CatalogoDetalle catdetTipoProp1;
    
    private CatalogoDetalle catdetTipoProp2;
    
    private CatalogoDetalle catdetRefCartografica;
   
    private CatalogoDetalle catdetDominio;
   
    private CatalogoDetalle catdetTraslacionDominio;
   
    private CatalogoDetalle catdetOcupacion;
   
    private CatalogoDetalle catdetNoEdificado;
   
    private CatalogoDetalle catdetEnConstruccion;
   
    private CatalogoDetalle catdetCaracteristicasSuelo;
    
    
            
    
    public EjecutarValoracion(){
        
    }

    public EjecutarValoracion(Integer catpreCodigo) {
        this.catpreCodigo = catpreCodigo;
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

    public String getCatpreClaveCatastal() {
        return catpreClaveCatastal;
    }

    public void setCatpreClaveCatastal(String catpreClaveCatastal) {
        this.catpreClaveCatastal = catpreClaveCatastal;
    }

    public CatastroPredialValoracion getCatastroPredialValoracion() {
        return catastroPredialValoracion;
    }

    public void setCatastroPredialValoracion(CatastroPredialValoracion catastroPredialValoracion) {
        this.catastroPredialValoracion = catastroPredialValoracion;
    }

    public BigDecimal getTotalRecargos() {
        return totalRecargos;
    }

    public void setTotalRecargos(BigDecimal totalRecargos) {
        this.totalRecargos = totalRecargos;
    }

    public BigDecimal getTotalDeduciones() {
        return totalDeduciones;
    }

    public void setTotalDeduciones(BigDecimal totalDeduciones) {
        this.totalDeduciones = totalDeduciones;
    }

    public BigDecimal getTotalExoneracion() {
        return totalExoneracion;
    }

    public void setTotalExoneracion(BigDecimal totalExoneracion) {
        this.totalExoneracion = totalExoneracion;
    }

    public BigDecimal getTotalRegistro() {
        return totalRegistro;
    }

    public void setTotalRegistro(BigDecimal totalRegistro) {
        this.totalRegistro = totalRegistro;
    }

   
    
}
