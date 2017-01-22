#ifndef __SERVEUR_H__
#define __SERVEUR_H__

#define LONGUEUR_TAMPON 4096

typedef struct {
	/* le socket de service */
	int socketService;
	/* le tampon de reception */
	char tamponClient[LONGUEUR_TAMPON];
	int debutTampon;
	int finTampon;
} Client;

/* Initialisation.
 * Creation du serveur.
 * renvoie 1 si a c'est bien passŽ 0 sinon
 */
int Initialisation();

/* Initialisation.
 * Creation du serveur en prŽcisant le service ou numŽro de port.
 * renvoie 1 si a c'est bien passŽ 0 sinon
 */
int InitialisationAvecService(char *service);


/* Attends qu'un client se connecte.
 * renvoie 1 si a c'est bien passŽ 0 sinon
 */
Client * AttenteClient();

/* Recoit un message envoye par le client.
 * retourne le message ou NULL en cas d'erreur.
 * Note : il faut liberer la memoire apres traitement.
 */
char *Reception();

/* Envoie un message au client.
 * Attention, le message doit etre termine par \n
 * renvoie 1 si a c'est bien passŽ 0 sinon
 */
int Emission(char *message);


/* Ferme la connexion avec le client.
 */
void TerminaisonClient();

/* Arrete le serveur.
 */
void Terminaison();

#endif
