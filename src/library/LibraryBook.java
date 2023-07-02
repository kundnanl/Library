package library;

public class LibraryBook implements LibraryItem {
    Book book; 
    boolean isIssued;

    public LibraryBook(Book book) {
        this.book = book;
        this.isIssued = false;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public void displayDetails() {
        book.display();
        if (isIssued) {
            System.out.println("Status: Issued");
        } else {
            System.out.println("Status: Available");
        }
    }
}
