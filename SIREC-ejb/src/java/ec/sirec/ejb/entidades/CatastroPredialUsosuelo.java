/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.sirec.ejb.entidades;

import java.io.Serializable;
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
@Table(name = "sirec.catastro_predial_usosuelo")
@NamedQueries({
    @NamedQuery(name = "CatastroPredialUsosuelo.findAll", query = "SELECT c FROM CatastroPredialUsosuelo c")})
public class CatastroPredialUsosuelo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "catpreusu_codigo")
    private Integer catpreusuCodigo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "catpreusu_item")
    private int catpreusuItem;
    @Column(name = "catpreusu_valor")
    private Integer catpreusuValor;
    @JoinColumn(name = "catpre_codigo", referencedColumnName = "catpre_codigo")
    @ManyToOne(optional = false)
    private CatastroPredial catpreCodigo;
    @JoinColumn(name = "catdet_codigo", referencedColumnName = "catdet_codigo")
    @ManyToOne(optional = false)
    private CatalogoDetalle catdetCodigo;

    public CatastroPredialUsosuelo() {
    }

    public CatastroPredialUsosuelo(Integer catpreusuCodigo) {
        this.catpreusuCodigo = catpreusuCodigo;
    }

    public CatastroPredialUsosuelo(Integer catpreusuCodigo, int catpreusuItem) {
        this.catpreusuCodigo = catpreusuCodigo;
        this.catpreusuItem = catpreusuItem;
    }

    public Integer getCatpreusuCodigo() {
        return catpreusuCodigo;
    }

    public void setCatpreusuCodigo(Integer catpreusuCodigo) {
        this.catpreusuCodigo = catpreusuCodigo;
    }

    public int getCatpreusuItem() {
        return catpreusuItem;
    }

    public void setCatpreusuItem(int catpreusuItem) {
        this.catpreusuItem = catpreusuItem;
    }

    public Integer getCatpreusuValor() {
        return catpreusuValor;
    }

    public void setCatpreusuValor(Integer catpreusuValor) {
        this.catpreusuValor = catpreusuValor;
    }

    public CatastroPredial getCatpreCodigo() {
        return catpreCodigo;
    }

    public void setCatpreCodigo(CatastroPredial catpreCodigo) {
        this.catpreCodigo = catpreCodigo;
    }

    public CatalogoDetalle getCatdetCodigo() {
        return catdetCodigo;
    }

    public void setCatdetCodigo(CatalogoDetalle catdetCodigo) {
        this.catdetCodigo = catdetCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catpreusuCodigo != null ? catpreusuCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CatastroPredialUsosuelo)) {
            return false;
        }
        CatastroPredialUsosuelo other = (CatastroPredialUsosuelo) object;
        if ((this.catpreusuCodigo == null && other.catpreusuCodigo != null) || (this.catpreusuCodigo != null && !this.catpreusuCodigo.equals(other.catpreusuCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.sirec.ejb.entidades.CatastroPredialUsosuelo[ catpreusuCodigo=" + catpreusuCodigo + " ]";
    }
    
}
