package doan.com.vn.controller.admin;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
import doan.com.vn.entity.GiaoVien;
import doan.com.vn.entity.HocSinh;
import doan.com.vn.entity.Lop;
import doan.com.vn.entity.MonHoc;
import doan.com.vn.model.DiemModel;
import doan.com.vn.repository.DiemRepository;
import doan.com.vn.repository.GiaoVienRepository;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.LopRepository;
import doan.com.vn.repository.MonHocRepository;
import doan.com.vn.utils.ExcelGenerator;

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
    private GiaoVienRepository giaoVienRepository;

    @Autowired
    private LopRepository lopRepository;

    @GetMapping({ "/", "/{tenLop}/{maMon}/{hocKy}" })
    public String showDiemAdmin(
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

        hocKy = (hocKy == null) ? 1 : hocKy;

        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", hsPage.getTotalPages());
        model.addAttribute("totalItems", hsPage.getTotalElements());
        model.addAttribute("tenLop", tenLop);
        model.addAttribute("maMon", maMon);
        model.addAttribute("hocKy", hocKy);
        model.addAttribute("lops", lopRepository.findByDeletedFalse());
        model.addAttribute("monHocs", monHocRepository.findByDeletedFalse());

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

        return "admin/diem/add2";
    }

    @PostMapping("/saveOrUpdate/{tenLop}/{maMon}/{hocKy}")
    public String saveOrUpdateAdmin(
            @PathVariable(value = "tenLop") String tenLop,
            @PathVariable(value = "maMon") String maMon,
            @PathVariable(value = "hocKy") Integer hocKy,
            @ModelAttribute("diemDTO") DiemCreationDTO diemDTO,
            RedirectAttributes redirectAttributes) {
        Diem diemU = null;
        List<Diem> diems = new ArrayList<Diem>();

        MonHoc monHoc = monHocRepository.findById(maMon).get();
        HocSinh hocSinh = null;

        for (DiemModel dm : diemDTO.getDiemModels()) {
            DiemPK diemPK = new DiemPK(dm.getMaHS(), maMon, hocKy);
            hocSinh = hocSinhRepository.findById(dm.getMaHS()).get();
            diemU = new Diem();
            diemU.setId(diemPK);
            diemU.setMonHoc(monHoc);
            diemU.setHocSinh(hocSinh);

            BeanUtils.copyProperties(dm, diemU);

            diems.add(diemU);

        }

        diemRepository.saveAll(diems);
        redirectAttributes.addFlashAttribute("msg", "Nhập điểm thành công.");

        return "redirect:/admin/diem/" + tenLop + "/" + maMon + "/" + hocKy;
    }

    @GetMapping({ "", "/{tenLop}/{hocKy}" })
    public String showDiem(
            @PathVariable(value = "tenLop", required = false) String tenLop,
            @PathVariable(value = "hocKy", required = false) Integer hocKy,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            Model model) {

        Pageable pageable = PageRequest.of(pageIndex - 1, 10,
                Sort.by(Sort.Direction.ASC, "ten"));
        Page<HocSinh> hsPage = hocSinhRepository.findAllStudentByClass(tenLop,
                pageable);
        List<HocSinh> hocSinhs = hsPage.getContent();
        List<Lop> lops = lopRepository.findByGiaoVien(username);

        hocKy = (hocKy == null) ? 1 : hocKy;

        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", hsPage.getTotalPages());
        model.addAttribute("totalItems", hsPage.getTotalElements());
        model.addAttribute("tenLop", tenLop);
        model.addAttribute("hocKy", hocKy);
        model.addAttribute("lops", lops);

        Optional<GiaoVien> gvOptional = giaoVienRepository.findById(username);
        if (gvOptional.isPresent()) {
            GiaoVien gv = gvOptional.get();
            MonHoc monHoc = gv.getMonHoc();
            model.addAttribute("maGV", gv.getMaGV());
            model.addAttribute("monHoc", monHoc);
            String maMon = monHoc.getMaMon();

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
        }

        return "admin/diem/add";
    }

    @PostMapping("/saveOrUpdate/{tenLop}/{hocKy}")
    public String saveOrUpdate(@PathVariable(value = "tenLop") String tenLop,
            @PathVariable(value = "hocKy") Integer hocKy,
            @RequestParam(value = "username") String username,
            @ModelAttribute("diemDTO") DiemCreationDTO diemDTO,
            RedirectAttributes redirectAttributes) {
        Diem diemU = null;
        List<Diem> diems = new ArrayList<Diem>();

        GiaoVien gv = giaoVienRepository.findById(username).get();
        MonHoc monHoc = gv.getMonHoc();
        String maMon = monHoc.getMaMon();

        for (DiemModel dm : diemDTO.getDiemModels()) {
            DiemPK diemPK = new DiemPK(dm.getMaHS(), maMon, hocKy);
            diemU = new Diem();
            diemU.setId(diemPK);
            diemU.setMonHoc(monHoc);
            diemU.setHocSinh(hocSinhRepository.findById(dm.getMaHS()).get());

            BeanUtils.copyProperties(dm, diemU);
            diems.add(diemU);
        }

        diemRepository.saveAll(diems);
        redirectAttributes.addFlashAttribute("msg", "Nhập điểm thành công.");

        return "redirect:/admin/diem/" + tenLop + "/" + hocKy + "?username="
                + username;
    }

    @GetMapping("/thong-ke/{tenLop}/{maMon}")
    public String thongke(
            @PathVariable(value = "tenLop", required = false) String tenLop,
            @PathVariable(value = "maMon", required = false) String maMon,
            Model model) {

        List<Diem> diems = diemRepository.findByLopAndMon(tenLop, maMon);
        List<Integer> soLuong = new ArrayList<Integer>();
        Integer countG = 0;
        Integer countKH = 0;
        Integer countTB = 0;
        Integer countY = 0;
        Integer countK = 0;

        for (Diem diem : diems) {
            if (diem.diemTB() > 8.0) {
                countG++;
            } else if (diem.diemTB() > 6.5) {
                countKH++;
            } else if (diem.diemTB() > 5.0) {
                countTB++;
            } else if (diem.diemTB() > 3.5) {
                countY++;
            } else {
                countK++;
            }
        }

        soLuong.add(0);
        soLuong.add(countG);
        soLuong.add(countKH);
        soLuong.add(countTB);
        soLuong.add(countY);
        soLuong.add(countK);
        soLuong.add(0);

        diems.sort((d1, d2) -> {
            return (int) (d2.diemTB() - d1.diemTB());
        });
        if (diems.size() > 5) {
            model.addAttribute("diemTop", diems.subList(0, 4));
        } else {
            model.addAttribute("diemTop", diems);
        }

        model.addAttribute("monHoc", monHocRepository.findById(maMon).get());
        model.addAttribute("soLuong", soLuong);
        return "admin/diem/thong-ke";
    }

    @GetMapping("/quan-ly-lop")
    public String list(@RequestParam(value = "username") String username,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 10);
        Page<Lop> lopPage = lopRepository.findByGiaoVienCN(username, pageable);
        if (lopPage != null) {
            List<Lop> lops = lopPage.getContent();

            model.addAttribute("lops", lops);
            model.addAttribute("currentPage", pageIndex);
            model.addAttribute("totalPages", lopPage.getTotalPages());
            model.addAttribute("totalItems", lopPage.getTotalElements());
        } else {
            model.addAttribute("msg", "Không có lớp nào.");
        }

        return "admin/diem/list";
    }

    @GetMapping({ "/xem-diem/{tenLop}", "/xem-diem/{tenLop}/{maMon}/{hocKy}" })
    public String xemDiem(@PathVariable(value = "tenLop") String tenLop,
            @PathVariable(value = "maMon", required = false) String maMon,
            @PathVariable(value = "hocKy", required = false) Integer hocKy,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int pageIndex,
            Model model) {

        List<MonHoc> monHocs = monHocRepository.findByDeletedFalse();

        Pageable pageable = PageRequest.of(pageIndex - 1, 10,
                Sort.by(Sort.Direction.ASC, "ten"));
        Page<HocSinh> hsPage = hocSinhRepository.findAllStudentByClass(tenLop,
                pageable);
        List<HocSinh> hocSinhs = hsPage.getContent();

        hocKy = (hocKy == null) ? 1 : hocKy;

        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", hsPage.getTotalPages());
        model.addAttribute("totalItems", hsPage.getTotalElements());
        model.addAttribute("tenLop", tenLop);
        model.addAttribute("hocKy", hocKy);
        model.addAttribute("maMon", maMon);

        model.addAttribute("monHocs", monHocs);

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

        return "admin/diem/xem-diem";
    }

    @GetMapping("/export-to-excel/{tenLop}/{maMon}")
    public void exportIntoExcelFile(
            @PathVariable(value = "tenLop", required = false) String tenLop,
            @PathVariable(value = "maMon", required = false) String maMon,
            HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=diem" + currentDateTime
                + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Diem> listOfStudents = diemRepository.findByLopAndMon(tenLop,
                maMon);
        ExcelGenerator generator = new ExcelGenerator(listOfStudents);
        generator.generateExcelFile(response);
    }
}
