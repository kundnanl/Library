# Library Management System

A Library Management System implemented in Java using JavaFX for the graphical user interface.

## Description

The Library Management System is designed to manage books in a library. It allows users to add books, view available books, issue books to borrowers, return books, and delete books from the library.

## Features

- Add new books to the library
- Display available books in the library
- Issue books to borrowers
- Return books to the library
- Delete books from the library

## Installation

1. Clone the repository:
git clone https://github.com/kundnanl/Library-Management-System.git


2. Set up the MySQL Database:
- Install MySQL if you haven't already.
- Create a MySQL database named `library_db`.
- Create a table named `books` with columns: `id` (INT, auto-increment), `title` (VARCHAR), `author` (VARCHAR), `is_issued` (BOOLEAN).

3. Configure MySQL Connection:
- Open the `Main.java` file in your Java IDE.
- Update the database connection URL, username, and password in the `databaseConnection` variable.

4. Open the project in your Java IDE (e.g., IntelliJ IDEA, Eclipse).

5. Build and run the application.

## Usage
1. Launch the application.

2. Select an action from the menu options:
- Add a new book
- View available books
- Issue a book to a borrower
- Return a book to the library
- Delete a book from the library

3. Follow the on-screen prompts to perform the desired action.

## Technologies Used
- Java
- JavaFX
- MySQL

## Project Structure
- css
- src
 - library
   - Book.java
   - FictionBook.java
   - NonFictionBook.java
   - LibraryItem.java
   - LibraryBook.java
   - Library.java
 - Main.java
- styles.css
- README.md

## Roadmap
- Implement a search functionality to search for books by title or author.
- Implement user authentication for librarian and borrower roles.
- Generate reports of issued books and overdue books.

## Contributing
Contributions are welcome! Please follow the guidelines in CONTRIBUTING.md for bug reports, feature requests, and code contributions.
