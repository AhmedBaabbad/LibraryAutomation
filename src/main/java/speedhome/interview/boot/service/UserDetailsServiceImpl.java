package speedhome.interview.boot.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import speedhome.interview.boot.model.Member;
import speedhome.interview.boot.respository.MemberRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
   // private final MemberService memberService;
    private final MemberRepository memberRepository ;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load user details from your MemberService or data source
        Member member = memberRepository.findByUsername(username).
                orElseThrow(()-> new UsernameNotFoundException("UserName " + username + " not found"));
        return new org.springframework.security.core.userdetails.User(member.getUsername(), member.getPassword(),
                getAuthorities(member));

        // You should replace the following lines with actual properties of your User entity
        // For example, getUsername(), getPassword(), and getAuthorities()
    }
    private static Collection<? extends GrantedAuthority> getAuthorities(Member member) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        // Map the role from the Member entity to a GrantedAuthority
        if (member.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + member.getRole()));
        }

        return authorities;
}
}
