import random

class Case:
    """création de la classe pour une case"""
    
    def __init__ (self, couleur):
        """ construit la case """
        self.__couleur = couleur
        self.__compo = -1
            
    def compo (self):
        """ Case -> int
        retourne le numéro de la composante
        """
        return self.__compo
    
    def pose_compo (self, val):
        """ Case, int -> none
        affecte le int val comme numéro de composane à une case choisie
        """
        self.__compo = val
    
    def supprime_compo (self):
        """ Case -> int
        met le numéro de la composante de la case à -1
        """
        if self.est_vide():
            self.__compo = 0
        else:
            self.__compo = -1
    
    def parcourue (self):
        """ Case -> boolean
        retourne si une case est affectée par un numéro de composante
        """
        return self.__compo >= 0
        
    def couleur (self):
        """ Case -> int
        retourne la couleur d une bille
        """
        return self.__couleur
        
    def change_couleur (self, new_couleur):
        """ Case -> int
        change la couleur de la bille
        """
        self.__couleur = new_couleur
        return self.__couleur
        
    def supprime(self):
        """ Case -> int
        met la couleur à -1 (la supprime sur le plateau) et met la compo à 0
        """
        self.__couleur = -1
        self.__compo = 0
        
    def est_vide (self):
        """ Case -> boolean
        indique si la case est vide
        """
        return self.__couleur == -1
        
        
