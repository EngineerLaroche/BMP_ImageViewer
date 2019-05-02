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
 * @Description: 	Cette classe est utilisée comme JFrame dans 
 * 					lequel il est possible d’afficher plusieurs
 * 					images en même temps. Chaque image sera affichée 
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
	 * @Resumer:	Défini les propriétés du Frame pour un 
	 * 				affichage adéquat.
	 * 
	 ******************************************************/
	public ImageViewer() {

		this.setTitle("BMP Image Viewer");
		this.setLayout(new BorderLayout());
		
		buildMenu();		//Creation du Menu
		setupFileChooser();	//Creation du sélecteur de fichiers
		
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
	 * @Resumer:	Prépare une boîte de dialogue JFileChooser 
	 * 				avec les options appropriées pour minimiser 
	 * 				les erreurs possibles causées par une sélection 
	 * 				non valide de l'utilisateur.
	 * 
	 ******************************************************/
	private void setupFileChooser() {
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setDialogTitle("Please select a valid image file");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);	//Sélection de fichier seulement
		fileChooser.addChoosableFileFilter(new ImageFileFilter());	//Ajout du filtre personnalisé
		fileChooser.setMultiSelectionEnabled(false);				//Permet seulement une sélection à la fois
		fileChooser.setCurrentDirectory(new File("."));				//Le répertoire de départ sera le projet
	}
	
	/******************************************************
	 * Add Tab
	 * 
	 * @Resumer:	Créez un nouvel onglet qui sera ajouté 
	 * 				à la section principale. Le nouvel onglet 
	 * 				affichera l'image associée au descripteur 
	 * 				donné.
	 * 
	 * @param 		Fichier (image) importé
	 * @throws 		IOException 
	 * 
	 ******************************************************/
	private void addTab(File file) throws IOException {
		
		// Créer une image proxy
		Image image = ProxyFactory.getInstance().build(file); 

		//Si le proxy a été créé avec succès, on ajoute un onglet pour afficher l'image.
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
