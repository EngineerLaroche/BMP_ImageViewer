# BMP_ImageViewer

Le projet consiste à créer une interface graphique permettant d’afficher des images. L’application, écrite en Java a été développée de façon à ce qu’il soit possible d’ajouter du support pour d’autres formats d’images. La version actuele supporte seulement des images de format Bitmap (BMP) non-compressé de 24 bits par pixels. 

## Aperçu

Lorsqu'un utilisateur importe une image de format Bitmap à partir de l'interface graphique de l'application, l'information retrouvée dans l'entête de l'image sera affichée dans la console du IDE (Java). Ensuite, l'application va reconstruction l'image à partir de l'information récupérée de l'entête et afficher (dessiner) l'image sur un panneau (onglet).

## Démarrer l'application

Le démarrage de l'application se fait à partir de la classe "Application" dans le package "View". L'information complète de l'image Bitmap importée sera affichée dans la console de votre IDE (Java). Dans le dossier "Bitmap Example", vous allez retrouver une image de format Bitmap comme exemple que vous pourrez importer à partir de l'interface utilisateur afin de rapidement tester l'application.

## Entête d'une image Bitmap

http://atlc.sourceforge.net/bmp.html

## Auteur

- Nom:   Alexandre Laroche
- Date:  Automne 2017