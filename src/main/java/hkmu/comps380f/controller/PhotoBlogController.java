package hkmu.comps380f.controller;

import hkmu.comps380f.dao.CommentServcie;
import hkmu.comps380f.dao.PhotoblogService;
import hkmu.comps380f.dao.UserManagementService;
import hkmu.comps380f.exception.CommentNotFound;
import hkmu.comps380f.exception.PhotoNotFound;
import hkmu.comps380f.exception.UserNotFound;
import hkmu.comps380f.model.Photo;
import hkmu.comps380f.view.DownloadingView;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/photo")
public class PhotoBlogController {
    @Resource
    private PhotoblogService pService ;

    @Resource
    private CommentServcie cService ;

    @Resource
    UserManagementService umService;

    @GetMapping(value = {"" , "view"})
    public String list(ModelMap model) {
        model.addAttribute("PhotoDatabase", pService.getPhotos());
        return "displayPhoto";
    }


    public static class Form {
        private String description ;

        private List<MultipartFile> photos ;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<MultipartFile> getPhotos() {
            return photos;
        }

        public void setPhotos(List<MultipartFile> photos) {
            this.photos = photos;
        }
    }

    public static class commentForm{

        private String body ;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    @GetMapping("/uploadphoto")
    public ModelAndView uploadphoto() {
        return new ModelAndView("uploadphoto", "photoForm", new Form());
    }

    @PostMapping("/uploadphoto")
    public String uploadphoto(  Form form , Principal principal) throws IOException, UserNotFound {
        UUID photoId = pService.uploadphotobyuser(principal.getName(),
                form.getDescription(), form.getPhotos());
        return "redirect:/" ;
    }

    @GetMapping("/photopagebyid/{id}")
    public ModelAndView photopage(@PathVariable("id") UUID id  , ModelMap model ) throws PhotoNotFound {
        Photo photo = pService.getPhoto(id);
        model.addAttribute("photo",photo);
        model.addAttribute("comment",cService.findByPhotoId(id));
        return new ModelAndView("photo","CommentForm",new commentForm());
    }

    @PostMapping("/photopagebyid/{id}")
    public String photopagecomment(@PathVariable("id") UUID id , commentForm commentform , Principal principal) throws PhotoNotFound, IOException {
        cService.createComment(principal.getName(),commentform.getBody(),id);
        return "redirect:/photo/photopagebyid/" + id ;
    }

    @GetMapping("/deletecomment/{id}/{commentID}")
    public String deletecomment(@PathVariable("id") UUID id ,@PathVariable("commentID") long commentID ) throws CommentNotFound {
        cService.deleteComment(commentID);
        return "redirect:/photo/photopagebyid/"  + id;
    }




    @GetMapping("/photodownload/{photo:.+}")
    public View download(@PathVariable("photo") UUID photoid
                         ) throws PhotoNotFound {
        Photo photo = pService.getPhoto(photoid);
        return new DownloadingView(photo.getFilename(),
                photo.getContent_type(), photo.getPhoto());
    }

}
