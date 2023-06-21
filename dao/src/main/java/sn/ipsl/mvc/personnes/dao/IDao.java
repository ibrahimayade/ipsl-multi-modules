package sn.ipsl.mvc.personnes.dao;

import sn.ipsl.mvc.personnes.entites.Personne;

import java.util.Collection;

public interface IDao {
	// liste de toutes les personnes
	Collection getAll();
	// obtenir une personne particulière
	Personne getOne(int id);
	// ajouter/modifier une personne
	void saveOne(Personne personne);
	// supprimer une personne
	void deleteOne(int id);
}
