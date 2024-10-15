package books;

public abstract class Book {
    private String bookID;
    private String title;
    private String author;
    private String ISBN;
    private int publicationYear;
    private String genre;
    private boolean loanStatus;
    private double baseLoanFee;
    private boolean isDamaged;  // New attribute for damage status
    private String bookType;  // New attribute for book type

    public Book(String bookID, String title, String author, String ISBN, int publicationYear, String genre, double baseLoanFee, String bookType) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.baseLoanFee = baseLoanFee;
        this.loanStatus = false; 
        this.isDamaged = false;  // Initialize as not damaged
        this.bookType = bookType;  // Initialize book type
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isLoaned() {
        return loanStatus;
    }

    public void setLoanStatus(boolean status) {
        this.loanStatus = status;
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public void markAsDamaged() {
        this.isDamaged = true;
        System.out.println("Book '" + title + "' has been marked as damaged.");
    }

    public double getBaseLoanFee() {
        return baseLoanFee;
    }

    public String getBookType() {
        return bookType;  // Getter for bookType
    }

    // Abstract method for loan calculation
    public abstract double calculateLoanCost(int loanDuration);

    // Abstract method for extension check
    public abstract boolean isExtendable();

    @Override
    public abstract String toString();
}
