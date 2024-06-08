package com.example.demo.controller;

import com.example.demo.dto.ReqRegister;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repo.AttachmentRepository;
import com.example.demo.repo.RoleRepository;
import com.example.demo.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;
    private final PasswordEncoder getPasswordEncoder;
    private final RoleRepository roleRepository;

    public AuthController(UserRepository userRepository,
                          AttachmentRepository attachmentRepository, PasswordEncoder getPasswordEncoder,
                          RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.getPasswordEncoder = getPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) Boolean error, Model model, RedirectAttributes redirectAttributes) {
        if (error != null) {
            redirectAttributes.addFlashAttribute("error", "Login yoki parol xato");
            return "redirect:/auth/login";
        }
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "auth/logout";
    }
    @GetMapping("/register")
    public String register() {
        return "auth/register";
    }
    @Transactional
    @PostMapping("/register")
    public String registration(@ModelAttribute ReqRegister reqRegister, @RequestParam MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if(userRepository.existsByEmail(reqRegister.email())){
            redirectAttributes.addFlashAttribute("error", "Email already in use");
            return "redirect:/auth/register";
        }
        if(!reqRegister.password().equals(reqRegister.passwordRepeat())){
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/auth/register";
        }
        Attachment profilePhoto=Attachment.builder()
                .content(file.getBytes())
                .build();
        attachmentRepository.save(profilePhoto);

        Role defoultUserRole = roleRepository.findByName("ROLE_USER");

        User user=User.builder()
                .email(reqRegister.email())
                .password(getPasswordEncoder.encode(reqRegister.password()))
                .firstName(reqRegister.firstName())
                .lastName(reqRegister.lastName())
                .age(reqRegister.age())
                .attachment(profilePhoto)
                .roles(List.of(defoultUserRole))
                .build();
        userRepository.save(user);
        return "redirect:/auth/login";

    }


}
