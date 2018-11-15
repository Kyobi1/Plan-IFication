package Model.Metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Classe de gestion du plan contenant les �l�ments obtenus dans le fichier XML charg�.
 * @author H4104
 */
public class Plan {

    private final Map<String, List<Troncon>> plan;

    /**
     * Constructeur de la classe Plan.
     * @param plan correspondant au m�me plan mais sous forme d'une map de strign associ�s avec des listes de troncons
     */
    public Plan(Map<String, List<Troncon>> plan) {
        this.plan = plan;
    }

    /**
     * M�thode d'obtention des successeurs associ�s � un noeud pr�cis.
     * @param idNoeud correspondant  � l'identifiant du noeud selectionn�
     * @return List<Troncon> correspondant � cette liste de successeurs
     */
    public List<Troncon> getSuccesseurs(String idNoeud) {
        return plan.get(idNoeud);
    }

    /**
     * M�thode d'obtention des noeuds du plan.
     * @return List<String> correspondant � cette s�rie de noeuds
     */
    public List<String> getNoeuds() {
        return new ArrayList<>(plan.keySet());
    }

    /**
     * M�thode d'obtention d'un nom de rue.
     * @param idSource correspondant � l'identifiant du noeud d'origine de la rue
     * @param idDes correspondant � l'identifiant du noeud de destination de la rue
     * @return String correspondant au nom de la rue recherch�e
     */
    public String getNomDeLaRue(String idSource, String idDes){
        for (Troncon troncon : plan.get(idSource)){
            if (troncon.getDestination().equals(idDes)) return troncon.nomDeLaRue;
        }

        return "Pas du Nom";
    }

    /**
     * M�thode d'obtention de la distance associ�e � une rue
     * @param idSource correspondant � l'identifiant du noeud d'origine de la rue
     * @param idDes correspondant � l'identifiant du noeud de destination de la rue
     * @return double correspondant � la distance associ�e � la rue recherch�e
     */
    public double getDistance(String idSource, String idDestination) {
        List<Troncon> troncons = plan.get(idSource);
        for (Troncon troncon : troncons) {
            if (troncon.getDestination().equals(idDestination)) return troncon.longueur;
        }

        return Double.POSITIVE_INFINITY;
    }

    /**
     * M�thode de conversion du plan sous forme de string.
     * @String correspondant � la description du plan sous forme d'une simple string
     */
    @Override
    public String toString() {
        return "Plan{" +
                "plan=" + plan +
                '}';
    }

    /**
     * Classe de gestion des tron�ons du plan.
     * @author H4104
     */
    public static class Troncon {
        private final String idOrigine;
        private final String idDestination;
        private final double longueur;
        private final String nomDeLaRue;

        /**
         * Constructeur de la classe Troncon.
         * @param idOrigine correspondant � l'identifiant du noeud d'origine de ce tron�on
         * @param idDestination correspondant � l'identifiant du noeud de destination de ce tron�on
         * @param longueur correspondant � la distance associ�e au tron�on en question
         * @param nomDeLaRue correspondant au nom de rue associ� � ce tron�on
         */
        public Troncon(String idOrigine, String idDestination, double longueur, String nomDeLaRue) {
            this.idOrigine = idOrigine;
            this.idDestination = idDestination;
            this.longueur = longueur;
            this.nomDeLaRue = nomDeLaRue;
        }

        /**
         * M�thode d'obtention de la distance.
         * @return longueur correspondant � un double quantifiant la distance parcourue par ce tron�on
         */
        public double getLongueur() {
            return longueur;
        }

        /**
         * M�thode d'obtention du noeud d'origine.
         * @return idOrigine correspondant � l'identifiant du noeud de d�part du tron�on
         */
        public String getOrigine() {
            return idOrigine;
        }

        /**
         * M�thode d'obtention du noeud de destination.
         * @return idDestination correspondant � l'identifiant du noeud d'arriv�e du tron�on
         */
        public String getDestination() {
            return idDestination;
        }

        /**
         * M�thode d'obtention du nom de la rue.
         * @return nomDeLaRue correspondant � une string contenant le nom de la rue associ�e � ce tron�on
         */
        public String getNomDeLaRue() {
            return nomDeLaRue;
        }

        /**
         * M�thode de conversion du tron�on sous forme de string.
         * @String correspondant � la description du tron�on sous forme d'une simple string
         */
        @Override
        public String toString() {
            return "Troncon{" +
                    "idOrigine='" + idOrigine + '\'' +
                    ", idDestination='" + idDestination + '\'' +
                    ", longueur=" + longueur +
                    ", nomDeLaRue='" + nomDeLaRue + '\'' +
                    '}';
        }
    }
}
