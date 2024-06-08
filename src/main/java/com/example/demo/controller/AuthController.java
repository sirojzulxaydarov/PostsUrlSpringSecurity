package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

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


}
