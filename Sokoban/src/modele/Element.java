/**
 * Classe Element
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Classe Element qui a int x et int y en variables globales
 */
public class Element{
    public int x;
    public int y;

    /**
     * Le constructeur de la classe Element
     * @param x : la ligne de l'Element
     * @param y : la colonne de l'Element
     */
    public Element(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Transforme aCaisse en false
     * @return (boolean) : false
     */
    public boolean aCaisse(){
        return false;
    }

    /**
     * Transforme aRobot en false
     * @return (boolean) : false
     */
    public boolean aRobot(){
        return false;
    }

    /**
     * Retourne la ligne de l'Element
     * @return (int) : la ligne de l'Element
     */
    public int getX() {
        return x;
    }

    /**
     * Retourne la colonne de l'Element
     * @return (int) : la colonne de l'Element
     */
    public int getY() {
        return y;
    }

    /**
     * Permet aux classes qui extends Element d'utiliser cette méthode
     * @param caisse : boolean true ou false pour set une caisse
     */
    public void setCaisse(boolean caisse){}

    /**
     * Permet aux classes qui extends Element d'utiliser cette méthode
     * @param robot : boolean true ou false pour set une robot
     */
    public void setRobot (boolean robot) {}

    /**
     * Retourne un String vide qui permet aux autres classes qui extends Element de se servir de cette méthode
     * @return (String) : vide
     */
    public String toImage() {
        return "";
    }
}