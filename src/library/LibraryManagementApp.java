package library;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.List;

public class LibraryManagementApp extends Application {
    private Library library;
    private ListView<String> itemList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        library = new Library();

        primaryStage.setTitle("Library Management");

        // Create UI components
        itemList = new ListView<>();
        Button addButton = new Button("Add Book");
        Button showButton = new Button("Show Items");
        Button issueButton = new Button("Issue Book");
        Button returnButton = new Button("Return Book");
        ButtonBase deleteButton = new Button("Delete Book");
        Label statusLabel = new Label();

        issueButton.setOnAction(event -> showIssueDialogBox());

        returnButton.setOnAction(event -> showReturnDialogBox());

        // Set event handlers for buttons
        addButton.setOnAction(e -> showAddBookDialog());
        showButton.setOnAction(e -> showLibraryItems());
        deleteButton.setOnAction(event -> {
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
        });
        

        // Create layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addButton, showButton, issueButton, returnButton,deleteButton);

        VBox.setVgrow(itemList, Priority.ALWAYS);
        VBox.setMargin(itemList, new Insets(0, 0, 10, 0));

        root.getChildren().addAll(itemList, buttonBox, statusLabel);

        // Set the scene
        Scene scene = new Scene(root, 600, 400);
        String cssPath = "styles/styles.css";
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddBookDialog() {
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
    }

    private void showLibraryItems() {
        itemList.getItems().clear();
        List<LibraryItem> libraryItems = library.getLibraryItems();

        for (LibraryItem item : libraryItems) {
            String itemDetails = item.getDisplayDetails();
            itemList.getItems().add(itemDetails);
        }
    }

    private void showIssueDialogBox() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Issue Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title of the book to issue:");
        dialog.showAndWait().ifPresent(title -> {
            boolean bookFound = library.issueItem(title);
            if (bookFound) {
                showAlert("Book issued successfully.", false);
            } else {
                showAlert("Book not found in the library.", true);
            }
        });
    }
    
    private void showReturnDialogBox() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Return Book");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter the title of the book to return:");
        dialog.showAndWait().ifPresent(title -> {
            boolean bookReturned = library.returnItem(title);
            if (bookReturned) {
                showAlert("Book returned successfully.", false);
            } else {
                showAlert("Book not found or not issued.", true);
            }
        });
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

}
