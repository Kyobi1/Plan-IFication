package Model.Metier;

import java.util.List;

/**
 * Classe m�tier pour la gestion des demandes de livraisons.
 * @author H4104
 */
public class DemandeLivraisons {
    private final String idEntrepot;
    private final List<Livraison> pointsDeLivraisons;
    private final Temps heureDeDepart;

    /**
     * Constructeur de la classe DemandeLivraisons
     * @param idEntrepot correspondant � l'identifiant de l'entrep�t � consid�rer
     * @param pointsDeLivraisons correspondant � la liste des livraisons � effectuer
     * @param heureDeDepart correspondant � l'heure de d�part
     * @see Model.Metier.Livraison
     */
    public DemandeLivraisons(String idEntrepot, List<Livraison> pointsDeLivraisons, Temps heureDeDepart) {
        this.idEntrepot = idEntrepot;
        this.pointsDeLivraisons = pointsDeLivraisons;
        this.heureDeDepart = heureDeDepart;
    }

    /**
     * M�thode d'obtention de l'entrep�t.
     * @return idEntrepot correspondant � l'identifiant dde l'entrep�t 
     */
    public String getEntrepot() {
        return idEntrepot;
    }

    /**
     * M�thode d'obtention des points de livraisons.
     * @return pointsDeLivraisons correspondant � une liste de livraisons correspondant aux points de livraisons de cette demande
     * @see Model.Metier.Livraison
     */
    public List<Livraison> getPointsDeLivraisons() {
        return pointsDeLivraisons;
    }

    /**
     * M�thode d'obtention de l'heure de d�part.
     * @return heureDeDepart correspondant � l'heure de d�part sous le format Temps
     * @see Model.Metier.Temps 
     */
    public Temps getHeureDeDepart() {
        return heureDeDepart;
    }

    /**
     * M�thode de conversion en string.
     * @return Livraison correspondant � une description de la demande de livraisons et de ses composantes
     */
    @Override
    public String toString() {
        return "DemandeLivraisons{" +
                "idEntrepot='" + idEntrepot + '\'' +
                ", pointsDeLivraisons=" + pointsDeLivraisons +
                ", heureDeDepart=" + heureDeDepart +
                '}';
    }
}
