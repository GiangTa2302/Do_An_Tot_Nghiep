package doan.com.vn.controller.user;

import java.util.List;
import java.util.Optional;

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

import doan.com.vn.dto.DapAnHSDTO;
import doan.com.vn.entity.CauHoi;
import doan.com.vn.entity.De;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.model.DapAnHSModel;
import doan.com.vn.repository.CauHoiRepository;
import doan.com.vn.repository.DeRepository;
import doan.com.vn.repository.MonHocRepository;

@Controller
@RequestMapping("/user")
public class TaiNguyenController {

    @Autowired
    private MonHocRepository monHocRepository;

    @Autowired
    private DeRepository deRepository;

    @Autowired
    private CauHoiRepository cauHoiRepository;

    @GetMapping("/thu-vien-thi")
    public String tvthi(Model model) {
        model.addAttribute("monHocs", monHocRepository.findByDeletedFalse());
        return "user/thu-vien-thi/thu-vien-thi";
    }

    @GetMapping("/danh-sach-de/{maMon}")
    public String listDe(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @PathVariable("maMon") String maMon,
            @RequestParam("loaiDe") boolean loaiDe, Model model) {
        Optional<MonHoc> mhOptional = monHocRepository.findById(maMon);

        if (mhOptional.isPresent()) {
            Pageable pageable = PageRequest.of(pageIndex - 1, 10);
            Page<De> dePage = null;

            if (loaiDe) {
                dePage = deRepository.findByLoaiDeTrue(maMon, pageable);
            } else {
                dePage = deRepository.findByLoaiDeFalse(maMon, pageable);
            }

            List<De> des = dePage.getContent();

            model.addAttribute("des", des);
            model.addAttribute("currentPage", pageIndex);
            model.addAttribute("totalPages", dePage.getTotalPages());
            model.addAttribute("totalItems", dePage.getTotalElements());
            model.addAttribute("tenMon", mhOptional.get().getTenMon());
            model.addAttribute("loaiDe", loaiDe);
        } else {
            model.addAttribute("msg", "Mã môn không tồn tại.");
        }

        return "user/thu-vien-thi/danh-sach-de";
    }

    @GetMapping("/lam-bai/{maDe}")
    public String baiThi(@PathVariable String maDe, Model model) {
        De de = deRepository.findById(maDe).get();

        List<CauHoi> cauHois = cauHoiRepository.findByDe(maDe);
        DapAnHSDTO dapAnDTO = new DapAnHSDTO();

        for (CauHoi ch : cauHois) {
            dapAnDTO.addDA(new DapAnHSModel(ch.getMaCH(), ch.getDapAnDung(),
                    ch.getDeBai(), ch.getDapAns()));
        }

        model.addAttribute("thoiGian", de.getThoiGian());
        model.addAttribute("dapAnDTO", dapAnDTO);

        return "user/thu-vien-thi/bai-thi";
    }

    @PostMapping("/ket-qua")
    public String ketQua(@ModelAttribute DapAnHSDTO dapAnDTO,
            Model model) {
        int count = 0;

        List<DapAnHSModel> daHSModel = dapAnDTO.getDaHSModels();
        DapAnHSModel daModel = null;
        int size = daHSModel.size();
        for (int i = 0; i < size; i++) {
            daModel = daHSModel.get(i);
            if(daModel.getDapAnDung().equals(daModel.getDapAn())) {                
                count++;
            }
        }
        model.addAttribute("scDung", count);
        model.addAttribute("tongSC", size);
        
        return "user/thu-vien-thi/ket-qua";
    }
}
