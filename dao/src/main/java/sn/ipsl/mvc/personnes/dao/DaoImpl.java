package sn.ipsl.mvc.personnes.dao;

import sn.ipsl.mvc.personnes.entites.Personne;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class DaoImpl implements IDao {

	// une liste de personnes
	private ArrayList<Personne> personnes = new ArrayList<Personne>();

	// n� de la prochaine personne
	private int id = 0;

	// constructeur
	public void init() {
		try {
			Personne p1 = new Personne(-1, "Ibrahima", "YADE",
					new SimpleDateFormat("dd/MM/yyyy").parse("13/11/1984"),
					true, 2);
			saveOne(p1);
			Personne p2 = new Personne(-1, "Dame", "Diong",
					new SimpleDateFormat("dd/MM/yyyy").parse("12/02/1985"),
					false, 1);
			saveOne(p2);
			Personne p3 = new Personne(-1, "Samba", "Sidibe",
					new SimpleDateFormat("dd/MM/yyyy").parse("01/03/1986"),
					false, 0);
			saveOne(p3);
		} catch (ParseException ex) {
			throw new DaoException(
					"Erreur d'initialisation de la couche [dao] : "
							+ ex.toString(), 1);
		}
	}

	// liste des personnes
	public Collection getAll() {
		return personnes;
	}

	// obtenir une personne en particulier
	public Personne getOne(int id) {
		// on cherche la personne
		int i = getPosition(id);
		// a-t-on trouv� ?
		if (i != -1) {
			return new Personne(((Personne) personnes.get(i)));
		} else {
			throw new DaoException("Personne d'id [" + id + "] inconnue", 2);
		}
	}

	// ajouter ou modifier une personne
	public void saveOne(Personne personne) {
		// le param�tre personne est-il valide ?
		check(personne);
		// ajout ou modification ?
		if (personne.getId() == -1) {
			// ajout
			personne.setId(getNextId());
			personne.setVersion(1);
			personnes.add(personne);
			return;
		}
		// modification - on cherche la personne
		int i = getPosition(personne.getId());
		// a-t-on trouv� ?
		if (i == -1) {
			throw new DaoException("La personne d'Id [" + personne.getId()
					+ "] qu'on veut modifier n'existe pas", 2);
		}
		// a-t-on la bonne version de l'original ?
		Personne original = (Personne) personnes.get(i);
		if (original.getVersion() != personne.getVersion()) {
			throw new DaoException("L'original de la personne [" + personne
					+ "] a changé depuis sa lecture initiale", 3);
		}
		// on attend 10 ms (pour tests uniquement, changer false en true)
		if (true)
			wait(10);
		// c'est bon - on fait la modification
		original.setVersion(original.getVersion() + 1);
		original.setNom(personne.getNom());
		original.setPrenom(personne.getPrenom());
		original.setDateNaissance((personne.getDateNaissance()));
		original.setMarie(personne.getMarie());
		original.setNbEnfants(personne.getNbEnfants());
	}

	// suppression d'une personne
	public void deleteOne(int id) {
		// on cherche la personne
		int i = getPosition(id);
		// a-t-on trouv� ?
		if (i == -1) {
			throw new DaoException("Personne d'id [" + id + "] inconnue", 2);
		} else {
			// on supprime la personne
			personnes.remove(i);
		}
	}

	// g�n�rateur d'id
	private int getNextId() {
		id++;
		return id;
	}

	// rechercher une personne
	private int getPosition(int id) {
		int i = 0;
		boolean trouve = false;
		// on parcourt la liste des personnes
		while (i < personnes.size() && !trouve) {
			if (id == ((Personne) personnes.get(i)).getId()) {
                trouve = true;
			} else {
				i++;
			}
		}
		// r�sultat ?
		return trouve ? i : -1;
	}

	// v�rification d'une personne
	private void check(Personne p) {
		// personne p
		if (p == null) {
			throw new DaoException("Personne null", 10);
		}
		// id
		if (p.getId() != -1 && p.getId() < 0) {
			throw new DaoException("Id [" + p.getId() + "] invalide", 11);
		}
		// date de naissance
		if (p.getDateNaissance() == null) {
			throw new DaoException("Date de naissance manquante", 12);
		}
		// nombre d'enfants
		if (p.getNbEnfants() < 0) {
			throw new DaoException("Nombre d'enfants [" + p.getNbEnfants()
					+ "] invalide", 13);
		}
		// nom
		if (p.getNom() == null || p.getNom().trim().length() == 0) {
			throw new DaoException("Nom manquant", 14);
		}
		// pr�nom
		if (p.getPrenom() == null || p.getPrenom().trim().length() == 0) {
			throw new DaoException("Pr�nom manquant", 15);
		}
	}

	// attente
	private void wait(int N) {
		// on attend N ms
		try {
			Thread.sleep(N);
		} catch (InterruptedException e) {
			// on affiche la trace de l'exception
			e.printStackTrace();
			return;
		}
	}
}
