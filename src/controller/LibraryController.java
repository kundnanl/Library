package controller;

import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import library.Book;
import library.FictionBook;
import library.Library;
import library.LibraryBook;
import library.LibraryItem;

public class LibraryController {
    private Library library;
    private ListView<String> itemList;

    public LibraryController(Library library, ListView<String> itemList) {
        this.library = library;
        this.itemList = itemList;
    }

    public void addBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the book title:");

        dialog.showAndWait().ifPresent(title -> {
            TextInputDialog authorDialog = new TextInputDialog();
            authorDialog.setTitle("Add Book");
            authorDialog.setHeaderText(null);
            authorDialog.setContentText("Enter the book author:");

            authorDialog.showAndWait().ifPresent(author -> {
                Book book = new FictionBook(title, author);
                LibraryItem libraryItem = new LibraryBook(book);
                library.addLibraryItem(libraryItem);
                showAlert("Book added successfully!", false);
            });
        });
        updateItemList();
    }

    public void showLibraryItems() {
        itemList.getItems().clear();
        List<LibraryItem> libraryItems = library.getLibraryItemsFromDatabase();

        for (LibraryItem item : libraryItems) {
            String itemDetails = item.getDisplayDetails();
            itemList.getItems().add(itemDetails);
        }
        updateItemList();
    }

    public void issueBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Issue Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title of the book to issue:");
        dialog.showAndWait().ifPresent(title -> {
            String formattedTitle = title.trim();
            boolean bookFound = library.issueItem(formattedTitle);
            if (bookFound) {
                library.updateIssuedStatusInDatabase(formattedTitle, true);
                showAlert("Book issued successfully.", false);
            } else {
                showAlert("Book not found in the library or already Issued.", true);
            }
        });
        updateItemList();
    }

    public void returnBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Return Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title of the book to return:");
        dialog.showAndWait().ifPresent(title -> {
            boolean bookReturned = library.returnItem(title);
            if (bookReturned) {
                library.updateIssuedStatusInDatabase(title, false);
                showAlert("Book returned successfully.", false);
            } else {
                showAlert("Book not found or not issued.", true);
            }
        });
        updateItemList();
    }

    public void deleteBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title of the book to delete:");
        dialog.showAndWait().ifPresent(title -> {
            boolean bookDeleted = library.deleteItem(title);
            if (bookDeleted) {
                showAlert("Book deleted successfully.", false);
            } else {
                showAlert("Book not found in the library.", true);
            }
        });
        updateItemList();
    }

    private void showAlert(String message, boolean isError) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox root = new VBox(label);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setEffect(new DropShadow(10, Color.BLACK));

        Color backgroundColor = isError ? Color.RED : Color.GREEN;
        Color textColor = isError ? Color.WHITE : Color.BLACK;

        root.setStyle(
                "-fx-background-color: " + toRGBCode(backgroundColor) + "; " +
                        "-fx-background-radius: 10;");

        label.setTextFill(textColor);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);

        // Fade in effect
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), root);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Slide up effect
        TranslateTransition slideUpTransition = new TranslateTransition(Duration.seconds(0.5), root);
        slideUpTransition.setFromY(100);
        slideUpTransition.setToY(0);
        slideUpTransition.setInterpolator(Interpolator.EASE_OUT);

        ParallelTransition parallelTransition = new ParallelTransition(fadeTransition, slideUpTransition);

        // Close the popup after a delay
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(2));
        pauseTransition.setOnFinished(event -> stage.close());

        parallelTransition.setOnFinished(event -> pauseTransition.play());

        stage.show();
        parallelTransition.play();
    }

    private static String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void updateItemList() {
        itemList.getItems().clear();
        List<LibraryItem> libraryItems = library.getLibraryItemsFromDatabase();

        for (LibraryItem item : libraryItems) {
            String itemDetails = item.getDisplayDetails();
            itemList.getItems().add(itemDetails);
        }
    }
}