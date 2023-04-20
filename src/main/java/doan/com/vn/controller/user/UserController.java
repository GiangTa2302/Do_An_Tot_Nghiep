package doan.com.vn.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController { 
    @GetMapping("/blog")
    public String blog() {
        return "user/blog";
    }
    
    @GetMapping("/tai-lieu")
    public String thuvienbg() {
        return "user/tai-lieu";
    }
}
