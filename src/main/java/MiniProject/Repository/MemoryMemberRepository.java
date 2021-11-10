package MiniProject.Repository;

import MiniProject.Domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member changeUser(Member member) {
        return null;
    }

    @Override
    public Member deleteUser(Member member) {
        return null;
    }

    @Override
    public Member save(Member member) {
        member.setUserid(++sequence);
        store.put(member.getUserid(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(
                member -> member.getUsername().equals(name)).findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearstore(){
        store.clear();
    }

    @Override
    public List<Member> findUser(Member member) {
        return null;
    }
}
