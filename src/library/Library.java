package library;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author laksh
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Library {
    private List<LibraryItem> libraryItems;
    private Connection databaseConnection;

    public Library(Connection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.libraryItems = new ArrayList<>();
    }

    public void addLibraryItem(LibraryItem item) {
        libraryItems.add(item);
        insertLibraryItemIntoDatabase(item);
        System.out.println("Library item has been added.");
    }

    public boolean issueItem(String title) {
        List<LibraryItem> items = new ArrayList<>(getLibraryItemsFromDatabase());
        for (LibraryItem item : items) {
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.trim().equalsIgnoreCase(title.trim())) {
                    if (!book.isIssued()) {
                        updateIssuedStatusInDatabase(book.book.title, true);

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

    public List<LibraryItem> getLibraryItemsFromDatabase() {
        List<LibraryItem> items = new ArrayList<>();
        String selectQuery = "SELECT title, author, is_issued FROM books";

        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(selectQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                boolean isIssued = resultSet.getBoolean("is_issued");

                System.out.println("Retrieved from DB: " + title + " - " + author + " - " + isIssued);

                Book book = new FictionBook(title, author);
                LibraryItem libraryItem = new LibraryBook(book);
                ((LibraryBook) libraryItem).setIssued(isIssued);
                items.add(libraryItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    private void insertLibraryItemIntoDatabase(LibraryItem item) {
        if (item instanceof LibraryBook) {
            LibraryBook book = (LibraryBook) item;
            String title = book.book.title;
            String author = book.book.author;
            boolean isIssued = book.isIssued();

            String insertQuery = "INSERT INTO books (title, author, is_issued) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, author);
                preparedStatement.setBoolean(3, isIssued);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean returnItem(String title) {
        List<LibraryItem> items = new ArrayList<>(getLibraryItemsFromDatabase());
        for (LibraryItem item : items) {
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title)) {
                    if (book.isIssued()) {
                        updateIssuedStatusInDatabase(book.book.title, false);

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

    public boolean deleteItem(String title) {
        List<LibraryItem> items = new ArrayList<>(getLibraryItemsFromDatabase());
        Iterator<LibraryItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            LibraryItem item = iterator.next();
            if (item instanceof LibraryBook) {
                LibraryBook book = (LibraryBook) item;
                if (book.book.title.equalsIgnoreCase(title)) {
                    iterator.remove();
                    System.out.println("Book has been deleted from the library.");
                    return true;
                }
            }
        }
        System.out.println("Book not found in the library.");
        return false;
    }

    public void updateIssuedStatusInDatabase(String title, boolean isIssued) {
        String updateQuery = "UPDATE books SET is_issued = ? WHERE title = ?";

        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(updateQuery)) {
            preparedStatement.setBoolean(1, isIssued);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
