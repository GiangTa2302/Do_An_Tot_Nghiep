package doan.com.vn.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import doan.com.vn.entity.HocSinh;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.MonHocRepository;

@Controller
@RequestMapping("/admin")
public class TongKetController {
    
    @Autowired
    private MonHocRepository monHocRepository;
    
    @Autowired
    private HocSinhRepository hocSinhRepository;
    
    @GetMapping("/tong-ket/{tenLop}/{hocKy}")
    public String tongKet(
            @PathVariable("tenLop") String tenLop,
            @PathVariable("hocKy") Integer hocKy,
            Model model) {
        List<MonHoc> monHocs = monHocRepository.findByDeletedFalse();
        
        List<HocSinh> hocSinhs = hocSinhRepository.findAllStudentByClass(tenLop);
        
//        List<Diem> diems = new ArrayList<Diem>();
//        Diem diem = null;
        model.addAttribute("hocSinhs", hocSinhs);   
        model.addAttribute("monHocs", monHocs);
        model.addAttribute("tenLop", tenLop);
        return "";
    }
}
