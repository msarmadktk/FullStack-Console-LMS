package db;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import books.Book;
import users.User;

public interface PersistentDBHandler {
 
	public void addUser(User user) throws SQLException;
	public void removeUser(int id) throws SQLException;
	public void addBook(Book book) throws SQLException;
	public void removeBook(int id) throws SQLException;
	public void addTransaction(int loanID, int userID, int bookID, LocalDate loanDate, LocalDate returnDate) throws SQLException;
	public void removeTransaction(int userID,int bookID) throws SQLException;
	public void updateUserLoan(User user, Book book) throws SQLException ;
}
