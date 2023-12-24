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
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("admin")
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
    public String userEdit(@PathVariable("id") Long id, @RequestParam Map<String, String> roles) {
        userService.changeUserRoles(id, roles);
        return "redirect:/admin";
    }

    @GetMapping("/backup")
    public ResponseEntity<String> backupDB() {
        try {
            String dbName = "JustPark";
            String userName = "postgres";
            String password = "postgres";
            String backupPath = "C:\\JustPark";
            String command = String.format("pg_dump --no-owner --dbname=postgresql://%s:%s@localhost:5432/%s > %s", userName, password, dbName, backupPath);
            Process process = Runtime.getRuntime().exec(command);

            int processComplete = process.waitFor();
            if (processComplete == 0) {
                return ResponseEntity.ok("Резервну копію створено!");
            } else {
                return ResponseEntity.ok("При створенні резервної копії сталась помилка");
            }
        } catch (IOException | InterruptedException e) {
            return ResponseEntity.ok("При створенні резервної копії сталась помилка");
        }
    }
}
