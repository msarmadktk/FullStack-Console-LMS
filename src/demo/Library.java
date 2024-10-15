package demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import users.User;
import books.Book;
import transactions.LoanTransaction;

public class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<LoanTransaction> loanTransactions;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        loanTransactions = new ArrayList<>();
    }

 
    
    // Getter and setter methods
    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<LoanTransaction> getLoanTransactions() {
        return loanTransactions;
    }

    public void setLoanTransactions(ArrayList<LoanTransaction> loanTransactions) {
        this.loanTransactions = loanTransactions;
    }

    // Add book with validation to prevent duplicate IDs
    public void addBook(Book book) {
        if (findBookByID(book.getBookID()) == null) {
            books.add(book);
            System.out.println("Book added: " + book.getTitle());
        } else {
            System.out.println("Error: A book with this ID already exists.");
        }
    }

    // Remove book only if it's not loaned
    public void removeBook(Book book) {
        if (!book.isLoaned()) {
            books.remove(book);
            System.out.println("Book removed: " + book.getTitle());
        } else {
            System.out.println("Cannot remove a loaned book.");
        }
    }

    // Prevent deletion of users with active loans
    public void removeUser(User user) {
        if (user.getLoanedBooks().isEmpty()) {
            users.remove(user);
            System.out.println("User removed: " + user.getName());
        } else {
            System.out.println("Cannot remove user with active loans.");
        }
    }
    // Add user with validation to prevent duplicate IDs
    public void addUser(User user) {
        if (findUserByID(user.getUserID()) == null) {
            users.add(user);
            System.out.println("User added: " + user.getName());
        } else {
            System.out.println("Error: A user with this ID already exists.");
        }
    }

    // Display available (non-loaned) books
    public void displayAvailableBooks() {
        for (Book book : books) {
            if (!book.isLoaned()) {
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
                 System.out.println("\n");
            }
        }
    }

    // Display all users
    public void displayUsers() {
        for (User user : users) {
            user.displayUserDetails();
        }
    }

    // Loan a book to a user with fine handling for late returns
    public void loanBookToUser(Book book, User user, String loanID, LocalDate loanDate, LocalDate returnDate) {
        if (user.canLoanMoreBooks() && !book.isLoaned()) {
            LoanTransaction transaction = new LoanTransaction(loanID, user, book, loanDate, returnDate);
            loanTransactions.add(transaction);
            book.setLoanStatus(true);
            user.addLoanedBook(book, transaction.calculateTotalFee(14, 0));  // Example loan duration of 14 days
            System.out.println("Book loaned: " + book.getTitle() + " to " + user.getName() + "\n");
            transaction.displayTransactionDetails();
        } else {
            System.out.println("Cannot loan book. Either the user has reached their limit or the book is already loaned.");
        }
    }

    // Return a book and calculate fine if overdue
    public void returnBookFromUser(Book book, User user, LocalDate actualReturnDate) {
        LoanTransaction transaction = findLoanTransactionByBook(book);
        if (transaction != null) {
            LocalDate expectedReturnDate = transaction.getReturnDate();
            long lateDays = actualReturnDate.toEpochDay() - expectedReturnDate.toEpochDay();
            if (lateDays > 0) {
                double fine = lateDays * 2.0;  // Example fine of $2 per day
                user.applyFine(fine);
            }
            book.setLoanStatus(false);
            user.removeLoanedBook(book);
            System.out.println("Book returned: " + book.getTitle());
        } else {
            System.out.println("Error: No loan transaction found for this book.");
        }
    }

 // Request loan extension (only one extension allowed)
    public void requestLoanExtension(Book book, User user) {
        LoanTransaction transaction = findLoanTransactionByBook(book);
        if (transaction != null && book.isExtendable()) {
            transaction.requestExtension();
            System.out.println("Loan extended successfully for book: " + book.getTitle());
        } else {
            System.out.println("Error: Loan extension not possible for this book.");
        }
    }

    // Search for a book by ID
    public Book findBookByID(String bookID) {
        for (Book book : books) {
            if (book.getBookID().equals(bookID)) {
                return book;
            }
        }
        return null;
    }

    // Search for a user by ID
    public User findUserByID(String userID) {
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    // Search for a book by title
    public Book searchBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

 // Search for a book by author
    public Book searchBookByAuthor(String author) {
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                return book;
            }
        }
        return null;
    }

    // Search for a book by ISBN
    public Book searchBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getISBN().equalsIgnoreCase(isbn)) {
                return book;
            }
        }
        return null;
    }

    // Search for a user by name
    public User findUserByName(String name) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    
    // Sort books alphabetically by title
    public void sortBooksByTitle() {
        books.sort(Comparator.comparing(Book::getTitle));
        System.out.println("Books sorted by title.");
    }

    // Sort users alphabetically by name
    public void sortUsersByName() {
        users.sort(Comparator.comparing(User::getName));
        System.out.println("Users sorted by name.");
    }
    
 // Sort books by loan status (loaned first)
    public void sortBooksByLoanStatus() {
        books.sort(Comparator.comparing(Book::isLoaned).reversed());
        System.out.println("Books sorted by loan status.");
    }



    // Display all books (including loaned)
    public void displayBooks() {
        for (Book book : books) {
            System.out.println("Book: " + book.getTitle() + " (Loaned: " + book.isLoaned() + ")" );
        }
    }

    // Display users with active loans
    public void displayUsersWithLoans() {
        for (User user : users) {
            if (!user.getLoanedBooks().isEmpty()) {
                user.displayUserDetails();
            }
        }
    }


    // Find loan transaction by book
    private LoanTransaction findLoanTransactionByBook(Book book) {
        for (LoanTransaction transaction : loanTransactions) {
            if (transaction.getBook().equals(book)) {
                return transaction;
            }
        }
        return null;
    }

    
    // Bubble sort and remove duplicates for books based on bookID
    public void removeDuplicateBooks() {
        // Bubble sort based on bookID
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(j).getBookID().compareTo(books.get(j + 1).getBookID()) > 0) {
                    // Swap books[j] and books[j+1]
                    Book temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }

        // Remove duplicates after sorting
        for (int i = 0; i < books.size() - 1; i++) {
            if (books.get(i).getBookID().equals(books.get(i + 1).getBookID())) {
                books.remove(i + 1);
                i--; // Adjust index after removal
            }
        }
    }

    // Bubble sort and remove duplicates for users based on userID
    public void removeDuplicateUsers() {
        int n = users.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (users.get(j).getUserID().compareTo(users.get(j + 1).getUserID()) > 0) {
                    User temp = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < users.size() - 1; i++) {
            if (users.get(i).getUserID().equals(users.get(i + 1).getUserID())) {
                users.remove(i + 1);
                i--;
            }
        }
    }

    // Bubble sort and remove duplicates for loanTransactions based on loanID
    public void removeDuplicateLoanTransactions() {
        int n = loanTransactions.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (loanTransactions.get(j).getLoanID().compareTo(loanTransactions.get(j + 1).getLoanID()) > 0) {
                    LoanTransaction temp = loanTransactions.get(j);
                    loanTransactions.set(j, loanTransactions.get(j + 1));
                    loanTransactions.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < loanTransactions.size() - 1; i++) {
            if (loanTransactions.get(i).getLoanID().equals(loanTransactions.get(i + 1).getLoanID())) {
                loanTransactions.remove(i + 1);
                i--;
            }
        }
    }

    // Method to remove duplicates from all lists at once
    public void removeAllDuplicates() {
        removeDuplicateBooks();
        removeDuplicateUsers();
        removeDuplicateLoanTransactions();
    }

}
