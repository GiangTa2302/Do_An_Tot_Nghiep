package doan.com.vn.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doan.com.vn.entity.BaiViet;
import doan.com.vn.entity.User;
import doan.com.vn.model.BaiVietModel;
import doan.com.vn.repository.BaiVietRepository;
import doan.com.vn.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class TinTucController {
    @Autowired
    private BaiVietRepository baiVietRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/tin-tuc-3")
    public String tintuc3(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            Model model
            ) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        Page<BaiViet> bvPage = baiVietRepository.findByTrangThaiTrue(pageable);
        List<BaiViet> baiViets = bvPage.getContent();
        List<BaiVietModel> bvModels = new ArrayList<BaiVietModel>();
        BaiVietModel bvModel = null;
        
        for(BaiViet bv : baiViets) {
            bvModel = new BaiVietModel();
            BeanUtils.copyProperties(bv, bvModel);
            bvModel.setAccount(bv.getUser().getAccount());
            
            bvModels.add(bvModel);
        }
        
        model.addAttribute("bvModels", bvModels);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", bvPage.getTotalPages());
        model.addAttribute("totalItems", bvPage.getTotalElements());
        
        return "user/tin-tuc-3";
    }
    
    @GetMapping("/tin-tuc-3/chi-tiet/{maBV}")
    public String chitiet(
            @PathVariable Integer maBV,
            Model model) {
        Optional<BaiViet> bvOptional = baiVietRepository.findByMaBVAndTrangThaiTrue(maBV);
        if(bvOptional.isPresent()) {
            BaiVietModel baiVietModel = new BaiVietModel();
            BeanUtils.copyProperties(bvOptional.get(), baiVietModel);
            baiVietModel.setAccount(bvOptional.get().getUser().getAccount());
            model.addAttribute("baiVietModel", baiVietModel);
        }else {
            model.addAttribute("baiVietModel", new BaiVietModel());
            model.addAttribute("msg", "Bài viết không tồn tại");
        }
        return "user/chi-tiet-bai-dang";
    }
    
    @GetMapping("/tin-tuc-3/dang-bai")
    public String dangbai(
            Model model) {
        model.addAttribute("bvModel", new BaiVietModel());
        return "user/dang-bai";
    }
    
    @PostMapping("/tin-tuc-3/dang-bai")
    public String dangbai(
            @ModelAttribute BaiVietModel bvModel,
            Model model) {
        Optional<User> userOptional = userRepository.findById(bvModel.getAccount());
        
        if(!userOptional.isPresent()) {
            model.addAttribute("msg", "Account không tồn tại.");
            return "user/dang-bai";
        }
        
        BaiViet baiViet = new BaiViet();
        BeanUtils.copyProperties(bvModel, baiViet);
        baiViet.setUser(userOptional.get());
        baiViet.setCreatedDate(new Date());
        baiVietRepository.save(baiViet);
        
        return "redirect:/user/tin-tuc-3";
    }
}
