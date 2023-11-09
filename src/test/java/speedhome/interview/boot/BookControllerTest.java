package speedhome.interview.boot;

import javassist.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import speedhome.interview.boot.controller.BookController;
import speedhome.interview.boot.controller.MemberController;
import speedhome.interview.boot.model.Book;
import speedhome.interview.boot.service.BookService;
import speedhome.interview.boot.service.MemberService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {


    @Mock
    private BookService bookService;
    @Mock
    private MemberService memberService;

    @InjectMocks
    private BookController bookController;

    @InjectMocks
    private MemberController memberController;

    @Test
    public void testAddBook() {
        // Setup test data
        Book book = new Book();
        when(bookService.addBook(any(Book.class))).thenReturn(book);

        // Perform the test
        ResponseEntity<Book> response = bookController.addBook(book);

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(book, response.getBody());
    }

    @Test
    public void testUpdateBook() {
        // Setup test data
        Long bookId = 1L;
        Book updatedBook = new Book();
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(updatedBook);

        // Perform the test
        ResponseEntity<Book> response = bookController.updateBook(bookId, updatedBook);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedBook, response.getBody());
    }


    @Test
    public void testRemoveBook() {
        // Setup test data
        Long bookId = 1L;

        // Perform the test
        ResponseEntity<Void> response = bookController.removeBook(bookId);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAvailableBooks() {
        // Arrange
        List<Book> availableBooks = Arrays.asList(new Book(), new Book());
        when(bookService.getAvailableBooks()).thenReturn(availableBooks);

        // Act
        ResponseEntity<List<Book>> response = memberController.getAvailableBooks();

        // Assert
        assertEquals(availableBooks, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testBorrowBook() throws NotFoundException, IllegalArgumentException {
        // Mock data
        long bookId = 1L;
        Book mockBorrowedBook = new Book();

        // Mock the behavior of the bookService
        Mockito.when(bookService.borrowBook(bookId)).thenReturn(mockBorrowedBook);

        // Perform the test
        ResponseEntity<Book> response = memberController.borrowBook(bookId);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBorrowedBook, response.getBody());
    }

    @Test
    public void returnBook () throws NotFoundException, IllegalArgumentException {
        // Mock data
        long bookId = 1L;
        Book mockReturnBook = new Book();

        // Mock the behavior of the bookService
        Mockito.when(bookService.borrowBook(bookId)).thenReturn(mockReturnBook);

        // Perform the test
        ResponseEntity<Book> response = memberController.returnBook(bookId);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockReturnBook, response.getBody());
    }


    @Test
    public void deleteAccount() throws NotFoundException {
        // Mock data
        String username = "testUser"; // Replace with an actual username
        UserDetails mockUserDetails = new User(username, "password", new ArrayList<>());

        // Mock the behavior of the authentication principal
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(mockUserDetails, null));

        // Mock the behavior of the memberService
        Mockito.doNothing().when(memberService).removeMember(username);

        // Perform the test
        ResponseEntity<Void> response = memberController.deleteAccount(mockUserDetails);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}
