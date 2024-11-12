package com.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class LibraryService {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    // Add a new book
    public void addBook(String title, String author) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Book(title, author, true));
        transaction.commit();
        session.close();
        System.out.println("Book added successfully.");
    }

    // Add a new member
    public void addMember(String name, String email) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Member(name, email));
        transaction.commit();
        session.close();
        System.out.println("Member added successfully.");
    }

    // View all books
    public void viewBooks() {
        Session session = sessionFactory.openSession();
        List<Book> books = session.createQuery("from Book", Book.class).list();
        for (Book book : books) {
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", Available: " + book.isAvailable());
        }
        session.close();
    }

    // Borrow a book
    public void borrowBook(int bookId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Book book = session.get(Book.class, bookId);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            session.update(book);
            transaction.commit();
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is not available.");
        }
        session.close();
    }

    // Return a book
    public void returnBook(int bookId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Book book = session.get(Book.class, bookId);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            session.update(book);
            transaction.commit();
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book was not borrowed.");
        }
        session.close();
    }
}
