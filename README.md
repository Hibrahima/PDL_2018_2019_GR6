# PDL_2018_2019_GR6
A Java project aiming to extract wikipedia matrices from html and wiki text and producing csv-based file.

Objectif
  Ce projet a pour but d’extraire des tableaux pertinents sous formes de CSV à partir d’une URL  Wikipédia donnée. Ce qui permettra au client d'avoir des fichiers cohérents, homogènes et faciles à manipuler.
Licence 
  Logiciel open source
Les technologies utilisées 
 Pour la mise en place de ce projet, nous avons utilisés des librairies et le langage de développement orienté objet Java.
  •joup pour l’extraction du code HTML et la conversion des tableaux,
  •Mylyn a pour l’extraction du code wiki puis le parsing du WikiTexte en HTML.
 
Résultat : On aura en sortie, les différents tableaux dans un répertoire Output.
Ce répertoire contient des tableaux émanant  de deux sources différentes :
  •Tableaux extraits à partir de html par la classe htmlconvertor  
  •Tableaux extraits à partir de wiki par la classe wikiconvertor

L'architecture du projet 
 Dans ce projet, l’architecture mise en place est celle 2-tiers ou « client-serveur » dans le sens où  l’application demande un service au serveur mise en place par mediawiki, le serveur reçoit cette requête , il effectue un traitement de la page, et renvoie la ressource demandée qui est la dernière révision de la page Wikipédia.

Comment exécuter l’application ? 
 Pour exécuter les différents cas de test, on se place à la racine du projet, vous avez les commandes maven suivantes: 
 
 #La branche HTML
 #pour exécuter le cas de test de la conversion en html
   mvn –Dtest=HTMLConverterTest test 
 #pour exécuter le cas de test d'extraction en html
   mvn –Dtest=HTMLExtractorTest   test

 #La branche wikiText
 #pour exécuter le cas de test de la conversion en wiki
   mvn –Dtest=WikiConverterTest  test
 #pour exécuter le cas de test d'extraction en wiki
   mvn –Dtest=WikiExtractorTest   test
 #pour exécuter tout le projet
   mvn test


