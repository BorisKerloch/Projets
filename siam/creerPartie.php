<?php 
    require_once 'classes/Plateau.php';
    require_once 'classes/Piece.php';
    require_once 'classes/Cell.php';

    session_start();

    try {
        $file_db = new PDO ('sqlite:bdSiam.sqlite3');

        $pseudo = $_SESSION['pseudo'];
        $id_joueur = $file_db->prepare("SELECT Pseudo FROM Utilisateur WHERE Pseudo = :pseudo");
        $id_joueur->execute(array(':pseudo' => $pseudo));
        $utilisateur = $id_joueur->fetch(PDO::FETCH_ASSOC);

        $plateau = new Plateau();
        $json = json_encode($plateau->toArray());

        $req = $file_db->prepare("INSERT INTO Partie(Plateau, Fini, Tour, Joueur1, Joueur2) VALUES (:Plateau, :Fini, :Tour, :Joueur1, :Joueur2)");
        $req->execute(array(
            ':Plateau' => $json, //fichier json contenant les infos de la partie
            ':Fini' => 0,     //si partie finie, faudra afficher le gagnant
            ':Tour' => 0,     // tour de joueur 1 ou 2
            ':Joueur1' => $utilisateur['Pseudo'],
            ':Joueur2' => null
        ));

        $file_db = null;
        header("Location: lobby.php");
        exit();
    } catch(PDOException $e) {
        echo $e->getMessage();
    }
?>