package ex.springsecurity_1805.Controllers;


import ex.springsecurity_1805.Models.Usermain;
import ex.springsecurity_1805.Models.updateModel;
import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.services.ServiceApp;
import ex.springsecurity_1805.services.UserDEtailsService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RestController {
    private UserRepository rep;
    private ServiceApp serviceApp;

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostMapping("/user")
    public void addUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("hi");
        serviceApp.addUser(request, file);
    }


    @PatchMapping("/user")
    public void updateUser(@RequestParam("file") MultipartFile file, updateModel modelUp, @AuthenticationPrincipal UserDEtailsService model) throws IOException {
        serviceApp.updateUser(modelUp.getName(), modelUp.getPassword(), file, model);

    }


    @DeleteMapping("/user")
    public void deleteUser(@AuthenticationPrincipal UserDEtailsService model) {
        System.out.println(model.getUsername());
        serviceApp.deleteUser(model);
    }

    @PreAuthorize("hasAuthority('SUPERVISIOR')")
    @GetMapping("/secret/{name}")
    public ResponseEntity<?> secret(@PathVariable String name) {
        System.out.println(name);
        Optional<Usermain> userOpt = rep.findByName(name);
        if (userOpt.isPresent()) {
            Usermain user = userOpt.get();
            String password = user.getPassword();
            System.out.println(passwordEncoder().encode(password));
            return ResponseEntity.ok(new BCryptPasswordEncoder().encode(passwordEncoder().encode(password)));
        } else
            return ResponseEntity.ok("No user with such name");

    }

}
