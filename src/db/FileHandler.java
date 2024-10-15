package db;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import books.Book;
import books.Novel;
import books.ReferenceBook;
import books.Textbook;
import demo.Library;
import transactions.LoanTransaction;
import users.Faculty;
import users.PublicMember;
import users.Student;
import users.User;

public class FileHandler implements PersistentDBHandler {
	private static final String USER_CSV_FILE = "users.csv";
	private static final String BOOK_CSV_FILE = "books.csv";
	private static final String LOAN_TRANSACTION_CSV_FILE = "loan_transactions.csv";

    private Library library; // Reference to the Library object

    // Constructor that accepts a Library object
    
    public FileHandler(Library library) {
        this.library = library;
    }
	// Method to save users to CSV file
	public void saveUsers(ArrayList<User> users) {
	    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(USER_CSV_FILE), 
	            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) { // Append mode and create if not exists
	        if (Files.size(Paths.get(USER_CSV_FILE)) == 0) { // Check if file is empty
	            writer.write("UserID,Name,Email,PhoneNumber,Address,UserType\n"); // Write header
	        }
	        for (User user : users) {
	            writer.write(String.format("%s,%s,%s,%s,%s,%s\n", user.getUserID(), user.getName(), user.getEmail(),
	                    user.getPhoneNumber(), user.getAddress(), user.getUserType()));
	        }
	        System.out.println("Users saved to CSV file successfully!");
	    } catch (IOException e) {
	        System.out.println("Error saving users: " + e.getMessage());
	    }
	}

	// Method to load users from CSV file
	public ArrayList<User> loadUsers() {
		ArrayList<User> users = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(USER_CSV_FILE))) {
			String line;
			reader.readLine(); // Skip header
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String userID = parts[0];
				String name = parts[1];
				String email = parts[2];
				String phoneNumber = parts[3];
				String address = parts[4];
				String userType = parts[5];

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
				
					continue; // Skip invalid user types
				}
				users.add(user);
			}
		
		} catch (IOException e) {
			System.out.println("Error loading users: " + e.getMessage());
		}
		return users;
	}

	// Method to save books to CSV file
	public void saveBooks(ArrayList<Book> books) {
	    try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(BOOK_CSV_FILE), 
	            StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) { // Append mode and create if not exists
	        if (Files.size(Paths.get(BOOK_CSV_FILE)) == 0) { // Check if file is empty
	            writer.write("BookID,Title,Author,ISBN,PublicationYear,Genre,LoanStatus,BaseLoanFee,BookType\n"); // Write header
	        }
	        for (Book book : books) {
	            writer.write(String.format("%s,%s,%s,%s,%d,%s,%b,%.2f,%s\n", book.getBookID(), book.getTitle(),
	                    book.getAuthor(), book.getISBN(), book.getPublicationYear(), book.getGenre(), book.isLoaned(),
	                    book.getBaseLoanFee(), book.getBookType()));
	        }
	      
	    } catch (IOException e) {
	        System.out.println("Error saving books: " + e.getMessage());
	    }
	}

	// Method to load books from CSV file
	public ArrayList<Book> loadBooks() {
		ArrayList<Book> books = new ArrayList<>();
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(BOOK_CSV_FILE))) {
			String line;
			reader.readLine(); // Skip header
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				String bookID = parts[0];
				String title = parts[1];
				String author = parts[2];
				String ISBN = parts[3];
				int publicationYear = Integer.parseInt(parts[4]);
				String genre = parts[5];
				boolean loanStatus = Boolean.parseBoolean(parts[6]);
				double baseLoanFee = Double.parseDouble(parts[7]);
				String bookType = parts[8];

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
		
		} catch (IOException e) {
			System.out.println("Error loading books: " + e.getMessage());
		}
		return books;
	}

	// Method to save loan transactions to CSV file
	   public void saveTransactions(ArrayList<LoanTransaction> transactions) {
	        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(LOAN_TRANSACTION_CSV_FILE))) {
	            // Write header
	            writer.write("LoanID,UserID,BookID,LoanDate,ReturnDate,TotalFee,Extended\n");

	            // Write each loan transaction
	            for (LoanTransaction transaction : transactions) {
	                writer.write(String.format("%s,%s,%s,%s,%s,%.2f,%b\n",
	                        transaction.getLoanID(),
	                        transaction.getUser().getUserID(),
	                        transaction.getBook().getBookID(),
	                        transaction.getLoanDate().toString(),
	                        transaction.getReturnDate() != null ? transaction.getReturnDate().toString() : "",
	                        transaction.getTotalFee(),
	                        transaction.isExtended()));
	            }

	        } catch (IOException e) {
	            System.out.println("Error saving loan transactions: " + e.getMessage());
	        }
	    }

	// Method to load loan transactions from CSV file
	   public ArrayList<LoanTransaction> loadTransactions() {
	        ArrayList<LoanTransaction> transactions = new ArrayList<>();
	        try (BufferedReader reader = Files.newBufferedReader(Paths.get(LOAN_TRANSACTION_CSV_FILE))) {
	            String line;
	            reader.readLine(); // Skip header

	            while ((line = reader.readLine()) != null) {
	                String[] parts = line.split(",");

	                // Parse data from the CSV
	                String loanID = parts[0];
	                String userID = parts[1];
	                String bookID = parts[2];
	                LocalDate loanDate = LocalDate.parse(parts[3]);
	                LocalDate returnDate = parts.length > 4 && !parts[4].isEmpty() ? LocalDate.parse(parts[4]) : null;
	                double totalFee = parts.length > 5 ? Double.parseDouble(parts[5]) : 0.0;
	                boolean extended = parts.length > 6 && Boolean.parseBoolean(parts[6]);

	                // Find the corresponding User and1 Book objects
	                
	                User user = library.findUserByID(userID);
	                Book book = library.findBookByID(bookID);

	                if (user != null && book != null) {
	                    // Create a new LoanTransaction with the loaded data
	                    LoanTransaction transaction = new LoanTransaction(loanID, user, book, loanDate, returnDate, totalFee, extended);
	                    transactions.add(transaction);
	                }
	            }

	        } catch (IOException e) {
	            System.out.println("Error loading loan transactions: " + e.getMessage());
	        }
	        return transactions;
	    }


	public void addUser(User user) {
		File file = new File("users.csv");
		boolean fileExists = file.exists();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // 'true' for append mode
			if (!fileExists) {
				// Write header if file didn't exist
				writer.write("UserID,Name,Email,PhoneNumber,Address,UserType");
				writer.newLine();
			}
			writer.write(String.join(",", user.getUserID(), user.getName(), user.getEmail(), user.getPhoneNumber(),
					user.getAddress(), user.getUserType()));
			writer.newLine();
			
		} catch (IOException e) {
			System.out.println("Error writing to users.csv: " + e.getMessage());
		}
	}

	public void removeUser(int userId) {
		File inputFile = new File("users.csv");
		File tempFile = new File("temp_users.csv");

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			String line;
			boolean found = false;

			while ((line = reader.readLine()) != null) {
				if (!line.startsWith(String.valueOf(userId))) { // Check if line starts with the UserID
					writer.write(line);
					writer.newLine();
				} else {
					found = true;
				}
			}

			if (found) {
				System.out.println("User was deleted successfully!");
			} else {
				System.out.println("No user found with the specified UserID.");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Delete the original file and rename the temp file
		inputFile.delete();
		tempFile.renameTo(inputFile);
	}

	public void addBook(Book book) {
		File file = new File("books.csv");
		boolean fileExists = file.exists();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // 'true' for append mode
			if (!fileExists) {
				// Write header if file didn't exist
				writer.write("BookID,Title,Author,ISBN,PublicationYear,Genre,LoanStatus,BaseLoanFee,BookType");
				writer.newLine();
			}
			writer.write(String.join(",", book.getBookID(), book.getTitle(), book.getAuthor(), book.getISBN(),
					String.valueOf(book.getPublicationYear()), book.getGenre(), String.valueOf(book.isLoaned()),
					String.valueOf(book.getBaseLoanFee()), book.getBookType()));
			writer.newLine();
			System.out.println("A new book was added to books.csv successfully!");
		} catch (IOException e) {
			System.out.println("Error writing to books.csv: " + e.getMessage());
		}
	}

	public void removeBook(int bookID) {
		File inputFile = new File("books.csv");
		File tempFile = new File("temp_books.csv");

		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

			String line;
			boolean found = false;

			while ((line = reader.readLine()) != null) {
				if (!line.startsWith(String.valueOf(bookID))) { // Check if line starts with the BookID
					writer.write(line);
					writer.newLine();
				} else {
					found = true;
				}
			}

			if (found) {
				System.out.println("Book was deleted successfully!");
			} else {
				System.out.println("No book found with the specified BookID.");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Delete the original file and rename the temp file
		inputFile.delete();
		tempFile.renameTo(inputFile);
	}

	public void addTransaction(int loanID, int userID, int bookID, LocalDate loanDate, LocalDate returnDate) {
		File file = new File("transactions.csv");
		boolean fileExists = file.exists();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) { // 'true' for append mode
			if (!fileExists) {
				// Write header if file didn't exist
				writer.write("LoanID,UserID,BookID,LoanDate,ReturnDate");
				writer.newLine();
			}
			writer.write(String.join(",", String.valueOf(loanID), String.valueOf(userID), String.valueOf(bookID),
					loanDate.toString(), returnDate != null ? returnDate.toString() : ""));
			writer.newLine();
			System.out.println("A new loan transaction was added to transactions.csv successfully!");
		} catch (IOException e) {
			System.out.println("Error writing to transactions.csv: " + e.getMessage());
		}
	}

	@Override
	public void removeTransaction(int userID, int bookID) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUserLoan(User user, Book book) throws SQLException {
		// TODO Auto-generated method stub

	}

}
