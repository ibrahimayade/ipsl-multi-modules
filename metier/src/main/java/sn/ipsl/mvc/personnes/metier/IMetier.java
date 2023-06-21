package sn.ipsl.mvc.personnes.metier;


import sn.ipsl.mvc.personnes.entites.Personne;

import java.util.Collection;

public interface IMetier {
        // liste de toutes les personnes
        Collection getAll();
        // obtenir une personne particuli�re
        Personne getOne(int id);
        // ajouter/modifier une personne
        void saveOne(Personne personne);
        // supprimer une personne
        void deleteOne(int id);
}
