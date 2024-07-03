package ex.springsecurity_1805.Controllers;


import ex.springsecurity_1805.Models.Img;


import ex.springsecurity_1805.Models.Usermain;
import ex.springsecurity_1805.Repositories.ImageRepository;
import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.services.UserDEtailsService;

import lombok.RequiredArgsConstructor;


import org.springframework.core.io.InputStreamResource;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class RestControllerForImg {
    private final ImageRepository repository;

    private final UserRepository repo;

    @GetMapping("/api/images/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        Img img = repository.findById(id).orElse(null);

        assert img != null;
        return ResponseEntity.ok().header("fileName", img.getOriginalFileName())
                .contentType(MediaType.valueOf(img.getContentType()))
                .contentLength(img.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(img.getBytes())));
    }

    @GetMapping("/api/userInfo")
    public String userInfo(@AuthenticationPrincipal UserDEtailsService model) {
        Optional<Usermain> userOpt = repo.findByName(model.getUsername());
        Long id;
        if (userOpt.isPresent()) {
            Usermain user = userOpt.get();
            id = user.getPreviewImageId();
        }
        else
            id=null;

        if (id == null)
            return "12";
        else
            return id.toString();
    }
}
