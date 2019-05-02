package model;

/*******************************************************************
 * @Titre_Classe: DETAILS IMAGE
 * 
 * @Resumer: 	Recoit et affiche dans la console les details de 
 * 				l'image bitmap (voir classe LectureBytesImage).
 * 				L'information ci-dessous provient de l'entête de
 * 				l'image BMP importée par l'utilisateur.
 * 
 * @Auteur: 	Alexandre Laroche
 * 
 *******************************************************************/
public class DisplayImageDetails{

	/***************************
	 * CONSTRUCTEUR
	 ***************************/
	public DisplayImageDetails(int numImage, String typeImage, int taille, int adresseDonnee, int tailleEntete, 
			int hauteur, int largeur, int planes, int bitsPixel, int compresser, int tailleImage, 
			int couleursUtilisees, int couleursImportantes, int bleu, int vert, int rouge) {

		System.out.println("\n********************************");
		System.out.println("*      Details de l'image      *");
		System.out.println("********************************\n");

		System.out.println("Type de fichier:       " + typeImage + " (" + numImage + ")");
		System.out.println("Taille du fichier:     " + taille + " octets");
		System.out.println("Adresse de depart:     " + adresseDonnee);
		System.out.println("Taille entete:         " + tailleEntete + " octets");
		System.out.println("Dimensions image:      " + hauteur + " x " + largeur);
		System.out.println("Planes:                " + planes);
		System.out.println("Nombre de BPP:         " + bitsPixel + " bits/pixel");
		System.out.println("Type de compression:   " + compresser);
		System.out.println("Taille de l'image:     " + tailleImage + " octets");
		System.out.println("\nNombre de couleurs:    " + couleursUtilisees);
		System.out.println("Couleurs importantes:  " + couleursImportantes + " (tous importantes)");
		System.out.println("Couleur moyenne:       B: " + bleu + " | V: " + vert + " | R: " + rouge);
	}
}
