package ex.springsecurity_1805.Controllers;


import ex.springsecurity_1805.Models.Usermain;
import ex.springsecurity_1805.Models.updateModel;
import ex.springsecurity_1805.servisies.ServiceApp;
import ex.springsecurity_1805.servisies.UserDEtailsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private ServiceApp serviceApp;
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    //@GetMapping("/{id}")
    //public Application applicationByID(@PathVariable int id){
    //   return serviceApp.applicationById(id);
    //}

    @PostMapping("/newUserPost")
    public void addUser(@RequestParam("file") MultipartFile file1, Usermain user) throws IOException {
        serviceApp.addUser(user, file1);
    }

    @Transactional
    @PatchMapping("/updateData")
    public void updateUser(@RequestParam("file") MultipartFile file, updateModel modelUp, @AuthenticationPrincipal UserDEtailsService model) throws IOException {
        serviceApp.updateUser(modelUp.getName(), modelUp.getPassword(), file, model);

    }

    @Transactional
    @DeleteMapping("/api/delete")
    public void deleteUser(@AuthenticationPrincipal UserDEtailsService model) {
        System.out.println(model.getUsername());
        serviceApp.deleteUser(model);
    }

}
