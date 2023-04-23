package doan.com.vn.controller.user;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.BaiGiang;
import doan.com.vn.entity.BinhLuan;
import doan.com.vn.entity.User;
import doan.com.vn.model.BinhLuanModel;
import doan.com.vn.repository.BaiGiangRepository;
import doan.com.vn.repository.BinhLuanRepository;
import doan.com.vn.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class BinhLuanController {
    @Autowired
    private BinhLuanRepository binhLuanRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BaiGiangRepository baiGiangRepository;
    
    @PostMapping("/binh-luan/{maBG}")
    public String postBL(
            @PathVariable Integer maBG,
            @RequestParam String username,
            @Valid @ModelAttribute("blModel") BinhLuanModel blModel,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            Model model) {
        if(result.hasErrors()) {
            return "user/danh-sach-video";
        }
        
        Optional<User> user = userRepository.findById(username);
        Optional<BaiGiang> bg = baiGiangRepository.findById(maBG);
        if(!user.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Account không tồn tại");
        }else {
            BinhLuan binhLuan = new BinhLuan();
            BeanUtils.copyProperties(blModel, binhLuan);
            binhLuan.setUser(user.get());
            binhLuan.setBaiGiang(bg.get());
            binhLuan.setCreatedDate(new Date());
            
            binhLuanRepository.save(binhLuan);
        }
        
        return "redirect:/user/danh-sach-video/" + maBG;
    }
}
