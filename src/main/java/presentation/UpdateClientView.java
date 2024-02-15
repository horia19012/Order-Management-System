package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import bll.ClientBLL;
import dao.ClientDAO;
import model.Client;
/**
 * 
 * GUI for updating a Client from the clients table
 *
 */
public class UpdateClientView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField addressTxt;
	private JTextField emailTxt;
	private JTextField phNbTxt;
	private JButton updateBtn;
	private JButton backBtn;
	private int idToUpdate;


	public UpdateClientView(int idToUpdate) {
		this.idToUpdate = idToUpdate;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 477);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("UPDATE CLIENT");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(54, 10, 202, 22);
		contentPane.add(titleLabel);

		JLabel idLabel = new JLabel("ID:");
		idLabel.setHorizontalAlignment(SwingConstants.CENTER);
		idLabel.setFont(new Font("Arial", Font.BOLD, 18));
		idLabel.setBounds(237, 10, 93, 22);
		contentPane.add(idLabel);

		JLabel id = new JLabel(String.valueOf(idToUpdate));
		id.setHorizontalAlignment(SwingConstants.LEFT);
		id.setFont(new Font("Arial", Font.BOLD, 18));
		id.setBounds(324, 10, 142, 22);
		contentPane.add(id);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLabel.setBounds(135, 95, 85, 22);
		contentPane.add(nameLabel);

		JLabel emailLabel = new JLabel("E-Mail:");
		emailLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		emailLabel.setBounds(135, 164, 85, 22);
		contentPane.add(emailLabel);

		JLabel addressLabel = new JLabel("Address:");
		addressLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		addressLabel.setBounds(135, 127, 85, 22);
		contentPane.add(addressLabel);

		JLabel phNumberLabel = new JLabel("Phone Number:");
		phNumberLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phNumberLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		phNumberLabel.setBounds(65, 198, 155, 22);
		contentPane.add(phNumberLabel);

		nameTxt = new JTextField();
		nameTxt.setBounds(234, 98, 365, 19);
		contentPane.add(nameTxt);
		nameTxt.setColumns(10);

		addressTxt = new JTextField();
		addressTxt.setColumns(10);
		addressTxt.setBounds(234, 132, 365, 19);
		contentPane.add(addressTxt);

		emailTxt = new JTextField();
		emailTxt.setColumns(10);
		emailTxt.setBounds(234, 164, 365, 19);
		contentPane.add(emailTxt);

		phNbTxt = new JTextField();
		phNbTxt.setColumns(10);
		phNbTxt.setBounds(234, 203, 365, 19);
		contentPane.add(phNbTxt);

		try {
			ClientDAO cDAO = new ClientDAO();
			Client client = new Client();
			ResultSet res = cDAO.findById(idToUpdate, client);
			nameTxt.setText(client.getName());
			addressTxt.setText(client.getAddress());
			emailTxt.setText(client.getEmail());
			phNbTxt.setText(client.getPhoneNumber());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		updateBtn = new JButton("UPDATE");
		updateBtn.setFont(new Font("Arial", Font.BOLD, 23));
		updateBtn.setBounds(286, 282, 212, 56);
		updateBtn.addActionListener(this);
		contentPane.add(updateBtn);
		
		backBtn = new JButton("Back");
		backBtn.setFont(new Font("Arial", Font.BOLD, 23));
		backBtn.setBounds(340, 350, 100, 30);
		backBtn.addActionListener(this);
		contentPane.add(backBtn);
		this.setVisible(true);
	}
	/**
	 * New window with error message
	 * @param errMessage
	 */
	public void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}
	/**
	 * Gives each button a role
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == updateBtn) {
			Client client = new Client(idToUpdate, nameTxt.getText().toString(), addressTxt.getText().toString(),
					emailTxt.getText().toString(), phNbTxt.getText().toString());
//			ClientDAO cDAO = new ClientDAO();
//			cDAO.updateFromTable(client);
			ClientBLL cBLL = new ClientBLL();
			try {
				cBLL.updateClient(client);
				this.showMessage("Updated Successfully!");
				this.dispose();
				ClientsView cw = new ClientsView(0);
			} catch (IllegalArgumentException ex) {
				this.showMessage("Invalid E-mail/Phone Number!");
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}else if(e.getSource()==backBtn) {
			this.dispose();
			new ClientsView(0);
		}
	}
}
