<?php

// Inclure les classes
require_once 'classes/Plateau.php';
require_once 'classes/Piece.php';
require_once 'classes/Cell.php';

// Données à envoyer à js
$data = array();

$est_admin = false;
$joueur1;
$joueur2;

function string_to_plateau($string)
{
    $plateau = new Plateau();
    $src = json_decode($string, true);
    $array = $src['board'];
    for ($i = 0; $i < 5; $i++) {
        for ($j = 0; $j < 5; $j++) {
            $piece = $array[$i][$j];
            if ($piece == "empty") {
                $plateau->setPiece($i, $j, null);
            } elseif ($piece == "rocher") {
                $plateau->setPiece($i, $j, new Piece("rocher", ""));
            } else {
                $type = $piece[0];
                $direction = $piece[1];
                $plateau->setPiece($i, $j, new Piece($type, $direction));
            }
        }
    }
    $plateau->setReserveJ1($src['reserveJ1']);
    $plateau->setReserveJ2($src['reserveJ2']);
    return $plateau;
}

function string_to_reserve($string)
{
    $src = json_decode($string, true);
    return array($src['reserveJ1'], $src['reserveJ2']);
}

// Convertir le plateau en array pour l'envoyer à js
function plateau_to_Array($p, $reserveJ1, $reserveJ2)
{
    $board = array();
    for ($i = 0; $i < 5; $i++) {
        $board[$i] = array();
        for ($j = 0; $j < 5; $j++) {
            $piece = $p->getCase($i, $j)->getPiece();
            if ($piece == null) {
                $board[$i][$j] = "empty";
            } else {
                $board[$i][$j] = $piece->toString();
            }
        }
    }
    $result = array(
        "board" => $board,
        "reserveJ1" => $reserveJ1,
        "reserveJ2" => $reserveJ2
    );
    return $result;
}

function joueur_suivant($tour)
{
    return ($tour + 1) % 2;
}

function test_tour_joueur($est_admin, $data, $pseudo, $joueur1, $joueur2)
{
    if ($est_admin)
        return true;
    if ($data['tour'] == 0 && $pseudo == $joueur1)
        return true;
    if ($data['tour'] == 1 && $pseudo == $joueur2)
        return true;

    return false;
}


session_start();

if (!isset ($_SESSION['idPartie'])) {
    echo 'idPartie not set in session';
    exit();
}
$idPartie = $_SESSION['idPartie'];

if (!isset ($_SESSION['pseudo'])) {
    echo 'pas connecter';
    exit();
}
$pseudo = $_SESSION['pseudo'];

// Récupérer les données de la base de données
try {
    $file_db = new PDO('sqlite:bdSiam.sqlite3');

    // Récupérer le plateau de la base de données
    $stmt = $file_db->prepare('SELECT Plateau FROM Partie WHERE IdPartie = :idPartie');
    $stmt->execute(array(':idPartie' => $idPartie));
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    if ($result === false) {
        echo 'No row found for idPartie ' . $idPartie;
        exit();
    }
    $plateau = string_to_plateau($result['Plateau']);
    $reserve = string_to_reserve($result['Plateau']);
    $data['reserveJ1'] = $reserve[0];
    $data['reserveJ2'] = $reserve[1];

    // Récupérer le tour de la base de données
    $stmt = $file_db->prepare('SELECT Tour FROM Partie WHERE IdPartie = :idPartie');
    $result = $stmt->execute(array(':idPartie' => $idPartie));
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    if ($result === false) {
        echo 'No row found for idPartie ' . $idPartie;
        exit();
    }
    $data['tour'] = $result['Tour'];

    // récupérer si le joueur est admin
    $stmt = $file_db->prepare('SELECT EstAdmin FROM Utilisateur WHERE Pseudo = :pseudo');
    $result = $stmt->execute(array(':pseudo' => $pseudo));
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    $est_admin = $result['EstAdmin'];

    // récupérer les pseudos des joueurs
    $stmt = $file_db->prepare('SELECT Joueur1, Joueur2 FROM Partie WHERE IdPartie = :idPartie');
    $result = $stmt->execute(array(':idPartie' => $idPartie));
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    $joueur1 = $result['Joueur1'];
    $joueur2 = $result['Joueur2'];

    // récupérer les pseudos des joueurs
    $stmt = $file_db->prepare('SELECT Joueur1, Joueur2 FROM Partie WHERE IdPartie = :idPartie');
    $result = $stmt->execute(array(':idPartie' => $idPartie));
    $result = $stmt->fetch(PDO::FETCH_ASSOC);
    $data['joueurs'] = array($result['Joueur1'], $result['Joueur2']);


    $file_db = null;
} catch (PDOException $e) {
    echo $e->getMessage();
    exit();
}


// Récupérer les données envoyées par js
$data_js = json_decode(file_get_contents('php://input'), true);
if ($data_js != null) {
    $input_row = $data_js['row'];
    $input_col = $data_js['col'];
    $input_dir = $data_js['dir'];
    $input_info = $data_js['info'];
    error_log("row: " . $input_row . " | col: " . $input_col . " | dir: " . $input_dir . " | info: " . $input_info);

    if (test_tour_joueur($est_admin, $data, $pseudo, $joueur1, $joueur2)) {
        $rep = $plateau->jouer($input_row, $input_col, $data['tour'], $input_dir, $input_info);

        if ($rep) {
            // Mettre à jour la base de données
            try {
                $file_db = new PDO('sqlite:bdSiam.sqlite3');

                // Mettre à jour le plateau dans la base de données
                $stmt = $file_db->prepare('UPDATE Partie SET Plateau = :plateau WHERE IdPartie = :idPartie');
                $stmt->execute(array(':plateau' => json_encode($plateau->toArray()), ':idPartie' => $idPartie));

                // Mettre à jour le tour dans la base de données
                $stmt = $file_db->prepare('UPDATE Partie SET Tour = :tour WHERE IdPartie = :idPartie');
                $stmt->execute(array(':tour' => joueur_suivant($data['tour']), ':idPartie' => $idPartie));

                $file_db = null;
            } catch (PDOException $e) {
                echo $e->getMessage();
                exit();
            }
        }
    }
}


// Envoyer les données à js

$data["boardArray"] = plateau_to_Array($plateau, $data['reserveJ1'], $data['reserveJ2']);
$data["pseudo-connecter"] = $pseudo;

echo json_encode($data);

?>