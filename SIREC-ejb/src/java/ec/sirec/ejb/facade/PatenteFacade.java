/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.sirec.ejb.facade;

import ec.sirec.ejb.entidades.Patente;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author dguano
 */
@Stateless
public class PatenteFacade extends AbstractFacade<Patente> {

    @PersistenceContext(unitName = "SIREC-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PatenteFacade() {
        super(Patente.class);
    }

    public Patente retornaNumSecuencial() throws Exception {
        String sql = "select max(c) from Patente c ";
        Query q = em.createQuery(sql);
        return (Patente) q.getResultList().get(0);
    }

}
