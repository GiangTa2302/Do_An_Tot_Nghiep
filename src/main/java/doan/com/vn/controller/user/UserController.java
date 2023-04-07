package doan.com.vn.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserController {
    @GetMapping("/ke-hoach-giao-duc")
    public String kehoachgd() {
        return "user/thoi-khoa-bieu";
    }
    
    @GetMapping("/thu-vien-bai-giang")
    public String tvbaigiang() {
        return "user/thu-vien-bai-giang";
    }
    
    @GetMapping("/thu-vien-thi")
    public String tvthi() {
        return "user/thu-vien-thi";
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
    
    @GetMapping("/tin-tuc-3")
    public String tintuc3() {
        return "user/tin-tuc-3";
    }
    
    @GetMapping("/blog")
    public String blog() {
        return "user/blog";
    }
    
    @GetMapping("/ket-qua-kt-thi")
    public String ketquakt() {
        return "user/ket-qua-kt-thi";
    }
    
    @GetMapping("/tai-lieu")
    public String thuvienbg() {
        return "user/tai-lieu";
    }
}
