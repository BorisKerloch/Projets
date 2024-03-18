import tkinter
import modele

class VueSame:
    """ création de la vue du Same """
    
    def __init__ (self, modele):
        """ construit la classe de la vue du same """
        self.__same = modele
        self.__lig = self.__same.nblig()
        self.__col = self.__same.nbcol()
        
        ###Fenêtre principale
        self.__fenetre = tkinter.Tk()
        self.__fenetre.title("Le jeu du Same")
        
        ###Les images
        self.__images = self.ListeImages()
        self.__images_noires = self.ListeImagesNoires()
        
        ###Fenêtre de gauche
        fenetre_gauche = tkinter.Frame(self.__fenetre)
        
        self.__liste_bouton = []
        for l in range(self.__lig):
            self.__liste_bouton.append([])
            for c in range(self.__col):
                bouton = tkinter.Button(fenetre_gauche, image=self.__images[self.__same.couleur(l, c)] ,command = self.creer_controleur_btn(l,c))
                bouton.bind("<Motion>", self.creer_controleur_btn_noirs(l, c))
                bouton.bind("<Leave>", self.creer_controleur_btn_normal(l, c))
                bouton.grid(row=l, column=c)
                self.__liste_bouton[l].append(bouton)
        fenetre_gauche.pack(side="left")
        
        ###Fenêtre de droite
        fenetre_droite = tkinter.Frame(self.__fenetre)


        trait0 = tkinter.Label(fenetre_droite,
                               text="---------------------------------")
        trait0.pack()

        indice = tkinter.Button(fenetre_droite, text="   Indice   ", command=self.indice)
        indice.pack()

        trait1 = tkinter.Label(fenetre_droite,
                               text="---------------------------------")
        trait1.pack()

        self.__lbl_message = tkinter.Label(fenetre_droite, 
                           text="    Score : " + str(self.__same.score()) + "    ")
        self.__lbl_message.pack()
        self.__next_score = tkinter.Label(fenetre_droite,
                                                   text="Score du prochain coup : ")
        self.__next_score.pack()

        trait2 = tkinter.Label(fenetre_droite,
                               text="---------------------------------")
        trait2.pack()

        btn_nouveau = tkinter.Button(fenetre_droite, text = "   Nouvelle partie   ", command = self.nouvelle_partie)
        btn_nouveau.pack()

        trait3 = tkinter.Label(fenetre_droite,
                               text="---------------------------------")
        trait3.pack()

        btn_quitter = tkinter.Button(fenetre_droite, 
                            text="   Quitter   ",
                            command = self.__fenetre.destroy) 
        btn_quitter.pack()

        trait4 = tkinter.Label(fenetre_droite,
                               text="---------------------------------")
        trait4.pack()

        fenetre_droite.pack(side="right")
        
        self.__fenetre.mainloop()


    ###Pour associer des indices aux images normales
    def indice_same (self, l, c):
        """ VueSame, int, int -> none
        créé un indice à une case du Same pour l'associer à une prochaine fonction à une image
        """
        indice = 0
        if self.__same.couleur(l, c) == -1:
            indice = 9
        elif self.__same.couleur(l,c) >= 0 and self.__same.couleur(l,c) < 9:
            indice = self.__same.couleur(l,c)
        return indice
    
    def cree_img (self, l, c):
        """ VueSame, int, int -> int
        associe à une case l'image adéquat en fonction de l'indice
        """
        indice = self.indice_same(l, c)
        return self.__images[indice]
    
    ###Initlie la liste des images normales
    def ListeImages(self):
        """ VueSame -> list
        crée une liste des images du démineur
        """
        image = []
        for i in range(1, 10):
            img = tkinter.PhotoImage(file="./img/image_" + str(i) + ".gif")
            image.append(img)
        image.append(tkinter.PhotoImage(file="./img/vide.gif"))
        return image

    ###Pour associer des indices aux images noires
    def indice_same_noire(self, l, c):
        """ VueSame, int, int -> none
        créé un indice à une case du Same pour l'associer à une prochaine fonction à une image
        """
        indice = 0
        if self.__same.couleur(l, c) >= 0 and self.__same.couleur(l, c) < 9:
            indice = self.__same.couleur(l, c)
        return indice

    def cree_img_noirs(self, l, c):
        """ VueSame, int, int -> int
        associe à une case l'image adéquat en fonction de l'indice
        """
        indice = self.indice_same_noire(l, c)
        return self.__images_noires[indice]

    ###Initlie la liste des images noires
    def ListeImagesNoires(self):
        """ VueSame -> list
        crée une liste des images du démineur
        """
        image = []
        for i in range (1, 10):
            img= tkinter.PhotoImage(file="./img/fond_image_" + str(i) +".gif")
            image.append(img)
        return image

    def redessine (self):
        """ VueSame -> none
        redessine une vue du Same
        """
        for l in range(self.__lig):
            for c in range(self.__col):
                self.__liste_bouton[l][c]["image"] = self.cree_img(l,c)
        self.__lbl_message["text"] = "    Score : " + str(self.__same.score()) + "    "
            
    def nouvelle_partie (self):
        """ VueSame -> list
        demande au modèle de se réinitialiser puis demande à la vue de se remettre
        complètement à jour
        """
        self.__same.nouvelle_partie()
        self.redessine()
        
    def creer_controleur_btn (self, i, j):
        """ VueSame, int, int -> method
        retourne une fonction qui demande au modèle de supprimer une bille donnée
        """
        def controleur_btn ():
            """ VueSame -> str
            redessine le modèle du same en ayant supprimé la bille selectionnée
            """
            if self.__same.supprime_composantes(self.__same.compo(i,j)):
                self.redessine()
        return controleur_btn

    def creer_controleur_btn_noirs(self, i, j):
        """ VueSame, int, int -> method
        retourne une fonction qui demande au modèle de modfier l'image d'une composante
        """
        def controleur_btn_noirs(event):
            """ VueSame -> str
            redessine le modèle du same en ayant modifié l'image du composant
            """
            for y in range(self.__lig):
                for z in range(self.__col):
                    if (self.__same.compo(y, z) == self.__same.compo(i, j)) and self.__same.couleur(y,z) != -1 and self.__same.compo(i,j) >=2:
                        self.__liste_bouton[y][z]["image"] = self.__images_noires[self.__same.couleur(y, z)]
                        self.__scorenext = (self.__same.nb_elts_compo()[self.__same.compo(y,z)] - 2) ** 2
                        self.__next_score.config(text="Score du prochain coup : " + str(self.__scorenext))
        return controleur_btn_noirs

    def creer_controleur_btn_normal (self, i, j):
        """ VueSame, int, int -> method
        retourne une fonction qui demande au modèle de remettre la couleur initiale des billes lorsqu'elles ne sont plus survolées
        """
        def controleur_btn_normal (event):
            """ VueSame -> str
            redessine le modèle du same en ayant remit la couleur initiale des billes qui ne sont plus survolées
            """
            if "<Leave>":
                self.redessine()
                self.__scorenext = 0
                self.__next_score.config(text="Score du prochain coup : " + str(self.__scorenext))
        return controleur_btn_normal

    ###Méthode pour le bouton indice
    def indice (self):
        """VueSame -> int
        montre sur le plateau de jeu la composante de bille qui rapporte le plus de point
        """
        for i in range(self.__lig):
            for j in range(self.__col):
                if self.__same.plus_grande_compo() == self.__same.compo(i,j):
                    self.__liste_bouton[i][j]["image"] = self.__images_noires[self.__same.couleur(i, j)]


if __name__ == "__main__":
    same = modele.ModeleSame()
    vue = VueSame(same)