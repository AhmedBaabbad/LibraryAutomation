package speedhome.interview.boot.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedhome.interview.boot.exception.BookNotFoundException;
import speedhome.interview.boot.exception.InvalidBookStatusException;
import speedhome.interview.boot.model.Book;
import speedhome.interview.boot.respository.BookRepository;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService (BookRepository bookRepository) {
        this.bookRepository=bookRepository;
    }
    // Add a new book
    public Book addBook(Book book) {
        // Check if the status is valid
        if (book.getStatus() == null || (!book.getStatus().equals(Book.Status.AVAILABLE) &&
                !book.getStatus().equals(Book.Status.BORROWED))) {
            throw new InvalidBookStatusException("Invalid book status. Must be either AVAILABLE or BORROWED.");
        }
        return bookRepository.save(book);
    }

    // Update an existing book
    public Book updateBook(Long bookId, Book updatedBook) {
        Book currentBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found"));
        // You can update book fields as needed
        currentBook.setTitle(updatedBook.getTitle());
        currentBook.setAuthor(updatedBook.getAuthor());
        currentBook.setStatus(updatedBook.getStatus());

        return bookRepository.save(currentBook);
    }

    // Remove a book by ID
    public void removeBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    // Retrieves  A list of books with the status "AVAILABLE".
    public List<Book> getAvailableBooks() {
        return bookRepository.findByStatus(String.valueOf(Book.Status.AVAILABLE));
    }

   // Borrows a book with the specified ID if it is available.
    public Book borrowBook(Long bookId) throws NotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (Book.Status.AVAILABLE.equals(book.getStatus())) {
            book.setStatus(String.valueOf(Book.Status.BORROWED));
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book is not available for borrowing");
        }
    }

    public Book returnBook(Long bookId) throws NotFoundException {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found"));

        if (Book.Status.BORROWED.equals(book.getStatus())) {
            book.setStatus(String.valueOf(Book.Status.AVAILABLE));
            return bookRepository.save(book);
        } else {
            throw new IllegalArgumentException("Book is not borrowed");
        }
    }

}
