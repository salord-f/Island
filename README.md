## Island groupe ISG

###Bienvenue sur le projet de QGL de l'équipe G !

##### Sujet
Ce projet a pour but d'explorer et exploiter des îles.  
L'exploration commence par un survol en drone, puis l'exploitation se fait en envoyant des marins sur île.

Le sujet est disponible à cette [adresse](https://ace-design.github.io/island/).

Le projet est constitué d'un moteur de jeu en Scala qui nous était fourni, et des sources en Java qui permettent d'intéragir avec le moteur du jeu.  
Le projet s'est déroulé selon une compétition hebdomadaire opposant les différents groupes en fonction de leur capacité à remplir des contrats en dépensant le moins de budget possible.

Un journal a été tenu tout au long du projet avec de tenir compte de l'avancée du projet. Voir *journal.md*.

##### Exécution

Les cartes sont nommées selon la convention "map_XX" avec *XX* le numéro de la semaine à laquelle la carte a été publiée.

Pour lancer l'éxecution du Runner sur la carte *map_03* :

```
mvn clean package
mvn -q exec:java -Djava.awt.headless=true -Dexec.args="scripts/maps/map_03.json"
```
Le résultat de l'exploration peut-être vu grâce au fichier *Explorer.svg* créé pour la dernière carte à la racine du projet.

##### Scripts shell
Le dossier *scripts* contient l'intégralité des scripts permettant d'évaluer la performance du projet sur les cartes existantes.

Pour lancer le script principal :

- Sur chaque carte selon les conditions du championnat :

```
bash scripts/map_tests.sh l
```
- Selon toutes les positions possibles sur chaque carte :

```
bash scripts/map_tests.sh f
```

- Selon chaque ressource sur chaque carte :

```
bash scripts/map_tests.sh r
```

Une fois l'exécution du script fini, un fichier "results.log" est disponible dans le dossier *scripts* affichant un résumé de l'exploration et l'exploitation de chaque carte.  
Un dossier *results* est également généré avec deux sous-dossiers contenant le log complet de l'exécution pour *full_logs* et un résumé plus précis que "results.log" pour chaque carte.