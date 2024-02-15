package presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the Controller for the MainWindow with the menu
 * 
 *
 */
public class Controller {
	MainView view;

	public Controller(MainView vw) {
		this.view = vw;
//		view.setVisible(true);
		view.addClientsBtnActionListener(new ClientsBtn());
		view.addOrdersBtnActionListener(new OrdersBtn());
		view.addProductsBtnActionListener(new ProductsBtn());

	}

	/**
	 * 
	 * Inner class for the ClientsButton
	 *
	 */
	public class ClientsBtn implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			view.dispose();
			new ClientsView(0);
		}
	}

	/**
	 * 
	 * Inner class for the OrdersButton
	 *
	 */
	public class OrdersBtn implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			view.dispose();
			new OrdersView();

		}
	}

	/**
	 * 
	 * Inner class for the ProductsButton
	 *
	 */
	public class ProductsBtn implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			view.dispose();
			new ProductsView(0);
		}
	}
}
