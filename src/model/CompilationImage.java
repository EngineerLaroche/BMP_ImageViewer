package model;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*******************************************************************
 * @Titre_Classe: COMPILATION IMAGE
 * 
 * @Resumer: 
 * Conversion et lecture en bytes du fichier (bitmap) recu.
 * Obtenir le descriptif complet de l'image (bitmap) recu (entete .bmp = 54 bits).
 * Reconstruire et afficher l'image correctement sur l'interface utilisateur.
 * 
 * @Source: 
 * Inspiration: https://alvinalexander.com/blog/post/java/getting-rgb-values-for-each-pixel-in-image-using-java-bufferedi
 * 
 * @author Alexandre Laroche
 * 
 * @Date: 29 janvier 2018
 *******************************************************************/
public class CompilationImage extends Image {

	/***************************
	 * Classe instanciee (image)
	 ***************************/
	private BufferedImage image = null;

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
	 * Variables
	 ***************************/
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
	couleursUtilisees = 0,
	couleursImportantes = 0,
	bitmap = 0,
	rouge = 0, 
	vert = 0, 
	bleu = 0;

	private String typeImage = "";


	/******************************************************
	 * CONSTRUCTEUR
	 * 
	 * @param File
	 * @Throws: IOException
	 * 
	 * @Source:
	 * FileInputStream: https://www.mkyong.com/java/how-to-read-file-in-java-fileinputstream/
	 * DataInputStream: http://www.codejava.net/java-se/file-io/java-io-datainputstream-and-dataoutputstream-examples
	 ******************************************************/
	public CompilationImage(File file) throws IOException {

		//Lecture en bytes du fichier recu en parametre
		FileInputStream fileInput = new FileInputStream(file);

		//Lecture du stream (fileInput)
		DataInputStream inputStream = new DataInputStream(fileInput);

		//Segmentation de la lecture des bytes en plusieurs sous-segments.
		typeFichier(inputStream); //Assure le respect du type de fichier BitMap
		tailleFichier(inputStream);
		adresseDepartBMP(inputStream);
		tailleEnteteBMP(inputStream);
		dimensionsImage(inputStream);
		quantiteBitsPixel(inputStream);//Assure le respect du 24 bits par pixel
		infoImage(inputStream);
		quantitePixelsXY(inputStream);
		couleursImage(inputStream);

		//Demarrage du processus d'affichage de l'image
		imageBitmap(inputStream);

		//Information supplémentaire
		nombreCouleurs();
		couleursRGB();

		//Fermeture de la lecture du fichier
		inputStream.close();

		//Classe qui affiche l'information de l'image dans la console
		new DetailsImage(numImage, typeImage, taille, adresseDonnee, 
				tailleEntete, hauteur, largeur, planes, bitsPixel, compresser, 
				tailleImage, couleursUtilisees, couleursImportantes, bleu, vert, rouge);
	}

	/***************************
	 * TYPE DE FICHIER
	 * (2 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void typeFichier(DataInputStream inputStream) throws IOException {

		byte[] bfType = new byte[2];
		inputStream.readFully(bfType);
		numImage = (char) bfType[0] + (char) bfType[1];
		typeImage = "" + (char) bfType[0] + (char) bfType[1];

		//Affiche une erreur si ne respecte pas le type de fichier BitMap
		if(!typeImage.equals(BIT_MAP) || numImage != NUM_BITMAP){
			throw new IOException(ERROR_BITMAP); 	
		}
	}

	/***************************
	 * TAILLE DU FICHIER
	 * (4 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void tailleFichier(DataInputStream inputStream) throws IOException {

		byte[] bfSize = new byte[4];
		inputStream.readFully(bfSize);
		taille = (((int) bfSize[3] & RGB) << BITS_PIXEL_24) | 
				(((int) bfSize[2] & RGB) << BITS_PIXEL_16) | 
				(((int) bfSize[1] & RGB) << BITS_PIXEL_8) | 
				(int) bfSize[0] & RGB;
	}

	/***************************
	 * ADRESSE DONNEES DEPART
	 * (8 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void adresseDepartBMP(DataInputStream inputStream) throws IOException {

		byte[] bdOffBits = new byte[8];
		inputStream.readFully(bdOffBits);
		adresseDonnee = (((int) bdOffBits[7] & RGB) << BITS_PIXEL_24) | 
				(((int) bdOffBits[6] & RGB) << BITS_PIXEL_16) | 
				(((int) bdOffBits[5] & RGB) << BITS_PIXEL_8) | 
				(int) bdOffBits[4] & RGB;
	}

	/***************************
	 * TAILLE ENTETE 
	 * (4 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void tailleEnteteBMP(DataInputStream inputStream) throws IOException {

		byte[] biSize = new byte[4];
		inputStream.readFully(biSize);
		tailleEntete = (((int) biSize[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biSize[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biSize[1] & RGB) << BITS_PIXEL_8) | 
				(int) biSize[0] & RGB;

		//Affiche une erreur si ne respecte pas 24 bits par pixelune entête de 40 octets
		if(tailleEntete != ENTETE_OCTET_40){
			throw new IOException(ERROR_ENTETE); 	
		}
	}

	/***************************
	 * DOMENSIONS IMAGE (pixels)
	 * (8 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void dimensionsImage(DataInputStream inputStream) throws IOException {

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

	/***************************
	 * NOMBRE DE BITS PAR PIXEL
	 * (4 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void quantiteBitsPixel(DataInputStream inputStream) throws IOException {

		byte[] biPlanes = new byte[2];
		inputStream.readFully(biPlanes);
		planes = (((int) biPlanes[1] & RGB) << BITS_PIXEL_8) | 
				(((int) biPlanes[0] & RGB));

		byte[] biBitCount = new byte[2];
		inputStream.readFully(biBitCount);
		bitsPixel = (((int) biBitCount[1] & RGB) << BITS_PIXEL_8) | 
				(((int) biBitCount[0] & RGB));

		//Affiche une erreur si ne respecte pas 24 bits par pixel
		if(bitsPixel != BPP_24){
			throw new IOException(ERROR_BPP); 	
		}
	}

	/***************************
	 * INFORMATION DE COMPRESSION
	 * (8 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void infoImage(DataInputStream inputStream) throws IOException {

		byte[] biCopression = new byte[4];
		inputStream.readFully(biCopression);
		compresser = ((int) biCopression[3] << BITS_PIXEL_24) | 
				((int) biCopression[2] << BITS_PIXEL_16) | 
				((int) biCopression[1] << BITS_PIXEL_8) | 
				(int) biCopression[0];

		byte[] biSizeImage = new byte[4];
		inputStream.readFully(biSizeImage);
		tailleImage = (((int) biSizeImage[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biSizeImage[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biSizeImage[1] & RGB) << BITS_PIXEL_8) | 
				(int) biSizeImage[0] & RGB;

		//Affiche une erreur si la taille du fichier est plus petite que la taille de l'image
		if(taille < tailleImage){
			throw new IOException(ERROR_SIZE); 	
		}
	}

	/***************************
	 * RESOLUTION IMAGE (pixels)
	 * (8 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void quantitePixelsXY(DataInputStream inputStream) throws IOException {

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

	/***************************
	 * INFORMATION COULEURS
	 * (8 octets)
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * << Bits: 	https://www.dyclassroom.com/image-processing-project/how-to-get-and-set-pixel-value-in-java
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 ***************************/
	private void couleursImage(DataInputStream inputStream) throws IOException {

		byte[] biClrUsed = new byte[4];
		inputStream.readFully(biClrUsed);

		//				couleursUtilisees = (((int) biClrUsed[3] & RGB) << BITS_PIXEL_24) | 
		//						(((int) biClrUsed[2] & RGB) << BITS_PIXEL_16) | 
		//						(((int) biClrUsed[1] & RGB) << BITS_PIXEL_8) | 
		//						(int) biClrUsed[0] & RGB;

		byte[] biClrImportant = new byte[4];
		inputStream.readFully(biClrImportant);

		couleursImportantes = (((int) biClrImportant[3] & RGB) << BITS_PIXEL_24) | 
				(((int) biClrImportant[2] & RGB) << BITS_PIXEL_16) | 
				(((int) biClrImportant[1] & RGB) << BITS_PIXEL_8) | 
				(int) biClrImportant[0] & RGB;
	}

