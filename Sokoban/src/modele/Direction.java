/**
 * Classe Direction
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

/**
 * Enumeration des différentes directions avec l'incrémentation de coordonnée qui est lié à la direction
 * Ses variables globales sont int incremnteLigne et int incrementeColonne
 */
public enum Direction {
    HAUT (-1, 0),
    BAS (1, 0),
    GAUCHE(0, -1),
    DROITE (0, 1);

    private final int incrementeLigne;
    private final int incrementeColonne;

    /**
     * Le constructeur de la classe Direction
     * @param incrementeLigne : la valeur à ajouter à la ligne
     * @param incrementeColonne : la valeur à ajouter à la colonne
     */
    Direction (int incrementeLigne, int incrementeColonne) {
        this.incrementeLigne = incrementeLigne;
        this.incrementeColonne = incrementeColonne;
    }

    /**
     * Retourne la valeur de l'incrément de ligne
     * @return (int) : valeur de l'incrémentation à faire pour la ligne
     */
    public int getIncrementeLigne() {
        return incrementeLigne;
    }

    /**
     * Retourne la valeur de l'incrément de colonne
     * @return (int) : valeur de l'incrémentation à faire pour la colonne
     */
    public int getIncrementeColonne() {
        return incrementeColonne;
    }
}