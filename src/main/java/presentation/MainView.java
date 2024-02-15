package presentation;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * Main View: It has 3 options:CLIENTS,PRODUCTS,ORDERS press the button of the
 * option wanted to pop a new window with the table desired
 * 
 */
public class MainView extends JFrame {

	private JPanel contentPane;
	JButton clientsBtn;
	JButton ordersBtn;
	JButton productsBtn;

	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 100, 494, 532);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("ORDERS MANAGEMENT SYSTEM");
		titleLabel.setFont(new Font("Cambria Math", Font.BOLD, 29));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(0, 10, 470, 61);
		contentPane.add(titleLabel);

		clientsBtn = new JButton("CLIENTS");
		clientsBtn.setFont(new Font("Arial", Font.BOLD, 20));
		clientsBtn.setBounds(98, 152, 275, 78);
		contentPane.add(clientsBtn);

		productsBtn = new JButton("PRODUCTS");
		productsBtn.setFont(new Font("Arial", Font.BOLD, 20));
		productsBtn.setBounds(98, 254, 275, 78);
		contentPane.add(productsBtn);

		ordersBtn = new JButton("ORDERS");
		ordersBtn.setFont(new Font("Arial", Font.BOLD, 20));
		ordersBtn.setBounds(98, 353, 275, 78);
		contentPane.add(ordersBtn);

		JLabel menuLabel = new JLabel("Option Menu");
		menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		menuLabel.setFont(new Font("Arial", Font.BOLD, 18));
		menuLabel.setBounds(170, 107, 139, 32);
		contentPane.add(menuLabel);
		this.setVisible(true);
	}

	/**
	 * method to add actionListener for clientsButton
	 * 
	 * @param x- ActionListener
	 */
	public void addClientsBtnActionListener(ActionListener x) {
		this.clientsBtn.addActionListener(x);
	}

	/**
	 * method to add actionListener for ProductsButton
	 * 
	 * @param x
	 */
	public void addProductsBtnActionListener(ActionListener x) {
		this.productsBtn.addActionListener(x);
	}

	/**
	 * method to add actionListener for OrdersButton
	 * 
	 * @param x
	 */
	public void addOrdersBtnActionListener(ActionListener x) {
		this.ordersBtn.addActionListener(x);
	}

}
