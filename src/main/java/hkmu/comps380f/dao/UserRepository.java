package hkmu.comps380f.dao;

import hkmu.comps380f.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, String> {
}
