package MiniProject.Service;

import MiniProject.Domain.Member;
import MiniProject.Repository.MemberRepository;
import MiniProject.Repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class MemberServiceJDBCtest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository MemberRepository;

    @Test
    void join() {
        Member member = new Member();
        member.setUsername("hello");

        Long saveId= memberService.join(member);

        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getUsername()).isEqualTo(findMember.getUsername());

    }
    
    @Test
    public void 중복_회원_예외(){
        //given
        
        Member member1 = new Member();
        member1.setUsername("spring");

        Member member2 = new Member();
        member2.setUsername("spring");

        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try{
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e){
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
//
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}