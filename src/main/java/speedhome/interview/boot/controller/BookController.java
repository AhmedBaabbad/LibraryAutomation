package speedhome.interview.boot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedhome.interview.boot.model.Book;
import speedhome.interview.boot.service.BookService;
import speedhome.interview.boot.service.MemberService;

@RestController
@RequestMapping("/librarian/books")
public class BookController {
    private final BookService bookService;

    public BookController (BookService bookService, MemberService memberService){
        this.bookService=bookService;
    }
    // Add a new book
    @PostMapping("/save")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book addedBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
    }
    // Update an existing book by ID
    @PutMapping("/update/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
        Book updated = bookService.updateBook(bookId, updatedBook);
        return ResponseEntity.ok(updated);
    }
    // Remove a book by ID
    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Void> removeBook(@PathVariable Long bookId) {
        bookService.removeBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
