/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package library;

/**
 *
 * @author laksh
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Library {
    private List<LibraryItem> libraryItems;

    public Library() {
        this.libraryItems = new ArrayList<>();
    }

    public void addLibraryItem(LibraryItem item) {
        libraryItems.add(item);
        System.out.println("Library item has been added.");
    }

    public List<LibraryItem> getLibraryItems() {
        return libraryItems;
    }

    public boolean issueItem(String title) {
        for (LibraryItem item : libraryItems) {
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title)) {
                    if (!book.isIssued()) {
                        book.setIssued(true);
                        System.out.println("Book has been issued.");
                        return true;
                    } else {
                        System.out.println("Book is already issued.");
                        return false;
                    }
                }
            }
        }
        System.out.println("Book not found in the library.");
        return false;
    }
    

    public boolean returnItem(String title) {
        for (LibraryItem item : libraryItems) {
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title)) {
                    if (book.isIssued()) {
                        book.setIssued(false);
                        System.out.println("Book has been returned.");
                        return true;
                    } else {
                        System.out.println("Book is not issued.");
                        return false;
                    }
                }
            }
        }
        System.out.println("Book not found in the library.");
        return false;
    }
    

    public void deleteItem(String title) {
        Iterator<LibraryItem> iterator = libraryItems.iterator();
        while (iterator.hasNext()) {
            LibraryItem item = iterator.next();
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title)) {
                    iterator.remove();
                    System.out.println("Book has been deleted from the library.");
                    return;
                }
            }
        }
        System.out.println("Book not found in the library.");
    }
}
