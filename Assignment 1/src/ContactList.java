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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ContactList {
    private ArrayList<Contact> contactList;

    public ContactList() {
        contactList = new ArrayList<Contact>();
    }

    // Load contacts from a text file
    public void loadContacts(String filePath) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                contactList.add(new Contact(line));
            }
        } catch (Exception ex) {
            ex.getStackTrace();
        }

    }

    // View all contacts
    public void viewContacts() {
        System.out.println();
        System.out.printf("%-4s%-21s%-15s%-25s%s\n", "Id","Name","Phone", "Email", "Address");
        for (Contact contact : contactList) {
            System.out.printf("%-4d",contactList.indexOf(contact));
            contact.print();
        }
        System.out.println();
    }

    public void showContacts(List<Contact> list) {
        System.out.println();
        System.out.printf("%-4s%-21s%-15s%-25s%s\n", "Id","Name","Phone", "Email", "Address");
        for (Contact contact : list) {
            System.out.printf("%-4d",contactList.indexOf(contact));
            contact.print();
        }
        System.out.println();
    }

    // Add a new contact
    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    // Delete a contact
    public void deleteContact(int id) {
        contactList.remove(id);
    }

    // Search the contact list by a given string
    public List<Contact> getContactList(String keyWord) {
        ArrayList<Contact> list = new ArrayList<Contact>();
        for (Contact contact : contactList) {
            if (contact.getName().toUpperCase().contains(keyWord.toUpperCase())) {
                list.add(contact);
            }
        }
        return list;
    }

    // Sort contact list by a given field name
    // 1: Name
    // 2: Phone
    // 3: Email
    // 4: Address
    public void sort(int option) {
        Comparator<Contact> comparator = null;
        switch(option) {
            case 1:
                comparator = new Comparator<Contact>() {
                    public int compare(Contact c1, Contact c2) {
                        return c1.getName().toUpperCase().compareTo(c2.getName().toUpperCase());
                    }
                };
                break;
            case 2:
                comparator = new Comparator<Contact>() {
                    public int compare(Contact c1, Contact c2) {
                        return c1.getPhone().toUpperCase().compareTo(c2.getPhone().toUpperCase());
                    }
                };
                break;
            case 3:
                comparator = new Comparator<Contact>() {
                    public int compare(Contact c1, Contact c2) {
                        return c1.getEmail().toUpperCase().compareTo(c2.getEmail().toUpperCase());
                    }
                };
                break;
            case 4:
                comparator = new Comparator<Contact>() {
                    public int compare(Contact c1, Contact c2) {
                        return c1.getAddress().toUpperCase().compareTo(c2.getAddress().toUpperCase());
                    }
                };
                break;
        }
        Collections.sort(contactList, comparator);

    }

    // Save contacts to a file
    public void saveContacts(String fileName)  {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            for (Contact contact : contactList) {
                writer.println(contact);
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    // Get a contact with given id
    public Contact getContact(int id) {
        return contactList.get(id);
    }

    // Get a number of contacts
    public int getContactNumber() {
        return contactList.size();
    }
}
