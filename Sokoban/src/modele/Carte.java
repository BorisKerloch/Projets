/**
 * Classe Carte
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe de la Carte
 * Ses variables globales sont Element[][]matrice, Robot robot, ArrayList<String> liste, ArrayList<Element> destinations, int nbLig, int nbCol, String currentMapURL
 */
public class Carte{
    
    private Element [][] matrice;
    private Robot robot;
    private ArrayList<String> liste;
    private ArrayList<Element> destinations = new ArrayList<Element>();
    private int nbLig;
    private int nbCol;
    private String currentMapURL;

    /**
     * Constructeur de la classe Carte qui permet de lire le fichier choisi dans ModeTexte ou Sokoban
     * @param nomFichier : le nom de l'URL du fichier à lire (fichier qui représente la forme du niveau choisi)
     */
    public Carte(String nomFichier){
        currentMapURL = nomFichier;
        try {lireCarte(nomFichier);
        } 
        catch (IOException e1) {
            System.out.println("Erreur création");
        }
    }

    /**
     * Permet de lire la carte en fonction du fichier choisi dans ModeTexte ou Sokoban
     * Associe ensuite à chaque Element de la matrice un Element correct, en fonction de si il est un robot ou un mur par exemple
     * Remet à vide la liste des destinations
     * @param nomFichier : le nom de l'URL du niveau choisi dans ModeTexte ou Sokoban
     * @throws IOException : 
     */
    public void lireCarte (String nomFichier) throws IOException {
        Lecture lecture = new Lecture(nomFichier);
        liste = lecture.getLignes();
        this.nbLig = liste.size();
        this.nbCol = liste.get(0).length();
        this.matrice = new Element [nbLig][nbCol];
        destinations.clear();

        for(int i = 0; i < nbLig; i++) {
            for(int j = 0; j < nbCol; j++){
                switch (liste.get(i).charAt(j)) {
                    case '@' :
                        robot = new Robot(i, j, Direction.BAS);
                        matrice[i][j] = new Sol (i, j, false, true);
                        break;
                    case '+' :
                        robot = new Robot(i, j, Direction.BAS);
                        matrice[i][j] = new Destination (i, j, false, true);
                        destinations.add(matrice[i][j]);
                        break;
                    case '*' :
                        matrice[i][j] = new Destination (i, j, true, false);
                        destinations.add(matrice[i][j]);
                        break;
                    case ' ' :
                        matrice[i][j] = new Sol(i, j, false, false);
                        break;
                    case '#' :
                        matrice[i][j] = new Mur(i, j);
                        break;
                    case '.' :
                        matrice[i][j] = new Destination(i, j, false, false);
                        destinations.add(matrice[i][j]);
                        break;
                    case '$' :
                        matrice[i][j] = new Sol(i, j, true, false);
                        break;
                    default :
                        matrice[i][j] = new EstVide(i, j);
                        break;
                }
            }
        }
    }

    /**
     * Retourne le nom de l'URL actuel et qui a été choisi dans le Sokoban
     * @return (String) : le nom de l'URL du niveau choisi
     */
    public String getMap() {
        return currentMapURL;
    }

    /**
     * Permet d'associer la variable globale currentMapURL à la nouvelle carte mise en paramètre
     * @param currentMap : le nom de la carte actuelle
     */
    public void setMap(String currentMap) {
        this.currentMapURL = currentMap;
    }

    /**
     * Retourne l'Element à l'emplacement i,j dans la matrice
     * @param i : la ligne de l'Element
     * @param j : la colonne de l'Element
     * @return (Element) : le type d'Element aux coordonnées données
     */
    public Element getMatrice (int i, int j) {
        return matrice[i][j];
    }

    /**
     * Retourne le robot et ses informations
     * @return (robot) : le robot et ses informations
     */
    public Robot getRobot () {
        return robot;
    }

    /**
     * Retourne le numéro de la ligne
     * @return (int) : numéro de la ligne
     */
    public int getNbLig () {
        return nbLig;
    }

    /**
     * Retourne le numéro de la colonne
     * @return (int) : numéro de la colonne
     */
    public int getNbCol () {
        return nbCol;
    }

    /**
     * Retourne la carte choisi réécrite en String
     * @return (String) : la carte écrite en String
     */
    public String toString(){
        String tmp = "";
        for(int i = 0; i < nbLig; i++){
            for (int j = 0; j < nbCol; j++) {
                tmp+= matrice[i][j].toString();
            }
            tmp += "\n";
        }
        return tmp;
    }

    /**
     * Retourne si oui ou non le robot peut se déplacer dans une direction donnée
     * @param direction : la direction du robot 
     * @return (boolean) : si le robot peut, oui ou non, aller dans la direction donnée
     */
    public boolean peutSeDeplacer (Direction direction) {
        int incrementeLigne = direction.getIncrementeLigne();
        int incrementeColonne = direction.getIncrementeColonne();
        int nextX = robot.x + incrementeLigne;
        int nextY = robot.y + incrementeColonne;

        String nextChar = matrice[nextX][nextY].toString();
        switch (nextChar) {
            case " " :
                return true;
            case "." :
                return true;
            case "$" :
                int nextNextX = nextX + incrementeLigne;
                int nextNextY = nextY + incrementeColonne;
                String nextNextChar = matrice[nextNextX][nextNextY].toString();
                switch (nextNextChar) {
                    case " ":
                    case ".":
                        return true;
                    default : 
                        return false;
                }
            case "*" :
                int nextNextX1 = nextX + incrementeLigne;
                int nextNextY1 = nextY + incrementeColonne;
                String nextNextChar1 = matrice[nextNextX1][nextNextY1].toString();
                switch (nextNextChar1) {
                    case " ":
                    case ".":
                        return true;
                    default : 
                        return false;
                }
            default : 
                return false;
        }
    }

    /**
     * Si le robot peut se déplacer (oui ou non, réponse obtenue avec la fonction peutSeDeplacer(Direction direction)), alors il se déplace
     * Et ensuite on change les attributs des Elements atteints par les nouvelles coordonnées pour correspondre au déplacement
     * En plus simple, si le robot avance en haut, son ancien emplacement est vide et le nouveau est robot
     * @param direction : direction vers où va le robot
     */
    public void deplaceRobot(Direction direction) {
        int incrementeLigne = direction.getIncrementeLigne();
        int incrementeColonne = direction.getIncrementeColonne();
        int new_x = robot.x + incrementeLigne;
        int new_y = robot.y + incrementeColonne;

        if (peutSeDeplacer(direction)) {
            if (!(matrice[new_x][new_y].aCaisse())) {
                matrice[robot.x][robot.y].setRobot(false);
                matrice[new_x][new_y].setRobot(true);
                robot.avancer(direction);
            } else {
                matrice[robot.x][robot.y].setRobot(false);
                matrice[new_x][new_y].setRobot(true);
                matrice[new_x][new_y].setCaisse(false);
                matrice[new_x + incrementeLigne][new_y + incrementeColonne].setCaisse(true);
                robot.avancer(direction);
            }
        } else {
        }
    }

    /**
     * Détermine si une partie est finie ou pas en fonction de si oui ou non les destinations sont toutes couvertes par une caisse
     * @return (boolean) : oui ou non si la partie est finie
     */
    public boolean finDePartie(){
        for (Element d : destinations) {
            if (!d.aCaisse()) {
                return false;
            }
        }
        return true;
    }
}