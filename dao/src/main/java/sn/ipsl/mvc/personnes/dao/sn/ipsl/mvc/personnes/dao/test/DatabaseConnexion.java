package sn.ipsl.mvc.personnes.dao.sn.ipsl.mvc.personnes.dao.test;

import sn.ipsl.mvc.personnes.entites.Personne;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class DatabaseConnexion {


    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ipslPU");
    EntityManager em = emf.createEntityManager();

    public List<Personne> getAll() {
        return em.createQuery("from Personne").getResultList();
    }

    public static void main(String args[]) {
        DatabaseConnexion databaseConnexion=new DatabaseConnexion();
        databaseConnexion.getAll();
    }


}
