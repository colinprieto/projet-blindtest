// Noeud de la liste chainée 
typedef struct Profil Profil;
struct Profil
 {
 	char adresse_mac[20];
 	char pseudo[20];
 	int score;
 	int booleen;
 	Profil *suivant;

 }; 
 // Pointeur sur le premier noeud de la liste chainée
 typedef struct Debut Debut;
 struct Debut
 {
 	Profil *premier;
 };
 // Fonction d'initialisation de la liste
 Debut *initialisation(const char adresse[20],const char pseudo[20]);
 // Fonction d'ajout d'un nouveau element dans la liste
 int insertion(Debut *debut, const char adresse[20], const char pseudo[20]);
 // Fonction de parcour de la liste chainée à la recherche d'un élément
 // renvoi -1 en cas d'échec sinon renvoi la position de l'éléments recherché
 int parcours(Debut *debut, const char adresse);
 // Fonction de modification de la liste chainée basé sur la recherche d'une adresse mac
 // renvoi 1 si la modification à lieu
 int modification(Debut *debut, const char adresse, const int score, const int booleen);
 // Fonction de suppression d'un element de la liste renvoi 1 si la suppression 
 // à lieu
 int suppression(Debut *debut, const char adresse);
 