package doan.com.vn.controller.admin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import doan.com.vn.entity.DanToc;
import doan.com.vn.entity.GiaoVien;
import doan.com.vn.entity.Lop;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.entity.Role;
import doan.com.vn.entity.User;
import doan.com.vn.model.GiaoVienModel;
import doan.com.vn.repository.DanTocRepository;
import doan.com.vn.repository.GiaoVienRepository;
import doan.com.vn.repository.LopRepository;
import doan.com.vn.repository.MonHocRepository;
import doan.com.vn.repository.RoleRepository;
import doan.com.vn.repository.UserRepository;
import doan.com.vn.utils.RoleName;

@Controller
@RequestMapping("admin/giao-vien")
public class GiaoVienController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir")
            + "/src/main/resources/static/ad/images";

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private DanTocRepository danTocRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private LopRepository lopRepository;

    @ModelAttribute("danTocs")
    private List<DanToc> getAllDT() {
        return danTocRepository.findAll();
    }

    @ModelAttribute("monHocs")
    private List<MonHoc> getAllMH() {
        return monHocRepository.findAll();
    }

    @ModelAttribute("lops")
    private List<Lop> getAllLop() {
        return lopRepository.findAll();
    }

    @GetMapping("")
    public String list(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        Page<GiaoVien> gvPages = giaoVienRepository
                .findByDeletedFalse(pageable);
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
        giaoVien.setDanToc(danToc);

        MonHoc monHoc = monHocRepository.findById(gvModel.getMaMon()).get();
        giaoVien.setMonHoc(monHoc);

        List<String> tenLops = gvModel.getLops();
        List<Lop> lops = new ArrayList<Lop>();
        Lop lop = null;
        for (String tenLop : tenLops) {
            lop = lopRepository.findById(tenLop).get();
            lop.setGiaoViens(List.of(giaoVien));
            lops.add(lop);
        }

        giaoVien.setLops(lops);

        if (!file.isEmpty()) {
            StringBuilder fileNames = new StringBuilder();
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY,
                    file.getOriginalFilename());
            fileNames.append(file.getOriginalFilename());
            Files.write(fileNameAndPath, file.getBytes());
            giaoVien.setAnhGV(fileNames.toString());
        }
        giaoVien.setCreatedDate(new Date());

        giaoVienRepository.save(giaoVien);
        giaoVienRepository.flush();
        
        String encodePass = passwordEncoder.encode(giaoVien.getMaGV() + "12345@");

        User user = new User(giaoVien.getMaGV(), encodePass,
                giaoVien.getMaGV() + "@edu.com");
        Optional<Role> roleOptional = roleRepository.findByRoleName(RoleName.TEACHER);
        Role role = null;
        if (roleOptional.isPresent()) {
            role = roleOptional.get();
        } else {
            role = new Role(RoleName.TEACHER);            
        }
        user.setRoles(Set.of(role));
        userRepository.save(user);

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

        model.addAttribute("gvModel", gvModel);
        model.addAttribute("maGV", maGV);

        return "admin/giao-vien/edit";
    }

    @PostMapping("/edit/{maGV}")
    public String edit(@PathVariable String maGV,
            @Valid @ModelAttribute("gvModel") GiaoVienModel gvModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            @RequestParam("image") MultipartFile file, Model model)
            throws IOException {
        if (result.hasErrors()) {
            return "admin/giao-vien/edit";
        }
        GiaoVien giaoVien = giaoVienRepository.findById(maGV).get();

        BeanUtils.copyProperties(gvModel, giaoVien);

        DanToc danToc = danTocRepository.findById(gvModel.getMaDanToc()).get();
        giaoVien.setDanToc(danToc);

        MonHoc monHoc = monHocRepository.findById(gvModel.getMaMon()).get();
        giaoVien.setMonHoc(monHoc);

//        List<String> tenLops = gvModel.getLops();
//        List<Lop> lops = giaoVien.getLops();
//        Lop lop = null;
//        for (String tenLop : tenLops) {
//            lop = lopRepository.findById(tenLop).get();
//            lop.setGiaoViens(List.of(giaoVien));
//            lops.add(lop);
//        }
//        
//        giaoVien.setLops(lops);

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

        return "redirect:/admin/giao-vien";
    }

    @GetMapping("/delete/{maGV}")
    public String xoaGV(@PathVariable String maGV,
            RedirectAttributes redirectAttributes) {
        GiaoVien giaoVien = giaoVienRepository.findById(maGV).get();
        giaoVien.setDeleted(true);
        giaoVienRepository.save(giaoVien);

        redirectAttributes.addFlashAttribute("msg",
                "Xóa giáo viên thành công!");
        return "redirect:/admin/giao-vien";
    }
}
