<?php 
session_start();

if (isset($_SESSION['pseudo'])) {
    header('Location: lobby.php');
    exit();
}

if ((isset($_POST['pseudo'])) && (isset($_POST['mdp']))) {
    $pseudo = $_POST['pseudo'];
    $motDePasse = $_POST['mdp'];

    try {
        $db = new PDO('sqlite:bdSiam.sqlite3');

        $joueur = $db->prepare("SELECT * FROM Utilisateur WHERE Pseudo = :pseudo");
        $joueur->execute(array(':pseudo' => $pseudo));
        $utilisateur = $joueur->fetch(PDO::FETCH_ASSOC);

        if ($utilisateur && $motDePasse == $utilisateur['MotDePasse']) {
            $_SESSION['pseudo'] = $pseudo;
            if ($utilisateur['EstAdmin']) {
                $_SESSION['estAdmin'] = true;
            } else {
                $_SESSION['estAdmin'] = false;
            }
            header('Location: lobby.php');
            exit();
        } else {
            $error = "Votre identifiant ou mot de passe est incorrect";
        }
    } catch(PDOException $e) {
        $error = "Erreur de connexion à la base de données: " . $e->getMessage();
    }
}

if (isset($_POST['quitter'])) {
    if (isset($_SESSION['pseudo'])) {
        session_unset();
        session_destroy();
    }
    header('Location: accueil.php');
    exit();
}

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta name="author1" content="BORIS KERLOCH">
    <meta name="author2" content="AXEL BRISSY">
    <title>Connexion SIAM</title>
    <link rel="stylesheet" type="text/css" href="connexion.css">
</head>
<body>
    <div class="login">
        <h1>CONNEXION</h1>
        <form method="post" action="connexion.php">
            <div class="form-group">
                <input type="text" id="pseudo" name="pseudo" placeholder="Pseudonyme">
            </div>
            
            <div class="form-group">
                <input type="password" name="mdp" placeholder="Mot de passe">
            </div>
            
            <?php 
                if (isset($error)) {
                    echo "<p style='color:#6f1d1b;'>$error</p>";
                }
            ?>

            <div class="form-connexion">
                <input type="submit" name="connecter" value="Connexion">
            </div>
        </form>

        <form method="post">
            <div class="quitter">
                <input type="submit" name="quitter" value="Retourner à la page d'accueil">
            </div>
        </form>

        <p>Pas encore inscrit ? Inscrivez-vous <a href="inscription.php">ici</a></p>

    </div>
</body>
</html>
