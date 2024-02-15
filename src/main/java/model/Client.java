package model;

import dao.AbstractDAO;

/**
 * 
 * Client class: it has all the atributes the same as the table columns.It has
 * getters and setters for the atributes
 *
 */
public class Client {
	private int id;
	private String name;
	private String address;
	private String email;
	private String phoneNumber;

	public Client(int id, String name, String address, String email, String phoneNumber) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public Client(String name, String address, String email, String phoneNumber) {

		this.name = name;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;

	}

	public Client() {

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", phone="
				+ phoneNumber + "]";
	}
}