##############################################################################################
        
        
class ModeleSame:
    """création de la classe du modèle du Same"""
    
    def __init__ (self, nb_lig=10, nb_col=10, nb_couleurs=3):
        """ constructeur du modèle """
        self.__nblig = nb_lig
        self.__nbcol = nb_col
        self.__nbcouleurs = nb_couleurs
        self.__score = 0
        self.nouvelle_partie()
    
    def compo (self, i, j):
        """ ModeleSamen int, int -> int
        retourne le numéro de la composante d'une bille donnée en (i,j)
        """
        return self.__mat[i][j].compo()

    def calcule_composante (self):
        """ ModeleSame
        initialise self.__nb_elts_compo à 0 et initialise le num_compo
        à 1. La méthode calcule ensuite la composante d'une case donnée
        """
        num_compo = 1
        self.__nb_elts_compo = [0]
        for i in range (self.__nblig):
            for j in range(self.__nbcol):
                if self.__mat[i][j].compo() == -1 :
                    num = self.calcule_composante_numero(i,j, num_compo, self.__mat[i][j].couleur())
                    self.__nb_elts_compo.append(num)
                    num_compo += 1
                    
    def calcule_composante_numero (self, i, j, num_compo, couleur):
        """ ModeleSame, int, int, int, int -> int
        cette méthode récursive permet d'obtenir la valeur d'une composante
        """
        if self.__mat[i][j].compo() != -1 or self.__mat[i][j].couleur() != couleur:
            res=0
        else:
            self.__mat[i][j].pose_compo(num_compo)
            res=1
            if self.coords_valides(i-1, j):
                res += self.calcule_composante_numero(i-1, j, num_compo, couleur)
            if self.coords_valides(i, j-1):
                res += self.calcule_composante_numero(i, j-1, num_compo, couleur)
            if self.coords_valides(i, j+1):
                res += self.calcule_composante_numero(i, j+1, num_compo, couleur)
            if self.coords_valides(i+1, j):
                res += self.calcule_composante_numero(i+1, j, num_compo, couleur)
        return res
                
    def recalc_composantes (self):
        """ ModeleSame -> int
        supprime la composante attribuée à chaque case et fait appelle à la méthode calcule_composante()
        """
        for i in range (self.__nblig):
            for j in range(self.__nbcol):
                self.__mat[i][j].supprime_compo()
        self.calcule_composante()
    
    def supprime_composantes (self, num_compo):
        """ ModeleSame -> boolean
        retourne si oui ou non une suppression a eu lieu
        """
        if self.__nb_elts_compo[num_compo] >= 2:
            for j in range (self.__nbcol):
                self.supprime_composante_colonne(j, num_compo)
            self.__score += ((self.__nb_elts_compo[num_compo]) - 2)**2
            self.supprime_colonne_vide()
            self.recalc_composantes()
            return True
        return False
    
    
    def supprime_composante_colonne(self,j, num_compo):
        """ ModelSame, int, int -> none
        supprime les billes de la composante num_compo dans une colonne donnée
        et les cases vides sont mises en haut de colonne. Les cases pleines tombent
        verticalement
        """
        for i in range (self.__nblig):
            if self.compo(i,j) == num_compo:
                self.supprimer_bille(i, j)
                
        colonne = []
        for i in range (self.__nblig):
            colonne.append(self.couleur(i,j))
        
        new_colonne = []
        for a in range (self.__nblig):
            if colonne[a] == -1:
                new_colonne.append(colonne[a])
        for b in range (self.__nblig):
            if colonne[b] != -1:
                new_colonne.append(colonne[b])
        
        for g, couleur in enumerate(new_colonne):
            self.__mat[g][j].change_couleur(couleur)
            
    def supprime_colonne_vide (self):
        """ ModeleSame -> none
        si une colonne est vide, la méthode la place le plus à droite
        possible
        """
        matrice = []
        for j in range(self.__nbcol):
            matrice.append([])
            for i in range(self.__nblig):
                matrice[j].append(self.couleur(i,j))

        new_matrice = []
        for col in range(self.__nbcol):
            if not self.colonne_vide(col):
                new_matrice.append(matrice[col])

        for col in range(self.__nbcol):
            if self.colonne_vide(col):
                new_matrice.append(matrice[col])

        for i in range(self.__nblig):
            for j in range(self.__nbcol):
                self.__mat[i][j].change_couleur(new_matrice[j][i])

    def colonne_vide (self, j):
        """ ModeleSame, int -> bool
        retourne si une colonne est vide
        """
        for i in range(self.__nblig):
            if self.__mat[i][j].couleur() != -1:
                return False
        return True
    
    def score (self):
        """ ModeleSame -> int
        retourne le score
        """
        return self.__score
    
    def nblig (self):
        """ ModeleSame -> int
        retourne le nombre de lignes
        """
        return self.__nblig
    
    def nbcol (self):
        """ ModeleSame -> int
        retourne le nombre de colones
        """
        return self.__nbcol
    
    def coords_valides (self, i, j):
        """ ModeleSame -> boolean
        retourne si les coordonnées données sont valides
        """
        return 0 <= i < self.__nblig and 0 <= j < self.__nbcol
                
    def couleur(self, i, j):
        """ ModeleSame -> boolean
        retourne la couleur de la bille en coordonnées i et j
        """
        return self.__mat[i][j].couleur()
     
    def supprimer_bille (self, i, j):
        """ ModeleSame -> none
        supprime une bille aux coordonnées i et j
        """
        return self.__mat[i][j].supprime()
    
    def nouvelle_partie (self):
        """ ModeleSame -> none
        réinitialise le modèle du same
        """
        self.__mat = []
        for i in range (self.__nblig):
            self.__mat.append([])
            for j in range(self.__nbcol):
                self.__mat[i].append(Case(random.randint(0, self.__nbcouleurs -1)))
        self.recalc_composantes()
        self.__score = 0
    
    def est_vide(self, i, j):
        """ ModeleSame, int, int -> bool
        retourne si la case donnée est vide ou non
        """
        return self.__mat[i][j].est_vide()

    def nb_elts_compo(self):
        """ ModeleSame -> list
        retourne la liste des éléments de compo
        """
        return self.__nb_elts_compo

    def plus_grande_compo(self):
        """ModeleSame -> int
        retourne la plus grande composante de la liste de composante
        """
        ret = 0
        for i in range(len(self.__nb_elts_compo)):
            if self.__nb_elts_compo[ret] <= self.__nb_elts_compo[i]:
                ret = i
        return ret

