package ex.springsecurity_1805.Controllers;


import ex.springsecurity_1805.services.ServiceApp;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;





public class ControllerF {
    /*
    private ServiceApp serviceApp;

   @GetMapping("/Welcome")
    public String welcome() {
        return "Welcome";
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    @GetMapping("/{id}")
    public String view(@PathVariable int id, Model model) {
        List<String> lst = new ArrayList<>();
        lst.add(STR."Id: \{serviceApp.applicationById(id).getId()}");
        lst.add("Name: " + serviceApp.applicationById(id).getName());
        lst.add("Author: " + serviceApp.applicationById(id).getAuthor());
        lst.add("Version: " + serviceApp.applicationById(id).getVersion());
        model.addAttribute("list", lst);
        return "id";
    }

    @GetMapping("/newUser")
    public String addUser() {
        return "NewUser";
    }

    @GetMapping("/All")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    public String All(Model model) {
        model.addAttribute("All", serviceApp.allApplications());
        return "All";
    }

    @GetMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('SUPERVISIOR')")
    public String update() {
        return "Update2";
    }

    @GetMapping("/Profile")
    public String profile() {
        return "Profile";
    }

    @GetMapping("/audio")
    public String audio() {
        return "Audio";
    }

    @GetMapping("/audioList")
    public String audioList() {
        return "audioList";
    }
    @PreAuthorize("hasAuthority('SUPERVISIOR')")
    @GetMapping("/security")
    public String security() {
        return "Security";
    }
*/

}
