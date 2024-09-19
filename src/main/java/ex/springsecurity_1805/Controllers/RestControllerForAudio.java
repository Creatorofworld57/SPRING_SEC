package ex.springsecurity_1805.Controllers;


import ex.springsecurity_1805.Models.Audio;
import ex.springsecurity_1805.Repositories.AudioRepository;
import ex.springsecurity_1805.services.UserDEtailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@EnableWebMvc
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestControllerForAudio {
    private final AudioRepository audioRepository;
    @Transactional
    @GetMapping("/audio/{id}")
    public ResponseEntity<?> getAudio(@PathVariable Long id)  {
        Audio audio = audioRepository.getReferenceById(id);


        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(audio.getContentType()))
                .contentLength(audio.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(audio.getBuffer())));
    }
    @PostMapping("/audio")
    public void audioSend(@RequestParam("file") MultipartFile file) throws IOException {
        Audio audio = new Audio();

        StringBuilder str = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
        str.delete(str.indexOf("."), str.lastIndexOf("3") + 1);

        audio.setBuffer(file.getBytes());
        audio.setName(str.toString());

        audio.setContentType(file.getContentType());
        audio.setSize(file.getSize());
        System.out.println(file.getSize());
        audioRepository.save(audio);
        System.out.println(audio.getId());

    }

    @GetMapping("/audioName/{id}")
    public Map<String,String> audioName(@PathVariable Long id) {
        Optional<Audio> opt = audioRepository.findAudioById(id);
        return opt.map(audio -> Map.of("name", audio.getName())).orElseGet(() -> Map.of("name", "Track"));
    }

    //доделать счетчик (сделано)
    @GetMapping("/audioCount")
    public String audioCount() {
        long counter = audioRepository.count();
        return Long.toString(102+(counter-1)*50);

    }
    @CrossOrigin(origins = "https://localhost:3000/audio_playlist",allowCredentials = "true")
    @GetMapping ("/playList")
    public List<String> playList(@AuthenticationPrincipal UserDEtailsService user1){
        return new ArrayList<>(audioRepository.findAllByOrderByIdAsc());

    }

}
