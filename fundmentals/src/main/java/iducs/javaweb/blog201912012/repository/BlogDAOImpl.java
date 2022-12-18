package iducs.javaweb.blog201912012.repository;

import iducs.javaweb.blog201912012.model.Blog;
import iducs.javaweb.blog201912012.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogDAOImpl extends OracleDAOImpl implements BlogDAO{

    private static Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public BlogDAOImpl(){
        conn = getConnection();
    }

    @Override
    public int create(Blog blog) {
        String query = "insert into blog201912012 values(seq_blog201912012.nextval, ?, ?, ?, ?)";
        int rows = 0; // 질의 처리 결과 영향받은 행의 수
        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, blog.getName());
            pstmt.setString(2, blog.getEmail());
            pstmt.setString(3, blog.getTitle());
            pstmt.setString(4, blog.getContent());
            rows = pstmt.executeUpdate();// 1 이상이면 정상, 0 이하면 오류
        } catch(SQLException e){
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public Blog read(Blog blog) {
        Blog ret = null;
        String sql = "select * from BLOG201912012 where id='" + blog.getId() + "'";
        try {
            conn = getConnection(); // DB 연결 객체 생성
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql); // SQL 실행 후 결과를 rs에 반환
            if(rs.next()) { // 결과 집합에서 다음 결과가 있는 지 확인, 있으면 true
                ret = new Blog();
                ret.setId(rs.getLong("id"));
                ret.setName(rs.getString("name"));
                ret.setEmail(rs.getString("email"));
                ret.setTitle(rs.getString("title"));
                ret.setContent(rs.getString("content"));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn, stmt, pstmt, rs);
            return ret;
        }
    }


    @Override
    public List<Blog> readList() {
        List<Blog> blogList = null;
        Blog ret = null;
        String sql = "select * from blog201912012";
        try {
            conn = getConnection(); // DB 연결 객체 생성
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql); // SQL 실행 후 결과를 rs에 반환
            blogList = new ArrayList<Blog>();
            while(rs.next()) { // 결과 집합에서 다음 결과가 있는 지 확인, 있으면 true
                ret = new Blog();
                ret.setId(rs.getLong("id"));
                ret.setName(rs.getString("name"));
                ret.setEmail(rs.getString("email"));
                ret.setTitle(rs.getString("title"));
                ret.setContent(rs.getString("content"));
                blogList.add(ret);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn, stmt, pstmt, rs);
            return blogList;
        }
    }

    @Override
    public int update(Blog blog) {
        int ret = 0;
        String sql = "update blog201912012 set name=?, email=?, title=?, content=? where id='" + blog.getId() + "'";
        // placeholder : 자리 대체 기호
        try {
            conn = getConnection(); // DB 연결 객체 생성
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, blog.getName());
            pstmt.setString(2, blog.getEmail());
            pstmt.setString(3, blog.getTitle());
            pstmt.setString(4, blog.getContent());
            ret = pstmt.executeUpdate(); // SQL 실행으로 영향받은 row 수를 반환
            if(ret > 0) { // 결과 집합에서 다음 결과가 있는 지 확인, 있으면 true
                ret = 1;
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            closeResources(conn, stmt, pstmt, rs);
            return ret;
        }
    }


    @Override
    public int delete(Blog blog) {
        int rows = 0;
        String sql = "delete from blog201912012 where id=?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, blog.getId());
            rows = pstmt.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

}

