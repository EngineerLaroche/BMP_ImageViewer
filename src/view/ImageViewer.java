package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

import controller.ImageFileFilter;
import controller.ProxyFactory;
import model.Image;

/**************************************************************
 * @CLASS_TITLE:	Image Viewer
 * 
 * @Description: 	Cette classe est utilis�e comme JFrame dans 
 * 					lequel il est possible d�afficher plusieurs
 * 					images en m�me temps. Chaque image sera affich�e 
 * 					sur un onglet.
 * 
 * @author:			Alexandre Laroche
 * 
 **************************************************************/
public class ImageViewer extends JFrame implements ActionListener {
	
	/*****************************
	 * Generated Serial
	 *****************************/
	private static final long serialVersionUID = -4720005788645827377L;	
	
	/*****************************
	 * Class Instances
	 *****************************/
	private JTabbedPane tabs;
	private JFileChooser fileChooser;
	
	/******************************************************
	 * Constructeur - ImageViewer
	 * 
	 * @Resumer:	D�fini les propri�t�s du Frame pour un 
	 * 				affichage ad�quat.
	 * 
	 ******************************************************/
	public ImageViewer() {

		this.setTitle("BMP Image Viewer");
		this.setLayout(new BorderLayout());
		
		buildMenu();		//Creation du Menu
		setupFileChooser();	//Creation du s�lecteur de fichiers
		
		tabs = new JTabbedPane();
		this.add(tabs);
		this.setSize(800,600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/******************************************************
	 * Build Menu
	 * 
	 * @Resumer:	Construire un menu pour l'utilisateur. 
	 * 				Offre "ouvrir", "fermer" et "quitter" 
	 * 				comme actions.
	 * 
	 ******************************************************/
	private void buildMenu() {

		//Open file action
		JMenuItem open = new JMenuItem("Open image");
		open.setActionCommand("open");
		open.addActionListener(this);
		open.setToolTipText("Select an image file to open");
		
		//Close tab action
		JMenuItem close = new JMenuItem("Close image");
		close.setActionCommand("close");
		close.addActionListener(this);
		close.setToolTipText("Close the active tab");
		
		// Quit action
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		quit.setToolTipText("Quit the application");
		
		//Initialisation du Menu avec le titre "File"
		JMenu menu = new JMenu("File");
		menu.add(open);
		menu.add(close);
		menu.add(quit);
		
		//Initialisation de la barre Menu
		JMenuBar menubar = new JMenuBar();
		menubar.add(menu);
		this.setJMenuBar(menubar);
	}
	
	/******************************************************
	 * Setup File Chooser
	 * 
	 * @Resumer:	Pr�pare une bo�te de dialogue JFileChooser 
	 * 				avec les options appropri�es pour minimiser 
	 * 				les erreurs possibles caus�es par une s�lection 
	 * 				non valide de l'utilisateur.
	 * 
	 ******************************************************/
	private void setupFileChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Please select a valid image file");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	//S�lection de fichier seulement
		fileChooser.addChoosableFileFilter(new ImageFileFilter());	//Ajout du filtre personnalis�
		fileChooser.setMultiSelectionEnabled(false);				//Permet seulement une s�lection � la fois
		fileChooser.setCurrentDirectory(new File("."));				//Le r�pertoire de d�part sera le projet
	}
	
	/******************************************************
	 * Add Tab
	 * 
	 * @Resumer:	Cr�ez un nouvel onglet qui sera ajout� 
	 * 				� la section principale. Le nouvel onglet 
	 * 				affichera l'image associ�e au descripteur 
	 * 				donn�.
	 * 
	 * @param 		Fichier (image) import�
	 * @throws 		IOException 
	 * 
	 ******************************************************/
	private void addTab(File file) throws IOException {
		
		// Cr�er une image proxy
		Image image = ProxyFactory.getInstance().build(file); 

		//Si le proxy a �t� cr�� avec succ�s, on ajoute un onglet pour afficher l'image.
		if(image != null)
			tabs.addTab(file.getName(), new ImagePanel(image));
	}
	
	/******************************************************
	 * Action Performed
	 * 
	 * @Resumer:	Supporte les actions de l'utilisateur
	 * 				dans le menu de l'application (GUI).
	 * 
	 * @param 		Action utilisateur
	 * 
	 ******************************************************/
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String action = arg0.getActionCommand();
		if(action.equals("open")) {
			if(fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
				try { addTab(fileChooser.getSelectedFile()); } 
				catch (IOException e) { e.printStackTrace(); }
		} 
		else if(action.equals("close")) {
			if(tabs.getSelectedComponent() != null)			// Fermeture de l'onglet 
				tabs.remove(tabs.getSelectedComponent());
		} 
		else if(action.equals("quit"))  System.exit(0); 	//Fermeture de l'application
		else System.out.println(arg0.getActionCommand());	//Action inconnu
		
	}
}
