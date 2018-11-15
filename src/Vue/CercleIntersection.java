package Vue;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe de gestion de l'affichage des noeuds.
 * @author H4104
 * @see Model.Metier.Noeud
 */
public class CercleIntersection extends Circle {
    private String idNoeud;
    private boolean selectionne = false;
    private EventHandler<MouseEvent> eventHandlerMouseEntered = event -> {
        setFill(Color.color(9/256.0,132/256.0,227/256.0,1));
        event.consume();
    };

    private EventHandler<MouseEvent> eventEventHandlerMouseExited = event -> {
        setFill(Color.TRANSPARENT);
        event.consume();
    };

    /**
     * Constructeur de la classe CercleIntersection.
     * @param centerX correspondant au centre de l'objet sur les abscisses
     * @param centerY correspondant au centre de l'objet sur les ordonn�es
     * @param radius correspondant au rayon du cercle
     * @param idNoeud correspondant au noeud associ� � l'objet cercle
     */
    public CercleIntersection(double centerX, double centerY, double radius, String idNoeud) {
        super(centerX, centerY, radius);
        this.idNoeud = idNoeud;
        setFill(Color.TRANSPARENT);
        addEventHandler(MouseEvent.MOUSE_ENTERED,eventHandlerMouseEntered);
        addEventHandler(MouseEvent.MOUSE_EXITED,eventEventHandlerMouseExited);
    }

    /**
     * M�thode de conversion de l'objet en string.
     * @return CercleIntersection correspondant � la description de cet objet avec le noeud concern�
     */
    @Override
    public String toString() {
        return "CercleIntersection{" +
                "idNoeud='" + idNoeud + '\'' +
                '}';
    }

    /**
     * M�thode de test pour savoir si l'objet est actuellement selectionn�.
     * @return selectionne correspondant � un booleen indiquant le r�sultat du test
     */
    public boolean isSelectionne() {
        return selectionne;
    }

    /**
     * M�thode d'affectation pour l'attribut selectionne.
     * @param selectionne correspondant � la valeur que l'on veut pour l'attribut booleen.
     */
    public void setSelectionne(boolean selectionne) {
        if (selectionne) {
            setFill(Color.BLACK);
            removeEventHandler(MouseEvent.MOUSE_ENTERED,eventHandlerMouseEntered);
            removeEventHandler(MouseEvent.MOUSE_EXITED,eventEventHandlerMouseExited);
        }
        else{
            setFill(Color.TRANSPARENT);
            addEventHandler(MouseEvent.MOUSE_ENTERED,eventHandlerMouseEntered);
            addEventHandler(MouseEvent.MOUSE_EXITED,eventEventHandlerMouseExited);
        }
        this.selectionne = selectionne;
    }

    /**
     * M�thode d'obtention de l'identifiant du noeud.
     * @return idNoeud correspondant � l'identifiant du noeud associ� � l'objet circulaire
     */
    public String getIdNoeud() {
        return idNoeud;
    }
}