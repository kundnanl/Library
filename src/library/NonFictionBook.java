package library;

class NonFictionBook extends Book {
    public NonFictionBook(String title, String author) {
        super(title, author);
    }
 
    @Override
    void display() {
        System.out.println("Non-Fiction Book - " + title + " by " + author);
    }
}
