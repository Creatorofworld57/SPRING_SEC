package ex.springsecurity_1805.Controllers;


import com.fasterxml.jackson.annotation.JsonView;
import ex.springsecurity_1805.Models.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://localhost:3000/", allowCredentials = "true")
@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RestController {
    private UserRepository rep;
    private ServiceApp serviceApp;


    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @CrossOrigin(origins = "https://localhost:3000/reg")
    @PostMapping("/user")
    public void addUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("hi");
        serviceApp.addUser(request, file);
    }


    @PatchMapping("/user")
    public void updateUser(@RequestParam("file") MultipartFile file, updateModel modelUp, @AuthenticationPrincipal UserDEtailsService model) throws IOException {
        System.out.println("update2");
        serviceApp.updateUser(modelUp.getName(), modelUp.getPassword(),modelUp.getTele(),modelUp.getGit(), file, model);

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

    @GetMapping("/authorization")
    public ResponseEntity<?> doYouHaveAuth(@AuthenticationPrincipal UserDEtailsService user) {
        System.out.println("ionic");
        if (user == null) {
            return ResponseEntity.status(201).build();
        } else
            return ResponseEntity.status(200).build();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    @GetMapping("programInfo/{id}")

    public Application view(@PathVariable int id) {
        System.out.println("print");

        return serviceApp.applicationById(id);

    }

    @CrossOrigin(origins = "https://localhost:3000/login")
    @PostMapping("/checking")
    public ResponseEntity<?> checkUserName(@RequestBody Data data) {
        System.out.println(data.getName());
        if (rep.findByName(data.getName()).isPresent()) {
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(200).build();
        }
    }

    @CrossOrigin(origins = "https://localhost:3000/profile",allowCredentials = "true")
    @JsonView(Views.Public.class)
    @GetMapping("/infoAboutUser")
    public Usermain infoAboutUser(@AuthenticationPrincipal UserDEtailsService user) {
        Optional<Usermain> u =  rep.findByName(user.getUsername());
        return u.orElse(null);
    }
    @CrossOrigin(origins = "https://localhost:3000/",allowCredentials = "true")
    @GetMapping("/All")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    public List<Application> All() {
        return  serviceApp.allApplications();
    }


    @CrossOrigin(origins = "https://localhost:3000/profile",allowCredentials = "true")
    @GetMapping("/socials")
    public Socials socials(@AuthenticationPrincipal UserDEtailsService userDEtailsService){
        Optional<Usermain> us = rep.findByName(userDEtailsService.getUsername());
        Socials social = new Socials();
        if(us.isPresent() && !us.get().getSocial().isEmpty()){

            social.setTelegram(us.get().getSocial().getFirst());
            social.setGit(us.get().getSocial().getLast());
        }
        else {
            social.setTelegram("tele");
            social.setGit("git");

        }
        System.out.println(social.getGit());
        return social;
    }
    @CrossOrigin(origins = "https://localhost:3000/profile",allowCredentials = "true")
    @PostMapping("/receivingSocials")
    public void receivingSocials(@RequestBody List<String>socials,@AuthenticationPrincipal UserDEtailsService user1){
        rep.findByName(user1.getUsername()).get().setSocial(socials);
    }

}
