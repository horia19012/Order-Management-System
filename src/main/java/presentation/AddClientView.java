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
 * AddClient view is a class that extends JFrame.It will have the role of showing an interface with fields for all the 
 * client atributes. It allows the user to add the client to the table
 *
 */
public class AddClientView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField addressTxt;
	private JTextField emailTxt;
	private JTextField phNbTxt;
	private JButton addBtn;
	private JButton backBtn;

	public AddClientView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 477);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("ADD CLIENT");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(280, 10, 202, 22);
		contentPane.add(titleLabel);

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

		addBtn = new JButton("ADD");
		addBtn.setFont(new Font("Arial", Font.BOLD, 23));
		addBtn.setBounds(286, 282, 212, 56);
		addBtn.addActionListener(this);
		contentPane.add(addBtn);

		backBtn = new JButton("Back");
		backBtn.setFont(new Font("Arial", Font.BOLD, 23));
		backBtn.setBounds(340, 350, 100, 30);
		backBtn.addActionListener(this);
		contentPane.add(backBtn);

		this.setVisible(true);
	}
	/**
	 * Shows a window with a message
	 * @param errMessage - the message that is shown in a new window
	 * 
	 */
	
	public void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}
	
	/**
	 * The method actionPerformed gets the event source in our case the button
	 * pressed and has a certain objective for each one of them
	 * 
	 * @param ActionEvent e
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == addBtn) {
			Client client = new Client(nameTxt.getText().toString(), addressTxt.getText().toString(),
					emailTxt.getText().toString(), phNbTxt.getText().toString());

			ClientBLL cBLL = new ClientBLL();
			try {
				if (client.getName().equals("") || client.getAddress().equals("")) {
					throw new Exception("Empty slots!");
				}
				cBLL.insertClient(client);
				this.showMessage("Added Successfully!");
				this.dispose();
				ClientsView cw = new ClientsView(0);
			} catch (IllegalArgumentException ex) {
				this.showMessage("Invalid E-mail/Phone Number!");
			} catch (Exception ex) {
				this.showMessage("Fill all the fields!");
			}

		} else if (e.getSource() == backBtn) {
			try {
				this.dispose();
				ClientsView cw = new ClientsView(0);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
