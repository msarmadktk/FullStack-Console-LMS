package users;

import java.util.*;
import books.Book;

public abstract class User {
    private String userID;
    private String name;
    private String email;
    private ArrayList<Book> loanedBooks;
    private double totalLoanFees;
    private String phoneNumber;
    private String address;
    private double unpaidFines;  // New attribute for unpaid fines
    private String userType;  // New attribute for user type

    public User(String userID, String name, String email, String phoneNumber, String address, String userType) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.loanedBooks = new ArrayList<>();
        this.totalLoanFees = 0;
        this.unpaidFines = 0;  // Initialize unpaid fines
        this.userType = userType;  // Initialize user type
    }

    public String getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<Book> getLoanedBooks() {
        return loanedBooks;
    }
    
    public void setLoanedBooks(ArrayList<Book> b) {
    	this.loanedBooks = b;    	
    }

    public double getTotalLoanFees() {
        return totalLoanFees;
    }
    
    public void setTotalLoanFees(double tlf) {
    	this.totalLoanFees = tlf;    	
    }
    public void setUnpaidFines(double upf) {
    	this.unpaidFines = upf;    	
    } 
  
    
    public double getUnpaidFines() {
        return unpaidFines;
    }

    public String getUserType() {
        return userType;  // Getter for userType
    }

    public void applyFine(double fine) {
        unpaidFines += fine;
        System.out.println("Fine of $" + fine + " applied to user " + name + ". Total unpaid fines: $" + unpaidFines);
    }

    public void clearFines() {
        unpaidFines = 0;
    }

    public void addLoanedBook(Book book, double fee) {
        loanedBooks.add(book);
        totalLoanFees += fee;
    }

    public void removeLoanedBook(Book book) {
        loanedBooks.remove(book);
    }

    public abstract int getMaxBooks();

    public void displayUserDetails() {
        System.out.println("-------------------------------------");
        System.out.println("User ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Address: " + address);
        System.out.println("User Type: " + userType);  // Display user type
        System.out.println("Unpaid Fines: $" + unpaidFines);
        System.out.println("Total Loan Fees: $" + totalLoanFees + " <================");
        System.out.println("\n");
    }

    public boolean canLoanMoreBooks() {
        return loanedBooks.size() < getMaxBooks();
    }
}
