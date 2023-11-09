package speedhome.interview.boot.controller;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import speedhome.interview.boot.model.Member;
import speedhome.interview.boot.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/librarian/members")
public class MemberLibController {
    private final MemberService memberService;

    public MemberLibController(MemberService memberService) {
        this.memberService = memberService;
    }
    // Get all members
    @GetMapping("/list")
    public ResponseEntity<List<Member>> getAllMembers() throws NotFoundException {
        List<Member> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }
    // Add a new member
    @PostMapping("/save")
    public ResponseEntity<Member> saveMember(@RequestBody Member member) {
        Member saveMember = memberService.saveMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveMember);
    }
    // Update an existing member by ID
    @PutMapping("/update/{memberId}")
    public ResponseEntity<Member> updateMember(@PathVariable Long memberId, @RequestBody Member updatedMember) throws NotFoundException {
        Member updated = memberService.updateMember(memberId, updatedMember);
        return ResponseEntity.ok(updated);
    }
   // Delete a member By passing ID
    @DeleteMapping("/remove/{memberId}")
    public ResponseEntity<Void> removeMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
