/**
 * Classe Mur
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Classe Mur qui extends Element
 */
public class Mur extends Element{

    /**
     * Le constructeur de la classe Mur
     * @param x : la ligne de l'Element mur
     * @param y : la colonne de l'Element mur
     */
    public Mur(int x, int y){
        super(x,y);
    }

    /**
     * Retourne le String du mur pour l'affichage texte dans ModeTexte
     * @return (String) : le string de mur pour son affichage texte
     */
    public String toString(){
        return "#";
    }

    /**
     * Retourne l'URL de l'image du mur pour l'affichage graphique dans VueSokoban
     * @return (String) : l'URL du mur pour son affichage graphique
     */
    public String toImage(){
        return "./bin/images/img/mur.gif";
    }
}