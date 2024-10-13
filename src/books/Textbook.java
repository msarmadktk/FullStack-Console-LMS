package books;

public class Textbook extends Book {
    private boolean extended = false;

    public Textbook(String bookID, String title, String author, String ISBN, int publicationYear, String genre, double baseLoanFee) {
        super(bookID, title, author, ISBN, publicationYear, genre, baseLoanFee);
    }

    @Override
    public double calculateLoanCost(int loanDuration) {
        double durationCost = loanDuration * 1.5; 
        return getBaseLoanFee() + durationCost;
    }

    @Override
    public boolean isExtendable() {
        return !extended;
    }

    public void extendLoan() {
        if (isExtendable()) {
            extended = true;
        } else {
            System.out.println("This textbook cannot be extended further.");
        }
    }
}

