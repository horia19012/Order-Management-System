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

import dao.ClientDAO;
import model.Client;
import model.Product;
//import net.proteanit.sql.DbUtils;

/**
 * 
 * Interface for showing clients
 *
 */
public class ClientsView extends JFrame implements ActionListener {

	private Choice chosenId = new Choice();
	private JButton show;
	private JButton remove;
	private JButton bill;
	private JButton back;
	private JButton add;
	private JTable table;
	private JButton update;
	private JButton selectBtn;

	private Client searchedClient;
	private Product searchedProduct;

	public ClientsView(int creatingOrder) {
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

		back = new JButton("Back");
		back.setBounds(1000, 240, 95, 20);
		back.addActionListener(this);
		this.add(back);
		if (creatingOrder == 1) {
			selectBtn = new JButton("Select");
			selectBtn.setBounds(1000, 140, 95, 20);
			selectBtn.addActionListener(this);
			this.add(selectBtn);
		} else {
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

			bill = new JButton("Bill");
			bill.setBounds(1000, 300, 95, 20);
			bill.addActionListener(this);
			this.add(bill);

		}
		try {
			ClientDAO cDAO = new ClientDAO();

			List<String> ids = cDAO.getIdsFromTable();
			for (String i : ids) {
				chosenId.add(i);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		table = new JTable();

		try {

			ClientDAO cDAO = new ClientDAO();
			table.setModel(cDAO.generateTable(cDAO.createObjects(cDAO.showTable())));
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
	 * shows Error message
	 * 
	 * @param errMessage
	 */
	public static void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(null, errMessage);
	}

	/**
	 * The method actionPerformed gets the event source in our case the button
	 * pressed and has a certain objective for each one of them
	 * 
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == show) {

			try {
				ClientDAO cDAO = new ClientDAO();
				searchedClient = new Client();
				ResultSet res = cDAO.findById(Integer.parseInt(chosenId.getSelectedItem()), searchedClient);
//				ReflectionExample.retrieveProperties(searchedClient);
				table.setModel(cDAO.generateTable(cDAO.createObjects(res)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == add) {
			this.dispose();
			try {
				new AddClientView();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == selectBtn) {
			ClientDAO cDAO = new ClientDAO();
			searchedClient = new Client();
			ResultSet res = cDAO.findById(Integer.parseInt(chosenId.getSelectedItem()), searchedClient);

			ProductsView pv = new ProductsView(1);
			pv.setSelectedClient(searchedClient);
			this.dispose();

		} else if (e.getSource() == remove) {
			try {

				ClientDAO cDAO = new ClientDAO();
				cDAO.deleteFromTable(chosenId);
				this.dispose();
				ClientsView cw = new ClientsView(0);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else if (e.getSource() == update) {
			this.dispose();
			try {
				new UpdateClientView(Integer.parseInt(chosenId.getSelectedItem()));

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == back) {
			this.dispose();
			MainView mw = new MainView();
			Controller controller = new Controller(mw);
		} else if (e.getSource() == bill) {
			this.dispose();
			new BillView(Integer.parseInt(chosenId.getSelectedItem()));

		}
	}
}
