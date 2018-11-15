package Model.Calcules;

import Model.Metier.Livraison;
import Model.Metier.Noeud;
import Model.Metier.NoeudFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe de gestion globale des clusters.
 * @author H4104
 * @see Model.Calcules.Clusterer
 */
public class PetalClustring implements Clusterer {
    private List<Livraison> livraisons;
    private List<List<Livraison>> clusters;

    /**
     * M�thode d'obtention des clusters associ�s � la liste de livraisons.
     * @param idEntrepot correspondant � l'identifiant de l'entrep�t pour cette serie de livraisons
     * @param livraisons correspondant � la liste de livraisons � traiter
     * @param nombreClusters correspondant au nombre de clusters que l'on veut obtenir
     * @param maxiter correspondant au nombre maximum d'it�rations que l'on autorise
     * @return clusters correspondant � une liste de liste correspondant aux clusters ainsi form�s
     */
    @Override
    public List<List<Livraison>> getClusters(String idEntrepot, List<Livraison> livraisons, int nombreClusters, int maxiter) {
        clusters = new LinkedList<>();
        this.livraisons = new ArrayList<>(livraisons);
        trierLivraisons(idEntrepot);
        creerClusters(nombreClusters);
        return clusters;
    }

    /**
     * M�thode de cr�ation des clusters.
     * @param nombreClusters correspondant au nombre de clusters voulus
     */
    private void creerClusters(int nombreClusters) {
        int q = livraisons.size() / nombreClusters;
        int r = livraisons.size() - q * nombreClusters;

        int idx = 0;
        while (idx < livraisons.size()) {
            List<Livraison> cluster = new LinkedList<>();
            while (cluster.size() < q) {
                cluster.add(livraisons.get(idx++));
            }
            if (r != 0) {
                r--;
                cluster.add(livraisons.get(idx++));
            }

            clusters.add(cluster);
        }
    }

    /**
     * M�thode de trie de livraisons en fonction de leur position sur le plan.
     * @param idEntrepot correspondant � l'identifiant de l'entrep�t que l'on consid�re
     */
    private void trierLivraisons(String idEntrepot) {
        Noeud entrepot = NoeudFactory.getNoeudParId(idEntrepot);

        livraisons.sort((o1, o2) -> {
            Noeud noeud1 = NoeudFactory.getNoeudParId(o1.getNoeud());
            Noeud noeud2 = NoeudFactory.getNoeudParId(o2.getNoeud());
            double angle1 = Math.atan2(noeud1.getLatitude() - entrepot.getLatitude(), noeud1.getLongitude() - entrepot.getLongitude());
            double angle2 = Math.atan2(noeud2.getLatitude() - entrepot.getLatitude(), noeud2.getLongitude() - entrepot.getLongitude());
            return Double.compare(angle1, angle2);
        });

    }
}
