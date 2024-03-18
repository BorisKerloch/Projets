/**
 * Classe Lecture
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package modele;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe Lecture qui a List<String> lignes, int nbLignes et int tailleLignes en variables globales
 */
public class Lecture {
    private List<String> lignes;
    private int nbLignes;
    private int tailleLignes;

    /**
     * Le constructeur de la classe Lecture qui permet de lire un fichier donné
     * @param nomFichier : nom du fichier donné
     * @throws IOException 
     */
    public Lecture(String nomFichier) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            lignes = new ArrayList<>();
            nbLignes = 0;
            tailleLignes = -1; // initialisation à une valeur impossible

            while ((ligne = br.readLine()) != null) {
                lignes.add(ligne);
                nbLignes++;

                if (tailleLignes == -1) {
                    // si c'est la première ligne, on initialise la taille des lignes
                    tailleLignes = ligne.length();
                } else if (ligne.length() != tailleLignes) {
                    // si une ligne a une taille différente des autres, on lève une exception
                    throw new IllegalArgumentException("Toutes les lignes doivent avoir la même taille.");
                }
            }
            br.close();
        }
    }

    /**
     * Retourne un tableau des lignes du fichier
     * @return (ArrayList<String>) : les lignes du fichier en tableau
     */
    public ArrayList<String> getLignes() {
        return (ArrayList<String>) lignes;
    }

    /**
     * Retourne le nombre de ligne du fichier lu
     * @return (int) : le nombre de ligne du fichier
     */
    public int getNbLignes() {
        return nbLignes;
    }

    /**
     * Retourne la taille d'une ligne (autrement dit la taille de la colonne qui sera utilisée ultérieurement) du fichier lu
     * @return (int) : la taille d'une ligne
     */
    public int getTailleLignes() {
        return tailleLignes;
    }
}

