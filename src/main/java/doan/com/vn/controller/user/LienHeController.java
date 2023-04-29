package doan.com.vn.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import doan.com.vn.model.EmailModel;
import doan.com.vn.service.EmailSenderService;

@Controller
public class LienHeController {
    @Autowired
    private EmailSenderService emailService;
    
    @GetMapping("/lien-he")
    public String lienhe(
            Model model) {
        model.addAttribute("mail", new EmailModel());
        return "user/lien-he";
    }
    
    @PostMapping("/lien-he")
    public String sendMail(
            @ModelAttribute EmailModel mail,
            RedirectAttributes redirectAttributes) {
        emailService.sendEmail(mail.getFromEmail(), "hieugiang2k1@gmail.com", mail.getSubject(), mail.getBody());
        
        redirectAttributes.addFlashAttribute("msg", "Gửi mail thành công!");
        return "redirect:/lien-he";
    }
}
