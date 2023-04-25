package doan.com.vn.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.DanToc;
import doan.com.vn.entity.GiaoVien;
import doan.com.vn.model.GiaoVienModel;
import doan.com.vn.repository.DanTocRepository;
import doan.com.vn.repository.GiaoVienRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private DanTocRepository danTocRepository;
    
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    
    @ModelAttribute("danTocs")
    private List<DanToc> getAllDT() {
        return danTocRepository.findAll();
    }
    
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
            + "/src/main/resources/static/ad/images";
    
    @GetMapping("api/dan-toc")
    @ResponseBody
    public List<DanToc> getAll(){
        return danTocRepository.findAll();
    }
    
    @GetMapping("/ca-nhan")
    public String caNhan(
            @RequestParam String username,
            Model model) {
        Optional<GiaoVien> gvOptional = giaoVienRepository.findById(username);
        GiaoVienModel gvModel = new GiaoVienModel();
        if(gvOptional.isPresent()) {
            BeanUtils.copyProperties(gvOptional.get(), gvModel);
            gvModel.setMaDanToc(gvOptional.get().getDanToc().getMaDanToc());
        }
        
        model.addAttribute("gvModel", gvModel);
        return "admin/tai-khoan/ca-nhan";
    }
    
    @PostMapping("/ca-nhan")
    public String edit(
            @RequestParam("username") String username,
            @Valid @ModelAttribute("gvModel") GiaoVienModel gvModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            @RequestParam("image") MultipartFile file, Model model)
            throws IOException {
        if (result.hasErrors()) {
            return "admin/giao-vien/edit";
        }
        GiaoVien giaoVien = giaoVienRepository.findById(username).get();

        BeanUtils.copyProperties(gvModel, giaoVien);

        DanToc danToc = danTocRepository.findById(gvModel.getMaDanToc()).get();
        giaoVien.setDanToc(danToc);

        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY,
                    file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            giaoVien.setAnhGV(fileNames.toString());
        }

        giaoVienRepository.save(giaoVien);
        redirectAttributes.addFlashAttribute("msg",
                "Cập nhật giáo viên thành công!");

        return "redirect:/admin/ca-nhan?username="+username;
    }
}
