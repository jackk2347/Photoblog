package hkmu.comps380f.dao;

import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.Users;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserManagementService {

    @Resource
    private UserRepository userRepo ;

    @Transactional
    public List<Users> getUsers() {
        return userRepo.findAll();
    }

    @Transactional
    public Users getUser(String username)throws UserNotFound{
        Users user =  userRepo.findById(username).orElse(null) ;
        if (user == null){
            throw new UserNotFound(username);
        }
        return user ;
    }

    @Transactional
    public void deleteUser(String username) {
        Users user = userRepo.findById(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User '" + username + "' not found.");
        }
        userRepo.delete(user);
    }


    @Transactional
    public void createUser(String username, String password,String email ,String  description, String phone_num , String[] roles) {
        Users user = new Users(username, password ,email , phone_num , description, roles);
        userRepo.save(user);
    }

    @Transactional
    public void editUserbyAdmin(Users user ,String usernames ,String password,String tel , String email ,String  description ){
        String username = user.getUsername();
        Users edituser = userRepo.findById(username).orElse(null);
        if (edituser == null){
            throw new UsernameNotFoundException(username);
        }
        edituser.setUsername(usernames);
        edituser.setPassword(password);
        edituser.setEmail(email);
        edituser.setDescription(description);
        edituser.setTel(tel);
        userRepo.save(edituser);
    }


    @Transactional
    public void editUserbyUser(Users user ,String email ,String tel,String  description ){
        String username = user.getUsername();
        Users edituser = userRepo.findById(username).orElse(null);
        if (edituser == null){
            throw new UsernameNotFoundException(username);
        }
        edituser.setEmail(email);
        edituser.setDescription(description);
        edituser.setTel(tel);
        userRepo.save(edituser);
    }




}