	/***************************
	 * MONTAGE DE L'IMASGE BMP
	 * 
	 * @param: DataStream
	 * @Throws: IOException
	 * 
	 * @Source:
	 * readFully(): https://stackoverflow.com/questions/1264709/convert-inputstream-to-byte-array-in-java
	 * Compilation couleurs: https://stackoverflow.com/questions/6524196/java-get-pixel-array-from-image
	 ***************************/
	private void imageBitmap(DataInputStream inputStream) throws IOException {

		image = new BufferedImage(largeur, hauteur, BufferedImage.TYPE_INT_RGB);

		//Lecture des pixels sur l'axe des Y (depart coin haut gauche)
		for (int axeY = image.getHeight() - 1; axeY >= 0; axeY--) {

			//Lecture des pixels sur l'axe des X
			for (int axeX = 0; axeX < image.getWidth(); axeX++) {

				//Array qui contient les couleurs R G B (3)
				byte[] arrayImage = new byte[NB_COULEURS];
				inputStream.readFully(arrayImage);

				//Compilation des couleurs R G B 
				bitmap = ((int) arrayImage[0] & RGB); //Bleu
				bitmap += (((int) arrayImage[1] & RGB) << 8); //Vert
				bitmap += (((int) arrayImage[2] & RGB) << 16); //Rouge
				image.setRGB(axeX, axeY, bitmap);
			}
		}
	}

	/***************************
	 * NOMBRE DE COULEURS
	 * 
	 * @Source:
	 * Complet: https://stackoverflow.com/questions/5153504/count-the-number-of-colors-of-images
	 ***************************/
	private void nombreCouleurs() {

		//Liste dynamique qui garde en memoire les couleurs
		Set<Integer> couleurs = new HashSet<Integer>();

		//Boucles pour lire les pixels sur l'axe des X et Y
		for(int axeY = 0; axeY < hauteur; axeY++) {
			for(int axeX = 0; axeX < largeur; axeX++) {

				//Toutes les couleurs sont gardees en memoire dans une liste
				couleurs.add(image.getRGB(axeX, axeY));
			}
		}
		//Le nombre de couleurs est identifier par la dimension de la liste
		couleursUtilisees = couleurs.size();
	}

	/***************************
	 * COULEUR PIXELS RGB
	 *
	 * @Source:
	 * Complet: https://stackoverflow.com/questions/22391353/get-color-of-each-pixel-of-an-image-using-bufferedimages
	 ***************************/
	private void couleursRGB() {

		//Boucles pour lire les pixels sur l'axe des X et Y
		for(int axeY = 0; axeY < hauteur; axeY++) {
			for(int axeX = 0; axeX < largeur; axeX++) {

				//Pour les valeurs (pixel) recuperer a une position indiquer
				int couleurs=  image.getRGB(axeX,axeY); 

				//Recupere les valeurs des couleurs (une fois passer au travers de tous les pixels)
				rouge   = (couleurs & 0x00ff0000) >> 16;
			vert = (couleurs & 0x0000ff00) >> 8;
			bleu  =  couleurs & 0x000000ff;
			}
		}
	}

	/***************************
	 * GET RESPECT TAILLE
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