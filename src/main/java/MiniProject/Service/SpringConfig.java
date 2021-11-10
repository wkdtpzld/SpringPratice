package MiniProject.Service;


import MiniProject.Repository.JdbcMemberRepository;
import MiniProject.Repository.JdbcTemplateMemberRepository;
import MiniProject.Repository.MemberRepository;
import MiniProject.Repository.MemoryMemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.sql.DataSource;

@Configuration
public class SpringConfig {

//    private MemberService memberService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception{
//        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "*/lib/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests()
//                // 페이지 권한
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/myinfo").hasRole("MEMBER")
//                .antMatchers("/**").permitAll()
//            .and() // 로그인 설정
//                .formLogin()
//                .loginPage("/user/login")
//                .defaultSuccessUrl("/user/login/result")
//                .permitAll()
//            .and() // 로그아웃 설정
//                .logout()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
//                .logoutSuccessUrl("/user/logout/result")
//                .invalidateHttpSession(true)
//            .and()
//                .exceptionHandling().accessDeniedPage("/user/denied");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
//    }

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){

        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
       //return new MemoryMemberRepository();
        //return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
