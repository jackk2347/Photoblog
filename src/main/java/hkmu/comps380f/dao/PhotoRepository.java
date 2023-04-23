package hkmu.comps380f.dao;

import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
    List<Photo> findPhotoByCustomer(Users user);
}
