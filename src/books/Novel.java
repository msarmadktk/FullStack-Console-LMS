package books;

public class Novel extends Book {
    public Novel(String bookID, String title, String author, String ISBN, int publicationYear, String genre, double baseLoanFee) {
        super(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
    }

    // Flat rate for novels
    @Override
    public double calculateLoanCost(int loanDuration) {
        return getBaseLoanFee(); 
    }
    
    // Novels cannot be extended
    @Override
    public boolean isExtendable() {
        return false; 
    }
}

