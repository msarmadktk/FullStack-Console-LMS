package db;


import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import books.Book;
import books.Novel;
import books.ReferenceBook;
import books.Textbook;
import transactions.LoanTransaction;
import users.Faculty;
import users.PublicMember;
import users.Student;
import users.User;

public class MySQLHandler implements PersistentDBHandler {
    private final String url = "jdbc:mysql://localhost:3306/LibraryManagement"; 
    private final String username = "root"; 
    private final String password = "PepeWillHit1$"; 
    private Connection connection;
    
    public MySQLHandler() throws SQLException {
        // Explicitly call the super constructor of PersistentDBHandler
    	super();
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to MySQL Database.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }


    
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (UserID, Name, Email, PhoneNumber, Address, UserType) VALUES (?,?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
        	statement.setInt(1, Integer.parseInt(user.getUserID()));
        	statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getUserType());
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted into MySql successfully!");
            }
        }
        catch(Exception e) {
        	System.out.println(e);
        }
    }

    
   
    public void removeUser(int userId) throws SQLException {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User was deleted successfully!");
            }
        }
    }


    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO Books (BookID, Title, Author, ISBN, PublicationYear, Genre, LoanStatus, BaseLoanFee, BookType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, Integer.parseInt(book.getBookID()));
            statement.setString(2, book.getTitle());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getISBN());
            statement.setInt(5, book.getPublicationYear());
            statement.setString(6, book.getGenre());
            statement.setBoolean(7, book.isLoaned());
            statement.setDouble(8, book.getBaseLoanFee());
            statement.setString(9, book.getBookType());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new book was inserted into MySQL successfully!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }



	public void removeBook(int id) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM Books WHERE BookID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Book was deleted successfully!");
            }
        }
		
	}

	
	
	public void addTransaction(int loanID, int userID, int bookID, LocalDate loanDate, LocalDate returnDate) throws SQLException {
		 String sql = "INSERT INTO LoanTransactions (LoanID, UserID, BookID, LoanDate, ReturnDate) VALUES (?, ?, ?, ?, ?)";
		    try (PreparedStatement statement = connection.prepareStatement(sql)) {
		        statement.setInt(1, loanID);
		        statement.setInt(2, userID);
		        statement.setInt(3, bookID);
		        statement.setDate(4, java.sql.Date.valueOf(loanDate)); // Convert LocalDate to java.sql.Date
		        if (returnDate != null) {
		            statement.setDate(5, java.sql.Date.valueOf(returnDate)); // Convert LocalDate to java.sql.Date
		        } else {
		            statement.setNull(5, java.sql.Types.DATE);
		        }

		        int rowsInserted = statement.executeUpdate();
		        if (rowsInserted > 0) {
		            System.out.println("A new loan transaction was added successfully!");
		        }
		    } catch (Exception e) {
		        System.out.println("Error adding loan transaction: " + e.getMessage());
		    }
	}


	public void removeTransaction(int userID, int bookID) throws SQLException {
	    String sql = "DELETE FROM LoanTransactions WHERE UserID = ? AND BookID = ?";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setInt(1, userID);
	        statement.setInt(2, bookID);

	        int rowsDeleted = statement.executeUpdate();
	        if (rowsDeleted > 0) {
	            System.out.println("The loan transaction was removed successfully!");
	        } else {
	            System.out.println("No transaction found for the specified UserID and BookID.");
	        }
	    } catch (Exception e) {
	        System.out.println("Error removing loan transaction: " + e.getMessage());
	    }
	}

	public void updateUserLoan(User user, Book book) throws SQLException {
	    // Update the total loan fees for the user based on the loan fee of the book
	    double newTotalLoanFees = user.getTotalLoanFees();
	    user.setTotalLoanFees(newTotalLoanFees);

	    // Update the user's record in the database
	    String sql = "UPDATE Users SET TotalLoanFees = ? WHERE UserID = ?";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        statement.setDouble(1, newTotalLoanFees);
	        statement.setInt(2, Integer.parseInt(user.getUserID()));
	        
	        int rowsUpdated = statement.executeUpdate();
	        if (rowsUpdated > 0) {
	            System.out.println("User's loan fees were updated successfully in the database!");
	        }
	    } catch (Exception e) {
	        System.out.println("Error updating user's loan fees: " + e.getMessage());
	    }
	}
	
	public ArrayList<Book> loadBooks() throws SQLException {
	    ArrayList<Book> books = new ArrayList<>();
	    String sql = "SELECT * FROM Books";
	    
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {
	        while (resultSet.next()) {
	            String bookID = String.valueOf(resultSet.getInt("BookID"));
	            String title = resultSet.getString("Title");
	            String author = resultSet.getString("Author");
	            String ISBN = resultSet.getString("ISBN");
	            int publicationYear = resultSet.getInt("PublicationYear");
	            String genre = resultSet.getString("Genre");
	            boolean loanStatus = resultSet.getBoolean("LoanStatus");
	            double baseLoanFee = resultSet.getDouble("BaseLoanFee");
	            String bookType = resultSet.getString("BookType");
	            
	            // Create book object based on type
	            Book book;
	            switch (bookType) {
	                case "Textbook":
	                    book = new Textbook(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
	                    break;
	                case "Novel":
	                    book = new Novel(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
	                    break;
	                case "ReferenceBook":
	                    book = new ReferenceBook(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
	                    break;
	                default:
	                    continue; // Skip invalid book types
	            }
	            book.setLoanStatus(loanStatus);
	            books.add(book);
	        }
	    }
	    return books;
	}

	public ArrayList<User> loadUsers() throws SQLException {
		ArrayList<User> users = new ArrayList<>();
	    String sql = "SELECT UserID, Name, Email, PhoneNumber, Address, UserType FROM Users";
	    try (Statement statement = connection.createStatement();
	         ResultSet resultSet = statement.executeQuery(sql)) {

	        users.clear(); // Clear the existing list before loading
	        while (resultSet.next()) {
	            String userID = resultSet.getString("UserID");
	            String name = resultSet.getString("Name");
	            String email = resultSet.getString("Email");
	            String phoneNumber = resultSet.getString("PhoneNumber");
	            String address = resultSet.getString("Address");
	            String userType = resultSet.getString("UserType");

	            User user;
	            switch (userType) {
	                case "Student":
	                    user = new Student(userID, name, email, phoneNumber, address);
	                    break;
	                case "Faculty":
	                    user = new Faculty(userID, name, email, phoneNumber, address);
	                    break;
	                case "PublicMember":
	                    user = new PublicMember(userID, name, email, phoneNumber, address);
	                    break;
	                default:
	                    System.out.println("Unknown user type for UserID: " + userID);
	                    continue; // Skip this user
	            }
	            users.add(user); // Add the user to the list
	        }
	        
	    } catch (SQLException e) {
	        System.out.println("Error loading users: " + e.getMessage());
	    }
	    return users;
	}

	public void saveUsers(ArrayList<User> users) throws SQLException {
	    String sql = "INSERT INTO Users (UserID, Name, Email, PhoneNumber, Address, UserType) VALUES (?, ?, ?, ?, ?, ?)"
	               + "ON DUPLICATE KEY UPDATE Name = VALUES(Name), Email = VALUES(Email), PhoneNumber = VALUES(PhoneNumber),"
	               + "Address = VALUES(Address), UserType = VALUES(UserType)";
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        for (User user : users) {
	            statement.setInt(1, Integer.parseInt(user.getUserID()));
	            statement.setString(2, user.getName());
	            statement.setString(3, user.getEmail());
	            statement.setString(4, user.getPhoneNumber());
	            statement.setString(5, user.getAddress());

	            // Save the specific user type
	            if (user instanceof Student) {
	                statement.setString(6, "Student");
	            } else if (user instanceof Faculty) {
	                statement.setString(6, "Faculty");
	            } else if (user instanceof PublicMember) {
	                statement.setString(6, "PublicMember");
	            } else {
	                statement.setString(6, "Unknown");
	            }

	            statement.addBatch();
	        }
	        int[] rowsAffected = statement.executeBatch();
	        
	    } catch (Exception e) {
	        System.out.println("Error saving users: " + e.getMessage());
	    }
	}
	
	public void saveBooks(ArrayList<Book> books) throws SQLException {
	    String sql = "INSERT INTO Books (BookID, Title, Author, ISBN, PublicationYear, Genre, LoanStatus, BaseLoanFee, BookType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
	               + "ON DUPLICATE KEY UPDATE Title = VALUES(Title), Author = VALUES(Author), ISBN = VALUES(ISBN), "
	               + "PublicationYear = VALUES(PublicationYear), Genre = VALUES(Genre), LoanStatus = VALUES(LoanStatus), "
	               + "BaseLoanFee = VALUES(BaseLoanFee), BookType = VALUES(BookType)";
	    
	    try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        for (Book book : books) {
	            statement.setInt(1, Integer.parseInt(book.getBookID()));
	            statement.setString(2, book.getTitle());
	            statement.setString(3, book.getAuthor());
	            statement.setString(4, book.getISBN());
	            statement.setInt(5, book.getPublicationYear());
	            statement.setString(6, book.getGenre());
	            statement.setBoolean(7, book.isLoaned());
	            statement.setDouble(8, book.getBaseLoanFee());
	            // Save the specific user type
	            if (book instanceof Textbook) {
	                statement.setString(9, "TextBook");
	            } else if (book instanceof Novel) {
	                statement.setString(9, "Novel");
	            } else if (book instanceof ReferenceBook) {
	                statement.setString(9, "ReferenceBook");
	            } else {
	                statement.setString(9, "Unknown");
	            }
	           
	            statement.addBatch();
	        }
	        statement.executeBatch();
	    }
	}




	
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


}
