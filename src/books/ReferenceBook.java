package books;

public class ReferenceBook extends Book {
    public ReferenceBook(String bookID, String title, String author, String ISBN, int publicationYear, String genre, double baseLoanFee) {
        super(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
    }

    // Reference books cannot be loaned out
    @Override
    public double calculateLoanCost(int loanDuration) {
        return 0; 
    }

    // Reference books are non-loanable and non-extendable
    @Override
    public boolean isExtendable() {
        return false; 
    }
}
