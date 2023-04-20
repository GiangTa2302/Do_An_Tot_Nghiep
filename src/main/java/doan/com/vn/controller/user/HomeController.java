package doan.com.vn.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "user/index";
    }
    
    @GetMapping("/thoi-khoa-bieu")
    public String tkb() {
        return "user/thoi-khoa-bieu";
    }
}
