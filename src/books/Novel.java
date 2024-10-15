package books;

public class Novel extends Book {
    public Novel(String bookID, String title, String author, String ISBN, int publicationYear, String genre, double baseLoanFee) {
        super(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee, "Novel");
    }

    @Override
    public double calculateLoanCost(int loanDuration) {
        return getBaseLoanFee();  // Flat rate for novels
    }

    @Override
    public boolean isExtendable() {
        return false;  // Novels cannot be extended
    }

    @Override
    public String toString() {
        return "Novel: " + getTitle() + " by " + getAuthor();
    }
}
