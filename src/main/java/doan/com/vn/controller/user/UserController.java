package doan.com.vn.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
    
    @GetMapping("/thoi-khoa-bieu")
    public String tkb() {
        return "user/thoi-khoa-bieu";
    }
    
    @GetMapping("/lien-he")
    public String lienhe() {
        return "user/lien-he";
    }
    
    @GetMapping("/tin-tuc-1")
    public String tintuc1() {
        return "user/tin-tuc-1";
    }
    
    @GetMapping("/tin-tuc-2")
    public String tintuc2() {
        return "user/tin-tuc-2";
    }
    
    @GetMapping("/chi-tiet-tuyen-sinh")
    public String chitiet() {
        
        return "user/chi-tiet-tuyen-sinh";
    }
    
    @GetMapping("/blog")
    public String blog() {
        return "user/blog";
    }
    
    @GetMapping("/tai-lieu")
    public String thuvienbg() {
        return "user/tai-lieu";
    }
}
