package Controleur;

import Model.Metier.Livraison;

import java.io.File;

/**
 * Interface pour la classe EtatDefaut
 * @author H4104
 *
 */
public interface Etat {
	
    void init();

    String getMessage();
    
    void boutonChargerPlan(File fichierXML);

    void definirNombreLivreur(int nbLivreurs);

    void boutonChargerDemandeLivraison(File fichierXML);

    void boutonCalculerTournees(int nbLivreurs);

    void boutonAjouterLivraison();

    void boutonSuprimmerLivraison();

    void boutonDeplacerLivraison();

    void boutonValider(ListeCommandes listeCommandes);

    void boutonAnnuler();

    boolean livraisonSelectionne(Livraison livraison);

    void livraisonDeselectionnee(Livraison livraison);

    boolean noeudSelectionne(String idNoeud);

    void noeudDeselectionne(String idNoeud);
    
    void undo(ListeCommandes l);
    
    void redo(ListeCommandes l);

    void saisieNombreLivreurs();

    void saisieDuree(int duree);
}

