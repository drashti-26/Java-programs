package model;
import java.io.Serializable;
import model.annotation.Id;
import model.annotation.MaxLength;

public class Customer implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    private static long customerCounter = 100;
    @Id private final String customerId;
    @MaxLength(60) private final String name;
    private final String email;
    private final String mobile;
    private Address address;
    private static String generateCustomerId() { return "CUST" + (++customerCounter); }
    public Customer(String name, String email, String mobile, Address address) {
        this.customerId = generateCustomerId(); this.name = name; this.email = email; this.mobile = mobile; this.address = address;
    }
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public Address getAddress() { return address; }
    @Override public Customer clone() {
        try { return (Customer) super.clone(); } catch (CloneNotSupportedException e) { throw new AssertionError(e); }
    }
    public static class Address implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String line, city, pincode;
        public Address(String line, String city, String pincode) { this.line=line; this.city=city; this.pincode=pincode; }
        public String getLine() { return line; } public String getCity() { return city; } public String getPincode() { return pincode; }
    }
}
