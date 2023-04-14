package doan.com.vn.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.dto.DiemCreationDTO;
import doan.com.vn.entity.Diem;
import doan.com.vn.entity.DiemPK;
import doan.com.vn.entity.HocSinh;
import doan.com.vn.entity.Lop;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.model.DiemModel;
import doan.com.vn.repository.DiemRepository;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.LopRepository;
import doan.com.vn.repository.MonHocRepository;

@Controller
@RequestMapping("/admin/diem")
public class DiemController {

    @Autowired
    private DiemRepository diemRepository;

    @Autowired
    private HocSinhRepository hocSinhRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private LopRepository lopRepository;

    @ModelAttribute("monHocs")
    private List<MonHoc> findAllMH() {
        return monHocRepository.findByDeletedFalse();
    }

    @ModelAttribute("lops")
    private List<Lop> findAllLop() {
        return lopRepository.findByDeletedFalse();
    }

    @GetMapping({ "", "/{tenLop}/{maMon}/{hocKy}" })
    public String showDiem(
            @PathVariable(value = "tenLop", required = false) String tenLop,
            @PathVariable(value = "maMon", required = false) String maMon,
            @PathVariable(value = "hocKy", required = false) Integer hocKy,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            Model model) {

        Pageable pageable = PageRequest.of(pageIndex - 1, 10,
                Sort.by(Sort.Direction.ASC, "ten"));
        Page<HocSinh> hsPage = hocSinhRepository.findAllStudentByClass(tenLop,
                pageable);
        List<HocSinh> hocSinhs = hsPage.getContent();

        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", hsPage.getTotalPages());
        model.addAttribute("totalItems", hsPage.getTotalElements());
        model.addAttribute("tenLop", tenLop);
        model.addAttribute("maMon", maMon);
        model.addAttribute("hocKy", hocKy == null ? 1 : hocKy);

        DiemCreationDTO diemDTO = new DiemCreationDTO();
        Optional<Diem> diem = null;
        DiemModel diemModel = null;

        for (HocSinh hs : hocSinhs) {
            DiemPK diemPK = new DiemPK(hs.getMaHS(), maMon, hocKy);
            diem = diemRepository.findById(diemPK);
            diemModel = new DiemModel(hs.getMaHS(), maMon,
                    hs.getHodem() + " " + hs.getTen(), hocKy);
            if (diem.isPresent()) {
                BeanUtils.copyProperties(diem.get(), diemModel);
            }
            diemDTO.addDiem(diemModel);
        }

        model.addAttribute("diemDTO", diemDTO);

        return "admin/diem/add";
    }

    @PostMapping("/saveOrUpdate/{tenLop}/{maMon}/{hocKy}")
    public String saveOrUpdate(@PathVariable(value = "tenLop") String tenLop,
            @PathVariable(value = "maMon") String maMon,
            @PathVariable(value = "hocKy") Integer hocKy,
            @ModelAttribute("diemDTO") DiemCreationDTO diemDTO,
            RedirectAttributes redirectAttributes) {
        Diem diemU = null;
        List<Diem> diems = new ArrayList<Diem>();

        for (DiemModel dm : diemDTO.getDiemModels()) {
            DiemPK diemPK = new DiemPK(dm.getMaHS(), maMon, hocKy);
            diemU = new Diem();
            diemU.setId(diemPK);
            diemU.setMonHoc(monHocRepository.findById(maMon).get());

            BeanUtils.copyProperties(dm, diemU);
            diems.add(diemU);
        }

        diemRepository.saveAll(diems);
        redirectAttributes.addFlashAttribute("msg", "Nhập điểm thành công.");

        return "redirect:/admin/diem/" + tenLop + "/" + maMon + "/" + hocKy;
    }
}
