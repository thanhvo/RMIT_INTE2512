/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2019C
  Assessment: Assignment 1
  Author: Vo Van Thanh
  ID: TA
  Created  date: 13/11/2019
  Last modified: 14/11/201
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
  The pattern for checking email is from https://howtodoinjava.com/regex/java-regex-validate-email-address/
*/

import java.util.List;
import java.util.Scanner;

public class Main {

    private static String inputFilePath = "input/contacts.txt";
    private static String outputFilePath = "output/contacts.txt";

    private static void showMenu() {
        String menu = "1. Load contacts from file\n" +
                "2. View all contacts\n" +
                "3. Add new contact\n" +
                "4. Edit a contact\n" +
                "5. Delete a contact\n" +
                "6. Search contact list\n" +
                "7. Sort contact list\n" +
                "8. Save contacts to file\n" +
                "9. Quit\n" +
                "\n" +
                "Select a function (1-9):";

        System.out.println(menu);
    }

    // Check if a name is valid
    private static boolean checkName(String name) {
        return name.matches("[a-zA-Z ]+");
    }

    // Check if a phone is valid
    private static boolean checkPhone(String phone) {
        return phone.matches("[+].[0-9 ]+");
    }

    // Check if an email is valid.
    private static boolean checkEmail(String email) {
        return email.matches("^(.+)@(.+)$");
    }

    //Get contact
    private static void getContact(Scanner scanner, ContactList contactList) {
        // Get the name of contact
        System.out.println("Enter name: ");
        String name = scanner.nextLine();
        while (!checkName(name)) {
            System.out.println("The input is not valid. Please reenter name: ");
            name = scanner.nextLine();
        }

        // Get the phone of contact
        System.out.println("Enter phone number: ");
        String phone = scanner.nextLine();
        while (!checkPhone(phone)) {
            System.out.println("The input is not valid. Please reenter phone: ");
            phone = scanner.nextLine();
        }

        // Get the email
        System.out.println("Enter new email: ");
        String email = scanner.nextLine();
        while (!checkEmail(email)) {
            System.out.println("The input is not valid. Please reenter email: ");
            email = scanner.nextLine();
        }

        // Get the address
        System.out.println("Enter address: ");
        String address = scanner.nextLine();

        Contact contact = new Contact(name, phone, email, address);
        contactList.addContact(contact);
    }

    // Edit the contact
    private static void editContact(Scanner scanner, ContactList contactList) {
        System.out.println("Enter the contact id to be edited: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Contact contact = contactList.getContact(id);
        System.out.println("Enter new name: ");
        String newName = scanner.nextLine();
        System.out.println("Enter new phone number: ");
        String newPhone = scanner.nextLine();
        System.out.println("Enter new email: ");
        String newEmail = scanner.nextLine();
        System.out.println("Enter new address: ");
        String newAddress = scanner.nextLine();
        contact.setName(newName);
        contact.setPhone(newPhone);
        contact.setEmail(newEmail);
        contact.setAddress(newAddress);
    }

    // Delete a contact
    private static void deleteContact(Scanner scanner, ContactList contactList) {
        int id = -1;
        while (id < 0 || id >= contactList.getContactNumber()) {
            System.out.println("Enter contact id to be deleted: ");
            id = scanner.nextInt();
        }
        contactList.deleteContact(id);
        scanner.nextLine();
    }

    // Search a contact
    private static void searchContacts(Scanner scanner, ContactList contactList) {
        System.out.println("Enter the keyword: ");
        String keyWord = scanner.nextLine();
        List<Contact> list = contactList.getContactList(keyWord);
        if (list == null || list.isEmpty()) {
            System.out.println("Can not find any contact that matches with the keyword!");
        } else {
            System.out.println("The list of contacts with the given keyword are:");
            contactList.showContacts(list);
        }
    }

    // Sort contacts
    private static void sortContacts(Scanner scanner, ContactList contactList) {
        String sortOptions = "Please choose the field to sort\n" +
                "1. Name\n" +
                "2. Phone\n" +
                "3. Email\n" +
                "4. Address\n";
        System.out.println(sortOptions);
        int sortOption = scanner.nextInt();
        contactList.sort(sortOption);
        contactList.viewContacts();
    }

    public static void main(String[] args) {
        ContactList contactList = new ContactList();
        // Declare the variables used in the switch
        int option = -1;
        while (option != 9) {
            showMenu();
            Scanner scanner = new Scanner(System.in);
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                // Load contacts from file
                case 1:
                    contactList.loadContacts(inputFilePath);
                    break;
                case 2:
                    contactList.viewContacts();
                    break;
                case 3:
                    getContact(scanner, contactList);
                    break;
                case 4:
                    editContact(scanner, contactList);
                    break;
                case 5:
                    deleteContact(scanner, contactList);
                    break;
                case 6:
                    searchContacts(scanner, contactList);
                    break;
                case 7:
                    sortContacts(scanner, contactList);
                    break;
                case 8:
                    contactList.saveContacts(outputFilePath);
                    break;
                case 9:
                    return;

            }
        }
    }
}
