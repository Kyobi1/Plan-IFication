package Vue;

import Model.Metier.DemandeLivraisons;
import Model.Metier.Plan;
import Model.Metier.Tournee;
import Model.Planification;
import javafx.scene.Parent;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Classe de gestion globale de l'affichage de l'application.
 * @author H4104
 * @see Model.Metier.DemandeLivraisons
 */
public abstract class Vue extends Parent implements Observer {

    Plan plan = null;
    DemandeLivraisons demandeLivraisons = null;
    List<Tournee> tournees = null;
    Planification planification;

    /**
     * Constructeur de la classe Vue.
     * @param planification que l'on veut prendre en compte
     */
    Vue(Planification planification) {
        this.planification = planification;
        planification.addObserver(this);
    }
    
    /**
     * M�thode d'obtention de la demande de livraisons.
     * @return demandeLivraisons correspondant � l'attribut de la vue qui concerne les livraisons � effectuer
     */
    public DemandeLivraisons getDemandeLivraisons(){
    	return demandeLivraisons;
    }
    
    /**
     * M�thode permettant la transmission d'informations aux observeurs, pour lancer les appels de m�thodes.
     * @param o correspondant � la classe observ�
     * @param arg correspondant � l'�l�ment observ�
     */
    @Override
    public void update(Observable o, Object arg) {
        String quoi = (String) arg;

        switch (quoi) {
            case "plan": {
                plan = planification.getPlan();
                dessinerPlan();
            }
            break;
            case "livraisons": {
                demandeLivraisons = planification.getDemandeLivraisons();
                dessinerDemandeDeLivraisons();
            }
            break;
            case "tournees": {
                tournees = planification.getTournees();
                dessinerTournees();
            }
        }
    }

    /**
     * M�thode Abstraire pour dessiner le plan.
     * @see Vue.VueGraphique
     */
    abstract void dessinerPlan();

    /**
     * M�thode Abstraire pour dessiner la demande de livraisons.
     * @see Vue.VueGraphique
     */
    abstract void dessinerDemandeDeLivraisons();

    /**
     * M�thode Abstraire pour dessiner les tourn�es.
     * @see Vue.VueGraphique
     */
    abstract void dessinerTournees();

}

