package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;
import model.Order;
import model.Product;

/**
 * 
 * The OrderDAO class is similar to ClientDAO and ProductDAO but includes its
 * own specialized methods and more complex queries specifically designed for
 * managing orders.
 *
 */
public class OrderDAO extends AbstractDAO<Order> {

	protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());
	/**
	 * the insert query
	 */
	private static final String insertStatementString = "INSERT INTO orders (client_id, product_id, quantity) VALUES (?,?,?)";
	/**
	 * and inner join query so the table in the interface has more details than the
	 * table columns
	 */
	private static final String showTable = "SELECT orders.id, clients.name AS client_name, products.name AS product_name, orders.quantity,clients.address,clients.phoneNumber FROM orders INNER JOIN clients ON orders.client_id = clients.id INNER JOIN products ON orders.product_id = products.id";
	String showBill = "SELECT  p.name AS product_name, p.price AS product_price, o.quantity  "
			+ "        FROM orders o  " + "        INNER JOIN clients c ON o.client_id = c.id "
			+ "        INNER JOIN products p ON o.product_id = p.id" + "		where c.id = ?";

	/**
	 * Returns the ResultSet of the query but it also changes the atributes values
	 * of the order parameter
	 * 
	 * @param orderId
	 * @param order
	 * @return ResultSet
	 */
	public ResultSet findById(int orderId, Order order) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		String query = createSelectQuery("id");
		try {
			findStatement = dbConnection.prepareStatement(query);
			findStatement.setInt(1, orderId);
			rs = findStatement.executeQuery();
			if (rs.next()) {
				int order_id = rs.getInt("id");
				int client_id = rs.getInt("client_id");
				int product_id = rs.getInt("product_id");
				int quantity = rs.getInt("quantity");

				// Set the properties of the Client object passed as a parameter
				order.setOrder_id(order_id);
				order.setClient_id(client_id);
				order.setProduct_id(product_id);
				order.setQuantity(quantity);
			}
			rs.previous();
//	        ReflectionExample.retrieveProperties(client);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:findById " + e.getMessage());
		} finally {
			// Close the statement and connection objects
//	        ConnectionFactory.close(findStatement);
//	        ConnectionFactory.close(dbConnection);
		}

		return rs;
	}

	/**
	 * Returns an int value of the insertedId. It checks that the quantity sent as
	 * parameter has a smaller value than the object found in the products table so
	 * it can be added to orders,and then inserted
	 * 
	 * @param c        -a Client
	 * @param p        -a Product
	 * @param quantity
	 * @return
	 * @throws Exception
	 */
	public static int insert(Client c, Product p, int quantity) throws Exception {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;

		try {
			System.out.println(p.getQuantity());
			if (p.getQuantity() >= quantity) {
				Order o = new Order();
				insertStatement = dbConnection.prepareStatement(AbstractDAO.createInsertQuery(o),
						Statement.RETURN_GENERATED_KEYS);
				insertStatement.setInt(1, c.getId());
				insertStatement.setInt(2, p.getId());
				insertStatement.setInt(3, quantity);

				insertStatement.executeUpdate();

				ResultSet rs = insertStatement.getGeneratedKeys();
				if (rs.next()) {
					insertedId = rs.getInt(1);
				}
			} else {
				throw new Exception("Not enough quantity in deposit!");
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

	/**
	 * ResultSet of the query of inner join
	 */
	public ResultSet showTable() {
		Connection dbConnection = ConnectionFactory.getConnection();

		Statement showTableStatement = null;
		try {
			showTableStatement = dbConnection.createStatement();
			ResultSet res = showTableStatement.executeQuery(showTable);
			return res;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:showTable " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
//			ConnectionFactory.close(showTableStatement);
//			ConnectionFactory.close(dbConnection);
		}
	}

	/**
	 * Returns a ResultSet of the orders of the client id sent as parameter
	 * 
	 * @param id
	 * @return ResultSet
	 */
	public ResultSet showBill(int id) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement showTableStatement = null;
		try {
			showTableStatement = dbConnection.prepareStatement(showBill);
			showTableStatement.setInt(1, id);
			System.out.println(showTableStatement);
			ResultSet res = showTableStatement.executeQuery();
			return res;
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "OrderDAO:showBill " + e.getMessage());
			e.printStackTrace();
			return null;
		} finally {
//			ConnectionFactory.close(showTableStatement);
//			ConnectionFactory.close(dbConnection);
		}
	}

}