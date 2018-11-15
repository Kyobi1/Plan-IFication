package Vue;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Classe de gestion de l'affichage des rues.
 * @author H4104
 */
public class LineModifiee extends Line {
    private String nomDeLaRue;
    private Color defaultColor;

    /**
     * Constructeur de la classe LineModifiee
     * @param startX correspondant � la coordonn�e de d�but de la rue selon les abscisses
     * @param startY correspondant � la coordonn�e de d�but de la rue selon les ordonn�es
     * @param endX correspondant � la coordonn�e de fin de la rue selon les abscisses
     * @param endY correspondant � la coordonn�e de fin de la rue selon les ordonn�es
     * @param nomDeLaRue correspondant � une string contenant le nom de la rue
     */
    public LineModifiee(double startX, double startY, double endX, double endY, String nomDeLaRue) {
        super(startX, startY, endX, endY);
        this.nomDeLaRue = nomDeLaRue;
        init();
    }

    /**
     * Constructeur par defaut de la classe LineModifiee.
     */
    public LineModifiee() {
        super();
        init();
    }

    /**
     * M�thode d'initialisation de la detection d'�v�nements.
     */
    private void init() {
        setOnMouseEntered(event -> {
            setStroke(Color.color(9/256.0,132/256.0,227/256.0,1));
        });

        setOnMouseExited(event -> {
            setStroke(defaultColor);
        });
    }

    /**
     * M�thode d'affectation du nom de la rue associ�e � l'objet.
     * @param nomDeLaRue correspondant au nom de rue choisit
     */
    public void setNomDeLaRue(String nomDeLaRue) {
        this.nomDeLaRue = nomDeLaRue;
    }

    /**
     * M�thode d'affectation de la couleur.
     * @param color correspondant � la couleur choisit dans un type Color
     */
    public void setDefaultColor(Color color) {
        setStroke(color);
        this.defaultColor = color;
    }

    /**
     * M�thode d'obtention du nom de la rue associ�e � l'objet.
     * @retour nomDeLaRue correspondant au nom de rue choisit
     */
    public String getNomDeLaRue() {
        return nomDeLaRue;
    }
}
