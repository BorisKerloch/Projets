<?php 
    session_start();

    
    if ((isset($_POST['pseudo'])) && (isset($_POST['mdp'])) && (isset($_POST['mdpConf']))) {
        $pseudo = $_POST['pseudo'];
        $motDePasse = $_POST['mdp'];
        $mdpConf = $_POST['mdpConf'];
        
        if ($mdpConf == $motDePasse) {


            try {
                $db = new PDO('sqlite:bdSiam.sqlite3');
        
                // Vérifier si le compte existe déjà
                $joueur = $db->prepare("SELECT * FROM Utilisateur WHERE Pseudo = :pseudo");
                $joueur->execute(array(':pseudo' => $pseudo));
                $utilisateur = $joueur->fetch(PDO::FETCH_ASSOC);
        
                if ($utilisateur) {
                    $error = "Ce pseudo est déjà utilisé, veuillez en choisir un autre.";
                } else {
                    $user = $db->prepare("INSERT INTO Utilisateur (Pseudo, MotDePasse, EstAdmin) VALUES (:pseudo, :motDePasse, :estAdmin)");
                    $user->execute(array(':pseudo' => $pseudo, ':motDePasse' => $motDePasse, ':estAdmin' => 0));
        
                    $_SESSION['pseudo'] = $pseudo;
                    header('Location: lobby.php');
                    exit();
                }
            } catch(PDOException $e) {
                $error = "Erreur de connexion à la base de données: " . $e->getMessage();
            }             
        } else {
            $error = "Les mots de passes ne correspondent pas";
        }
    }

    if (isset($_POST['quitter'])) {
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
    <title>Inscription SIAM</title>
    <link rel="stylesheet" type="text/css" href="connexion.css">
</head>
<body>
    <div class="login">
        <h1>INSCRIPTION</h1>
        <form method="post">
            <div class="form-group">
                <input type="text" id="pseudo" name="pseudo" placeholder="Pseudonyme">
            </div>
            
            <div class="form-group">
                <input type="password" id="mdp" name="mdp" placeholder="Mot de passe">
            </div>
            
            <div class="form-group">
                <input type="password" id="mdpConf" name="mdpConf" placeholder="Confirmer mot de passe">
            </div>

            <?php 
                if (isset($error)) {
                    echo "<p style='color:#6f1d1b;'>$error</p>";
                }
            ?>

            <div class="form-connexion">
                <input type="submit" name="connecter" value="S'inscrire">
            </div>
        </form>

        <form method="post">
            <div class="form-quitter">
                <input type="submit" name="quitter" value="Retourner à la page d'accueil">
            </div>
        </form>

        <p>Déjà inscrit ? Connectez-vous <a href="connexion.php">ici</a></p>

    </div>
</body>
</html>