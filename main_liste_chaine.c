#include <stdio.h>
#include <stdlib.h>
#include "liste_chaine.h"
#include <string.h>

int main ( int argc, char *argv[] )
{	

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
	debut = initialisation("adresse_1","pseudo_1");
	exemple = debut->premier;
	insertion(debut, "adresse_2", "pseudo_2");
	insertion(debut, "adresse_3", "pseudo_3");
	insertion(debut, "adresse_4", "pseudo_4");
	//exemple = debut->premier;
	//printf("%s\n%s\n%d\n%d\n",exemple->adresse_mac,exemple->pseudo,exemple->score,exemple->booleen);
	int i = 0;
	//for(i=0; i < 3; i++)
	//{
	//	exemple = exemple->suivant;
	//	printf("%s\n%s\n%d\n%d\n",exemple->adresse_mac,exemple->pseudo,exemple->score,exemple->booleen);
	//}
	//int comp = parcours(debut,"adresse_2");
	//printf("%d\n", comp);
	//modification(debut,5,12,parcours(debut,"adresse_2"));
	//int i = 0;
	//exemple = debut->premier;
	//for(i=0; i <parcours(debut,"adresse_2");i++)
	//{
	//	exemple = exemple->suivant;
	//}
	//printf("%s\n%s\n%d\n%d\n",exemple->adresse_mac,exemple->pseudo,exemple->score,exemple->booleen);
	exemple=debut->premier;
	for(i=0; i < 4; i++)
	{
		printf("%s\n%s\n%d\n%d\n",exemple->adresse_mac,exemple->pseudo,exemple->score,exemple->booleen);
		exemple = exemple->suivant;
	}
	suppression(debut,parcours(debut, "adresse_3"));
	exemple=debut->premier;
	for(i=0; i < 4; i++)
	{
		printf("%s\n%s\n%d\n%d\n",exemple->adresse_mac,exemple->pseudo,exemple->score,exemple->booleen);
		exemple = exemple->suivant;
	}
		

	return 0;
}