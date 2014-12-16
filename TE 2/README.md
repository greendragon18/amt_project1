#TEST 2 AMT

##Big Web & REST Services 
*Etre capable d'expliquer les avantages et inconvénients respectifs*  
http://fr.wikipedia.org/wiki/Service_web

Les **Services web** sont des applications web permettant la communication et l'échange de données entre applications et systèmes hétérogènes dans des environnements distribués en utilisant des RPC (Remote Procedure Call).

Les **Big web services** utilisent les standards SOAP & WSDL afin de définir, d'implémenter et d'exposer des fonctionnalités sous la forme de services exécutables à distance. (Voir SOAP & WSDL)

Les **Services REST** exposent entièrement ces fonctionnalités comme un ensemble de ressources (URI) identifiables et accessibles par la syntaxe et la sémantique du protocole HTTP. Les Services Web de type REST sont donc basés sur l'architecture du web et ses standards de base : HTTP et URI 

##SOAP & WSDL
###SOAP
**SOAP** (**S**imple **O**bject **A**ccess **P**rotocol) est un protocole de RPC orienté objet bâti sur XML.  

Le protocole SOAP est composé de deux parties :

- Une enveloppe, contenant des informations sur le message lui-même afin de permettre son acheminement et son traitement.  
- Un modèle de données, définissant le format du message, c'est-à-dire les informations à transmettre.

####Avantages:  

- Assez ouvert pour s'adapter à différents protocoles de transport.  
- Indépendant de la plate-forme.  
- Indépendant du langage.  
- Extensible.  

####Inconvénients:  

- Complexe, Verbeux
- Problèmes d'incompatibilité (fort couplage client-serveur)  
- Peu compréhensible (messages réseau)


###WSDL
**WSDL** (**W**eb **S**ervices **D**escription **L**anguage) est une grammaire XML permettant de décrire un service web (comment communiquer pour utiliser le service). 
 
Le WSDL sert à décrire :

- Le protocole de communication (SOAP RPC ou SOAP orienté message)  
- Le format de messages requis pour communiquer avec ce service.  
- Les méthodes que le client peut invoquer.  
- La localisation du service.  

##REST
**REST** (**RE**presentational **S**tate **T**ransfer) est un style d’architecture pour les systèmes hypermédia (extension de l'hypertexte à des données multimédias) distribués. Ce n’est pas un protocole (tel que HTTP) ou un format mais un style architectural.  

Contraintes d'architecture :

- Client-serveur : les responsabilités sont séparées entre le client et le serveur.  
- Sans état : auncune notion de session sur le serveur.  
- Mise en cache : le serveur peux indiquer une "durée de vie" aux données envoyées.  
- Une interface uniforme :  
  - L'identification des ressources : chaque ressource est identifiée unitairement.
  - Les ressources ont des représentations définies (uniques).  
  - Un message auto-descriptif : les messages expliquent leur nature (ex. UTF-8)  
  - Contient les informations nécessaires pour accéder aux ressources suivantes.  
- Système hiérarchisé par couche : états identifiés par des ressources individuelles (commandes/:id/produits).
- Code-On-Demand (facultatif) : la possibilité pour les clients d’exécuter des scripts obtenus depuis le serveur.

####Avantages:  

- L’application est plus simple à entretenir, car elle désolidarise la partie client de la partie serveur (on peut accéder aux états suivants de l'application par inspection de la ressource courante).  
- Indépendance entre le client et le serveur, pas de sessions donc pas de connexions permanentes client-serveur.  
- Pas de session = répartition des requêtes sur plusieurs serveurs, meilleure évolutivité et tolérance aux pannes.  
- Dans un contexte Web :  
  - Utilisation du protocole HTTP en tirant parti de son enveloppe et ses en-têtes.  
  - Utilisation d’URI comme représentant d’une ressource = système universel d'identification des éléments.  
  - La nature auto-descriptive du message permet la mise en place de serveurs cache. Les informations nécessaires sur la fraîcheur, la péremption de la ressource sont contenues dans le message lui-même

####Inconvénients:  
Le principal inconvénient de REST est la nécessité pour le client de conserver localement toutes les données nécessaires au bon déroulement d’une requête.  

- Consommation en bande passante réseau plus grande (mobiles / énergie).  
- La latence de la connexion rend également l'interaction moins fluide.

##@WebService 
Lors de l'utilisation de ce tag, nous demandons à Java EE de créer un point d'accès de type web service (SOAP & WSDL) sur le stateless session beans en question.  
Ce faisant, nous utilisons le mécanisme d'inversion de contrôle qui consiste à déléguer au framework la charge de créer cet accès et de le gérer.  
À la manière d'une factory, on donne uniquement le contexte (ex. URL) au framework et il se charge d'instancier (et de conserver) les classes nécessaires à cette fonctionnalité (contrairement à une servlet qui elle attend sur nous pour générer le contenu de la vue).  

***Ex.*** Un @EJB est également une forme particulière d'inversion de contrôle: l'injection de dépendance.

###Stub & Skeleton
Le **skeleton** est le bout serveur d'une relation. Il définit toute l'interface mise à disposition par l'application de web services et peut être défini par un fichier WSDL ou par un language comme RAML (REST). Ce squelette expose donc les ressources mise à disposition des clients, il récupère les informations sous leur forme de communication (JSON / XML) et les transforment invocations réelles pour l'application serveur.  

Le **stub** est le bout client d'une relation. Il est en soit identique au skeletton pour le client, il va récupérer les données de la requête du client et les envoyer au serveur sous leur format de communication.  
Logiquement, le **stub** n'implémente pas forcément toutes les méthodes du skeleton mais seule celle qu'il référence seront disponible du point de vue du client.

##Data Transfer Object (DTO)
Avantage d'introduire ce type de composant
Etre capable d'illustrer le principe de DTO et l'avantage avec un exemple concret et détaillé.

##Création API REST
Etre capable de décrire les URLs, les méthodes HTTP, les payloads et de décrire la sémantique associée.

##JPA
Implémenter la persistence dans une application multi-tiers (bien décrire toutes les étapes, avec la phase de définition du modèle et la phase d'utilisation du service de persistence, cf. slides).

##Différence entre le "eager loading" et le "lazy loading"
Etre capable de l'expliquer avec un exemple (inspiré du projet).

##Maven

##Injection de dépendance JPA
```
@EJB
private UserLocal user_id;
```