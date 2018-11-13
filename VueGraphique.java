package Vue;

import Controleur.Controleur;
import Model.Metier.*;
import Model.Planification;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class VueGraphique extends Vue {
    private double minLongitude;
    private double maxLongitude;
    private double minLatitude;
    private double maxLatitude;
    private Color couleurInit;
    private Chemin cheminInit;
    private boolean init= false;
    private Group planGroup;
    private Group tourneesGroup;
    private Group livraisonsGroup;
    private List<Color> colors;
    private List<CercleLivraison> cerclesLivraisonsSelectionnes = new LinkedList<>();
    private CercleIntersection cercleIntersection;
    private VueTextuelle vueTextuelle;

    public VueGraphique(Planification planification) {
        super(planification);
        initCouleurs();

        Group rootGroup = new Group();
        BorderPane borderPane = new BorderPane();
        ScrollPane scrollPane = new ScrollPane();

        planGroup = new Group();
        livraisonsGroup = new Group();
        tourneesGroup = new Group();

        rootGroup.getChildren().addAll(planGroup, tourneesGroup, livraisonsGroup);

        //--------------Danger---------------------------------------------------
        Slider slider = new Slider();
        slider.setMin(1);
        slider.setMax(2);
        slider.setBlockIncrement(0.05);

        rootGroup.scaleXProperty().bind(slider.valueProperty());
        rootGroup.scaleYProperty().bind(slider.valueProperty());
        scrollPane.setContent(new Group(rootGroup));
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(600);
        scrollPane.setFocusTraversable(false);
        borderPane.setCenter(scrollPane);
        borderPane.setBottom(slider);
        this.getChildren().add(borderPane);





        //-------------------------------------------------------------------------
        livraisonsGroup.setOnMouseClicked(event -> {
            CercleLivraison cercleLivraison = (CercleLivraison) event.getTarget();
            if (cercleLivraison.isSelectionne()) {
                cerclesLivraisonsSelectionnes.remove(cercleLivraison);
                Controleur.livraisonDeselectionnee(cercleLivraison.getLivraison());
                cercleLivraison.setSelectionne(false);
            } else if (Controleur.livraisonSelectionne(cercleLivraison.getLivraison())) {
                cerclesLivraisonsSelectionnes.add(cercleLivraison);
                cercleLivraison.setSelectionne(true);
            }
        });
        planGroup.setOnMouseClicked(event -> {
            if (event.getTarget() instanceof CercleIntersection) {
                CercleIntersection intersection = (CercleIntersection) event.getTarget();
                if (intersection.isSelectionne()) {
                    cercleIntersection = null;
                    Controleur.noeudDeselectionne(intersection.getIdNoeud());
                    intersection.setSelectionne(false);
                } else if (Controleur.noeudSelectionne(intersection.getIdNoeud())) {
                    cercleIntersection = intersection;
                    intersection.setSelectionne(true);
                }
            }
        });
        planGroup.setOnMouseMoved(event -> {
            if (event.getTarget() instanceof LineModifiee) {            	
                LineModifiee lineModifiee = (LineModifiee) event.getTarget();
                lineModifiee.init();
                vueTextuelle.rueSelectionnee(lineModifiee.getNomDeLaRue());
            }
        });
        
        tourneesGroup.setOnMouseMoved(event -> {
            if (event.getTarget() instanceof LineModifiee) {   
            	
                LineModifiee lineModifiee = (LineModifiee) event.getTarget();
                vueTextuelle.rueSelectionnee(lineModifiee.getNomDeLaRue());
                int j = getIndexColor(lineModifiee.getColor());
                
                if(init) {
                	dessinerUnChemin(cheminInit,couleurInit,4);
                	}
                
                if (j==(-1)){                	
                	lineModifiee.init();
                }
                
                else {  
                	
                	Tournee tournee = tournees.get(j);
                	List<Chemin> chemins = tournee.getChemins();
                	boolean succes = false;
                	couleurInit = lineModifiee.getColor(); 
                    for (Chemin chemin : chemins) { 
                    	cheminInit = chemin;
                    	init=true;
                    	 Noeud premierNoeud = NoeudFactory.getNoeudParId(chemin.getDepart());
                         LineModifiee line = new LineModifiee();         
                         line.setStartX(trX(premierNoeud.getLongitude()));
                         line.setStartY(trY(premierNoeud.getLatitude()));
                         
                    	for (String idNoeud : chemin.getChemin().subList(1, chemin.getChemin().size())) {
                            Noeud noeud = NoeudFactory.getNoeudParId(idNoeud);
                            line.setEndX(trX(noeud.getLongitude()));
                            line.setEndY(trY(noeud.getLatitude()));                           
                            
                            if (lineModifiee.getStartX()==line.getStartX() && lineModifiee.getStartY()==line.getStartY()) {                            	
                            	dessinerUnChemin (chemin, Color.RED, 4);
                            	succes = true;
                            	break;
                            }                            
                            line = new LineModifiee();
                            premierNoeud = noeud;
                            line.setStartX(trX(noeud.getLongitude()));
                            line.setStartY(trY(noeud.getLatitude()));
                        } 
                    	if (succes ==true) {
                    		break;
                    	}                    	
                    }
                }
            }
        });
    }


    @Override
    void dessinerPlan() {
        tourneesGroup.getChildren().clear();
        livraisonsGroup.getChildren().clear();
        planGroup.getChildren().clear();
        if (plan != null) {
            calculerCoins();

            for (String idNoeud : plan.getNoeuds()) {
                Noeud n1 = NoeudFactory.getNoeudParId(idNoeud);
                CercleIntersection cercleIntersection = new CercleIntersection(trX(n1.getLongitude()), trY(n1.getLatitude()), 7, idNoeud);
                planGroup.getChildren().add(cercleIntersection);
                for (Plan.Troncon trancon : plan.getSuccesseurs(idNoeud)) {
                    Noeud n2 = NoeudFactory.getNoeudParId(trancon.getDestination());
                    LineModifiee line = new LineModifiee(trX(n1.getLongitude()), trY(n1.getLatitude()), trX(n2.getLongitude()), trY(n2.getLatitude()), trancon.getNomDeLaRue());
                    line.setDefaultColor(Color.BLACK);
                    line.setStrokeWidth(3);
                    planGroup.getChildren().add(line);
                }
            }

        }

    }

    @Override
    void dessinerDemandeDeLivraisons() {
        livraisonsGroup.getChildren().clear();
        tourneesGroup.getChildren().clear();
        if (demandeLivraisons != null) {
            Noeud entrepot = NoeudFactory.getNoeudParId(demandeLivraisons.getEntrepot());

            CercleLivraison cercleLivraisonEntrepot = new CercleLivraison(trX(entrepot.getLongitude()), trY(entrepot.getLatitude()), 5, new Livraison(entrepot.getId(), 0));
            cercleLivraisonEntrepot.setDefaultColor(Color.RED);
            livraisonsGroup.getChildren().add(cercleLivraisonEntrepot);

            for (Livraison livraison : demandeLivraisons.getPointsDeLivraisons()) {
                Noeud pointLivr = NoeudFactory.getNoeudParId(livraison.getNoeud());
                CercleLivraison cercleLivraison = new CercleLivraison(trX(pointLivr.getLongitude()), trY(pointLivr.getLatitude()), 7, livraison);
                cercleLivraison.setCouleur(Color.BLUE);
                livraisonsGroup.getChildren().add(cercleLivraison);
            }
        }
    }

    
    @Override
    void dessinerTournees() {
        tourneesGroup.getChildren().clear();
        if (tournees != null) {
            int indexTournee = 0;
            for (Tournee tournee : tournees) {
                Color color = getColor(indexTournee);
                dessinerUneTournee (tournee, color, 4);
                indexTournee = indexTournee + 1;
            }
        }
    }
    
    void dessinerUneTournee (Tournee tournee, Color color, int width) {
    	
    	  List<Chemin> chemins = tournee.getChemins();
          for (Chemin chemin : chemins) {
        	  dessinerUnChemin(chemin,color,width);           
          }    	
    }
    
    void dessinerUnChemin (Chemin chemin, Color color, int width) {
    	
    	  Noeud premierNoeud = NoeudFactory.getNoeudParId(chemin.getDepart());
          LineModifiee line = new LineModifiee();
          line.setStartX(trX(premierNoeud.getLongitude()));
          line.setStartY(trY(premierNoeud.getLatitude()));
          for (String idNoeud : chemin.getChemin().subList(1, chemin.getChemin().size())) {
              Noeud noeud = NoeudFactory.getNoeudParId(idNoeud);
              line.setEndX(trX(noeud.getLongitude()));
              line.setEndY(trY(noeud.getLatitude()));
              line.setDefaultColor(color);
              line.setNomDeLaRue(planification.getNomDeLaRue(premierNoeud.getId(), noeud.getId()));
              line.setStrokeWidth(width);
              tourneesGroup.getChildren().add(line);
              line = new LineModifiee();
              premierNoeud = noeud;
              line.setStartX(trX(noeud.getLongitude()));
              line.setStartY(trY(noeud.getLatitude()));
          }    	
    }
    

    //Met en Orange le point selectionné
    void couleurPointFocus(String idNoeud) {
        for (int i = 0; i < livraisonsGroup.getChildren().size(); i++) {
            CercleLivraison cercle = (CercleLivraison) livraisonsGroup.getChildren().get(i);
            if (cercle.getLivraison().getNoeud().equals(idNoeud)) {
                cercle.setCouleur(Color.ORANGE);
                break;
            }
        }
    }

    //Reset tous les points de livraison en bleu sauf l'entrepot (rouge)
    void resetCouleurs() {
       
    	dessinerTournees();
        
        for (int i = 0; i < livraisonsGroup.getChildren().size(); i++) {
            CercleLivraison cercle = (CercleLivraison) livraisonsGroup.getChildren().get(i);

            if (demandeLivraisons.getEntrepot().equals(cercle.getLivraison().getNoeud()))
                cercle.setCouleur(Color.RED);

            else
                cercle.setCouleur(Color.BLUE);
        }         
    }

    private double trX(double longitude) {
        double echelleHor = 600 / (maxLongitude - minLongitude);
        return echelleHor * (longitude - minLongitude);
    }

    private double trY(double latitude) {
        double echelleVer = 600 / (maxLatitude - minLatitude);
        return echelleVer * (maxLatitude - latitude);
    }

    private void calculerCoins() {
        minLongitude = Double.POSITIVE_INFINITY;
        maxLongitude = Double.NEGATIVE_INFINITY;
        minLatitude = Double.POSITIVE_INFINITY;
        maxLatitude = Double.NEGATIVE_INFINITY;
        for (String idNoeud : plan.getNoeuds()) {
            Noeud noeud = NoeudFactory.getNoeudParId(idNoeud);
            if (noeud.getLongitude() < minLongitude) minLongitude = noeud.getLongitude();
            if (noeud.getLongitude() > maxLongitude) maxLongitude = noeud.getLongitude();
            if (noeud.getLatitude() < minLatitude) minLatitude = noeud.getLatitude();
            if (noeud.getLatitude() > maxLatitude) maxLatitude = noeud.getLatitude();
        }
    }

    private void initCouleurs() {
        colors = new LinkedList<>();
        colors.add(Color.BROWN);
        colors.add(Color.SEAGREEN);
        colors.add(Color.AQUA);
        colors.add(Color.YELLOWGREEN);
        colors.add(Color.CRIMSON);
        colors.add(Color.BLUEVIOLET);
        colors.add(Color.BLUEVIOLET);
        colors.add(Color.SPRINGGREEN);
        colors.add(Color.TOMATO);
        colors.add(Color.YELLOW);
        colors.add(Color.CORNFLOWERBLUE);
    }

    private Color getColor(int n) {
        if (n < colors.size()) {
            return colors.get(n);
        }
        Random random = new Random();
        Color newColor = Color.color(random.nextInt(100) / 100.0, random.nextInt(100) / 100.0, random.nextInt(100) / 100.0);
        colors.add(newColor);
        return newColor;
    }
    
    public int getIndexColor (Color color) {
    	int j;
    	boolean present = false;
    	for (j=0; j<colors.size(); j++) {
    		if (colors.get(j).equals(color))
    		{
    			present = true;
    			break;
    		}
    	}
    	if (present == false) {
    		j=-1;
    	}    	
    	return j;
    }

    public void annulerModification() {
        for (CercleLivraison cercleLivraison : cerclesLivraisonsSelectionnes) cercleLivraison.setSelectionne(false);
        if (cercleIntersection != null) cercleIntersection.setSelectionne(false);
        cercleIntersection = null;
        cerclesLivraisonsSelectionnes.clear();
    }

    public void setVueTextuelle(VueTextuelle vueTextuelle) {
        this.vueTextuelle = vueTextuelle;
    }
}


