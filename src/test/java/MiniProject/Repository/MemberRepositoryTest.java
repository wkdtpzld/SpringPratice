package MiniProject.Repository;

import MiniProject.Domain.Member;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MemberRepositoryTest {
    
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearstore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setUsername("spring");

        repository.save(member);

        repository.findById(member.getUserid());

        Member result = repository.findById(member.getUserid()).get();
        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setUsername("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setUsername("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        Assertions.assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member1 = new Member();
        member1.setUsername("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setUsername("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        Assertions.assertThat(result.size()).isEqualTo(2);
    }
}
