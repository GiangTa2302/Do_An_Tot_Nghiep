package doan.com.vn.controller.admin;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.MonHoc;
import doan.com.vn.model.MonHocModel;
import doan.com.vn.repository.MonHocRepository;

@Controller
@RequestMapping("/admin/mon-hoc")
public class MonHocController {
    @Autowired
    private MonHocRepository monHocRepository;

    @GetMapping({ "/", "/{tenLop}" })
    public String list(
            @PathVariable(value = "tenLop", required = false) String tenLop,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        Page<MonHoc> mhPage = monHocRepository.findByDeletedFalse(pageable);
        List<MonHoc> monHocs = mhPage.getContent();
        
        if(tenLop != null) {
            model.addAttribute("tenLop", tenLop);
        }

        model.addAttribute("monHocs", monHocs);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", mhPage.getTotalPages());
        model.addAttribute("totalItems", mhPage.getTotalElements());

        return "admin/mon-hoc/list";
    }

    @GetMapping("/add")
    public String showFormAdd(Model model) {
        model.addAttribute("mhModel", new MonHocModel());

        return "admin/mon-hoc/add";
    }

    @PostMapping("/add")
    public String addMH(@Valid @ModelAttribute("mhModel") MonHocModel mhModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            return "admin/mon-hoc/add";
        }

        MonHoc monHoc = new MonHoc();
        BeanUtils.copyProperties(mhModel, monHoc);
        monHocRepository.save(monHoc);

        redirectAttributes.addFlashAttribute("msg", "Thêm môn học thành công.");

        return "redirect:/admin/mon-hoc/";
    }

    @GetMapping("/edit/{maMon}")
    public String showFormEdit(@PathVariable String maMon, Model model) {
        MonHoc monHoc = monHocRepository.findById(maMon).get();
        MonHocModel mhModel = new MonHocModel();

        BeanUtils.copyProperties(monHoc, mhModel);

        model.addAttribute("mhModel", mhModel);
        model.addAttribute("maMon", maMon);

        return "admin/mon-hoc/edit";
    }

    @PostMapping("/edit/{maMon}")
    public String editMH(@PathVariable String maMon,
            @Valid @ModelAttribute("mhModel") MonHocModel mhModel,
            BindingResult result, RedirectAttributes redirectAttributes,
            Model model) {
        if (result.hasErrors()) {
            return "admin/mon-hoc/add";
        }

        MonHoc monHoc = monHocRepository.findById(maMon).get();
        BeanUtils.copyProperties(mhModel, monHoc);
        monHocRepository.save(monHoc);

        redirectAttributes.addFlashAttribute("msg",
                "Cập nhật môn học thành công.");

        return "redirect:/admin/mon-hoc/";
    }

    @GetMapping("/delete/{maMon}")
    public String deleteMH(@PathVariable String maMon,
            RedirectAttributes redirectAttributes) {
        MonHoc monHoc = monHocRepository.findById(maMon).get();
        monHoc.setDeleted(true);
        monHocRepository.save(monHoc);

        redirectAttributes.addFlashAttribute("msg", "Xóa môn học thành công.");
        return "redirect:/admin/mon-hoc/";
    }
}
