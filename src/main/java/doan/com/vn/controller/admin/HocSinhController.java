package doan.com.vn.controller.admin;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.DanToc;
import doan.com.vn.entity.HocSinh;
import doan.com.vn.entity.Lop;
import doan.com.vn.entity.PhuHuynh;
import doan.com.vn.entity.Role;
import doan.com.vn.entity.TheBHYT;
import doan.com.vn.entity.User;
import doan.com.vn.model.HocSinhModel;
import doan.com.vn.repository.DanTocRepository;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.LopRepository;
import doan.com.vn.repository.PhuHuynhRepository;
import doan.com.vn.repository.RoleRepository;
import doan.com.vn.repository.TheBHYTRepository;
import doan.com.vn.repository.UserRepository;
import doan.com.vn.utils.RoleName;

@Controller
@RequestMapping("/admin/hoc-sinh")
public class HocSinhController {

    @Autowired
    private HocSinhRepository hocSinhRepository;

    @Autowired
    private DanTocRepository danTocRepository;

    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private PhuHuynhRepository phuHuynhRepository;

    @Autowired
    private TheBHYTRepository bhytRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ModelAttribute("danTocs")
    private List<DanToc> getAllDT() {
        return danTocRepository.findAll();
    }

    @ModelAttribute("lops")
    private List<Lop> getAllLop() {
        return lopRepository.findByDeletedFalse();
    }

    @GetMapping("/{tenLop}")
    public String list(@PathVariable String tenLop,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10, Sort.by(Sort.Direction.ASC, "ten"));
        Page<HocSinh> hsPage = hocSinhRepository.findAllStudentByClass(tenLop,
                pageable);
        List<HocSinh> hocSinhs = hsPage.getContent();

        model.addAttribute("hocSinhs", hocSinhs);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", hsPage.getTotalPages());
        model.addAttribute("totalItems", hsPage.getTotalElements());

        return "admin/hoc-sinh/list";
    }

    @GetMapping("/add/{tenLop}")
    public String showFormAdd(@PathVariable String tenLop, Model model) {
        HocSinhModel hsModel = new HocSinhModel(tenLop);
        model.addAttribute("hsModel", hsModel);

        return "admin/hoc-sinh/add";
    }

    @PostMapping("/add")
    public String addHS(@Valid @ModelAttribute("hsModel") HocSinhModel hsModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            return "admin/hoc-sinh/add";
        }

        PhuHuynh phuHuynh = new PhuHuynh();
        BeanUtils.copyProperties(hsModel, phuHuynh);
        phuHuynhRepository.save(phuHuynh);

        TheBHYT theBHYT = new TheBHYT();
        BeanUtils.copyProperties(hsModel, theBHYT);
        bhytRepository.save(theBHYT);

        HocSinh hocSinh = new HocSinh();
        BeanUtils.copyProperties(hsModel, hocSinh);
        hocSinh.setPhuHuynh(phuHuynh);
        hocSinh.setTheBHYT(theBHYT);
        hocSinh.setLop(lopRepository.findById(hsModel.getTenLop()).get());
        hocSinhRepository.save(hocSinh);        
        hocSinhRepository.flush();
        
        String encodePass = passwordEncoder.encode( hocSinh.getMaHS() + "12345@");
        User user = new User(hocSinh.getMaHS(), encodePass,
                hocSinh.getMaHS() + "@edu.com");
        Optional<Role> roleOptional = roleRepository.findByRoleName(RoleName.STUDENT);
        Role role = null;
        if (roleOptional.isPresent()) {
            role = roleOptional.get();
        } else {
            role = new Role(RoleName.STUDENT);            
        }
        user.setRoles(Set.of(role));
        userRepository.save(user);

        redirectAttributes.addFlashAttribute("msg",
                "Thêm học sinh thành công!");

        return "redirect:/admin/hoc-sinh/" + hsModel.getTenLop();
    }

    @GetMapping("/edit/{maHS}")
    public String showFormEdit(@PathVariable String maHS, Model model) {
        HocSinh hocSinh = hocSinhRepository.findById(maHS).get();
        HocSinhModel hsModel = new HocSinhModel();
        BeanUtils.copyProperties(hocSinh, hsModel);

        hsModel.setTenLop(hocSinh.getLop().getTenLop());

        PhuHuynh phuHuynh = hocSinh.getPhuHuynh();
        BeanUtils.copyProperties(hsModel, phuHuynh);

        TheBHYT bhyt = hocSinh.getTheBHYT();
        BeanUtils.copyProperties(hsModel, bhyt);

        model.addAttribute("maHS", maHS);
        model.addAttribute("hsModel", hsModel);

        return "admin/hoc-sinh/edit";
    }

    @PostMapping("/edit/{maHS}")
    public String editHS(@PathVariable String maHS,
            @Valid @ModelAttribute("hsModel") HocSinhModel hsModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            return "admin/hoc-sinh/edit";
        }

        HocSinh hocSinh = hocSinhRepository.findById(maHS).get();

        PhuHuynh phuHuynh = phuHuynhRepository
                .findById(hocSinh.getPhuHuynh().getMaPH()).get();
        BeanUtils.copyProperties(hsModel, phuHuynh);
        phuHuynhRepository.save(phuHuynh);

//        TheBHYT theBHYT = bhytRepository.findById(hocSinh.getTheBHYT().getMaThe()).get();
//        BeanUtils.copyProperties(hsModel, theBHYT);
//        bhytRepository.save(theBHYT);

        BeanUtils.copyProperties(hsModel, hocSinh);
        hocSinh.setPhuHuynh(phuHuynh);
        hocSinh.setLop(lopRepository.findById(hsModel.getTenLop()).get());
        hocSinhRepository.save(hocSinh);

        redirectAttributes.addFlashAttribute("msg",
                "Cập nhật học sinh thành công!");

        return "redirect:/admin/hoc-sinh/" + hsModel.getTenLop();
    }

    @GetMapping("/delete/{maHS}")
    public String delete(@PathVariable String maHS,
            RedirectAttributes redirectAttributes) {
        HocSinh hocSinh = hocSinhRepository.findById(maHS).get();
        hocSinh.setDeleted(true);
        hocSinhRepository.save(hocSinh);
        redirectAttributes.addFlashAttribute("msg", "Xóa học sinh thành công!");
        return "redirect:/admin/hoc-sinh/" + hocSinh.getLop().getTenLop();
    }
}
