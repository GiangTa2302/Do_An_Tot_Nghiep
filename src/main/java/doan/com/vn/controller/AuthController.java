package doan.com.vn.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import doan.com.vn.dto.PasswordDTO;
import doan.com.vn.entity.PasswordResetToken;
import doan.com.vn.entity.User;
import doan.com.vn.repository.BaiVietRepository;
import doan.com.vn.repository.GiaoVienRepository;
import doan.com.vn.repository.HocSinhRepository;
import doan.com.vn.repository.LopRepository;
import doan.com.vn.repository.MonHocRepository;
import doan.com.vn.repository.PasswordTokenRepository;
import doan.com.vn.repository.UserRepository;
import doan.com.vn.service.EmailSenderService;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private GiaoVienRepository giaoVienRepository;
    
    @Autowired
    private HocSinhRepository hocSinhRepository;
    
    @Autowired
    private MonHocRepository monHocRepository;
    
    @Autowired
    private BaiVietRepository baiVietRepository;
    
    @Autowired
    private LopRepository lopRepository;
    
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private EmailSenderService emailService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        
        model.addAttribute("slGiaoVien", giaoVienRepository.countByDeletedFalse());
        model.addAttribute("slHocSinh", hocSinhRepository.countByDeletedFalse());
        model.addAttribute("slLop", lopRepository.countByDeletedFalse());
        model.addAttribute("slMon", monHocRepository.countByDeletedFalse());
        model.addAttribute("slBaiViet", baiVietRepository.countByDeletedFalse());
        return "admin/index";
    }

    @GetMapping("/login")
    public String loginUI(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginUI(@ModelAttribute("user") User user) {

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/resetPassword")
    public String forgotPass(Model model) {
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "forgot-password";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@ModelAttribute("passwordDTO") PasswordDTO passwordDTO, 
            Model model) {
        
        User user = userRepository.findByEmail(passwordDTO.getEmail());

        if (user == null) {
            model.addAttribute("msg", "Email không tồn tại!");
            return "forgot-password";
        }

        String token = UUID.randomUUID().toString();
        createPasswordResetTokenForUser(user, token);

        String body = "Token reset password: " + token;

        emailService.sendEmail("hieugiang2k1@gmail.com", user.getEmail(),
                "Reset Password", body);

        return "redirect:/changePassword?email=" + passwordDTO.getEmail();
    }

    private void createPasswordResetTokenForUser(User user, String token) {
        
        PasswordResetToken myToken = passwordTokenRepository.findByUser(user);
        myToken.setToken(token);
        myToken.setUser(user);
        passwordTokenRepository.save(myToken);
    }

    @GetMapping("/changePassword")
    public String changePass(@RequestParam("email") String email ,
            Model model) {
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setEmail(email);
        
        model.addAttribute("passwordDTO", passwordDTO);
        return "change-password";
    }

    @PostMapping("/changePassword")
    public String changePass(
            @ModelAttribute("passwordDTO") PasswordDTO passwordDTO,
            Model model) {
        User user = userRepository.findByEmail(passwordDTO.getEmail());
        if (user == null) {
            model.addAttribute("msg", "Email không tồn tại!");
            return "change-password";
        }
        
        String token = passwordTokenRepository.findByUser(user).getToken();
        if(!passwordDTO.getToken().equals(token)) {
            model.addAttribute("msg", "Mã xác thực không đúng!");
            return "change-password";
        }   
        
        user.setPassword(passwordDTO.getNewPassword());
        userRepository.save(user);
        
        return "redirect:/login";
    }
}
