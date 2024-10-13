package db;

import books.Book;
import users.User;
import transactions.LoanTransaction;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileHandler implements PersistentDBHandler {
    private static final String USERS_FILE = "users.csv";
    private static final String BOOKS_FILE = "books.csv";
    private static final String TRANSACTIONS_FILE = "transactions.csv";

    @Override
    public void addUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(user.getUserID() + "," + user.getName() + "," + user.getEmail() + "," +
                    user.getPhoneNumber() + "," + user.getAddress() + "," + user.getTotalLoanFees() + "," +
                    user.getUnpaidFines());
            writer.newLine();
        }
    }

    @Override
    public void addBook(Book book) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE, true))) {
            writer.write(book.getBookID() + "," + book.getTitle() + "," + book.getAuthor() + "," +
                    book.getISBN() + "," + book.getPublicationYear() + "," + book.getGenre() + "," +
                    book.getBaseLoanFee() + "," + book.isLoaned() + "," + book.isDamaged());
            writer.newLine();
        }
    }

    @Override
    public void addLoanTransaction(LoanTransaction transaction) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTIONS_FILE, true))) {
            writer.write(transaction.getLoanID() + "," + transaction.getUser().getUserID() + "," +
                    transaction.getBook().getBookID() + "," + transaction.getLoanDate() + "," +
                    transaction.getReturnDate() + "," + transaction.calculateTotalFee(0, 0));
            writer.newLine();
        }
    }

    @Override
    public List<User> getAllUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User(data[0], data[1], data[2], data[3], data[4]) {
                    @Override
                    public int getMaxBooks() {
                        return 5;
                    }
                };
                users.add(user);
            }
        }
        return users;
    }

    @Override
    public List<Book> getAllBooks() throws IOException {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Parse and create a Book object here (based on your Book subclass implementations)
            }
        }
        return books;
    }

    @Override
    public List<LoanTransaction> getAllLoanTransactions() throws IOException {
        List<LoanTransaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Parse and create a LoanTransaction object here
            }
        }
        return transactions;
    }

	@Override
	public void removeUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User getUser(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeBook(String bookId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Book getBook(String bookId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateLoanTransaction(LoanTransaction transaction) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LoanTransaction getLoanTransaction(int loanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void calculateFines() {
		// TODO Auto-generated method stub
		
	}
}
