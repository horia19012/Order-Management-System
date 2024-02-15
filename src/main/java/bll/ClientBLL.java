package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.EmailValidator;
import bll.validators.PhoneNumberValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

/**
 * Validates the fields before doing a specific operation on the table
 */
public class ClientBLL {

	private List<Validator<Client>> validators;

	/**
	 * It adds the validators to the validators List
	 */
	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());
		validators.add(new PhoneNumberValidator());
	}

	/**
	 * 
	 * @param client -the client object that is being validated and inserted after
	 * @return the value returned by the insertion
	 */
	public int insertClient(Client client) {
		for (Validator<Client> v : validators) {
			v.validate(client);
		}
		return ClientDAO.insert(client);
	}

	/**
	 * 
	 * @param client -the client object that is being validated and updated after
	 * @throws IllegalAccessException
	 */

	public void updateClient(Client client) throws IllegalAccessException {
		for (Validator<Client> v : validators) {
			v.validate(client);
		}
		ClientDAO cDAO = new ClientDAO();
		cDAO.updateFromTable(client);
	}
}