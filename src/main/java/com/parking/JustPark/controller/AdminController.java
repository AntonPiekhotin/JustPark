package com.parking.JustPark.controller;

import com.parking.JustPark.entity.User;
import com.parking.JustPark.entity.enums.Role;
import com.parking.JustPark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    // Сторінка адмін панелі для перегляду списку користувачів
    @GetMapping
    public String adminPage(Principal principal, Model model) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("usersList", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/user/edit/{id}")
    public String userEdit(@PathVariable("id") Long id, Model model, Principal principal) {
        User userToEdit = userService.getUserById(id);
        if (userToEdit != null) {
            model.addAttribute("user", userService.getUserByPrincipal(principal));
            model.addAttribute("userToEdit", userToEdit);
            model.addAttribute("roles", Role.values());
            return "userEdit";
        }
        return null;
    }

    @PostMapping("/user/edit/{id}")
    public String userEditRoles(@PathVariable("id") Long id, @RequestParam Map<String, String> roles) {
        userService.changeUserRoles(id, roles);
        return "redirect:/admin";
    }

    @PostMapping("/user/ban/{id}")
    public String banUser(@PathVariable("id") Long id) {
        if (userService.banUser(id))
            return "redirect:/admin";
        return "Сталась помилка при блокуванні користувача.";
    }

    @PostMapping("/user/unban/{id}")
    public String unbanUser(@PathVariable("id") Long id) {
        if (userService.unbanUser(id))
            return "redirect:/admin";
        return "Сталась помилка при розблокуванні користувача.";
    }

    @PostMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        if (userService.deleteUser(id))
            return "redirect:/admin";
        return "Сталась помилка";
    }

    @GetMapping("/backup")
    public ResponseEntity<String> backupDB() {
        try {
            String backupPath = "C:\\backup_" + LocalDateTime.now() + ".tar";
            File directory = new File("C:\\JustPark\\" + backupPath);
            if (!directory.exists())
                directory.mkdirs();

            String command = "pg_dump -U postgres -h localhost -d JustPark -W";
            Process process = Runtime.getRuntime().exec(command);
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Бекап успішно створений.");
            } else {
                System.out.println("Помилка при створенні бекапу.");
            }
            return ResponseEntity.ok("Бекап успішно створений.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.ok("Помилка при створенні бекапу.");
        }
    }
}
