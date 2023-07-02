package library;

import java.util.*; 

public class Testing {
    public static void main(String[] args) {
        Library centralLibrary = new Library();

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Central Library!");
        System.out.println("------------------------------");
        System.out.println("What would you like to do?");
        System.out.println("1. Add a Fiction Book");
        System.out.println("2. Add a Non-Fiction Book");
        System.out.println("3. Show Available Library Items");
        System.out.println("4. Issue a Book"); 
        System.out.println("5. Return a Book");
        System.out.println("0. Exit");

        int choice;

        do {
            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Fiction book title: ");
                    String fictionTitle = sc.nextLine();
                    System.out.print("Enter Fiction book author: ");
                    String fictionAuthor = sc.nextLine();
                    Book fictionBook = new FictionBook(fictionTitle, fictionAuthor);
                    LibraryItem fictionLibraryItem = new LibraryBook(fictionBook);
                    centralLibrary.addLibraryItem(fictionLibraryItem);
                    break;
                case 2:
                    System.out.print("Enter Non-Fiction book title: ");
                    String nonFictionTitle = sc.nextLine();
                    System.out.print("Enter Non-Fiction book author: ");
                    String nonFictionAuthor = sc.nextLine();
                    Book nonFictionBook = new NonFictionBook(nonFictionTitle, nonFictionAuthor);
                    LibraryItem nonFictionLibraryItem = new LibraryBook(nonFictionBook);
                    centralLibrary.addLibraryItem(nonFictionLibraryItem);
                    break;
                case 3:
                    centralLibrary.showAvailableItems();
                    break;
                case 4:
                    System.out.print("Enter the title of the book to issue: ");
                    String issueTitle = sc.nextLine();
                    centralLibrary.issueItem(issueTitle);
                    break;
                case 5:
                    System.out.print("Enter the title of the book to return: ");
                    String returnTitle = sc.nextLine();
                    centralLibrary.returnItem(returnTitle);
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 0);
    }
}
//this is the Testing Class