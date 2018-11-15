package Model.Calcules;

import Model.Metier.Chemin;
import Model.Metier.Livraison;
import Model.Metier.Plan;

import java.util.*;

/**
 * Classe de fonctionnement de l'algorithme Djikstra
 * @author H4104
 * @see Model.Metier.Livraison
 * @see Model.Calcules.PCC
 */
public class Djikstra implements PCC {

    private String idSource;
    private Set<String> nonEvalues;
    private Set<String> evalues;
    private List<Livraison> pointsDestination;
    private Plan plan;
    private Map<String, MetaDonne> metaDonnes;
    private Map<Livraison, Chemin> resultat;

    /**
     * M�thode d'obtention des plus courts chemins.
     * @param idSource correspondant � l'�l�ment de d�part pour le lancement de l'algorithme
     * @param pointsDestination correspondant � la liste des livraisons encore � effectuer
     * @param plan correspondant au plan que l'on consid�re
     * @return resultat correspondant � une map de livraisons et de chemins associ�s
     */
    @Override
    public Map<Livraison, Chemin> getPlusCourtsChemins(String idSource, List<Livraison> pointsDestination, Plan plan) {
        nonEvalues = new HashSet<>();
        evalues = new HashSet<>();
        metaDonnes = new HashMap<>();
        resultat = new HashMap<>();
        this.idSource = idSource;
        this.pointsDestination = new LinkedList<>(pointsDestination);
        this.plan = plan;
        initialiser();
        evaluer();
        return resultat;
    }

    /**
     * M�thode d'�valuation de la liste des livraisons � effectuer.
     */
    private void evaluer() {
        while (!pointsDestination.isEmpty()) {
            String lePlusProche = getPlusProche();
            for (Plan.Troncon troncon : plan.getSuccesseurs(lePlusProche)) {
                String successeur = troncon.getDestination();
                if (!evalues.contains(successeur)) traiter(lePlusProche, successeur);
            }

            nonEvalues.remove(lePlusProche);
            evalues.add(lePlusProche);
            for (Iterator<Livraison> it = pointsDestination.iterator(); it.hasNext(); ) {
                Livraison livraison = it.next();
                if (livraison.getNoeud().equals(lePlusProche)) {
                    MetaDonne metaDonne = metaDonnes.get(lePlusProche);
                    metaDonne.predecesseurs.add(livraison.getNoeud());
                    Chemin chemin = new Chemin(metaDonne.predecesseurs, metaDonne.distance);
                    resultat.put(livraison, chemin);
                    it.remove();
                }
            }



        }
    }

    /**
     * M�thode de traitement dans le cadre de l'algorithme.
     * @param source correspondant au point de d�partv de l'algorithme pour cette �tape du traitement
     * @param successeur correspondaant au successeur � �valuer lors de cette �tape
     */
    private void traiter(String source, String successeur) {
        nonEvalues.add(successeur);

        if (!metaDonnes.containsKey(successeur)) {
            metaDonnes.put(successeur, new MetaDonne());
        }

        double longueurTroncon = plan.getDistance(source, successeur);
        MetaDonne metaDonneSuccesseur = metaDonnes.get(successeur);
        MetaDonne metaDonneSource = metaDonnes.get(source);
        if (longueurTroncon + metaDonneSource.distance < metaDonneSuccesseur.distance) {
            metaDonneSuccesseur.distance = metaDonneSource.distance + longueurTroncon;
            metaDonneSuccesseur.predecesseurs = new LinkedList<>(metaDonneSource.predecesseurs);
            metaDonneSuccesseur.predecesseurs.add(source);
        }
    }

    /**
     * M�thode d'obtention du noeud le plus proche de l'�l�ment courant.
     * @return lePlusProche correspondant � l'identifiant du prochain noeud � traiter par ordre de proximit�.
     */
    private String getPlusProche() {
        String lePlusProche = null;
        double minDist = Double.POSITIVE_INFINITY;
        for (String idNoeud : nonEvalues) {
            double distance = metaDonnes.get(idNoeud).distance;
            if (distance < minDist) {
                minDist = distance;
                lePlusProche = idNoeud;
            }
        }

        return lePlusProche;
    }

    /**
     * M�thode d'initialisation de l'algorithme
     */
    private void initialiser() {
        nonEvalues.add(idSource);

        MetaDonne metaDonnesSource = new MetaDonne();
        metaDonnesSource.distance = 0;
        metaDonnesSource.predecesseurs = new LinkedList<>();
        metaDonnes.put(idSource, metaDonnesSource);
    }

    /**
     * Classe MetaDonne contenant la distance et la liste des predecesseurs
     * @author H4104
     */
    private class MetaDonne {
        double distance = Double.POSITIVE_INFINITY;
        List<String> predecesseurs;

        /**
         * M�thode de conversion de la m�tadonn�e en string
         * @return MetaDonne correspondant � la m�tadonn�e et � ses composants sous forme d'un string
         */
        @Override
        public String toString() {
            return "MetaDonne{" +
                    "distance=" + distance +
                    ", predecesseurs=" + predecesseurs +
                    '}';
        }
    }


}

