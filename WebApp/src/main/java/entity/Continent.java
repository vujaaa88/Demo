package entity;

import java.util.ArrayList;

public class Continent {
	
	private String name;
	private ArrayList<Country> countries;
	
	public Continent() {
		
	}

	public Continent(String name, ArrayList<Country> countries) {
		super();
		this.name = name;
		this.countries = countries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Country> getCountries() {
		return countries;
	}

	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}
	
}
