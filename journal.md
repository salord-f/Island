# Journal Équipe ISG


## État courant du projet

  - Ticketing: _[3]_
  - Versioning: _[3]_
  - Qualité Objet: _[4]_
  - Tests: _[4]_
  - Confiance dans le code: _[5]_

## Semainier

### Semaine 9

- Points Positifs
	- Ticketing: des tâches attribuées à chacun des membres.
	- Versioning: releases chaque semaine avec de nouvelles fonctionnalités, correspond à nos attentes.
	- Qualité Objet: globalement un code bien orienté objet, avec de l'héritage afin d'éviter la duplication de code, et des enums pour les constantes.
	- Tests: une couverture de tests à environ 85%, ce qui nous donne confiance dans le bon fonctionnement de notre code.
	- Confiance dans le code: nous avons des garde-fous dans le cas où il y aurait un bug quelque part qui nous permet de faire rentrer le drone sain et sauf.

- Points négatifs
	- Ticketing: nos tickets ne sont pas forcément explicites sur les fonctionnalités rajoutées et sont parfois trop techniques.
	- Versioning: release 6 (semaine 9) sortie en retard par manque d'attention (nous avons oublié de release sur Jira).
	- Qualité Objet: notre classe Drone et constituée majoritairement de méthodes private et est donc difficilement testable.
	- Tests: nous avons dû utiliser des whitebox pour tester les différentes parties de l'ia de notre drone.
	- Confiance dans le code: certaines conditions de notre code ne sont pas testées.

---

### Semaine 10 

- Points Positifs
	- Confiance dans le code: bug de threads concurrents corrigé, ce qui rend notre programme bien plus stable.

- Points négatifs
	- Ticketing: nos tickets ne sont pas forcément explicites sur les fonctionnalités rajoutées et sont parfois trop techniques. De plus nous n'avons pas découpés notre IA correctement au niveau du ticketing, il va falloir créer des nouveaux tickets.
	- Versioning: Nous n'avons pas pu sortir la release 7 avec les fonctionnalités que l'on voulait implémenter.
	- Qualité Objet: notre classe Drone et constituée majoritairement de méthodes private et est donc difficilement testable. De même nous prenons la même direction pour l'IA sailor, elle devient de plus en plus difficilement testable et de moins en moins extensible.
	- Tests: nous avons dû utiliser des whitebox pour tester les différentes parties de l'ia de notre drone. Certaines fonctionnalités de notre IA sailor n'est pas encore testé. Notre converture de tests est également en baisse à 70%
	- Confiance dans le code: certaines conditions de notre code ne sont pas testées (cf Tests).

---

### Semaine 11

- Points positifs
	- Validation au minimum d'un objectif sur 9/72 configurations différentes de cartes.
	- Confiance dans le code, sécurités fonctionnelles.

- Points négatifs
	- Ticketing: pas d'amélioration par rapport à la semaine dernière.
	- Qualité Objet: pas d'évolution par rapport à la semaine dernière.
	- Tests: La couverture de tests est toujours en baisse car nous n'avons pas commencé les tests pour nos marins.

---

### Semaine 12

- Points positifs
	- Ticketing: beaucoup de tâches rajoutées pour les prochaines releases. Bonne vision de la suite du projet.
	- Les sécurités nous empêchent de crasher et nous rendent confiants.
	- L'intelligence de nos marins est de plus en plus fonctionnelle.
	
- Points négatifs
    - Beaucoup de travail ces dernière semaines, le projet tourne au ralenti.
    - On commence a accumuler les dettes, surtout au niveau de l'intelligence des marins.
    - Tests : notre couverture de tests diminue chaque semaine à cause de notre classe de choix d'actions pour les marins.

---

### Semaine 13

- Points positifs
	- 	SmartSailors, notre IA intelligente est presque terminée et devrait être opérationnelle pour la semaine prochaine.

- Points négatifs
	- Les classes sans tests s'accumulent, ce qui sera corrigé pour la semaine prochaine.
	- De la duplication de code temporaire le temps de la transition du Sailors vers SmartSailors.
	- Beaucoup de méthodes inutiles ou redondantes.

---

### Semaine 14

- Points positifs
	- L'IA smartSailors est opérationnelle. Elle remplace l'IA bête utilisée précedemment.
	- La couverture des tests a été grandement ameliorée. De plus nos tests sont utiles comme le montre l'utilisation de pitest sur notre programme, qui montre un coverage de mutation à 57%.
	- La majeure partie des méthodes inutiles/redondantes a été supprimée.

- Points négatifs
	- Il reste encore des méthodes non testées avant d'avoir une couverture de tests confortable.
 
---

### Semaine 15

- Points positifs
	- Une augmentation constante de tests de plus en plus complets.
	- Focalisation sur la qualité du code, tout en améliorant l'intelligence de nos marins.

- Points négatifs
	- Des tests toujours à améliorer.
	- De nombreux points difficiles à améliorer concernant les marins n'ayant pas pu etre présentés cette release.
	
---

### Semaine 16

- Points positifs
    - Une bonne couverture de tests, beaucoup de tests ont été améliorés.
    - La qualité du code c'est amélioré, notre IA est plus performante.

- Points négatifs
    - Des tâches qui trainent en longueur et qui n'apportent pas de valeur client en continue.
    - Le back log est améliorable, nous ne nous projetons pas assez dans le futur.

---

### Semaine 17 
 
- Points positifs 
	- Une couverture de tests à 87%, et pitest à 78%. Il reste seulement quelques parties du package sailors à tester et quelques conditions dans d'autres packages.
	- Une amélioration considérable dans la gestion du budget avec une limitation de la consommation du drone qui nous permet de remplir plus de contrats.
 
- Points négatifs 
	- Des tâches qui trainent en longueur depuis deux semaines.
	- Le back log est largement améliorable. Les idées pour améliorer notre IA commencent à se faire rares.

---

### Semaine 18
 
- Points positifs 
    - Une couverture de tests satisfaisante, avec une lègère baisse : 85% contre 87% la semaine dernière.
    - Une amélioration sur la gestion du budget du drone. Nous devenons plus précis et nous pouvons stopper le drone encore plus tôt.
    - Les contracts qui demandent plus de budget que disponible pour les remplir sont automatiquement passés en contrat impossible à réaliser.
    - Une équipe de plus en plus motivée par le projet et pour battre la première équipe ISI.
    - Beaucoup de nouvelles idées et de nouvelles tâches sur Jira.
    
 
- Points négatifs 
    - Beaucoup de nouvelles tâches Jira simultanément, l'ajout n'est pas du tout régulier.
    - Une difficulté croissante dans l'ajout de valeur au produit pour le client, car les améliorations sont de moins en moins visibles.
    - Le back log est encore améliorable.

---

### Semaine 19
 
- Points positifs 
    - Nous avons encore augmenté la couverture de test jusqu'à dépasser les 90%.
    - Le budget du drone a encore été optimisé.
    - Notre backlog est bien garni et prêt pour la passation. Nous avons rajouté de la javadoc dans toutes les classes qui en ont besoin.
    
 
- Points négatifs 
    - L'implémentation de la carte est à changer afin d'améliorer les performances (on parcourt toutes les cellules à chaque fois que l'on veut récupérer une cellule).                         
    - A l'heure actuelle les contrats de poissons ne sont pas optimisés.
    
---