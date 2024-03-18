/**
 * Classe Sol
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Classe Sol qui extends Element
 * Ses variables globales sont boolean acaisse et boolean arobot
 */
public class Sol extends Element{
    private boolean acaisse;
    private boolean arobot;

    /**
     * Le constructeur de la classe Sol
     * @param x : la ligne de l'Element Sol
     * @param y : la colonne de l'Element Sol
     * @param acaisse : si l'Element Sol a une caisse sur lui ou pas
     * @param arobot : si l'Element Sol a un robot sur lui ou pas
     */
    public Sol(int x, int y, boolean acaisse, boolean arobot){
        super(x,y);
        this.acaisse = acaisse;
        this.arobot = arobot;
    }

    /**
     * Retourne acaisse
     * @return (boolean) : si oui ou non il y a une caisse
     */
    public boolean aCaisse(){
        return acaisse;
    }

    /**
     * Retourne arobot
     * @return (boolean) : si oui ou non il y a un robot
     */
    public boolean aRobot(){
        return arobot;
    }

    /**
     * Retourne le String associé à l'Element Sol si il y a une caisse sur lui ou non, si il y a un un robot sur lui ou non ou si il n'y a rien sur lui
     * La méthode retourne ensuite le bon string en fonction des conditions précédentes
     * Cette méthode sert pour la vue texte du Sokobans
     * @return (String) : le bon String en fonction des conditions
     */
    public String toString(){
        if(acaisse){
            return "$";
        }
        if(arobot){
            return "@";
        }
        return " ";
    }

    /**
     * Retourne l'URL de l'image du Sol en fonction des conditions. Fonctionne de la même manière que toString() mais associe des images et non un caractère
     * Cette méthode sert pour la vue graphique du Sokoban
     * @return (String) : le bon URL d'image en fonction des conditions
     */
    public String toImage(){
        if(acaisse){
            return "./bin/images/img/caisse1.gif";
        }
        if(arobot){
            return "./bin/images/img/Bas.gif";
        }
        return "./bin/images/img/sol.gif";
    }
    
    /**
     * Permet d'associer à arobot le boolean en paramètre (permettant de changer la présence ou non d'un robot sur l'Element Sol voulu)
     */
    public void setRobot (boolean arobot) {
        this.arobot = arobot;
    }

    /**
     * Permet d'associer à acaisse le boolean en paramètre (permettant de changer la présence ou non d'une caisse sur l'Element Sol voulu)
     */
    public void setCaisse (boolean acaisse) {
        this.acaisse = acaisse;
    }
    
}