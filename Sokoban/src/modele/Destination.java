/**
 * Classe Destination
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Classe Destination qui extends Element
 * Ses variables globales sont boolean acaisse et boolean arobot
 */
public class Destination extends Element{
    private boolean acaisse;
    private boolean arobot;

    /**
     * Constructeur de la classe Destination
     * @param x : la ligne de l'Element Destination
     * @param y : la colonne de l'Element Destination
     * @param acaisse : si il y a une caisse à la destination
     * @param arobot : si il y a un robot à la destination
     */
    public Destination(int x, int y, boolean acaisse, boolean arobot){
        super(x,y);
        this.acaisse = acaisse;
        this.arobot = arobot;
    }

    /** 
     * Retourne si il y a une caisse ou non sur la destination
     * @return (boolean) : retourne si il y a une caisse ou non
     */
    public boolean aCaisse(){
        return acaisse;
    }
    /** 
     * Retourne si il y a ou un robot ou non sur la destination
     * @return (boolean) : retourne si il y a un robot ou non
     */
    public boolean aRobot(){
        return arobot;
    }

    /** Retourne, en fonction de si il y a une caisse, un robot, ou rien, le bon String pour la VueTexte
     * @return (String) : retourne un string en fonction des conditions
     */
    public String toString(){
        if(acaisse){
            return "*";
        }
        if(arobot){
            return "+";
        }
        return ".";
    }

    /** Retourne, en fonction de si il y a une caisse, un robot, ou rien, le bon String de l'URL d'une image pour la VueGraphique
     * @return (String) : retourne un string en fonction des conditions
     */
    public String toImage(){
        if(acaisse){
            return "./bin/images/img/caisse2.gif";
        }
        if(arobot){
            return "./bin/images/img/Bas.gif";
        }
        return "./bin/images/img/but.gif";
    }

    /** Permet d'associer arobot avec le boolean en paramètre 
     * @return (void) : associe arobot au boolean en paramètre
     */
    public void setRobot (boolean arobot) {
        this.arobot = arobot;
    }

    /** Permet d'associer acaisse avec le boolean en paramètre 
     * @return (void) : associe acaise au boolean en paramètre
     */
    public void setCaisse (boolean acaisse) {
        this.acaisse = acaisse;
    }
}