package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import rmiclient.RMIFacade;

import bussiness.Contant;

import bussiness.IFacade;


import entities.CreditProgram;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class PreviewWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JTextPane textPane;
	private IFacade facade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PreviewWindow dialog = new PreviewWindow();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws RemoteException
	 */
	public DefaultTableModel creditProgramTable() throws RemoteException {

		DefaultTableModel model1 = new DefaultTableModel();
		model1.setColumnIdentifiers(new Object[] {"ID", "NAME", "SHORT_DESC",
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
			model1.addRow(data);
		}
		return model1;
	}

	public PreviewWindow() throws RemoteException {
		setTitle("CREDIT PROGRAMS PREVIEW");

		this.facade = RMIFacade.getFacade();

		setBounds(100, 100, 640, 306);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 604, 97);
			contentPanel.add(scrollPane);
			{
				DefaultTableModel T;
				T = creditProgramTable();
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
					
						try {
							int row = table.getSelectedRow();
							Long table_click = (Long)(table.getModel().getValueAt(row, 0));
							List<CreditProgram> programs;
							
								programs = facade.findAllCreditPrograms();
							
							for (CreditProgram cp : programs) {
								if (cp.getId() == table_click) {
									textPane.setText(cp.getLongDescription());

								}
							}}
						 catch (RemoteException e) {
								
								e.printStackTrace();
							}
						 catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex);
							}
						} 
						

					
				});
				table.setModel(T);
				scrollPane.setViewportView(table);
			}
		}
		{
			JLabel lblNewLabel = new JLabel("Full description");
			lblNewLabel.setBounds(237, 114, 133, 14);
			contentPanel.add(lblNewLabel);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 129, 604, 64);
			contentPanel.add(scrollPane);
			{
				textPane = new JTextPane();
				scrollPane.setViewportView(textPane);
			}
		}
		{
			JLabel lblNewLabel_1 = new JLabel(
					"If you have any questions please contact us: \r\ntel.(044)999-99-99");
			lblNewLabel_1.setBounds(169, 204, 368, 14);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Close");
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
