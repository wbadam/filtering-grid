package org.vaadin.addons.filteringgrid.data;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.vaadin.addons.filteringgrid.comparators.StringComparator;
import org.vaadin.addons.filteringgrid.data.Person.Continent;

import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.impl.JsonUtil;

public class PersonService {

    private static PersonService instance;
    private final List<Person> people;

    private PersonService() {
        people = fetchAll();
    }

    public static PersonService getInstance() {
        if (instance == null) {
            instance = new PersonService();
        }
        return instance;
    }

    private Predicate<Person> filterFirstName(String filter) {
        return person -> StringComparator.containsIgnoreCase().test(person
                .getFirstName(), filter);
    }

    private Predicate<Person> filterLastName(String filter) {
        return person -> StringComparator.containsIgnoreCase().test(person
                .getLastName(), filter);
    }

    private Predicate<Person> filterName(String filter) {
        return person -> StringComparator.containsIgnoreCase().test(person
                .getFirstName() + " " + person.getLastName(), filter);
    }

    public List<Person> getAll() {
        return people;
    }

    public List<Person> getPersons(int offset, int limit) {
        return people.subList(offset, offset + limit);
    }

    public Stream<Person> getPersonsFilteredFirstName(int offset, int limit,
                                                      String firstNameContains) {
        return people.stream().filter(filterFirstName(firstNameContains))
                .skip(offset).limit(limit);
    }

    public Stream<Person> getPersonsFilteredName(int offset, int limit,
                                                 String nameContains) {
        return people.stream().filter(filterName(nameContains)).skip(offset)
                .limit(limit);
    }

    public int getSize() {
        return people.size();
    }

    public int getSizeFilteredFirstName(String firstNameContains) {
        return (int) people.stream().filter(filterFirstName
                (firstNameContains)).count();
    }

    public int getSizeFilteredName(String nameContains) {
        return (int) people.stream().filter(filterName(nameContains)).count();
    }

    private List<Person> fetchAll() {

        String dataJson = "";

        try {
            dataJson = FileUtils.readFileToString(new File(
                    getClass().getClassLoader().getResource("data.json")
                            .getFile()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateOfBirthFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat registeredFormat = new SimpleDateFormat(
                "E, MMMM d, yyyy hh:mm a");

        List<Person> persons = new ArrayList<>();

        JsonArray data = JsonUtil.parse(dataJson);
        for (int i = 0; i < data.length(); i++) {
            JsonObject p = data.getObject(i);

            Person person = new Person();
            person.setIndex((int) p.getNumber("index"));
            person.setGuid(p.getString("guid"));

            JsonObject name = p.getObject("name");
            person.setFirstName(name.getString("first"));
            person.setLastName(name.getString("last"));

            try {
                person.setDateOfBirth(
                        dateOfBirthFormat.parse(p.getString("dateOfBirth")));
                person.setRegistered(
                        registeredFormat.parse(p.getString("registered")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            person.setAddress(p.getString("address"));
            person.setEmail(p.getString("email"));
            person.setPhone(p.getString("phone"));
            person.setCompany(p.getString("company"));
            person.setLongitude(Double.valueOf(p.getString("longitude")));
            person.setLatitude(Double.valueOf(p.getString("latitude")));
            person.setBalance(Float.valueOf(
                    p.getString("balance").substring(1).replaceAll(",", "")));
            person.setActive(p.getBoolean("isActive"));
            person.setContinent(Continent.valueOf(
                    p.getString("continent").toUpperCase().replace(' ', '_')));

            persons.add(person);
        }

        return persons;
    }
}
