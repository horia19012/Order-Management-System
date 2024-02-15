package model;

/**
 * 
 * Order class: it has all the atributes the same as the table columns.It has
 * getters and setters for the atributes
 *
 */
public class Order {
	private int id;
	private int client_id;
	private int product_id;
	private int quantity;
	public int getOrder_id() {
		return id;
	}
	public void setOrder_id(int order_id) {
		this.id = order_id;
	}
	public int getClient_id() {
		return client_id;
	}
	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
