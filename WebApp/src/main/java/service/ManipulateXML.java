package service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.google.gson.Gson;

import entity.Continent;
import entity.Country;
import entity.User;

public class ManipulateXML {
	
	ArrayList<Continent> data = new ArrayList<Continent>();
	private ArrayList<Country> countries;
	private Continent continent;
	Country country;
	ArrayList<User> users;
	User user;
	
	public ManipulateXML() {

	}

	public List<Continent> parse() throws XMLStreamException, IOException {
		  ClassLoader classLoader = getClass().getClassLoader();
           XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
           XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(classLoader.getResource("data.xml").getFile()));
          
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                   String str =  startElement.getName().getLocalPart();
					switch (str) {
                        case "continent":
                            continent = new Continent();
                            Attribute name = startElement.getAttributeByName(new QName("name"));
                            if (name != null) {
                            	continent.setName(name.getValue());
                            }
                            countries = new ArrayList<Country>();
                            break;
                        case "country":
                            nextEvent = reader.nextEvent();
                            country = new Country();
                            Attribute cname = startElement.getAttributeByName(new QName("name"));
                            country.setName(cname.getValue());
                            countries.add(country);
                            users = new ArrayList<User>();
                            break;
                        case "user":
                            nextEvent = reader.nextEvent();
                            user = new User();
                            user.setCountry(country.getName());
                            users.add(user);
                            break;
                        case "first_name":
                            nextEvent = reader.nextEvent();
                            user.setFirstName(nextEvent.asCharacters().getData());
                            break;
                        case "last_name":
                            nextEvent = reader.nextEvent();
                            user.setLastName(nextEvent.asCharacters().getData());
                            break;
                        case "address":
                            nextEvent = reader.nextEvent();
                            user.setAddress(nextEvent.asCharacters().getData());
                            break;
                        case "city":
                            nextEvent = reader.nextEvent();
                            user.setCity(nextEvent.asCharacters().getData());
                            break;
                        case "email":
                            nextEvent = reader.nextEvent();
                            user.setEmail(nextEvent.asCharacters().getData());
                            break;
                        case "password":
                            nextEvent = reader.nextEvent();
                            user.setPassword(nextEvent.asCharacters().getData());
                            break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    switch (endElement.getName().getLocalPart()) {
                    case "continent":
                        data.add(continent);
                        break;
                    case "country":
                    	continent.setCountries(countries);
                    	break;
                    case "user":
                    	country.setUsers(users);
                    	break;
                    }
                }
            }
            return data;
	}

    public String getAll() throws Exception {
    	String json = new Gson().toJson(parse());
    	return json;
	}
}
