# Exercise calculator

### What transport protocol do we use ?

Nous allons utilisé le protocole TCP car pour chaque commande que nous allons envoyé au serveur nous devons recevoir la réponse au client du résultat

### How does the client find the server (addresses and ports) ?

Le port part defaut sera le port 1515. L'adresse IP sera celle du serveur

### Who speak first ?

Le serveur va parler en premier lorsque l'on va se connecter dessus. Il va nous envoyé les différent commande que l'on peut faire

### What is the sequence of messages exchanged by the client and the server

|          Serveur          |      |       Client       |
| :-----------------------: | :--: | :----------------: |
|      Initialisation       |  ->  | Liste des commande |
|          calcule          |  <-  |      calcule       |
|         resultat          |  ->  |      resultat      |
| Fermeture de la connexion |  <-  |        QUIT        |

### What happens when a message is received from the other party ?

Lorsque le client se connecte sur le serveur il recevera comme premier message la liste de tout les commande qu'il peut faire. Lorsque le client fait une commande, par exemple ADD, le serveur lui renvera le resultat et pourra par la suite continuer a exécuter des commandes si il le souhaite.

### What is the syntax of the messages ? How we generate and parse them ?

Voici les différente commande que le client peux envoyer :

| **Command**   | What does the cmd               | Response                    |
| ------------- | ------------------------------- | --------------------------- |
| ADD nbr1 nbr2 | res = nbr1 + nbr2               | {"resultat" : monresultat}  |
| SUB nbr1 nbr2 | res = nbr1 - nbr2               | {"resultat" : monresultat}  |
| DIV nbr1 nbr2 | res = nbr1 / nbr2               | {"resultat" : monresultat}  |
| MUL nbr1 nbr2 | res = nbr1 * nbr2               | {"resultat" : monresultat}  |
| HELP          | return the list of command      | Command: [HELP,ADD,SUB,MUL] |
| QUIT          | The server close the connection | No response                 |

Le client enverra un simple message texte mais le serveur lui renvera le resultat de la command sous la form d'un jso

###  Who closes the connection and when?

On ferme la connection lorsque le client envoie la commande QUIT.