package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import dao.ProductDAO;
import model.Product;

/**
 * 
 * AddProduct view is a class that extends JFrame.It will have the role of
 * showing an interface with fields for all the product atributes. It allows the
 * user to add the client to the table
 *
 */
public class AddProductView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField descriptionTxt;
	private JTextField priceTxt;
	private JTextField quantityTxt;
	private JButton addBtn;
	private JButton backBtn;

	public AddProductView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 477);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("ADD PRODUCT");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(270, 10, 202, 22);
		contentPane.add(titleLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		nameLabel.setBounds(135, 95, 85, 22);
		contentPane.add(nameLabel);

		JLabel priceLabel = new JLabel("Price:");
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		priceLabel.setBounds(135, 164, 85, 22);
		contentPane.add(priceLabel);

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descriptionLabel.setBounds(120, 127, 100, 22);
		contentPane.add(descriptionLabel);

		JLabel quantityLabel = new JLabel("Quantity:");
		quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		quantityLabel.setBounds(65, 198, 155, 22);
		contentPane.add(quantityLabel);

		nameTxt = new JTextField();
		nameTxt.setBounds(234, 98, 365, 19);
		contentPane.add(nameTxt);
		nameTxt.setColumns(10);

		descriptionTxt = new JTextField();
		descriptionTxt.setColumns(10);
		descriptionTxt.setBounds(234, 132, 365, 19);
		contentPane.add(descriptionTxt);

		priceTxt = new JTextField();
		priceTxt.setColumns(10);
		priceTxt.setBounds(234, 164, 365, 19);
		contentPane.add(priceTxt);

		quantityTxt = new JTextField();
		quantityTxt.setColumns(10);
		quantityTxt.setBounds(234, 203, 365, 19);
		contentPane.add(quantityTxt);

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
	 * shows a window with an error message
	 * 
	 * @param errMessage
	 */
	public void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	/**
	 * The method actionPerformed gets the event source in our case the button
	 * pressed and has a certain objective for each one of them
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == addBtn) {
			try {
				Product product = new Product(nameTxt.getText().toString(), descriptionTxt.getText().toString(),
						Double.parseDouble(priceTxt.getText().toString()),
						Integer.parseInt(quantityTxt.getText().toString()));

				if (product.getName().equals("")) {
					throw new Exception("Empty name slot!");
				}
				ProductDAO pDAO = new ProductDAO();

				pDAO.insert(product);
				this.showMessage("Added Successfully!");
				this.dispose();
				ProductsView pw = new ProductsView(0);
			} catch (NumberFormatException ex) {
				this.showMessage("Invalid price/quantity!");
			} catch (Exception ex) {
				this.showMessage("Empty Name field!");
			}

		} else if (e.getSource() == backBtn) {
			try {
				this.dispose();
				ProductsView cw = new ProductsView(0);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
