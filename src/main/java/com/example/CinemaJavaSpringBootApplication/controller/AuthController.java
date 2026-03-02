package com.example.CinemaJavaSpringBootApplication.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private static final String USERNAME = "personalCinematograf";
    private static final String PASSWORD = "Cinem@123";

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            session.setAttribute("loggedIn", true);
            return "redirect:/dashboard";
        }

        model.addAttribute("error", "Username sau parolă greșită!");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}