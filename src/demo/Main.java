package demo;

import books.Book;
import books.ReferenceBook;
import books.Textbook;
import books.Novel;
import users.User;
import users.Faculty;
import users.Student;
import users.PublicMember;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    private static Library library = new Library();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeLibrary();

        boolean exit = false;
        while (!exit) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    displayAvailableBooks();
                    break;
                case 4:
                    displayUsers();
                    break;
                case 5:
                    loanBook();
                    break;
                case 6:
                    returnBook();
                    break;
                case 7:
                    searchItem();
                    break;
                case 8:
                    sortItems();
                    break;
                case 9:
                    requestLoanExtension();
                    break;
                case 10:
                    removeUser();
                    break;
                case 11:
                    removeBook();
                    break;
                case 12:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("Exiting the LMS. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\n--- Library Management System ---");
        System.out.println("1. Add User");
        System.out.println("2. Add Book");
        System.out.println("3. Display Available Books");
        System.out.println("4. Display Users");
        System.out.println("5. Loan a Book");
        System.out.println("6. Return a Book");
        System.out.println("7. Search (Books or Users)");
        System.out.println("8. Sort Books or Users");
        System.out.println("9. Request Loan Extension");
        System.out.println("10. Remove User");
        System.out.println("11. Remove Book");
        System.out.println("12. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void initializeLibrary() {
        // Some default user and student
        User student = new Student("1", "sarmad", "Sarmad@gmail.com", "123-456-7890", "123 Main St");
        User faculty = new Faculty("2", "databot", "databot@gmail.com", "098-765-4321", "456 College Rd");

        library.addUser(student);
        library.addUser(faculty);

        Book textbook = new Textbook("1", "Data Structures", "John Doe", "123456789", 2020, "Education", 15.0);
        Book novel = new Novel("2", "The Great Gatsby", "F. Scott Fitzgerald", "987654321", 1925, "Fiction", 10.0);
        Book referenceBook = new ReferenceBook("3", "Encyclopedia of Science", "Various Authors", "1122334455", 2015, "Reference", 20.0);

        library.addBook(textbook);
        library.addBook(novel);
        library.addBook(referenceBook);
    }

    private static void addUser() {
        System.out.println("Enter User ID:");
        String userID = scanner.nextLine();
        System.out.println("Enter Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Email:");
        String email = scanner.nextLine();
        System.out.println("Enter Phone Number:");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Select User Type: \n1. Student, \n2. Faculty, \n3. Public Member");
        int userType = scanner.nextInt();
        scanner.nextLine(); 

        User user;
        switch (userType) {
            case 1:
                user = new Student(userID, name, email, phoneNumber, address);
                break;
            case 2:
                user = new Faculty(userID, name, email, phoneNumber, address);
                break;
            case 3:
                user = new PublicMember(userID, name, email, phoneNumber, address);
                break;
            default:
                System.out.println("Invalid user type. User not added.");
                return;
        }

        library.addUser(user);
        System.out.println("User added successfully.");
    }

    private static void addBook() {
        System.out.println("Enter Book ID:");
        String bookID = scanner.nextLine();
        System.out.println("Enter Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Author:");
        String author = scanner.nextLine();
        System.out.println("Enter ISBN:");
        String ISBN = scanner.nextLine();
        System.out.println("Enter Publication Year:");
        int publicationYear = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Enter Genre:");
        String genre = scanner.nextLine();
        System.out.println("Enter Base Loan Fee:");
        double baseLoanFee = scanner.nextDouble();
        scanner.nextLine(); 

        System.out.println("Select Book Type: \n1. Textbook, \n2. Novel, \n3. Reference Book");
        int bookType = scanner.nextInt();
        scanner.nextLine(); 

        Book book;
        switch (bookType) {
            case 1:
                book = new Textbook(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
                break;
            case 2:
                book = new Novel(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
                break;
            case 3:
                book = new ReferenceBook(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
                break;
            default:
                System.out.println("Invalid book type. Book not added.");
                return;
        }

        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void displayAvailableBooks() {
        System.out.println("\n--- Available Books ---");
        library.displayAvailableBooks();
    }

    private static void displayUsers() {
        System.out.println("\n--- Users ---");
        library.displayUsers();
    }
    
    private static void removeUser() {
        System.out.println("Enter User ID to remove:");
        String userID = scanner.nextLine();
        User user = findUserByID(userID);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        library.removeUser(user);
    }

    private static void removeBook() {
        System.out.println("Enter Book ID to remove:");
        String bookID = scanner.nextLine();
        Book book = findBookByID(bookID);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        library.removeBook(book);
    }

    private static void loanBook() {
        System.out.println("Enter User ID:");
        String userID = scanner.nextLine();
        User user = findUserByID(userID);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Enter Book ID:");
        String bookID = scanner.nextLine();
        Book book = findBookByID(bookID);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        if (book.isLoaned()) {
            System.out.println("Book is already loaned.");
            return;
        }

        System.out.println("Enter Loan ID:");
        String loanID = scanner.nextLine();
        LocalDate loanDate = LocalDate.now();
        LocalDate returnDate = loanDate.plusDays(14);

        library.loanBookToUser(book, user, loanID, loanDate, returnDate);
        
    }

    private static void returnBook() {
        System.out.println("Enter User ID:");
        String userID = scanner.nextLine();
        User user = findUserByID(userID);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Enter Book ID:");
        String bookID = scanner.nextLine();
        Book book = findBookByID(bookID);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        System.out.println("Enter Actual Return Date (format: yyyy-MM-dd):");
        String returnDateInput = scanner.nextLine();
        LocalDate actualReturnDate;
        try {
            actualReturnDate = LocalDate.parse(returnDateInput);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd.");
            return;
        }

        library.returnBookFromUser(book, user, actualReturnDate);
    }

    private static void searchItem() {
        System.out.println("\n--- Search Menu ---");
        System.out.println("Search by: ");
        System.out.println("1. Book by ID");
        System.out.println("2. Book by Title");
        System.out.println("3. Book by Author");
        System.out.println("4. Book by ISBN");
        System.out.println("5. User by ID");
        System.out.println("6. User by Name");
        System.out.print("Enter your choice: ");
        int searchType = scanner.nextInt();
        scanner.nextLine();

        switch (searchType) {
            case 1:
                System.out.print("Enter Book ID: ");
                String bookID = scanner.nextLine();
                Book bookByID = library.findBookByID(bookID);
                if (bookByID != null) {
                    System.out.println("Book found: \n");
                    System.out.println("-------------------------------");
                    System.out.println("Book ID: " + bookByID.getBookID());
                    System.out.println("Title: " + bookByID.getTitle());
                    System.out.println("Author: " + bookByID.getAuthor());
                    System.out.println("ISBN: " + bookByID.getISBN());
                    System.out.println("Publication Year: " + bookByID.getPublicationYear());
                    System.out.println("Genre: " + bookByID.getGenre());
                    System.out.println("Loan Status: " + (bookByID.isLoaned() ? "Loaned" : "Available"));
                    System.out.println("Base Loan Fee: $" + bookByID.getBaseLoanFee());
                    System.out.println("Damaged: " + (bookByID.isDamaged() ? "Yes" : "No"));
                    System.out.println("\n");
                } else {
                    System.out.println("Book not found.");
                }
                break;
            case 2:
                System.out.print("Enter Book Title: ");
                String bookTitle = scanner.nextLine();
                Book bookByTitle = library.searchBookByTitle(bookTitle);
                if (bookByTitle != null) {
                    System.out.println("Book found: \n");
                    System.out.println("-------------------------------");
                    System.out.println("Book ID: " + bookByTitle.getBookID());
                    System.out.println("Title: " + bookByTitle.getTitle());
                    System.out.println("Author: " + bookByTitle.getAuthor());
                    System.out.println("ISBN: " + bookByTitle.getISBN());
                    System.out.println("Publication Year: " + bookByTitle.getPublicationYear());
                    System.out.println("Genre: " + bookByTitle.getGenre());
                    System.out.println("Loan Status: " + (bookByTitle.isLoaned() ? "Loaned" : "Available"));
                    System.out.println("Base Loan Fee: $" + bookByTitle.getBaseLoanFee());
                    System.out.println("Damaged: " + (bookByTitle.isDamaged() ? "Yes" : "No"));
                    System.out.println("\n");
                } else {
                    System.out.println("Book not found.");
                }
                break;
            case 3:
                System.out.print("Enter Author Name: ");
                String authorName = scanner.nextLine();
                Book book = library.searchBookByAuthor(authorName); //bookbyauthor name
                if (book != null) {
                    System.out.println("Book found: \n");
                    System.out.println("-------------------------------");
                    System.out.println("Book ID: " + book.getBookID());
                    System.out.println("Title: " + book.getTitle());
                    System.out.println("Author: " + book.getAuthor());
                    System.out.println("ISBN: " + book.getISBN());
                    System.out.println("Publication Year: " + book.getPublicationYear());
                    System.out.println("Genre: " + book.getGenre());
                    System.out.println("Loan Status: " + (book.isLoaned() ? "Loaned" : "Available"));
                    System.out.println("Base Loan Fee: $" + book.getBaseLoanFee());
                    System.out.println("Damaged: " + (book.isDamaged() ? "Yes" : "No"));
                    System.out.println("-------------------------------");
                } else {
                    System.out.println("Book not found.");
                }
                break;
            case 4:
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                Book book1 = library.searchBookByISBN(isbn);
                if (book1 != null) {
                    System.out.println("Book found: \n");
                    System.out.println("-------------------------------");
                    System.out.println("Book ID: " + book1.getBookID());
                    System.out.println("Title: " + book1.getTitle());
                    System.out.println("Author: " + book1.getAuthor());
                    System.out.println("ISBN: " + book1.getISBN());
                    System.out.println("Publication Year: " + book1.getPublicationYear());
                    System.out.println("Genre: " + book1.getGenre());
                    System.out.println("Loan Status: " + (book1.isLoaned() ? "Loaned" : "Available"));
                    System.out.println("Base Loan Fee: $" + book1.getBaseLoanFee());
                    System.out.println("Damaged: " + (book1.isDamaged() ? "Yes" : "No"));
                    System.out.println("-------------------------------");
                } else {
                    System.out.println("Book not found.");
                }
                break;
            case 5:
                System.out.print("Enter User ID: ");
                String userID = scanner.nextLine();
                User user = library.findUserByID(userID);
                if (user != null) {
                    System.out.println("User found: \n");
                	System.out.println("-------------------------------------"); 
                    System.out.println("User ID: " + user.getUserID());
                    System.out.println("Name: " + user.getName());
                    System.out.println("Email: " + user.getEmail());
                    System.out.println("Phone: " + user.getPhoneNumber());
                    System.out.println("Address: " + user.getAddress());
                    System.out.println("Unpaid Fines: $" + user.getUnpaidFines());  // Show unpaid fines
                    System.out.println("Total Loan Fees: $" + user.getTotalLoanFees()+" <================");
                    System.out.println("\n"); 
                } else {
                    System.out.println("User not found.");
                }
                break;
            case 6:
                System.out.print("Enter User Name: ");
                String userName = scanner.nextLine();
                User userByName = library.findUserByName(userName);
                if (userByName != null) {
                    System.out.println("User found: \n");
                    System.out.println("-------------------------------------"); 
                    System.out.println("User ID: " + userByName.getUserID());
                    System.out.println("Name: " + userByName.getName());
                    System.out.println("Email: " + userByName.getEmail());
                    System.out.println("Phone: " + userByName.getPhoneNumber());
                    System.out.println("Address: " + userByName.getAddress());
                    System.out.println("Unpaid Fines: $" + userByName.getUnpaidFines());  // Show unpaid fines
                    System.out.println("Total Loan Fees: $" + userByName.getTotalLoanFees()+" <================");
                    System.out.println("\n"); 
                } else {
                    System.out.println("User not found.");
                }
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void sortItems() {
        System.out.println("\n--- Sort Menu ---");
        System.out.println("Sort by: ");
        System.out.println("1. Books by Title");
        System.out.println("2. Books by Loan Status");
        System.out.println("3. Users by Name");
        System.out.print("Enter your choice: ");
        int sortType = scanner.nextInt();
        scanner.nextLine();

        switch (sortType) {
            case 1:
                library.sortBooksByTitle();
                System.out.println("\n--- Books Sorted by Title ---");
                library.displayAvailableBooks();
                break;
            case 2:
                library.sortBooksByLoanStatus();
                System.out.println("\n--- Books Sorted by Loan Status ---");
                library.displayBooks();
                break;
            case 3:
                library.sortUsersByName();
                System.out.println("\n--- Users Sorted by Name ---");
                library.displayUsers();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }


    private static void requestLoanExtension() {
        System.out.println("Enter User ID:");
        String userID = scanner.nextLine();
        User user = findUserByID(userID);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Enter Book ID:");
        String bookID = scanner.nextLine();
        Book book = findBookByID(bookID);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }

        library.requestLoanExtension(book, user);
        System.out.println("Loan extended successfully.");
    }


    private static User findUserByID(String userID) {
        for (User user : library.getUsers()) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    private static User findUserByName(String name) {
        for (User user : library.getUsers()) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    private static Book findBookByID(String bookID) {
        for (Book book : library.getBooks()) {
            if (book.getBookID().equals(bookID)) {
                return book;
            }
        }
        return null;
    }
}