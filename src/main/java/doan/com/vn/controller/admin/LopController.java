package doan.com.vn.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import doan.com.vn.entity.GiaoVien;
import doan.com.vn.entity.Lop;
import doan.com.vn.model.LopModel;
import doan.com.vn.repository.GiaoVienRepository;
import doan.com.vn.repository.LopRepository;

@Controller
@RequestMapping("admin/lop")
public class LopController {
    @Autowired
    private LopRepository lopRepository;

    @Autowired
    private GiaoVienRepository giaoVienRepository;

    @ModelAttribute("giaoViens")
    private List<GiaoVien> getAllGV() {
        return giaoVienRepository.findByDeletedFalse();
    }

    @GetMapping("/{khoi}")
    public String list(@PathVariable int khoi,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 5, Sort.by(Sort.Direction.ASC, "createdDate"));
        Page<Lop> lopPage = lopRepository.findByTenKhoiAndDeletedFalse(khoi,
                pageable);
        List<Lop> lops = lopPage.getContent();

        model.addAttribute("lops", lops);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", lopPage.getTotalPages());
        model.addAttribute("totalItems", lopPage.getTotalElements());

        model.addAttribute("khoi", khoi);
        return "admin/lop/list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model) {
        model.addAttribute("lopModel", new LopModel());
        return "admin/lop/add";
    }

    @PostMapping("/add")
    public String addLop(@Valid LopModel lopModel, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "admin/lop/add";
        }

        Lop lop = new Lop();
        BeanUtils.copyProperties(lopModel, lop);

        Optional<GiaoVien> giaoVien = giaoVienRepository
                .findById(lopModel.getMaGV());
        if (giaoVien.isPresent()) {
            lop.setGiaoVien(giaoVien.get());
        }
        lop.setSiSo(0);
        lop.setCreatedDate(new Date());
        lopRepository.save(lop);

        redirectAttributes.addFlashAttribute("msg", "Thêm lớp thành công!");

        return "redirect:/admin/lop/" + lopModel.getTenKhoi();
    }

    @GetMapping("/edit/{tenLop}")
    public String showFormEdit(@PathVariable String tenLop, Model model) {
        LopModel lopModel = new LopModel();
        Lop lop = lopRepository.findById(tenLop).get();

        BeanUtils.copyProperties(lop, lopModel);
        if (lop.getGiaoVien() == null) {
            lopModel.setMaGV("");
        }else {
            lopModel.setMaGV(lop.getGiaoVien().getMaGV());
        }
        

        model.addAttribute("lopModel", lopModel);

        return "admin/lop/edit";
    }

    @PostMapping("/edit")
    public String editLop(@Valid LopModel lopModel, BindingResult result,
            RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            return "admin/lop/edit";
        }

        Lop lop = lopRepository.findById(lopModel.getTenLop()).get();
        BeanUtils.copyProperties(lopModel, lop);

        Optional<GiaoVien> giaoVien = giaoVienRepository
                .findById(lopModel.getMaGV());
        if (giaoVien.isPresent()) {
            lop.setGiaoVien(giaoVien.get());
        }

        lopRepository.save(lop);

        redirectAttributes.addFlashAttribute("msg", "Cập nhật lớp thành công!");

        return "redirect:/admin/lop/" + lopModel.getTenKhoi();
    }

    @GetMapping("/delete/{tenLop}")
    public String deleteLop(@PathVariable String tenLop,
            RedirectAttributes redirectAttributes) {
        Lop lop = lopRepository.findById(tenLop).get();
        lop.setDeleted(true);
        lopRepository.save(lop);
        redirectAttributes.addFlashAttribute("msg", "Xóa lớp thành công!");

        return "redirect:/admin/lop/" + lop.getTenKhoi();
    }
}
