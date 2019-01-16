# PDL_2018_2019_GR6
A Java project aiming to extract wikipedia matrices from html and wiki text and producing csv-based file.

# Objectif
  Ce projet a pour but d’extraire des tableaux pertinents sous formes de CSV à partir d’une URL  Wikipédia donnée. Ce qui permettra d'avoir des fichiers cohérents, homogènes et faciles à manipuler.

# Licence 
  Logiciel open source

# Les technologies utilisées 
 Pour la mise en place de ce projet, nous avons utilisés des librairies et le langage de développement orienté objet Java.
  •Jsoup pour l’extraction du code HTML et la conversion des tableaux,
  •Mylyn pour le parsing du WikiTexte en HTML.
 
# Résultat
On aura en sortie, les différents tableaux dans un répertoire output.
Ce répertoire contient des tableaux émanant  de deux sources différentes :
  •Tableaux extraits à partir du code html et seront enrégistrés dans le dossier output/html  
  •Tableaux extraits à partir du code wikitext et seront enrégistrés dans le dossier output/wiki

# L'architecture du projet 
 Dans ce projet, l’architecture mise en place est celle du 2-tiers ou « client-serveur » dans le sens où  l’application demande un service au serveur mis en place par mediawiki, le serveur reçoit cette requête , il effectue un traitement de la page, et renvoie la ressource demandée qui est la dernière révision de la page Wikipédia concernée.

# Comment exécuter l’application ? 
 Pour exécuter les différents cas de test, on se place à la racine du projet, vous avez les commandes maven suivantes: 
 
 # HTML
 Pour exécuter le cas de test de la conversion en html </br>
   mvn -Dtest=HTMLConverterTest test </br>
 Pour exécuter le cas de test d'extraction en html </br>
   mvn -Dtest=HTMLExtractorTest test </br>

 # WikiText
 Pour exécuter le cas de test de la conversion en wiki </br>
   mvn -Dtest=WikiConverterTest test </br>
 Pour exécuter le cas de test d'extraction en wiki </br>
   mvn -Dtest=WikiExtractorTest test </br>
 

Pour exécuter tous les cas de test </br>
   mvn test

Assurez-vous d'avoir au moins maven 3.8.0 installé avant de lancer les tests ou configurez la version du compilateur maven dans le fichier pom.xml

La Javadoc du projet se trouve dans le dossier documentation. Pour le parcourir, ouvrez juste le fichier index.html dans un navigateur.

Feel free to make suggestions.

# Lien de la vidéo de demonstration
https://drive.google.com/open?id=1SEWEGzS-YYkW1Ia2dG_sq7J0WN_6Gn5X

# Authors:
	Ibrahima HAIDARA
	Mariam COULIBALY
	Mahamadou SYLLA
	Abdoul Hamide BA


