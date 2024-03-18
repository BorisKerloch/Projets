/**
 * Classe SokobanTexte
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package vueTexte;

import java.io.IOException;

/**
 * Classe SokobanTexte
 */
public class SokobanTexte{
    
    /**
     * Constructeur de SokobanTexte qui permet de lancer la version texte du jeu en créant un nouveau ModeTexte
     * @param paramArrayOfString
     * @throws IOException
     */
    public static void main(String[] paramArrayOfString)  throws IOException {
        new ModeTexte();
    }
}