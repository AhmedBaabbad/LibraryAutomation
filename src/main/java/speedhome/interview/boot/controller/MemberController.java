package speedhome.interview.boot.controller;

import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import speedhome.interview.boot.model.Book;
import speedhome.interview.boot.service.BookService;
import speedhome.interview.boot.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    private final BookService bookService;

    public MemberController(MemberService memberService, BookService bookService) {
        this.memberService = memberService;
        this.bookService =bookService;
    }
    // Get all available books
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        List<Book> availableBooks = bookService.getAvailableBooks();
        return ResponseEntity.ok(availableBooks);
    }

    // Borrow a book by ID
    @PostMapping("/borrow/{bookId}")
    public ResponseEntity<Book> borrowBook(@PathVariable Long bookId) throws NotFoundException, IllegalArgumentException {
        Book borrowedBook = bookService.borrowBook(bookId);
        return ResponseEntity.ok(borrowedBook);
    }

    // Return a borrowed book by ID
    @PostMapping("/return/{bookId}")
    public ResponseEntity<Book> returnBook(@PathVariable Long bookId) throws NotFoundException, IllegalArgumentException {
        Book returnedBook = bookService.returnBook(bookId);
        return ResponseEntity.ok(returnedBook);
    }

    // delete member based on username
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAccount(@AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {
        String username = userDetails.getUsername();
        memberService.removeMember(username);
        return ResponseEntity.noContent().build();
    }

}
