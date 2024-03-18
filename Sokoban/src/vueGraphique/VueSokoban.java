/**
 * Classe VueSokoban
 *
 * @author Kerloch Boris
 * @version 1.0
 */
package vueGraphique;

import modele.*;

import java.awt.*;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.awt.event.KeyEvent;
import javax.swing.*;

/**
 * Classe VueSokoban qui implements KeyListener
 * Ses variables globales sont un Carte modele, un int nbLig, un int nbCol, un JFrame fenetre et un JPanel panel
 */
public class VueSokoban implements KeyListener {

    private Carte modele;
    private int nbLig;
    private int nbCol;
    private JFrame fenetre;
    private JPanel panel; 
    private int numMap;

    /**
     * Constructeur de la classe VueSokoban
     * @param modele : le niveau de jeu du sokoban
     */
    public VueSokoban (Carte modele) {
        this.modele = modele;
        init_carte();
    }       

    /** 
     * Retourne du vide et permet de faire les déplacements du joueur. 
     * Les déplacements permettent le déplacement du robot et après chaque coup, la carte se met à jour. 
     * Si la partie est finie, la fenêtre se ferme
     * @param e : un événement créé par l'utilisateur
     */
    public void keyPressed (KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT :
                modele.deplaceRobot(Direction.GAUCHE);
                break;
            case KeyEvent.VK_RIGHT :
                modele.deplaceRobot(Direction.DROITE);
                break;
            case KeyEvent.VK_UP :
                modele.deplaceRobot(Direction.HAUT);
                break;
            case KeyEvent.VK_DOWN :
                modele.deplaceRobot(Direction.BAS);
                break;
            case KeyEvent.VK_R :
                try {
                    chargerCarte();
                } catch (IOException e1) {
                    System.out.println("Erreur création");
                }
                break;
        }
        update_carte();
        if (modele.finDePartie()) {
            if (numMap == 3) {
                fenetre.setVisible(false);
                fenetre.dispose();
            } else {
                numMap++;
                try {
                    chargerCarte();
                } catch (IOException e1) {
                    System.out.println("Erreur création");
                }
            }
        }
    }

    /**
     * Permet de recommencer la partie si la touche r est actionnée (dans les touches possibles dans keyPressed) ou lance la carte suivante
     * @throws IOException
     */
    public void chargerCarte() throws IOException {
        modele.lireCarte("bin/map/map" + numMap + ".txt");
        fenetre.dispose();
        init_carte();
    }

    /**
     * @param e : sait si la touche est désactivée
     */
    public void keyReleased (KeyEvent e) {}

    /**
     * @param e : sait si la touche est activée
     */
    public void keyTyped (KeyEvent e) {}

    /**
     * Permet d'initialiser la fenêtre principale avec son nom et de l'afficher
     * Ensuite la fonction fait appel à la fonction update_carte qui initialise une première fois la carte
     */
    public void init_carte () {
        this.nbLig = modele.getNbLig();
        this.nbCol = modele.getNbCol();
        this.panel = new JPanel(new GridLayout(nbLig, nbCol));

        //Initialisation fenêtre :
        fenetre = new JFrame("Le jeu du Sokoban");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.addKeyListener(this);
        
        // Container et grid layout :
        update_carte();
        // Affichage fenêtre :
        fenetre.add(panel);
        fenetre.pack();
        fenetre.setVisible(true);
    }

    /**
     * Permet de créer la carte et de la mettre à jour suite aux mouvements du robot
     * La fonction change le panel actuel après chaque update par le nouveau
     */
    public void update_carte() {
        panel.removeAll();
        for (int i = 0; i < nbLig; i++) {
            for (int j = 0; j < nbCol; j++) {
                if (modele.getMatrice(i,j) instanceof Sol) {
                    ImageIcon image = new ImageIcon( ( (Sol)modele.getMatrice(i,j)).toImage());
                    JLabel label = new JLabel (image);
                    panel.add(label);
                }
                if (modele.getMatrice(i,j) instanceof Mur) {
                    ImageIcon image = new ImageIcon( ( (Mur)modele.getMatrice(i,j)).toImage());
                    JLabel label = new JLabel (image);
                    panel.add(label);
                }
                if (modele.getMatrice(i,j) instanceof Destination) {
                    ImageIcon image = new ImageIcon( ( (Destination)modele.getMatrice(i,j)).toImage());
                    JLabel label = new JLabel (image);
                    panel.add(label);
                }
                if (modele.getMatrice(i,j) instanceof EstVide) {
                    ImageIcon image = new ImageIcon( ( (EstVide)modele.getMatrice(i,j)).toImage());
                    JLabel label = new JLabel (image);
                    panel.add(label);
                }
            }
        }
        panel.revalidate();
        panel.repaint();
    }
}