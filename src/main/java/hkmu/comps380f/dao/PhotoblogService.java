package hkmu.comps380f.dao;

import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.model.Comment;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.Users;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PhotoblogService {

    @Resource
    private CommentRepository cRepo;

    @Resource
    private PhotoRepository pRepo;

    @Resource
    private UserRepository userRepo;


    @Transactional
    public List<Comment> getPhotosComment() {
        return cRepo.findAll();
    }

    @Transactional
    public List<Photo> getPhotos (){
        return pRepo.findAll();
    }


    @Transactional
    public Photo getPhoto(UUID PhotoId)
            throws  PhotoNotFound {
        Photo photo = pRepo.findById(PhotoId).orElse(null);
        if (photo == null) {
            throw new PhotoNotFound(PhotoId);
        }
        return photo;
    }

    @Transactional
    public void deletePhoto (UUID PhotoId)
            throws  PhotoNotFound {
        Photo photo = pRepo.findById(PhotoId).orElse(null);
        if (photo == null) {
            throw new PhotoNotFound(PhotoId);
        }
        pRepo.delete(photo);
    }

    @Transactional
    public UUID uploadphotobyuser(String username ,String description,List<MultipartFile> photos) throws IOException {
        Users user = userRepo.findById(username).orElse(null);
        UUID id = null;
        for (MultipartFile filePart : photos){
            Photo photo = new Photo();
            photo.setCustomer(user); ;
            photo.setDescription(description);
            photo.setFilename(filePart.getOriginalFilename());
            photo.setContent_type(filePart.getContentType());
            photo.setPhoto(filePart.getBytes());
            Photo savedPhoto = pRepo.save(photo);
            user.getPhotos().add(savedPhoto);
            id = savedPhoto.getId();
        }
        return id;
    }

    @Transactional
    public UUID deletephoto(UUID photoID) throws IOException, PhotoNotFound {
        Photo deletephoto = pRepo.findById(photoID).orElse(null) ;
        if (deletephoto == null){
            throw new PhotoNotFound(photoID);
        }
        pRepo.delete(deletephoto);
        return photoID;
    }

    @Transactional
    public List<Photo> findPhotoByUser(Users user){
        List<Photo> photo = pRepo.findPhotoByCustomer(user);
        return photo ;
    }

}
