package Model.Calcules;

import Model.Metier.Livraison;

import javax.naming.OperationNotSupportedException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Classe de l'it�rateur pour iterer sur l'ensemble des sommets de nonVus.
 * @author H4104
 */
public class IteratorSeq implements Iterator<Livraison> {

    private Livraison[] candidats;
    private int nbCandidats;

    /**
     * Constructeur de la classe IteratorSeq 
     * @param nonVus correspondant la collection des livraisons non vus
     * @param livraisonCourante correspondant� la livraison en cours d'it�ration
     */
    public IteratorSeq(Collection<Livraison> nonVus, Livraison livraisonCourante) {
        this.candidats = new Livraison[nonVus.size()];
        nbCandidats = 0;
        for (Livraison s : nonVus){
            candidats[nbCandidats++] = s;
        }
    }

    /**
     * M�thode de test pour savoir si la livraison en cours d'it�ration dispose d'un successeur.
     * @return boolean correspondant au r�sultat de ce test
     */
    @Override
    public boolean hasNext() {
        return nbCandidats > 0;
    }

    /**
     * M�thode d'obtention de la livraison suivante dans le cadre de l'it�ration.
     * @return Livraison correspondant � l'�lement recherch�
     */
    @Override
    public Livraison next() {
        return candidats[--nbCandidats];
    }

}
