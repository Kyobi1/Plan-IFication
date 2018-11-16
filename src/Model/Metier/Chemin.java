package Model.Metier;

import java.util.List;
import java.util.Objects;

/**
 * Classe m�tier pour la gestion de chemins.
 * @author H4104
 */
public class Chemin {

    private final List<String> chemin;
    private final double cout;
    private String idDepart;

    /**
     * Constructeur de la classe Chemin.
     * @param chemin correspondant � une liste de string contenant les noeuds parcourus
     * @param cout correspondant � la valeur de la distance associ�e
     */
    public Chemin(List<String> chemin, double cout) {
        this.chemin = chemin;
        this.cout = cout;
        idDepart = chemin.get(0);
    }

    /**
     * M�thode d'obtention de la liste de noeuds.
     * @return chemin correspondant � la liste de noeuds du chemins
     */
    public List<String> getChemin() {
        return chemin;
    }

    /**
     * M�thode d'obtention de la distance associ�e au chemin.
     * @return cout correspondant � cette distance
     */
    public double getCout() {
        return cout;
    }

    /**
     * M�thode d'obtention du noeud de d�part du chemin.
     * @return idDepart correspondant � l'identifiant du noeud de d�part.
     */
    public String getDepart() {
        return idDepart;
    }

    /**
     * M�thode d'obtention du noeud final du chemin.
     * @return idArrive correspondant � l'identifiant du noeud final.
     */
    public String getArrivee() {
        return chemin.get(chemin.size() - 1);
    }

    /**
     * M�thode de conversion en string du chemin.
     * @return Chemin correspondant � un string contenant le descriptif du chemin
     */
    @Override
    public String toString() {
        return "Chemin{" +
                "chemin=" + chemin +
                ", cout=" + cout +
                '}';
    }

    /**
     * M�thode de test pour l'�galit� de deux chemins.
     * @return boolean correspondant au r�sultat de ce test.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chemin chemin1 = (Chemin) o;
        return idDepart.equals(chemin1.getDepart());
    }


}
