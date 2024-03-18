/**
 * Classe Sokoban
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package vueGraphique;
import java.io.IOException;

import modele.Carte;

/**
 * Classe du Sokoban
 */
public class Sokoban {
    
    /**
     * Constructeur de la classe Sokoban
     * Permet à l'utilisateur de choisir son niveau et lance ensuite la carte pour initialiser le niveau
     * @param args
     * @throws IOException
     */
    public static void main (String[] args) throws IOException {
        String nomFichier;
        
        nomFichier = "bin/map/map0.txt";
        Carte carte = new Carte(nomFichier);
        new VueSokoban(carte);
    }
}
