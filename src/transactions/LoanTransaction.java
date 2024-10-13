package transactions;
import books.Book;
import users.User;
import java.time.LocalDate;

public class LoanTransaction {
    private String loanID;
    private User user;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private double totalFee;
    private boolean extended;  // New attribute to track loan extension

    public LoanTransaction(String loanID, User user, Book book, LocalDate loanDate, LocalDate returnDate) {
        this.loanID = loanID;
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.totalFee = 0;
        this.extended = false;  // Initialize as not extended
    }

    public String getLoanID() {
        return loanID;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public double calculateTotalFee(int loanDuration, int lateDays) {
        totalFee = book.calculateLoanCost(loanDuration);
        if (lateDays > 0) {
            totalFee += lateDays * 2.0; 
        }
        return totalFee;
    }

    
    public void requestExtension() {
        if (!extended) {
            this.returnDate = this.returnDate.plusDays(7); // Extend by 7 days
            extended = true;
            System.out.println("Loan extension granted. New return date: " + returnDate);
        } else {
            System.out.println("Error: Loan has already been extended once.");
        }
    }


    public void displayTransactionDetails() {
        System.out.println("Loan ID: " + loanID);
        System.out.println("User: " + user.getName());
        System.out.println("Book: " + book.getTitle());
        System.out.println("Loan Date: " + loanDate);
        System.out.println("Return Date: " + returnDate);
        System.out.println("Total Fee: $" + totalFee);
    }
}
