package Model.Calcules;

import Model.Metier.Chemin;
import Model.Metier.Livraison;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Classe 
 * @author H4104
 * @see Model.Calcules.TemplateTSP
 */
public class TSPDeBase extends TemplateTSP {

	/**
	 * Constructeur de l'it�rateur sur la map de livraisons et map de livraisons et chemins. 
	 * @return iterator correspondat � cet �l�ment recherch� pour le fonctionnement de l'algorithme
	 */
    @Override
    protected Iterator<Livraison> iterator(Livraison livraisonCourante, List<Livraison> nonVus, Map<Livraison, Map<Livraison, Chemin>> plusCourtsChemins) {
        return new IteratorSeq(nonVus, livraisonCourante);
    }

    /**
     * M�thode bound.
     */
    @Override
    protected int bound(Livraison sommetCourant, List<Livraison> nonVus, Map<Livraison, Map<Livraison, Chemin>> plusCourtsChemins) {
        return 0;
    }
}
