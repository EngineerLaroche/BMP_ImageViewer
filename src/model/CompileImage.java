package model;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**************************************************************
 * @CLASS_TITLE:	Compile Image
 * 
 * @Description: 	Conversion et lecture en bytes du 
 * 					fichier (bitmap) recu. Obtenir le descriptif 
 * 					complet de l'image (bitmap) recu (entete .bmp = 54 bits).
 * 					Reconstruire et afficher l'image correctement 
 * 					sur l'interface utilisateur.
 * 
 * @Source: 		https://alvinalexander.com/blog/post/java/getting-rgb-values-for-each-pixel-in-image-using-java-bufferedi
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class CompileImage extends Image {

	/***************************
	 * Constantes
	 ***************************/
	private static final int 
	RGB = 255,
	BPP_24 = 24,
	NB_COULEURS = 3,
	NUM_BITMAP = 143,
	BITS_PIXEL_8 = 8, 
	BITS_PIXEL_16 = 16, 
	BITS_PIXEL_24 = 24,
	ENTETE_OCTET_40 = 40;

	private static final String
	BIT_MAP = "BM",
	ERROR_BPP = "*** L'image Bitmap sélectionnée n'est pas 24 bits par pixel ***",
	ERROR_BITMAP = "*** L'image sélectionnée n'est pas de format Bitmap ***",
	ERROR_ENTETE = "*** L'entête de l'image sélectionnée ne respecte pas 40 octets ***",
	ERROR_SIZE = "*** L'image sélectionnée possède un problème au niveau du poids ***";

	/***************************
	 * Classe instanciee (image)
	 ***************************/
	private BufferedImage image = null;
	
	/***************************
	 * Variables
	 ***************************/
	private String typeImage = "";
	
	private int 
	numImage = 0,
	taille = 0, 
	adresseDonnee = 0, 
	tailleEntete = 0, 
	hauteur = 0, 
	largeur = 0, 
	planes = 0,
	bitsPixel = 0,
	compresser = 0,
	tailleImage = 0,
	pixelsHorizontale = 0,
	pixelsVerticale = 0,
	colorsQty = 0,
	couleursImportantes = 0,
	bitmap = 0,
	rouge = 0, 
	vert = 0, 
	bleu = 0;
	

	/******************************************************
	 * Constructeur - CompileImage
	 * 
	 * @Resumer:	Appel les fonctions pour connaître les
	 * 				détails de l'image BMP importée.
	 * 
	 * @param 		Fichier image sélectionné
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	public CompileImage(File file) throws IOException {

		FileInputStream fileInput = new FileInputStream(file);
		DataInputStream inputStream = new DataInputStream(fileInput);

		//Segmentation de la lecture des bytes en plusieurs sous-segments.
		fileType(inputStream); 
		fileWeight(inputStream);
		initialDataAddress(inputStream);		
		headerSize(inputStream);
		imageResolution(inputStream);
		bitsPerPixel(inputStream);
		compressType(inputStream);
		compressedFileWeight(inputStream);
		resolution(inputStream);
		colorInfo(inputStream);

		//Construction de l'image BMP
		buildImageBMP(inputStream);

		//Information supplémentaire
		identifyColorsQty();
		averageRGB();

		//Fermeture de la lecture du fichier image
		inputStream.close();

		//Classe qui affiche l'information de l'image dans la console
		new DisplayImageDetails(numImage, typeImage, taille, adresseDonnee, 
				tailleEntete, hauteur, largeur, planes, bitsPixel, compresser, 
				tailleImage, colorsQty, couleursImportantes, bleu, vert, rouge);
	}

	/******************************************************
	 * File Type
	 * (2 bytes)
	 * 
	 * @Resumer:	Fonction qui le type de fichier importé.
	 * 				Techniquement, le fichier image devrait 
	 * 				être de type BMP (Bitmap).
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void fileType(DataInputStream inputStream) throws IOException {

		byte[] bfType = new byte[2];
		inputStream.readFully(bfType);
		numImage = (char) bfType[0] + (char) bfType[1];
		typeImage = "" + (char) bfType[0] + (char) bfType[1];

		//Affiche une erreur si ne respecte pas le type de fichier BitMap
		if(!typeImage.equals(BIT_MAP) || numImage != NUM_BITMAP)
			throw new IOException(ERROR_BITMAP); 	
	}

	/******************************************************
	 * File Weight
	 * (4 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère la taille de 
	 * 				l'image non-compressé en bytes (octets)
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void fileWeight(DataInputStream inputStream) throws IOException {

		byte[] bfSize = new byte[4];
		inputStream.readFully(bfSize);
		taille = (((int) bfSize[3] & RGB) << BITS_PIXEL_24) | 
				(((int) bfSize[2] & RGB) << BITS_PIXEL_16) | 
				(((int) bfSize[1] & RGB) << BITS_PIXEL_8) | 
				(int) bfSize[0] & RGB;
	}

	/******************************************************
	 * Initial Data Address
	 * (8 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère l'adresse de départ 
	 * 				des données du fichier image BMP.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void initialDataAddress(DataInputStream inputStream) throws IOException {

		byte[] bdOffBits = new byte[8];
		inputStream.readFully(bdOffBits);
		adresseDonnee = (((int) bdOffBits[7] & RGB) << BITS_PIXEL_24) | 
				(((int) bdOffBits[6] & RGB) << BITS_PIXEL_16) | 
				(((int) bdOffBits[5] & RGB) << BITS_PIXEL_8) | 
				(int) bdOffBits[4] & RGB;
	}

	/******************************************************
	 * Header Size
	 * (8 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère la taille de 
	 * 				l’entête BMP. Typiquement 40 bytes.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void headerSize(DataInputStream inputStream) throws IOException {

		byte[] biSize = new byte[4];
		inputStream.readFully(biSize);
		tailleEntete = (((int) biSize[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biSize[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biSize[1] & RGB) << BITS_PIXEL_8) | 
				(int) biSize[0] & RGB;

		//Affiche une erreur si ne respecte pas 24 bits par pixelune entête de 40 octets
		if(tailleEntete != ENTETE_OCTET_40)
			throw new IOException(ERROR_ENTETE); 	
	}

	/******************************************************
	 * Image Resolution
	 * (8 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère les dimensions
	 * 				en pixels de l'image (hauteur et largeur).
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void imageResolution(DataInputStream inputStream) throws IOException {

		byte[] biWidth = new byte[4];
		inputStream.readFully(biWidth);
		largeur = (((int) biWidth[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biWidth[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biWidth[1] & RGB) << BITS_PIXEL_8) | 
				(int) biWidth[0] & RGB;

		byte[] biHeight = new byte[4];
		inputStream.readFully(biHeight);
		hauteur = (((int) biHeight[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biHeight[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biHeight[1] & RGB) << BITS_PIXEL_8) | 
				(int) biHeight[0] & RGB;
	}

	/******************************************************
	 * Bits per Pixel
	 * (4 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère la quantité de
	 * 				bits par pixel de l'image importée.
	 * 				Vérifie si l'image est bien 
	 * 				24 bits par pixel.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void bitsPerPixel(DataInputStream inputStream) throws IOException {

		byte[] biPlanes = new byte[2];
		inputStream.readFully(biPlanes);
		planes = (((int) biPlanes[1] & RGB) << BITS_PIXEL_8) | 
				(((int) biPlanes[0] & RGB));

		byte[] biBitCount = new byte[2];
		inputStream.readFully(biBitCount);
		bitsPixel = (((int) biBitCount[1] & RGB) << BITS_PIXEL_8) | 
				(((int) biBitCount[0] & RGB));

		//Affiche une erreur si ne respecte pas 24 bits par pixel
		if(bitsPixel != BPP_24)
			throw new IOException(ERROR_BPP); 	
	}

	/******************************************************
	 * Compress Type
	 * (4 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère  le type d'algorithme
	 * 				utilisé pour compresser l'image.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void compressType(DataInputStream inputStream) throws IOException {

		byte[] biCopression = new byte[4];
		inputStream.readFully(biCopression);
		compresser = ((int) biCopression[3] << BITS_PIXEL_24) | 
				((int) biCopression[2] << BITS_PIXEL_16) | 
				((int) biCopression[1] << BITS_PIXEL_8) | 
				(int) biCopression[0];	
	}
	
	/******************************************************
	 * File Weight
	 * (4 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère la taille de 
	 * 				l'image compressé en bytes (octets)
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void compressedFileWeight(DataInputStream inputStream) throws IOException {

		byte[] biSizeImage = new byte[4];
		inputStream.readFully(biSizeImage);
		tailleImage = (((int) biSizeImage[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biSizeImage[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biSizeImage[1] & RGB) << BITS_PIXEL_8) | 
				(int) biSizeImage[0] & RGB;

		//Affiche une erreur si la taille du fichier est plus petite que la taille de l'image
		if(taille < tailleImage)
			throw new IOException(ERROR_SIZE); 	
	}

	/******************************************************
	 * Resolution
	 * (8 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère  la résolution
	 * 				horizontale et verticale de l'image.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void resolution(DataInputStream inputStream) throws IOException {

		byte[] biXPelsPerMeter = new byte[4];
		inputStream.readFully(biXPelsPerMeter);
		pixelsHorizontale = (((int) biXPelsPerMeter[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biXPelsPerMeter[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biXPelsPerMeter[1] & RGB) << BITS_PIXEL_8) | 
				(int) biXPelsPerMeter[0] & RGB;

		byte[] biYPelsPerMeter = new byte[4];
		inputStream.readFully(biYPelsPerMeter);
		pixelsVerticale = (((int) biYPelsPerMeter[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biYPelsPerMeter[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biYPelsPerMeter[1] & RGB) << BITS_PIXEL_8) | 
				(int) biYPelsPerMeter[0] & RGB;
	}

	/******************************************************
	 * Color Information
	 * (8 bytes)
	 * 
	 * @Resumer:	Fonction qui récupère l'information
	 * 				sur les couleurs de l'image telle
	 * 				que la couleur dominante et autre.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void colorInfo(DataInputStream inputStream) throws IOException {

		byte[] biClrUsed = new byte[4];
		inputStream.readFully(biClrUsed);

		//couleursUtilisees = (((int) biClrUsed[3] & RGB) << BITS_PIXEL_24) | 
		//		(((int) biClrUsed[2] & RGB) << BITS_PIXEL_16) | 
		//		(((int) biClrUsed[1] & RGB) << BITS_PIXEL_8) | 
		//		(int) biClrUsed[0] & RGB;

		byte[] biClrImportant = new byte[4];
		inputStream.readFully(biClrImportant);

		couleursImportantes = (((int) biClrImportant[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biClrImportant[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biClrImportant[1] & RGB) << BITS_PIXEL_8) | 
				(int) biClrImportant[0] & RGB;
	}

	/******************************************************
	 * Build Image BMP
	 * 
	 * @Resumer:	Fonction qui construit l'image BPM.
	 * 				Organisation des pixels pour un 
	 * 				affichage adéquat de l'image.
	 * 
	 * @param: 		Les données de l'image importée
	 * @Throws: 	IOException
	 * 
	 ******************************************************/
	private void buildImageBMP(DataInputStream inputStream) throws IOException {

		image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);
		
		for (int axeY = image.getHeight() - 1; axeY >= 0; axeY--) {
			for (int axeX = 0; axeX < image.getWidth(); axeX++) {

				//Array qui contient les couleurs R G B (3)
				byte[] arrayImage = new byte[NB_COULEURS];
				inputStream.readFully(arrayImage);

				//Compilation des couleurs R G B 
				bitmap = ((int) arrayImage[0] & RGB); 			//Bleu
				bitmap += (((int) arrayImage[1] & RGB) << 8); 	//Vert
				bitmap += (((int) arrayImage[2] & RGB) << 16); 	//Rouge
				image.setRGB(axeX, axeY, bitmap);
			}
		}
	}

	/******************************************************
	 * Identify Colors Quantity
	 * 
	 * @Resumer:	Fonction qui récupère le nombre de
	 * 				couleurs différentes dans l'image iportée.
	 * 
	 * @Source:		https://stackoverflow.com/questions/5153504/count-the-number-of-colors-of-images
	 * 
	 ******************************************************/
	private void identifyColorsQty() {

		//Liste dynamique qui garde en memoire les couleurs
		Set<Integer> couleurs = new HashSet<Integer>();

		for(int axeY = 0; axeY < hauteur; axeY++) {
			for(int axeX = 0; axeX < largeur; axeX++) 
				couleurs.add(image.getRGB(axeX, axeY));
		}
		//Nombre de couleurs différentes identifiées.
		colorsQty = couleurs.size();	
	}

	/***************************
	 * COULEUR PIXELS RGB
	 *
	 * @Source:
	 * Complet: https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
	 ***************************/
	/******************************************************
	 * Identify Colors Quantity
	 * 
	 * @Resumer:	Fonction qui récupère la moyenne des
	 * 				valeurs RGB de l'image.
	 * 
	 * @Source:		https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
	 * 
	 ******************************************************/
	private void averageRGB() {

		//Boucles pour lire les pixels sur l'axe des X et Y
		for(int axeY = 0; axeY < hauteur; axeY++) {
			for(int axeX = 0; axeX < largeur; axeX++) {
				int color = image.getRGB(axeX,axeY); 

				//Recupere les valeurs des couleurs (une fois passer au travers de tous les pixels)
				rouge   = (color & 0x00ff0000) >> 16;
				vert = (color & 0x0000ff00) >> 8;
				bleu  =  color & 0x000000ff;
			}
		}
	}

	/***************************
	 * Get Image
	 * @return: image
	 ***************************/
	@Override
	public BufferedImage draw() {
		return image;
	}

	/***************************
	 * GET HEIGHT
	 * @return: hauteur image
	 ***************************/
	@Override
	public int getHeight() {
		return hauteur;
	}

	/***************************
	 * GET WIDTH
	 * @return: largeur image
	 ***************************/
	@Override
	public int getWidth() {
		return largeur;
	}
}