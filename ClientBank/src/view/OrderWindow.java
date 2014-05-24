package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;

import rmiclient.RMIFacade;

import bussiness.IFacade;


import entities.Client;
import entities.CreditProgram;
import entities.Order;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OrderWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private String login;
	private long table_click;
	private JTextField TextField1;
	private JTextField TextField2;
	private JCheckBox CheckBox;
	private IFacade facade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderWindow dialog = new OrderWindow("login", 1);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
			dialog.setLocation(x, y);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public OrderWindow(final String login, final long table_click) {
		this.login = login;
		this.facade = RMIFacade.getFacade();
		this.table_click = table_click;
		setBounds(100, 100, 313, 247);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblCreditSumm = new JLabel("Credit summ");
			lblCreditSumm.setBounds(10, 24, 93, 14);
			contentPanel.add(lblCreditSumm);
		}
		{
			JLabel lblCurrentSalary = new JLabel("Current salary");
			lblCurrentSalary.setBounds(10, 68, 93, 14);
			contentPanel.add(lblCurrentSalary);
		}

		CheckBox = new JCheckBox("Guarantee");
		CheckBox.setBounds(31, 109, 97, 23);
		contentPanel.add(CheckBox);

		TextField1 = new JTextField();
		TextField1.addKeyListener(keyListener);
		TextField1.setBounds(113, 21, 76, 20);
		contentPanel.add(TextField1);

		TextField2 = new JTextField();
		TextField2.addKeyListener(keyListener);
		TextField2.setBounds(113, 65, 76, 20);
		contentPanel.add(TextField2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Submit");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try{createOrder();}
						catch(java.lang.NumberFormatException e){ JOptionPane.showMessageDialog(null, "Please input data in the fields");}
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);

				{
					JButton cancelButton = new JButton("Cancel");
					cancelButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dispose();
						}
					});
					cancelButton.setActionCommand("Cancel");
					buttonPane.add(cancelButton);
				}
			}

		}
	}
	protected void createOrder(){
		try{
		Long credSum = Long.parseLong(TextField1.getText());
		Long salary = Long.parseLong(TextField2.getText());
		Boolean guarantee = CheckBox.isSelected();
		Long inn = Long.parseLong(login);
		Client client = facade.findByInnClient(inn);
		CreditProgram crPr = facade.findByIdCreditProgram(table_click);
		if (credSum>=crPr.getMinSum() && credSum<=crPr.getMaxSum() ){
		Order order = new Order(client, crPr, guarantee,credSum,salary);
		facade.makePersistentOrder(order);
		JOptionPane.showMessageDialog(null, "Your order sent to our manager. Thanks.");}
		else{JOptionPane.showMessageDialog(null, "Requires sum of money is unprovided for this credit program",
				"Error", JOptionPane.ERROR_MESSAGE);}}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
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
