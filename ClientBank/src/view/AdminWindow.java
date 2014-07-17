package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import rmiclient.RMIFacade;
import bussiness.IFacade;
import entities.CreditProgram;
import entities.Manager;

//import util.HibernateUtil;

public class AdminWindow extends JFrame {

	
	private JPanel contentPane= new JPanel();
	private JTable programsTable= new JTable();;
	private IFacade iFacade;
	private JTable managersTable= new JTable();;
	private JTextField textFieldCredit2 = new JTextField();
	private JTextField textFieldCredit6 = new JTextField();
	private JTextField textFieldCredit3 = new JTextField();
	private JTextField textFieldCredit4 = new JTextField();
	private JTextField textFieldCredit5 = new JTextField();
	private JTextPane textCreditPane;
	private JTextField textManager1 = new JTextField();
	private JTextField textManager2 = new JTextField();
	private JTextField textManager3 = new JTextField();
	private String currentLogin;
	private JCheckBox chckbxCreate = new JCheckBox("Create Rule");
	private JCheckBox chckbxUpdate = new JCheckBox("Update Rule");
	private JCheckBox chckbxDelete = new JCheckBox("Delete Rule");
	private JMenuBar menuBar= new JMenuBar();
	private JMenu mnMenu= new JMenu("Menu");
	private JMenuItem mntmLogout = new JMenuItem("Logout");
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JPanel panel= new JPanel();
	private JScrollPane scrollPane= new JScrollPane();
	private JLabel lblCredit2 = new JLabel("Name");
	private JLabel lblCredit6 = new JLabel("Short Description");
	private JLabel lblCredit7 = new JLabel("Long description");
	private JLabel lblCredit3 = new JLabel("Min Suma");
	private JLabel lblCredit4 = new JLabel("Max Suma");
	private JLabel lblCredit5= new JLabel("Termin");
	private JButton btnNew = new JButton("New") ;
	private JButton btnEdit= new JButton("Edit") ;
	private JButton btnDelete= new JButton("Delete") ;
	private JScrollPane scrollPane_2= new JScrollPane();
	private JPanel panel_1= new JPanel();
	private JScrollPane scrollPane_1= new JScrollPane();
	private JLabel lblManager1 = new JLabel("Name");
	private JLabel lblManager2 = new JLabel("Login");
	private JLabel lblManager3 = new JLabel("Password");
	private JButton btnNewManager= new JButton("New") ;
	private JButton btnEditManager= new JButton("Edit") ;
	private JButton btnDeleteManager= new JButton("Delete") ;
	private List<CreditProgram> programs;
	 private List<Manager> managers;
	 private DefaultTableModel creditProgramTableModel;
	  private DefaultTableModel managerTableModel;
	  


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminWindow frame = new AdminWindow("adminLogin");
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

	public AdminWindow(String login) {
        this.currentLogin = login;
        this.iFacade = RMIFacade.getFacade();
        createTableModels();
        fillCreditProgramTableModel();
        fillManagerTableModel();
        drawAllStaff();
        addListeners();
    }
	
	
	
	 private void createTableModels() {
	        creditProgramTableModel = new DefaultTableModel();
	        managerTableModel = new DefaultTableModel();
	        creditProgramTableModel.setColumnIdentifiers(new Object[]{"ID", "NAME", "SHORT_DESC", "MAX_SUM", "MIN SUM", "TERM"});
	        managerTableModel.setColumnIdentifiers(new Object[]{"ID", "NAME", "LOGIN", "PASSWORD","Create", "Update", "Delete","USER_TYPE" });
	    }
	 
	 void removeRowsCredit() {
	        int rows = creditProgramTableModel.getRowCount();
	        for (int i = 0; i < rows; i++) {
	            creditProgramTableModel.removeRow(0);
	        }
	    }
	 
	 void removeRowsManager() {
	        int rows = managerTableModel.getRowCount();
	        for (int i = 0; i < rows; i++) {
	            managerTableModel.removeRow(0);
	        }
	    }

