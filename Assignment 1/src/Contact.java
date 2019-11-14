/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Assignment 1
  Author: Vo Van Thanh
  ID: TA
  Created  date: 13/11/2019
  Last modified: 14/11/2019
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

public class Contact {
    private String name;
    private String phone;
    private String email;
    private String address;

    public Contact(String name, String phone, String email, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Contact(String line) {
        String[] tokens = line.split(";");
        this.name = tokens[0];
        this.phone = tokens[1];
        this.email = tokens[2];
        this.address = tokens[3];
    }

    // Update contact details
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Get information of contact
    public String getName() { return name;}

    public String getPhone() { return phone;}

    public String getEmail() { return email;}

    public String getAddress() { return address;}

    // Print the contact
    public String toString() {
        return this.name + ";" + this.phone + ";" + this.email + ";" + this.address;
    }

    public void print() {
        System.out.printf("%-20s%-15s%-25s%s\n", this.name, this.phone, this.email, this.address);
    }
}
