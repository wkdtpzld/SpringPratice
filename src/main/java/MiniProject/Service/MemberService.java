package MiniProject.Service;

import MiniProject.Domain.Member;
import MiniProject.Repository.MemberRepository;
import MiniProject.Repository.MemoryMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



public class MemberService {

    private final MemberRepository memberRepository;

//    @Transactional
//    public Long joinUser(MemberDto memberDto){
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return null;
//    }

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     *
     * @param member
     * 회원가입
     */
    public Long join(Member member){
        //같은 이름이 있으면 안됨
        extracted(member);


        memberRepository.save(member);
        return member.getUserid();

    }


    //중복 회원 검증 메서드
    private void extracted(Member member) {
        memberRepository.findByName(member.getUsername())
            .ifPresent(member1 -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    /**
     * 전체 회원 조회
     * @return
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();

    }

    public Optional<Member> findOne(Long memberID){

        return memberRepository.findById(memberID);
    }

    public Optional<Member> findName(String memberName){
        return memberRepository.findByName(memberName);
    }

    public Long changeUser(Member member){
        //같은 이름이 있으면 안됨
        extracted(member);

        memberRepository.changeUser(member);
        return member.getUserid();
    }

    public void deleteUser(Member member){
        memberRepository.deleteUser(member);
    }

    public List<Member> findUser(Member member){
        return memberRepository.findUser(member);
    }
}
