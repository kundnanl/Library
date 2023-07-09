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
    public String getDisplayDetails() {
        String status = isIssued ? "Status: Issued" : "Status: Available";
        
        return "Book: " + book.title + " by " + book.author + " - " + status;
    }

}
