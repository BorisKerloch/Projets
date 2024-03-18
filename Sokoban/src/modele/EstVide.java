/**
 * Classe EstVide
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Classe EstVide qui extends Element
 */
public class EstVide extends Element{

    /**
     * Constructeur de la classe EstVide
     * @param x : ligne de l'Element
     * @param y : colonne de l'Element
     */
    public EstVide(int x, int y){
        super(x,y);
    }

    /** Retourne la représentation du vide en string pour la VueTexte
     * @return (string) : retourne "'"
     */
    public String toString(){
        return "'";
    }

    /** Retourne la représentation du vide avec l'URL de l'image (donc du vide) pour la VueGraphique
     * @return (string) : retourne l'URL de l'image du vide
     */
    public String toImage(){
        return "/bin/img/sol.gif";
    }
}