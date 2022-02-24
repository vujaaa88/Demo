package entity;

import java.util.ArrayList;

public class Country {
	
	private String name;
	private ArrayList<User> users;
	
	public Country() {
		
	}

	public Country(String name, ArrayList<User> users) {
		super();
		this.name = name;
		this.users = users;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}

}
