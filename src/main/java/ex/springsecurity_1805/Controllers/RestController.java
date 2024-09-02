package ex.springsecurity_1805.Controllers;


import com.fasterxml.jackson.annotation.JsonView;
import ex.springsecurity_1805.Models.*;
import ex.springsecurity_1805.Repositories.TrailerRepository;
import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.services.ServiceApp;
import ex.springsecurity_1805.services.UserDEtailsService;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@CrossOrigin(origins="https://localhost:3000",allowCredentials = "true")
@org.springframework.web.bind.annotation.RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RestController {
    private UserRepository rep;
    private ServiceApp serviceApp;
    private TrailerRepository trailerRepository;
    private ServiceApp service;


    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @PostMapping("/user")
    public void addUser(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println("hi");
        serviceApp.addUser(request, file);
    }
    @PostMapping("/user/withGithub")
    public void addUserWithGitHub(@AuthenticationPrincipal OAuth2User principal){

    }


    @PatchMapping("/user")
    public void updateUser(@RequestParam("file") MultipartFile file, updateModel modelUp, @AuthenticationPrincipal UserDEtailsService model,@AuthenticationPrincipal OAuth2User principal) throws IOException {
        System.out.println("update2");
        if(model!=null)
          serviceApp.updateUser(modelUp.getName(), modelUp.getPassword(),modelUp.getTele(),modelUp.getGit(), file, model);
        else {
            Object loginValue = principal.getAttributes().get("login");
            Usermain  user = rep.findByName(loginValue.toString()).get();
            UserDEtailsService dEtailsService=new UserDEtailsService(user);
            serviceApp.updateUser(modelUp.getName(), modelUp.getPassword(),modelUp.getTele(),modelUp.getGit(), file, dEtailsService);
        }
    }


    @DeleteMapping("/user")
    public void deleteUser(@AuthenticationPrincipal UserDEtailsService model) {
        System.out.println(model.getUsername());
        serviceApp.deleteUser(model);
    }
    //Deprecated
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
    public Mono<ResponseEntity<?>> doYouHaveAuth(@AuthenticationPrincipal UserDEtailsService user,@AuthenticationPrincipal OAuth2User principal) throws IOException {

        if (principal==null && user==null ) {
            System.out.println("Не авторизован ");

            return Mono.just(ResponseEntity.status(201).build());
        }
        else{
            if(principal!=null){
                System.out.println(" авторизован"+principal.getName()+principal.getAttributes());
                Object loginValue = principal.getAttributes().get("login");
                System.out.println(loginValue);
               if(rep.findByName(loginValue.toString()).isEmpty()){
                   service.newUserWithOAuth(principal);
               }
            }
            System.out.println(" авторизован");
            return Mono.just(ResponseEntity.status(200).build());
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    @GetMapping("programInfo/{id}")
    public Application view(@PathVariable int id) {
        System.out.println("Инфа о пользователе доставлена");

        return serviceApp.applicationById(id);

    }

    @CrossOrigin(origins="https://localhost:3000",allowCredentials = "true")
    @PostMapping("/checking")
    public ResponseEntity<?> checkUserName(@RequestBody Data data) {
        System.out.println(data.getName());
        if (rep.findByName(data.getName()).isPresent()) {
            return ResponseEntity.status(201).build();
        } else {
            return ResponseEntity.status(200).build();
        }
    }


    @JsonView(Views.Public.class)
    @GetMapping("/infoAboutUser")
    public Mono<Usermain> infoAboutUser(@AuthenticationPrincipal UserDEtailsService user,@AuthenticationPrincipal OAuth2User principal) {
        if(user !=null) {
            Optional<Usermain> u = rep.findByName(user.getUsername());

            assert u.orElse(null) != null;
            return Mono.just(u.orElse(null));
        }
        else{
            Object loginValue = principal.getAttributes().get("login");
            Optional<Usermain> u = rep.findByName(loginValue.toString());

            assert u.orElse(null) != null;
            return Mono.just(u.orElse(null));
        }
    }

    @GetMapping("/All")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    public List<Application> All() {
        return  serviceApp.allApplications();
    }



    @GetMapping("/socials")
    public Mono<Socials> socials(@AuthenticationPrincipal UserDEtailsService userDEtailsService,@AuthenticationPrincipal OAuth2User principal){
        Socials social = new Socials();
        if(userDEtailsService!=null) {
            Optional<Usermain> us = rep.findByName(userDEtailsService.getUsername());
            if (us.isPresent() && !us.get().getSocial().isEmpty()) {

                social.setTelegram(us.get().getSocial().getFirst());
                social.setGit(us.get().getSocial().getLast());
            } else {
                social.setTelegram("tele");
                social.setGit("git");

            }
        }
        else{
            Object obj=principal.getAttributes().get("html_url");
            social.setTelegram("tele");
            social.setGit(obj.toString());
        }
        System.out.println(social.getGit());
        return Mono.just(social);
    }

    @PostMapping("/receivingSocials")
    public void receivingSocials(@RequestBody List<String>socials,@AuthenticationPrincipal UserDEtailsService user1){
        rep.findByName(user1.getUsername()).get().setSocial(socials);
    }
    @GetMapping("/trailer")
    public ResponseEntity<?> getTrailer() {
        Trailer trailer = trailerRepository.findById(1L).orElse(null);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(new InputStreamResource(new ByteArrayInputStream(trailer.getSize())));
    }
    @PostMapping("/uploadTrailer")
    public void uploadTrailer(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("good");
        Trailer trailer = new Trailer();
        trailer.setSize(file.getBytes());
        trailerRepository.save(trailer);

    }

}
