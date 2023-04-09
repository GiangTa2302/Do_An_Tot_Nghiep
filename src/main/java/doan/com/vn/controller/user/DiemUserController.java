package doan.com.vn.controller.user;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.entity.Diem;
import doan.com.vn.entity.DiemPK;
import doan.com.vn.entity.HocSinh;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.model.DiemModel;
import doan.com.vn.model.DiemPKModel;
import doan.com.vn.repository.DiemRepository;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.MonHocRepository;

@Controller
@RequestMapping("user")
public class DiemUserController {
    @Autowired
    private DiemRepository diemRepository;
    
    @Autowired
    private MonHocRepository monHocRepository;
    
    @Autowired
    private HocSinhRepository hocSinhRepository;
    
    @ModelAttribute("monHocs")
    private List<MonHoc> getAllMH(){
        return monHocRepository.findByDeletedFalse();
    }
    
    @GetMapping("/ket-qua-kt-thi")
    public String ketquakt(Model model) {
        model.addAttribute("diemKTModel", new DiemPKModel());
        model.addAttribute("diemThiModel", new DiemModel());
        return "user/ket-qua-kt-thi";
    }
    
    @PostMapping("/ket-qua-kt")
    public String ketquakt(
            @Valid @ModelAttribute("diemKTModel") DiemPKModel diemKTModel,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "user/ket-qua-kt-thi";
        }
        
        DiemPK diemPK = new DiemPK(diemKTModel.getMaHS(), diemKTModel.getMaMon(), diemKTModel.getHocKy());
        
        Optional<HocSinh> hs = hocSinhRepository.findById(diemKTModel.getMaHS());
        
        if(!hs.isPresent()) {
            redirectAttributes.addFlashAttribute("msg1", "Mã học sinh không chính xác");
        } else {
            Optional<Diem> diem = diemRepository.findById(diemPK);
            DiemModel diemModel = new DiemModel();
            if(diem.isPresent()) {
                BeanUtils.copyProperties(diem.get(), diemModel);
            }
            diemModel.setHoTen(hs.get().getHodem() + " " + hs.get().getTen());
            redirectAttributes.addFlashAttribute("diemKT", diemModel);
        }
        
        return "redirect:/user/ket-qua-kt-thi";
    }
    
    @PostMapping("/ket-qua-thi")
    public String ketquathi(
            @Valid @ModelAttribute("diemThiModel") DiemModel diemThiModel,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if(result.hasErrors()) {
            return "user/ket-qua-kt-thi";
        }
        
        Optional<HocSinh> hs = hocSinhRepository.findById(diemThiModel.getMaHS());
        
        if(!hs.isPresent()) {
            redirectAttributes.addFlashAttribute("msg2", "Mã học sinh không chính xác");
        }else {
            List<Diem> diems = diemRepository.findByHocSinhAndHocKy(diemThiModel.getMaHS(), diemThiModel.getHocKy());
            
            List<DiemModel> diemModels = new ArrayList<DiemModel>();
            DiemModel diemModel = null;
            
            for(Diem diem : diems) {
                diemModel = new DiemModel();
                BeanUtils.copyProperties(diem, diemModel);
                diemModel.setMaMon(diem.getMonHoc().getTenMon());
                
                diemModels.add(diemModel);
            }
            
            redirectAttributes.addFlashAttribute("diemModels", diemModels);
            redirectAttributes.addFlashAttribute("hoTen", hs.get().getHodem() + " " + hs.get().getTen());
        }
        
        return "redirect:/user/ket-qua-kt-thi";
    }
    
    
}
