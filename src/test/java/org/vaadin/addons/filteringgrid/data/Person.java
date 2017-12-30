package org.vaadin.addons.filteringgrid.data;

import java.util.Date;

/*
  {
    "continent": "Europe",
    "longitude": "-139.30154",
    "latitude": "15.530338",
    "registered": "Tuesday, January 19, 2016 12:42 PM",
    "address": "962 Dinsmore Place, Bowmansville, Indiana, 5535",
    "phone": "+1 (974) 438-3565",
    "email": "nell.erickson@otherway.info",
    "company": "OTHERWAY",
    "dateOfBirth": "3/4/1995",
    "name": {
      "last": "Erickson",
      "first": "Nell"
    },
    "balance": "$3,142.70",
    "isActive": false,
    "guid": "02cb0dbe-e1c0-4bc6-9b31-04dadc90ea9e",
    "index": 0
  }
*/
public class Person {

    public enum Continent {
        ASIA, AFRICA, NORTH_AMERICA, SOUTH_AMERICA, ANTARCTICA, EUROPE, AUSTRALIA
    }

    private int index;
    private String guid;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phone;
    private String company;
    private double longitude;
    private double latitude;
    private float balance;
    private Date registered;
    private boolean isActive;
    private Continent continent;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(
            Continent continent) {
        this.continent = continent;
    }
}
