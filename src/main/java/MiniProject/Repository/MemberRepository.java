package MiniProject.Repository;

import MiniProject.Domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    Member changeUser(Member member);
    Member deleteUser(Member member);
    List<Member> findUser(Member member);
}
