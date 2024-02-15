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

/**
 * The ClientDAO class contains methods specific to client-related operations, such as findById and insert. These methods are 
 * implemented here to work specifically with the ClientDAO functionality 
 * and cannot be generalized or reused for other purposes.
 * 
 *
 */
public class ClientDAO extends AbstractDAO<Client>{

	protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
	/**
	 * It returns a ResultSet made of the row in the table with the id provided as parameter.At the same time it 
	 * changes the value of the client parameter to the values given in the table
	 * @param clientId
	 * @param client
	 * @return
	 */
	public  ResultSet findById(int clientId, Client client) {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement findStatement = null;
		ResultSet rs = null;
		try {
			findStatement = dbConnection.prepareStatement(createSelectQuery("id"));
			findStatement.setLong(1, clientId);
			rs = findStatement.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");

				// Set the properties of the Client object passed as a parameter
				client.setId(clientId);
				client.setName(name);
				client.setAddress(address);
				client.setEmail(email);
				client.setPhoneNumber(phoneNumber);
			}
			rs.previous();
//	        ReflectionExample.retrieveProperties(client);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
		} finally {
			// Close the statement and connection objects
//	        ConnectionFactory.close(findStatement);
//	        ConnectionFactory.close(dbConnection);
		}

		return rs;
	}
	/**
	 * It inserts a client to the table and returns the insertedId
	 * @param client
	 * @return int
	 */
	public static int insert(Client client) {

		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement insertStatement = null;
		int insertedId = -1;
		try {
			insertStatement = dbConnection.prepareStatement(AbstractDAO.createInsertQuery(client), Statement.RETURN_GENERATED_KEYS);
			insertStatement.setString(1, client.getName());
			insertStatement.setString(2, client.getAddress());
			insertStatement.setString(3, client.getEmail());
			insertStatement.setString(4, client.getPhoneNumber());
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			if (rs.next()) {
				insertedId = rs.getInt(1);
			}
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
		} finally {
			ConnectionFactory.close(insertStatement);
			ConnectionFactory.close(dbConnection);
		}
		return insertedId;
	}


}
