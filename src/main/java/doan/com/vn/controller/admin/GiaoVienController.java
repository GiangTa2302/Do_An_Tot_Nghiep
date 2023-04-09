package doan.com.vn.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.Ban;
import doan.com.vn.entity.DanToc;
import doan.com.vn.entity.GiaoVien;
import doan.com.vn.model.GiaoVienModel;
import doan.com.vn.repository.BanRepository;
import doan.com.vn.repository.DanTocRepository;
import doan.com.vn.repository.GiaoVienRepository;

@Controller
@RequestMapping("admin/giao-vien")
public class GiaoVienController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images";
    
    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private DanTocRepository danTocRepository;

    @Autowired
    private BanRepository banRepository;

    @ModelAttribute("bans")
    private List<Ban> getAllBan() {
        return banRepository.findAll();
    }

    @ModelAttribute("danTocs")
    private List<DanToc> getAllDT() {
        return danTocRepository.findAll();
    }

    @GetMapping("")
    public String list(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        Page<GiaoVien> gvPages = giaoVienRepository.findByDeletedFalse(pageable);
        List<GiaoVien> giaoViens = gvPages.getContent();
        
        model.addAttribute("giaoViens", giaoViens);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", gvPages.getTotalPages());
        model.addAttribute("totalItems", gvPages.getTotalElements());

        return "admin/giao-vien/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        
        model.addAttribute("gvModel", new GiaoVienModel());
        return "admin/giao-vien/add";
    }

    @PostMapping("/add")
    public String addGV(@Valid @ModelAttribute("gvModel") GiaoVienModel gvModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            @RequestParam(value = "image", required = false, defaultValue = "") MultipartFile file,
            Model model) throws IOException {
        if (result.hasErrors()) {
            return "admin/giao-vien/add";
        }
        
        GiaoVien giaoVien = new GiaoVien();

        BeanUtils.copyProperties(gvModel, giaoVien);

        DanToc danToc = danTocRepository.findById(gvModel.getMaDanToc()).get();
        Ban ban = banRepository.findById(gvModel.getMaBan()).get();
        giaoVien.setDanToc(danToc);
        giaoVien.setBan(ban);
        
        if(!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            giaoVien.setAnhGV(fileNames.toString());
        }
        giaoVien.setCreatedDate(new Date());

        giaoVienRepository.save(giaoVien);

        redirectAttributes.addFlashAttribute("msg",
                "Thêm giáo viên thành công!");

        return "redirect:/admin/giao-vien";
    }

    @GetMapping("/edit/{maGV}")
    public String showEditForm(@PathVariable String maGV, Model model) {
        GiaoVien giaoVien = giaoVienRepository.findById(maGV).get();
        GiaoVienModel gvModel = new GiaoVienModel();

        BeanUtils.copyProperties(giaoVien, gvModel);
        gvModel.setMaDanToc(giaoVien.getDanToc().getMaDanToc());
        gvModel.setMaBan(giaoVien.getBan().getMaBan());

        model.addAttribute("gvModel", gvModel);
        model.addAttribute("maGV", maGV);

        return "admin/giao-vien/edit";
    }

    @PostMapping("/edit/{maGV}")
    public String edit(@PathVariable String maGV,
            @Valid @ModelAttribute("gvModel") GiaoVienModel gvModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            @RequestParam("image") MultipartFile file,
            Model model) throws IOException {
        if (result.hasErrors()) {
            return "admin/giao-vien/edit";
        }
        GiaoVien giaoVien = giaoVienRepository.findById(maGV).get();

        BeanUtils.copyProperties(gvModel, giaoVien);

        DanToc danToc = danTocRepository.findById(gvModel.getMaDanToc()).get();
        Ban ban = banRepository.findById(gvModel.getMaBan()).get();
        giaoVien.setDanToc(danToc);
        giaoVien.setBan(ban);
        
        if(!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            giaoVien.setAnhGV(fileNames.toString());
        }

        giaoVienRepository.save(giaoVien);
        redirectAttributes.addFlashAttribute("msg",
                "Cập nhật giáo viên thành công!");

        return "redirect:/admin/giao-vien";
    }

    @GetMapping("/delete/{maGV}")
    public String xoaGV(@PathVariable String maGV, RedirectAttributes redirectAttributes) {
        GiaoVien giaoVien = giaoVienRepository.findById(maGV).get();
        giaoVien.setDeleted(true);
        giaoVienRepository.save(giaoVien);
//        giaoVienRepository.deleteById(maGV);
        redirectAttributes.addFlashAttribute("msg",
                "Xóa giáo viên thành công!");
        return "redirect:/admin/giao-vien";
    }
}
