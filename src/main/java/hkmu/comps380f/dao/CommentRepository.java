package hkmu.comps380f.dao;

import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.Users;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByUser(Users user);
    List<Comment> findByPhotoId(UUID photoid);

}
