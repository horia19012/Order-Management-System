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

import dao.OrderDAO;
import model.Order;
//import database.DBConnection;
import net.proteanit.sql.DbUtils;

/**
 * 
 * Graphical Interface for showing the orders
 *
 */
public class OrdersView extends JFrame implements ActionListener {

	private Choice chosenId = new Choice();
	private JButton show;
	private JButton remove;
	private JButton back;
	private JButton add;
	private JTable table;
	private JButton update;

	public OrdersView() {
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

		remove = new JButton("Remove");
		remove.setBounds(1000, 190, 95, 20);
		remove.addActionListener(this);
		this.add(remove);

		add = new JButton("Add");
		add.setBounds(1000, 90, 95, 20);
		add.addActionListener(this);
		this.add(add);

		back = new JButton("Back");
		back.setBounds(1000, 240, 95, 20);
		back.addActionListener(this);
		this.add(back);

		try {
			OrderDAO oDAO = new OrderDAO();

			List<String> ids = oDAO.getIdsFromTable();
			for (String i : ids) {
				chosenId.add(i);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		table = new JTable();

		try {

			OrderDAO oDAO = new OrderDAO();
			table.setModel(DbUtils.resultSetToTableModel(oDAO.showTable()));
//			table.setModel(oDAO.generateTable(oDAO.createObjects(oDAO.showTable())));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBounds(0, 100, 950, 900);

		this.add(scroll);

		this.setSize(1200, 1200);
		this.setVisible(true);
	}

	/**
	 * Pops new window with an error Message sent as parameter
	 * 
	 * @param errMessage
	 */
	public void showMessage(String errMessage) {
		JOptionPane.showMessageDialog(this, errMessage);
	}

	/**
	 * it adds a performed action for each button
	 * 
	 * @param e-ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == show) {

			try {
				OrderDAO oDAO = new OrderDAO();
				ResultSet res = oDAO.findById(Integer.parseInt(chosenId.getSelectedItem()), new Order());
//				table.setModel(DbUtils.resultSetToTableModel(res));
				table.setModel(oDAO.generateTable(oDAO.createObjects(res)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else if (e.getSource() == add) {
			this.dispose();
			try {
				new ClientsView(1);

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == remove) {
			try {

				OrderDAO oDAO = new OrderDAO();
				oDAO.deleteFromTable(chosenId);
				this.dispose();
				OrdersView cw = new OrdersView();
				cw.showMessage("Deleted Successfully!");
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} else if (e.getSource() == back) {
			this.dispose();
			MainView mw = new MainView();
			Controller controller = new Controller(mw);
		}
	}
}
