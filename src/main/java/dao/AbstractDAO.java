package dao;

import java.awt.Choice;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.table.DefaultTableModel;

import connection.ConnectionFactory;
import presentation.ClientsView;

/**
 * 
 * This is the super class AbstractDAO that has methods for every DAO in the
 * project. It creates a query for each operation no matter the table and also
 * statements to be executed.
 *
 * @param <T>
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	/**
	 * Returns a sql query that has form of "SELECT * FROM *table name*s where
	 * *parameter*=?" the "s" after the table name is essential because the classes
	 * are named "client" for example and the table is named "clients"
	 * 
	 * @param field
	 * @return -query string
	 */
	public String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append("s WHERE " + field + " =?");
		return sb.toString();
	}

	// private final static String deleteStatementString = "delete from clients
	// where id= ?";
	/**
	 * Returns a sql query that deletes a row from a table that has a specific id
	 * 
	 * @return -the query
	 */
	public String createDeleteQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("Delete from ");
		sb.append(type.getSimpleName() + "s ");
		sb.append("where id=?");
		return sb.toString();
	}

	/**
	 * Returns a query for showing all the data from a table
	 * 
	 * @return -the String query
	 */
	public String showTableQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT");
		sb.append(" * ");
		sb.append("FROM ");
		sb.append(type.getSimpleName() + "s");
		return sb.toString();
	}

