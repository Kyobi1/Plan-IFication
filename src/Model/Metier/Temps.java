package Model.Metier;

import javafx.beans.property.SimpleStringProperty;

/**
 * Classe de gestion des heures dans le cadre de l'application.
 * @author H4104
 */
public class Temps implements Comparable{
    private final SimpleStringProperty horraire;
    private int heures, minutes, secondes;
    private int value;

    /**
     * Constructeur de la classe Temps.
     * @param heures correspondant au nombre d'heures sous forme enti�re
     * @param minutes correspondant au nombre de minutes sous forme enti�re
     * @param secondes correspondant au nombre de secondes sous forme enti�re
     */
    public Temps(int heures, int minutes, int secondes) {
        this.heures = heures;
        this.minutes = minutes;
        this.secondes = secondes;
        horraire = this.PropertytoString();
        value = heures * 3600 + minutes * 60 + secondes;
    }

    /**
     * M�thode d'ajout d'un temps en seconde � un autre temps .
     * @param debut correspondant au temps sous forme d'objet
     * @param secondesAajouter correspondant � la dur�e � ajouter en seconde
     * @return Temps correspondant au nouveau temps ainsi obtenu
     */
    public static Temps addConvert(Temps debut, double secondesAajouter) {
        int h, m, s;

        int total = (int) (debut.heures * 3600 + debut.minutes * 60 + debut.secondes + secondesAajouter);

        h = (total / 3600);
        m = (total - h * 3600) / 60;
        s = total - m * 60 - h * 3600;
        return new Temps(h, m, s);
    }

    /**
     * M�thode d'obtention du nombre d'heure associ� au temps.
     * @return heures correspondant � ce nombre d'heures sous forme enti�re
     */
    public int getHeures() {
        return heures;
    }

    /**
     * M�thode d'obtention du nombre de minutes associ� au temps.
     * @return minutes correspondant � ce nombre de minutes sous forme enti�re
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * M�thode d'obtention du nombre de secondes associ� au temps.
     * @return secondes correspondant � ce nombre de secondes sous forme enti�re
     */
    public int getSecondes() {
        return secondes;
    }

    /**
     * M�thode d'obtention de la valeur totale associ� au temps.
     * @return value correspondant � la valeur totale de ce temps en secondes
     */
    public int getValue() {
        return value;
    }

    /**
     * M�thode d'obtention de la dur�e associ�e � ce temps sous forme de SimpleStringProperty.
     * @return horraire correspondant � cette SimpleStringProperty
     */
    public SimpleStringProperty getHorraireProperty() {
        return horraire;
    }

    /**
     * M�thode de conversion de ce temps en string.
     * @return res correspondant � un string contenant la description totale de ce temps en heures, minutes et secondes
     */
    @Override
    public String toString() {
        String res;
        if(heures<10){
            res = "0"+heures;
        }else{
            res =""+heures;
        }
        if(minutes<10){
            res+=":"+"0"+minutes;
        }else{
            res+=":"+minutes;
        }
        return res;
    }

    /**
     * M�thode d'obtention de la SimpleStringProperty associ�e � un string. 
     * @return SimpleStringProperty
     */
    public SimpleStringProperty PropertytoString() {
        return new SimpleStringProperty(toString());
    }
    
    /**
     * M�thode de comparaison
     * @return int correspondant � l'�cart en heures, minutes ou secondes selon o� se trouve la premi�re diff�rence
     */
    @Override
    public int compareTo(Object o) {
        Temps temps = (Temps) o;
        if (heures != temps.heures) return heures - temps.heures;
        if (minutes != temps.minutes) return minutes - temps.minutes;
        return secondes - temps.secondes;
    }
}
