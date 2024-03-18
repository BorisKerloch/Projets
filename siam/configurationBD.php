<?php 
$db_file = "bdSiam.sqlite3";

if (!file_exists($db_file)) {
    try {
        $file_db = new PDO('sqlite:bdSiam.sqlite3');

        $file_db->exec(
            "CREATE TABLE IF NOT EXISTS Utilisateur 
            (
                IdUtilisateur INTEGER PRIMARY KEY AUTOINCREMENT,
                Pseudo VARCHAR(50),
                MotDePasse VARCHAR(50),
                EstAdmin boolean
            )"
        );

        $admin = array (
            "Pseudo" => "admin",
            "MotDePasse" => "admin",
            "EstAdmin" => true
        );
        
        $insertion = "INSERT INTO Utilisateur (Pseudo, MotDePasse, EstAdmin) VALUES 
        ('{$admin["Pseudo"]}', '{$admin["MotDePasse"]}', {$admin["EstAdmin"]})";
        $file_db->exec($insertion);

        $file_db->exec(
            "CREATE TABLE IF NOT EXISTS Partie 
            (
                IdPartie INTEGER PRIMARY KEY AUTOINCREMENT,
                Plateau VARCHAR,
                Fini INTEGER,
                Tour INTEGER,
                Joueur1 VARCHAR,
                Joueur2 VARCHAR,
                FOREIGN KEY(Joueur1) REFERENCES Utilisateur(IdUtilisateur),
                FOREIGN KEY(Joueur2) REFERENCES Utilisateur(IdUtilisateur)
            )"
        );

        $file_db = null;
    } catch (PDOException $e) {
        echo $e->getMessage();
    }
}

?>
