package model;

public class ShippingInformation {
    private String name;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;

    public ShippingInformation() {}
    
    public ShippingInformation(String name, String phoneNumber, String address, String city, String country) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.country = country;
    }

    // Getter and Setter for name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter and Setter for address
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    // Getter and Setter for city
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    // Getter and Setter for country
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
}