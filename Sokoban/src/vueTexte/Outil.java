/**
 * Classe Outil
 *
 * @author Kerloch Boris
 * @version 1.0
 */

package vueTexte;

import java.io.IOException;

/**
 * Classe Outil
 */
public class Outil {

  /**
   * Le constructeur de la casse Outil, qui permet de lire un caractère
   * @return (char) : le caractère lu
   */
  public static char lireCaractere() {
    int rep = ' ';
    int buf;
    try {
      rep = System.in.read();
      buf = rep;
      while (buf != '\n') {
        buf = System.in.read();
      }
    }
    catch (IOException e) {};
    return (char) rep;
  }
}

