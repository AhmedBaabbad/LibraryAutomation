package speedhome.interview.boot.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import speedhome.interview.boot.model.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
