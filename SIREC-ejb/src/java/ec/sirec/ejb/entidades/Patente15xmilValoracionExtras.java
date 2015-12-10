/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.entidades;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author DAVID GUAN
 */
@Entity
@Table(name = "sirec.patente_15xmil_valoracion_extras")
@NamedQueries({
    @NamedQuery(name = "Patente15xmilValoracionExtras.findAll", query = "SELECT p FROM Patente15xmilValoracionExtras p")})
public class Patente15xmilValoracionExtras implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pat15valext_codigo")
    private Integer pat15valextCodigo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "pat15valext_base")
    private BigDecimal pat15valextBase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pat15valext_valor")
    private BigDecimal pat15valextValor;
    @JoinColumn(name = "pat15val_codigo", referencedColumnName = "pat15val_codigo")
    @ManyToOne(optional = false)
    private Patente15xmilValoracion pat15valCodigo;
    @JoinColumn(name = "adided_codigo", referencedColumnName = "adided_codigo")
    @ManyToOne(optional = false)    
    private AdicionalesDeductivos adidedCodigo;
    @Column(name = "pat15valext_enti_pub")
    private Boolean pat15valextentiPub;
    @Column(name = "pat15valext_funBenEdu")
    private Boolean pat15valextfunBenEdu;
    @Column(name = "pat15valext_leyFomArtes")
    private Boolean pat15valextleyFomArtes;
    @Column(name = "pat15valext_actAgro")
    private Boolean pat15valextactAgro;
    @Column(name = "pat15valext_coop")
    private Boolean pat15valextCoop;
    @Column(name = "pat15valext_multiNac")
    private Boolean pat15valextmultiNac;
   

    public Patente15xmilValoracionExtras() {
    }

    public Patente15xmilValoracionExtras(Integer pat15valextCodigo) {
        this.pat15valextCodigo = pat15valextCodigo;
    }

    public Patente15xmilValoracionExtras(Integer pat15valextCodigo, BigDecimal pat15valextBase, BigDecimal pat15valextValor) {
        this.pat15valextCodigo = pat15valextCodigo;
        this.pat15valextBase = pat15valextBase;
        this.pat15valextValor = pat15valextValor;
    }

    public Integer getPat15valextCodigo() {
        return pat15valextCodigo;
    }

    public void setPat15valextCodigo(Integer pat15valextCodigo) {
        this.pat15valextCodigo = pat15valextCodigo;
    }

    public BigDecimal getPat15valextBase() {
        return pat15valextBase;
    }

    public void setPat15valextBase(BigDecimal pat15valextBase) {
        this.pat15valextBase = pat15valextBase;
    }

    public BigDecimal getPat15valextValor() {
        return pat15valextValor;
    }

    public void setPat15valextValor(BigDecimal pat15valextValor) {
        this.pat15valextValor = pat15valextValor;
    }

    public Patente15xmilValoracion getPat15valCodigo() {
        return pat15valCodigo;
    }

    public void setPat15valCodigo(Patente15xmilValoracion pat15valCodigo) {
        this.pat15valCodigo = pat15valCodigo;
    }

    public AdicionalesDeductivos getAdidedCodigo() {
        return adidedCodigo;
    }

    public void setAdidedCodigo(AdicionalesDeductivos adidedCodigo) {
        this.adidedCodigo = adidedCodigo;
    }

    public Boolean getPat15valextentiPub() {
        return pat15valextentiPub;
    }

    public void setPat15valextentiPub(Boolean pat15valextentiPub) {
        this.pat15valextentiPub = pat15valextentiPub;
    }

    public Boolean getPat15valextfunBenEdu() {
        return pat15valextfunBenEdu;
    }

    public void setPat15valextfunBenEdu(Boolean pat15valextfunBenEdu) {
        this.pat15valextfunBenEdu = pat15valextfunBenEdu;
    }

    public Boolean getPat15valextleyFomArtes() {
        return pat15valextleyFomArtes;
    }

    public void setPat15valextleyFomArtes(Boolean pat15valextleyFomArtes) {
        this.pat15valextleyFomArtes = pat15valextleyFomArtes;
    }

    public Boolean getPat15valextactAgro() {
        return pat15valextactAgro;
    }

    public void setPat15valextactAgro(Boolean pat15valextactAgro) {
        this.pat15valextactAgro = pat15valextactAgro;
    }

    public Boolean getPat15valextCoop() {
        return pat15valextCoop;
    }

    public void setPat15valextCoop(Boolean pat15valextCoop) {
        this.pat15valextCoop = pat15valextCoop;
    }

    public Boolean getPat15valextmultiNac() {
        return pat15valextmultiNac;
    }

    public void setPat15valextmultiNac(Boolean pat15valextmultiNac) {
        this.pat15valextmultiNac = pat15valextmultiNac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pat15valextCodigo != null ? pat15valextCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Patente15xmilValoracionExtras)) {
            return false;
        }
        Patente15xmilValoracionExtras other = (Patente15xmilValoracionExtras) object;
        if ((this.pat15valextCodigo == null && other.pat15valextCodigo != null) || (this.pat15valextCodigo != null && !this.pat15valextCodigo.equals(other.pat15valextCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.sirec.ejb.entidades.Patente15xmilValoracionExtras[ pat15valextCodigo=" + pat15valextCodigo + " ]";
    }
    
}
