/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.facade;

import ec.sirec.ejb.entidades.Propietario;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dguano
 */
@Stateless
public class PropietarioFacade extends AbstractFacade<Propietario> {

    @PersistenceContext(unitName = "SIREC-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PropietarioFacade() {
        super(Propietario.class);
    }

    public Propietario retornaObjPropietarioPorCatastroPred(int catPreCodigo) throws Exception {
        String sql = "select p from Propietario  p, PropietarioPredio pp,CatastroPredial cp "
                + " where p.proCi=pp.proCi "
                + " and pp.catpreCodigo=cp.catpreCodigo "
                + " and cp.catpreCodigo=:codCatastro ";
        Query q = em.createQuery(sql);
        q.setParameter("codCatastro", catPreCodigo);
        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return (Propietario) q.getResultList().get(0);
        }
    }

}