//	"INSERT INTO products (name,description,price,quantity)"

	/**
	 * Creates an insert query that can be used for every table case
	 * 
	 * @param o -type Object
	 * @return - String of query
	 */
	public static String createInsertQuery(Object o) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(o.getClass().getSimpleName() + "s (");

		boolean firstField = true;
		for (Field field : o.getClass().getDeclaredFields()) {
			field.setAccessible(true);

			try {

				if (!firstField) {
					sb.append(field.getName() + ",");
				} else {
					firstField = false;
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}

		}
		int lastIndex = sb.length() - 1;
		if (lastIndex >= 0) {
			sb.deleteCharAt(lastIndex);
		}
		firstField = true;
		sb.append(")VALUES (");

		for (Field field : o.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (!firstField) {
				sb.append("?,");
			} else {
				firstField = false;
			}
		}
		lastIndex = sb.length() - 1;
		if (lastIndex >= 0) {
			sb.deleteCharAt(lastIndex);
		}

		sb.append(")");
		System.out.println(sb);
		return sb.toString();
	}

	// "UPDATE clients SET name=?,address=?,email=?,phoneNumber=? where id=?";

	/**
	 * Returns an update query that works for every table case
	 * 
	 * @param o -type Object
	 * @return - String of query
	 */
	public String createUpdateQuery(Object o) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE " + type.getSimpleName() + "s SET ");
		boolean firstField = true;
		for (Field field : o.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (!firstField) {
				sb.append(field.getName() + "=?,");
			} else {
				firstField = false;
			}

		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" where id=?");
		System.out.println(sb);
		return sb.toString();

	}

	/**
	 * Gets the ids from the table in the form of a list of Strings
	 * 
	 * @return -List<String>
	 */
	public List<String> getIdsFromTable() {
		Connection dbConnection = ConnectionFactory.getConnection();
		PreparedStatement showTableStatement = null;
		List<String> chosenIds = new ArrayList();
		String query = showTableQuery();

		try {
			showTableStatement = dbConnection.prepareStatement(query);
			ResultSet res = showTableStatement.executeQuery();

			while (res.next()) {
				chosenIds.add(res.getString("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(showTableStatement);
			ConnectionFactory.close(dbConnection);
		}
		return chosenIds;
	}

	/**
	 * It generates the table in the console based on a list of Objects. First it
	 * retrieves the fields of the table then it populates it by going through the
	 * object lists
	 * 
	 * @param list of Objects
	 */
	public DefaultTableModel generateTable(List<?> list) {
	    if (list == null || list.isEmpty()) {
	        System.out.println("The list is empty.");
	        return null;
	    }

	    Object[] header = new Object[list.getClass().getDeclaredFields().length-1];
	    int index = 0;
	    Object[][] data = new Object[list.size()][list.getClass().getDeclaredFields().length-1];

	    Field[] fields = list.get(0).getClass().getDeclaredFields();
	    for (Field field : fields) {
	        field.setAccessible(true);
	        header[index] = field.getName();
	        System.out.print(field.getName() + "\t");
	        index++;
	    }
	    System.out.println();

	    int rowIndex = 0;
	    for (Object obj : list) {
	        int columnIndex = 0;
	        for (Field field : fields) {
	            field.setAccessible(true);
	            try {
	                Object value = field.get(obj);
	                data[rowIndex][columnIndex] = value;
	                System.out.print(value + "\t");
	                columnIndex++;
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }
	        }
	        rowIndex++;
	        System.out.println();
	    }

	    return new DefaultTableModel(data, header);
	}

	public List<T> getObjectsFromTable() {
	    Connection connection = ConnectionFactory.getConnection();
	    PreparedStatement statement = null;
	    ResultSet resultSet = null;
	    List<T> objects = new ArrayList();

	    try {
	        String query = showTableQuery();
	        statement = connection.prepareStatement(query);
	        resultSet = statement.executeQuery();
	        objects = createObjects(resultSet);
	    } catch (SQLException e) {
	        LOGGER.log(Level.WARNING, "AbstractDAO:getObjectsFromTable " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	        ConnectionFactory.close(resultSet);
	        ConnectionFactory.close(statement);
	        ConnectionFactory.close(connection);
	    }

	    return objects;
	}

	/**
	 * The createObjects method creates a list of objects of type T by populating
	 * them with data from a ResultSet.
	 * 
	 * @param resultSet
	 * @return List<T> objects
	 */
	public List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T) ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Returns result set of the query select *from table that shows all table
	 * elements
	 * 
	 * @return ResultSet
	 */
	public ResultSet showTable() {
		Connection dbConnection = ConnectionFactory.getConnection();
		String query = showTableQuery();
		System.out.println(query);
		Statement showTableStatement = null;
		try {

			showTableStatement = dbConnection.createStatement();
			ResultSet res = showTableStatement.executeQuery(showTableQuery());
			return res;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Deletes the table element that has the id provided
	 * 
	 * @param choice - the choice of ids from the View
	 */
	public void deleteFromTable(Choice choice) {
		Connection dbConnection = ConnectionFactory.getConnection();

		PreparedStatement deleteStatement = null;
		try {

			deleteStatement = dbConnection.prepareStatement(createDeleteQuery());
			deleteStatement.setString(1, choice.getSelectedItem().toString());
			deleteStatement.executeUpdate();
		} catch (SQLException e) {
			ClientsView.showMessage("This client/product still appears in ORDERS Table!\n"
					+ " -------------- Opperation not allowed ------------------");
			LOGGER.log(Level.WARNING, "AbstractDAO:deleteFromTable " + e.getMessage());
		}
	}

	private Map<String, Object> retrieveProperties(T object) throws IllegalAccessException {
		Map<String, Object> properties = new HashMap();
		for (Field field : object.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			properties.put(field.getName(), field.get(object));
		}
		return properties;
	}

	/**
	 * updates row from table with the values provided in the View stored in the T
	 * object
	 * 
	 * @param obj - the object made from the table row
	 * @throws IllegalAccessException
	 */
	public void updateFromTable(T obj) throws IllegalAccessException {
		Connection connection = ConnectionFactory.getConnection();
		PreparedStatement updateStatement = null;

		try {

			updateStatement = connection.prepareStatement(createUpdateQuery(obj));
			int index = 1;
			int id = -1;
			int isFirst = 1;
			for (Field f : obj.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				Object propValue = f.get(obj);
				System.out.println(propValue + " " + index);
				if (isFirst == 0) {
					if (propValue instanceof String) {
						updateStatement.setString(index, (String) propValue);
					} else if (propValue instanceof Integer) {
						updateStatement.setInt(index, (Integer) propValue);
					} else {
						updateStatement.setDouble(index, (Double) propValue);
					}
					index++;
				} else {
					isFirst = 0;
					id = (Integer) propValue;
				}

			}
			updateStatement.setInt(index, id);
			updateStatement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "AbstractDAO:updateFromTable " + e.getMessage());
			e.printStackTrace();

		} finally {
			ConnectionFactory.close(updateStatement);
			ConnectionFactory.close(connection);
		}

	}
}
