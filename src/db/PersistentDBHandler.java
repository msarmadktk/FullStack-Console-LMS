package db;

import java.io.IOException;
import java.util.List;

import books.Book;
import transactions.LoanTransaction;
import users.User;

public interface PersistentDBHandler {
    // User-related methods
    void addUser(User user) throws IOException;
    void removeUser(String userId);
    User getUser(String userId);
    List<User> getAllUsers() throws IOException;

    // Book-related methods
    void addBook(Book book) throws IOException;
    void removeBook(String bookId);
    Book getBook(String bookId);
    List<Book> getAllBooks() throws IOException;

    // Loan-related methods
    void addLoanTransaction(LoanTransaction transaction) throws IOException;
    void updateLoanTransaction(LoanTransaction transaction);
    LoanTransaction getLoanTransaction(int loanId);
    List<LoanTransaction> getAllLoanTransactions() throws IOException;

    // Additional methods for fines, loan extensions, etc.
    void calculateFines();
}
