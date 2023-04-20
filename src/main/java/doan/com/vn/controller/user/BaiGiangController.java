package doan.com.vn.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doan.com.vn.entity.BaiGiang;
import doan.com.vn.entity.BinhLuan;
import doan.com.vn.entity.DanhSachVideo;
import doan.com.vn.model.BaiGiangModel;
import doan.com.vn.model.BinhLuanModel;
import doan.com.vn.repository.BaiGiangRepository;
import doan.com.vn.repository.BinhLuanRepository;
import doan.com.vn.repository.DanhSachVideoRepository;

@Controller
@RequestMapping("/user")
public class BaiGiangController {
    @Autowired
    private BaiGiangRepository baiGiangRepository;
    
    @Autowired
    private BinhLuanRepository binhLuanRepository;
    
    @Autowired
    private DanhSachVideoRepository danhSachVideoRepository;
    
    @GetMapping("/thu-vien-bai-giang")
    public String list(@RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 6);
        Page<BaiGiang> bgPages = baiGiangRepository.findByDeletedFalse(pageable);
        List<BaiGiang> baiGiangs = bgPages.getContent();
        List<BaiGiangModel> baiGiangModels = new ArrayList<BaiGiangModel>();
        BaiGiangModel bgModel = null;
        
        for(BaiGiang bg : baiGiangs) {
            bgModel = new BaiGiangModel();
            BeanUtils.copyProperties(bg, bgModel);
            long soBL = binhLuanRepository.countByMaBG(bg.getMaBG());
            bgModel.setSoBL(soBL);
            
            baiGiangModels.add(bgModel);
        }
        
        model.addAttribute("baiGiangModels", baiGiangModels);
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", bgPages.getTotalPages());
        model.addAttribute("totalItems", bgPages.getTotalElements());
        
        return "user/thu-vien-bai-giang";
    }
    
    @GetMapping("/danh-sach-video/{maBG}")
    public String listVideo(
            @RequestParam(value = "pageIndex", required = false, defaultValue = "1") Integer pageIndex,
            @PathVariable Integer maBG,
            Model model) {
        Pageable pageable = PageRequest.of(pageIndex - 1, 4);
        Page<DanhSachVideo> dsPages = danhSachVideoRepository.findByMaBG(maBG, pageable);
        List<DanhSachVideo> danhSachVideos = dsPages.getContent();
        List<BinhLuan> binhLuans = binhLuanRepository.findByMaBG(maBG);
        List<BinhLuanModel> blModels = new ArrayList<BinhLuanModel>();
        BinhLuanModel blModel = null;
        
        for(BinhLuan bl : binhLuans) {
            blModel = new BinhLuanModel();
            BeanUtils.copyProperties(bl, blModel);
            blModel.setAccount(bl.getUser().getUsername());
            
            blModels.add(blModel);
        }
        
        model.addAttribute("danhSachVideos", danhSachVideos);
        model.addAttribute("blModels", blModels);
        model.addAttribute("maBG", maBG);
        model.addAttribute("blModel", new BinhLuanModel());
        model.addAttribute("currentPage", pageIndex);
        model.addAttribute("totalPages", dsPages.getTotalPages());
        model.addAttribute("totalItems", dsPages.getTotalElements());
        
        return "user/danh-sach-video";
    }
}