	 public void fillCreditProgramTableModel() {
	        removeRowsCredit();
	        try {
	            programs = iFacade.findAllCreditPrograms();
	           
	        } catch (RemoteException e) {
	            JOptionPane.showMessageDialog(null, e);
	        }
	        for (CreditProgram cp : programs) {
	            Object[] data = new Object[7];
	            data[0] = cp.getId();
	            data[1] = cp.getName();
	            data[2] = cp.getShortDescription();
	            data[3] = cp.getMinSum();
	            data[4] = cp.getMaxSum();
	            data[5] = cp.getCreditTimeExpiration();
	            creditProgramTableModel.addRow(data);
	           
	        }
	    
	    }


	 public void fillManagerTableModel() {
		  removeRowsManager();
	        try {
	            managers = iFacade.findAllManager();
	        } catch (RemoteException e) {
	            JOptionPane.showMessageDialog(null, e);
	        }
	        for (Manager cp : managers) {
	            Object[] data = new Object[8];
	            data[0] = cp.getId();
	            data[1] = cp.getName();
	            data[2] = cp.getLogin();
	            data[3] = cp.getPassword();
	            data[4] = cp.isCreateRule();
				data[5] = cp.isUpdateRule();
				data[6] = cp.isDeleteRule();
	            data[7] = cp.getuserType();
	            
	            managerTableModel.addRow(data);
	        }
	    }
	 
