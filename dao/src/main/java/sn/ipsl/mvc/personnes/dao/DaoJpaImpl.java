package sn.ipsl.mvc.personnes.dao;

import sn.ipsl.mvc.personnes.entites.Personne;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class DaoJpaImpl  implements IDao, Serializable {
    @PersistenceContext(unitName="ipslPU")
    private EntityManager em;
    @Override
    public Collection getAll() {
        Query query=em.createQuery("from Personne");
       List<Personne> list= query.getResultList();


        return null;
    }

    @Override
    public Personne getOne(int id) {
        return null;
    }

    @Override
    public void saveOne(Personne personne) {
        em.persist(personne);

    }

    @Override
    public void deleteOne(int id) {
        em.remove(id);


    }
}
