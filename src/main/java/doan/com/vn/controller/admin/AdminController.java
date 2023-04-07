package doan.com.vn.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import doan.com.vn.entity.DanToc;
import doan.com.vn.repository.DanTocRepository;

@Controller
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private DanTocRepository danTocRepository;
    
    @GetMapping("")
    public String adminPage() {
        return "admin/index";
    }
    
    @GetMapping("api/dan-toc")
    @ResponseBody
    public List<DanToc> getAll(){
        return danTocRepository.findAll();
    }
}
