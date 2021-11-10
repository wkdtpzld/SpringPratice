package MiniProject.Repository;

import MiniProject.Domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(id, name, password, email) values(AAA.NEXTVAL, ?, ?, ?)";

        jdbcTemplate.update(sql,member.getUsername(),member.getUserpassword(),member.getMail());

        return member;

//        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
//        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("ID");
//
//        Map<String, Object> parameters = new HashMap<>();
//        parameters.put("name", member.getUsername());
//        parameters.put("password", member.getUserpassword());
//        parameters.put("email", member.getMail());
//
//        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
//        member.setUserid(key.longValue());
//
//        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id=?",memberRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name=?",memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member",memberRowMapper());
    }

    @Override
    public Member changeUser(Member member) {
        String sql = "update member set name=?, password=?,email=? where id=?";
        jdbcTemplate.update(sql,member.getUsername(),member.getUserpassword(),member.getMail(),member.getUserid());

        return null;
    }

    @Override
    public Member deleteUser(Member member) {
        String sql = "delete member where name=?";

        jdbcTemplate.update(sql,member.getUsername());

        return null;
    }

    @Override
    public List<Member> findUser(Member member) {
        String sql = "select * from member where name like ?";

        Object[] params = {"%"+member.getUsername()+"%"};

        return jdbcTemplate.query(sql,memberRowMapper(),params);
    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();

            member.setUserid(rs.getLong("id"));
            member.setUsername(rs.getString("name"));
            member.setUserpassword(rs.getString("password"));
            member.setMail(rs.getString("email"));

            return member;
        };
    }
}
