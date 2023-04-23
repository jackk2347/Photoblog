package hkmu.comps380f.dao;

import hkmu.comps380f.exception.CommentNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.Users;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Service

public class CommentServcie {
    @Resource
    private CommentRepository cRepo;
    @Resource
    private PhotoRepository pRepo ;
    @Resource
    private UserRepository uRepo ;
    @Transactional
    public List<Comment> getComments() {
        List<Comment> comment = cRepo.findAll();
        return comment;
    }

    @Transactional
    public Comment getComment(long id)
            throws CommentNotFound {
        Comment comment = cRepo.findById(id).orElse(null);
        if (comment == null) {
            throw new CommentNotFound(id);
        }
        return comment;
    }


    @Transactional
    public List<Comment> findByPhotoId(UUID photoid) {
        List<Comment> comment = cRepo.findByPhotoId(photoid);
        return comment;
    }


    @Transactional
    public long createComment(String username, String body, UUID id )
            throws IOException {
        Users user = uRepo.findById(username).orElse(null);
        Photo photo = pRepo.findById(id).orElse(null);
        Comment comment= new Comment();
        comment.setUsername(username);
        comment.setUser(user);
        comment.setContent(body);
        comment.setPhoto(photo);
        Comment savedComment = cRepo.save(comment);
        user.getComments().add(savedComment);
        photo.getComments().add(savedComment);
        return savedComment.getId();
    }

    @Transactional
    public void deleteComment(long id) throws CommentNotFound {
        Comment deletedComment = cRepo.findById(id).orElse(null);
        if (deletedComment == null) {
            throw new CommentNotFound(id);
        }
        cRepo.delete(deletedComment);
    }

    @Transactional
    public List<Comment> findCommentByUser(Users user){
        List<Comment> comment = cRepo.findCommentByUser(user);
        return comment ;
    }


}
