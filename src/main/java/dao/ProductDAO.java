package dao;

import java.awt.Choice;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;
import model.Client;
import model.Product;

/**
 * The ProductDAO class contains methods specific to product-related operations,
 * such as findById and insert.
 * 
 *
 */
public class ProductDAO extends AbstractDAO<Product> {

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

	public ResultSet findById(int productId, Product product) {
//		product = null;

		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(createSelectQuery("id"));
			findStatement.setLong(1, productId);
			rs = findStatement.executeQuery();
			rs.next();

			String name = rs.getString("name");
			String description = rs.getString("description");
			double price = rs.getDouble("price");
			int quantity = rs.getInt("quantity");

			product.setName(name);
			product.setDescription(description);
			product.setPrice(price);
			product.setQuantity(quantity);

			rs.previous();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:findById " + e.getMessage());
		} finally {
//	        ConnectionFactory.close(findStatement);
//	        ConnectionFactory.close(dbConnection);
		}

		return rs;
	}

	public static int insert(Product product) {

		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(AbstractDAO.createInsertQuery(product),
					Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, product.getName());
			insertStatement.setString(2, product.getDescription());
			insertStatement.setDouble(3, product.getPrice());
			insertStatement.setInt(4, product.getQuantity());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ProductDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}

}
