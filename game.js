document.addEventListener('DOMContentLoaded', function () {
    let board = document.getElementById('game-board');
    let reservej1 = document.getElementById('reserve-j1');
    let reservej2 = document.getElementById('reserve-j2');
    let infos_joueurs = document.getElementById('infos-joueurs');
    let infos_tour = document.getElementById('infos-tour');
    let infos_pseudo_connecter = document.getElementById('infos-pseudo-connecter');
    let infos_to_php = "pousser";
    // if poser ==> peux poser une piece quand on clique
    // if empty ==> peux selectionner une piece quand on clique pour ensuite la pousser
    let player = 0;
    let cpt_reserve_j1 = 0;
    let cpt_reserve_j2 = 0;
    let pseudo_connecter;
    let joueur = [];
    let direction = 0;
    let boardArray = [];
    let row;
    let col;

    let pieces = [];
    for (let i = 1; i < 3; i++) {
        for (let j = 0; j < 4; j++) {
            pieces.push('img/' + i + j + '.gif');
        }
    }
    initializeGame();
    updatePlateau();

    function initializeGame() {
        // Créer et placer l'image du plateau en utilisant JavaScript
        const plateau = document.createElement('img');
        plateau.src = 'img/plateau.jpg';
        plateau.classList.add('plateau');
        board.appendChild(plateau);

        // initialiser le plateau avec des cellules vides
        for (let i = 0; i < 5; i++) {
            for (let j = 0; j < 5; j++) {
                const cell = document.createElement('div');
                cell.classList.add('cell');
                cell.dataset.row = i;
                cell.dataset.col = j;
                cell.style.height = 80 + 'px';
                cell.style.width = 80 + 'px';
                cell.style.top = (20 + i * 80) + 'px';
                cell.style.left = (20 + j * 80) + 'px';
                cell.addEventListener('click', handleCellClick);
                board.appendChild(cell);
            }
        }

        // initialiser les div pour pousser et poser


        document.getElementById('dir_haut').addEventListener('click', function () {
            direction = 0;
        });

        document.getElementById('dir_droite').addEventListener('click', function () {
            direction = 1;
        });

        document.getElementById('dir_bas').addEventListener('click', function () {
            direction = 2;
        });

        document.getElementById('dir_gauche').addEventListener('click', function () {
            direction = 3;
        });

        document.getElementById('toLobby').addEventListener('click', function () {
            window.location.href = 'lobby.php';
        });

        document.getElementById('poser').addEventListener('click', function () {
            infos_to_php = "poser";
        });

        document.getElementById('pousser').addEventListener('click', function () {
            envoyerData(row, col);
        });

    }

    function afficherPlateau() {
        // supprimer les pièces existantes
        const cells = document.querySelectorAll('.cell');
        cells.forEach(cell => {
            while (cell.firstChild) {
                cell.removeChild(cell.firstChild);
            }
        });


        // afficher les pièces du plateau
        for (let i = 0; i < boardArray.length; i++) {
            for (let j = 0; j < boardArray[i].length; j++) {
                if (boardArray[i][j] !== "empty") {
                    let piece = document.createElement('img');
                    piece.src = 'img/' + boardArray[i][j] + '.gif';
                    piece.classList.add('piece');
                    const cell = document.querySelector(`.cell[data-row="${i}"][data-col="${j}"]`);
                    cell.appendChild(piece);
                }
            }
        }
    }

    async function recupData() {
        try {
            const response = await fetch('game.php');
            if (!response.ok) {
                throw new Error("HTTP error " + response.status);
            }
            const text = await response.text();
            const data = JSON.parse(text);
            boardArray = data["boardArray"]["board"];
            cpt_reserve_j1 = data["boardArray"]["reserveJ1"];
            cpt_reserve_j2 = data["boardArray"]["reserveJ2"];
            joueur = data["joueurs"];
            player = data["tour"];
            pseudo_connecter = data["pseudo-connecter"];
        } catch (error) {
            console.error('Fetch error:', error);
        }
    }

    async function updatePlateau() {
        await recupData();
        afficherPlateau();
        reservej1.innerHTML = "Réserves J1 : " + cpt_reserve_j1;
        reservej2.innerHTML = "Réserves J2 : " + cpt_reserve_j2;
        infos_joueurs.innerHTML = "Joueur 1 : " + joueur[0] + " | Joueur 2 : " + joueur[1];
        infos_tour.innerHTML = "Tour du joueur : " + joueur[player];
        infos_pseudo_connecter.innerHTML = "Connecté en tant que : " + pseudo_connecter;
    }

    function envoyerData(row, col) {
        console.log('clicked on cell', row, col);
        fetch('game.php', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ row: row, col: col, dir: direction, info: infos_to_php })
        })
            .then(response => response.text())
            .then(text => {
                console.log('Success:', text);
            })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
        updatePlateau();
    }


    function handleCellClick(event) {
        const clickedCell = event.currentTarget;
        row = clickedCell.dataset.row;
        col = clickedCell.dataset.col;
        console.log('clicked on cell', row, col);

        if (infos_to_php === "poser") {
            envoyerData(row, col);
            infos_to_php = "pousser";
        }
    }

});