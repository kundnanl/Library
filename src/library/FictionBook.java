package library;


public class FictionBook extends Book {
    public FictionBook(String title, String author) {
        super(title, author);
    }

    @Override
    void display() {
        System.out.println("Fiction Book - " + title + " by " + author);
    } 
}