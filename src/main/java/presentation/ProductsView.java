package presentation;

import java.awt.Choice;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import dao.AbstractDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;
//import database.DBConnection;
//import net.proteanit.sql.DbUtils;

/**
 * 
 * GUI for showing Products, it has getters and setters for each part of the GUI
 *
 */
public class ProductsView extends JFrame implements ActionListener {

	private Choice chosenId = new Choice();
	private JButton show;
	private JButton remove;
	private JButton back;
	private JButton add;
	private JTable table;
	private JButton update;
	private Client selectedClient;
	private JButton selectBtn;
	private Product selectedProduct;
	private JTextField quantityTxt;

	public ProductsView(int creatingOrder) {
		setLayout(null);
		this.setLocation(200, 0);
		this.getContentPane().setBackground(new Color(255, 128, 0));

		JLabel searchLabel = new JLabel("Search by ID:");
		searchLabel.setBounds(100, 0, 100, 100);
		searchLabel.setForeground(Color.white);
		this.add(searchLabel);

		chosenId.setBounds(200, 40, 100, 100);
		this.add(chosenId);

		show = new JButton("Search");
		show.setBounds(330, 40, 95, 20);
		show.addActionListener(this);
		this.add(show);

		if (creatingOrder == 0) {
			remove = new JButton("Remove");
			remove.setBounds(1000, 190, 95, 20);
			remove.addActionListener(this);
			this.add(remove);

			add = new JButton("Add");
			add.setBounds(1000, 90, 95, 20);
			add.addActionListener(this);
			this.add(add);

			update = new JButton("Update");
			update.setBounds(1000, 140, 95, 20);
			update.addActionListener(this);
			this.add(update);
		} else {
			selectBtn = new JButton("Select");
			selectBtn.setBounds(1000, 140, 95, 20);
			selectBtn.addActionListener(this);
			this.add(selectBtn);

			JLabel quantityLabel = new JLabel("Insert quantity:");
			quantityLabel.setBounds(955, 170, 95, 20);
			this.add(quantityLabel);

			quantityTxt = new JTextField();
			quantityTxt.setBounds(1050, 170, 40, 20);
			this.add(quantityTxt);
		}

		back = new JButton("Back");
		back.setBounds(1000, 240, 95, 20);
		back.addActionListener(this);
		this.add(back);

		try {
			ProductDAO pDAO = new ProductDAO();

			List<String> ids = pDAO.getIdsFromTable();
			for (String i : ids) {
				chosenId.add(i);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		table = new JTable();
		try {
			ProductDAO pDAO = new ProductDAO();
			table.setModel(pDAO.generateTable(pDAO.createObjects(pDAO.showTable())));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(0, 100, 950, 600);

		this.add(scroll);

		this.setSize(1200, 1200);
		this.setVisible(true);
	}

	/**
	 * Pops a new window with an error message sent as parameter
	 * 
	 * @param errMessage
	 */
	public void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	/**
	 * Gives a performed action for each button in the GUI
	 * 
	 * @param e-ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == show) {

			try {
				ProductDAO pDAO = new ProductDAO();
				ResultSet res = pDAO.findById(Integer.parseInt(chosenId.getSelectedItem()), new Product());
				table.setModel(pDAO.generateTable(pDAO.createObjects(res)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == add) {
			this.dispose();
			AddProductView apv = new AddProductView();
		} else if (e.getSource() == remove) {
			try {

				ProductDAO pDAO = new ProductDAO();
				pDAO.deleteFromTable(chosenId);
				this.dispose();
				ProductsView pw = new ProductsView(0);
//				pw.showMessage("Deleted Successfully!");
				// Update your table accordingly
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == selectBtn) {
			try {
				ProductDAO pDAO = new ProductDAO();
				selectedProduct = new Product();
				ResultSet res = pDAO.findById(Integer.parseInt(chosenId.getSelectedItem()), selectedProduct);
				selectedProduct.setId(Integer.parseInt(chosenId.getSelectedItem()));
				try {
					int q = Integer.parseInt(quantityTxt.getText().toString());
					OrderDAO oDAO = new OrderDAO();

					if (selectedProduct.getQuantity() >= q) {
						oDAO.insert(selectedClient, selectedProduct, q);
						selectedProduct.setQuantity(selectedProduct.getQuantity() - q);
						pDAO.updateFromTable(selectedProduct);
					} else {
						throw new Exception();
					}

					this.showMessage("Order added successfully!");
					this.dispose();
					new OrdersView();
				} catch (NumberFormatException ex) {
					this.showMessage("Invalid quantity!");
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				this.showMessage("Not enough quantity in deposit!");
			}

		} else if (e.getSource() == update)

		{
			this.dispose();
			try {
				new UpdateProductsView(Integer.parseInt(chosenId.getSelectedItem()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			this.dispose();
			MainView mw = new MainView();
			Controller controller = new Controller(mw);
		}
	}
	
	public Client getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(Client selectedClient) {
		this.selectedClient = selectedClient;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public JTextField getQuantityTxt() {
		return quantityTxt;
	}

	public void setQuantityTxt(JTextField quantityTxt) {
		this.quantityTxt = quantityTxt;
	}
}
