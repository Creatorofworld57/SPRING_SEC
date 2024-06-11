package ex.springsecurity_1805.Controllers;




import ex.springsecurity_1805.servisies.ServiceApp;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/api")
@AllArgsConstructor
public class ControllerF {
    private ServiceApp serviceApp;
    @GetMapping("/Welcome")
    public String welcome() {
        return "Welcome";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public String view (@PathVariable int id, Model model){
        List<String> lst = new ArrayList<>();
        lst.add("Id: "+ serviceApp.applicationById(id).getId());
        lst.add("Name: "+ serviceApp.applicationById(id).getName());
        lst.add("Author: "+serviceApp.applicationById(id).getAuthor());
        lst.add("Version: "+serviceApp.applicationById(id).getVersion());
        model.addAttribute("list",lst);
        return "id";
    }
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/name/{name}")
    public String view1 (String name,Model model){
        List<String> lst = new ArrayList<>();
        lst.add("Id: "+ serviceApp.applicationByName(name).getId());
        lst.add("Name: "+ serviceApp.applicationByName(name).getName());
        lst.add("Author: "+serviceApp.applicationByName(name).getAuthor());
        lst.add("Version: "+serviceApp.applicationByName(name).getVersion());
        model.addAttribute("list",lst);
        return "id";

    }

    @GetMapping("/newUser")
    public String addUser(){
       return "NewUser";
    }

    @GetMapping("/All")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String All(Model model){
        model.addAttribute("All",serviceApp.allApplications());
        return "All";
    }
    @GetMapping("/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String update(){
        return "Update2";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/Profile")
    public String profile(){
        return "Profile";
    }


}
