package iducs.javaweb.blog.repository;

import java.util.List;
import iducs.javaweb.blog.model.Blog;

public interface BlogDAO {
    int create(Blog blog);
    Blog read(Blog blog);
    List<Blog> readList();
    int update(Blog blog);
    int delete(Blog blog);

}
