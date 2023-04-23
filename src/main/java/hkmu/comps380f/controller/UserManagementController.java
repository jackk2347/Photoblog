package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentServcie;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.exception.CommentNotFound;
import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.model.Users;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserManagementController {
    @Resource
    UserManagementService umService;

    @Resource
    CommentServcie cService ;

    public static class Form {
        private String username;
        private String password;
        private String tel;
        private String email;
        private String description;
        private String[] roles;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

    }


    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register", "RegisterForm", new Form());
    }

    @PostMapping("/register")
    public String register(Form form) throws IOException {
        String [] roles = {"ROLE_USER"};
        form.setRoles(roles);
        umService.createUser(form.getUsername(),
                form.getPassword(),form.getTel(),form.getEmail(),form.getDescription(), form.getRoles());
        return "redirect:/login";
    }

    @GetMapping("/createuser")
    public ModelAndView createuser() {
        return new ModelAndView("createuserbyadmin", "CreateuserForm", new Form());
    }

    @PostMapping("/createuser")
    public String createuser(Form form) throws IOException {
        umService.createUser(form.getUsername(),
                form.getPassword(),form.getTel(),form.getEmail(),form.getDescription(), form.getRoles());
        return "redirect:/user/list";
    }


    @GetMapping("/list")
    public String list(ModelMap model) {
        model.addAttribute("Userlist", umService.getUsers());
        return "userlist";
    }

    @GetMapping("/profile")
    public String selfprofile(Principal principal,
                          ModelMap model)
            throws UserNotFound {
        Users user = umService.getUser(principal.getName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("user", user);
        return "redirect:/user/profile/" + principal.getName();
    }

    @GetMapping("/profile/{username}")
    public String profile(@PathVariable("username") String username,
                       ModelMap model)
            throws UserNotFound {
        Users user = umService.getUser(username);
        model.addAttribute("username", username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/profile/editprofile/{username}")
    public ModelAndView editprofile(@PathVariable("username") String username, ModelMap model , Principal principal, HttpServletRequest request) throws UserNotFound {
        Users user = umService.getUser(username);
        if (user == null || (!request.isUserInRole("ROLE_ADMIN") && !principal.getName().equals(user.getUsername()))){
            return new ModelAndView(new RedirectView("/user/profile/" + username,true));
        }
        Form form = new Form();
        form.setEmail(user.getEmail());
        form.setTel(user.getTel());
        form.setDescription(user.getDescription());
        model.addAttribute("user", user);
        return new ModelAndView("editprofile","Editform",form) ;
    }

    @PostMapping("/profile/editprofile/{username}")
    public String editprofilepost(@PathVariable("username") String username , Principal principal , Form form , HttpServletRequest request , ModelMap model ) throws UserNotFound {
        Users user = umService.getUser(username);
        if (user == null || (!request.isUserInRole("ROLE_ADMIN") && !principal.getName().equals(user.getUsername()))){
           return "redirect:/user/profile" + username;
        }
        umService.editUserbyUser(user,form.getEmail(),form.getTel(),form.getDescription());
        return "redirect:/user/profile/{username}" ;
    }

    @GetMapping("/profile/editprofile/admin/{username}")
    public ModelAndView editprofilebyadmin(@PathVariable("username") String username ,ModelMap model ) throws UserNotFound {
        Users user = umService.getUser(username);
        Form form = new Form();
        form.setUsername(user.getUsername());
        String password = user.getPassword() ;
        password = password.replace("{noop}","");
        form.setPassword(password);
        form.setTel(user.getTel());
        form.setEmail(user.getEmail());
        form.setDescription(user.getDescription());
        model.addAttribute("user",user);
        return new ModelAndView("editprofileforadmin","Editformformbyadmin",form) ;
    }

    @PostMapping("/profile/editprofile/admin/{username}")
    public String editprofilepostbyadmin(@PathVariable("username") String username, Form form) throws UserNotFound {
        Users user = umService.getUser(username);
        String password = "{noop}" + form.getPassword() ;
        umService.editUserbyAdmin(user,form.getUsername(),password,form.getTel(),form.getEmail(),form.getDescription());
        return "redirect:/user/list" ;
    }

    @GetMapping("/deleteuser/{username}")
    public String deleteUser(@PathVariable("username") String username) {
        umService.deleteUser(username);
        return "redirect:/user/list";
    }

    @GetMapping("/comment/{username}")
    public String commentHistory(@PathVariable("username") String username ,  ModelMap model) throws UserNotFound {
        Users user = umService.getUser(username);
        model.addAttribute("comment",cService.findCommentByUser(user));
        model.addAttribute("username",username);
        return "commentHistory";
    }




}