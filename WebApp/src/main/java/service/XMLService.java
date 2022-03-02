package service;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class XMLService {

    private ArrayList <Continent> data = new ArrayList <>();
    private ArrayList <Country> countries;
    private Continent currentContinent;
    private Country currentCountry;
    private ArrayList <User> users;
    private User currentUser;

    public XMLService() {

    }

    public ArrayList <Continent> getResultsFromXML() {
        ClassLoader classLoader = getClass().getClassLoader();
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        data = new ArrayList <>();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(classLoader.getResource("data.xml").getFile()));
            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    String str = startElement.getName().getLocalPart();
                    switch (str) {
                    case "continent":
                        currentContinent = new Continent();
                        Attribute name = startElement.getAttributeByName(new QName("name"));
                        if (name != null) {
                            currentContinent.setName(name.getValue());
                        }
                        countries = new ArrayList <>();
                        break;
                    case "country":
                        nextEvent = reader.nextEvent();
                        currentCountry = new Country();
                        Attribute cname = startElement.getAttributeByName(new QName("name"));
                        currentCountry.setName(cname.getValue());
                        countries.add(currentCountry);
                        users = new ArrayList <>();
                        break;
                    case "user":
                        nextEvent = reader.nextEvent();
                        currentUser = new User();
                        currentUser.setCountry(currentCountry.getName());
                        users.add(currentUser);
                        break;
                    case "first_name":
                        nextEvent = reader.nextEvent();
                        currentUser.setFirstName(nextEvent.asCharacters().getData());
                        break;
                    case "last_name":
                        nextEvent = reader.nextEvent();
                        currentUser.setLastName(nextEvent.asCharacters().getData());
                        break;
                    case "address":
                        nextEvent = reader.nextEvent();
                        currentUser.setAddress(nextEvent.asCharacters().getData());
                        break;
                    case "city":
                        nextEvent = reader.nextEvent();
                        currentUser.setCity(nextEvent.asCharacters().getData());
                        break;
                    case "email":
                        nextEvent = reader.nextEvent();
                        currentUser.setEmail(nextEvent.asCharacters().getData());
                        break;
                    case "password":
                        nextEvent = reader.nextEvent();
                        currentUser.setPassword(nextEvent.asCharacters().getData());
                        break;
                    }
                }
                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();
                    switch (endElement.getName().getLocalPart()) {
                    case "continent":
                        data.add(currentContinent);
                        break;
                    case "country":
                        currentContinent.setCountries(countries);
                        break;
                    case "user":
                        currentCountry.setUsers(users);
                        break;
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getJsonData() throws Exception {
        if (data.isEmpty()) {
            data = getResultsFromXML();
        }
        return new Gson().toJson(data);
    }

    public void addUser(User user) throws XMLStreamException, IOException {
        if (user != null) {
            findPlaceForNewUser(user);
        }
    }

    public void deleteUser(User user) throws XMLStreamException, IOException {

        Continent con = findContinent(user.getCountry());
        Country country = findCountry(con.getCountries(), user.getCountry());
        country.getUsers().remove(user);
        if (country.getUsers().size() == 0) {
            con.getCountries().remove(country);
        }
        if (con.getCountries().size() == 0) {
            data.remove(con);
        }
        createXML(data);
    }
    private void findPlaceForNewUser(User user) throws XMLStreamException, IOException {

        Continent con = findContinent(user.getCountry());
        if (con == null) {
            Continent newContinent = new Continent();
            newContinent.setName(setContinentName(user.getCountry()));

            Country newCountry = new Country();
            newCountry.setName(user.getCountry());

            ArrayList <User> userList = new ArrayList<>();
            userList.add(user);
            newCountry.setUsers(userList);

            ArrayList <Country> countryList = new ArrayList<>();
            countryList.add(newCountry);
            newContinent.setCountries(countryList);
            data.add(newContinent);

            createXML(data);
        } else {
            Country country = findCountry(con.getCountries(), user.getCountry());

            if (country == null) {
                Country newCountry = new Country();
                newCountry.setName(user.getCountry());
                ArrayList <User> userList = new ArrayList<>();
                userList.add(user);
                newCountry.setUsers(userList);
                con.getCountries().add(newCountry);

                createXML(data);
            } else {
                country.getUsers().add(user);
                createXML(data);
            }
        }
    }
    private Continent findContinent(String country) {
        String continentName = setContinentName(country);
        return data.stream()
            .filter(name -> name.getName()
                .equals(continentName)).findAny().orElse(null);
    }
    private Country findCountry(List < Country > list, String country) {
        return list.stream().filter(x -> x.getName().equals(country)).findAny().orElse(null);
    }

    private String setContinentName(String country) {
        Map <String, String[]> continentList = new HashMap<>();
        continentList.put("Europe", new String[] {
            "United Kingdom",
            "Germany",
            "France"
        });
        continentList.put("North America", new String[] {
            "United States",
            "Canada"
        });
        continentList.put("Asia", new String[] {
            "China",
            "Japan"
        });

        return continentList.entrySet().stream()
            .filter(e -> Arrays.asList(e.getValue()).contains(country))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }
    public User findUserByEmail(String email) throws XMLStreamException, IOException {
        return data.stream().map(Continent::getCountries)
            .flatMap(Collection::stream)
            .map(Country::getUsers)
            .flatMap(Collection::stream)
            .filter(u -> u.getEmail().equals(email)).findAny().orElse(null);
    }
    public User getUser(String firstName, String lastName, String address, String city, String country, String email, String password) throws Exception {
        if (!userExist(email)) {
            User user = new User(firstName, lastName, address, city, country, email, password);
            return user;
        }
        return null;
    }
    public boolean userExist(String email) throws XMLStreamException, IOException {
        return data.stream()
            .map(Continent::getCountries)
            .flatMap(Collection::stream)
            .map(Country::getUsers)
            .flatMap(Collection::stream)
            .anyMatch(user -> user.getEmail().equals(email));
    }

    private void createXML(List <Continent> list) throws XMLStreamException, IOException {

        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        sb.append("<data>\n");

        for (Continent continent: list) {
            sb.append("    <continent name=\"" + continent.getName() + "\">\n");
            for (Country country: continent.getCountries()) {
                sb.append("       <country name=\"" + country.getName() + "\">\n");
                for (User user: country.getUsers()) {
                    sb.append(
                        "           <user>\n" +
                        "               <first_name>" + user.getFirstName() + "</first_name>\n" +
                        "               <last_name>" + user.getLastName() + "</last_name>\n" +
                        "               <address>" + user.getAddress() + "</address>\n" +
                        "               <city>" + user.getCity() + "</city>\n" +
                        "               <email>" + user.getEmail() + "</email>\n" +
                        "               <password>" + user.getPassword() + "</password>\n" +
                        "           </user>\n");
                }
                sb.append("       </country>\n");
            }
            sb.append("   </continent>\n");
        }
        sb.append("</data>");
        writeIntoXml(sb.toString());
    }

    public void writeIntoXml(String data) throws XMLStreamException, IOException {

        String fileName = "C:\\Users\\win10\\git\\repository\\Web-App\\WebApp\\src\\main\\resources\\data.xml";

        try (PrintWriter pw = new PrintWriter(fileName)) {
            pw.print("");
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Writer writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}