package doan.com.vn.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.dto.CauHoiDTO;
import doan.com.vn.entity.CauHoi;
import doan.com.vn.entity.DapAn;
import doan.com.vn.entity.De;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.model.CauHoiModel;
import doan.com.vn.model.DeModel;
import doan.com.vn.repository.CauHoiRepository;
import doan.com.vn.repository.DeRepository;
import doan.com.vn.repository.MonHocRepository;

@Controller
@RequestMapping("/admin/de")
public class DeThiController {
    @Autowired
    private DeRepository deRepository;

    @Autowired
    private CauHoiRepository cauHoiRepository;

    @Autowired
    private MonHocRepository monHocRepository;

    @GetMapping("")
    public String de(@RequestParam boolean loaiDe, Model model) {
        List<MonHoc> monHocs = monHocRepository.findByDeletedFalse();

        model.addAttribute("monHocs", monHocs);
        model.addAttribute("loaiDe", loaiDe);

        if (!loaiDe) {
            model.addAttribute("de", new DeModel(loaiDe));
        } else {
            model.addAttribute("de", new DeModel(loaiDe));
        }

        return "admin/de-thi/de";
    }

    @PostMapping("/tao-de")
    public String postDe(@ModelAttribute DeModel deModel) {

        De de = new De();
        BeanUtils.copyProperties(deModel, de);
        MonHoc monHoc = monHocRepository.findById(deModel.getMaMon()).get();
        de.setMonHoc(monHoc);
        De objectDe = deRepository.saveAndFlush(de);

        return "redirect:/admin/de/cau-hoi/" + objectDe.getMaDe();
    }

    @GetMapping("/cau-hoi/{maDe}")
    public String cauHoi(@PathVariable("maDe") String maDe, Model model) {

        Optional<De> de = deRepository.findById(maDe);
        if (de.isPresent()) {
            CauHoiDTO cauHoi = new CauHoiDTO();
            Integer soCau = de.get().getSoCau();

            for (int i = 0; i < soCau; i++) {
                cauHoi.addCauHoi(new CauHoiModel(maDe));
            }

            model.addAttribute("cauHoi", cauHoi);
            model.addAttribute("maDe", maDe);
        } else {
            model.addAttribute("msg", "Mã đề không tồn tại");
        }

        return "admin/de-thi/cau-hoi";
    }

    @PostMapping("/cau-hoi/{maDe}")
    public String postCauHoi(@PathVariable("maDe") String maDe,
            @ModelAttribute("cauHoi") CauHoiDTO cauHoi,
            RedirectAttributes redirectAttributes) {
        Optional<De> de = deRepository.findById(maDe);

        List<CauHoi> cauHois = new ArrayList<CauHoi>();
        CauHoi ch = null;
        List<CauHoiModel> chModels = cauHoi.getChModels();

        for (CauHoiModel chModel : chModels) {
            ch = new CauHoi();
            BeanUtils.copyProperties(chModel, ch);
            ch.setDe(de.get());

            List<DapAn> dapAns = new ArrayList<DapAn>();
            dapAns.add(new DapAn("A", ch, chModel.getDaA()));
            dapAns.add(new DapAn("B", ch, chModel.getDaB()));
            dapAns.add(new DapAn("C", ch, chModel.getDaC()));
            dapAns.add(new DapAn("D", ch, chModel.getDaD()));

            ch.setDapAns(dapAns);
            cauHois.add(ch);
        }

        cauHoiRepository.saveAll(cauHois);
        redirectAttributes.addFlashAttribute("msg", "Thành công.");

        if (de.get().isLoaiDe()) {
            return "redirect:/admin/de?loaiDe=1";
        } else {
            return "redirect:/admin/de?loaiDe=0";
        }

    }
}
