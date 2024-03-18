<?php 
session_start();

if ((isset($_POST['ancienMdp'])) && (isset($_POST['nouveauMdp']))) {
    $pseudo = $_SESSION['pseudo'];
    $ancienMdp = $_POST['ancienMdp'];
    $nouveauMdp = $_POST['nouveauMdp'];
    
    try {
        $db = new PDO('sqlite:bdSiam.sqlite3');

        $joueur = $db->prepare("SELECT * FROM Utilisateur WHERE Pseudo = :pseudo");
        $joueur->execute(array(':pseudo' => $pseudo));
        $utilisateur = $joueur->fetch(PDO::FETCH_ASSOC);

        if ($ancienMdp == $utilisateur['MotDePasse']) {
            $updateDb = $db->prepare("UPDATE Utilisateur SET MotDePasse = :nouveauMdp WHERE Pseudo = :pseudo");
            $updateDb->execute(array(':nouveauMdp' => $nouveauMdp, ':pseudo' => $_SESSION['pseudo']));

            header('Location: lobby.php');
            exit();
        } else {
            $error = "Ancien mot de passe incorrect !"; 
        }
    } catch(PDOException $e) {
        $error = "Erreur de connexion à la base de données: " . $e->getMessage();
    }
}

if (isset($_POST['quitter'])) {
    header('Location: lobby.php');
    exit();
}

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta name="author1" content="BORIS KERLOCH">
    <meta name="author2" content="AXEL BRISSY">
    <title>Changement de mot de passe</title>
    <link rel="stylesheet" type="text/css" href="connexion.css">
</head>
<body>
    <div class="login">
        <h1>Changement de MDP</h1>
        <form method="post">
            <div class="form-group">
                <input type="text" id="ancienMdp" name="ancienMdp" placeholder="Ancien mot de passe">
            </div>
            
            <div class="form-group">
                <input type="password" name="nouveauMdp" placeholder="Nouveau mot de passe">
            </div>
            
            <?php 
                if (isset($error)) {
                    echo "<p style='color:#6f1d1b;'>$error</p>";
                }
            ?>

            <div class="form-connexion">
                <input type="submit" name="confirmer" value="Confirmer le changement">
            </div>
        </form>

        <form method="post">
            <div class="quitter">
                <input type="submit" name="quitter" value="Annuler">
            </div>
        </form>

        <p>Si vous avez un soucis pour vous rappeler de votre mot de passe actuel, désolé, c'est tant pis.</p>

    </div>
</body>
</html>
