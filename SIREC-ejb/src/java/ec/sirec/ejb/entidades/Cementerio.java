/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "sirec.cementerio")
@NamedQueries({
    @NamedQuery(name = "Cementerio.findAll", query = "SELECT c FROM Cementerio c")})
public class Cementerio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cem_codigo")
    private Integer cemCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "cem_nombre_occiso")
    private String cemNombreOcciso;
    @Size(max = 20)
    @Column(name = "cem_num_papeleta")
    private String cemNumPapeleta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cem_fecha_fallece")
    @Temporal(TemporalType.DATE)
    private Date cemFechaFallece;
    @Column(name = "cem_fecha_fin_contrato")
    @Temporal(TemporalType.DATE)
    private Date cemFechaFinContrato;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cem_genero")
    private String cemGenero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cem_ubicacion")
    private String cemUbicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cem_estado")
    private String cemEstado;
    @Size(max = 20)
    @Column(name = "cem_num_nicho")
    private String cemNumNicho;
    @Size(max = 20)
    @Column(name = "cem_num_suelo")
    private String cemNumSuelo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "cem_tipo")
    private String cemTipo;
    @Size(max = 2147483647)
    @Column(name = "cem_observacion")
    private String cemObservacion;
    @JoinColumn(name = "usu_identificacion", referencedColumnName = "usu_identificacion")
    @ManyToOne(optional = false)
    private SegUsuario usuIdentificacion;
    @JoinColumn(name = "pro_ci", referencedColumnName = "pro_ci")
    @ManyToOne(optional = false)
    private Propietario proCi;
    @JoinColumn(name = "catdet_parroquia", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetParroquia;

    public Cementerio() {
    }

    public Cementerio(Integer cemCodigo) {
        this.cemCodigo = cemCodigo;
    }

    public Cementerio(Integer cemCodigo, String cemNombreOcciso, Date cemFechaFallece, String cemGenero, String cemUbicacion, String cemEstado, String cemTipo) {
        this.cemCodigo = cemCodigo;
        this.cemNombreOcciso = cemNombreOcciso;
        this.cemFechaFallece = cemFechaFallece;
        this.cemGenero = cemGenero;
        this.cemUbicacion = cemUbicacion;
        this.cemEstado = cemEstado;
        this.cemTipo = cemTipo;
    }

    public Integer getCemCodigo() {
        return cemCodigo;
    }

    public void setCemCodigo(Integer cemCodigo) {
        this.cemCodigo = cemCodigo;
    }

    public String getCemNombreOcciso() {
        return cemNombreOcciso;
    }

    public void setCemNombreOcciso(String cemNombreOcciso) {
        this.cemNombreOcciso = cemNombreOcciso;
    }

    public String getCemNumPapeleta() {
        return cemNumPapeleta;
    }

    public void setCemNumPapeleta(String cemNumPapeleta) {
        this.cemNumPapeleta = cemNumPapeleta;
    }

    public Date getCemFechaFallece() {
        return cemFechaFallece;
    }

    public void setCemFechaFallece(Date cemFechaFallece) {
        this.cemFechaFallece = cemFechaFallece;
    }

    public Date getCemFechaFinContrato() {
        return cemFechaFinContrato;
    }

    public void setCemFechaFinContrato(Date cemFechaFinContrato) {
        this.cemFechaFinContrato = cemFechaFinContrato;
    }

    public String getCemGenero() {
        return cemGenero;
    }

    public void setCemGenero(String cemGenero) {
        this.cemGenero = cemGenero;
    }

    public String getCemUbicacion() {
        return cemUbicacion;
    }

    public void setCemUbicacion(String cemUbicacion) {
        this.cemUbicacion = cemUbicacion;
    }

    public String getCemEstado() {
        return cemEstado;
    }

    public void setCemEstado(String cemEstado) {
        this.cemEstado = cemEstado;
    }

    public String getCemNumNicho() {
        return cemNumNicho;
    }

    public void setCemNumNicho(String cemNumNicho) {
        this.cemNumNicho = cemNumNicho;
    }

    public String getCemNumSuelo() {
        return cemNumSuelo;
    }

    public void setCemNumSuelo(String cemNumSuelo) {
        this.cemNumSuelo = cemNumSuelo;
    }

    public String getCemTipo() {
        return cemTipo;
    }

    public void setCemTipo(String cemTipo) {
        this.cemTipo = cemTipo;
    }

    public String getCemObservacion() {
        return cemObservacion;
    }

    public void setCemObservacion(String cemObservacion) {
        this.cemObservacion = cemObservacion;
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

    public CatalogoDetalle getCatdetParroquia() {
        return catdetParroquia;
    }

    public void setCatdetParroquia(CatalogoDetalle catdetParroquia) {
        this.catdetParroquia = catdetParroquia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cemCodigo != null ? cemCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cementerio)) {
            return false;
        }
        Cementerio other = (Cementerio) object;
        if ((this.cemCodigo == null && other.cemCodigo != null) || (this.cemCodigo != null && !this.cemCodigo.equals(other.cemCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.sirec.ejb.entidades.Cementerio[ cemCodigo=" + cemCodigo + " ]";
    }
    
}
