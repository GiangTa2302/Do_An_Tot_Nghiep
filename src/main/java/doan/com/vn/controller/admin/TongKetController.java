package doan.com.vn.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TongKetController {
    
    @GetMapping("/tong-ket/{hocKy}")
    public String tongKet(
            @PathVariable("hocKy") String hocKy) {
        
        return "";
    }
}
