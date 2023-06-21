package sn.ipsl.mvc.personnes.metier;


import sn.ipsl.mvc.personnes.dao.DaoImpl;
import sn.ipsl.mvc.personnes.dao.DaoJpaImpl;
import sn.ipsl.mvc.personnes.dao.IDao;
import sn.ipsl.mvc.personnes.entites.Personne;

import java.util.Collection;


public class MetierImpl implements IMetier{
    // la couche [dao]
    private IDao dao;//=new DaoJpaImpl();

    public IDao getDao() {
        return dao;
    }


    public void setDao(IDao dao) {
        this.dao = new DaoJpaImpl();
    }

    // liste des personnes
    public synchronized Collection getAll() {
        return dao.getAll();
    }

    // obtenir une personne en particulier
    public synchronized Personne getOne(int id) {

        return dao.getOne(id);
    }

    // ajouter ou modifier une personne
    public synchronized void saveOne(Personne personne) {
        dao.saveOne(personne);
    }

    // suppression d'une personne
    public synchronized void deleteOne(int id) {

        dao.deleteOne(id);
        }
    }
