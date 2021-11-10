package MiniProject.Repository;

import MiniProject.Domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {

        this.dataSource = dataSource;
    }

    @Override
    public List<Member> findUser(Member mo) {
        String sql = "select * from member where name like ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Member> members = new ArrayList<>();

        try{
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,"%"+mo.getUsername()+"%");
            rs = pstmt.executeQuery();



            while (rs.next()){
                Member member = new Member();
                member.setUserid(rs.getLong("id"));
                member.setUsername(rs.getString("name"));
                member.setUserpassword(rs.getString("password"));
                member.setMail(rs.getString("email"));

                members.add(member);
            }



        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        return members;
    }

    @Override
    public Member deleteUser(Member member) {
        String sql = "delete member where name=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,member.getUsername());

            pstmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return null;

    }

    @Override
    public Member changeUser(Member member) {
        String sql = "update member set name=?, password=?,email=? where id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,member.getUsername());
            pstmt.setString(2,member.getUserpassword());
            pstmt.setString(3,member.getMail());
            pstmt.setLong(4,member.getUserid());

            pstmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }

        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where name = ?";


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);


            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setUserid(rs.getLong("id"));
                member.setUsername(rs.getString("name"));
                member.setUserpassword(rs.getString("password"));
                member.setMail(rs.getString("email"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }


        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }


    }


    @Override
    public Member save(Member member) {
        String sql = "insert into member(id, name, password, email) values ((select nvl(max(id),0)+1 from member ), ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,member.getUsername());
            pstmt.setString(2,member.getUserpassword());
            pstmt.setString(3,member.getMail());

            pstmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }


        return null;
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<Member> result = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);


            rs = pstmt.executeQuery();


            if(rs.next()) {
                Member member = new Member();
                member.setUserid(rs.getLong("id"));
                member.setUsername(rs.getString("name"));
                member.setUserpassword(rs.getString("password"));
                member.setMail(rs.getString("email"));
                return Optional.of(member);
            }


            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    @Override
    public List<Member> findAll() {
        String sql = "select * from member";


        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);


            rs = pstmt.executeQuery();


            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setUserid(rs.getLong("id"));
                member.setUsername(rs.getString("name"));
                member.setUserpassword(rs.getString("password"));
                member.setMail(rs.getString("email"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }


    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }



}


