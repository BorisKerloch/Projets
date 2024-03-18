<?php
    include_once('configurationBD.php');
?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta name="author1" content="BORIS KERLOCH">
    <meta name="author2" content="AXEL BRISSY">
    <title>Accueil SIAM</title>
    <link rel="stylesheet" type="text/css" href="accueil.css">
</head>
<body>
    
    <div class="image">
        <img src="./img/logo_sia.gif" alt="Logo du jeu du siam"/>
    </div>

    <div class="content">
        <div class="presentation">
            <h1>JEU DU SIAM</h1>
            <p>Bienvenue dans le jeu du SIAM</p>
            <p>Du rhinocéros et de l'éléphant, lequel est le plus fort ? 
            Cette question fait l'objet de vifs débats depuis l'aube de l'humanité. 
            Maintenant, vous allez pouvoir choisir un camp ! Siam, un jeu de Didier Dhorbait publié par Ferti Games</p>
        </div>

        <div class="login">
            <P>Vous pouvez vous connecter ou vous inscrire en cliquant sur les boutons</P>
            <p>Il existe un profil "admin". Pseudo : admin | Mot de passe : admin.</p>
            <form method="post" action="connexion.php">
                <input type="submit" name="connecter" value="Se connecter">
            </form>
            <form method="post" action="inscription.php">
                <input type="submit" name="inscription" value="S'inscrire">
            </form>
        </div>
    </div>
</body>
</html>
