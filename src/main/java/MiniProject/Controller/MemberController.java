package MiniProject.Controller;

import MiniProject.Domain.Member;
import MiniProject.Service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "/members/createForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setUsername(form.getName());
        member.setUserpassword(form.getPassword());
        member.setMail(form.getEmail());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberlist";
    }

    //홈
    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/members/change")
    public String change(Model model){
        return "/members/change";
    }

    @PostMapping("/members/change")
    public String changeForm(MemberForm form){

        Member member = new Member();

        member.setUsername(form.getName());
        member.setUserpassword(form.getPassword());
        member.setMail(form.getEmail());
        member.setUserid(form.getId());

        memberService.changeUser(member);

        return "redirect:/";
    }

    @GetMapping("/members/delete")
    public String delete(){
        return "/members/delete";
    }

    @PostMapping("/members/delete")
    public String deleteForm(MemberForm form){

        Member member = new Member();

        member.setUsername(form.getName());

        memberService.deleteUser(member);

        return "redirect:/";
    }

    @GetMapping("/members/search")
    public String search(){
        return "/members/search";
    }

    @PostMapping("/members/search")
    public String searchForm(MemberForm form, Model model){
        Member member = new Member();
        member.setUsername(form.getName());
        List<Member> result = memberService.findUser(member);

        model.addAttribute("member",result);
        return "/members/search";
    }

//    // 회원가입 페이지
//    @GetMapping("/user/signup")
//    public String dispSignup(){
//        return "/signup";
//    }
//
//    // 회원가입 처리
//    @PostMapping("/user/signup")
//    public String execSignup(MemberDto memberDto){
//        memberService.joinuser(memberDto);
//
//        return "redirec:/user/login";
//    }
//
//    //로그인 페이지
//    @GetMapping("/user/login")
//    public String dispLogin(){
//        return "/lgoin";
//    }
//
//    //로그인 결과 페이지
//    @GetMapping("/user/login/result")
//    public String dispLoginResult(){
//        return "/loginSucces";
//    }
//
//    //로그아웃 결과 페이지
//    @GetMapping("/user/logout/result")
//    public String dispLogout(){
//        return "/logout";
//    }
//
//    //접근 거부 페이지
//    @GetMapping("/user/denied")
//    public String dispDenied(){
//        return "/denied";
//    }
//
//    //내 정보 페이지
//    @GetMapping("/user/info")
//    public String dispMyInfo(){
//        return "/denied";
//    }
//
//    // 어드민 페이지
//    @GetMapping("/admin")
//    public String dispAdmin(){
//        return "/admin";
//    }
}
