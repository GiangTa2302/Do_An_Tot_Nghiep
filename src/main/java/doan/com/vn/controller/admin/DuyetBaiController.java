package doan.com.vn.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.BaiViet;
import doan.com.vn.repository.BaiVietRepository;

@Controller
@RequestMapping("/admin")
public class DuyetBaiController {
    @Autowired
    private BaiVietRepository baiVietRepository;
    
    @GetMapping("/cong-van")
    public String listCV() {
        return "admin/cong-van/list";
    }
    
    @GetMapping("/bai-viet")
    public String listBV(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 6);
        Page<BaiViet> bvPage = baiVietRepository.findByTrangThaiTrue(pageable);
        List<BaiViet> baiVietsTrue = bvPage.getContent();
        List<BaiViet> baiVietsFalse = baiVietRepository.findByTrangThaiFalseAndDeletedFalse();
        
        model.addAttribute("baiVietsTrue", baiVietsTrue);
        model.addAttribute("baiVietsFalse", baiVietsFalse);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", bvPage.getTotalPages());
        model.addAttribute("totalItems", bvPage.getTotalElements());
        
        return "admin/bai-viet/list";
    }
    
    @GetMapping("/chi-tiet-bv/{maBV}")
    public String chitiet(
            @PathVariable Integer maBV,
            Model model) {
        Optional<BaiViet> baiViet = baiVietRepository.findById(maBV);
        if(baiViet.isPresent()) {
            model.addAttribute("baiViet", baiViet.get());
        }else {
            model.addAttribute("baiViet", new BaiViet());
            model.addAttribute("msg", "Bài viết không tồn tại");
        }
        
        model.addAttribute("maBV", maBV);
        
        return "admin/bai-viet/detail";
    }
    
    @GetMapping("/chi-tiet-bv/duyet/{maBV}")
    public String duyetBV(
            @PathVariable Integer maBV,
            RedirectAttributes redirectAttributes,
            Model model) {
        Optional<BaiViet> baiViet = baiVietRepository.findById(maBV);
        if(baiViet.isPresent()) {
            BaiViet bv = baiViet.get();
            bv.setTrangThai(true);
            baiVietRepository.save(bv);
        }
        return "redirect:/admin/bai-viet";
    }
    
    @GetMapping("/chi-tiet-bv/huy/{maBV}")
    public String huyBV(
            @PathVariable Integer maBV,
            RedirectAttributes redirectAttributes,
            Model model) {
        Optional<BaiViet> baiViet = baiVietRepository.findById(maBV);
        if(baiViet.isPresent()) {
            BaiViet bv = baiViet.get();
            bv.setDeleted(true);
            baiVietRepository.save(bv);
        }
        return "redirect:/admin/bai-viet";
    }
}
