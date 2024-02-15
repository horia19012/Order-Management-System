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
import dao.ProductDAO;
import model.Product;

/**
 * GUI for updating a product from products table
 * 
 *
 */
public class UpdateProductsView extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JTextField descriptionTxt;
	private JTextField priceTxt;
	private JTextField quantityTxt;
	private JButton updateBtn;
	private JButton backBtn;
	private int idToUpdate;

	public UpdateProductsView(int idToUpdate) {
		this.idToUpdate = idToUpdate;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 477);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 128, 64));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel titleLabel = new JLabel("UPDATE PRODUCT");
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

		JLabel descriptionLabel = new JLabel("Price:");
		descriptionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		descriptionLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		descriptionLabel.setBounds(120, 164, 100, 22);
		contentPane.add(descriptionLabel);

		JLabel priceLabel = new JLabel("Description:");
		priceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		priceLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		priceLabel.setBounds(120, 127, 100, 22);
		contentPane.add(priceLabel);

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

		try {
			ProductDAO pDAO = new ProductDAO();
			Product product = new Product();
			ResultSet res = pDAO.findById(idToUpdate, product);
			nameTxt.setText(product.getName());
			descriptionTxt.setText(product.getDescription());
			priceTxt.setText(String.valueOf(product.getPrice()));
			quantityTxt.setText(String.valueOf(product.getQuantity()));
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
	 * Opens new window with error message
	 * 
	 * @param errMessage
	 */
	public void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	/**
	 * gives the buttons a role depending on the name
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == updateBtn) {
			try {
				Product product = new Product(idToUpdate, nameTxt.getText().toString(),
						descriptionTxt.getText().toString(), Double.parseDouble(priceTxt.getText().toString()),
						Integer.parseInt(quantityTxt.getText().toString()));
//			ClientDAO cDAO = new ClientDAO();
//			cDAO.updateFromTable(client);
//			ClientBLL cBLL = new ClientBLL();
				ProductDAO pDAO = new ProductDAO();

//				cBLL.updateClient(client);
				pDAO.updateFromTable(product);
				this.showMessage("Updated Successfully!");
				this.dispose();
				ProductsView pw = new ProductsView(0);
			} catch (NumberFormatException ex) {
				this.showMessage("Invalid price/quantity!");
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else if (e.getSource() == backBtn) {
			this.dispose();
			new ProductsView(0);
		}
	}
}
