package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import entities.Client;
import entities.Manager;
import entities.Order;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import bussiness.IFacade;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import rmiclient.RMIFacade;

public class ManagerWindow extends JFrame {

	private JPanel contentPane;
	private JTable tableClients;
	private JTable tableOrders;
	private JTable tableOneClient;
	private JTable tablelClientOrders;
	private IFacade facade;
	private JTextField textFieldClients1;
	private JTextField textFieldClients2;
	private JTextField textFieldClients3;
	private JTextField textFieldClients4;
	private JTextField textFieldClients5;
	private JTextField textFieldClients6;
	private JTextField textFieldSearch;
	private String currentLogin;
	private DefaultTableModel orderTableModel;
	private long table_click;
	private JComboBox comboBox;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmLogout;
	private JTabbedPane tabbedPane;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JLabel lblAdress;
	private JLabel lblNewLabel_1;
	private JLabel lblPhone;
	private JLabel lblPassword;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnDelete;
	private JLabel lblInn;
	private JLabel lblInputClientsInn;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblPesonalInformation;
	private JLabel lblNewLabel_2;
	private JScrollPane scrollPane_2;
	private JButton btnNewButton;
	private JScrollPane scrollPane_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerWindow frame = new ManagerWindow("manager");
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

	public void setUpStatusColumn(JTable table2, TableColumn statusColumn) {
		// Set up the editor for the sport cells.
		comboBox = new JComboBox();
		comboBox.addItem("Waiting");
		comboBox.addItem("Denied");
		comboBox.addItem("Accepted");
		statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

		// Set up tool tips for the sport cells.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setToolTipText("Click for combo box");
		statusColumn.setCellRenderer(renderer);
	}

