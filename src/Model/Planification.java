package Model;

import Model.Calcules.Calculateur;
import Model.Calcules.Djikstra;
import Model.Calcules.PetalClustring;
import Model.Metier.DemandeLivraisons;
import Model.Metier.Livraison;
import Model.Metier.Plan;
import Model.Metier.Tournee;
import Model.XMLHelpers.DemandeLivraisonsXMLHelperDom4J;
import Model.XMLHelpers.PlanXMLHelperDom4J;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Planification extends Observable {
    private Plan plan;
    private DemandeLivraisons demandeLivraisons;
    private Calculateur calculateur;
    private List<Tournee> tournees;

    public boolean chargerPlan(File fichier) {
        plan = new PlanXMLHelperDom4J().getPlan(fichier);
        if (plan != null){
        	calculateur = new Calculateur(plan);
        	notifierAbonnes("plan");
        	return true;
        }
        return false;
    }

    public boolean chargerDemandesDeLivraisons(File fichierXML) {
        demandeLivraisons = new DemandeLivraisonsXMLHelperDom4J().getDemandeLivraisons(fichierXML);
        if (demandeLivraisons == null){
        	return false;
        }
        
        if(!plan.getNoeuds().contains(demandeLivraisons.getEntrepot())){
    		return false;
    	}
        for(Livraison l : demandeLivraisons.getPointsDeLivraisons()){
        	if(!plan.getNoeuds().contains(l)){
        		return false;
        	}
        }
        notifierAbonnes("livraisons");
    	return true;
        
    }

    public void calculerTournees(int nombreLivreurs) {
        tournees = calculateur.getTournees(demandeLivraisons, nombreLivreurs);
        notifierAbonnes("tournees");
    }

    public Livraison ajouterPointDeLivraison(String idPointLivraison, int duree, Livraison livraison1, Livraison livraison2){
        Livraison livraison = null;
        for (Tournee tournee: tournees){
            if (tournee.consecutives(livraison1,livraison2)){
                livraison = new Livraison(idPointLivraison,duree);
                demandeLivraisons.getPointsDeLivraisons().add(livraison);
                tournee.ajouterLivraison(livraison, livraison1,livraison2,plan);
                break;
            }
        }
        notifierAbonnes("livraisons");
        notifierAbonnes("tournees");
        return livraison;
    }

    public void deplacerLivraison(Livraison livraisonADeplacer, Livraison livraison1, Livraison livraison2){
        boolean supprimee = false;
        boolean ajoutee = false;
        for (Tournee tournee : tournees){
            if (tournee.consecutives(livraison1,livraison2)) {
                tournee.ajouterLivraison(livraisonADeplacer, livraison1, livraison2, plan);
                ajoutee = true;
            }
            else if (tournee.contientLivraison(livraisonADeplacer)) {
                tournee.supprimerLivraison(livraisonADeplacer,plan);
                supprimee = true;
            }

            if (supprimee && ajoutee) break;
        }

        notifierAbonnes("livraisons");
        notifierAbonnes("tournees");
    }

    public void supprimerPointDeLivraison(Livraison livraison){
        for (Tournee tournee: tournees){
            if (tournee.contientLivraison(livraison)){
                tournee.supprimerLivraison(livraison,plan);
                demandeLivraisons.getPointsDeLivraisons().remove(livraison);
                break;
            }
        }
        notifierAbonnes("livraisons");
        notifierAbonnes("tournees");
    }
    
    public void MAJAffichage(){
    	notifierAbonnes("plan");
    	notifierAbonnes("livraisons");
        notifierAbonnes("tournees");
    }
    
    public void supprimerPlan(){
    	supprimerDemandesLivraisons();
    	plan = null;
    }
    
    public void supprimerDemandesLivraisons(){
    	tournees = null;
    	demandeLivraisons = null;
    }

    public Plan getPlan() {
        return plan;
    }

    public DemandeLivraisons getDemandeLivraisons() {
        return demandeLivraisons;
    }

    public List<Tournee> getTournees() {
        return tournees;
    }

    public void setTournees(List<Tournee> t) {
        tournees = t;
    }

    private void notifierAbonnes(String quoi) {
        setChanged();
        notifyObservers(quoi);
    }

    public boolean livraisonsConsecutives(Livraison livraison1, Livraison livraison2){
        for (Tournee tournee : tournees){
            if (tournee.consecutives(livraison1,livraison2)) return true;
        }

        return false;
    }

    public String getNomDeLaRue(String idOrigine, String idDestination) {
        List<Plan.Troncon> tronconList = plan.getSuccesseurs(idOrigine);
        for (Plan.Troncon troncon : tronconList)
            if (troncon.getDestination().equals(idDestination)) return troncon.getNomDeLaRue();
        return "Pas Du Nom";
    }
}
