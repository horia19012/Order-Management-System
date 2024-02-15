package presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import dao.OrderDAO;
import net.proteanit.sql.DbUtils;
/**
 * 
 * Shows the Bill for the chosen id
 *
 */
public class BillView extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JButton back;

	public BillView(Integer id) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 768, 469);
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel billLabel = new JLabel("Bill for:");
		billLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		billLabel.setBounds(176, 25, 98, 22);
		contentPane.add(billLabel);
		
		back =new JButton("BACK");
		back.setBounds(176, 50, 99, 22);
		back.addActionListener(this);
		contentPane.add(back);
		
		

		JLabel idLabel = new JLabel(String.valueOf(id));
		idLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		idLabel.setBounds(259, 25, 163, 21);
		contentPane.add(idLabel);
		double totalBill = 0;
		table = new JTable();
		try {
			OrderDAO oDAO = new OrderDAO();
			ResultSet rs = oDAO.showBill(id);
			table.setModel(DbUtils.resultSetToTableModel(rs));

			ResultSet rs1 = oDAO.showBill(id);
			while (rs1.next()) {
				double price = rs1.getDouble("product_price");
				int quantity = rs1.getInt("quantity");
				double orderPrice = price * quantity;

				// Accumulate the order price to the total bill
				totalBill += orderPrice;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		JLabel total = new JLabel("TOTAL: " + String.valueOf(totalBill));
		total.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		total.setBounds(500, 25, 163, 21);
		contentPane.add(total);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(102, 87, 556, 318);
		contentPane.add(scrollPane);
	}
	/**
	 * Gets the source and does operation
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == back) {
			this.dispose();
			new ClientsView(0);

		}
	}
}
