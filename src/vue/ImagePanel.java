package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import model.Image;

/**************************************************************
 * @CLASS_TITLE:	Image Panel
 * 
 * @Description: 	ImagePanel ajoute des fonctionnalit�s 
 * 					d'affichage d'image � un JPanel standard. 
 * 					L'image affich�e sur le panneau ne peut pas 
 * 					�tre modifi�e. ImagePanel ne v�rifie pas si 
 * 					l'image est valide, mais s'assure que les 
 * 					exceptions null ne sont pas d�clench�es.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class ImagePanel extends JPanel {	
	
	/*****************************
	 * Generated Serial
	 *****************************/
	private static final long serialVersionUID = -3554101564819375032L;
	
	/*****************************
	 * Class Instances
	 *****************************/
	private BufferedImage buffer = null;	//The ARGB representation of the image
	private Image image = null;				//The image bundle
	
	/******************************************************
	 * Constructeur - Image Panel
	 * 
	 * @Resumer:	D�fini l'image � afficher.
	 * 
	 * @param 		Image g�r�e par le panneau
	 * 
	 ******************************************************/
	public ImagePanel(Image _image) {
		this.image = _image;	
	}
	
	/******************************************************
	 * Paint
	 * 
	 * @Resumer:	Fonction qui permet de dessiner l'image
	 * 				sur un panneau.
	 * 
	 * @param		Graphics
	 * 
	 ******************************************************/
	public void paint(Graphics g) {
		int x, y;
		
		//Image valide
		if(image != null) {
			
			if(buffer == null)
				try { buffer = image.draw(); } 
				catch (IOException e) { e.printStackTrace(); }
			
			//Validation de l'image re�u
			if(buffer != null) {				
				
				// Sert � centrer l'image
				x = (this.getWidth() - image.getWidth()) / 2;
				y = (this.getHeight() - image.getHeight()) / 2;
				this.setBounds(x,y,image.getWidth(),image.getHeight());
				
				//Dessine l'image sur la panneau
				g.drawImage(buffer,x,y,Color.BLACK,null);
			}
		}
	}
}
