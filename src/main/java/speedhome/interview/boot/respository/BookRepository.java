package speedhome.interview.boot.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedhome.interview.boot.model.Book;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByStatus(String status);
}