	public DefaultTableModel clientTable() throws RemoteException {

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "INN", "FIO",
				"ADRESS", "EMAIL", "PHONE", "PASSWORD", "USER TYPE" });
		List<Client> clients = facade.findAllClient();
		for (Client cp : clients) {
			Object[] data = new Object[9];
			data[0] = cp.getInn();
			data[1] = cp.getFio();
			data[2] = cp.getAdress();
			data[3] = cp.getEmail();
			data[4] = cp.getPhone();
			data[5] = cp.getPassword();
			data[6] = cp.getuserType();
			model.addRow(data);
		}
		return model;
	}

	public void orderTable() throws RemoteException {

		orderTableModel = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				// return column==3;
				if (column < 2) {
					return false;
				} else {
					return true;
				}
			}
		};
		orderTableModel.setColumnIdentifiers(new Object[] { "ID", "Client",
				"Date", "Status", "CreditProgram", "Gurantee", "CreditSum",
				"Salary" });
		List<Order> orders = facade.findAllOrders();

		for (Order cp : orders) {
			Object[] data = new Object[9];
			data[0] = cp.getId();
			data[1] = cp.getClient().getInn();
			data[2] = cp.getDate();
			data[3] = cp.getStatus();
			data[4] = cp.getCreditProgram().getName();
			data[5] = cp.isGurantee();
			data[6] = cp.getCreditSum();
			data[7] = cp.getSalary();
			orderTableModel.addRow(data);
		}

	}

	public DefaultTableModel personalInformation() throws RemoteException {

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "INN", "FIO", "ADRESS",
				"EMAIL", "PHONE", "PASSWORD", "USER TYPE" });
		Long inn = Long.parseLong(textFieldSearch.getText());

		Client clients = facade.findByInnClient(inn);

		Object[] data = new Object[9];
		data[0] = clients.getInn();
		data[1] = clients.getFio();
		data[2] = clients.getAdress();
		data[3] = clients.getEmail();
		data[4] = clients.getPhone();
		data[5] = clients.getPassword();
		data[6] = clients.getuserType();
		model.addRow(data);

		return model;
	}

	public DefaultTableModel orderTableByINN() throws RemoteException {

		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new Object[] { "ID", "Client", "Date",
				"Status", "CreditProgram", "Guarantee", "CreditSum", "Salary" });

		String inn = textFieldSearch.getText();

		// JOptionPane.showMessageDialog(null,inn);
		List<Order> orders = facade.findByInnOrders(inn);

		for (Order cp : orders) {
			Object[] data = new Object[9];
			data[0] = cp.getId();
			data[1] = cp.getClient().getInn();
			data[2] = cp.getDate();
			data[3] = cp.getStatus();
			data[4] = cp.getCreditProgram().getName();
			data[5] = cp.isGurantee();
			data[6] = cp.getCreditSum();
			data[7] = cp.getSalary();
			model.addRow(data);
		}
		return model;
	}

	/**
	 * Create the frame.
	 */
	public ManagerWindow(String login) {

		this.facade = RMIFacade.getFacade();
		this.currentLogin = login;
		try {
			orderTable();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setTitle("Manager Window. " + " Manager " + login);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 767, 364);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnNewMenu = new JMenu("Menu");
		menuBar.add(mnNewMenu);

		mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentLogin = "";
				LoginWindow loginFrame = new LoginWindow(currentLogin);
				loginFrame.setVisible(true);
				dispose();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			}
		});
		mnNewMenu.add(mntmLogout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		panel = new JPanel();
		tabbedPane.addTab("Clients", null, panel, null);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 716, 98);
		panel.add(scrollPane);

		tableClients = new JTable();
		try{DefaultTableModel T5;
		T5 = clientTable();
		tableClients.setModel(T5);
		tableClients.getColumnModel().getColumn(2).setPreferredWidth(90);
		tableClients.getColumnModel().getColumn(3).setPreferredWidth(90);
		tableClients.getColumnModel().getColumn(4).setPreferredWidth(70);
		tableClients.getColumnModel().getColumn(5).setPreferredWidth(50);
		tableClients.getColumnModel().getColumn(6).setPreferredWidth(50);
		
		}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		tableClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tableClientClick();
			}
		});

		
		scrollPane.setViewportView(tableClients);

		lblNewLabel = new JLabel("Family and Name");
		lblNewLabel.setBounds(20, 142, 117, 14);
		panel.add(lblNewLabel);

		lblAdress = new JLabel("Adress");
		lblAdress.setBounds(20, 180, 78, 14);
		panel.add(lblAdress);

		lblNewLabel_1 = new JLabel("Email");
		lblNewLabel_1.setBounds(20, 223, 46, 14);
		panel.add(lblNewLabel_1);

		lblPhone = new JLabel("Phone");
		lblPhone.setBounds(283, 142, 58, 14);
		panel.add(lblPhone);

		lblPassword = new JLabel("Password");
		lblPassword.setBounds(283, 180, 58, 14);
		panel.add(lblPassword);

		textFieldClients1 = new JTextField();
		textFieldClients1.setBounds(128, 139, 133, 20);
		panel.add(textFieldClients1);
		textFieldClients1.setColumns(10);

		textFieldClients2 = new JTextField();
		textFieldClients2.setBounds(128, 177, 133, 20);
		panel.add(textFieldClients2);
		textFieldClients2.setColumns(10);

		textFieldClients3 = new JTextField();
		textFieldClients3.setBounds(128, 220, 133, 20);
		panel.add(textFieldClients3);
		textFieldClients3.setColumns(10);

		textFieldClients4 = new JTextField();
		textFieldClients4.addKeyListener(keyListener2);
		textFieldClients4.setBounds(366, 139, 108, 20);
		panel.add(textFieldClients4);
		textFieldClients4.setColumns(10);

		textFieldClients5 = new JTextField();
		textFieldClients5.setBounds(366, 177, 108, 20);
		panel.add(textFieldClients5);
		textFieldClients5.setColumns(10);

		btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createNewClient();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateClientsTable();
				clearFields();
			}
		});
		btnNew.setBounds(567, 138, 89, 23);
		panel.add(btnNew);

		btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					try {
						editClient();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null,e1);
					}		
				updateClientsTable();
				clearFields();
			}
		});
		btnEdit.setBounds(567, 176, 89, 23);
		panel.add(btnEdit);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					deleteClient();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateClientsTable();
				clearFields();
			}
		});
		btnDelete.setBounds(567, 210, 89, 23);
		panel.add(btnDelete);

		lblInn = new JLabel("Inn");
		lblInn.setBounds(283, 223, 46, 14);
		panel.add(lblInn);

		textFieldClients6 = new JTextField();
		textFieldClients6.setBounds(366, 220, 108, 20);
		textFieldClients6.addKeyListener(keyListener);
		panel.add(textFieldClients6);
		textFieldClients6.setColumns(10);
		lblInputClientsInn = new JLabel("Input client INN");

		orderTableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODo
				int cl = e.getColumn();
				int r = e.getFirstRow();
				if (cl == 3) {
					try {
						updateStatusOrder(cl, r);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

		panel_1 = new JPanel();
		tabbedPane.addTab("Orders", null, panel_1, null);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 11, 716, 242);
		panel_1.add(scrollPane_1);

		tableOrders = new JTable();

		tableOrders.setModel(orderTableModel);

		scrollPane_1.setViewportView(tableOrders);
		setUpStatusColumn(tableOrders, tableOrders.getColumnModel()
				.getColumn(3));

		panel_2 = new JPanel();
		tabbedPane.addTab("Information about particular client", null, panel_2,
				null);
		panel_2.setLayout(null);

		lblInputClientsInn.setBounds(32, 11, 113, 14);
		panel_2.add(lblInputClientsInn);

		textFieldSearch = new JTextField();
		textFieldSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findAllAboutClient();
			}
		});
		textFieldSearch.setBounds(171, 8, 133, 20);
		textFieldSearch.addKeyListener(keyListener);
		panel_2.add(textFieldSearch);
		textFieldSearch.setColumns(10);

		lblPesonalInformation = new JLabel("Pesonal information");
		lblPesonalInformation.setBounds(10, 60, 146, 14);
		panel_2.add(lblPesonalInformation);

		lblNewLabel_2 = new JLabel("Information about client credit programs");
		lblNewLabel_2.setBounds(10, 148, 248, 14);
		panel_2.add(lblNewLabel_2);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(20, 173, 706, 80);
		panel_2.add(scrollPane_2);

		tablelClientOrders = new JTable();
		scrollPane_2.setViewportView(tablelClientOrders);

		btnNewButton = new JButton("Find");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				findAllAboutClient();
			}
		});
		btnNewButton.setBounds(352, 7, 89, 23);
		panel_2.add(btnNewButton);

		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(20, 85, 706, 38);
		panel_2.add(scrollPane_3);

		tableOneClient = new JTable();
		scrollPane_3.setViewportView(tableOneClient);

	}

	protected void updateClientsTable() {
		try{DefaultTableModel T2;
		T2 = clientTable();
		tableClients.setModel(T2);}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected void tableClientClick() {
		try {
			int row = tableClients.getSelectedRow();
			table_click = (Long) (tableClients.getModel().getValueAt(row, 0));

			List<Client> clients = facade.findAllClient();

			for (Client cp : clients) {

				if (cp.getInn() == table_click) {

					textFieldClients1.setText(cp.getFio());
					textFieldClients2.setText(cp.getAdress());
					textFieldClients3.setText(cp.getEmail());
					textFieldClients4.setText(cp.getPhone());
					textFieldClients5.setText(cp.getPassword());
				}

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
	}

	protected void createNewClient() throws RemoteException {
		Manager managers = facade.findByLoginManager(currentLogin);
		try { if (managers.isCreateRule() == true) {

			String fio = textFieldClients1.getText();
			String adress = textFieldClients2.getText();
			String email = textFieldClients3.getText();
			String phone = textFieldClients4.getText();
			String password = textFieldClients5.getText();
			long inn = Long.parseLong(textFieldClients6.getText());

			Client cp = new Client(password, adress, email, fio, inn, phone);
			facade.makePersistentClient(cp);
			JOptionPane.showMessageDialog(null, "Created",
					"Information message", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"You have no rules to create. Contact the Administrator",
					"Error", JOptionPane.ERROR_MESSAGE);
		}}
		catch(IllegalArgumentException | ArrayIndexOutOfBoundsException e) {JOptionPane.showMessageDialog(null,
				"Please input data in fields",
				"Error", JOptionPane.ERROR_MESSAGE);}
	}

	protected void deleteClient() throws RemoteException {
		Manager managers = facade.findByLoginManager(currentLogin);
		if (managers.isDeleteRule() == true) {
			try {

				int row = tableClients.getSelectedRow();
				Long table_click = (Long) (tableClients.getModel().getValueAt(
						row, 0));

				List<Client> clients = facade.findAllClient();

				for (Client cp : clients) {

					if (cp.getInn() == table_click) {

						facade.deleteByInnClient(cp.getInn());

					}
				}

				JOptionPane.showMessageDialog(null, "Deleted",
						"Information message", JOptionPane.INFORMATION_MESSAGE);
			} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(null,
						"Please click on the table to delete data",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} else {
			JOptionPane.showMessageDialog(null,
					"You have no rules to delete. Contact the Administrator",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	protected void editClient() throws RemoteException {
		Manager managers = facade.findByLoginManager(currentLogin);
		if (managers.isUpdateRule() == true){
			
		try{ String inn = String.valueOf(tableClients.getValueAt(tableClients.getSelectedRow(), 0));
		
	        HashMap<String, String> map = new HashMap();
	        map.put("fio",textFieldClients1.getText());
	        map.put("adress", textFieldClients2.getText());
	        map.put("email", textFieldClients3.getText());
	        map.put("phone",textFieldClients4.getText());
	        map.put("password", textFieldClients5.getText());
	        map.put("inn", inn);
	      
	            facade.updateClient(map);
	            JOptionPane.showMessageDialog(null, "Information updated",
						"Information message", JOptionPane.INFORMATION_MESSAGE);
	        }
		 catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
			JOptionPane.showMessageDialog(null,
					"Please click on the table for edit data",
					"Error", JOptionPane.ERROR_MESSAGE);
	        }}
	        else {
				JOptionPane.showMessageDialog(null,
						"You have no rules to edit. Contact the Administrator",
						"Error", JOptionPane.ERROR_MESSAGE);
			}
	}
		
		


	protected void findAllAboutClient() {
		try{DefaultTableModel T3;
		T3 = personalInformation();
		tableOneClient.setModel(T3);
		tableOneClient.getColumnModel().getColumn(2).setPreferredWidth(90);
		tableOneClient.getColumnModel().getColumn(3).setPreferredWidth(90);
		tableOneClient.getColumnModel().getColumn(4).setPreferredWidth(70);
		tableOneClient.getColumnModel().getColumn(5).setPreferredWidth(50);
		tableOneClient.getColumnModel().getColumn(6).setPreferredWidth(50);}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try{DefaultTableModel T4;
		T4 = orderTableByINN();
		tablelClientOrders.setModel(T4);}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected void updateStatusOrder(int cl, int r) throws RemoteException {
		
		//String id = String.valueOf(tabl.getValueAt(programsTable.getSelectedRow(), 0));
		String status = tableOrders.getModel().getValueAt(r, cl).toString();
		String id = tableOrders.getModel().getValueAt(r, 0).toString();
		
        HashMap<String, String> map = new HashMap();
        map.put("status", status);
        map.put("id", id);
        try {
            facade.updateStatusOrder(map);
        } catch (RemoteException e) {
            JOptionPane.showMessageDialog(null, e);
        }
		
		
//		String value1 = tableOrders.getModel().getValueAt(r, cl).toString();
//		String value2 = tableOrders.getModel().getValueAt(r, 0).toString();
//		Long id = Long.parseLong(value2);
//		List<Order> orders = facade.findAllOrders();
//		for (Order cp : orders) {
//			if (cp.getId() == id) {
//
//				cp.setStatus(value1);
//				facade.updateByIdOrder(cp.getId());
//			}
//
//		}

	}

	protected void clearFields() {
		textFieldClients1.setText("");
		textFieldClients2.setText("");
		textFieldClients3.setText("");
		textFieldClients4.setText("");
		textFieldClients5.setText("");
		textFieldClients6.setText("");
	}

	KeyListener keyListener = new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE)
					|| (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_ENTER))) {
				JOptionPane.showMessageDialog(null, "Please put only numbers");
				getToolkit().beep();
				e.consume();
			}
		}
	};
	
	KeyListener keyListener2 = new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE)
					|| (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_ENTER)|| (c == '-')|| (c == '(')|| (c == ')'))) {
				JOptionPane.showMessageDialog(null, "Please put only numbers");
				getToolkit().beep();
				e.consume();
			}
		}
	};
}
