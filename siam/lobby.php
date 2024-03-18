<?php
session_start();

if (isset($_POST['creer'])) {
    header("Location: creerPartie.php");
    exit();
}

if (isset($_POST['mesParties'])) {
    $_SESSION['mesParties'] = true;
    header('Location: lobby.php');
    exit();
}

if (isset($_POST['lesParties'])) {
    $_SESSION['mesParties'] = false;
    header('Location: lobby.php');
    exit();
}



if (isset($_POST['changeMdp'])) {
    header("Location: changeMdp.php");
    exit();
}
if (isset($_POST['creerCompte'])) {
    header("Location: creerCompte.php");
    exit();
}

if (isset($_POST['quitter'])) {
    if (isset($_SESSION['pseudo'])) {
        session_unset();
        session_destroy();
    }
    header('Location: accueil.php');
    exit();
}

$isAdmin = isset($_SESSION['estAdmin']) ? $_SESSION['estAdmin'] : false;
$mesParties = isset($_SESSION['mesParties']) ? $_SESSION['mesParties'] : false;

$file_db = new PDO('sqlite:bdSiam.sqlite3');

$query = "SELECT IdPartie, Plateau, Tour, Fini, Joueur1, Joueur2 FROM Partie";
$parties = $file_db->query($query);

if (isset($_POST['supprimer'])) {
    $id_partie = $_POST['idPartieSuppr'];
    $delete = $file_db->prepare("DELETE FROM Partie WHERE IdPartie = :IdPartie");
    $delete->bindParam(':IdPartie', $id_partie);
    $delete->execute();
    header("Refresh:0");
}

if (isset($_POST['rejoindre'])) {
    $idPartie = $_POST['idPartie'];
    
    $query = "SELECT Joueur1, Joueur2 FROM Partie WHERE IdPartie = :idPartie";
    $stmt = $file_db->prepare($query);
    $stmt->execute(array(':idPartie' => $idPartie));
    $partie = $stmt->fetch(PDO::FETCH_ASSOC);
    
    if (is_null($partie['Joueur2']) && ($_SESSION['pseudo'] != $partie['Joueur1'] || $isAdmin)) {    
        $updateQuery = "UPDATE Partie SET Joueur2 = :pseudo WHERE IdPartie = :idPartie";
        $updateStmt = $file_db->prepare($updateQuery);
        $updateStmt->execute(array(':pseudo' => $_SESSION['pseudo'], ':idPartie' => $idPartie));
                
        $_SESSION['idPartie'] = $idPartie;
        header('Location: game.html');
        exit();
    } else if ($_SESSION['pseudo'] == $partie['Joueur1'] || $_SESSION['pseudo'] == $partie['Joueur2'] || $isAdmin) {
        $_SESSION['idPartie'] = $idPartie;
        header('Location: game.html');
        exit();
    } else {
        $error = "La partie est pleine. Créez-en une ou rejoignez une autre partie.";
    }
}


$file_db = null;
?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta name="author1" content="BORIS KERLOCH">
    <meta name="author2" content="AXEL BRISSY">
    <title>Lobby Jeu du SIAM</title>
    <link rel="stylesheet" type="text/css" href="lobby.css">
</head>

