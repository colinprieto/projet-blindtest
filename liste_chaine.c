#include <stdio.h>
#include <stdlib.h>
#include "liste_chaine.h"
#include <string.h>

Debut *initialisation(const char adresse[20],const char pseudo[20])
{
	Debut *debut = malloc(sizeof(*debut));
	Profil *profil = malloc(sizeof(*profil));
	if(profil == NULL || debut == NULL)
	{
		exit(EXIT_FAILURE);
	}
	strcpy(profil->pseudo,pseudo);
	//printf("%s\n",profil->pseudo );
	strcpy(profil->adresse_mac,adresse);
	//printf("%s\n", profil->adresse_mac);
	profil->score = 0;
	profil->booleen = 0;
	profil->suivant = NULL;
	debut->premier = profil;
	return debut;
}

int insertion(Debut *debut, const char adresse[20], const char pseudo[20])
{
	Profil *nouveau = malloc(sizeof(*nouveau));
	if(debut == NULL || nouveau == NULL)
	{
		exit(EXIT_FAILURE);
	}
	strcpy(nouveau->pseudo,pseudo);
	strcpy(nouveau->adresse_mac,adresse);
	nouveau->suivant = debut->premier;
	debut->premier = nouveau;

	return 1;
}

int parcours(Debut *debut, const char adresse)
{
	int i = 0;
	int comparaison = 1;
	int terminer = 0;
	Profil *recherche = malloc(sizeof(*recherche));
	if(recherche == NULL || debut == NULL)
	{
		exit(EXIT_FAILURE);
	}
	recherche = debut->premier;
	while (recherche != NULL||terminer != 1)
	{	
		comparaison = strcmp(recherche->adresse_mac, adresse);
		if (comparaison = 0)
		{
			terminer = 1;
		}
		else
		{
			i ++;
			recherche = recherche->suivant;
		}

	}
	if(recherche == NULL)
	{
		i = -1;
	}
	recherche = NULL;
	free(recherche);
	return i;
} 

int modification(Debut *debut, const char adresse, const int score, const int booleen)
{
	int i = 0;
	int comparaison = 1;
	int terminer = 0;
	Profil *recherche = malloc(sizeof(*recherche));
	if(recherche == NULL || debut == NULL)
	{
		exit(EXIT_FAILURE);
	}
	while (recherche != NULL || terminer != 1)
	{
		comparaison = strcmp(*recherche->adresse_mac, adresse);
		if(comparaison =0)
		{
			terminer = 1;
			recherche->score = score;
			recherche->booleen = booleen;
			i = 1;

		}
		else
		{
			recherche = recherche->suivant;
		}
	}
	recherche = NULL;
	free(recherche);
	return i;
}

int suppression(Debut *debut, const char adresse)
{	
	int i = 0;
	int comparaison = 1;
	int terminer = 0;
	Profil *recherche = malloc(sizeof(*recherche));
	if(recherche == NULL || debut == NULL)
	{
		exit(EXIT_FAILURE);
	}
	recherche = debut->premier;
	while (recherche != NULL||terminer != 1)
	{	
		comparaison = strcmp(*recherche->adresse_mac, adresse);
		if (comparaison = 0)
		{
			terminer = 1;
		}
		else
		{
			i ++;
			recherche = recherche->suivant;
		}

	}

	free(recherche);
	return i;
}