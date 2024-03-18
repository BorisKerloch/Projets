/**
 * Classe Robot
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Classe du Robot qui a pour variables globales Direction direction, int x et int y
 */
public class Robot {

    public Direction direction;
    public int x;
    public int y;

    /**
     * Le constructeur du Robot
     * @param x : la ligne du robot dans la carte
     * @param y : la colonne du robot dans la carte
     * @param direction : la direction du robot dans la carte
     */
    public Robot(int x, int y, Direction direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    /**
     * Retourne le string du robot dans la carte pour son affichage texte
     * @return (String) : le string du robot dans la carte pour son affichage texte
     */
    public String toString(){
        return "@";
    }

    /**
     * Retourne la ligne du robot dans la carte
     * @return (int) : la ligne du robot dans la carte
     */
    public int getX () {
        return x;
    }

    /**
     * Retourne la colonne du robot dans la carte
     * @return (int) : la colonne du robot dans la carte
     */
    public int getY() {
        return y;
    }

    /**
     * Retourne l'URL de l'image du Robot en fonction de sa direction pour sa representation dans l'affichage graphique
     * @return (String) : l'URL de l'image du Robot pour sa représentation graphique en fonction de sa direction
     */
    public String toImage(){
        switch (direction) {
            case BAS :
                return "./bin/images/img/Bas.gif";
            case DROITE :
                return "./bin/images/img/Droite.gif";
            case GAUCHE :
                return "./bin/images/img/Gauche.gif";
            case HAUT :
                return "./bin/images/img/Haut.gif";
            default :
                return "./bin/images/img/Bas.gif";
        }
    }

    /**
     * Retourne si le robot false pour le robot sur la destination
     * @return (boolean) : false
     */
    public boolean robotSurDestination(){
        return false;
    }

    /**
     * Permet de faire avancer le robot en lui incrémentant les valeurs associées à la direction mise en paramètre
     * @param direction : la direction choisie
     */
    public void avancer(Direction direction) {
        x += direction.getIncrementeLigne();
        y += direction.getIncrementeColonne();
    }
}