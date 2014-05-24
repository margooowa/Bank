package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import bussiness.Contant;

import entities.Client;
import entities.CreditProgram;
import entities.Order;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import rmiclient.RMIFacade;
import bussiness.IFacade;;

public class ClientWindow extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextPane textPane;
	private IFacade facade;
	private JTable table_1;
	private String currentLogin;
	private long table_click;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNewMenuItem;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JButton btnSelectCreditProgram;
	private JScrollPane scrollPane_1;
	private JPanel panel_1;
	private JScrollPane scrollPane_2;
	private JButton btnNewButton;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientWindow frame = new ClientWindow("clientLogin");
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
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

	public DefaultTableModel clientTableModel() throws RemoteException {

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "ID","NAME", "SHORT_DESC",
				"MIN_SUM", "MAX SUMA", "TERMIN" });
		List<CreditProgram> programs = facade.findAllCreditPrograms();

		for (CreditProgram cp : programs) {
			Object[] data = new Object[7];
			data[0] = cp.getId();
			data[1] = cp.getName();
			data[2] = cp.getShortDescription();
			data[3] = cp.getMinSum();
			data[4] = cp.getMaxSum();
			data[5] = cp.getCreditTimeExpiration();
			model.addRow(data);
		}
		return model;
	}

	public DefaultTableModel orderTableModel() throws RemoteException {

		DefaultTableModel model = new DefaultTableModel();
				model.setColumnIdentifiers(new Object[] { "ID", "CreditProgram",
				"OrderDate", "Status", "Guarantee", "CreditSum", "Salary" });

		List<Order> orders = facade.findByInnOrders(currentLogin);


		for (Order cp : orders) {
			Object[] data = new Object[7];
			data[0] = cp.getId();
			data[1] = cp.getCreditProgram().getName();
			data[2] = cp.getDate();
			data[3] = cp.getStatus();
			data[4] = cp.isGurantee();
			data[5] = cp.getCreditSum();
			data[6] = cp.getSalary();

			model.addRow(data);

		}
		return model;
	}

	/**
	 * Create the frame.
	 * @throws RemoteException 
	 */
	public ClientWindow(final String login) throws RemoteException {
		
		this.currentLogin = login;
		
		this.facade = RMIFacade.getFacade();
		
		setTitle("Client Window"+" Client inn "+login);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 615, 390);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);
		
		mntmNewMenuItem = new JMenuItem("Logout");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLogin = "";
				LoginWindow loginFrame = new LoginWindow(currentLogin);
				loginFrame.setVisible(true);
				dispose();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		DefaultTableModel T;
		T = clientTableModel();

		panel = new JPanel();
		tabbedPane.addTab("Credit programs", null, panel, null);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 551, 126);
		panel.add(scrollPane);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableProgramClick();
			}
		});
		table.setModel(T);
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		//table.getColumnModel().getColumn(4).setPreferredWidth(70);
		scrollPane.setViewportView(table);

		lblNewLabel = new JLabel(
				"Full description of delected credit program");
		lblNewLabel.setBounds(156, 148, 278, 14);
		panel.add(lblNewLabel);

		btnSelectCreditProgram = new JButton("Order credit program");
		btnSelectCreditProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table_click == 0){JOptionPane.showMessageDialog(null, "Please select credit program");}
				else {OrderWindow addFrame = new OrderWindow(login,table_click);
				addFrame.setVisible(true);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}

			}
		});
		btnSelectCreditProgram.setBounds(208, 263, 172, 23);
		panel.add(btnSelectCreditProgram);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 171, 551, 52);
		panel.add(scrollPane_1);

		textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);

		panel_1 = new JPanel();
		tabbedPane.addTab("Credit history", null, panel_1, null);
		panel_1.setLayout(null);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 11, 564, 184);
		panel_1.add(scrollPane_2);

		DefaultTableModel T1;
		T1=orderTableModel();
		table_1 = new JTable(T1);
		scrollPane_2.setViewportView(table_1);
		
		
		
		
		btnNewButton = new JButton("Update Information");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					removeRowsOrder();
					DefaultTableModel T1;
					T1=orderTableModel();
					table_1.setModel(T1);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton.setBounds(390, 235, 149, 23);
		panel_1.add(btnNewButton);
			
		
	}

	
	
	protected void tableProgramClick() {
		try {
			int row = table.getSelectedRow();
			table_click = (Long)(table.getModel().getValueAt(row, 0));
			List<CreditProgram> programs = facade.findAllCreditPrograms();
			for (CreditProgram cp : programs) {
				if (cp.getId() == table_click) {
					textPane.setText(cp.getLongDescription());

				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}
	
	void removeRowsOrder() throws RemoteException {
        int rows = orderTableModel().getRowCount();
        for (int i = 0; i < rows; i++) {
        	orderTableModel().removeRow(0);
        }
    }
	
	
}
