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
import speedhome.interview.boot.controller.MemberLibController;
import speedhome.interview.boot.model.Member;
import speedhome.interview.boot.service.MemberService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberLibController memberLibController;

    @Test
    public void testGetAllMembers() throws NotFoundException {
        // Mock data
        List<Member> mockMembers = Arrays.asList(new Member(), new Member());

        // Mock the behavior of the memberService
        Mockito.when(memberService.getAllMembers()).thenReturn(mockMembers);

        // Perform the test
        ResponseEntity<List<Member>> response = memberLibController.getAllMembers();

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockMembers, response.getBody());
    }

    @Test
    public void testSaveMember() {
        // Mock data
        Member mockMember = new Member();

        // Mock the behavior of the memberService
        Mockito.when(memberService.saveMember(Mockito.any(Member.class))).thenReturn(mockMember);

        // Perform the test
        ResponseEntity<Member> response = memberLibController.saveMember(new Member());

        // Assertions
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockMember, response.getBody());
    }

    @Test
    public void testUpdateMember() throws NotFoundException {
        // Mock data
        long memberId = 1L;
        Member updatedMember = new Member();

        // Mock the behavior of the memberService
        Mockito.when(memberService.updateMember(memberId, updatedMember)).thenReturn(updatedMember);

        // Perform the test
        ResponseEntity<Member> response = memberLibController.updateMember(memberId, updatedMember);

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedMember, response.getBody());
    }

    @Test
   public void testRemoveMember() {
        // Perform the test
        ResponseEntity<Void> response = memberLibController.removeMember(1L);

        // Assertions
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}