<body>

    <!-- partie haute du lobby -->
    <div class="haut">
        <img src="./img/logo_sia.gif" alt="Logo du jeu du siam"/>
        <!-- <?php
            echo "<h1>Bonjour " . htmlspecialchars($_SESSION['pseudo']) . " ! </h1>";
        ?> -->
    </div>

    <!-- tous les boutons du lobby -->
    <div class="container">
        <div class="menu">
            <form method="post">
                <div class="creerPartie">
                    <input type="submit" name="creer" value="Créer une nouvelle partie">
                </div>
            </form>

            <form method="post">
                <div class="partiesJoignables">
                    <input type="submit" name="lesParties" value="Afficher les parties en cours">
                </div>
            </form>

            <form method="post">
                <div class="mesParties">
                    <input type="submit" name="mesParties" value="Afficher mes parties en cours">
                </div>
            </form>
        </div>

        <div class="infoJoueur">
            <form method="post">
                <div class="changeMdp">
                    <input type="submit" name="changeMdp" value="Changer le mot de passe">
                </div>
            </form>

            <?php if ($isAdmin == true) { ?>
                <form method="post" action="creerCompte.php">
                    <div class="creerCompte">
                        <input type="submit" name="creerCompte" value="Créer un compte">
                    </div>
                </form>
            <?php } ?>

            <form method="post">
                <div class="deconnexion">
                    <input type="submit" name="quitter" value="Se déconnecter">
                </div>
            </form>
        </div>
    </div>

    <div class="lesParties">
        <!-- message d'erreur si le joueur tente de se connecter alors qu'il ne peut pas -->
        <?php if(isset($error)) echo $error ?>
        <!-- affichage des parties en fonction de si le joueur à choisi d'afficher ses parties ou toutes les parties -->

        <!-- ici s'affiche toutes MES parties (celles du joueur) -->
        <?php foreach ($parties as $partie): ?>
            <?php if ($mesParties && (($_SESSION['pseudo'] == $partie['Joueur1'] || $_SESSION['pseudo'] == $partie['Joueur2'])) || $isAdmin == true): ?>
                <div class="partie">
                    <p>
                        <strong><span><?php echo "Partie numéro : " . $partie['IdPartie']; ?></span></strong><br>

                        <?php echo $partie['Joueur1'] . " VS " . (($partie['Joueur2'] != null) ? $partie['Joueur2'] : 'X'); ?>
                        . C'est au tour de :
                        <?php echo ($partie['Tour'] == 0) ? $partie['Joueur1'] : $partie['Joueur2']; ?>. La partie
                        <?php echo ($partie['Fini'] == 1) ? "est terminée !" : "est en cours !"; ?>
                    </p>
                    <form method="post">
                        <input type="hidden" name="idPartie" value="<?php echo $partie['IdPartie']; ?>">
                        <input type="submit" name="rejoindre" value="Rejoindre la partie">
                    </form>
                    <?php if ($isAdmin == true) { ?>
                    <form method="post">
                    <input type="hidden" name="idPartieSuppr" value="<?php echo $partie['IdPartie']; ?>">
                        <input type="submit" name="supprimer" value="Supprimer la partie">
                    </form>
                    <?php } ?>
                </div>
            
            <!-- ici s'affiche toutes LES parties (celles de tous les joueurs) -->
            <?php elseif (!$mesParties): ?>

                <div class="partie">
                    <p>
                        <strong><span><?php echo "Partie numéro : " . $partie['IdPartie']; ?></span></strong><br>

                        <?php echo $partie['Joueur1'] . " VS " . (($partie['Joueur2'] != null) ? $partie['Joueur2'] : 'X'); ?>
                        . C'est au tour de :
                        <?php echo ($partie['Tour'] == 0) ? $partie['Joueur1'] : $partie['Joueur2']; ?>. La partie
                        <?php echo ($partie['Fini'] == 1) ? "est terminée !" : "est en cours !"; ?>
                    </p>
                    <form method="post">
                        <input type="hidden" name="idPartie" value="<?php echo $partie['IdPartie']; ?>">
                        <input type="submit" name="rejoindre" value="Rejoindre la partie">
                    </form>
                    <?php if ($isAdmin == true) { ?>
                    <form method="post">
                    <input type="hidden" name="idPartieSuppr" value="<?php echo $partie['IdPartie']; ?>">
                        <input type="submit" name="supprimer" value="Supprimer la partie">
                    </form>
                    <?php } ?>
                </div>
            <?php endif; ?>
        <?php endforeach; ?>
    </div>
</body>

</html>