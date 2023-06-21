package sn.ipsl.mvc.personnes.entites;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
@Entity
@Table(name = "personne")
public class Personne {

    // identifiant unique de la personne
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERSONNE_SEQUENCE")
    @Column(name = "id")
    private int id;
    // la version actuelle
    @Version
    @Column(name = "version")
    private long version;
    // le nom
    @Column(name = "nom")
    private String nom;
    // le prénom
    @Column(name = "prenom")
    private String prenom;
    // la date de naissance
    @Column(name = "naissance")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dateNaissance;

    // l'état marital
    @Column(name = "marie")
    private boolean marie = false;
    // le nombre d'enfants
    @Column(name = "enfants")
    private int nbEnfants;

    // getters - setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public boolean getMarie() {
        return marie;
    }

    public void setMarie(boolean marie) {
        this.marie = marie;
    }

    public int getNbEnfants() {
        return nbEnfants;
    }

    public void setNbEnfants(int nbEnfants) {
        this.nbEnfants = nbEnfants;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom.trim();
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom.trim();
    }

    // constructeur par défaut
    public Personne() {

    }

    // constructeur avec initialisation des champs de la personne
    public Personne(int id, String prenom, String nom, Date dateNaissance,
                    boolean marie, int nbEnfants) {
        setId(id);
        setNom(nom);
        setPrenom(prenom);
        setDateNaissance(dateNaissance);
        setMarie(marie);
        setNbEnfants(nbEnfants);
    }

    // constructeur d'une personne par recopie d'une autre personne
    public Personne(Personne p) {
        setId(p.getId());
        setVersion(p.getVersion());
        setNom(p.getNom());
        setPrenom(p.getPrenom());
        setDateNaissance(p.getDateNaissance());
        setMarie(p.getMarie());
        setNbEnfants(p.getNbEnfants());
    }

    // création de la copie d'une personne
//	public Personne clone(Personne p) {
//		return new Personne(p);
//	}

    // toString
    public String toString() {
        return "[" + id + "," + version + "," + prenom + "," + nom + ","
            + new SimpleDateFormat("dd/MM/yyyy").format(dateNaissance)
            + "," + marie + "," + nbEnfants + "]";
    }
}
