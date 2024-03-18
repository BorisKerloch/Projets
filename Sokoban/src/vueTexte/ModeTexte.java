/**
 * Classe ModeTexte
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package vueTexte;

import modele.Carte;
import modele.Direction;
import java.io.IOException;

/**
 * Classe ModeTexte
 */
public class ModeTexte {

    /**
     * Constructeur de la classe ModeTexte
     * Selectione le fichier à lire en fonction d'un input entré par l'utilisateur, l'associe à la carte
     * Contient également un while qui permet de considérer les choix de déplacements de l'utilisateur et considère la fin de partie
     * @throws IOException
     */
    public ModeTexte () throws IOException  {

        System.out.println("Entrez le niveau que vous voulez faire (allant de 1 à 3) : ");
        char input = Outil.lireCaractere();
        System.out.println("Vous avez choisi le niveau : " + input + ".\nLes touches sont z : haut, q : gauche, s : bas, d : droite");


        String nomFichier;
        if (input == '1') {
            nomFichier = "bin/map/map1.txt";
        } else if (input == '2') {
            nomFichier = "bin/map/map2.txt";
        } else if (input == '3') {
            nomFichier = "bin/map/map3.txt";
        } else {
            nomFichier = "bin/map/map0.txt";
        }

        Carte carte = new Carte(nomFichier);

        char choix;
        System.out.println(carte);
        while (carte.finDePartie() == false) {
            System.out.print("Déplacer le robot :");
            choix = Outil.lireCaractere();
            switch(choix) {
                case 'z' :
                    carte.deplaceRobot(Direction.HAUT);
                    break;
                case 'q' :
                    carte.deplaceRobot(Direction.GAUCHE);
                    break;
                case 'd' :
                    carte.deplaceRobot(Direction.DROITE);
                    break;
                case 's' :
                    carte.deplaceRobot(Direction.BAS);
                    break;
                default :
                    System.out.println("Caractère non reconnu");
            }
            System.out.println(carte);
        }
    }
}