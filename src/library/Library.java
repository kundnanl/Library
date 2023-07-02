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
import java.util.List;

 
public class Library {
    List<LibraryItem> libraryItems;

    public Library() {
        this.libraryItems = new ArrayList<>();
    }

    void addLibraryItem(LibraryItem item) {
        libraryItems.add(item);
        System.out.println("Library item has been added.");
    }

    void showAvailableItems() {
        System.out.println("Available Library Items:");
        for (int i = 0; i < libraryItems.size(); i++) {
            System.out.print(i + ". ");
            libraryItems.get(i).displayDetails();
        }
    }

    void issueItem(String title) {
        for (LibraryItem item : libraryItems) {
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title) && !book.isIssued()) {
                    book.setIssued(true);
                    System.out.println("Book has been issued.");
                    return;
                }
            }
        }
        System.out.println("Book not found or already issued.");
    }

    void returnItem(String title) {
        for (LibraryItem item : libraryItems) {
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title) && book.isIssued()) {
                    book.setIssued(false);
                    System.out.println("Book has been returned.");
                    return;
                }
            }
        }
        System.out.println("Book not found or not issued.");
    }
}