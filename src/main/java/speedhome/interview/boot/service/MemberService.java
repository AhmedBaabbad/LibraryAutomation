package speedhome.interview.boot.service;

import javassist.NotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import speedhome.interview.boot.exception.UsernameAlreadyTakenException;
import speedhome.interview.boot.model.Member;
import speedhome.interview.boot.respository.MemberRepository;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository,
                         PasswordEncoder passwordEncoder){
        this.memberRepository=memberRepository;
        this.passwordEncoder=passwordEncoder;
    }
    // Get all Members
    public List<Member> getAllMembers() throws NotFoundException {
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty()) {
            throw new NotFoundException("No members found");
        }

        return members;
    }

    //  Add a new Member
    public Member saveMember(Member member) {
        // Additional validation or business logic can be added here.
        validateMember(member);
        if (memberRepository.findByUsername(member.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException("Username is already taken");
        }
        // Hash the password before saving it
        member.setPassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member);
    }

    // Update currentMember
    public Member updateMember(Long memberId, Member updatedMember) throws NotFoundException {
        Member currentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        // Update the fields you want to allow the LIBRARIAN to modify.
        currentMember.setUsername(updatedMember.getUsername());
        currentMember.setPassword(passwordEncoder.encode(updatedMember.getPassword()));
        currentMember.setRole(updatedMember.getRole());

        return memberRepository.save(currentMember);
    }
   // delete a member by ID
    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    // Delete a member by ID or username
    public void removeMember(String username) throws NotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Member not found"));

        memberRepository.delete(member);
    }

    // Check if member information to ensure it is complete.
    private void validateMember(Member member) {
        if (member == null || member.getUsername() == null || member.getPassword() == null || member.getRole() == null) {
            throw new IllegalArgumentException("Member information is incomplete");
        }

        if (!isValidRole(member.getRole())) {
            throw new IllegalArgumentException("Invalid member role");
        }

    }

    // Checks if the provided role is "LIBRARIAN".
    private boolean isValidRole(String role) {
        return Member.role.LIBRARIAN.name().equals(role);
    }

}
