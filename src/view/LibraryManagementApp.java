package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import controller.LibraryController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.Library;

public class LibraryManagementApp extends Application {
    private Connection databaseConnection;
    private LibraryController controller;
    private ListView<String> itemList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            databaseConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library_db",
                    "root",
                    "mysql");


            primaryStage.setTitle("Library Management");

            itemList = new ListView<>();
            Button addButton = new Button("Add Book");
            Button showButton = new Button("Show Items");
            Button issueButton = new Button("Issue Book");
            Button returnButton = new Button("Return Book");
            ButtonBase deleteButton = new Button("Delete Book");
            Label statusLabel = new Label();

            controller = new LibraryController(new Library(databaseConnection), itemList);

            addButton.setOnAction(e -> controller.addBook());
            showButton.setOnAction(e -> controller.showLibraryItems());
            issueButton.setOnAction(e -> controller.issueBook());
            returnButton.setOnAction(e -> controller.returnBook());
            deleteButton.setOnAction(e -> controller.deleteBook());


            // Create layout
            VBox root = new VBox(10);
            root.setPadding(new Insets(10));
            root.setAlignment(Pos.CENTER);
            root.setStyle("-fx-background-color: #f4f4f4;");

            HBox buttonBox = new HBox(10);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.getChildren().addAll(addButton, showButton, issueButton, returnButton, deleteButton);

            VBox.setVgrow(itemList, Priority.ALWAYS);
            VBox.setMargin(itemList, new Insets(0, 0, 10, 0));

            root.getChildren().addAll(itemList, buttonBox, statusLabel);

            // Set the scene
            Scene scene = new Scene(root, 600, 400);
            String cssPath = "styles/styles.css";
            scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
