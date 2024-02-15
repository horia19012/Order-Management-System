package start;

import dao.AbstractDAO;
import dao.ClientDAO;
import dao.ProductDAO;
import model.Client;
import model.Product;
import presentation.Controller;
import presentation.MainView;

/**
 * This is the start Class with the main that launches the menu Interface and it
 * has the controller atributed
 * 
 *
 */
public class Start1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainView mw = new MainView();
		Controller controller = new Controller(mw);
	}

}
