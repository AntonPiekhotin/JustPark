package com.parking.JustPark.controller;

import com.parking.JustPark.entity.User;
import com.parking.JustPark.entity.enums.Role;
import com.parking.JustPark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * Функція створення резервної копії бази даних. Створює файл бекапу в корневій директорії диску C.
     * @return http-response "OK" якщо резервна копія створена успішно, hhtp-response "bad request"
     * якщо під час створення копії сталась помилка.
     */
    @GetMapping("/backup")
    public ResponseEntity<String> backupDB() {

        String dbName = "JustPark";
        String userName = "postgres";
        String password = "postgres";
        String backupPath = "C:\\backup_" +
                LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE) + ".sql";
        String pgDumpPath = "C:\\Program Files\\PostgreSQL\\16\\bin\\pg_dump.exe";

        //Створюємо файл бекапу
        File file = new File(backupPath);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                pgDumpPath,
                "-h", "localhost",
                "-p", "5432",
                "-U", userName,
                "-d", dbName,
                "-F", "c",
                "-b",
                "-v",
                "-f", backupPath
        );

        processBuilder.environment().put("PGPASSWORD", password);

        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Очікуємо на завершення операції створення бекапу
        try {
            int exitCode = process.waitFor();
            System.out.println("Backup completed with exit code: " + exitCode);
            return ResponseEntity.ok("Бекап створено");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Помилка створення бекапу");
    }
}
