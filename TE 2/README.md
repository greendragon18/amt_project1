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
Un DTO est un POJO. Il permet de filtrer les informations des entités données aux clients. En résumé, un client ne manipule jamais un POJO entité mais un DTO. Il est possible d'avoir plusieurs DTO pour une entitée.  
Par exemple, si l'on prend une entité **employe** qui posède un champ salaire et un champ password, il est possible de créer :  

- Un DTO avec tous les champs d'employe à l'excéption de salaire, l'intention de l'employé en question ainsi qu'aux techniciens de l'entreprise.
- Un DTO avec tous les champs à l'exception du mot de passe, destiné aux RH de l'entreprise.  

Dans les POJOs d'entités, les relations sont reprsentées de telle sorte : entité A possède un ou plusieurs objet(s) B. Charger tout ces éléments a une certaine lourdeur. Le DTO permet de ne pas transférer les sous-objets complètement mais de retourner que les clefs étrangères.  
Par exemple, dans le cadre de notre projet un Sensor appartient à une organisation. Ce qui veut dire que dans notre POJO Sensor il existe un objet Organisation. Grâce au DTO, quand un client demande un Sensor, il ne récupère que l'ID de l'organisation et non l'organisation au complet (si il a besoin de cette information, il pourra refaire une demande sur l'API REST).  

Le DTO permet encore de formater des objets et de les convertir. Dans le cadre de notre projet nous avons utilisé un DTO pour transformer une date en un Long (timestamp) ou en String (date formatée).

##Création API REST
Etre capable de décrire les URLs, les méthodes HTTP, les payloads et de décrire la sémantique associée.

Lors de la conception d'une API REST, il faut réfléchir en terme de **ressources**. Quelles sont les entités (ressources) que l'on veut rendre accessible aux utilisateurs et sous quelles conditions ? Un utilisateur est une ressource, toute personne peut en créer une mais seule celle qui y est associée peut accéder aux informations. Il y a donc un travail de logique à effectuer par rapport aux ressources et à l'action que l'on cherche à effectuer.

###Ressources
Peut être défini à l'aide d'un modèle de domaine. Les ressources de notre application sont les éléments métiers de cette dernière, le résultat de ses fonctionnalités. Ainsi il assez logique d'imaginer qu'une application permettant de concevoir des albums photos disposera d'une ressource "images" et d'une ressource "albums". La liaison entre ces entités est également évidente.  

Afin de disposer de ces informations, REST impose une identification unique de chaque ressource dans un format standart (génlralement une URL), cet ressource particulière peut cependant disposer de plusieurs représentations (XML, JSON). L'interraction doit par contre ce faire à l'aide d'une interface unique (requêtes HTTP).  

####Exemple:

```/api/users/``` : Liste des utilisateurs  
```/api/users/:id/commandes``` : Liste des commandes pour tel utilisateur (*:id* = Identifiant utilisateur)   
```/api/commande/:id/produits?number=20&start=0``` : Les 20 premiers produits de telle commande (*:id* = Identifiant de la commande)

###CRUD
Acronyme des actions de base que l'on peut utiliser sur des ressources:

- **C**reate: Création d'une nouvelle ressource
- **R**ead: Lecture de la(des) ressource(s) existante(s)
- **U**pdate: Mise à jour d'une ressource existante
- **D**elete: Suppression d'une ressource existante

Si ```/api/users/1``` est l'identifiant unique de l'utilisateur à l'id 1, il va falloir trouver un moyen d'indiquer ce que l'on cherche à faire sur cette ressource (lecture, modification ou suppression).  

C'est la que les requêtes HTTP entrent en jeu:  

- **C**reate: POST
- **R**ead: GET / HEAD / OPTIONS
- **U**pdate: PUT / PATCH
- **D**elete: DELETE

Chacune réalisant alors l'opération demandée et/ou générant une réponse à la requête.  
 
Il nous faut encore ajouter à cela une notion d'autorisations, il n'est pas concevable d'offrir la possibilité à n'importe qui d'afficher la liste des utisateurs et encore moins leurs informations, ni de créer des commandes pour un autre utilisateur.  
Ainsi, lors de la création de l'API ces options doivent être spécifiquement indiquées pour toute requête vers une ressource spécifique.

####Exemple
```/api/users/me```: Information sur l'utilisateur actuellement connecté => **Utilisateur courant**.   
```/api/users/:id```: Information sur un utilisateur en particulier => **Admin**.   
```/api/users/:id/commandes```: Information sur les commandes de l'utilisateur actuellement connecté => **Utilisateur courant** & **Admin**.   


##JPA
Implémenter la persistence dans une application multi-tiers (bien décrire toutes les étapes, avec la phase de définition du modèle et la phase d'utilisation du service de persistence, cf. slides).

##Différence entre le "eager loading" et le "lazy loading"
Etre capable de l'expliquer avec un exemple (inspiré du projet).  

Lorsque l'on crée une jointure, un objet **a** peut être lié à n objets **b** (OneToMany). Si l'on récupère l'objet **a**, nous n'avons pas forcément besoin de tout les objets **b** liés (par exemple un utilisateur et sa liste de commandes passées).  

Le **Lazy loading** répondra parfaitement à cette problématique, si une jointure est définie comme ***Lazy***, l'appel d'un objet ne chargera ses objets liés uniquement en cas de demande spécifique (via getObjetsLies() par exemple). Cette option économise donc beaucoup de ressources.  

Le **Eager loading**, à l'inverse, chargera en cascade tout les objets joints (la cascade bien sur ne s'étendra qu'aux sous-jointures également définie comme étant ***Eager***), le chargement sera alors plus lourd mais les objets persistés seront alors tous instanciés. Cela est particulièrement utile si une entité n'a pas de raison d'être sans un élément joint comme par exemple un *type de compte* pour un *compte*.

####Exemple dans le projet:
Observation.java  

```
//Une observation n'a aucun sens si on ne sait pas à quel Sensor elle est liée.
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(nullable = false)
private Sensor sensor;
```

##Maven
**Apache Maven** est un outil pour la gestion et l'automatisation de production des projets logiciels Java en général et Java EE en particulier. Maven permet principalement de gérer les dépendances des projet en téléchargeant du matériel sur des dépôts logiciels connus. Il propose ainsi la synchronisation transparente de modules nécessaires.  
Maven utilise un paradigme connu sous le nom de Project Object Model (POM) afin de décrire un projet logiciel, ses dépendances avec des modules externes et l'ordre à suivre pour sa production.  

####Cycle de vie
Les buts principaux du cycle de vie d'un projet Maven sont:

- compile
- test
- package
- install
- deploy

L'idée est que, pour n'importe quel but, tous les buts en amont doivent être exécutés sauf s'ils ont déjà été exécutés avec succès et qu'aucun changement n'a été fait dans le projet depuis. Par exemple, quand on exécute ```mvn install```, Maven va vérifier que ```mvn package``` s'est terminé avec succès (le jar existe dans target/), auquel cas cela ne sera pas ré-exécuté.

##Injection de dépendance JPA
```
@EJB
private UserLocal user_id;
```