package doan.com.vn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import doan.com.vn.entity.User;


@Controller
public class AuthController {
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//    
//    @Autowired
//    private UserRepository userRepository;
    
//    @GetMapping("/register")
//    public String registerUI(Model model) {
//        model.addAttribute("user", new User());
//        return "auth/register";
//    }
//    
//    @PostMapping("/register")
//    public String registerUI(@ModelAttribute("user") User user) {
//        String encodePass = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodePass);
//        user.setRoleName(RoleName.USER);
//        
//        userRepository.save(user);
//        
//        return "redirect:/login";
//    }
    @GetMapping("/admin")
    public String adminPage(Model model) {
        return "admin/index";
    }
    
    @GetMapping("/login")
    public String loginUI(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    
    @PostMapping("/login")
    public String loginUI(@ModelAttribute("user") User user) {
        
        return "redirect:/admin/";
    }
}
