package iducs.javaweb.blog.repository;

import iducs.javaweb.blog.model.Member;

import java.util.List;

public interface MemberDAO {
    int create(Member m);
    Member read(Member m);
    List<Member> readList();
    int update(Member m);
    int delete(Member m);

    Member login(Member m);
}