	 private void addListeners() {
	 
	    btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewCreditProgram();
                fillCreditProgramTableModel();
				clearFields();
			}
		}); 
	    
	    btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
					editCreditProgram();
				
				fillCreditProgramTableModel();
				clearFields();
			}
		});
	    
	    btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteCreditProgram();
                //better deleteSelectedRow();
                fillCreditProgramTableModel();
				clearFields();
			}
		});
	    
	    btnNewManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createNewManager();
				fillManagerTableModel();
				clearFieldsManager();
			}
		});
	    
	    btnEditManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editManager();
				fillManagerTableModel();
				clearFieldsManager();
			}
		});
	
	    btnDeleteManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteManager();
				fillManagerTableModel();
				clearFieldsManager();
			}
		});
	
	programsTable.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		try {
			int row = programsTable.getSelectedRow();
			Long id = (Long) (programsTable.getModel().getValueAt(row, 0));
			for (CreditProgram cp : programs) {

				if (cp.getId() == id) {

					textFieldCredit2.setText(cp.getName());
					textFieldCredit3.setText(String.valueOf(cp.getMinSum()));
					textFieldCredit4.setText(String.valueOf(cp.getMaxSum()));
					textFieldCredit5.setText(String.valueOf(cp
							.getCreditTimeExpiration()));
					textFieldCredit6.setText(cp.getShortDescription());
					textCreditPane.setText(cp.getLongDescription());
				}	}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
		}
		}		});
		
		managersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {	
						int row = managersTable.getSelectedRow();
						Long table_click = (Long) (managersTable.getModel().getValueAt(row, 0));
			
						for (Manager cp : managers) {
							if (cp.getId() == table_click) {
								textManager1.setText(cp.getName());
								textManager2.setText(String.valueOf(cp.getLogin()));
								textManager3.setText(String.valueOf(cp.getPassword()));
								chckbxCreate.setSelected(cp.isCreateRule());
								chckbxUpdate.setSelected(cp.isUpdateRule());
								chckbxDelete.setSelected(cp.isDeleteRule());
							}			}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex);
					}	
			}		});
		
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLogin = "";
				LoginWindow loginFrame = new LoginWindow(currentLogin);
				loginFrame.setVisible(true);
				dispose();
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
}
	/////////CREDIT PROGRAMS	
	 
	 private void deleteCreditProgram() {
	        try{Long id = (Long) programsTable.getValueAt(programsTable.getSelectedRow(), 0);
	        
	            iFacade.deleteByIdCreditProgram(id);
	            JOptionPane.showMessageDialog(null, "Deleted",
						"Information message", JOptionPane.INFORMATION_MESSAGE);
	        } catch (RemoteException e) {
	            JOptionPane.showMessageDialog(null, e);}
	            catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null,
							"Please click on the table for data delete", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
	        }
	     
	    

	    private void editCreditProgram() {
	    	try{
	        String id = String.valueOf(programsTable.getValueAt(programsTable.getSelectedRow(), 0));
	        HashMap<String, String> map = new HashMap();
	           
	        	        
	       map.put("name",textFieldCredit2.getText());
	       map.put("longDesc", textCreditPane.getText());
	       map.put("shortDesc",textFieldCredit6.getText());
	       map.put("minSum",textFieldCredit3.getText());
	       map.put("maxSum",textFieldCredit4.getText());
	       map.put("term" ,textFieldCredit5.getText());
	       map.put("id", id);
	       
	            iFacade.updateCreditProgram(map);
	            JOptionPane.showMessageDialog(null, "Information updated",
						"Information message", JOptionPane.INFORMATION_MESSAGE);
	        } catch (RemoteException e) {
	            JOptionPane.showMessageDialog(null, e);
	        }
	    	catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(null,
						"Please click on the table for data edit", "Error",
						JOptionPane.ERROR_MESSAGE);
			} 
	   
	    }

	    protected void createNewCreditProgram() {
	       try{ String name = textFieldCredit2.getText();
	        String shortDesc = textFieldCredit6.getText();
	        String longDesc = textCreditPane.getText();
	        long minSum = Long.parseLong(textFieldCredit3.getText());
	        long maxSum = Long.parseLong(textFieldCredit4.getText());
	        long term = Long.parseLong(textFieldCredit5.getText());

	        CreditProgram cp = new CreditProgram(name, shortDesc, longDesc, minSum, maxSum, term);
	        
	            iFacade.makePersistentCreditProgram(cp);
	            JOptionPane.showMessageDialog(null, "Created",
						"Information message", JOptionPane.INFORMATION_MESSAGE);
	        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
				JOptionPane.showMessageDialog(null,
						"Please click on the table for data create", "Error",
						JOptionPane.ERROR_MESSAGE);
			} 
	       catch (RemoteException e) {
	            e.printStackTrace();
	        } }
	        
	        
	       ///////////// MANAGERS
	        private void deleteManager() {
	        	try {
	        		Long id = (Long) managersTable.getValueAt(managersTable.getSelectedRow(), 0);
		        
		            iFacade.deleteByIdManager(id);
		            JOptionPane.showMessageDialog(null, "Deleted",
							"Information message", JOptionPane.INFORMATION_MESSAGE);
		        } catch (RemoteException e) {
		            JOptionPane.showMessageDialog(null, e);
		        }		        
				catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null,
							"Please click on the table for data delete", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
		       
		    }

		    private void editManager() {
		       try{ String id = String.valueOf(managersTable.getValueAt(managersTable.getSelectedRow(), 0));
		        HashMap<String, String> map = new HashMap();
		        
		           
		        	        
		        map.put("login", textManager2.getText());
		        map.put("password", textManager3.getText());
		        map.put("name",textManager1.getText());
		        map.put("createRule", String.valueOf(chckbxCreate.isSelected()));
		        map.put("updateRule",String.valueOf(chckbxUpdate.isSelected()));
		        map.put("deleteRule",String.valueOf(chckbxDelete.isSelected()));
		        map.put("id", id);
		        
		            iFacade.updateManager(map);
		            JOptionPane.showMessageDialog(null, "Information updated",
							"Information message", JOptionPane.INFORMATION_MESSAGE);
		        } catch (RemoteException e) {
		            JOptionPane.showMessageDialog(null, e);
		        }
		       catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null,
							"Please click on the table for data edit", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
		      
		    }

		    protected void createNewManager() {
		    	try{String login = textManager2.getText();
				String password = textManager3.getText();
				String name = textManager1.getText();
				Boolean createRule = chckbxCreate.isSelected();
				Boolean updateRule = chckbxUpdate.isSelected();
				Boolean deleteRule = chckbxDelete.isSelected();

				Manager man = new Manager(login, password, name, createRule,
						updateRule, deleteRule);
		       
		            iFacade.makePersistentManager(man);
		            JOptionPane.showMessageDialog(null, "Created",
							"Information message", JOptionPane.INFORMATION_MESSAGE);
		        } 
		        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
					JOptionPane.showMessageDialog(null,
							"Please click on the table for data create", "Error",
							JOptionPane.ERROR_MESSAGE);
				} 
		        catch (RemoteException e) {
		            e.printStackTrace();
		        } finally {
		  
		        }
	    }

	/**
	 * Create the frame.
	 */
	 private void drawAllStaff() {

				
		setTitle("Administrator Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 426);
		setJMenuBar(menuBar);
		menuBar.add(mnMenu);
		 programsTable.setModel(creditProgramTableModel);
		 programsTable.getColumnModel().getColumn(0).setPreferredWidth(7);
		 programsTable.getColumnModel().getColumn(1).setPreferredWidth(20);
		 programsTable.getColumnModel().getColumn(2).setPreferredWidth(100);
	
	     managersTable.setModel(managerTableModel);
		
		
		mnMenu.add(mntmLogout);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("Credit programs", null, panel, null);
		panel.setLayout(null);
		scrollPane.setBounds(10, 11, 632, 105);
		
		panel.add(scrollPane);
		scrollPane.setViewportView(programsTable);
		lblCredit2.setBounds(23, 168, 64, 14);
		
		panel.add(lblCredit2);
		textFieldCredit2.setBounds(119, 165, 86, 20);
		
		panel.add(textFieldCredit2);
		textFieldCredit2.setColumns(10);

		lblCredit6.setBounds(236, 168, 117, 14);
		panel.add(lblCredit6);

		lblCredit7.setBounds(236, 196, 117, 14);
		panel.add(lblCredit7);

		textFieldCredit6.setBounds(384, 165, 258, 20);
		panel.add(textFieldCredit6);
		textFieldCredit6.setColumns(10);

		lblCredit3.setBounds(23, 196, 64, 14);
		panel.add(lblCredit3);

		lblCredit4.setBounds(23, 229, 77, 14);
		panel.add(lblCredit4);

		
		textFieldCredit3.addKeyListener(keyListener);
		textFieldCredit3.setBounds(119, 193, 86, 20);
		panel.add(textFieldCredit3);
		textFieldCredit3.setColumns(10);

		
		textFieldCredit4.addKeyListener(keyListener);
		textFieldCredit4.setBounds(119, 226, 86, 20);
		panel.add(textFieldCredit4);
		textFieldCredit4.setColumns(10);

	
		lblCredit5.setBounds(23, 254, 64, 14);
		panel.add(lblCredit5);

		textFieldCredit5.addKeyListener(keyListener);
		textFieldCredit5.setBounds(119, 251, 86, 20);
		panel.add(textFieldCredit5);
		textFieldCredit5.setColumns(10);

				
		btnNew.setBounds(182, 299, 89, 23);
		panel.add(btnNew);

				
		btnEdit.setBounds(285, 299, 89, 23);
		panel.add(btnEdit);

				

		btnDelete.setBounds(384, 299, 89, 23);
		panel.add(btnDelete);

		
		scrollPane_2.setBounds(386, 196, 256, 81);
		panel.add(scrollPane_2);

		textCreditPane = new JTextPane();
		scrollPane_2.setViewportView(textCreditPane);

	
		tabbedPane.addTab("Managers", null, panel_1, null);
		panel_1.setLayout(null);

		
		scrollPane_1.setBounds(10, 11, 644, 123);
		panel_1.add(scrollPane_1);

		
		
		scrollPane_1.setViewportView(managersTable);
		


		lblManager1.setBounds(21, 179, 64, 14);
		panel_1.add(lblManager1);

		
		lblManager2.setBounds(21, 218, 64, 14);
		panel_1.add(lblManager2);

	
		lblManager3.setBounds(21, 255, 64, 14);
		panel_1.add(lblManager3);

	
		textManager1.setBounds(109, 176, 86, 20);
		panel_1.add(textManager1);
		textManager1.setColumns(10);

	
		textManager2.setBounds(109, 215, 86, 20);
		panel_1.add(textManager2);
		textManager2.setColumns(10);

		textManager3.setBounds(109, 252, 86, 20);
		panel_1.add(textManager3);
		textManager3.setColumns(10);

		
		
		btnNewManager.setBounds(532, 175, 89, 23);
		panel_1.add(btnNewManager);

	
		
		btnEditManager.setBounds(532, 214, 89, 23);
		panel_1.add(btnEditManager);

		
		
		btnDeleteManager.setBounds(532, 251, 89, 23);
		panel_1.add(btnDeleteManager);

		chckbxCreate = new JCheckBox("Create Rule");
		chckbxCreate.setBounds(292, 175, 97, 23);
		panel_1.add(chckbxCreate);

		chckbxUpdate = new JCheckBox("Update Rule");
		chckbxUpdate.setBounds(292, 214, 97, 23);
		panel_1.add(chckbxUpdate);

		chckbxDelete = new JCheckBox("Delete Rule");
		chckbxDelete.setBounds(292, 251, 97, 23);
		panel_1.add(chckbxDelete);

	}

//	protected void createNewCreditProgram() {
//		try {
//			String name = textFieldCredit2.getText();
//			String longDesc = textCreditPane.getText();
//			String shortDesc = textFieldCredit6.getText();
//			long minSum = Long.parseLong(textFieldCredit3.getText());
//			long maxSum = Long.parseLong(textFieldCredit4.getText());
//			long term = Long.parseLong(textFieldCredit5.getText());
//
//			CreditProgram cp = new CreditProgram(name, shortDesc, longDesc,
//					minSum, maxSum, term);
//			facade.makePersistentCreditProgram(cp);
//			JOptionPane.showMessageDialog(null, "Created",
//					"Information message", JOptionPane.INFORMATION_MESSAGE);
//		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
//			JOptionPane.showMessageDialog(null, "Please input data in fields",
//					"Error", JOptionPane.ERROR_MESSAGE);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	protected void createNewManager() {
//		try {
//			String login = textManager3.getText();
//			String password = textManager2.getText();
//			String name = textManager1.getText();
//			Boolean createRule = chckbxCreate.isSelected();
//			Boolean updateRule = chckbxUpdate.isSelected();
//			Boolean deleteRule = chckbxDelete.isSelected();
//
//			Manager man = new Manager(login, password, name, createRule,
//					updateRule, deleteRule);
//			facade.makePersistentManager(man);
//			JOptionPane.showMessageDialog(null, "Created",
//					"Information message", JOptionPane.INFORMATION_MESSAGE);
//		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
//			JOptionPane.showMessageDialog(null, "Please input data in fields",
//					"Error", JOptionPane.ERROR_MESSAGE);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	protected void deleteCreditProgram() {
//		try {
//			int row = table.getSelectedRow();
//
//			Long table_click = (Long) (table.getModel().getValueAt(row, 0));
//
//			List<CreditProgram> programs = facade.findAllCreditPrograms();
//
//			for (CreditProgram cp : programs) {
//
//				if (cp.getId() == table_click) {
//
//					facade.deleteByIdCreditProgram(cp.getId());
//				}
//
//			}
//			JOptionPane.showMessageDialog(null, "Deleted",
//					"Information message", JOptionPane.INFORMATION_MESSAGE);
//		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
//			JOptionPane.showMessageDialog(null,
//					"Please click on the table to delete data", "Error",
//					JOptionPane.ERROR_MESSAGE);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	protected void deleteMeneger() {
//		try {
//			int row = table2.getSelectedRow();
//			Long table_click = (Long) (table2.getModel().getValueAt(row, 0));
//
//			List<Manager> manager = facade.findAllManager();
//
//			for (Manager cp : manager) {
//
//				if (cp.getId() == table_click) {
//
//					facade.deleteByIdManager(cp.getId());
//
//				}
//			}
//			JOptionPane.showMessageDialog(null, "Deleted",
//					"Information message", JOptionPane.INFORMATION_MESSAGE);
//		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
//			JOptionPane.showMessageDialog(null,
//					"Please click on the table to delete data", "Error",
//					JOptionPane.ERROR_MESSAGE);
//		} catch (RemoteException e) {
//			
//			e.printStackTrace();
//		}
//
//	}
//
//	

	protected void clearFields() {
		textFieldCredit2.setText("");
		textFieldCredit3.setText("");
		textFieldCredit4.setText("");
		textFieldCredit5.setText("");
		textFieldCredit6.setText("");
		textCreditPane.setText("");

	}

	protected void clearFieldsManager() {
		textManager1.setText("");
		textManager2.setText("");
		textManager3.setText("");
		chckbxCreate.setOpaque(false);
		chckbxCreate.setOpaque(false);
		chckbxCreate.setOpaque(false);
	}

//	protected void editManagerTable() {
//		try {
//			String login = textManager2.getText();
//			String password = textManager3.getText();
//			String name = textManager1.getText();
//			Boolean createRule = chckbxCreate.isSelected();
//			Boolean updateRule = chckbxUpdate.isSelected();
//			Boolean deleteRule = chckbxDelete.isSelected();
//
//			int row = table2.getSelectedRow();
//			Long table_click = (Long) (table2.getModel().getValueAt(row, 0));
//
//			
//
//			facade.editManager(login,password,name,createRule,updateRule,deleteRule,table_click);
////			for (Manager cp : manager) {
////
////				if (cp.getId() == table_click) {
////					cp.setLogin(login);
////					cp.setPassword(password);
////					cp.setName(name);
////					cp.setCreateRule(createRule);
////					cp.setUpdateRule(updateRule);
////					cp.setDeleteRule(deleteRule);
////					facade.updateByIdManager(cp.getId());
////
////				}
////			}
//			JOptionPane.showMessageDialog(null, "Information updated",
//					"Information message", JOptionPane.INFORMATION_MESSAGE);
//		} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
//			JOptionPane.showMessageDialog(null,
//					"Please click on the table for edit data", "Error",
//					JOptionPane.ERROR_MESSAGE);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	protected void editCreditTable() throws RemoteException {
//		try{
//
//		String name = textFieldCredit2.getText();
//		Long minSum = Long.parseLong(textFieldCredit3.getText());
//		Long maxSum = Long.parseLong(textFieldCredit4.getText());
//		Long termin = Long.parseLong(textFieldCredit5.getText());
//		String shortDescr = textFieldCredit6.getText();
//		String longDescr = textCreditPane.getText();
//
//		int row = table.getSelectedRow();
//		Long table_click = (Long) (table.getModel().getValueAt(row, 0));
//
//		
//		facade.editCreditProgram(name, minSum, maxSum, termin, shortDescr,
//				longDescr, table_click);
//		JOptionPane.showMessageDialog(null, "Information updated",
//				"Information message", JOptionPane.INFORMATION_MESSAGE);}
//		catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ex) {
//			JOptionPane.showMessageDialog(null,
//					"Please click on the table for edit data", "Error",
//					JOptionPane.ERROR_MESSAGE);
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	

	KeyListener keyListener = new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
				JOptionPane.showMessageDialog(null, "Please put only numbers");
				getToolkit().beep();
				e.consume();
			}
		}
	};
}
