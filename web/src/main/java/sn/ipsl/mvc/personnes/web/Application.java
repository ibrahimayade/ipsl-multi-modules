package sn.ipsl.mvc.personnes.web;

import sn.ipsl.mvc.personnes.dao.DaoException;
import sn.ipsl.mvc.personnes.dao.DaoImpl;
import sn.ipsl.mvc.personnes.entites.Personne;
import sn.ipsl.mvc.personnes.metier.MetierImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Application extends HttpServlet {
	// param�tres d'instance
	private String urlErreurs = null;

	private ArrayList erreursInitialisation = new ArrayList<String>();

	private String[] parametres = { "urlList", "urlEdit", "urlErreurs" };

	private Map params = new HashMap<String, String>();

	// service
    MetierImpl metier = null;

	// init
	@SuppressWarnings("unchecked")
	public void init() throws ServletException {
		// on recupere les parametres d'initialisation de la servlet
		ServletConfig config = getServletConfig();
		// on traite les autres param�tres d'initialisation
		String valeur = null;
		for (int i = 0; i < parametres.length; i++) {
			// valeur du param�tre
			valeur = config.getInitParameter(parametres[i]);
			// param�tre pr�sent ?
			if (valeur == null) {
				// on note l'erreur
				erreursInitialisation.add("Le param�tre [" + parametres[i]
						+ "] n'a pas été initialisé");
			} else {
				// on m�morise la valeur du param�tre
				params.put(parametres[i], valeur);
			}
		}
		// l'url de la vue [erreurs] a un traitement particulier
		urlErreurs = config.getInitParameter("urlErreurs");
		if (urlErreurs == null)
			throw new ServletException(
					"Le paramétre [urlErreurs] n'a pas été initialisé");
		// instanciation de la couche [dao]
        metier = new MetierImpl();
        metier.getAll();
		// instanciation de la couche [service]


	}

	// GET
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// on v�rifie comment s'est pass�e l'initialisation de la servlet
		if (erreursInitialisation.size() != 0) {
			// on passe la main � la page d'erreurs
			request.setAttribute("erreurs", erreursInitialisation);
			getServletContext().getRequestDispatcher(urlErreurs).forward(
					request, response);
			// fin
			return;
		}
		// on récupére la méthode d'envoi de la requééte
		String parametres = request.getMethod().toLowerCase();
		// on récupére l'action à exécuter
		String action = request.getPathInfo();
		// action ?
		if (action == null) {
			action = "/list";
		}
		// ex�cution action
		if (parametres.equals("get") && action.equals("/list")) {
			// liste des personnes
			doListPersonnes(request, response);
			return;
		}
		if (parametres.equals("get") && action.equals("/delete")) {
			// suppression d'une personne
			doDeletePersonne(request, response);
			return;
		}
		if (parametres.equals("get") && action.equals("/edit")) {
			// pr"sentation formulaire ajout / modification d'une personne
			doEditPersonne(request, response);
			return;
		}
		if (parametres.equals("post") && action.equals("/validate")) {
			// validation formulaire ajout / modification d'une personne
			doValidatePersonne(request, response);
			return;
		}
		// autres cas
		doListPersonnes(request, response);
	}

	// affichage liste des personnes
	private void doListPersonnes(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// le mod�le de la vue [list]
		request.setAttribute("personnes", metier.getAll());
		// affichage de la vue [list]
		getServletContext()
				.getRequestDispatcher((String) params.get("urlList")).forward(
						request, response);
	}

	// modification / ajout d'une personne
	private void doEditPersonne(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// on r�cup�re l'id de la personne
		int id = Integer.parseInt(request.getParameter("id"));
		// ajout ou modification ?
		Personne personne = null;
		if (id != -1) {
			// modification - on r�cup�re la personne � modifier
			personne = metier.getOne(id);
		} else {
			// ajout - on cr�e une personne vide
			personne = new Personne();
			personne.setId(-1);
		}
		// on met l'objet [Personne] dans le mod�le de la vue [edit]
		request.setAttribute("erreurEdit", "");
		request.setAttribute("id", personne.getId());
		request.setAttribute("version", personne.getVersion());
		request.setAttribute("prenom", personne.getPrenom());
		request.setAttribute("nom", personne.getNom());
		Date dateNaissance = personne.getDateNaissance();
		if (dateNaissance != null) {
			request.setAttribute("dateNaissance", new SimpleDateFormat(
					"dd/MM/yyyy").format(dateNaissance));
		} else {
			request.setAttribute("dateNaissance", "");
		}
		request.setAttribute("marie", personne.getMarie());
		request.setAttribute("nbEnfants", personne.getNbEnfants());
		// affichage de la vue [edit]
		getServletContext()
				.getRequestDispatcher((String) params.get("urlEdit")).forward(
						request, response);
	}

	// validation modification / ajout d'une personne
	private void doDeletePersonne(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// on r�cup�re l'id de la personne
		int id = Integer.parseInt(request.getParameter("id"));
		// on supprime la personne
        metier.deleteOne(id);
		// on redirige vers la liste des personnes
		response.sendRedirect("list");
	}

	// validation modification / ajout d'une personne
	public void doValidatePersonne(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// on r�cup�re les �l�ments post�s
		boolean formulaireErrone = false;
		boolean erreur;
		// le pr�nom
		String prenom = request.getParameter("prenom").trim();
		// pr�nom valide ?
		if (prenom.length() == 0) {
			// on note l'erreur
			request.setAttribute("erreurPrenom", "Le pr�nom est obligatoire");
			formulaireErrone = true;
		}
		// le nom
		String nom = request.getParameter("nom").trim();
		// pr�nom valide ?
		if (nom.length() == 0) {
			// on note l'erreur
			request.setAttribute("erreurNom", "Le nom est obligatoire");
			formulaireErrone = true;
		}
		// la date de naissance
		Date dateNaissance = null;
		try {
			dateNaissance = new SimpleDateFormat("dd/MM/yyyy").parse(request
					.getParameter("dateNaissance").trim());
System.out.println(dateNaissance);
		} catch (ParseException e) {
			// on note l'erreur
			request.setAttribute("erreurDateNaissance", "Date incorrecte");
			formulaireErrone = true;
		}
		// �tat marital
		boolean marie = Boolean.parseBoolean(request.getParameter("marie")
				.trim());
		// nombre d'enfants
		int nbEnfants = 0;
		erreur = false;
		try {
			nbEnfants = Integer.parseInt(request.getParameter("nbEnfants")
					.trim());
			if (nbEnfants < 0) {
				erreur = true;
			}
		} catch (NumberFormatException ex) {
			// on note l'erreur
			erreur = true;
		}
		// nombre d'enfants erron� ?
		if (erreur) {
			// on signale l'erreur
			request.setAttribute("erreurNbEnfants",
					"Nombre d'enfants incorrect");
			formulaireErrone = true;
		}
		// id de la personne
		int id = Integer.parseInt(request.getParameter("id"));
		// version
		long version = Long.parseLong(request.getParameter("version"));
		// le formulaire est-il erron� ?
		if (formulaireErrone) {
			// on r�affiche le formulaire avec les messages d'erreurs
			showFormulaire(request, response, "");
			// fini
			return;
		}
		// le formulaire est correct - on enregistre la personne
		Personne personne = new Personne(id, prenom, nom, dateNaissance, marie,
				nbEnfants);
		personne.setVersion(version);
		try {
			// enregistrement
            metier.saveOne(personne);
		} catch (DaoException ex) {
			// on r�affiche le formulaire avec le message de l'erreur survenue
			showFormulaire(request, response, ex.getMessage());
			// fini
			return;
		}
		// on redirige vers la liste des personnes
		response.sendRedirect("list");
	}

	// affichage formulaire pr�-rempli
	private void showFormulaire(HttpServletRequest request,
			HttpServletResponse response, String erreurEdit)
			throws ServletException, IOException {
		// on pr�pare le mod�le de la vue [edit]
		request.setAttribute("erreurEdit", erreurEdit);
		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("version", request.getParameter("version"));
		request.setAttribute("prenom", request.getParameter("prenom").trim());
		request.setAttribute("nom", request.getParameter("nom").trim());
		request.setAttribute("dateNaissance", request.getParameter(
				"dateNaissance").trim());
		request.setAttribute("marie", request.getParameter("marie"));
		request.setAttribute("nbEnfants", request.getParameter("nbEnfants")
				.trim());
		// affichage de la vue [edit]
		getServletContext()
				.getRequestDispatcher((String) params.get("urlEdit")).forward(
						request, response);
	}

	// post
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// on passe la main au GET
		doGet(request, response);
	}

}
