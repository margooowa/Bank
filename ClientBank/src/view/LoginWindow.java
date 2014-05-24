package view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JButton;
import entities.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import rmiclient.RMIFacade;
import xml.Signin;
import xml.Signins;

import bussiness.Contant;
import bussiness.IFacade;

public class LoginWindow extends JFrame {
	
	private IFacade facade;
	private JPanel contentPane;
	private JTextField textFieldLogin1;
	private JPasswordField passwordFieldLogin;
	protected String value1;
	private JPanel panel;
	private JLabel lblLogin;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JMenu mnNewMenu;
	private JMenuBar menuBar;
	private JMenuItem Preview;
	private JMenuItem Contact ;
	

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow("login");
					Dimension dimension = Toolkit.getDefaultToolkit()
							.getScreenSize();
					int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
					int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
					frame.setLocation(x, y);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param currentLogin 
	 */

	public LoginWindow(String currentLogin) {
		
		this.facade = RMIFacade.getFacade();
		
		
		this.value1 = currentLogin;
		setTitle("LOGIN WINDOW");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 289, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBorder(new TitledBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, null), "Login", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 205)));
		panel.setBounds(30, 42, 202, 200);
		contentPane.add(panel);
		panel.setLayout(null);

		lblLogin = new JLabel("Login");
		lblLogin.setBounds(10, 38, 60, 14);
		panel.add(lblLogin);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(10, 86, 60, 14);
		panel.add(lblPassword);

		textFieldLogin1 = new JTextField();
		textFieldLogin1.setBounds(106, 35, 86, 20);
		panel.add(textFieldLogin1);
		textFieldLogin1.setColumns(10);

		passwordFieldLogin = new JPasswordField();
		passwordFieldLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					login();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		passwordFieldLogin.setBounds(106, 83, 86, 20);
		panel.add(passwordFieldLogin);

		btnLogin = new JButton("LOGIN");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					login();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(55, 151, 89, 23);
		panel.add(btnLogin);

		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 46, 21);
		contentPane.add(menuBar);

		mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);

		Preview = new JMenuItem("Preview Credit Programs");
		Preview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreviewWindow addFrame;
				try {
					addFrame = new PreviewWindow();
					addFrame.setVisible(true);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnNewMenu.add(Preview);

		Contact = new JMenuItem("Contacts");
		Contact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContactsWindow addFrame = new ContactsWindow();
				addFrame.setVisible(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
		});
		mnNewMenu.add(Contact);

	}

	
//	private void log(User user) throws IOException{
////		
//		try {
//			
//			JAXBContext context = JAXBContext.newInstance(Signins.class);
//			Marshaller m = context.createMarshaller();
//			
//			File f = new File("C:/logi.txt");
//			f.mkdirs(); 
//			f.createNewFile();
//			m.marshal(new Signins(new Signin(user)),f);
//			
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			JOptionPane.showMessageDialog(null, e);
//		}
//		   
//	          
//	      
//	              
//	        	FileHandler fileTxt = new FileHandler("eLog.txt", true);
//	            SimpleFormatter formatter = new SimpleFormatter();
//	            fileTxt.setFormatter(formatter);
//	            Logger LOGGER = Logger.getLogger("info");
//	            LOGGER.addHandler(fileTxt);
//	            LOGGER.setLevel(Level.INFO);
//	            String info = "Login: "+user.getLogin()+", User Type: "+user.getuserType();
//	            LOGGER.info(info);
//            
//	        	}
	     
	             	
		
	
	public void login() throws RemoteException {
		String value1 = textFieldLogin1.getText();
		@SuppressWarnings("deprecation")
		String value2 = passwordFieldLogin.getText();
		try {
			User users = facade.findByPassLogUser(value1, value2);
			
//			log(users);
			switch (users.getuserType()) {
			case MANAGER:
				ManagerWindow managerFrame = new ManagerWindow(value1);
				managerFrame.setVisible(true);
				dispose();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				break;
			case ADMIN:
				AdminWindow adminFrame = new AdminWindow(value1);
				adminFrame.setVisible(true);
				dispose();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				break;
			case Client:
				ClientWindow clientFrame = new ClientWindow(value1);
				clientFrame.setVisible(true);
				dispose();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				break;
			}

			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Incorrect passport or login",
					"Error", JOptionPane.ERROR_MESSAGE);
		//	JOptionPane.showMessageDialog(null, ex);
		}

	}

}
