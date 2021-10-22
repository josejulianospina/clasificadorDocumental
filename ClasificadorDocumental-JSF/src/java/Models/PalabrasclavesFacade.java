/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Julián
 */
@Stateless
public class PalabrasclavesFacade extends AbstractFacade<Palabrasclaves> {

    @PersistenceContext(unitName = "ClasificadorDocumental-JSFPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PalabrasclavesFacade() {
        super(Palabrasclaves.class);
    }
    
}
