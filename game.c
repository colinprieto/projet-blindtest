#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
#include <sys/types.h>
#include <time.h>


#include <unistd.h>
#include <netdb.h>
#include <sys/socket.h>
#include <sys/select.h>
#include <strings.h>

#include <errno.h>
#include "liste_chaine.h"
#include "serveur.h"

#define TRUE 1
#define FALSE 0
/* 
 * Types de requêtes
 */

#define REQ_INCONNUE	0
#define CREATE 		1
#define EFFACE 		2
#define RECHERCHE	7
void decoupeEtExecute( char *requete, Client *cl) {
	char *reponse;
	int type;
	char *parametres;
	int lgParam;

	/* compare le debut de la requête avec les types connus */
	if(strncmp(requete, "CREATE", 6)==0) {
		type = CREATE;
	} else if(strncmp(requete, "JOIN", 4)==0) {
		type = EFFACE;
	} else {
		/* type inconnu */
		type = REQ_INCONNUE;
	}
	/* recupere les paramètres */
	parametres = strstr(requete, " ");
	if(parametres != NULL) {
		/* saute l'espace */
		parametres++;
		/* enlève \r et \n à la fin de parametres */
		lgParam = strlen(parametres);
		if(parametres[lgParam-2] == '\r') {
			parametres[lgParam-2] = '\0';
		}
		if(parametres[lgParam-1] == '\n') {
			parametres[lgParam-1] = '\0';
		}
	}
	/* execute la requête */
	reponse = executeRequete(type, parametres);
	/* envoie la réponse */
	printf("Ma réponse : %s\n", reponse);
	Emission(reponse, cl);
	/* libère la mémoire si nécessaire */
	if(type == RECHERCHE) {
		free(reponse);
	}
}
char *executeRequete(int type, char *parametres) {
	char *reponse;
	switch(type) {
		case CREATE:
			reponse = executeCreate(parametres);
			break;
		case JOIN:
			reponse = executeJoin(parametres);
			break;
		default:
			reponse = "ERREUR requete inconnue\n";
			break;
	}
	return reponse;
}

char *executeCreate(char *parametres) {

srand(time(NULL));
int r = rand();
int idpartie;
char mac_address[50];
char pseudo[50];

int ret;

	/* decoupe les parametres */
	ret = sscanf(parametres, "%[^|]|%[^|]|%[^|]", 
			idpartie, mac_address, pseudo);

	Debut *debut = malloc(sizeof(*debut));
	if (debut == NULL)
	{
		exit(EXIT_FAILURE);
	}
	Profil *exemple = malloc(sizeof(*exemple));
	if (exemple == NULL)
	{
		exit(EXIT_FAILURE);
	}

	if(ret != 3) {
		return "ERREUR Requête mal formée\n";
	} else {
		debut = initialisation(mac_address,pseudo);
		exemple = debut->premier;
		return "CREATE OK";
	}
}
char *executeJoin(char *parametres) {

int idpartie;
char mac_address[50];
char pseudo[50];

int ret;

	/* decoupe les parametres */
	ret = sscanf(parametres, "%[^|]|%[^|]|%[^|]", 
			idpartie, mac_address, pseudo);

	Debut *debut = malloc(sizeof(*debut));
	if (debut == NULL)
	{
		exit(EXIT_FAILURE);
	}
	Profil *exemple = malloc(sizeof(*exemple));
	if (exemple == NULL)
	{
		exit(EXIT_FAILURE);
	}

	if(ret != 3) {
		return "ERREUR Requête mal formée\n";
	} else {
		if(parcours(debut,mac_address)== -1)
		{
			return "Deja present";
		}
		else
		{
		debut = insertion(debut,mac_address,pseudo);
		exemple = debut->premier;
		return "Ajouté";
		/* prépare la réponse */
	}
	}
}
/*
void create_thread(arg)
{
		decoupeEtExecute(message, cl);
		TerminaisonClient(cl);
			pthread_exit(NULL);
}
*/
int main()
{
	char *message = NULL;
	Client cl;
	Initialisation();
	while (1)
	{
		cl = AttenteClient();
		message = Reception();
		decoupeEtExecute(message, &cl);
		/*
		if(strncmp(requete, "CREATE", 6)==0) {
			pthread_t thread;
			pthread_create(&thread, NULL, create_thread, cl, message;
	}
	*/
		
		TerminaisonClient(cl);
	}
}