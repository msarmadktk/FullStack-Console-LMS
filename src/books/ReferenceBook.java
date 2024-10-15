package books;

public class ReferenceBook extends Book {
    public ReferenceBook(String bookID, String title, String author, String ISBN, int publicationYear, String genre, double baseLoanFee) {
        super(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee, "ReferenceBook");
    }

    @Override
    public double calculateLoanCost(int loanDuration) {
        return 0;  // Reference books cannot be loaned out
    }

    @Override
    public boolean isExtendable() {
        return false;  // Reference books are non-loanable and non-extendable
    }

    @Override
    public String toString() {
        return "Reference Book: " + getTitle() + " by " + getAuthor();
    }
}
